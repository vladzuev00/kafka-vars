package by.aurorasoft.kafka.model.entity.entityevent;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
@Getter
@Accessors(makeFinal = true)
public abstract class EntityEventTransportable<B> {
    private final B body;
}
