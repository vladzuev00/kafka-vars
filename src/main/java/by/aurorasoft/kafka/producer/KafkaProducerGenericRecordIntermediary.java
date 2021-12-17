package by.aurorasoft.kafka.producer;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.*;
import org.apache.avro.reflect.ReflectDatumWriter;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.ByteArrayOutputStream;

public abstract class KafkaProducerGenericRecordIntermediary<TOPIC_KEY, TRANSPORTABLE, MODEL> extends KafkaProducerAbstract<TOPIC_KEY, GenericRecord, TRANSPORTABLE, MODEL> {

    private final Schema schema;

    public KafkaProducerGenericRecordIntermediary(String topicName, KafkaTemplate<TOPIC_KEY, GenericRecord> kafkaTemplate, Schema schema) {
        super(topicName, kafkaTemplate);
        this.schema = schema;
    }

    @Override
    protected GenericRecord convertTransportableToTopicValue(TRANSPORTABLE model) {
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
