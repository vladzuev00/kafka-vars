package by.aurorasoft.kafka.model;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants
public class FixOrderEventTransportable {
    long unitId;
    long timeSeconds;
    /** PUT, REMOVE*/
    String action;
}
