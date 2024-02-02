package by.aurorasoft.kafka.model.dtoevent;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;

public abstract class RemovedEntityEventTransportable<DTO extends AbstractDto<?>>
        extends EntityEventTransportable<DTO> {

    public RemovedEntityEventTransportable(final DTO dto) {
        super(dto);
    }

}
