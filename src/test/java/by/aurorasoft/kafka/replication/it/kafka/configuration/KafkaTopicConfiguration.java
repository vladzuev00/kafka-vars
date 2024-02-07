package by.aurorasoft.kafka.replication.it.kafka.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfiguration {

    @Bean
    public NewTopic syncPersonTopic(
            @Value("${kafka.topic.sync-person.name}") final String topicName,
            @Value("${kafka.topic.sync-person.partitions-amount}") final int partitionsAmount,
            @Value("${kafka.topic.sync-person.replication-factor}") final short replicationFactor
    ) {
        return new NewTopic(topicName, partitionsAmount, replicationFactor);
    }

}
