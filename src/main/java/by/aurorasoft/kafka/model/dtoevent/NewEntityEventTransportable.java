package by.aurorasoft.kafka.model.dtoevent;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;

public abstract class NewEntityEventTransportable<DTO extends AbstractDto<?>> extends EntityEventTransportable<DTO> {

    public NewEntityEventTransportable(final DTO dto) {
        super(dto);
    }
}
