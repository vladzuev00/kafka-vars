package by.aurorasoft.kafka.schema;

import by.aurorasoft.kafka.model.SubscriberItemActionTransportable;
import by.aurorasoft.kafka.model.UserActionTransportable;
import org.apache.avro.Schema;
import org.apache.avro.reflect.ReflectData;

public class SchemaRepositorySubscriberItemActionTransportable {
    private static final Schema SCHEMA = ReflectData.get().getSchema(SubscriberItemActionTransportable.class);

    public static Schema getSchemaObject() {
        return SCHEMA;
    }
}
