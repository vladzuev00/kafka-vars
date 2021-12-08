package by.aurorasoft.kafka.model;

import org.apache.avro.reflect.Nullable;

import java.util.Objects;

public class UserActionTransportable {
    private final Long id;
    private final long userId;
    private final long timeSeconds;
    private final String ip;
    private final String method;
    private final String uri;
    @Nullable
    private final String params;
    @Nullable
    private final String body;
    @Nullable
    private final String client;

    public UserActionTransportable(Long id, long userId, long timeSeconds, String ip, String method,
                                   String uri, String params, String body, String client) {
        this.id = id;
        this.userId = userId;
        this.timeSeconds = timeSeconds;
        this.ip = ip;
        this.method = method;
        this.uri = uri;
        this.params = params;
        this.body = body;
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public long getTimeSeconds() {
        return timeSeconds;
    }

    public String getIp() {
        return ip;
    }

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public String getParams() {
        return params;
    }

    public String getBody() {
        return body;
    }

    public String getClient() {
        return client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserActionTransportable)) return false;
        UserActionTransportable that = (UserActionTransportable) o;
        return getUserId() == that.getUserId() && getTimeSeconds() == that.getTimeSeconds() && Objects.equals(getId(), that.getId()) && Objects.equals(getIp(), that.getIp()) && Objects.equals(getMethod(), that.getMethod()) && Objects.equals(getUri(), that.getUri()) && Objects.equals(getParams(), that.getParams()) && Objects.equals(getBody(), that.getBody()) && Objects.equals(getClient(), that.getClient());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserId(), getTimeSeconds(), getIp(), getMethod(), getUri(), getParams(), getBody(), getClient());
    }
}
