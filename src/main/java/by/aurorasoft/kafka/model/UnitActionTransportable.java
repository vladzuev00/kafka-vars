package by.aurorasoft.kafka.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UnitActionTransportable)) return false;
        UnitActionTransportable that = (UnitActionTransportable) o;
        return getUnitId() == that.getUnitId() && getTimeSeconds() == that.getTimeSeconds() && Objects.equals(getType(), that.getType()) && Objects.equals(getMessage(), that.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUnitId(), getType(), getMessage(), getTimeSeconds());
    }
}
