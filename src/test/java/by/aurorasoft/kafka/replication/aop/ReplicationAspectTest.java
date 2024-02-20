package by.aurorasoft.kafka.replication.aop;

import by.aurorasoft.kafka.base.AbstractSpringBootTest;
import by.aurorasoft.kafka.replication.annotation.ReplicatedService;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.domain.AbstractEntity;
import by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD;
import lombok.Value;
import org.apache.kafka.common.serialization.LongSerializer;
import org.junit.Test;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.JpaRepository;

@Import(ReplicationAspectTest.TestCRUDService.class)
public final class ReplicationAspectTest extends AbstractSpringBootTest {

    @Test
    public void aspectShouldBeCreated() {

    }

    @Value
    private static class TestDto implements AbstractDto<Long> {
        Long id;
    }

    @ReplicatedService(topicName = "sync-dto", keySerializer = LongSerializer.class)
    static class TestCRUDService extends AbsServiceCRUD<
            Long,
            AbstractEntity<Long>,
            TestDto,
            JpaRepository<AbstractEntity<Long>, Long>
            > {

        public TestCRUDService() {
            super(null, null);
        }
    }
}
