package by.aurorasoft.kafka.model.entityevent;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class UpdatedEntityEventTransportable<ENTITY_ID, DTO extends AbstractDto<ENTITY_ID>>
        implements EntityEventTransportable<ENTITY_ID> {
    private final DTO dto;

    @Override
    public ENTITY_ID getEntityId() {
        return dto.getId();
    }
}
