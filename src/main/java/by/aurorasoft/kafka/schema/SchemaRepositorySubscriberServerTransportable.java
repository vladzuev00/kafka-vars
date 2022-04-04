package by.aurorasoft.kafka.schema;

import by.aurorasoft.kafka.model.SubscriberServerTransportable;
import org.apache.avro.Schema;
import org.apache.avro.reflect.ReflectData;

public class SchemaRepositorySubscriberServerTransportable {
    private static final Schema SCHEMA = ReflectData.get().getSchema(SubscriberServerTransportable.class);

    public static Schema getSchemaObject() {
        return SCHEMA;
    }
}
