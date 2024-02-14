package by.aurorasoft.kafka.replication.model.replication;

import by.aurorasoft.kafka.replication.model.TransportableReplication;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD;

import static by.aurorasoft.kafka.replication.model.TransportableReplication.ReplicationType.SAVE;

public final class SaveReplication<ID, DTO extends AbstractDto<ID>> extends ReplicationWithDto<ID, DTO> {

    public SaveReplication(final DTO dto) {
        super(dto);
    }

    @Override
    protected TransportableReplication createTransportable(final String dtoJsonView) {
        return TransportableReplication.builder()
                .type(SAVE)
                .dtoJsonView(dtoJsonView)
                .build();
    }

    @Override
    protected void execute(final AbsServiceCRUD<ID, ?, DTO, ?> service, final DTO dto) {
        service.save(dto);
    }
}
