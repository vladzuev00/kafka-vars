package replication.it.eventproducer;

import by.aurorasoft.kafka.replication.model.ReplicationOperation;
import by.aurorasoft.kafka.replication.model.TransportableReplication;
import by.aurorasoft.kafka.replication.producer.KafkaProducerReplication;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import replication.it.crud.dto.Person;
import replication.it.model.TransportablePersonReplicationEvent;

@Component
public final class KafkaProducerPersonReplication extends KafkaProducerReplication<Long, Person> {

    public KafkaProducerPersonReplication(final String topicName,
                                          final KafkaTemplate<Long, GenericRecord> kafkaTemplate,
                                          final Schema schema) {
        super(topicName, kafkaTemplate, schema);
    }

    @Override
    protected TransportableReplication<Long> createTransportableEvent(final ReplicationOperation operation,
                                                                      final Person dto) {
        return new TransportablePersonReplicationEvent(operation, dto.getId(), dto.getName(), dto.getSurname());
    }
}
