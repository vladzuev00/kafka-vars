package by.aurorasoft.kafka.replication.holder;

import by.aurorasoft.kafka.replication.annotation.ReplicatedService;
import by.aurorasoft.kafka.replication.producer.KafkaProducerReplication;
import by.nhorushko.crudgeneric.v2.service.AbsServiceRUD;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.LongSerializer;

import java.util.Map;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
@ReplicatedService(topicName = "", keySerializer = LongSerializer.class)
public final class KafkaProducerReplicationHolder {
    private final Map<AbsServiceRUD<?, ?, ?, ?, ?>, KafkaProducerReplication<?, ?>> producersByServices;

    public Optional<KafkaProducerReplication<?, ?>> findByService(final AbsServiceRUD<?, ?, ?, ?, ?> service) {
        return ofNullable(producersByServices.get(service));
    }
}
