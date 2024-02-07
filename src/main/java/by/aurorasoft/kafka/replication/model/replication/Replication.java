package by.aurorasoft.kafka.replication.model.replication;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD;

public interface Replication<ENTITY_ID, DTO extends AbstractDto<ENTITY_ID>> {
    ENTITY_ID getEntityId();
    void execute(final AbsServiceCRUD<ENTITY_ID, ?, DTO, ?> service);
}
