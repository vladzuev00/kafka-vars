package by.aurorasoft.kafka.schema;

import by.aurorasoft.kafka.model.UnitSimpleTransportable;
import by.aurorasoft.kafka.model.UserActionTransportable;
import org.apache.avro.Schema;
import org.apache.avro.reflect.ReflectData;

public class SchemaRepositoryUnitSimpleTransportable {
    private static final Schema SCHEMA = ReflectData.get().getSchema(UnitSimpleTransportable.class);

    public static Schema getSchemaObject() {
        return SCHEMA;
    }
}
