package by.aurorasoft.kafka.replication.aop;

import by.aurorasoft.kafka.replication.replicator.ServiceReplicator;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
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
    private final Map<Class<?>, ServiceReplicator> replicatorsByServiceTypes;

    public ReplicationAspect(final List<ServiceReplicator> replicators) {
        replicatorsByServiceTypes = createReplicatorsByServiceTypes(replicators);
    }

    @AfterReturning(pointcut = "replicatedService() && replicatedSave()", returning = "savedDto")
    public void replicateSave(final JoinPoint joinPoint, final AbstractDto<?> savedDto) {
        findReplicator(joinPoint).replicateSave(savedDto);
    }

    @AfterReturning(pointcut = "replicatedService() && replicatedSaveAll()", returning = "savedDtos")
    public void replicateSaveAll(final JoinPoint joinPoint, final List<AbstractDto<?>> savedDtos) {
        findReplicator(joinPoint).replicateSaveAll(savedDtos);
    }

    @AfterReturning(value = "replicatedService() && replicatedUpdate()", returning = "updatedDto")
    public void replicateUpdate(final JoinPoint joinPoint, final AbstractDto<?> updatedDto) {
        findReplicator(joinPoint).replicateUpdate(updatedDto);
    }

    @AfterReturning("replicatedService() && replicatedDelete()")
    public void replicateDelete(final JoinPoint joinPoint) {
        final Object entityId = joinPoint.getArgs()[0];
        findReplicator(joinPoint).replicateDelete(entityId);
    }

    private static Map<Class<?>, ServiceReplicator> createReplicatorsByServiceTypes(
            final List<ServiceReplicator> replicators
    ) {
        return replicators.stream()
                .collect(
                        toMap(
                                ServiceReplicator::getReplicatedService,
                                identity()
                        )
                );
    }

    private ServiceReplicator findReplicator(final JoinPoint joinPoint) {
        return replicatorsByServiceTypes.get(joinPoint.getTarget().getClass());
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
}
