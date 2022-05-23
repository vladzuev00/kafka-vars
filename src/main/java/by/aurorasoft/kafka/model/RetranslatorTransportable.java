package by.aurorasoft.kafka.model;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

import java.util.Objects;

@Value
@FieldNameConstants
public class RetranslatorTransportable {
    long id;
    long protocolId;
    String name;
    String host;
    int port;
    boolean isActive;
    boolean deleted;
}
