package by.aurorasoft.kafka.replication.model.replication;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD;

public final class UpdateReplication<ID, DTO extends AbstractDto<ID>> extends ReplicationWithDto<ID, DTO> {

    public UpdateReplication(final DTO dto) {
        super(dto);
    }

    @Override
    protected void execute(final AbsServiceCRUD<ID, ?, DTO, ?> service, final DTO dto) {
        service.update(dto);
    }
}
