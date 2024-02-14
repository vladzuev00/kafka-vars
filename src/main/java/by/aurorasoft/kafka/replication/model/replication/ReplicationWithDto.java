package by.aurorasoft.kafka.replication.model.replication;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class ReplicationWithDto<ID, DTO extends AbstractDto<ID>> implements Replication<ID, DTO> {

    @Getter
    private final DTO dto;

    @Override
    public final ID getEntityId() {
        return dto.getId();
    }

    @Override
    public final void execute(final AbsServiceCRUD<ID, ?, DTO, ?> service) {
        execute(service, dto);
    }

    protected abstract void execute(final AbsServiceCRUD<ID, ?, DTO, ?> service, final DTO dto);
}
