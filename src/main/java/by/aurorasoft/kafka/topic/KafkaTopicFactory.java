package by.aurorasoft.kafka.topic;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;

public class KafkaTopicFactory {

    private static final int DAY_MS = 86_400_000;
    private static final long BYTES_GB = 1073741824;

    /**
     *     @Value(value = "${kafka.topic.received-commands.partitions-number}")
     *     private String receivedCommandsPartitionsNumber;
     *     @Value(value = "${kafka.topic.received-commands.replication-factor}")
     *     private String receivedCommandsReplicationFactor;
     *
     *     @Bean
     *     public NewTopic receivedCommands() {
     *         return KafkaTopicFactory.create(KafkaVars.RECEIVED_COMMANDS_LOG_TOPIC_NAME, receivedCommandsPartitionsNumber, receivedCommandsReplicationFactor);
     *     }
     *
     *     0 - kafka default value
     *
     */
    public static NewTopic create(String name, String numberPartitions, String replicationFactor, int retentionDays, int retentionGb) {
        NewTopic topic = new NewTopic(name, parseInt(name, numberPartitions), (short) parseInt(name, replicationFactor));
        if (retentionDays > 0) {
            topic.configs().put(TopicConfig.RETENTION_MS_CONFIG, String.valueOf(retentionDays * DAY_MS));
        }
        if (retentionDays == -1) {
            topic.configs().put(TopicConfig.RETENTION_MS_CONFIG, String.valueOf(retentionDays));
        }
        if (retentionGb > 0) {
            topic.configs().put(TopicConfig.RETENTION_MS_CONFIG, String.valueOf(retentionGb * BYTES_GB));
        }
        if (retentionGb == -1) {
            topic.configs().put(TopicConfig.RETENTION_MS_CONFIG, String.valueOf(retentionGb));
        }
        return new NewTopic(name, parseInt(name, numberPartitions), (short) parseInt(name, replicationFactor));
    }

    public static NewTopic create(String name, String numberPartitions, String replicationFactor) {

        return create(name, numberPartitions, replicationFactor, 0, 0);
    }

    private static int parseInt(String name, String value) {
        if (value == null) {
            throw new IllegalArgumentException(String.format("Topic: '%s' creating failed. value is null", name));
        }
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
