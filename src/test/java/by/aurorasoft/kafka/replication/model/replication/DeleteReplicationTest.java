package by.aurorasoft.kafka.replication.model.replication;

import by.aurorasoft.kafka.replication.model.ReplicationType;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD;
import lombok.Value;
import org.junit.Test;

import static by.aurorasoft.kafka.replication.model.ReplicationType.DELETE;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public final class DeleteReplicationTest {

    @Test
    public void typeShouldBeGot() {
        final Replication<Long, TestDto> givenReplication = new DeleteReplication<>(null);

        final ReplicationType actual = givenReplication.getType();
        assertSame(DELETE, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void replicationShouldBeExecuted() {
        final Replication<Long, TestDto> givenReplication = new DeleteReplication<>(null);

        final Long givenId = 255L;
        final TestDto givenDto = new TestDto(givenId);

        final AbsServiceCRUD<Long, ?, TestDto, ?> givenService = mock(AbsServiceCRUD.class);

        givenReplication.execute(givenService, givenDto);

        verify(givenService, times(1)).delete(same(givenId));
    }

    @Value
    private static class TestDto implements AbstractDto<Long> {
        Long id;
    }
}
