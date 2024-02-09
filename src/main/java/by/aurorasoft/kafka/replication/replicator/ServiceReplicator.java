package by.aurorasoft.kafka.replication.replicator;

import by.aurorasoft.kafka.replication.mapper.TransportableReplicatedDtoFactory;
import by.aurorasoft.kafka.replication.model.Replication;
import by.aurorasoft.kafka.replication.model.TransportableDto;
import by.aurorasoft.kafka.replication.producer.KafkaProducerReplication;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Function;

import static by.aurorasoft.kafka.replication.model.Replication.*;

@RequiredArgsConstructor
public final class ServiceReplicator {

    @Getter
    private final Class<?> replicatedService;

    private final KafkaProducerReplication<?> producer;
    private final TransportableReplicatedDtoFactory<?> transportableDtoFactory;

    public void replicateSave(final AbstractDto<?> savedDto) {
        sendReplicationWithDto(savedDto, Replication::createSaveReplication);
    }

    public void replicateSaveAll(final List<AbstractDto<?>> savedDtos) {
        savedDtos.forEach(this::replicateSave);
    }

    public void replicateUpdate(final AbstractDto<?> updatedDto) {
        sendReplicationWithDto(updatedDto, Replication::createUpdateReplication);
    }

    public void replicateDelete(final Object entityId) {
        producer.send(createDeleteReplication(entityId));
    }

    private void sendReplicationWithDto(final AbstractDto<?> dto,
                                        final Function<TransportableDto, Replication> replicationFactory) {
        final TransportableDto transportableDto = transportableDtoFactory.create(dto);
        final Replication replication = replicationFactory.apply(transportableDto);
        producer.send(replication);
    }
}
