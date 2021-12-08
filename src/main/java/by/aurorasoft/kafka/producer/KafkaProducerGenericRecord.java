package by.aurorasoft.kafka.producer;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Collection;

public abstract class KafkaProducerGenericRecord<TOPIC_KEY, APP_MODEL, TRANSPORTABLE> extends KafkaProducerGenericRecordBase<TOPIC_KEY, TRANSPORTABLE> {

    public KafkaProducerGenericRecord(String topicName, KafkaTemplate<TOPIC_KEY, GenericRecord> kafkaTemplate, Schema schema) {
        super(topicName, kafkaTemplate, schema);
    }

    public void send(TRANSPORTABLE transportable) {
        if (!isSendable(transportable)) {
            return;
        }
        GenericRecord record = pojoToRecord(transportable);
        kafkaTemplate.send(topicName, record);
    }

    public void sendModel(APP_MODEL model) {
        send(convert(model));
    }

    public void sendModel(Collection<APP_MODEL> pojos){
        pojos.forEach(this::sendModel);
    }

    protected abstract TRANSPORTABLE convert(APP_MODEL pojo);

    protected boolean isSendable(TRANSPORTABLE transportable) {
        return transportable != null;
    }
}
