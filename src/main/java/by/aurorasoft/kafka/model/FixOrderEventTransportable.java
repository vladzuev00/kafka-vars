package by.aurorasoft.kafka.model;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

import java.time.Instant;

@Value
@FieldNameConstants
public class FixOrderEventTransportable {
    long unitId;
    Instant time;
    /** PUT, REMOVE*/
    String action;
}
