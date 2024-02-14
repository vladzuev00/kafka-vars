package by.aurorasoft.kafka.base.kafka;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;

import static by.aurorasoft.kafka.base.kafka.AbstractKafkaContainerTest.kafkaContainer;

public final class KafkaBootstrapAddressOverrider implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final String PROPERTY_KEY_BOOTSTRAP_SERVERS = "spring.kafka.bootstrap-servers";

    @Override
    public void initialize(final ConfigurableApplicationContext context) {
        TestPropertyValues.of(
                Map.of(
                        PROPERTY_KEY_BOOTSTRAP_SERVERS, kafkaContainer.getBootstrapServers()
                )
        ).applyTo(context.getEnvironment());
    }

}
