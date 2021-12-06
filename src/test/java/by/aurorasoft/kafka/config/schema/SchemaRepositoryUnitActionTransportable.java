package by.aurorasoft.kafka.config.schema;

import by.aurorasoft.kafka.config.model.CommandTransportable;
import by.aurorasoft.kafka.config.model.UnitActionTransportable;
import org.apache.avro.Schema;
import org.apache.avro.reflect.ReflectData;

public class SchemaRepositoryUnitActionTransportable {
    private static final Schema SCHEMA = ReflectData.get().getSchema(UnitActionTransportable.class);

    public static Schema getSchemaObject() {
        return SCHEMA;
    }
}
