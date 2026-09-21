public class Main {
    public static void main(String[] args) {
        ComputerDirector director = new ComputerDirector();

        System.out.println("=== BASIC COMPUTER ===");
        System.out.println(director.createBasic());

        System.out.println("\n==============================\n");

        System.out.println("=== GAMING COMPUTER ===");
        System.out.println(director.createGaming());

        System.out.println("\n==============================\n");

        System.out.println("=== PROFESSIONAL COMPUTER ===");
        System.out.println(director.createProfessional());

        System.out.println("\n==============================\n");

        Computer custom = new Computer.Builder(
                "AMD Ryzen 5", 16, new Storage("SSD", 1000), "Windows 11")
                .gpu("RTX 3060")
                .enableWifi()
                .enableBluetooth()
                .monitor("24 inch Monitor")
                .usageType(UsageType.GAMING)
                .build();

        System.out.println("=== CUSTOM COMPUTER ===");
        System.out.println(custom);
        System.out.println("\n🍌 Successfully built custom configuration!");
    }
}
