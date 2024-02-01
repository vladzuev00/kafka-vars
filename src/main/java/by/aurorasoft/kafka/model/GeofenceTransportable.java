package by.aurorasoft.kafka.model;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants
public class GeofenceTransportable {
    String name;
    String description;
    String color;
    int maxSpeed;
    long userId;
    String geometryText;
}
