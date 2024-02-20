package by.aurorasoft.kafka.replication.holder;

import by.aurorasoft.kafka.replication.producer.KafkaProducerReplication;
import by.nhorushko.crudgeneric.v2.service.AbsServiceRUD;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
public final class KafkaProducerReplicationHolder {
    private final Map<AbsServiceRUD<?, ?, ?, ?, ?>, KafkaProducerReplication<?, ?>> producersByServices;

    public KafkaProducerReplicationHolder(final List<KafkaProducerReplication<?, ?>> producers) {
        producersByServices = createProducersByServices(producers);
    }

    public Optional<KafkaProducerReplication<?, ?>> findByService(final AbsServiceRUD<?, ?, ?, ?, ?> service) {
        return ofNullable(producersByServices.get(service));
    }

    private Map<AbsServiceRUD<?, ?, ?, ?, ?>, KafkaProducerReplication<?, ?>> createProducersByServices(
            final List<KafkaProducerReplication<?, ?>> producers
    ) {
        return producers.stream()
                .collect(
                        toMap(
                                KafkaProducerReplication::getReplicatedService,
                                identity()
                        )
                );
    }
}
