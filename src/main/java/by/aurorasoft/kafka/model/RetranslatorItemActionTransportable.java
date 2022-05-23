package by.aurorasoft.kafka.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;

@Data
@FieldNameConstants
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public class RetranslatorItemActionTransportable {
    long id;
    String type;
    String message;
    long timeSeconds;

    public RetranslatorItemActionTransportable(long id, String type, String message, long timeSeconds) {
        this.id = id;
        this.type = type;
        this.message = message;
        this.timeSeconds = timeSeconds;
    }
}
