package by.aurorasoft.kafka.replication.config;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@RequiredArgsConstructor
@Getter
@Builder
@ConfigurationProperties("kafka.entity-replication.producer")
public class ReplicationProducerConfig {

    @NotNull
    @Min(1)
    @Max(10000)
    private final Integer batchSize;

    @NotNull
    @Min(1)
    @Max(10000)
    private final Integer lingerMs;

    @NotNull
    @Min(1)
    @Max(1000000)
    private final Integer deliveryTimeoutMs;
}
