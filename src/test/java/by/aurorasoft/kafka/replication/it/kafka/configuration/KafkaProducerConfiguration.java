package by.aurorasoft.kafka.replication.it.kafka.configuration;

import by.aurorasoft.kafka.serialize.AvroGenericRecordSerializer;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Map;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

@Configuration
@Import(SchemaConfiguration.class)
public class KafkaProducerConfiguration {
    private static final String SCHEMA_PROPERTY_NAME = "SCHEMA";

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public KafkaTemplate<Long, GenericRecord> kafkaTemplateSyncPerson(
            @Value("${kafka.topic.sync-person.producer.batch-size}") final int batchSize,
            @Value("${kafka.topic.sync-person.producer.linger-ms}") final int lingerMs,
            @Value("${kafka.topic.sync-person.producer.delivery-timeout-ms}") final int deliveryTimeoutMs,
            @Qualifier("transportablePersonReplicationSchema") final Schema schema
    ) {
        return createKafkaTemplate(batchSize, lingerMs, deliveryTimeoutMs, schema);
    }

    private <K, V> KafkaTemplate<K, V> createKafkaTemplate(final int batchSize,
                                                           final int lingerMs,
                                                           final int deliveryTimeoutMs,
                                                           final Schema schema) {
        final Map<String, Object> propertiesByNames = Map.of(
                BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress,
                KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class,
                VALUE_SERIALIZER_CLASS_CONFIG, AvroGenericRecordSerializer.class,
                BATCH_SIZE_CONFIG, batchSize,
                LINGER_MS_CONFIG, lingerMs,
                DELIVERY_TIMEOUT_MS_CONFIG, deliveryTimeoutMs,
                SCHEMA_PROPERTY_NAME, schema
        );
        final DefaultKafkaProducerFactory<K, V> producerFactory = new DefaultKafkaProducerFactory<>(propertiesByNames);
        return new KafkaTemplate<>(producerFactory);
    }
}
