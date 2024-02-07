package by.aurorasoft.kafka.replication.consumer;

import by.aurorasoft.kafka.consumer.KafkaConsumerGenericRecordBatch;
import by.aurorasoft.kafka.replication.model.ReplicationOperation;
import by.aurorasoft.kafka.replication.model.replication.DeleteReplication;
import by.aurorasoft.kafka.replication.model.replication.Replication;
import by.aurorasoft.kafka.replication.model.replication.SaveReplication;
import by.aurorasoft.kafka.replication.model.replication.UpdateReplication;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD;
import lombok.RequiredArgsConstructor;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.List;

import static by.aurorasoft.kafka.replication.model.ReplicationOperation.*;
import static by.aurorasoft.kafka.replication.model.TransportableReplication.Fields.operation;

@RequiredArgsConstructor
public abstract class KafkaConsumerReplication<ID, DTO extends AbstractDto<ID>>
        extends KafkaConsumerGenericRecordBatch<ID, Replication<ID, DTO>> {
    private final AbsServiceCRUD<ID, ?, DTO, ?> service;

    @Override
    public void listen(final List<ConsumerRecord<ID, GenericRecord>> records) {
        records.forEach(this::consume);
    }

    @Override
    protected final Replication<ID, DTO> map(final GenericRecord record) {
        final ReplicationOperation operation = findOperation(record);
        final ID entityId = createId(record);
        if (operation == SAVE) {
            return new SaveReplication<>(createDto(entityId, record));
        } else if (operation == UPDATE) {
            return new UpdateReplication<>(createDto(entityId, record));
        } else if (operation == DELETE) {
            return new DeleteReplication<>(entityId);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    protected abstract ID createId(final GenericRecord record);

    protected abstract DTO createDto(final ID id, final GenericRecord record);

    private void consume(final ConsumerRecord<ID, GenericRecord> record) {
        final Replication<ID, DTO> replication = map(record.value());
        replication.execute(service);
    }

    private ReplicationOperation findOperation(final GenericRecord record) {
        return valueOf(getString(record, operation));
    }
}
