package by.aurorasoft.kafka.model.entityevent;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;

public final class NewEntityEventTransportable<ID, DTO extends AbstractDto<ID>>
        extends EntityEventFixedDtoTransportable<ID, DTO> {

    public NewEntityEventTransportable(final DTO dto) {
        super(dto);
    }
}
