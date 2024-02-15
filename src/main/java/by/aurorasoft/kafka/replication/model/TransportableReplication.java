package by.aurorasoft.kafka.replication.model;

import by.aurorasoft.kafka.replication.consumer.ReplicationConsumingContext;
import by.aurorasoft.kafka.replication.model.replication.DeleteReplication;
import by.aurorasoft.kafka.replication.model.replication.Replication;
import by.aurorasoft.kafka.replication.model.replication.SaveReplication;
import by.aurorasoft.kafka.replication.model.replication.UpdateReplication;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.FieldNameConstants;

@AllArgsConstructor
@Builder
@FieldNameConstants
public final class TransportableReplication {
    private final ReplicationType type;
    private final String dtoProjectAsJson;

    public <ID, DTO extends AbstractDto<ID>> Replication<ID, DTO> createReplication(final ReplicationConsumingContext<ID, DTO> context) {
        return type.createReplication(this, context);
    }

    public enum ReplicationType {
        SAVE {
            @Override
            public <ID, DTO extends AbstractDto<ID>> SaveReplication<ID, DTO> createReplication(final TransportableReplication replication,
                                                                                                final ReplicationConsumingContext<ID, DTO> context) {
                final DTO dto = context.mapJsonViewToDto(replication.dtoProjectAsJson);
                return new SaveReplication<>(dto);
            }
        },

        UPDATE {
            @Override
            public <ID, DTO extends AbstractDto<ID>> Replication<ID, DTO> createReplication(final TransportableReplication replication,
                                                                                            final ReplicationConsumingContext<ID, DTO> context) {
                final DTO dto = context.mapJsonViewToDto(replication.dtoProjectAsJson);
                return new UpdateReplication<>(dto);
            }
        },

        DELETE {
            @Override
            public <ID, DTO extends AbstractDto<ID>> Replication<ID, DTO> createReplication(final TransportableReplication replication,
                                                                                            final ReplicationConsumingContext<ID, DTO> context) {
                final DTO dto = context.mapJsonViewToDto(replication.dtoProjectAsJson);
                return new DeleteReplication<>(dto);
            }
        };

        public abstract <ID, DTO extends AbstractDto<ID>> Replication<ID, DTO> createReplication(final TransportableReplication replication,
                                                                                                 final ReplicationConsumingContext<ID, DTO> context);
    }
}
