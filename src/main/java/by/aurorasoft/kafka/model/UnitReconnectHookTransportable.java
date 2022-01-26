package by.aurorasoft.kafka.model;

import java.util.Objects;

public class UnitReconnectHookTransportable {

    private final String imei;
    private final int timeoutSeconds;

    public UnitReconnectHookTransportable(String imei, int timeoutSeconds) {
        this.imei = imei;
        this.timeoutSeconds = timeoutSeconds;
    }

    public UnitReconnectHookTransportable(String imei) {
        this.imei = imei;
        this.timeoutSeconds = 0;
    }

    public String getImei() {
        return imei;
    }

    public int getTimeoutSeconds() {
        return timeoutSeconds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnitReconnectHookTransportable that = (UnitReconnectHookTransportable) o;
        return timeoutSeconds == that.timeoutSeconds && imei.equals(that.imei);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imei, timeoutSeconds);
    }

    @Override
    public String toString() {
        return "UnitReconnectHookTransportable{" +
                "imei='" + imei + '\'' +
                ", timeoutSeconds=" + timeoutSeconds +
                '}';
    }
}
