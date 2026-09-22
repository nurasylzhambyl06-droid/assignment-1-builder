import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComputerBuilderTest {

    @Test
    void buildsBasicConfiguration() {
        Computer computer = new Computer.Builder(
                "Intel Core i3", 8, new Storage("SSD", 512), "Windows 11")
                .build();

        assertEquals("Intel Core i3", computer.getCpu());
        assertEquals(8, computer.getRamGb());
        assertEquals(UsageType.BASIC, computer.getUsageType());
    }

    @Test
    void buildsGamingConfiguration() {
        Computer computer = new Computer.Builder(
                "Intel Core i7", 16, new Storage("SSD", 1000), "Windows 11")
                .gpu("RTX 4060")
                .usageType(UsageType.GAMING)
                .build();

        assertEquals("RTX 4060", computer.getGpu());
        assertEquals(UsageType.GAMING, computer.getUsageType());
    }

    @Test
    void buildsProfessionalConfiguration() {
        Computer computer = new Computer.Builder(
                "Intel Core i9", 32, new Storage("NVMe SSD", 2000), "Windows 11")
                .gpu("RTX 4070")
                .enableBluetooth()
                .usageType(UsageType.PROFESSIONAL)
                .build();

        assertEquals(32, computer.getRamGb());
        assertTrue(computer.isBluetooth());
        assertEquals("NVMe SSD", computer.getStorage().getType());
    }

    @Test
    void rejectsBlankCpu() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Computer.Builder("   ", 8, new Storage("SSD", 512), "Windows 11").build());

        assertEquals("CPU must be specified.", exception.getMessage());
    }

    @Test
    void rejectsNonPositiveRam() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Computer.Builder("Intel Core i5", 0, new Storage("SSD", 512), "Ubuntu").build());

        assertEquals("RAM must be greater than 0 GB.", exception.getMessage());
    }

    @Test
    void rejectsMissingOperatingSystem() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Computer.Builder("Intel Core i5", 16, new Storage("SSD", 512), "").build());

        assertEquals("Operating system must be specified.", exception.getMessage());
    }

    @Test
    void acceptsWindows11AtExactlyEightGbRam() {
        Computer computer = new Computer.Builder(
                "Intel Core i5", 8, new Storage("SSD", 512), "Windows 11")
                .build();

        assertEquals(8, computer.getRamGb());
    }

    @Test
    void acceptsMinimalStorageCapacityBoundary() {
        Computer computer = new Computer.Builder(
                "Intel Core i5", 16, new Storage("SSD", 1), "Ubuntu")
                .build();

        assertEquals(1, computer.getStorage().getCapacityGb());
    }

    @Test
    void gamingRequiresDedicatedGpu() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Computer.Builder("Intel Core i5", 16, new Storage("SSD", 1000), "Windows 11")
                        .usageType(UsageType.GAMING)
                        .build());

        assertEquals("Gaming computers require a dedicated GPU.", exception.getMessage());
    }

    @Test
    void reusingBuilderDoesNotChangePreviouslyBuiltProduct() {
        Computer.Builder builder = new Computer.Builder(
                "Intel Core i7", 16, new Storage("SSD", 1000), "Windows 11");

        Computer first = builder.gpu("RTX 4060").usageType(UsageType.GAMING).build();
        Computer second = builder.gpu("RTX 4070").usageType(UsageType.PROFESSIONAL).build();

        assertEquals("RTX 4060", first.getGpu());
        assertEquals(UsageType.GAMING, first.getUsageType());
        assertEquals("RTX 4070", second.getGpu());
        assertEquals(UsageType.PROFESSIONAL, second.getUsageType());
    }

    @Test
    void rejectsWindows11WithLessThanEightGbRam() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Computer.Builder("Intel Core i5", 7, new Storage("SSD", 512), "Windows 11").build());

        assertEquals("Windows 11 requires at least 8 GB of RAM.", exception.getMessage());
    }
}
