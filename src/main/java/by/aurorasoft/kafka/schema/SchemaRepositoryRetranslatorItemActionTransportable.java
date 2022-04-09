package by.aurorasoft.kafka.schema;

import by.aurorasoft.kafka.model.RetranslatorItemActionTransportable;
import org.apache.avro.Schema;
import org.apache.avro.reflect.ReflectData;

public class SchemaRepositoryRetranslatorItemActionTransportable {
    private static final Schema SCHEMA = ReflectData.get().getSchema(RetranslatorItemActionTransportable.class);

    public static Schema getSchemaObject() {
        return SCHEMA;
    }
}
