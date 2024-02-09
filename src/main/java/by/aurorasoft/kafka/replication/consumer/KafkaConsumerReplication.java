package by.aurorasoft.kafka.replication.consumer;

import by.aurorasoft.kafka.consumer.KafkaConsumerGenericRecord;
import by.aurorasoft.kafka.replication.model.Replication;
import by.aurorasoft.kafka.replication.model.ReplicationOperation;
import by.aurorasoft.kafka.replication.model.TransportableDto;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD;
import lombok.RequiredArgsConstructor;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import static by.aurorasoft.kafka.replication.model.Replication.Fields.*;
import static by.aurorasoft.kafka.replication.model.Replication.createDeleteReplication;
import static by.aurorasoft.kafka.replication.model.ReplicationOperation.*;

@RequiredArgsConstructor
public abstract class KafkaConsumerReplication<ENTITY_ID, DTO extends AbstractDto<ENTITY_ID>, TRANSPORTABLE_DTO extends TransportableDto>
        extends KafkaConsumerGenericRecord<ENTITY_ID, Replication> {
    private final AbsServiceCRUD<ENTITY_ID, ?, DTO, ?> service;

    @Override
    public void listen(final ConsumerRecord<ENTITY_ID, GenericRecord> record) {
        final Replication replication = map(record);
        replication.execute(service);
    }

    @Override
    protected final Replication map(final GenericRecord record) {
        final ReplicationOperation operation = getReplicationOperation(record);
        if (operation == SAVE || operation == UPDATE) {
            return Replication.createSaveReplication(getTransportableDto(getObject(record, dto)));
        } else if (operation == UPDATE) {
            return Replication.createUpdateReplication(getTransportableDto(getObject(record, dto)));
        } else if (operation == DELETE) {
            return extractDeleteReplication(record);
        }
        throw new RuntimeException();
    }

    protected abstract TRANSPORTABLE_DTO getTransportableDto(final GenericRecord record);

    protected abstract DTO mapToDto(final TRANSPORTABLE_DTO transportableDto);

    private ReplicationOperation getReplicationOperation(final GenericRecord record) {
        return valueOf(getString(record, operation));
    }

    private Replication extractDeleteReplication(final GenericRecord record) {
        final ENTITY_ID entityId = getEntityId(record);
        return createDeleteReplication(entityId);
    }

    @SuppressWarnings("unchecked")
    private ENTITY_ID getEntityId(final GenericRecord record) {
        return (ENTITY_ID) record.get(entityId);
    }
}
