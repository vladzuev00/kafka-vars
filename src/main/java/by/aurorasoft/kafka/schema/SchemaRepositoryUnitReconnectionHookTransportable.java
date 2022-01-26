package by.aurorasoft.kafka.schema;

import by.aurorasoft.kafka.model.UnitReconnectHookTransportable;
import org.apache.avro.Schema;
import org.apache.avro.reflect.ReflectData;

public class SchemaRepositoryUnitReconnectionHookTransportable {

    private static final Schema SCHEMA = ReflectData.get().getSchema(UnitReconnectHookTransportable.class);

    public static Schema getSchemaObject() {
        return SCHEMA;
    }
}
