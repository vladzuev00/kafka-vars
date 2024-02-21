package by.aurorasoft.kafka.replication.holder;

import by.aurorasoft.kafka.replication.producer.KafkaProducerReplication;
import by.nhorushko.crudgeneric.v2.service.AbsServiceRUD;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public final class KafkaProducerReplicationHolderTest {

    @Test
    public void producerShouldBeFoundByService() {
        final AbsServiceRUD<?, ?, ?, ?, ?> givenService = mock(AbsServiceRUD.class);
        final KafkaProducerReplication<?, ?> givenProducer = mock(KafkaProducerReplication.class);
        final Map<AbsServiceRUD<?, ?, ?, ?, ?>, KafkaProducerReplication<?, ?>> givenProducersByServices = Map.of(
                givenService,
                givenProducer
        );
        final KafkaProducerReplicationHolder givenHolder = new KafkaProducerReplicationHolder(givenProducersByServices);

        final Optional<KafkaProducerReplication<?, ?>> optionalActual = givenHolder.findByService(givenService);
        assertTrue(optionalActual.isPresent());
        final KafkaProducerReplication<?, ?> actual = optionalActual.get();
        assertSame(givenProducer, actual);
    }

    @Test
    public void producerShouldNotBeFoundByService() {
        final AbsServiceRUD<?, ?, ?, ?, ?> firstGivenService = mock(AbsServiceRUD.class);
        final AbsServiceRUD<?, ?, ?, ?, ?> secondGivenService = mock(AbsServiceRUD.class);
        final KafkaProducerReplication<?, ?> givenProducer = mock(KafkaProducerReplication.class);
        final Map<AbsServiceRUD<?, ?, ?, ?, ?>, KafkaProducerReplication<?, ?>> givenProducersByServices = Map.of(
                firstGivenService,
                givenProducer
        );
        final KafkaProducerReplicationHolder givenHolder = new KafkaProducerReplicationHolder(givenProducersByServices);

        final Optional<KafkaProducerReplication<?, ?>> optionalActual = givenHolder.findByService(secondGivenService);
        assertTrue(optionalActual.isEmpty());
    }
}
