package by.aurorasoft.kafka.schema;

import by.aurorasoft.kafka.model.FixOrderEventTransportable;
import by.aurorasoft.kafka.model.MessageTransportable;
import org.apache.avro.Schema;
import org.apache.avro.reflect.ReflectData;

import java.text.Format;

public class SchemaRepositoryFixOrderEventTransportable {
    private static final Schema SCHEMA = ReflectData.get().getSchema(FixOrderEventTransportable.class);

    public static Schema getSchemaObject() {
        return SCHEMA;
    }
}
