package by.aurorasoft.kafka.replication.holder;

import by.aurorasoft.kafka.replication.producer.KafkaProducerReplication;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Getter
public final class KafkaProducerReplicationHolder {
    private final List<? extends KafkaProducerReplication<?, ?>> producers;
}
