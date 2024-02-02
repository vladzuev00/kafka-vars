package by.aurorasoft.kafka.model.entityevent;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class RemovedEntityEventTransportable<ENTITY_ID> implements EntityEventTransportable<ENTITY_ID> {
    private final ENTITY_ID entityId;
}
