package by.aurorasoft.kafka.producer;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Collection;

public abstract class KafkaProducerAbstract<TOPIC_KEY, TOPIC_VALUE, TRANSPORTABLE, MODEL> {

    protected final KafkaTemplate<TOPIC_KEY, TOPIC_VALUE> kafkaTemplate;
    protected final String topicName;

    public KafkaProducerAbstract(String topicName, KafkaTemplate<TOPIC_KEY, TOPIC_VALUE> kafkaTemplate) {
        this.topicName = topicName;
        this.kafkaTemplate = kafkaTemplate;
    }

    public abstract void send(MODEL model);

    protected abstract TRANSPORTABLE convertModelToTransportable(MODEL model);

    protected abstract TOPIC_VALUE convertTransportableToTopicValue(TRANSPORTABLE intermediate);

    public void send(Collection<MODEL> models){
        models.forEach(this::send);
    }

    protected void sendModel(TOPIC_KEY key, MODEL model) {
        TOPIC_VALUE value = topicValue(model);
        sendKafka(key, value);
    }

    protected void sendModel(MODEL model) {
        TOPIC_VALUE value = topicValue(model);
        sendKafka(value);
    }

    protected TOPIC_VALUE topicValue(MODEL model) {
        TRANSPORTABLE transportable = convertModelToTransportable(model);
        return convertTransportableToTopicValue(transportable);
    }

    protected void sendKafka(TOPIC_VALUE value) {
        sendKafka(new ProducerRecord<>(topicName, value));
    }

    protected void sendKafka(TOPIC_KEY key, TOPIC_VALUE value) {
        sendKafka(new ProducerRecord<>(topicName, key, value));
    }

    protected void sendKafka(ProducerRecord<TOPIC_KEY, TOPIC_VALUE> producerRecord) {
        kafkaTemplate.send(producerRecord);
    }
}
