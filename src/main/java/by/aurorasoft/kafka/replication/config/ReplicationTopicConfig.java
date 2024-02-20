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
@ConfigurationProperties("kafka.entity-replication.topic")
public class ReplicationTopicConfig {

    @NotNull
    @Min(1)
    @Max(10)
    private final Integer partitionCount;

    @NotNull
    @Min(1)
    @Max(10)
    private final Integer replicationFactor;
}
