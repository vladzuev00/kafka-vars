package by.aurorasoft.kafka.replication.model.replication;

import by.aurorasoft.kafka.replication.model.TransportableReplication;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD;

import static by.aurorasoft.kafka.replication.model.TransportableReplication.ReplicationType.UPDATE;

public final class UpdateReplication<ID, DTO extends AbstractDto<ID>> extends ReplicationWithDto<ID, DTO> {

    public UpdateReplication(final DTO dto) {
        super(dto);
    }

    @Override
    protected TransportableReplication createTransportable(final String dtoJsonView) {
        return TransportableReplication.builder()
                .type(UPDATE)
                .dtoJsonView(dtoJsonView)
                .build();
    }

    @Override
    protected void execute(final AbsServiceCRUD<ID, ?, DTO, ?> service, final DTO dto) {
        service.update(dto);
    }
}
