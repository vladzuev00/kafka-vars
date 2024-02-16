package by.aurorasoft.kafka.replication.aop;

import by.aurorasoft.kafka.base.AbstractSpringBootTest;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import lombok.Value;
import org.junit.Test;

public final class ReplicationAspectTest extends AbstractSpringBootTest {

    @Test
    public void aspectShouldBeCreated() {
        throw new RuntimeException();
    }



    @Value
    private static class TestPerson implements AbstractDto<Long> {
        Long id;
        String name;
        String surname;
        String patronymic;
    }
}
