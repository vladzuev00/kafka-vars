package by.aurorasoft.kafka.replication.config;

import by.aurorasoft.kafka.base.AbstractSpringBootTest;
import by.aurorasoft.kafka.replication.model.TransportableReplication;
import org.apache.avro.Schema;
import org.apache.avro.reflect.ReflectData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import static org.junit.Assert.assertEquals;

public final class ReplicationConfigTest extends AbstractSpringBootTest {

    @Autowired
    @Qualifier("replicationSchema")
    private Schema schema;

    @Test
    public void replicationSchemaShouldBeCreated() {
        final Schema expected = ReflectData.get().getSchema(TransportableReplication.class);
        assertEquals(expected, schema);
    }
}
