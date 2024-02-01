package by.aurorasoft.kafka.model.entity.entityevent;

import by.aurorasoft.kafka.model.entity.EntityTransportable;

public abstract class UpdatedEntityEventTransportable<E extends EntityTransportable<?>>
        extends EntityEventTransportable<E> {

    public UpdatedEntityEventTransportable(final E entity) {
        super(entity);
    }
}
