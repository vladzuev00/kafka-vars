package by.aurorasoft.kafka.producer.entityevent;

import by.aurorasoft.kafka.model.entityevent.DeleteReplicatedEntityEvent;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.springframework.kafka.core.KafkaTemplate;

public final class KafkaProducerDeletedEntityEvent extends KafkaProducerEntityEvent<ID, ID, DeleteReplicatedEntityEvent<ID>> {

    public KafkaProducerDeletedEntityEvent(final String topicName,
                                           final KafkaTemplate<ID, GenericRecord> kafkaTemplate,
                                           final Schema schema) {
        super(topicName, kafkaTemplate, schema);
    }

    @Override
    protected ID convertModelToTransportable(final DeleteReplicatedEntityEvent<ID> event) {
        return event.getEntityId();
    }
}
