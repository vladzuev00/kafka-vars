package by.aurorasoft.kafka.model.entityevent;

import by.aurorasoft.kafka.model.entity.EntityTransportable;

public abstract class NewEntityEventTransportable<ENTITY extends EntityTransportable<?>>
        extends EntityEventTransportable<ENTITY> {

    public NewEntityEventTransportable(final ENTITY entity) {
        super(entity);
    }
}
