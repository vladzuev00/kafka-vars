package by.aurorasoft.kafka.replication.model.replication;

import by.aurorasoft.kafka.replication.model.ReplicationProducingContext;
import by.aurorasoft.kafka.replication.model.TransportableReplication;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static by.aurorasoft.kafka.replication.model.ReplicationType.DELETE;

@RequiredArgsConstructor
@Getter
public final class DeleteReplication implements Replication {
    private final Object entityId;

    @Override
    public <DTO extends AbstractDto<?>> TransportableReplication mapToTransportable(final ReplicationProducingContext<DTO> context) {
        return TransportableReplication.builder()
                .type(DELETE)
                .entityId(entityId)
                .build();
    }
}
