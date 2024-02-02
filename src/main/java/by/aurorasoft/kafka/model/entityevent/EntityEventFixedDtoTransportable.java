package by.aurorasoft.kafka.model.entityevent;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
@Getter
@Accessors(makeFinal = true)
public abstract class EntityEventFixedDtoTransportable<ID, DTO extends AbstractDto<ID>>
        implements EntityEventTransportable<ID> {
    private final DTO dto;

    @Override
    public final ID getEntityId() {
        return dto.getId();
    }
}
