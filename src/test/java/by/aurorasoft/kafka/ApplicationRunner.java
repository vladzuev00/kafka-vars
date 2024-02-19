package by.aurorasoft.kafka;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableConfigurationProperties
public class ApplicationRunner {
    public static void main(final String... args) {
        run(ApplicationRunner.class, args);
    }
}
