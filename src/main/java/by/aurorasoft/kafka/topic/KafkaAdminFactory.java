package by.aurorasoft.kafka.topic;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

public class KafkaAdminFactory {

    /**
     * @EnableKafka
     * @Configuration
     * public class KafkaAdminConfig {
     *
     *     @Value(value = "${spring.kafka.bootstrap-servers}")
     *     private String bootstrapAddress;
     *
     *     @Bean
     *     public KafkaAdmin kafkaAdmin() {
     *         return KafkaAdminFactory.create(bootstrapAddress);
     *     }
     * }
     *
     *
     */
    public static KafkaAdmin create(String bootstrapAddress) {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }
}
