package by.aurorasoft.kafka.replication.topiccreator;

import by.aurorasoft.kafka.replication.config.ReplicationTopicConfig;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class ReplicationTopicCreator {
    private final KafkaAdmin kafkaAdmin;
    private final ReplicationTopicConfig config;

    public void create(final String topicName) {
        final NewTopic topic = TopicBuilder.name(topicName)
                .partitions(config.getPartitionCount())
                .replicas(config.getReplicationFactor())
                .build();
        kafkaAdmin.createOrModifyTopics(topic);
    }
}
