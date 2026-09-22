
public class ComputerConstructorVersion {
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

    public ComputerConstructorVersion(String cpu, int ramGb, Storage storage,
                                      String operatingSystem, String gpu,
                                      boolean wifi, boolean bluetooth,
                                      String monitor, String keyboard,
                                      String mouse, UsageType usageType) {

        this.cpu = cpu;
        this.ramGb = ramGb;
        this.storage = storage;
        this.operatingSystem = operatingSystem;
        this.gpu = gpu;
        this.wifi = wifi;
        this.bluetooth = bluetooth;
        this.monitor = monitor;
        this.keyboard = keyboard;
        this.mouse = mouse;
        this.usageType = usageType;
    }
}
