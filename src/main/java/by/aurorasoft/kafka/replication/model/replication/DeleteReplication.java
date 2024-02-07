package by.aurorasoft.kafka.replication.model.replication;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class DeleteReplication<ENTITY_ID, DTO extends AbstractDto<ENTITY_ID>>
        implements Replication<ENTITY_ID, DTO> {
    private final ENTITY_ID id;

    @Override
    public ENTITY_ID getEntityId() {
        return id;
    }

    @Override
    public void execute(final AbsServiceCRUD<ENTITY_ID, ?, DTO, ?> service) {
        service.delete(id);
    }
}
