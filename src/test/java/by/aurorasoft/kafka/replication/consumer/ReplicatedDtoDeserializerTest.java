package by.aurorasoft.kafka.replication.consumer;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Value;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class ReplicatedDtoDeserializerTest {
    private final ReplicatedDtoDeserializer<TestPerson> personDeserializer = new ReplicatedDtoDeserializer<>(
            new ObjectMapper(),
            TestPerson.class
    );

    @Test
    public void dtoShouldBeDeserialized() {
        final String givenJson = "{\"id\":255,\"name\":\"Vlad\",\"surname\":\"Zuev\",\"patronymic\":\"Sergeevich\"}";

        final TestPerson actual = personDeserializer.deserializeDto(givenJson);
        final TestPerson expected = TestPerson.builder()
                .id(255L)
                .name("Vlad")
                .surname("Zuev")
                .patronymic("Sergeevich")
                .build();
        assertEquals(expected, actual);
    }

    @Test(expected = ReplicatedDtoDeserializationException.class)
    public void dtoShouldNotBeDeserialized() {
        final String givenJson = "{\"unknown\":255,\"name\":\"Vlad\",\"surname\":\"Zuev\",\"patronymic\":\"Sergeevich\"}";

        personDeserializer.deserializeDto(givenJson);
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
