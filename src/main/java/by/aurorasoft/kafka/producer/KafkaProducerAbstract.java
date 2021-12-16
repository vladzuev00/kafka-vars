package by.aurorasoft.kafka.producer;

import org.apache.avro.Schema;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;

public abstract class KafkaProducerAbstract<TOPIC_KEY, TOPIC_VALUE> {
    protected final KafkaTemplate<TOPIC_KEY, TOPIC_VALUE> kafkaTemplate;
    protected final String topicName;
    protected final Schema schema;

    public KafkaProducerAbstract(String topicName, KafkaTemplate<TOPIC_KEY, TOPIC_VALUE> kafkaTemplate, Schema schema) {
        this.topicName = topicName;
        this.kafkaTemplate = kafkaTemplate;
        this.schema = schema;
    }

    public void sendKafka(TOPIC_VALUE value) {
        sendKafka(new ProducerRecord<>(topicName, value));
    }

    public void sendKafka(TOPIC_KEY key, TOPIC_VALUE value) {
        sendKafka(new ProducerRecord<>(topicName, key, value));
    }

    public void sendKafka(ProducerRecord<TOPIC_KEY, TOPIC_VALUE> producerRecord) {
        if (isNotSendable(producerRecord.value())) {
            return;
        }
        kafkaTemplate.send(producerRecord);
    }

    protected boolean isNotSendable(TOPIC_VALUE value) {
        return false;
    }
}
