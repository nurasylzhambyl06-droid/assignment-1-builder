# Assignment 1 — Builder Pattern: Design Under Changing Requirements

## 1. Problem description

The selected domain is **Computer Configuration**. A computer configuration contains multiple required and optional properties. Constructing such an object with one large constructor makes client code difficult to read because many arguments have similar types and optional values are easy to confuse.

The Builder Pattern is appropriate because the configuration can be assembled step by step and the final object can be validated before it is created.

## 2. Individual variant

| Item | Selected variant |
|---|---|
| Domain | Computer Configuration |
| Constraint 1 | Gaming computers require a dedicated GPU. |
| Constraint 2 | Windows 11 requires at least 8 GB of RAM. |
| Presets | BASIC, GAMING, PROFESSIONAL |

The product contains 11 meaningful properties, including 4 required properties and 7 optional properties. It uses `String`, `int`, `boolean`, and `enum` data types. `Storage` is a supporting value object.

### Required properties

1. CPU
2. RAM
3. Storage
4. Operating System

### Optional properties

1. GPU
2. Wi-Fi
3. Bluetooth
4. Monitor
5. Keyboard
6. Mouse
7. Usage Type

## 3. Part A — Initial constructor-based solution

The initial version is preserved in `legacy/ComputerConstructorVersion.java` and is represented in the Git history by the first commit.

Example construction:

```java
ComputerConstructorVersion computer = new ComputerConstructorVersion(
        "Intel Core i7",
        16,
        new Storage("SSD", 1000),
        "Windows 11",
        "RTX 4060",
        true,
        true,
        "27 inch Gaming Monitor",
        "Mechanical Keyboard",
        "Gaming Mouse",
        UsageType.GAMING
);
```

### Problems with the constructor approach

**Problem 1 — readability.** The client must remember the position of every argument. For example, two consecutive boolean values (`wifi`, `bluetooth`) do not communicate their meaning at the call site.

**Problem 2 — maintenance.** Adding another optional property requires changing the constructor and every existing call that depends on its parameter list.

**Problem 3 — error-prone configuration.** Values with the same or compatible types can be placed in the wrong position without making the client code self-explanatory. The constructor also does not naturally show which optional values were intentionally selected.

## 4. Part B — Builder solution

The final implementation contains the required Builder participants:

- **Product:** `Computer`
- **Builder:** `Computer.Builder`
- **Build operation:** `build()`
- **Client:** `Main`
- **Director:** `ComputerDirector`
- **Supporting value object:** `Storage`

The Builder uses fluent methods that return `this`:

```java
public Builder gpu(String gpu) {
    this.gpu = gpu;
    return this;
}
```

Therefore client code can use method chaining:

```java
Computer gaming = new Computer.Builder(
        "Intel Core i7", 16,
        new Storage("SSD", 1000),
        "Windows 11")
        .gpu("RTX 4060")
        .enableWifi()
        .enableBluetooth()
        .monitor("27 inch Gaming Monitor")
        .usageType(UsageType.GAMING)
        .build();
```

### Default values

Optional properties have meaningful defaults:

- GPU → `Integrated Graphics`
- Wi-Fi → `true`
- Bluetooth → `false`
- Monitor → `Not included`
- Keyboard → `Not included`
- Mouse → `Not included`
- Usage type → `BASIC`

## 5. Part C — Validation challenge

Validation is performed in the Builder before the Product is created.

### Single-field validation

1. CPU must not be null or blank.
2. RAM must be greater than 0 GB.
3. Storage must be specified.
4. Operating System must not be null or blank.

### Cross-field validation

**Rule 1:** If `usageType == GAMING`, a dedicated GPU must be specified.

```text
GAMING
  ↓
Dedicated GPU required
```

**Rule 2:** If the operating system is Windows 11, RAM must be at least 8 GB.

```text
Windows 11
    ↓
RAM >= 8 GB
```

Invalid configurations throw `IllegalArgumentException` with a clear message.

### Why validation is in the Builder

The Builder has all configuration values available before the Product is created. This makes it possible to check both individual fields and dependencies between fields and prevent an invalid `Computer` object from being constructed.

## 6. Part D — Preset configurations

Three substantially different presets are implemented through `ComputerDirector`:

### BASIC

- Intel Core i3
- 8 GB RAM
- 512 GB SSD
- Windows 11
- integrated graphics
- basic usage

### GAMING

- Intel Core i7
- 16 GB RAM
- 1 TB SSD
- Windows 11
- RTX 4060
- Bluetooth and Wi-Fi
- gaming peripherals

### PROFESSIONAL

- Intel Core i9
- 32 GB RAM
- 2 TB NVMe SSD
- Windows 11
- RTX 4070
- Bluetooth and Wi-Fi
- 4K monitor and professional peripherals

The Director is justified because the same construction sequences can be named and reused as domain-level presets instead of being copied into the client.

## 7. Part E — Clean Code: Before → After

### Example 1 — Error handling in validation

**BEFORE** (a single undifferentiated check, as it would look without decomposition)

private void validate() {
if (cpu == null || cpu.isBlank()) {
throw new IllegalArgumentException("Invalid configuration.");
}
if (ramGb <= 0) {
throw new IllegalArgumentException("Invalid configuration.");
}
if (usageType == UsageType.GAMING && !hasDedicatedGpu()) {
throw new IllegalArgumentException("Invalid configuration.");
}
if (operatingSystem.equalsIgnoreCase("Windows 11") && ramGb < 8) {
throw new IllegalArgumentException("Invalid configuration.");
}
}

