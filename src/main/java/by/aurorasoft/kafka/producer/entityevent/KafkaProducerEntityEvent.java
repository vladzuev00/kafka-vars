package by.aurorasoft.kafka.producer.entityevent;

import by.aurorasoft.kafka.model.entity.EntityTransportable;
import by.aurorasoft.kafka.model.entity.entityevent.EntityEventTransportable;
import by.aurorasoft.kafka.producer.KafkaProducerGenericRecordIntermediaryHooks;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.springframework.kafka.core.KafkaTemplate;

public abstract class KafkaProducerEntityEvent<
        ENTITY_ID,
        ENTITY extends EntityTransportable<ENTITY_ID>,
        TRANSPORTABLE,
        EVENT extends EntityEventTransportable<ENTITY_ID, ENTITY>
        >
        extends KafkaProducerGenericRecordIntermediaryHooks<ENTITY_ID, TRANSPORTABLE, EVENT> {

    public KafkaProducerEntityEvent(final String topicName,
                                    final KafkaTemplate<ENTITY_ID, GenericRecord> kafkaTemplate,
                                    final Schema schema) {
        super(topicName, kafkaTemplate, schema);
    }

    @Override
    protected final ENTITY_ID getTopicKey(final EVENT event) {
        return event.getEntity().getId();
    }


    @Override
    protected TRANSPORTABLE convertModelToTransportable(final EVENT event) {
        final ENTITY entity = event.getEntity();
        return convertEntityToTransportable(entity);
    }

    protected abstract TRANSPORTABLE convertEntityToTransportable(final ENTITY entity);
}
