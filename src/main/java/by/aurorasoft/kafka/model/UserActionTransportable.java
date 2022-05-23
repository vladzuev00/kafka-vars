package by.aurorasoft.kafka.model;

import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.apache.avro.reflect.Nullable;

import java.util.Objects;

@Value
@FieldNameConstants
public class UserActionTransportable {
    @Nullable
    Long id;
    long userId;
    long timeSeconds;
    String ip;
    String method;
    String uri;
    @Nullable
    String params;
    @Nullable
    String body;
    @Nullable
    String client;
}
