package by.aurorasoft.kafka.producer.entityevent;

import by.aurorasoft.kafka.model.entityevent.ReplicatedEntityEvent;
import by.aurorasoft.kafka.producer.KafkaProducerGenericRecordIntermediaryHooks;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

public abstract class KafkaProducerReplicatedEntityEvent<TRANSPORTABLE, EVENT extends ReplicatedEntityEvent>
        extends KafkaProducerGenericRecordIntermediaryHooks<UUID, TRANSPORTABLE, EVENT> {

    public KafkaProducerReplicatedEntityEvent(final String topicName,
                                              final KafkaTemplate<UUID, GenericRecord> kafkaTemplate,
                                              final Schema schema) {
        super(topicName, kafkaTemplate, schema);
    }

    @Override
    protected final UUID getTopicKey(final EVENT event) {
        UUIDSerializer
        return event.getEntityId();
    }
}
