package by.aurorasoft.kafka.config.topic;

import org.apache.kafka.clients.admin.NewTopic;

public class KafkaTopicFactory {

    public static NewTopic create(String name, int numberPartitions, short replicationFactor) {
        return new NewTopic(name, numberPartitions, replicationFactor);
    }
}
