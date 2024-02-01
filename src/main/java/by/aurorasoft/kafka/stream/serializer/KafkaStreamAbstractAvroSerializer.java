package by.aurorasoft.kafka.stream.serializer;

import by.aurorasoft.kafka.serialize.AvroGenericRecordSerializer;
import org.apache.avro.Schema;

import java.util.Map;

public abstract class KafkaStreamAbstractAvroSerializer extends AvroGenericRecordSerializer {

    @Override
    public void configure(Map<String, ?> arg0, boolean arg1) {
        schema = this.createSchema();
    }

    protected abstract Schema createSchema();
}
