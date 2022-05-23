package by.aurorasoft.kafka.model;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

import java.util.Objects;

@Value
@FieldNameConstants
public class UnitReconnectHookTransportable {

    String imei;
    int timeoutSeconds;

    public UnitReconnectHookTransportable(String imei) {
        this.imei = imei;
        this.timeoutSeconds = 0;
    }
}
