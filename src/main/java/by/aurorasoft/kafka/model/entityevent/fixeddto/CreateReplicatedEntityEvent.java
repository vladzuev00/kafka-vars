package by.aurorasoft.kafka.model.entityevent.fixeddto;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;

import java.util.UUID;

public final class CreateReplicatedEntityEvent<DTO extends AbstractDto<UUID>> extends ReplicatedEntityEventFixingDto<DTO> {

    public CreateReplicatedEntityEvent(final DTO dto) {
        super(dto);
    }
}
