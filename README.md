# Assignment 1 — Builder Pattern: Computer Configuration

**Course:** Software Design Patterns  
**Pattern:** Builder  
**Language:** Java (JDK 17+)  
**Domain:** Computer Configuration  
**Individual constraint:** Gaming computers require a dedicated GPU; Windows 11 requires at least 8 GB RAM.  
**Presets:** BASIC, GAMING, PROFESSIONAL

## Project structure

```text
assignment-1-builder/
├── src/
│   ├── main/java/
│   │   ├── Computer.java
│   │   ├── ComputerDirector.java
│   │   ├── Main.java
│   │   ├── Storage.java
│   │   └── UsageType.java
│   └── test/java/
│       └── ComputerBuilderTest.java
├── legacy/
│   └── ComputerConstructorVersion.java
├── docs/
│   ├── builder-uml.png
│   └── builder-uml.dot
├── pom.xml
├── README.md
└── report.md
```

## Run

Open the project in IntelliJ IDEA as a Maven project. Use JDK 17 or newer.

From a terminal:

```bash
mvn clean test
```

To run the application from IntelliJ, run `Main.java`.

## Builder usage example

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

## Design summary

`Computer` is the Product. `Computer.Builder` is the Builder. `ComputerDirector` defines reusable preset construction sequences. `Main` is the Client. `Storage` is a value object used by the Product.
