package by.aurorasoft.kafka.config.model;

public class UnitActionTransportable {

    private final long unitId;
    private final String type;
    private final String message;
    private final long timeSeconds;

    public UnitActionTransportable(long unitId, String type, String message, long timeSeconds) {
        this.unitId = unitId;
        this.type = type;
        this.message = message;
        this.timeSeconds = timeSeconds;
    }

    public long getUnitId() {
        return unitId;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public long getTimeSeconds() {
        return timeSeconds;
    }
}
