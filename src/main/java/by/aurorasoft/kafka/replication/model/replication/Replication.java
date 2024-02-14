package by.aurorasoft.kafka.replication.model.replication;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD;

public interface Replication<ID, DTO extends AbstractDto<ID>> {
    ID getEntityId();
    void execute(final AbsServiceCRUD<ID, ?, DTO, ?> service);
}
