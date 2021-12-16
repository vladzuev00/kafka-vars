package by.aurorasoft.kafka.producer;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.*;
import org.apache.avro.reflect.ReflectDatumWriter;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.ByteArrayOutputStream;
import java.util.Collection;

public abstract class KafkaProducerGenericRecordIntermediary<TOPIC_KEY, INTERMEDIARY, MODEL> extends KafkaProducerGenericRecord<TOPIC_KEY, INTERMEDIARY> {

    public KafkaProducerGenericRecordIntermediary(String topicName, KafkaTemplate<TOPIC_KEY, GenericRecord> kafkaTemplate, Schema schema) {
        super(topicName, kafkaTemplate, schema);
    }

    public void sendModel(MODEL model) {
        INTERMEDIARY intermediary = convertIntermediary(model);
        send(intermediary);
    }

    public void sendModel(TOPIC_KEY key, MODEL model) {
        INTERMEDIARY intermediary = convertIntermediary(model);
        send(key, intermediary);
    }

    public void sendModel(TOPIC_KEY key, Collection<MODEL> list) {
        list.forEach(model -> sendModel(key, model));
    }

    public void sendModel(Collection<MODEL> list) {
        list.forEach(model -> sendModel(model));
    }

    protected abstract INTERMEDIARY convertIntermediary(MODEL model);
}
