package by.aurorasoft.kafka.producer;

import org.apache.avro.Schema;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Collection;

public abstract class KafkaProducerAbstractModel<TOPIC_KEY, TOPIC_VALUE, MODEL> extends KafkaProducerAbstract<TOPIC_KEY, TOPIC_VALUE> {

    public KafkaProducerAbstractModel(String topicName, KafkaTemplate<TOPIC_KEY, TOPIC_VALUE> kafkaTemplate, Schema schema) {
        super(topicName, kafkaTemplate, schema);
    }

    public void send(MODEL model) {
        sendKafka(convert(model));
    }

    public void send(TOPIC_KEY key, MODEL model) {
        sendKafka(key, convert(model));
    }

    public void send(Collection<MODEL> list) {
        list.forEach(model -> sendKafka(convert(model)));
    }

    public void send(TOPIC_KEY key, Collection<MODEL> list) {
        list.forEach(model -> sendKafka(key, convert(model)));
    }

    protected abstract TOPIC_VALUE convert(MODEL transportable);
}
