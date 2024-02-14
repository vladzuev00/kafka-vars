package by.aurorasoft.kafka.base.kafka;

import by.aurorasoft.kafka.base.AbstractSpringBootTest;
import org.junit.ClassRule;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import static org.testcontainers.utility.DockerImageName.parse;

@ContextConfiguration(initializers = KafkaBootstrapAddressOverrider.class)
public abstract class AbstractKafkaContainerTest extends AbstractSpringBootTest {
    private static final String FULL_IMAGE_NAME = "confluentinc/cp-kafka:5.4.3";
    private static final DockerImageName DOCKER_IMAGE_NAME = parse(FULL_IMAGE_NAME);

    @ClassRule
    public static KafkaContainer kafkaContainer = new KafkaContainer(DOCKER_IMAGE_NAME);
}
