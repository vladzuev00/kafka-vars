package by.aurorasoft.kafka.replication.holder;

import by.aurorasoft.kafka.replication.producer.KafkaProducerReplication;
import by.nhorushko.crudgeneric.v2.service.AbsServiceRUD;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
public final class KafkaProducerReplicationHolder {
    private final Map<AbsServiceRUD<?, ?, ?, ?, ?>, KafkaProducerReplication<?, ?>> producersByServices;

    public Optional<KafkaProducerReplication<?, ?>> findByService(final AbsServiceRUD<?, ?, ?, ?, ?> service) {
        return ofNullable(producersByServices.get(service));
    }
}
