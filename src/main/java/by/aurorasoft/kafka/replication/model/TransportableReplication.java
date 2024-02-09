package by.aurorasoft.kafka.replication.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.apache.avro.reflect.Nullable;

@Getter
@AllArgsConstructor
@Builder
@FieldNameConstants
public final class TransportableReplication {
    private final ReplicationType type;

    @Nullable
    private final Object entityId;

    @Nullable
    private final TransportableDto dto;

}
