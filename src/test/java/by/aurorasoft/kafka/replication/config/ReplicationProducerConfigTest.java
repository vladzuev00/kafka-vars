package by.aurorasoft.kafka.replication.config;

import by.aurorasoft.kafka.base.AbstractSpringBootTest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public final class ReplicationProducerConfigTest extends AbstractSpringBootTest {

    @Autowired
    private Validator validator;

    @Test
    public void configShouldBeValid() {
        final ReplicationProducerConfig givenConfig = ReplicationProducerConfig.builder()
                .batchSize(5)
                .lingerMs(5)
                .deliveryTimeoutMs(5)
                .build();

        final Set<ConstraintViolation<ReplicationProducerConfig>> constraintViolations = validator.validate(givenConfig);
        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    public void configShouldNotBeValidBecauseOfBatchSizeIsNull() {
        final ReplicationProducerConfig givenConfig = ReplicationProducerConfig.builder()
                .lingerMs(5)
                .deliveryTimeoutMs(5)
                .build();

        final Set<ConstraintViolation<ReplicationProducerConfig>> constraintViolations = validator.validate(givenConfig);
        assertEquals(1, constraintViolations.size());
        assertEquals("не должно равняться null", findFirstMessage(constraintViolations));
    }

    @Test
    public void configShouldNotBeValidBecauseOfBatchSizeIsLessThanMinimalAllowable() {
        final ReplicationProducerConfig givenConfig = ReplicationProducerConfig.builder()
                .batchSize(0)
                .lingerMs(5)
                .deliveryTimeoutMs(5)
                .build();

        final Set<ConstraintViolation<ReplicationProducerConfig>> constraintViolations = validator.validate(givenConfig);
        assertEquals(1, constraintViolations.size());
        assertEquals("должно быть не меньше 1", findFirstMessage(constraintViolations));
    }

    @Test
    public void configShouldNotBeValidBecauseOfBatchSizeIsMoreThanMaximalAllowable() {
        final ReplicationProducerConfig givenConfig = ReplicationProducerConfig.builder()
                .batchSize(100001)
                .lingerMs(5)
                .deliveryTimeoutMs(5)
                .build();

        final Set<ConstraintViolation<ReplicationProducerConfig>> constraintViolations = validator.validate(givenConfig);
        assertEquals(1, constraintViolations.size());
        assertEquals("должно быть не больше 10000", findFirstMessage(constraintViolations));
    }

    @Test
    public void configShouldNotBeValidBecauseOfLingerMsIsNull() {
        final ReplicationProducerConfig givenConfig = ReplicationProducerConfig.builder()
                .batchSize(5)
                .deliveryTimeoutMs(5)
                .build();

        final Set<ConstraintViolation<ReplicationProducerConfig>> constraintViolations = validator.validate(givenConfig);
        assertEquals(1, constraintViolations.size());
        assertEquals("не должно равняться null", findFirstMessage(constraintViolations));
    }

    @Test
    public void configShouldNotBeValidBecauseOfLingerMsIsLessThanMinimalAllowable() {
        final ReplicationProducerConfig givenConfig = ReplicationProducerConfig.builder()
                .batchSize(5)
                .lingerMs(0)
                .deliveryTimeoutMs(5)
                .build();

        final Set<ConstraintViolation<ReplicationProducerConfig>> constraintViolations = validator.validate(givenConfig);
        assertEquals(1, constraintViolations.size());
        assertEquals("должно быть не меньше 1", findFirstMessage(constraintViolations));
    }

    @Test
    public void configShouldNotBeValidBecauseOfLingerMsIsMoreThanMinimalAllowable() {
        final ReplicationProducerConfig givenConfig = ReplicationProducerConfig.builder()
                .batchSize(5)
                .lingerMs(10001)
                .deliveryTimeoutMs(5)
                .build();

        final Set<ConstraintViolation<ReplicationProducerConfig>> constraintViolations = validator.validate(givenConfig);
        assertEquals(1, constraintViolations.size());
        assertEquals("должно быть не больше 10000", findFirstMessage(constraintViolations));
    }

    @Test
    public void configShouldNotBeValidBecauseOfDeliveryTimeoutMsIsNull() {
        final ReplicationProducerConfig givenConfig = ReplicationProducerConfig.builder()
                .batchSize(5)
                .lingerMs(5)
                .build();

        final Set<ConstraintViolation<ReplicationProducerConfig>> constraintViolations = validator.validate(givenConfig);
        assertEquals(1, constraintViolations.size());
        assertEquals("не должно равняться null", findFirstMessage(constraintViolations));
    }

    @Test
    public void configShouldNotBeValidBecauseOfDeliveryTimeoutMsIsLessThanMinimalAllowable() {
        final ReplicationProducerConfig givenConfig = ReplicationProducerConfig.builder()
                .batchSize(5)
                .lingerMs(5)
                .deliveryTimeoutMs(0)
                .build();

        final Set<ConstraintViolation<ReplicationProducerConfig>> constraintViolations = validator.validate(givenConfig);
        assertEquals(1, constraintViolations.size());
        assertEquals("должно быть не меньше 1", findFirstMessage(constraintViolations));
    }

    @Test
    public void configShouldNotBeValidBecauseOfDeliveryTimeoutMsIsMoreThanMaximalAllowable() {
        final ReplicationProducerConfig givenConfig = ReplicationProducerConfig.builder()
                .batchSize(5)
                .lingerMs(5)
                .deliveryTimeoutMs(1000001)
                .build();

        final Set<ConstraintViolation<ReplicationProducerConfig>> constraintViolations = validator.validate(givenConfig);
        assertEquals(1, constraintViolations.size());
        assertEquals("должно быть не больше 1000000", findFirstMessage(constraintViolations));
    }

    private static String findFirstMessage(final Set<ConstraintViolation<ReplicationProducerConfig>> violations) {
        return violations.iterator().next().getMessage();
    }
}
