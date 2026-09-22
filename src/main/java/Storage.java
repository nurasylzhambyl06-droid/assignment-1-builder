public final class Storage {
    private final String type;
    private final int capacityGb;

    public Storage(String type, int capacityGb) {
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Storage type must be specified.");
        }
        if (capacityGb <= 0) {
            throw new IllegalArgumentException("Storage capacity must be greater than 0 GB.");
        }
        this.type = type;
        this.capacityGb = capacityGb;
    }

    public String getType() {
        return type;
    }

    public int getCapacityGb() {
        return capacityGb;
    }

    @Override
    public String toString() {
        return type + " " + capacityGb + "GB";
    }
}
