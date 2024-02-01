package by.aurorasoft.kafka.model.entity.entityevent;

public abstract class NewEntityEventTransportable<E extends EntityEventTransportable<?>>
        extends EntityEventTransportable<E> {

    public NewEntityEventTransportable(final E entity) {
        super(entity);
    }

}
