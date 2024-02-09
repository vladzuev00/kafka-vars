package by.aurorasoft.kafka.replication.producer;

import by.aurorasoft.kafka.producer.KafkaProducerGenericRecordIntermediaryHooks;
import by.aurorasoft.kafka.replication.model.replication.Replication;
import by.aurorasoft.kafka.replication.model.transportable.TransportableDto;
import by.aurorasoft.kafka.replication.model.transportable.TransportableReplication;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.reflect.ReflectData;
import org.springframework.kafka.core.KafkaTemplate;

public abstract class KafkaProducerReplication<ENTITY_ID, DTO extends AbstractDto<ENTITY_ID>>
        extends KafkaProducerGenericRecordIntermediaryHooks<ENTITY_ID, TransportableReplication, Replication> {

    public KafkaProducerReplication(final String topicName, final KafkaTemplate<ENTITY_ID, GenericRecord> kafkaTemplate) {
        super(topicName, kafkaTemplate, getTransportableReplicationSchema());
    }

    @Override
    protected final TransportableReplication convertModelToTransportable(final Replication replication) {
        return replication.mapToTransportable(this::mapToTransportableDto);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected final ENTITY_ID getTopicKey(final Replication replication) {
        return (ENTITY_ID) replication.getEntityId();
    }

    protected abstract TransportableDto mapToTransportableDto(final DTO dto);

    private static Schema getTransportableReplicationSchema() {
        return ReflectData.get().getSchema(TransportableReplication.class);
    }
}
