package by.aurorasoft.kafka.replication.model;

import by.aurorasoft.kafka.replication.consumer.ReplicatedDtoDeserializer;
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
    private final String dtoJson;

    public <ID, DTO extends AbstractDto<ID>> Replication<ID, DTO> createReplication(final ReplicatedDtoDeserializer<DTO> context) {
        return type.createReplication(this, context);
    }

    public enum ReplicationType {
        SAVE {
            @Override
            protected <ID, DTO extends AbstractDto<ID>> SaveReplication<ID, DTO> createReplication(final DTO dto) {
                return new SaveReplication<>(dto);
            }
        },

        UPDATE {
            @Override
            protected <ID, DTO extends AbstractDto<ID>> UpdateReplication<ID, DTO> createReplication(final DTO dto) {
                return new UpdateReplication<>(dto);
            }
        },

        DELETE {
            @Override
            protected <ID, DTO extends AbstractDto<ID>> DeleteReplication<ID, DTO> createReplication(final DTO dto) {
                return new DeleteReplication<>(dto);
            }
        };

        public final <ID, DTO extends AbstractDto<ID>> Replication<ID, DTO> createReplication(final TransportableReplication replication,
                                                                                              final ReplicatedDtoDeserializer<DTO> context) {
            final DTO dto = context.deserializeDto(replication.dtoJson);
            return createReplication(dto);
        }

        protected abstract <ID, DTO extends AbstractDto<ID>> Replication<ID, DTO> createReplication(final DTO dto);
    }
}
