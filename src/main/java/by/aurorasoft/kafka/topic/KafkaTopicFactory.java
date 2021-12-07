package by.aurorasoft.kafka.topic;

import org.apache.kafka.clients.admin.NewTopic;

public class KafkaTopicFactory {

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
     */
    public static NewTopic create(String name, String numberPartitions, String replicationFactor) {

        return new NewTopic(name, parseInt(name, numberPartitions), (short) parseInt(name, replicationFactor));
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
