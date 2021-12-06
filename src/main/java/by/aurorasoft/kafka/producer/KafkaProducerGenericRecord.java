package by.aurorasoft.kafka.producer;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.*;
import org.apache.avro.reflect.ReflectDatumWriter;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.ByteArrayOutputStream;

public abstract class KafkaProducerGenericRecord<TOPIC_KEY, TRANSPORTABLE> extends KafkaProducerAbstract<TOPIC_KEY, GenericRecord, TRANSPORTABLE> {

    public KafkaProducerGenericRecord(String topicName, KafkaTemplate<TOPIC_KEY, GenericRecord> kafkaTemplate, Schema schema) {
        super(topicName, kafkaTemplate, schema);
    }

    public void send(TRANSPORTABLE transportable) {
        GenericRecord record = pojoToRecord(transportable);
        kafkaTemplate.send(topicName, record);
    }

    private GenericRecord pojoToRecord(TRANSPORTABLE model) {
        try {
            ReflectDatumWriter<TRANSPORTABLE> datumWriter = new ReflectDatumWriter<>(schema);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(outputStream, null);
            datumWriter.write(model, encoder);
            encoder.flush();

            DatumReader<GenericRecord> datumReader = new GenericDatumReader<>(schema);
            BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(outputStream.toByteArray(), null);

            return datumReader.read(null, decoder);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
