package by.aurorasoft.kafka.replication.producer;

import by.aurorasoft.kafka.producer.KafkaProducerGenericRecordIntermediaryHooks;
import by.aurorasoft.kafka.replication.model.TransportableReplication;
import by.aurorasoft.kafka.replication.model.replication.Replication;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.springframework.kafka.core.KafkaTemplate;

public abstract class KafkaProducerReplication<ID, DTO extends AbstractDto<ID>>
        extends KafkaProducerGenericRecordIntermediaryHooks<ID, TransportableReplication, Replication<ID, DTO>> {
    private final ReplicationProducingContext<ID, DTO> context;

    public KafkaProducerReplication(final String topicName,
                                    final KafkaTemplate<ID, GenericRecord> kafkaTemplate,
                                    final Schema schema,
                                    final ObjectMapper objectMapper) {
        super(topicName, kafkaTemplate, schema);
        context = new ReplicationProducingContext<>(objectMapper);
    }

    @Override
    protected final ID getTopicKey(final Replication<ID, DTO> replication) {
        return replication.getEntityId();
    }

    @Override
    protected final TransportableReplication convertModelToTransportable(final Replication<ID, DTO> replication) {
        return replication.createTransportable(context);
    }
}
