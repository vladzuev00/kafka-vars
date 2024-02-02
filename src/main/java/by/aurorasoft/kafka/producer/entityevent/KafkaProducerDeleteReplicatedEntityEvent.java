package by.aurorasoft.kafka.producer.entityevent;

import by.aurorasoft.kafka.model.entityevent.DeleteReplicatedEntityEvent;
import by.aurorasoft.kafka.producer.KafkaProducerAbstract;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

public final class KafkaProducerDeleteReplicatedEntityEvent
        extends KafkaProducerAbstract<UUID, UUID, UUID, DeleteReplicatedEntityEvent> {

    public KafkaProducerDeleteReplicatedEntityEvent(final String topicName, final KafkaTemplate<UUID, UUID> kafkaTemplate) {
        super(topicName, kafkaTemplate);
    }

    @Override
    protected UUID convertModelToTransportable(final DeleteReplicatedEntityEvent event) {
        return event.getEntityId();
    }

    @Override
    protected UUID convertTransportableToTopicValue(final UUID id) {
        return id;
    }
}
