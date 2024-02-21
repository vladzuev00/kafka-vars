package by.aurorasoft.kafka.replication.producer;

import by.aurorasoft.kafka.replication.model.TransportableReplication;
import by.aurorasoft.kafka.replication.model.replication.Replication;
import by.aurorasoft.kafka.replication.model.replication.SaveReplication;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Value;
import org.junit.Test;

import static by.aurorasoft.kafka.replication.model.ReplicationType.SAVE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public final class KafkaProducerReplicationTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final KafkaProducerReplication<Long, TestDto> producer = new KafkaProducerReplication<>(
            null,
            null,
            null,
            objectMapper
    );

    @Test
    public void topicKeyShouldBeFound() {
        final Long givenId = 255L;
        final TestDto givenDto = new TestDto(givenId);
        final Replication<Long, TestDto> givenReplication = new SaveReplication<>(givenDto);

        final Long actual = producer.getTopicKey(givenReplication);
        assertSame(givenId, actual);
    }

    @Test
    public void modelShouldBeConvertedToTransportable() {
        final TestDto givenDto = new TestDto(255L);
        final Replication<Long, TestDto> givenReplication = new SaveReplication<>(givenDto);

        final TransportableReplication actual = producer.convertModelToTransportable(givenReplication);
        final TransportableReplication expected = new TransportableReplication(SAVE, "{\"id\":255}");
        assertEquals(expected, actual);
    }

    @Value
    private static class TestDto implements AbstractDto<Long> {
        Long id;

        @JsonCreator
        public TestDto(@JsonProperty("id") final Long id) {
            this.id = id;
        }
    }
}
