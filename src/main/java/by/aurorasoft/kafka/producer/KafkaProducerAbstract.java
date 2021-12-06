package by.aurorasoft.kafka.producer;

import org.apache.avro.Schema;
import org.springframework.kafka.core.KafkaTemplate;

public abstract class KafkaProducerAbstract <TOPIC_KEY, TOPIC_VALUE, TRANSPORTABLE> {
    protected final KafkaTemplate<TOPIC_KEY, TOPIC_VALUE> kafkaTemplate;
    protected final String topicName;
    protected final Schema schema;

    public KafkaProducerAbstract(String topicName, KafkaTemplate<TOPIC_KEY, TOPIC_VALUE> kafkaTemplate, Schema schema) {
        this.topicName = topicName;
        this.kafkaTemplate = kafkaTemplate;
        this.schema = schema;
    }

    public abstract void send(TRANSPORTABLE transportable);
}
