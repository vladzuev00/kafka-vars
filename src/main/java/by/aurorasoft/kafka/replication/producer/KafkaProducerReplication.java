package by.aurorasoft.kafka.replication.producer;

import by.aurorasoft.kafka.producer.KafkaProducerGenericRecordIntermediaryHooks;
import by.aurorasoft.kafka.replication.model.TransportableReplication;
import by.aurorasoft.kafka.replication.model.replication.Replication;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.reflect.ReflectData;
import org.springframework.kafka.core.KafkaTemplate;

public abstract class KafkaProducerReplication<ID, DTO extends AbstractDto<ID>>
        extends KafkaProducerGenericRecordIntermediaryHooks<ID, TransportableReplication, Replication<ID, DTO>> {
    private final ReplicationProducingContext<ID, DTO> context;

    public KafkaProducerReplication(final String topicName,
                                    final KafkaTemplate<ID, GenericRecord> kafkaTemplate,
                                    final ObjectMapper objectMapper) {
        super(topicName, kafkaTemplate, getTransportableReplicationSchema());
        this.context = new ReplicationProducingContext<>(this::mapToJsonView, objectMapper);
    }

    @Override
    protected final ID getTopicKey(final Replication<ID, DTO> replication) {
        return replication.getEntityId();
    }

    @Override
    protected final TransportableReplication convertModelToTransportable(final Replication<ID, DTO> replication) {
        return replication.createTransportable(context);
    }

    protected abstract Object mapToJsonView(final DTO dto);

    private static Schema getTransportableReplicationSchema() {
        return ReflectData.get().getSchema(TransportableReplication.class);
    }
}
