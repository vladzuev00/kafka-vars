package by.aurorasoft.kafka.replication.topiccreator;

import by.aurorasoft.kafka.base.AbstractSpringBootTest;
import by.aurorasoft.kafka.replication.annotation.ReplicatedService;
import by.aurorasoft.kafka.replication.it.crud.entity.AbstractEntity;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.mapper.AbsMapperEntityDto;
import by.nhorushko.crudgeneric.v2.service.AbsServiceRUD;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.LongSerializer;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Import(
        {
                ReplicationTopicCreatorTest.TestFirstService.class,
                ReplicationTopicCreatorTest.TestSecondService.class,
                ReplicationTopicCreatorTest.TestThirdService.class,
        }
)
public final class ReplicationTopicCreatorTest extends AbstractSpringBootTest {

    @MockBean
    private KafkaAdmin mockedKafkaAdmin;

    @Captor
    private ArgumentCaptor<NewTopic> topicArgumentCaptor;

    @Test
    public void topicsShouldBeCreated() {
        verify(mockedKafkaAdmin, times(3)).createOrModifyTopics(topicArgumentCaptor.capture());

        final Set<NewTopic> actualCreatedTopics = new HashSet<>(topicArgumentCaptor.getAllValues());
        final Set<NewTopic> expectedCreatedTopics = Set.of(
                new NewTopic("first-topic", 1, (short) 1),
                new NewTopic("second-topic", 1, (short) 1),
                new NewTopic("third-topic", 1, (short) 1)
        );
        assertEquals(expectedCreatedTopics, actualCreatedTopics);
    }

    @ReplicatedService(topicName = "first-topic", keySerializer = LongSerializer.class)
    static class TestFirstService extends AbsServiceRUD<
            Long,
            AbstractEntity<Long>,
            AbstractDto<Long>,
            AbsMapperEntityDto<AbstractEntity<Long>, AbstractDto<Long>>,
            JpaRepository<AbstractEntity<Long>, Long>
            > {

        public TestFirstService() {
            super(null, null);
        }
    }

    @ReplicatedService(topicName = "second-topic", keySerializer = LongSerializer.class)
    static class TestSecondService extends AbsServiceRUD<
            Long,
            AbstractEntity<Long>,
            AbstractDto<Long>,
            AbsMapperEntityDto<AbstractEntity<Long>, AbstractDto<Long>>,
            JpaRepository<AbstractEntity<Long>, Long>
            > {

        public TestSecondService() {
            super(null, null);
        }
    }

    @ReplicatedService(topicName = "third-topic", keySerializer = LongSerializer.class)
    static class TestThirdService extends AbsServiceRUD<
            Long,
            AbstractEntity<Long>,
            AbstractDto<Long>,
            AbsMapperEntityDto<AbstractEntity<Long>, AbstractDto<Long>>,
            JpaRepository<AbstractEntity<Long>, Long>
            > {

        public TestThirdService() {
            super(null, null);
        }
    }
}
