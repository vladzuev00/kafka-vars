package by.aurorasoft.kafka.config.schema;

import by.aurorasoft.kafka.config.model.CommandTransportable;
import org.apache.avro.Schema;
import org.apache.avro.reflect.ReflectData;

public class SchemaRepositoryCommandTransportable {
    private static final Schema SCHEMA = ReflectData.get().getSchema(CommandTransportable.class);

    public static Schema getSchemaObject() {
        return SCHEMA;
    }
}
