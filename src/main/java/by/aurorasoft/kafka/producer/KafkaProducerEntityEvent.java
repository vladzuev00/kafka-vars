package by.aurorasoft.kafka.producer;

import by.aurorasoft.kafka.model.entityevent.EntityEventTransportable;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.springframework.kafka.core.KafkaTemplate;

public abstract class KafkaProducerEntityEvent<ID, DTO extends AbstractDto<ID>, TRANSPORTABLE>
        extends KafkaProducerGenericRecordIntermediaryHooks<ID, TRANSPORTABLE, EntityEventTransportable<ID>> {

    public KafkaProducerEntityEvent(final String topicName,
                                    final KafkaTemplate<ID, GenericRecord> kafkaTemplate,
                                    final Schema schema) {
        super(topicName, kafkaTemplate, schema);
    }

    @Override
    protected final ID getTopicKey(final EntityEventTransportable<ID> event) {
        return event.getEntityId();
    }


    @Override
    protected final TRANSPORTABLE convertModelToTransportable(final EntityEventTransportable<ID> event) {
        return mapToTransportable(event.getDto());
    }

    protected abstract TRANSPORTABLE mapToTransportable(final DTO dto);
}
