package by.aurorasoft.kafka.schema;

import by.aurorasoft.kafka.model.UnitActionTransportable;
import org.apache.avro.Schema;
import org.apache.avro.reflect.ReflectData;

public class SchemaRepositoryUnitActionTransportable {
    private static final Schema SCHEMA = ReflectData.get().getSchema(UnitActionTransportable.class);

    public static Schema getSchemaObject() {
        return SCHEMA;
    }
}
