package by.aurorasoft.kafka.producer;

import org.apache.avro.Schema;
import org.springframework.kafka.core.KafkaTemplate;

public abstract class KafkaProducerGeneric<TOPIC_KEY, TOPIC_VALUE, MODEL> extends KafkaProducerAbstract<TOPIC_KEY, TOPIC_VALUE, MODEL, MODEL> {

    public KafkaProducerGeneric(String topicName, KafkaTemplate<TOPIC_KEY, TOPIC_VALUE> kafkaTemplate) {
        super(topicName, kafkaTemplate);
    }

    @Override
    protected MODEL convertModelToTransportable(MODEL model) {
        return model;
    }
}
