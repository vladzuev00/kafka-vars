package by.aurorasoft.kafka.model.entityevent;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class RemovedEntityEventTransportable<ID> implements EntityEventTransportable<ID> {
    private final ID entityId;
}
