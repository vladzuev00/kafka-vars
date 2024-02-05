package by.aurorasoft.kafka.replication;

import by.aurorasoft.kafka.consumer.KafkaConsumerGenericRecord;
import by.aurorasoft.kafka.replication.model.Replication;
import by.aurorasoft.kafka.replication.model.ReplicationOperation;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD;
import lombok.RequiredArgsConstructor;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import static by.aurorasoft.kafka.replication.model.ReplicationOperation.valueOf;
import static by.aurorasoft.kafka.replication.model.TransportableReplication.Fields.operation;

@RequiredArgsConstructor
public abstract class KafkaConsumerReplication<ID, DTO extends AbstractDto<ID>>
        extends KafkaConsumerGenericRecord<ID, Replication<ID, DTO>> {
    private final AbsServiceCRUD<ID, ?, DTO, ?> service;

    @Override
    public final void listen(final ConsumerRecord<ID, GenericRecord> record) {
        final Replication<ID, DTO> replication = map(record.value());
        final ReplicationOperation operation = replication.getOperation();
        final DTO dto = replication.getDto();
        operation.execute(service, dto);
    }

    @Override
    protected final Replication<ID, DTO> map(final GenericRecord record) {
        final ReplicationOperation operation = findOperation(record);
        final DTO dto = createDto(record);
        return new Replication<>(operation, dto);
    }

    protected abstract DTO createDto(final GenericRecord record);

    private ReplicationOperation findOperation(final GenericRecord record) {
        return valueOf(getString(record, operation));
    }
}
