package by.aurorasoft.kafka.model.entityevent;

import by.aurorasoft.kafka.model.entity.EntityTransportable;

public abstract class UpdatedEntityEventTransportable<ENTITY extends EntityTransportable<?>>
        extends EntityEventTransportable<ENTITY> {

    public UpdatedEntityEventTransportable(final ENTITY entity) {
        super(entity);
    }
}
