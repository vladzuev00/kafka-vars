package by.aurorasoft.kafka.schema;

import by.aurorasoft.kafka.model.UserActionTransportable;
import org.apache.avro.Schema;
import org.apache.avro.reflect.ReflectData;

public class SchemaRepositoryUserActionTransportable {
    private static final Schema SCHEMA = ReflectData.get().getSchema(UserActionTransportable.class);

    public static Schema getSchemaObject() {
        return SCHEMA;
    }
}
