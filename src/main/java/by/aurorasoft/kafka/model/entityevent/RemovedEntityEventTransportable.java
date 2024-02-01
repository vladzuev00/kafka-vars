package by.aurorasoft.kafka.model.entityevent;

import by.aurorasoft.kafka.model.entity.EntityTransportable;

public abstract class RemovedEntityEventTransportable<ENTITY extends EntityTransportable<?>>
        extends EntityEventTransportable<ENTITY> {

    public RemovedEntityEventTransportable(final ENTITY entity) {
        super(entity);
    }

}
