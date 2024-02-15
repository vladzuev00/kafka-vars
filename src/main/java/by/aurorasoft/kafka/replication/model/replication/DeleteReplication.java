package by.aurorasoft.kafka.replication.model.replication;

import by.aurorasoft.kafka.replication.model.TransportableReplication.ReplicationType;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD;

import static by.aurorasoft.kafka.replication.model.TransportableReplication.ReplicationType.DELETE;

public final class DeleteReplication<ID, DTO extends AbstractDto<ID>> extends Replication<ID, DTO> {

    public DeleteReplication(final DTO dto) {
        super(dto);
    }

    @Override
    public void execute(final AbsServiceCRUD<ID, ?, DTO, ?> service, final DTO dto) {
        service.delete(dto.getId());
    }

    @Override
    protected ReplicationType getType() {
        return DELETE;
    }
}
