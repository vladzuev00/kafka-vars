package by.aurorasoft.kafka.replication.producer;

import by.aurorasoft.kafka.replication.annotation.ReplicatedService;
import by.aurorasoft.kafka.replication.config.ReplicationProducerConfig;
import by.aurorasoft.kafka.replication.serviceholder.ReplicatedServiceHolder;
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

import java.util.List;
import java.util.Map;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

@Component
public final class KafkaProducerReplicationFactory {
    private static final String PRODUCER_CONFIG_KEY_SCHEMA = "SCHEMA";

    private final ReplicatedServiceHolder replicatedServiceHolder;
    private final ObjectMapper objectMapper;
    private final ReplicationProducerConfig producerConfig;
    private final Schema schema;
    private final String bootstrapAddress;

    public KafkaProducerReplicationFactory(final ReplicatedServiceHolder replicatedServiceHolder,
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

    public List<? extends KafkaProducerReplication<?, ?>> create() {
        return replicatedServiceHolder.getServices()
                .stream()
                .map(this::createProducer)
                .toList();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private KafkaProducerReplication<?, ?> createProducer(final AbsServiceRUD<?, ?, ?, ?, ?> service) {
        final ReplicatedService annotation = service.getClass().getAnnotation(ReplicatedService.class);
        final Map<String, Object> configsByKeys = createProducerConfigsByKeys(annotation.keySerializer());
        final ProducerFactory producerFactory = new DefaultKafkaProducerFactory(configsByKeys);
        final KafkaTemplate kafkaTemplate = new KafkaTemplate(producerFactory);
        return new KafkaProducerReplication<>(annotation.topicName(), kafkaTemplate, schema, service, objectMapper);
    }

    private Map<String, Object> createProducerConfigsByKeys(final Class<? extends Serializer<?>> keySerializer) {
        return Map.of(
                BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress,
                KEY_SERIALIZER_CLASS_CONFIG, keySerializer,
                VALUE_SERIALIZER_CLASS_CONFIG, AvroGenericRecordSerializer.class,
                BATCH_SIZE_CONFIG, producerConfig.getBatchSize(),
                LINGER_MS_CONFIG, producerConfig.getLingerMs(),
                DELIVERY_TIMEOUT_MS_CONFIG, producerConfig.getDeliveryTimeoutMs(),
                PRODUCER_CONFIG_KEY_SCHEMA, schema
        );
    }
}