package by.aurorasoft.kafka.model;

import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.apache.avro.reflect.Nullable;

import java.util.Objects;

@Value
@FieldNameConstants
public class CommandTransportable {
    @Nullable
    Long id;
    long unitId;
    String text;
    String commandType;
    String providerType;
    /**
     * 0 if null
     */
    long actualEpoch;
    long createdEpoch;
    int lifeTimeout;
}
