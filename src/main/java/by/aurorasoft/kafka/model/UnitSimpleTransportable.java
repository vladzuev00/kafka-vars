package by.aurorasoft.kafka.model;

import java.util.Objects;

public class UnitSimpleTransportable {
    private final Long id;
    private final String deviceImei;
    private final boolean deleted;

    public UnitSimpleTransportable(Long id, String deviceImei, boolean deleted) {
        this.id = id;
        this.deviceImei = deviceImei;
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public String getDeviceImei() {
        return deviceImei;
    }

    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UnitSimpleTransportable)) return false;
        UnitSimpleTransportable that = (UnitSimpleTransportable) o;
        return isDeleted() == that.isDeleted() && Objects.equals(getId(), that.getId()) && Objects.equals(getDeviceImei(), that.getDeviceImei());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDeviceImei(), isDeleted());
    }
}
