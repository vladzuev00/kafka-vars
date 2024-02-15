package by.aurorasoft.kafka.replication.model.replication;

import by.aurorasoft.kafka.replication.model.TransportableReplication.ReplicationType;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD;

import static by.aurorasoft.kafka.replication.model.TransportableReplication.ReplicationType.UPDATE;

public final class UpdateReplication<ID, DTO extends AbstractDto<ID>> extends Replication<ID, DTO> {

    public UpdateReplication(final DTO dto) {
        super(dto);
    }

    @Override
    public void execute(final AbsServiceCRUD<ID, ?, DTO, ?> service, final DTO dto) {
        service.update(dto);
    }

    @Override
    protected ReplicationType getType() {
        return UPDATE;
    }
}
