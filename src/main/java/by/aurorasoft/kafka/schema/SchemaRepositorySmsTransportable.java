package by.aurorasoft.kafka.schema;

import by.aurorasoft.kafka.model.SmsTransportable;
import org.apache.avro.Schema;

import static org.apache.avro.reflect.ReflectData.get;

public final class SchemaRepositorySmsTransportable {
    private static final Schema SCHEMA = get().getSchema(SmsTransportable.class);

    public static Schema getSchema() {
        return SCHEMA;
    }
}
