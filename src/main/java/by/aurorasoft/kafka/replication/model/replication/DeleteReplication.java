package by.aurorasoft.kafka.replication.model.replication;

import by.aurorasoft.kafka.replication.model.TransportableReplication;
import by.aurorasoft.kafka.replication.producer.ReplicationProducingContext;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static by.aurorasoft.kafka.replication.model.TransportableReplication.ReplicationType.DELETE;

@RequiredArgsConstructor
public final class DeleteReplication<ID, DTO extends AbstractDto<ID>> implements Replication<ID, DTO> {

    @Getter
    private final ID entityId;

    @Override
    public TransportableReplication createTransportable(final ReplicationProducingContext<ID, DTO> context) {
        return TransportableReplication.builder()
                .type(DELETE)
                .entityId(entityId)
                .build();
    }

    @Override
    public void execute(final AbsServiceCRUD<ID, ?, DTO, ?> service) {
        service.delete(entityId);
    }
}
