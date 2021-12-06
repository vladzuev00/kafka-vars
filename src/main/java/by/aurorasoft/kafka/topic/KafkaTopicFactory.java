package by.aurorasoft.kafka.topic;

import org.apache.kafka.clients.admin.NewTopic;

public class KafkaTopicFactory {

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
