package by.aurorasoft.kafka.replication.it.kafka.consumer;

import by.aurorasoft.kafka.replication.consumer.KafkaConsumerReplication;
import by.aurorasoft.kafka.replication.it.crud.dto.PersonReplication;
import by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class KafkaConsumerPersonReplication extends KafkaConsumerReplication<Long, PersonReplication> {

    public KafkaConsumerPersonReplication(final AbsServiceCRUD<Long, ?, PersonReplication, ?> service,
                                          final ObjectMapper objectMapper) {
        super(service, objectMapper, PersonReplication.class);
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

}
