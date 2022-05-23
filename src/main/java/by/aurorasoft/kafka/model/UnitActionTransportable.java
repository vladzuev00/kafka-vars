package by.aurorasoft.kafka.model;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants
public class UnitActionTransportable {
    long unitId;
    String type;
    String message;
    long timeSeconds;
}
