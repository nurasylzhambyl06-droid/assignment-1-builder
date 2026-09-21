public final class Computer {
    private final String cpu;
    private final int ramGb;
    private final Storage storage;
    private final String operatingSystem;
    private final String gpu;
    private final boolean wifi;
    private final boolean bluetooth;
    private final String monitor;
    private final String keyboard;
    private final String mouse;
    private final UsageType usageType;

    private Computer(Builder builder) {
        this.cpu = builder.cpu;
        this.ramGb = builder.ramGb;
        this.storage = builder.storage;
        this.operatingSystem = builder.operatingSystem;
        this.gpu = builder.gpu;
        this.wifi = builder.wifi;
        this.bluetooth = builder.bluetooth;
        this.monitor = builder.monitor;
        this.keyboard = builder.keyboard;
        this.mouse = builder.mouse;
        this.usageType = builder.usageType;
    }

    public static class Builder {
        private final String cpu;
        private final int ramGb;
        private final Storage storage;
        private final String operatingSystem;

        private String gpu = "Integrated Graphics";
        private boolean wifi = true;
        private boolean bluetooth = false;
        private String monitor = "Not included";
        private String keyboard = "Not included";
        private String mouse = "Not included";
        private UsageType usageType = UsageType.BASIC;

        public Builder(String cpu, int ramGb, Storage storage, String operatingSystem) {
            this.cpu = cpu;
            this.ramGb = ramGb;
            this.storage = storage;
            this.operatingSystem = operatingSystem;
        }

        public Builder gpu(String gpu) {
            this.gpu = gpu;
            return this;
        }

        public Builder enableWifi() {
            this.wifi = true;
            return this;
        }

        public Builder disableWifi() {
            this.wifi = false;
            return this;
        }

        public Builder enableBluetooth() {
            this.bluetooth = true;
            return this;
        }

        public Builder monitor(String monitor) {
            this.monitor = monitor;
            return this;
        }

        public Builder keyboard(String keyboard) {
            this.keyboard = keyboard;
            return this;
        }

        public Builder mouse(String mouse) {
            this.mouse = mouse;
            return this;
        }

        public Builder usageType(UsageType usageType) {
            this.usageType = usageType;
            return this;
        }

        public Computer build() {
            validate();
            return new Computer(this);
        }

        private void validate() {
            validateRequiredFields();
            validateGamingConstraint();
            validateWindowsConstraint();
        }

        private void validateRequiredFields() {
            if (cpu == null || cpu.isBlank()) {
                throw new IllegalArgumentException("CPU must be specified.");
            }
            if (ramGb <= 0) {
                throw new IllegalArgumentException("RAM must be greater than 0 GB.");
            }
            if (storage == null) {
                throw new IllegalArgumentException("Storage must be specified.");
            }
            if (operatingSystem == null || operatingSystem.isBlank()) {
                throw new IllegalArgumentException("Operating system must be specified.");
            }
        }

        private void validateGamingConstraint() {
            if (usageType == UsageType.GAMING && !hasDedicatedGpu()) {
                throw new IllegalArgumentException(
                        "Gaming computers require a dedicated GPU."
                );
            }
        }

        private void validateWindowsConstraint() {
            if (operatingSystem.equalsIgnoreCase("Windows 11") && ramGb < 8) {
                throw new IllegalArgumentException(
                        "Windows 11 requires at least 8 GB of RAM."
                );
            }
        }

        private boolean hasDedicatedGpu() {
            return gpu != null
                    && !gpu.isBlank()
                    && !gpu.equalsIgnoreCase("Integrated Graphics");
        }
    }

    public String getCpu() { return cpu; }
    public int getRamGb() { return ramGb; }
    public Storage getStorage() { return storage; }
    public String getOperatingSystem() { return operatingSystem; }
    public String getGpu() { return gpu; }
    public boolean isWifi() { return wifi; }
    public boolean isBluetooth() { return bluetooth; }
    public String getMonitor() { return monitor; }
    public String getKeyboard() { return keyboard; }
    public String getMouse() { return mouse; }
    public UsageType getUsageType() { return usageType; }

    @Override
    public String toString() {
        return "Computer Configuration\n" +
                "CPU: " + cpu + "\n" +
                "RAM: " + ramGb + " GB\n" +
                "Storage: " + storage + "\n" +
                "Operating System: " + operatingSystem + "\n" +
                "GPU: " + gpu + "\n" +
                "Wi-Fi: " + wifi + "\n" +
                "Bluetooth: " + bluetooth + "\n" +
                "Monitor: " + monitor + "\n" +
                "Keyboard: " + keyboard + "\n" +
                "Mouse: " + mouse + "\n" +
                "Usage: " + usageType;
    }
}
