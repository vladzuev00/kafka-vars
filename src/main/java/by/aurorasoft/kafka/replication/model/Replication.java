package by.aurorasoft.kafka.replication.model;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.apache.avro.reflect.Nullable;

import static by.aurorasoft.kafka.replication.model.ReplicationOperation.*;
import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
@Builder(access = PRIVATE)
@FieldNameConstants
public final class Replication {
    private final ReplicationOperation operation;

    @Nullable
    private final Object entityId;

    @Nullable
    private final TransportableDto dto;

    public Object getEntityId() {
        return dto != null ? dto.getId() : entityId;
    }

    public <ID, DTO extends AbstractDto<ID>> void execute(final AbsServiceCRUD<ID, ?, DTO, ?> service) {
        operation.execute(this, service);
    }

    public static Replication createSaveReplication(final TransportableDto dto) {
        return Replication.builder()
                .operation(SAVE)
                .dto(dto)
                .build();
    }

    public static Replication createUpdateReplication(final TransportableDto dto) {
        return Replication.builder()
                .operation(UPDATE)
                .dto(dto)
                .build();
    }

    public static Replication createDeleteReplication(final Object entityId) {
        return Replication.builder()
                .operation(DELETE)
                .entityId(entityId)
                .build();
    }
}
