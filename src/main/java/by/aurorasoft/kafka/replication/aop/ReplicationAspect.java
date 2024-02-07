package by.aurorasoft.kafka.replication.aop;

import by.aurorasoft.kafka.replication.annotation.ReplicatedService;
import by.aurorasoft.kafka.replication.model.replication.UpdateReplication;
import by.aurorasoft.kafka.replication.producer.KafkaProducerReplication;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.lang.String.format;

@Aspect
@Component
@RequiredArgsConstructor
public class ReplicationAspect {
    private final List<KafkaProducerReplication<?, ?>> producers;

    @AfterReturning(pointcut = "replicatedService() && replicatedSave()", returning = "savedDto")
    public void replicateSave(final JoinPoint joinPoint, final AbstractDto<?> savedDto) {
        throw new UnsupportedOperationException();
    }

    @AfterReturning(value = "replicatedService() && replicatedUpdate()", returning = "updatedDto")
    public void replicateUpdate(final JoinPoint joinPoint, final AbstractDto<?> updatedDto) {
        findProducer(joinPoint).send(new UpdateReplication(updatedDto));
    }

    @AfterReturning("replicatedService() && replicatedDelete()")
    public void replicateDelete(final JoinPoint ignoredJoinPoint) {
        throw new NotImplementedException();
    }

    private KafkaProducerReplication<?, ?> findProducer(final JoinPoint joinPoint) {
        final Class<? extends KafkaProducerReplication<?, ?>> type = findProducerType(joinPoint);
        return producers.stream()
                .filter(producer -> producer.getClass() == type)
                .findAny()
                .orElseThrow(() -> new NoReplicationProducerException(format("Producer '%s' wasn't found", type)));
    }

    private Class<? extends KafkaProducerReplication<?, ?>> findProducerType(final JoinPoint joinPoint) {
        return ((MethodSignature) joinPoint.getSignature())
                .getMethod()
                .getDeclaringClass()
                .getAnnotation(ReplicatedService.class)
                .replicationProducer();
    }

    @Pointcut("within(@by.aurorasoft.kafka.replication.annotation.ReplicatedService *)")
    private void replicatedService() {

    }

    @Pointcut("@annotation(by.aurorasoft.kafka.replication.annotation.ReplicatedSave)")
    private void replicatedSave() {

    }

    @Pointcut("@annotation(by.aurorasoft.kafka.replication.annotation.ReplicatedUpdate)")
    private void replicatedUpdate() {

    }

    @Pointcut("@annotation(by.aurorasoft.kafka.replication.annotation.ReplicatedDelete)")
    private void replicatedDelete() {

    }

    private static final class NoReplicationProducerException extends RuntimeException {

        @SuppressWarnings("unused")
        public NoReplicationProducerException() {

        }

        public NoReplicationProducerException(final String description) {
            super(description);
        }

        @SuppressWarnings("unused")
        public NoReplicationProducerException(final Exception cause) {
            super(cause);
        }

        @SuppressWarnings("unused")
        public NoReplicationProducerException(final String description, final Exception cause) {
            super(description, cause);
        }
    }
}
