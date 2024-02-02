package by.aurorasoft.kafka.model.dtoevent;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
@Getter
@Accessors(makeFinal = true)
public abstract class EntityEventTransportable<DTO extends AbstractDto<?>> {
    private final DTO dto;
}
