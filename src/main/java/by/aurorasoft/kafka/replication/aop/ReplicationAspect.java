package by.aurorasoft.kafka.replication.aop;

import by.aurorasoft.kafka.replication.annotation.ReplicatedService;
import by.aurorasoft.kafka.replication.model.TransportableReplication;
import by.aurorasoft.kafka.replication.model.replication.DeleteReplication;
import by.aurorasoft.kafka.replication.model.replication.SaveReplication;
import by.aurorasoft.kafka.replication.model.replication.UpdateReplication;
import by.aurorasoft.kafka.replication.producer.KafkaProducerReplication;
import by.aurorasoft.kafka.serialize.AvroGenericRecordSerializer;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD;
import by.nhorushko.crudgeneric.v2.service.AbsServiceRUD;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.avro.reflect.ReflectData;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;
import static org.apache.kafka.clients.producer.ProducerConfig.*;
import static org.apache.kafka.clients.producer.ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG;

@Aspect
@Component
public class ReplicationAspect {
    private final Map<Class<?>, KafkaProducerReplication<?, ?>> producersByServiceTypes;
    private final ObjectMapper objectMapper;

    //TODO: inject producer factory instead
    public ReplicationAspect(final List<AbsServiceRUD<?, ?, ?, ?, ?>> rudServices,
                             final ObjectMapper objectMapper,
                             @Value("${spring.kafka.bootstrap-servers}") final String bootstrapAddress,
                             @Value("${kafka.entity-replication.producer.batch-size}") final int batchSize,
                             @Value("${kafka.entity-replication.producer.linger-ms}") final int lingerMs,
                             @Value("${kafka.entity-replication.producer.delivery-timeout-ms}") final int deliveryTimeoutMs) {
        this.objectMapper = objectMapper;
        producersByServiceTypes = createProducersByServiceTypes(rudServices, bootstrapAddress, batchSize, lingerMs, deliveryTimeoutMs);
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

    private Map<Class<?>, KafkaProducerReplication<?, ?>> createProducersByServiceTypes(
            final List<AbsServiceRUD<?, ?, ?, ?, ?>> rudServices,
            final String bootstrapAddress,
            final int batchSize,
            final int lingerMs,
            final int deliveryTimeoutMs
    ) {
        return rudServices.stream()
                .filter(service -> {
                    boolean replicatedService = service.getClass().isAnnotationPresent(ReplicatedService.class);
                    return replicatedService;
                })
                .collect(
                        toMap(
                                Object::getClass,
                                service -> {
                                    final ReplicatedService annotation = service.getClass().getDeclaredAnnotation(ReplicatedService.class);
                                    return new KafkaProducerReplication<>(annotation.topicName(), new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(Map.of(
                                            BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress,
                                            KEY_SERIALIZER_CLASS_CONFIG, annotation.keySerializer(),
                                            VALUE_SERIALIZER_CLASS_CONFIG, AvroGenericRecordSerializer.class,
                                            BATCH_SIZE_CONFIG, batchSize,
                                            LINGER_MS_CONFIG, lingerMs,
                                            DELIVERY_TIMEOUT_MS_CONFIG, deliveryTimeoutMs,
                                            "schema", TransportableReplication.class
                                    ))), ReflectData.get().getSchema(TransportableReplication.class), objectMapper);
                                }
                        )
                );
//                .filter(Objects::nonNull)
//                .map(annotation -> new KafkaProducerReplication<>(annotation.topicName(), new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(Map.of(
//                        BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress,
//                        KEY_SERIALIZER_CLASS_CONFIG, annotation.keySerializer(),
//                        VALUE_SERIALIZER_CLASS_CONFIG, AvroGenericRecordSerializer.class,
//                        BATCH_SIZE_CONFIG, batchSize,
//                        LINGER_MS_CONFIG, lingerMs,
//                        DELIVERY_TIMEOUT_MS_CONFIG, deliveryTimeoutMs,
//                        "schema", TransportableReplication.class
//                ))), ReflectData.get().getSchema(TransportableReplication.class), objectMapper))
//                .collect(toMap(Object::getClass, identity()));
    }

    private KafkaProducerReplication<?, ?> findProducer(final JoinPoint joinPoint) {
        final Class<?> serviceType = joinPoint.getTarget().getClass();
        return producersByServiceTypes.computeIfAbsent(serviceType, this::throwNoProducerException);
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
