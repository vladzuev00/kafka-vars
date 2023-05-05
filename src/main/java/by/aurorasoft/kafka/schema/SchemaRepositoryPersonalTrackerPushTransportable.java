package by.aurorasoft.kafka.schema;

import by.aurorasoft.kafka.model.PersonalTrackerPushTransportable;
import org.apache.avro.Schema;

import static org.apache.avro.reflect.ReflectData.get;

public final class SchemaRepositoryPersonalTrackerPushTransportable {

    private static final Schema SCHEMA = get().getSchema(PersonalTrackerPushTransportable.class);

    public static Schema getSchema() {
        return SCHEMA;
    }

}
