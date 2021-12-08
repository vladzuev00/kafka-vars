package by.aurorasoft.kafka.model;

import org.apache.avro.reflect.Nullable;

import java.util.Objects;

public class CommandTransportable {
    @Nullable
    private final Long id;
    private final long unitId;
    private final String text;
    private final String commandType;
    private final String providerType;
    /**
     * 0 if null
     */
    private final long actualEpoch;
    private final long createdEpoch;
    private final int lifeTimeout;

    public CommandTransportable(Long id, long unitId, String text, String commandType,
                                String providerType, long actualEpoch, long createdEpoch,
                                int lifeTimeout) {
        this.id = id;
        this.unitId = unitId;
        this.text = text;
        this.commandType = commandType;
        this.providerType = providerType;
        this.actualEpoch = actualEpoch;
        this.createdEpoch = createdEpoch;
        this.lifeTimeout = lifeTimeout;
    }

    public Long getId() {
        return id;
    }

    public long getUnitId() {
        return unitId;
    }

    public String getText() {
        return text;
    }

    public String getCommandType() {
        return commandType;
    }

    public String getProviderType() {
        return providerType;
    }

    public long getActualEpoch() {
        return actualEpoch;
    }

    public long getCreatedEpoch() {
        return createdEpoch;
    }

    public int getLifeTimeout() {
        return lifeTimeout;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandTransportable)) return false;
        CommandTransportable that = (CommandTransportable) o;
        return getUnitId() == that.getUnitId() && getActualEpoch() == that.getActualEpoch() && getCreatedEpoch() == that.getCreatedEpoch() && getLifeTimeout() == that.getLifeTimeout() && Objects.equals(getId(), that.getId()) && Objects.equals(getText(), that.getText()) && Objects.equals(getCommandType(), that.getCommandType()) && Objects.equals(getProviderType(), that.getProviderType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUnitId(), getText(), getCommandType(), getProviderType(), getActualEpoch(), getCreatedEpoch(), getLifeTimeout());
    }
}
