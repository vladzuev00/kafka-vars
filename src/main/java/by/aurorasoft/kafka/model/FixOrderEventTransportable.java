package by.aurorasoft.kafka.model;

import lombok.Value;

import java.time.Instant;

@Value
public class FixOrderEventTransportable {
    long unitId;
    Instant time;
    /** PUT, REMOVE*/
    String action;
}
