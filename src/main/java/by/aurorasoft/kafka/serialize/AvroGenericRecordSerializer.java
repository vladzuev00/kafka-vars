package by.aurorasoft.kafka.serialize;


import by.aurorasoft.kafka.variables.KafkaVars;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.kafka.common.serialization.Serializer;

import java.io.ByteArrayOutputStream;
import java.util.Map;

public class AvroGenericRecordSerializer implements Serializer<GenericRecord> {

    private Schema schema = null;

    @Override
    public void configure(Map<String, ?> map, boolean b) {
        schema = (Schema) map.get(KafkaVars.SCHEMA_PROP_NAME);
    }

    @Override
    public byte[] serialize(String arg0, GenericRecord record) {
        byte[] bytes = null;
        try {
            if (record != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                BinaryEncoder binaryEncoder = EncoderFactory.get().binaryEncoder(byteArrayOutputStream, null);
                DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(schema);
                datumWriter.write(record, binaryEncoder);
                binaryEncoder.flush();
                byteArrayOutputStream.close();
                bytes = byteArrayOutputStream.toByteArray();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    @Override
    public void close() {
    }
}


