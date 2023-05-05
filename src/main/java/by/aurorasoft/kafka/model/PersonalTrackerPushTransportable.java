package by.aurorasoft.kafka.model;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants
public class PersonalTrackerPushTransportable {
    String title;
    String body;
    String token;
    long unitId;
}
