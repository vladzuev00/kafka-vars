package by.aurorasoft.kafka.replication.producer;

import by.aurorasoft.kafka.producer.KafkaProducerGenericRecordIntermediaryHooks;
import by.aurorasoft.kafka.replication.model.replication.Replication;
import by.aurorasoft.kafka.replication.model.ReplicationOperation;
import by.aurorasoft.kafka.replication.model.TransportableReplication;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.springframework.kafka.core.KafkaTemplate;

public abstract class KafkaProducerReplication<ENTITY_ID, DTO extends AbstractDto<ENTITY_ID>>
        extends KafkaProducerGenericRecordIntermediaryHooks<ENTITY_ID, TransportableReplication, Replication<ENTITY_ID, DTO>> {

    public KafkaProducerReplication(final String topicName,
                                    final KafkaTemplate<ENTITY_ID, GenericRecord> kafkaTemplate,
                                    final Schema schema) {
        super(topicName, kafkaTemplate, schema);
    }

    @Override
    protected final ENTITY_ID getTopicKey(final Replication<ENTITY_ID, DTO> event) {
        return event.getDto().getId();
    }

    @Override
    protected final TransportableReplication convertModelToTransportable(final Replication<ENTITY_ID, DTO> event) {
        return createTransportableEvent(event.getOperation(), event.getDto());
    }

    protected abstract TransportableReplication createTransportableEvent(final ReplicationOperation type,
                                                                                    final DTO dto);
}
