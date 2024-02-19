package by.aurorasoft.kafka.replication.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

//TODO if need: ConstructorBinding
//TODO: validate
@RequiredArgsConstructor
@Getter
@ConfigurationProperties("kafka.entity-replication.producer")
public class ReplicationProducerConfig {
    private final int batchSize;
    private final int lingerMs;
    private final int deliveryTimeoutMs;
}
