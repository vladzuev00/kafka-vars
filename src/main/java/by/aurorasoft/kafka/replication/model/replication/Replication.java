package by.aurorasoft.kafka.replication.model.replication;

import by.aurorasoft.kafka.replication.model.ReplicationType;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public abstract class Replication<ID, DTO extends AbstractDto<ID>> {
    private final DTO dto;

    public final ID getEntityId() {
        return dto.getId();
    }

    public abstract ReplicationType getType();

    public final void execute(final AbsServiceCRUD<ID, ?, DTO, ?> service) {
        execute(service, dto);
    }

    protected abstract void execute(final AbsServiceCRUD<ID, ?, DTO, ?> service, final DTO dto);
}
