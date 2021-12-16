package by.aurorasoft.kafka.serialize;

import by.aurorasoft.kafka.variables.KafkaVars;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

//https://medium.com/@mailshine/apache-avro-quick-example-in-kafka-7b2909396c02
public class AvroGenericRecordDeserializer implements Deserializer {
    private Schema schema = null;

    @Override
    public void configure(Map configs, boolean isKey) {
        schema = (Schema) configs.get(KafkaVars.SCHEMA_PROP_NAME);
    }

    @Override
    public Object deserialize(String topic, byte[] bytes) {
        GenericRecord result = null;
        try {
            if (bytes != null) {
                DatumReader<GenericRecord> datumReader = new GenericDatumReader<>(schema);
                Decoder decoder = DecoderFactory.get().binaryDecoder(bytes, null);
                result = datumReader.read(null, decoder);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void close() {
        // do nothing
    }
}
