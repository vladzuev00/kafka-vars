package by.aurorasoft.kafka.stream.deserializer;

import by.aurorasoft.kafka.serialize.AvroGenericRecordDeserializer;
import org.apache.avro.Schema;

import java.util.Map;

public abstract class KafkaStreamAbstractAvroDeserializer extends AvroGenericRecordDeserializer {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        this.schema = createSchema();
    }

    public abstract Schema createSchema();
}
