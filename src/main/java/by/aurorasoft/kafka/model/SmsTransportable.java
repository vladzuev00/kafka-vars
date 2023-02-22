package by.aurorasoft.kafka.model;

import lombok.Value;

@Value
public class SmsTransportable {
    long createdTimeInNanos;
    int lifeTimeInSeconds;
    String phoneNumber;
    String text;
}
