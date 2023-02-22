package by.aurorasoft.kafka.model;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants
public class SmsTransportable {
    long createdTimeSeconds;
    int createdTimeNanos;
    int lifeTimeSeconds;
    String phoneNumber;
    String text;
}
