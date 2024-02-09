package by.aurorasoft.kafka.replication.model.transportable;

import by.aurorasoft.kafka.replication.model.ReplicationOperation;
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
public final class TransportableReplication {
    private final ReplicationOperation operation;

    @Nullable
    private final Object entityId;

    @Nullable
    private final TransportableDto dto;

    public <ID, DTO extends AbstractDto<ID>> void execute(final AbsServiceCRUD<ID, ?, DTO, ?> service) {
        operation.execute(this, service);
    }

    public static TransportableReplication createTransportableSave(final TransportableDto dto) {
        return TransportableReplication.builder()
                .operation(SAVE)
                .dto(dto)
                .build();
    }

    public static TransportableReplication createTransportableUpdate(final TransportableDto dto) {
        return TransportableReplication.builder()
                .operation(UPDATE)
                .dto(dto)
                .build();
    }

    public static TransportableReplication createTransportableDelete(final Object entityId) {
        return TransportableReplication.builder()
                .operation(DELETE)
                .entityId(entityId)
                .build();
    }
}
