package by.aurorasoft.kafka.model.entity.entityevent;

public abstract class RemovedEntityEventTransportable<ID> extends EntityEventTransportable<ID> {

    public RemovedEntityEventTransportable(final ID id) {
        super(id);
    }

}
