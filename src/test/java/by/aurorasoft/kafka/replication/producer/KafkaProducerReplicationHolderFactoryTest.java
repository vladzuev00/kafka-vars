package by.aurorasoft.kafka.replication.producer;

import by.aurorasoft.kafka.replication.annotation.ReplicatedService;
import by.aurorasoft.kafka.replication.config.ReplicationProducerConfig;
import by.aurorasoft.kafka.replication.holder.KafkaProducerReplicationHolder;
import by.aurorasoft.kafka.replication.holder.ReplicatedServiceHolder;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.domain.AbstractEntity;
import by.nhorushko.crudgeneric.v2.mapper.AbsMapperEntityDto;
import by.nhorushko.crudgeneric.v2.service.AbsServiceRUD;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.avro.Schema;
import org.apache.kafka.common.serialization.LongSerializer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public final class KafkaProducerReplicationHolderFactoryTest {
    private static final String FIELD_NAME_HOLDER_PRODUCERS_BY_SERVICES = "producersByServices";

    private static final String GIVEN_BOOTSTRAP_ADDRESS = "127.0.0.1:9092";

    @Mock
    private ReplicatedServiceHolder mockedReplicatedServiceHolder;

    @Mock
    private ObjectMapper mockedObjectMapper;

    @Mock
    private ReplicationProducerConfig mockedProducerConfig;

    @Mock
    private Schema mockedSchema;

    private KafkaProducerReplicationHolderFactory factory;

    @Before
    public void initializeFactory() {
        factory = new KafkaProducerReplicationHolderFactory(
                mockedReplicatedServiceHolder,
                mockedObjectMapper,
                mockedProducerConfig,
                mockedSchema,
                GIVEN_BOOTSTRAP_ADDRESS
        );
    }

    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void replicationProducersShouldBeCreated() {
        final TestFirstService firstGivenService = new TestFirstService();
        final TestSecondService secondGivenService = new TestSecondService();
        final TestThirdService thirdGivenService = new TestThirdService();
        final List givenServices = List.of(firstGivenService, secondGivenService, thirdGivenService);

        final Integer givenProducerBatchSize = 5;
        final Integer givenProducerLingerMs = 10;
        final Integer givenDeliveryTimeoutMs = 15;

        when(mockedReplicatedServiceHolder.getServices()).thenReturn(givenServices);
        when(mockedProducerConfig.getBatchSize()).thenReturn(givenProducerBatchSize);
        when(mockedProducerConfig.getLingerMs()).thenReturn(givenProducerLingerMs);
        when(mockedProducerConfig.getDeliveryTimeoutMs()).thenReturn(givenDeliveryTimeoutMs);

        final KafkaProducerReplicationHolder actual = factory.create();

    }

    private static

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
