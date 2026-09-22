public final class ComputerDirector {
    public Computer createBasic() {
        return new Computer.Builder(
                "Intel Core i3", 8, new Storage("SSD", 512), "Windows 11")
                .usageType(UsageType.BASIC)
                .build();
    }

    public Computer createGaming() {
        return new Computer.Builder(
                "Intel Core i7", 16, new Storage("SSD", 1000), "Windows 11")
                .gpu("RTX 4060")
                .enableWifi()
                .enableBluetooth()
                .monitor("27 inch Gaming Monitor")
                .keyboard("Mechanical Keyboard")
                .mouse("Gaming Mouse")
                .usageType(UsageType.GAMING)
                .build();
    }

    public Computer createProfessional() {
        return new Computer.Builder(
                "Intel Core i9", 32, new Storage("NVMe SSD", 2000), "Windows 11")
                .gpu("RTX 4070")
                .enableWifi()
                .enableBluetooth()
                .monitor("32 inch 4K Monitor")
                .keyboard("Professional Keyboard")
                .mouse("Precision Mouse")
                .usageType(UsageType.PROFESSIONAL)
                .build();
    }
}
