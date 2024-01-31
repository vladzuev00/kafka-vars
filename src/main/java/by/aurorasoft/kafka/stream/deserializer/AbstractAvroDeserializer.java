package by.aurorasoft.kafka.stream.deserializer;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

public abstract class AbstractAvroDeserializer implements Deserializer<GenericRecord> {
    private Schema schema;

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        this.schema = createSchema();
    }

    public abstract Schema createSchema();

    @Override
    public GenericRecord deserialize(String topic, byte[] data) {
        DatumReader<GenericRecord> reader = new GenericDatumReader<>(schema);
        Decoder decoder = DecoderFactory.get().binaryDecoder(data, null);
        try {
            return reader.read(null, decoder);
        } catch (IOException e) {
            throw new RuntimeException("Deserialization error", e);
        }
    }

    @Override
    public void close() {
        // Закрытие ресурсов, если необходимо
    }
}
