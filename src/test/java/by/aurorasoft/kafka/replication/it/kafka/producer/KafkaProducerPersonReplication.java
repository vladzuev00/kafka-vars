package by.aurorasoft.kafka.replication.it.kafka.producer;

import by.aurorasoft.kafka.replication.it.crud.dto.Person;
import by.aurorasoft.kafka.replication.producer.KafkaProducerReplication;
import com.fasterxml.jackson.databind.ObjectMapper;
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
                                          @Qualifier("transportableReplicationSchema") final Schema schema,
                                          final ObjectMapper objectMapper) {
        super(topicName, kafkaTemplate, schema, objectMapper);
    }

    @Override
    protected Object projectDto(final Person dto) {
        return new PersonJsonView(dto.getId(), dto.getName(), dto.getSurname(), dto.getPatronymic());
    }

    @lombok.Value
    private static class PersonJsonView {
        Long id;
        String name;
        String surname;
        String patronymic;
    }
}
