package by.aurorasoft.kafka.model;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants
public class UnitReconnectHookTransportable {
    String imei;
    int timeoutSeconds;

    public UnitReconnectHookTransportable(String imei) {
        this.imei = imei;
        this.timeoutSeconds = 0;
    }

    public UnitReconnectHookTransportable(String imei, int timeoutSeconds) {
        this.imei = imei;
        this.timeoutSeconds = timeoutSeconds;
    }
}
