package by.aurorasoft.kafka.model.entityevent;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;

public final class UpdatedEntityEventTransportable<ID, DTO extends AbstractDto<ID>>
        extends EntityEventFixedDtoTransportable<ID, DTO> {

    public UpdatedEntityEventTransportable(final DTO dto) {
        super(dto);
    }
}
