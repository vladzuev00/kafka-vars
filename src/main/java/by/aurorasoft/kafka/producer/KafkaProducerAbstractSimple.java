package by.aurorasoft.kafka.producer;

import org.apache.avro.Schema;
import org.springframework.kafka.core.KafkaTemplate;

public abstract class KafkaProducerAbstractSimple<TOPIC_KEY, TOPIC_VALUE> extends KafkaProducerAbstract<TOPIC_KEY, TOPIC_VALUE, TOPIC_VALUE, TOPIC_VALUE> {

    public KafkaProducerAbstractSimple(String topicName, KafkaTemplate<TOPIC_KEY, TOPIC_VALUE> kafkaTemplate, Schema schema) {
        super(topicName, kafkaTemplate, schema);
    }

    @Override
    protected TOPIC_VALUE convertModelToTransportable(TOPIC_VALUE topicValue) {
        return topicValue;
    }

    @Override
    protected TOPIC_VALUE convertTransportableToTopicValue(TOPIC_VALUE topicValue) {
        return topicValue;
    }
}
