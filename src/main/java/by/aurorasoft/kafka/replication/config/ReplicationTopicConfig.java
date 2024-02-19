package by.aurorasoft.kafka.replication.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

//TODO: validate
@RequiredArgsConstructor
@Getter
@ConfigurationProperties("kafka.entity-replication.topic")
public class ReplicationTopicConfig {
    private final int partitionCount;
    private final short replicationFactor;
}
