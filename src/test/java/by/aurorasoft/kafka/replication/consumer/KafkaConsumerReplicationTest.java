package by.aurorasoft.kafka.replication.consumer;

import by.aurorasoft.kafka.replication.model.TransportableReplication.Fields;
import by.aurorasoft.kafka.replication.model.TransportableReplication.ReplicationType;
import by.aurorasoft.kafka.replication.model.replication.Replication;
import by.aurorasoft.kafka.replication.model.replication.SaveReplication;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Value;
import org.apache.avro.generic.GenericRecord;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static by.aurorasoft.kafka.replication.model.TransportableReplication.ReplicationType.SAVE;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public final class KafkaConsumerReplicationTest {

    @Mock
    private AbsServiceCRUD<Long, ?, TestPerson, ?> mockedService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private KafkaConsumerReplication<Long, TestPerson> consumer;

    @Before
    public void initializeConsumer() {
        consumer = new TestKafkaConsumerPersonReplication(mockedService, objectMapper);
    }

    @Test
    public void recordShouldBeMappedToReplication() {
        final String givenDtoJson = "{\"id\":255,\"name\":\"Vlad\",\"surname\":\"Zuev\",\"patronymic\":\"Sergeevich\"}";
        final GenericRecord givenGenericRecord = createGenericRecord(SAVE, givenDtoJson);

        final Replication<Long, TestPerson> actual = consumer.map(givenGenericRecord);
        final Replication<Long, TestPerson> expected = new SaveReplication<>(
                TestPerson.builder()
                        .id(255L)
                        .name("Vlad")
                        .surname("Zuev")
                        .patronymic("Sergeevich")
                        .build()
        );
        assertEquals(expected, actual);
    }

    @SuppressWarnings("SameParameterValue")
    private static GenericRecord createGenericRecord(final ReplicationType replicationType, final String dtoJson) {
        final GenericRecord record = mock(GenericRecord.class);
        when(record.get(same(Fields.type))).thenReturn(replicationType);
        when(record.get(same(Fields.dtoJson))).thenReturn(dtoJson);
        return record;
    }

    private static final class TestKafkaConsumerPersonReplication extends KafkaConsumerReplication<Long, TestPerson> {

        public TestKafkaConsumerPersonReplication(final AbsServiceCRUD<Long, ?, TestPerson, ?> service,
                                                  final ObjectMapper objectMapper) {
            super(service, objectMapper, TestPerson.class);
        }
    }

    @Value
    private static class TestPerson implements AbstractDto<Long> {
        Long id;
        String name;
        String surname;
        String patronymic;

        @Builder
        @JsonCreator
        public TestPerson(@JsonProperty("id") final Long id,
                          @JsonProperty("name") final String name,
                          @JsonProperty("surname") final String surname,
                          @JsonProperty("patronymic") final String patronymic) {
            this.id = id;
            this.name = name;
            this.surname = surname;
            this.patronymic = patronymic;
        }
    }
}
