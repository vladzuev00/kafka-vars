package by.aurorasoft.kafka.schema;

import by.aurorasoft.kafka.model.MessageTransportable;
import by.aurorasoft.kafka.model.UserActionTransportable;
import org.apache.avro.Schema;
import org.apache.avro.reflect.ReflectData;

public class SchemaRepositoryMessageTransportable {
    private static final Schema SCHEMA = ReflectData.get().getSchema(MessageTransportable.class);

    public static Schema getSchemaObject() {
        return SCHEMA;
    }
}
