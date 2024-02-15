package by.aurorasoft.kafka.replication.model.replication;

import by.aurorasoft.kafka.replication.model.TransportableReplication.ReplicationType;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD;

import static by.aurorasoft.kafka.replication.model.TransportableReplication.ReplicationType.SAVE;

public final class SaveReplication<ID, DTO extends AbstractDto<ID>> extends Replication<ID, DTO> {

    public SaveReplication(final DTO dto) {
        super(dto);
    }

    @Override
    public void execute(final AbsServiceCRUD<ID, ?, DTO, ?> service, final DTO dto) {
        service.save(dto);
    }

    @Override
    protected ReplicationType getType() {
        return SAVE;
    }
}
