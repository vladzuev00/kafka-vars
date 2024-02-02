package by.aurorasoft.kafka.model.entityevent.fixeddto;

import by.aurorasoft.kafka.model.entityevent.ReplicatedEntityEvent;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
@Accessors(makeFinal = true)
public abstract class ReplicatedEntityEventFixingDto<DTO extends AbstractDto<UUID>> implements ReplicatedEntityEvent {
    private final DTO dto;

    @Override
    public final UUID getEntityId() {
        return dto.getId();
    }
}
