package by.aurorasoft.kafka.replication.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.apache.avro.reflect.Nullable;

import static by.aurorasoft.kafka.replication.model.ReplicationType.*;
import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
@Builder
@FieldNameConstants
public final class TransportableReplication {
    private final ReplicationType type;

    @Nullable
    private final Object entityId;

    @Nullable
    private final String dtoJsonView;

    public static TransportableReplication createSaveReplication(final String dtoJsonView) {
        return TransportableReplication.builder()
                .type(SAVE)
                .dtoJsonView(dtoJsonView)
                .build();
    }

    public static TransportableReplication createUpdateReplication(final String dtoJsonView) {
        return TransportableReplication.builder()
                .type(UPDATE)
                .dtoJsonView(dtoJsonView)
                .build();
    }

    public static TransportableReplication createDeleteReplication(final Object entityId) {
        return TransportableReplication.builder()
                .type(DELETE)
                .entityId(entityId)
                .build();
    }
}
