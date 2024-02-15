package by.aurorasoft.kafka.replication.aop;

import by.aurorasoft.kafka.replication.annotation.ReplicatedService;
import by.aurorasoft.kafka.replication.model.replication.DeleteReplication;
import by.aurorasoft.kafka.replication.model.replication.SaveReplication;
import by.aurorasoft.kafka.replication.model.replication.UpdateReplication;
import by.aurorasoft.kafka.replication.producer.KafkaProducerReplication;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
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

    @SuppressWarnings({"rawtypes", "unchecked"})
    @AfterReturning(pointcut = "replicatedSave()", returning = "savedDto")
    public void replicateSave(final JoinPoint joinPoint, final AbstractDto savedDto) {
        findProducer(joinPoint).send(new SaveReplication(savedDto));
    }

    @SuppressWarnings("rawtypes")
    @AfterReturning(pointcut = "replicatedSaveAll()", returning = "savedDtos")
    public void replicateSaveAll(final JoinPoint joinPoint, final List<AbstractDto> savedDtos) {
        savedDtos.forEach(dto -> replicateSave(joinPoint, dto));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @AfterReturning(value = "replicatedUpdate()", returning = "updatedDto")
    public void replicateUpdate(final JoinPoint joinPoint, final AbstractDto updatedDto) {
        findProducer(joinPoint).send(new UpdateReplication<>(updatedDto));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Around("replicatedDeleteById()")
    public Object replicateDeleteById(final ProceedingJoinPoint joinPoint)
            throws Throwable {
        final Object entityId = joinPoint.getArgs()[0];
        final AbsServiceCRUD service = (AbsServiceCRUD) joinPoint.getTarget();
        final AbstractDto dto = service.getById(entityId);
        final Object result = joinPoint.proceed();
        findProducer(joinPoint).send(new DeleteReplication(dto));
        return result;
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
        return joinPoint.getTarget()
                .getClass()
                .getAnnotation(ReplicatedService.class)
                .replicationProducer();
    }

    private KafkaProducerReplication<?, ?> throwNoProducerException(final Class<?> producerType) {
        throw new NoReplicationProducerException("There is no replication producer for type %s".formatted(producerType));
    }

    @Pointcut("replicatedCrudService() && save()")
    private void replicatedSave() {

    }

    @Pointcut("replicatedCrudService() && saveAll()")
    private void replicatedSaveAll() {

    }

    @Pointcut("replicatedRudService() && (update() || updatePartial())")
    private void replicatedUpdate() {

    }

    @Pointcut("replicatedRudService() && deleteById()")
    private void replicatedDeleteById() {

    }

    @Pointcut("replicatedService() && rudService()")
    private void replicatedRudService() {

    }

    @Pointcut("replicatedService() && crudService()")
    private void replicatedCrudService() {

    }

    @Pointcut("@target(by.aurorasoft.kafka.replication.annotation.ReplicatedService)")
    private void replicatedService() {

    }

    @Pointcut("target(by.nhorushko.crudgeneric.v2.service.AbsServiceRUD)")
    private void rudService() {

    }

    @Pointcut("target(by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD)")
    private void crudService() {

    }

    @Pointcut("execution(public by.nhorushko.crudgeneric.v2.domain.AbstractDto+ *.save(by.nhorushko.crudgeneric.v2.domain.AbstractDto+))")
    private void save() {

    }

    @Pointcut("execution(public by.nhorushko.crudgeneric.v2.domain.AbstractDto+ *.saveAll(java.util.Collection))")
    private void saveAll() {

    }

    @Pointcut("execution(public by.nhorushko.crudgeneric.v2.domain.AbstractDto+ *.update(by.nhorushko.crudgeneric.v2.domain.AbstractDto+))")
    private void update() {

    }

    @Pointcut("execution(public by.nhorushko.crudgeneric.v2.domain.AbstractDto+ *.updatePartial(Object+, Object))")
    private void updatePartial() {

    }

    @Pointcut("execution(public void *.delete(Object+))")
    private void deleteById() {

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
