package by.aurorasoft.kafka.replication.producer;

import by.aurorasoft.kafka.producer.KafkaProducerGenericRecordSimple;
import by.aurorasoft.kafka.replication.model.Replication;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.reflect.ReflectData;
import org.springframework.kafka.core.KafkaTemplate;

public abstract class KafkaProducerReplication<ENTITY_ID> extends KafkaProducerGenericRecordSimple<ENTITY_ID, Replication> {

    public KafkaProducerReplication(final String topicName, final KafkaTemplate<ENTITY_ID, GenericRecord> kafkaTemplate) {
        super(topicName, kafkaTemplate, getReplicationSchema());
    }

    @Override
    @SuppressWarnings("unchecked")
    public final void send(final Replication replication) {
        final ENTITY_ID entityId = (ENTITY_ID) replication.getEntityId();
        sendModel(entityId, replication);
    }

    private static Schema getReplicationSchema() {
        return ReflectData.get().getSchema(Replication.class);
    }
}
