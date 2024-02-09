package by.aurorasoft.kafka.replication.aop;

import by.aurorasoft.kafka.replication.annotation.ReplicatedService;
import by.aurorasoft.kafka.replication.model.replication.DeleteReplication;
import by.aurorasoft.kafka.replication.model.replication.SaveReplication;
import by.aurorasoft.kafka.replication.model.replication.UpdateReplication;
import by.aurorasoft.kafka.replication.producer.KafkaProducerReplication;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Aspect
@Component
public class ReplicationAspect {
    private final Map<Class<?>, KafkaProducerReplication<?, ?>> producersByTypes;

    public ReplicationAspect(final List<KafkaProducerReplication<?, ?>> producers) {
        producersByTypes = createProducersByTypes(producers);
    }

    @AfterReturning(pointcut = "replicatedService() && replicatedSave()", returning = "savedDto")
    public void replicateSave(final JoinPoint joinPoint, final AbstractDto<?> savedDto) {
        findProducer(joinPoint).send(new SaveReplication(savedDto));
    }

    @AfterReturning(pointcut = "replicatedService() && replicatedSaveAll()", returning = "savedDtos")
    public void replicateSaveAll(final JoinPoint joinPoint, final List<AbstractDto<?>> savedDtos) {
        savedDtos.forEach(dto -> replicateSave(joinPoint, dto));
    }

    @AfterReturning(value = "replicatedService() && replicatedUpdate()", returning = "updatedDto")
    public void replicateUpdate(final JoinPoint joinPoint, final AbstractDto<?> updatedDto) {
        findProducer(joinPoint).send(new UpdateReplication(updatedDto));
    }

    @AfterReturning("replicatedService() && replicatedDelete()")
    public void replicateDelete(final JoinPoint joinPoint) {
        final Object entityId = joinPoint.getArgs()[0];
        findProducer(joinPoint).send(new DeleteReplication(entityId));
    }

    private static Map<Class<?>, KafkaProducerReplication<?, ?>> createProducersByTypes(
            final List<KafkaProducerReplication<?, ?>> producers
    ) {
        return producers.stream()
                .collect(
                        toMap(
                                KafkaProducerReplication::getClass,
                                identity()
                        )
                );
    }

    private KafkaProducerReplication<?, ?> findProducer(final JoinPoint joinPoint) {
        final Class<?> producerType = findProducerType(joinPoint);
        return producersByTypes.computeIfAbsent(producerType, this::throwNoProducerException);
    }

    private static Class<?> findProducerType(final JoinPoint joinPoint) {
        return ((MethodSignature) joinPoint.getSignature())
                .getMethod()
                .getAnnotation(ReplicatedService.class)
                .replicationProducer();
    }

    private KafkaProducerReplication<?, ?> throwNoProducerException(final Class<?> producerType) {
        throw new NoReplicationProducerException("There is no replication producer for type %s".formatted(producerType));
    }

    @Pointcut("within(@by.aurorasoft.kafka.replication.annotation.ReplicatedService *)")
    private void replicatedService() {

    }

    @Pointcut("@annotation(by.aurorasoft.kafka.replication.annotation.ReplicatedSave)")
    private void replicatedSave() {

    }

    @Pointcut("@annotation(by.aurorasoft.kafka.replication.annotation.ReplicatedSaveAll)")
    private void replicatedSaveAll() {

    }

    @Pointcut("@annotation(by.aurorasoft.kafka.replication.annotation.ReplicatedUpdate)")
    private void replicatedUpdate() {

    }

    @Pointcut("@annotation(by.aurorasoft.kafka.replication.annotation.ReplicatedDelete)")
    private void replicatedDelete() {

    }

    static final class NoReplicationProducerException extends RuntimeException {

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
