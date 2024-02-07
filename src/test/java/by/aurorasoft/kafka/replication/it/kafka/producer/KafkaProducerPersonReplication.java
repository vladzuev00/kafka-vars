package by.aurorasoft.kafka.replication.it.kafka.producer;

import by.aurorasoft.kafka.replication.it.crud.dto.Person;
import by.aurorasoft.kafka.replication.it.model.TransportablePersonReplication;
import by.aurorasoft.kafka.replication.model.TransportableReplication;
import by.aurorasoft.kafka.replication.model.replication.Replication;
import by.aurorasoft.kafka.replication.producer.KafkaProducerReplication;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public final class KafkaProducerPersonReplication extends KafkaProducerReplication<Long, Person> {

    public KafkaProducerPersonReplication(@Value("${kafka.topic.sync-person.name}") final String topicName,
                                          @Qualifier("kafkaTemplateSyncPerson") final KafkaTemplate<Long, GenericRecord> kafkaTemplate,
                                          @Qualifier("transportablePersonReplicationSchema") final Schema schema) {
        super(topicName, kafkaTemplate, schema);
    }

    @Override
    protected TransportablePersonReplication convertModelToTransportable(final Replication<Long, Person> replication) {
        return new TransportablePersonReplication(
                replication.getOperation(),
                replication.getEntityId(),
                replication
                );
    }
}
