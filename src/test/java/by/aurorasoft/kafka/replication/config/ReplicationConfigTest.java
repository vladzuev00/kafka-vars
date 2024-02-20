package by.aurorasoft.kafka.replication.config;

import by.aurorasoft.kafka.base.AbstractSpringBootTest;
import by.aurorasoft.kafka.replication.holder.KafkaProducerReplicationHolder;
import by.aurorasoft.kafka.replication.model.TransportableReplication;
import org.apache.avro.Schema;
import org.apache.avro.reflect.ReflectData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public final class ReplicationConfigTest extends AbstractSpringBootTest {

    @Autowired
    @Qualifier("replicationSchema")
    private Schema schema;

    @Autowired
    private KafkaProducerReplicationHolder producerHolder;

    @Test
    public void replicationSchemaShouldBeCreated() {
        final Schema expected = ReflectData.get().getSchema(TransportableReplication.class);
        assertEquals(expected, schema);
    }

    @Test
    public void producerHolderShouldBeCreated() {
        assertNotNull(producerHolder);
    }
}
