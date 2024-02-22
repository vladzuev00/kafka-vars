package by.aurorasoft.kafka.replication.producer;

import by.aurorasoft.kafka.replication.annotation.ReplicatedService;
import by.aurorasoft.kafka.replication.config.ReplicationProducerConfig;
import by.aurorasoft.kafka.replication.holder.KafkaProducerReplicationHolder;
import by.aurorasoft.kafka.replication.holder.ReplicatedServiceHolder;
import by.aurorasoft.kafka.serialize.AvroGenericRecordSerializer;
import by.nhorushko.crudgeneric.v2.service.AbsServiceRUD;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.avro.Schema;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;
import static org.apache.kafka.clients.producer.ProducerConfig.*;

@Component
public final class KafkaProducerReplicationHolderFactory {
    private static final String PRODUCER_CONFIG_KEY_SCHEMA = "SCHEMA";

    private final ReplicatedServiceHolder replicatedServiceHolder;
    private final ObjectMapper objectMapper;
    private final ReplicationProducerConfig producerConfig;
    private final Schema schema;
    private final String bootstrapAddress;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public KafkaProducerReplicationHolderFactory(final ReplicatedServiceHolder replicatedServiceHolder,
                                                 final ObjectMapper objectMapper,
                                                 final ReplicationProducerConfig producerConfig,
                                                 @Qualifier("replicationSchema") final Schema schema,
                                                 @Value("${spring.kafka.bootstrap-servers}") final String bootstrapAddress) {
        this.replicatedServiceHolder = replicatedServiceHolder;
        this.objectMapper = objectMapper;
        this.producerConfig = producerConfig;
        this.schema = schema;
        this.bootstrapAddress = bootstrapAddress;
    }

    public KafkaProducerReplicationHolder create() {
        return replicatedServiceHolder.getServices()
                .stream()
                .collect(collectingAndThen(toMap(identity(), this::createProducer), KafkaProducerReplicationHolder::new));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private KafkaProducerReplication<?, ?> createProducer(final AbsServiceRUD<?, ?, ?, ?, ?> service) {
        final ReplicatedService annotation = service.getClass().getAnnotation(ReplicatedService.class);
        final Map<String, Object> configsByKeys = createProducerConfigsByKeys(annotation.keySerializer());
        final ProducerFactory producerFactory = new DefaultKafkaProducerFactory(configsByKeys);
        final KafkaTemplate kafkaTemplate = new KafkaTemplate(producerFactory);
        return new KafkaProducerReplication<>(annotation.topicName(), kafkaTemplate, schema, objectMapper);
    }

    private Map<String, Object> createProducerConfigsByKeys(final Class<? extends Serializer<?>> keySerializerType) {
        return Map.of(
                BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress,
                KEY_SERIALIZER_CLASS_CONFIG, keySerializerType,
                VALUE_SERIALIZER_CLASS_CONFIG, AvroGenericRecordSerializer.class,
                BATCH_SIZE_CONFIG, producerConfig.getBatchSize(),
                LINGER_MS_CONFIG, producerConfig.getLingerMs(),
                DELIVERY_TIMEOUT_MS_CONFIG, producerConfig.getDeliveryTimeoutMs(),
                PRODUCER_CONFIG_KEY_SCHEMA, schema
        );
    }
}