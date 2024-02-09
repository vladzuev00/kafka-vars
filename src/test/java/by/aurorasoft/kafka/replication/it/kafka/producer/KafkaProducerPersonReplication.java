package by.aurorasoft.kafka.replication.it.kafka.producer;

import by.aurorasoft.kafka.replication.producer.KafkaProducerReplication;
import org.apache.avro.generic.GenericRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public final class KafkaProducerPersonReplication extends KafkaProducerReplication<Long> {

    public KafkaProducerPersonReplication(@Value("${kafka.topic.sync-person.name}") final String topicName,
                                          @Qualifier("kafkaTemplateSyncPerson") final KafkaTemplate<Long, GenericRecord> kafkaTemplate) {
        super(topicName, kafkaTemplate);
    }

}
