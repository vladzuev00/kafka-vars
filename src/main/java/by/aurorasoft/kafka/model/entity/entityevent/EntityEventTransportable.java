package by.aurorasoft.kafka.model.entity.entityevent;

import by.aurorasoft.kafka.model.entity.EntityTransportable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
@Getter
@Accessors(makeFinal = true)
public abstract class EntityEventTransportable<ENTITY_ID, ENTITY extends EntityTransportable<ENTITY_ID>> {
    private final ENTITY entity;
}
