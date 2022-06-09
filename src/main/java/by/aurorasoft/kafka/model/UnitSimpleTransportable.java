package by.aurorasoft.kafka.model;

import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.apache.avro.reflect.Nullable;

import java.util.Objects;

@Value
@FieldNameConstants
public class UnitSimpleTransportable {
    @Nullable
    String id;
    String deviceImei;
    boolean deleted;

    public UnitSimpleTransportable(String id, String deviceImei, boolean deleted) {
        this.id = id;
        this.deviceImei = deviceImei;
        this.deleted = deleted;
    }
}
