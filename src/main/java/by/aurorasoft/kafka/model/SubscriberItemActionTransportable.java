package by.aurorasoft.kafka.model;

import java.util.Objects;

public class SubscriberItemActionTransportable {
    protected final long id;
    protected final String type;
    protected final String message;
    protected final long time;

    public SubscriberItemActionTransportable(long id, String type, String message, long time) {
        this.id = id;
        this.type = type;
        this.message = message;
        this.time = time;
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

    public long getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscriberItemActionTransportable that = (SubscriberItemActionTransportable) o;
        return id == that.id && time == that.time && type.equals(that.type) && message.equals(that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, message, time);
    }

    @Override
    public String toString() {
        return "SubscriberItemActionTransportable{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", message='" + message + '\'' +
                ", timeSeconds=" + time +
                '}';
    }
}
