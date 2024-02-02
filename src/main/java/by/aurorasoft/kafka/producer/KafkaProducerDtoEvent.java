package by.aurorasoft.kafka.producer;

import by.aurorasoft.kafka.model.dtoevent.DtoTransportable;
import by.aurorasoft.kafka.model.dtoevent.EntityEventTransportable;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.springframework.kafka.core.KafkaTemplate;

public abstract class KafkaProducerDtoEvent<
        ID,
        DTO extends DtoTransportable<ID>,
        TRANSPORTABLE,
        EVENT extends EntityEventTransportable<DTO>
        >
        extends KafkaProducerGenericRecordIntermediaryHooks<ID, TRANSPORTABLE, EVENT> {

    public KafkaProducerDtoEvent(final String topicName,
                                 final KafkaTemplate<ID, GenericRecord> kafkaTemplate,
                                 final Schema schema) {
        super(topicName, kafkaTemplate, schema);
    }

    @Override
    protected final ID getTopicKey(final EVENT event) {
        return event.getDto().getId();
    }


    @Override
    protected final TRANSPORTABLE convertModelToTransportable(final EVENT event) {
        return mapToTransportable(event.getDto());
    }

    protected abstract TRANSPORTABLE mapToTransportable(final DTO source);
}
