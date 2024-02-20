package by.aurorasoft.kafka.replication.holder;

import by.aurorasoft.kafka.replication.producer.KafkaProducerReplication;
import by.nhorushko.crudgeneric.v2.service.AbsServiceRUD;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public final class KafkaProducerReplicationHolderTest {
    private static final String FIELD_NAME_PRODUCERS_BY_SERVICES = "producersByServices";

    @Test
    public void holderShouldBeCreated()
            throws Exception {
        final AbsServiceRUD<?, ?, ?, ?, ?> firstGivenService = mock(AbsServiceRUD.class);
        final KafkaProducerReplication<?, ?> firstGivenProducer = createProducer(firstGivenService);

        final AbsServiceRUD<?, ?, ?, ?, ?> secondGivenService = mock(AbsServiceRUD.class);
        final KafkaProducerReplication<?, ?> secondGivenProducer = createProducer(secondGivenService);

        final List<KafkaProducerReplication<?, ?>> givenProducers = List.of(firstGivenProducer, secondGivenProducer);

        final KafkaProducerReplicationHolder actual = new KafkaProducerReplicationHolder(givenProducers);
        final var actualProducersByServices = getProducersByServices(actual);
        final var expectedProducersByServices = Map.of(
                firstGivenService, firstGivenProducer,
                secondGivenService, secondGivenProducer
        );
        assertEquals(expectedProducersByServices, actualProducersByServices);
    }

    @Test(expected = IllegalStateException.class)
    public void holderShouldNotBeCreatedBecauseOfDuplicatedServices() {
        final AbsServiceRUD<?, ?, ?, ?, ?> givenService = mock(AbsServiceRUD.class);
        final KafkaProducerReplication<?, ?> firstGivenProducer = createProducer(givenService);
        final KafkaProducerReplication<?, ?> secondGivenProducer = createProducer(givenService);

        final List<KafkaProducerReplication<?, ?>> givenProducers = List.of(firstGivenProducer, secondGivenProducer);

        new KafkaProducerReplicationHolder(givenProducers);
    }

    @Test
    public void producerShouldBeFoundByService() {
        final AbsServiceRUD<?, ?, ?, ?, ?> givenService = mock(AbsServiceRUD.class);
        final KafkaProducerReplication<?, ?> givenProducer = createProducer(givenService);
        final List<KafkaProducerReplication<?, ?>> givenProducers = List.of(givenProducer);
        final KafkaProducerReplicationHolder givenHolder = new KafkaProducerReplicationHolder(givenProducers);

        final Optional<KafkaProducerReplication<?, ?>> optionalActual = givenHolder.findByService(givenService);
        assertTrue(optionalActual.isPresent());
        final KafkaProducerReplication<?, ?> actual = optionalActual.get();
        assertSame(givenProducer, actual);
    }

    @Test
    public void producerShouldNotBeFoundByService() {
        final AbsServiceRUD<?, ?, ?, ?, ?> firstGivenService = mock(AbsServiceRUD.class);

        final AbsServiceRUD<?, ?, ?, ?, ?> secondGivenService = mock(AbsServiceRUD.class);
        final KafkaProducerReplication<?, ?> givenProducer = createProducer(secondGivenService);
        final List<KafkaProducerReplication<?, ?>> givenProducers = List.of(givenProducer);
        final KafkaProducerReplicationHolder givenHolder = new KafkaProducerReplicationHolder(givenProducers);

        final Optional<KafkaProducerReplication<?, ?>> optionalActual = givenHolder.findByService(firstGivenService);
        assertTrue(optionalActual.isEmpty());
    }

    @SuppressWarnings("rawtypes")
    private static KafkaProducerReplication<?, ?> createProducer(final AbsServiceRUD service) {
        final KafkaProducerReplication producer = mock(KafkaProducerReplication.class);
        when(producer.getReplicatedService()).thenReturn(service);
        return producer;
    }

    @SuppressWarnings("unchecked")
    private static Map<AbsServiceRUD<?, ?, ?, ?, ?>, KafkaProducerReplication<?, ?>> getProducersByServices(
            final KafkaProducerReplicationHolder holder
    ) throws Exception {
        final Field field = KafkaProducerReplicationHolder.class.getDeclaredField(FIELD_NAME_PRODUCERS_BY_SERVICES);
        field.setAccessible(true);
        try {
            return (Map<AbsServiceRUD<?, ?, ?, ?, ?>, KafkaProducerReplication<?, ?>>) field.get(holder);
        } finally {
            field.setAccessible(false);
        }
    }
}
