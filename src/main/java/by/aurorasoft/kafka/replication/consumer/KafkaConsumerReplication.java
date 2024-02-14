package by.aurorasoft.kafka.replication.consumer;

import by.aurorasoft.kafka.consumer.KafkaConsumerGenericRecord;
import by.aurorasoft.kafka.replication.model.TransportableReplication;
import by.aurorasoft.kafka.replication.model.replication.Replication;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD;
import lombok.RequiredArgsConstructor;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import static by.aurorasoft.kafka.replication.model.ReplicationType.valueOf;
import static by.aurorasoft.kafka.replication.model.TransportableReplication.Fields.entityId;

@RequiredArgsConstructor
public abstract class KafkaConsumerReplication<ENTITY_ID, DTO extends AbstractDto<ENTITY_ID>>
        extends KafkaConsumerGenericRecord<ENTITY_ID, Replication> {
    private final AbsServiceCRUD<ENTITY_ID, ?, DTO, ?> service;


//    @Override
//    public void listen(final ConsumerRecord<ENTITY_ID, GenericRecord> record) {
//        final TransportableReplication replication = map(record);
//        replication.execute(service);
//    }
//
//    @Override
//    protected final TransportableReplication map(final GenericRecord record) {
//        final ReplicationOperation operation = getReplicationOperation(record);
//        if (operation == SAVE || operation == UPDATE) {
//            return TransportableReplication.createTransportableSave(getTransportableDto(getObject(record, dto)));
//        } else if (operation == UPDATE) {
//            return TransportableReplication.createTransportableUpdate(getTransportableDto(getObject(record, dto)));
//        } else if (operation == DELETE) {
//            return extractDeleteReplication(record);
//        }
//        throw new RuntimeException();
//    }

//    protected abstract TRANSPORTABLE_DTO getTransportableDto(final GenericRecord record);
//
//    protected abstract DTO mapToDto(final TRANSPORTABLE_DTO transportableDto);
//
//    private ReplicationType getReplicationOperation(final GenericRecord record) {
//        return valueOf(getString(record, type));
//    }
//
//    private TransportableReplication extractDeleteReplication(final GenericRecord record) {
//        final ENTITY_ID entityId = getEntityId(record);
//        return createTransportableDelete(entityId);
//    }

    @SuppressWarnings("unchecked")
    private ENTITY_ID getEntityId(final GenericRecord record) {
        return (ENTITY_ID) record.get(entityId);
    }

    @Override
    public void listen(final ConsumerRecord<ENTITY_ID, GenericRecord> record) {

    }

    @Override
    protected Replication map(final GenericRecord record) {

        return null;
    }

    private TransportableReplication mapToTransportableReplication() {
        return null;
    }
}
