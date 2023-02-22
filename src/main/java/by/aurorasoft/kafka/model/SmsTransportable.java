package by.aurorasoft.kafka.model;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants
public class SmsTransportable {
    long createdTimeMillis;
    int lifeTimeSeconds;
    String phoneNumber;
    String text;
}
