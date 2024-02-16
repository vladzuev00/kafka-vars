package by.aurorasoft.kafka.replication.model;

import by.aurorasoft.kafka.replication.model.replication.DeleteReplication;
import by.aurorasoft.kafka.replication.model.replication.Replication;
import by.aurorasoft.kafka.replication.model.replication.SaveReplication;
import by.aurorasoft.kafka.replication.model.replication.UpdateReplication;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import lombok.Value;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static by.aurorasoft.kafka.replication.model.ReplicationType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public final class TransportableReplicationTest {
    private static final TestPerson GIVEN_DTO = new TestPerson(
            255L,
            "Vlad",
            "Zuev",
            "Sergeevich"
    );

    @ParameterizedTest
    @MethodSource("provideTypeAndExpected")
    public void replicationShouldBeCreated(final ReplicationType givenType, final Replication<Long, TestPerson> expected) {
        final Replication<Long, TestPerson> actual = givenType.createReplication(GIVEN_DTO);
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> provideTypeAndExpected() {
        return Stream.of(
                Arguments.of(SAVE, new SaveReplication<>(GIVEN_DTO)),
                Arguments.of(UPDATE, new UpdateReplication<>(GIVEN_DTO)),
                Arguments.of(DELETE, new DeleteReplication<>(GIVEN_DTO))
        );
    }

    @Value
    private static class TestPerson implements AbstractDto<Long> {
        Long id;
        String name;
        String surname;
        String patronymic;
    }
}
