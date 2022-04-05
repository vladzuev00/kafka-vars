package by.aurorasoft.kafka.model;

import java.util.Objects;

public class SubscriberItemActionTransportable {
    private final long id;
    private final String type;
    private final String message;
    private final long timeSeconds;

    public SubscriberItemActionTransportable(long id, String type, String message, long timeSeconds) {
        this.id = id;
        this.type = type;
        this.message = message;
        this.timeSeconds = timeSeconds;
    }

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public long getTimeSeconds() {
        return timeSeconds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscriberItemActionTransportable that = (SubscriberItemActionTransportable) o;
        return id == that.id && timeSeconds == that.timeSeconds && type.equals(that.type) && message.equals(that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, message, timeSeconds);
    }

    @Override
    public String toString() {
        return "SubscriberItemActionTransportable{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", message='" + message + '\'' +
                ", timeSeconds=" + timeSeconds +
                '}';
    }
}
