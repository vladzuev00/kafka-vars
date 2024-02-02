package by.aurorasoft.kafka.producer.entityevent;

import by.aurorasoft.kafka.model.entityevent.ReplicatedEntityEvent;
import by.aurorasoft.kafka.producer.KafkaProducerGenericRecordIntermediaryHooks;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

public abstract class KafkaProducerEntityEvent<TRANSPORTABLE, EVENT extends ReplicatedEntityEvent<ID>>
        extends KafkaProducerGenericRecordIntermediaryHooks<UUID, TRANSPORTABLE, EVENT> {

    public KafkaProducerEntityEvent(final String topicName,
                                    final KafkaTemplate<ID, GenericRecord> kafkaTemplate,
                                    final Schema schema) {
        super(topicName, kafkaTemplate, schema);
    }

    @Override
    protected final ID getTopicKey(final EVENT event) {
        return event.getEntityId();
    }
}
