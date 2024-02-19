package by.aurorasoft.kafka.replication.topiccreator;

import by.aurorasoft.kafka.base.AbstractSpringBootTest;
import by.aurorasoft.kafka.replication.annotation.ReplicatedService;
import by.aurorasoft.kafka.replication.it.crud.entity.AbstractEntity;
import by.aurorasoft.kafka.replication.serviceholder.ReplicatedServiceHolder;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.mapper.AbsMapperEntityDto;
import by.nhorushko.crudgeneric.v2.service.AbsServiceRUD;
import org.apache.kafka.common.serialization.LongSerializer;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.test.context.junit.jupiter.SpringExtension;

//@ExtendWith(SpringExtension.class)
//@Import({SomeSpringBean.class})
public final class ReplicationTopicCreatorTest {

    @MockBean
    private ReplicatedServiceHolder mockedReplicatedServiceHolder;

    @MockBean
    private KafkaAdmin mockedKafkaAdmin;

    @Test
    public void topicsShouldBeCreated() {

    }


    @ReplicatedService(topicName = "first-topic", keySerializer = LongSerializer.class)
    static final class TestFirstService extends AbsServiceRUD<
            Long,
            AbstractEntity<Long>,
            AbstractDto<Long>,
            AbsMapperEntityDto<AbstractEntity<Long>, AbstractDto<Long>>,
            JpaRepository<AbstractEntity<Long>, Long>
            > {

        public TestFirstService(final AbsMapperEntityDto<AbstractEntity<Long>, AbstractDto<Long>> mapper,
                                final JpaRepository<AbstractEntity<Long>, Long> repository) {
            super(mapper, repository);
        }
    }

    @ReplicatedService(topicName = "second-topic", keySerializer = LongSerializer.class)
    static final class TestSecondService extends AbsServiceRUD<
            Long,
            AbstractEntity<Long>,
            AbstractDto<Long>,
            AbsMapperEntityDto<AbstractEntity<Long>, AbstractDto<Long>>,
            JpaRepository<AbstractEntity<Long>, Long>
            > {

        public TestSecondService(final AbsMapperEntityDto<AbstractEntity<Long>, AbstractDto<Long>> mapper,
                                 final JpaRepository<AbstractEntity<Long>, Long> repository) {
            super(mapper, repository);
        }
    }

    @ReplicatedService(topicName = "third-topic", keySerializer = LongSerializer.class)
    static final class TestThirdService extends AbsServiceRUD<
            Long,
            AbstractEntity<Long>,
            AbstractDto<Long>,
            AbsMapperEntityDto<AbstractEntity<Long>, AbstractDto<Long>>,
            JpaRepository<AbstractEntity<Long>, Long>
            > {

        public TestThirdService(final AbsMapperEntityDto<AbstractEntity<Long>, AbstractDto<Long>> mapper,
                                final JpaRepository<AbstractEntity<Long>, Long> repository) {
            super(mapper, repository);
        }
    }
}
