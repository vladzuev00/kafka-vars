package by.aurorasoft.kafka.replication.it.kafka.consumer;

import by.aurorasoft.kafka.replication.consumer.KafkaConsumerReplication;
import by.aurorasoft.kafka.replication.it.crud.dto.PersonReplication;
import by.aurorasoft.kafka.replication.it.crud.service.PersonReplicationService;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

import static by.aurorasoft.kafka.replication.it.model.TransportablePersonReplication.Fields.*;

@Component
public final class KafkaConsumerPersonReplication extends KafkaConsumerReplication<Long, PersonReplication> {

    public KafkaConsumerPersonReplication(final PersonReplicationService replicationService) {
        super(replicationService);
    }

    @Override
    @KafkaListener(
            topics = "${kafka.topic.sync-person.name}",
            groupId = "${kafka.topic.sync-person.consumer.group-id}",
            containerFactory = "listenerContainerFactorySyncPerson"
    )
    public void listen(final List<ConsumerRecord<Long, GenericRecord>> records) {
        super.listen(records);
    }

    @Override
    protected PersonReplication createDto(final GenericRecord record) {
        return new PersonReplication(
                getLong(record, entityId),
                getString(record, name),
                getString(record, surname)
        );
    }
}
