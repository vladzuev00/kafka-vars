package by.aurorasoft.kafka.replication.producer;

import by.aurorasoft.kafka.producer.KafkaProducerGenericRecordIntermediaryHooks;
import by.aurorasoft.kafka.replication.model.TransportableReplication;
import by.aurorasoft.kafka.replication.model.replication.DeleteReplication;
import by.aurorasoft.kafka.replication.model.replication.Replication;
import by.aurorasoft.kafka.replication.model.replication.SaveReplication;
import by.aurorasoft.kafka.replication.model.replication.UpdateReplication;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.reflect.ReflectData;
import org.springframework.kafka.core.KafkaTemplate;

import static by.aurorasoft.kafka.replication.model.TransportableReplication.*;

public abstract class KafkaProducerReplication<ID, DTO extends AbstractDto<ID>>
        extends KafkaProducerGenericRecordIntermediaryHooks<ID, TransportableReplication, Replication<ID, DTO>> {
    private final ObjectMapper objectMapper;

    public KafkaProducerReplication(final String topicName,
                                    final KafkaTemplate<ID, GenericRecord> kafkaTemplate,
                                    final ObjectMapper objectMapper) {
        super(topicName, kafkaTemplate, getTransportableReplicationSchema());
        this.objectMapper = objectMapper;
    }

    @Override
    protected final TransportableReplication convertModelToTransportable(final Replication<ID, DTO> replication) {
        if (replication instanceof final SaveReplication saveReplication) {
            return createSaveReplication(objectMapper.writeValueAsString(mapToJsonView((DTO) saveReplication.getDto())));
        } else if (replication instanceof final UpdateReplication updateReplication) {
            return createUpdateReplication(objectMapper.writeValueAsString(mapToJsonView((DTO) updateReplication.getDto())));
        } else if(replication instanceof final DeleteReplication deleteReplication) {
            return createDeleteReplication(deleteReplication.getEntityId());
        }
        throw new UnsupportedOperationException("Replicate replication is unsupported: '%s'".formatted(replication));
    }

    @Override
    @SuppressWarnings("unchecked")
    protected final ID getTopicKey(final Replication replication) {
        return (ID) replication.getEntityId();
    }

    protected abstract Object mapToJsonView(final DTO dto);

    private static Schema getTransportableReplicationSchema() {
        return ReflectData.get().getSchema(TransportableReplication.class);
    }
}
