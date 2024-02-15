package by.aurorasoft.kafka.replication.consumer;

import by.aurorasoft.kafka.consumer.KafkaConsumerGenericRecordBatch;
import by.aurorasoft.kafka.replication.model.TransportableReplication;
import by.aurorasoft.kafka.replication.model.TransportableReplication.ReplicationType;
import by.aurorasoft.kafka.replication.model.replication.Replication;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.List;

import static by.aurorasoft.kafka.replication.model.TransportableReplication.Fields.dtoJson;
import static by.aurorasoft.kafka.replication.model.TransportableReplication.Fields.type;

public abstract class KafkaConsumerReplication<ID, DTO extends AbstractDto<ID>>
        extends KafkaConsumerGenericRecordBatch<ID, Replication<ID, DTO>> {
    private final AbsServiceCRUD<ID, ?, DTO, ?> service;
    private final ReplicationConsumingContext<ID, DTO> context;

    public KafkaConsumerReplication(final AbsServiceCRUD<ID, ?, DTO, ?> service,
                                    final ObjectMapper objectMapper,
                                    final Class<DTO> dtoType) {
        this.service = service;
        context = new ReplicationConsumingContext<>(objectMapper, dtoType);
    }

    @Override
    public void listen(final List<ConsumerRecord<ID, GenericRecord>> records) {
        records.stream()
                .map(this::map)
                .forEach(replication -> replication.execute(service));
    }

    @Override
    protected final Replication<ID, DTO> map(final GenericRecord record) {
        return createTransportableReplication(record).createReplication(context);
    }

    private TransportableReplication createTransportableReplication(final GenericRecord record) {
        return new TransportableReplication(
                getEnumObject(record, type, ReplicationType.class),
                getString(record, dtoJson)
        );
    }
}
