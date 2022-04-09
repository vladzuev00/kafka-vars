package by.aurorasoft.kafka.model;

import java.util.Objects;

public class RetranslatorTransportable {
    private final long id;
    private final long protocolId;
    private final String name;
    private final String host;
    private final int port;
    private final boolean isActive;
    private final boolean deleted;

    public RetranslatorTransportable(long id, long protocolId, String name, String host, int port, boolean isActive, boolean deleted) {
        this.id = id;
        this.protocolId = protocolId;
        this.name = name;
        this.host = host;
        this.port = port;
        this.isActive = isActive;
        this.deleted = deleted;
    }

    public long getId() {
        return id;
    }

    public long getProtocolId() {
        return protocolId;
    }

    public String getName() {
        return name;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RetranslatorTransportable that = (RetranslatorTransportable) o;
        return id == that.id && protocolId == that.protocolId && port == that.port && isActive == that.isActive && deleted == that.deleted && name.equals(that.name) && host.equals(that.host);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, protocolId, name, host, port, isActive, deleted);
    }

    @Override
    public String toString() {
        return "SubscriberServerTransportable{" +
                "id=" + id +
                ", protocolId=" + protocolId +
                ", name='" + name + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", isActive=" + isActive +
                ", deleted=" + deleted +
                '}';
    }
}
