package by.aurorasoft.kafka.model.dtoevent;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;

public abstract class UpdatedEntityEventTransportable<DTO extends AbstractDto<?>>
        extends EntityEventTransportable<DTO> {

    public UpdatedEntityEventTransportable(final DTO dto) {
        super(dto);
    }
}
