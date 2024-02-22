package by.aurorasoft.kafka.replication.consumer;

import by.aurorasoft.kafka.consumer.KafkaConsumerGenericRecordBatch;
import by.aurorasoft.kafka.replication.annotation.ReplicatedService;
import by.aurorasoft.kafka.replication.model.ReplicationType;
import by.aurorasoft.kafka.replication.model.TransportableReplication;
import by.aurorasoft.kafka.replication.model.replication.Replication;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.LongSerializer;

import java.util.List;

import static by.aurorasoft.kafka.replication.model.TransportableReplication.Fields.dtoJson;
import static by.aurorasoft.kafka.replication.model.TransportableReplication.Fields.type;

@RequiredArgsConstructor
public abstract class KafkaConsumerReplication<ID, DTO extends AbstractDto<ID>>
        extends KafkaConsumerGenericRecordBatch<ID, Replication<ID, DTO>> {
    private final AbsServiceCRUD<ID, ?, DTO, ?> service;
    private final ObjectMapper objectMapper;
    private final Class<DTO> dtoType;

    @Override
    public void listen(final List<ConsumerRecord<ID, GenericRecord>> records) {
        records.stream()
                .map(this::map)
                .forEach(replication -> replication.execute(service));
    }

    @Override
    protected final Replication<ID, DTO> map(final GenericRecord record) {
        try {
            final TransportableReplication transportableReplication = createTransportableReplication(record);
            final DTO dto = objectMapper.readValue(transportableReplication.getDtoJson(), dtoType);
            return transportableReplication.getType().createReplication(dto);
        } catch (final JsonProcessingException cause) {
            throw new ReplicationConsumingException(cause);
        }
    }

    private TransportableReplication createTransportableReplication(final GenericRecord record) {
        return new TransportableReplication(
                getEnumObject(record, type, ReplicationType.class),
                getString(record, dtoJson)
        );
    }

    static final class ReplicationConsumingException extends RuntimeException {

        @SuppressWarnings("unused")
        public ReplicationConsumingException() {

        }

        @SuppressWarnings("unused")
        public ReplicationConsumingException(final String description) {
            super(description);
        }

        public ReplicationConsumingException(final Exception cause) {
            super(cause);
        }

        @SuppressWarnings("unused")
        public ReplicationConsumingException(final String description, final Exception cause) {
            super(description, cause);
        }
    }
}
