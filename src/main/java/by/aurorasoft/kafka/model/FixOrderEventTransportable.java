package by.aurorasoft.kafka.model;

import java.time.Instant;

public class FixOrderEventTransportable {
    long unitId;
    Instant time;
    /** PUT, REMOVE*/
    String action;
}