**AFTER** (actual implementation)

private void validate() {
validateRequiredFields();
validateGamingConstraint();
validateWindowsConstraint();
}

**Principle:** clear error handling / DRY.
**Why better:** every rule reports exactly what is wrong instead of a single generic message.
### Example 2 — Validation method

**BEFORE**

```java
private void validate() {
    if (cpu == null || cpu.isBlank()) { ... }
    if (ramGb <= 0) { ... }
    if (storage == null) { ... }
    if (operatingSystem == null || operatingSystem.isBlank()) { ... }
    if (usageType == UsageType.GAMING && !hasDedicatedGpu()) { ... }
    if (operatingSystem.equalsIgnoreCase("Windows 11") && ramGb < 8) { ... }
}
```

**AFTER**

```java
private void validate() {
    validateRequiredFields();
    validateGamingConstraint();
    validateWindowsConstraint();
}
```

**Principle:** small functions / one function — one responsibility.  
**Why better:** the main validation method communicates the high-level steps, while each helper has one focused responsibility.

### Example 3 — Boolean flag setters

**BEFORE**

```java
.setWifi(true)
.setBluetooth(true)
```

**AFTER**

```java
.enableWifi()
.enableBluetooth()
```

**Principle:** avoiding flag arguments / descriptive naming.  
**Why better:** the intent is visible directly at the call site, without making the reader interpret `true` or `false`.

Additional Clean Code practices used include clear error handling, DRY in the preset construction through the Director, and a clear separation between construction and the final Product.

## 8. Part F — Design decision

### Decision
Validation belongs to the Builder, and the Product is immutable.

### Alternative
Validation could be placed in the `Computer` constructor, or the Product could expose setters and be modified after construction.

### Reasoning
The Builder already contains the complete candidate configuration and is responsible for construction. Validating before `new Computer(this)` prevents an invalid Product from escaping the construction process. The Product fields are `final`, so a successfully built `Computer` cannot be changed after construction. This makes already-built Products independent from later Builder changes.

## 9. Part G — UML

The UML class diagram is stored in `docs/builder-uml.png` and corresponds to the submitted source code.

### Traceability table

| Builder role | Class | Responsibility |
|---|---|---|
| Product | `Computer` | Stores the final computer configuration. |
| Builder | `Computer.Builder` | Collects parameters, provides fluent methods, validates, and creates `Computer`. |
| Client | `Main` | Requests presets and creates a custom configuration. |
| Director | `ComputerDirector` | Defines reusable BASIC, GAMING, and PROFESSIONAL construction sequences. |
| Value object | `Storage` | Represents storage type and capacity. |
| Supporting type | `UsageType` | Represents BASIC, GAMING, or PROFESSIONAL usage. |

## 10. Part H — Automated testing

`ComputerBuilderTest.java` contains 10 behavior-oriented JUnit 5 tests.

| # | Scenario | Category |
|---|---|---|
| 1 | Basic configuration builds correctly | Valid construction |
| 2 | Gaming configuration builds correctly | Valid construction |
| 3 | Professional configuration builds correctly | Valid construction |
| 4 | Blank CPU is rejected | Invalid construction |
| 5 | Non-positive RAM is rejected | Invalid construction |
| 6 | Missing OS is rejected | Invalid construction |
| 7 | Windows 11 with exactly 8 GB RAM is accepted | Boundary case |
| 8 | Windows 11 with 7 GB RAM is rejected | Boundary case (negative side) |
| 9 | Minimal storage capacity (1 GB) is accepted | Boundary case |
| 10 | Gaming without a dedicated GPU is rejected | Individual constraint |
| 11 | Reusing Builder does not change an earlier Product | Builder reuse / Product independence |
The tests verify observable behavior and error messages, not merely method execution.

## 11. Sample program output

A successful run prints BASIC, GAMING, PROFESSIONAL, and one custom configuration. Exactly one successful configuration output contains the required banana marker:

```text
🍌 Successfully built custom configuration!
```

## 12. Git development history

The repository contains six meaningful development commits:

1. `add initial constructor-based domain model`
2. `implement Builder and fluent API`
3. `add reusable preset configurations`
4. `add single-field and cross-field validation`
5. `add automated behavior tests`
6. `complete UML, report, and README documentation`

The history demonstrates progression from the initial construction approach to the Builder refactoring, validation, presets, tests, and documentation.

## 13. Defense preparation

The student should be able to explain:

1. Why Builder is appropriate for this domain.
2. What the Product is.
3. What the Builder is.
4. What `build()` does.
5. Why fluent methods return `this`.
6. Which properties are required and optional.
7. What the default values are.
8. The difference between single-field and cross-field validation.
9. Why validation is performed before Product creation.
10. Why `Computer` is immutable.
11. Why the Director is used.
12. How Builder reuse works.
13. What happens when a validation rule fails.
14. How the UML maps to the source code.
15. How to add a new optional property or preset without breaking existing client code.

## 14. AI assistance and responsibility

The assignment specification permits AI tools as assistants unless the instructor gives additional instructions. The submitted code must still be understood and defended by the student. The student is responsible for explaining, modifying, debugging, and extending the submitted implementation during the defense.
