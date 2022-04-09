package by.aurorasoft.kafka.schema;

import by.aurorasoft.kafka.model.RetranslatorTransportable;
import org.apache.avro.Schema;
import org.apache.avro.reflect.ReflectData;

public class SchemaRepositoryRetranslatorTransportable {
    private static final Schema SCHEMA = ReflectData.get().getSchema(RetranslatorTransportable.class);

    public static Schema getSchemaObject() {
        return SCHEMA;
    }
}
