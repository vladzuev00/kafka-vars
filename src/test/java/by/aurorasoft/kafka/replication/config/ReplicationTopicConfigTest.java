package by.aurorasoft.kafka.replication.config;

import by.aurorasoft.kafka.base.AbstractSpringBootTest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public final class ReplicationTopicConfigTest extends AbstractSpringBootTest {

    @Autowired
    private Validator validator;

    @Test
    public void configShouldBeValid() {
        final ReplicationTopicConfig givenConfig = new ReplicationTopicConfig(5, 5);

        final Set<ConstraintViolation<ReplicationTopicConfig>> constraintViolations = validator.validate(givenConfig);
        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    public void configShouldNotBeValidBecauseOfPartitionCountIsNull() {
        final ReplicationTopicConfig givenConfig = ReplicationTopicConfig.builder()
                .replicationFactor(5)
                .build();

        final Set<ConstraintViolation<ReplicationTopicConfig>> constraintViolations = validator.validate(givenConfig);
        assertEquals(1, constraintViolations.size());
        assertEquals("не должно равняться null", findFirstMessage(constraintViolations));
    }

    @Test
    public void configShouldNotBeValidBecauseOfPartitionCountIsLessThanMinimalAllowable() {
        final ReplicationTopicConfig givenConfig = new ReplicationTopicConfig(0, 5);

        final Set<ConstraintViolation<ReplicationTopicConfig>> constraintViolations = validator.validate(givenConfig);
        assertEquals(1, constraintViolations.size());
        assertEquals("должно быть не меньше 1", findFirstMessage(constraintViolations));
    }

    @Test
    public void configShouldNotBeValidBecauseOfPartitionCountIsMoreThanMaximalAllowable() {
        final ReplicationTopicConfig givenConfig = new ReplicationTopicConfig(11, 5);

        final Set<ConstraintViolation<ReplicationTopicConfig>> constraintViolations = validator.validate(givenConfig);
        assertEquals(1, constraintViolations.size());
        assertEquals("должно быть не больше 10", findFirstMessage(constraintViolations));
    }

    @Test
    public void configShouldNotBeValidBecauseOfReplicationFactorIsNull() {
        final ReplicationTopicConfig givenConfig = ReplicationTopicConfig.builder()
                .partitionCount(5)
                .build();

        final Set<ConstraintViolation<ReplicationTopicConfig>> constraintViolations = validator.validate(givenConfig);
        assertEquals(1, constraintViolations.size());
        assertEquals("не должно равняться null", findFirstMessage(constraintViolations));
    }

    @Test
    public void configShouldNotBeValidBecauseOfReplicationFactorIsLessThanMinimalAllowable() {
        final ReplicationTopicConfig givenConfig = new ReplicationTopicConfig(5, 0);

        final Set<ConstraintViolation<ReplicationTopicConfig>> constraintViolations = validator.validate(givenConfig);
        assertEquals(1, constraintViolations.size());
        assertEquals("должно быть не меньше 1", findFirstMessage(constraintViolations));
    }

    @Test
    public void configShouldNotBeValidBecauseOfReplicationFactorIsLessThanMaximalAllowable() {
        final ReplicationTopicConfig givenConfig = new ReplicationTopicConfig(5, 11);

        final Set<ConstraintViolation<ReplicationTopicConfig>> constraintViolations = validator.validate(givenConfig);
        assertEquals(1, constraintViolations.size());
        assertEquals("должно быть не больше 10", findFirstMessage(constraintViolations));
    }

    private static String findFirstMessage(final Set<ConstraintViolation<ReplicationTopicConfig>> violations) {
        return violations.iterator().next().getMessage();
    }
}
