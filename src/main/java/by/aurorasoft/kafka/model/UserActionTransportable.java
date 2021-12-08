package by.aurorasoft.kafka.model;

import org.apache.avro.reflect.Nullable;

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
}
