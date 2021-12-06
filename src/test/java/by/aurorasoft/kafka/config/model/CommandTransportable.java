package by.aurorasoft.kafka.config.model;

import org.apache.avro.reflect.Nullable;

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
}
