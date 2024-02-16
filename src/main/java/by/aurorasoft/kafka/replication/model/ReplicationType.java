package by.aurorasoft.kafka.replication.model;

import by.aurorasoft.kafka.replication.model.replication.DeleteReplication;
import by.aurorasoft.kafka.replication.model.replication.Replication;
import by.aurorasoft.kafka.replication.model.replication.SaveReplication;
import by.aurorasoft.kafka.replication.model.replication.UpdateReplication;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;

public enum ReplicationType {
    SAVE {
        @Override
        public <ID, DTO extends AbstractDto<ID>> SaveReplication<ID, DTO> createReplication(final DTO dto) {
            return new SaveReplication<>(dto);
        }
    },

    UPDATE {
        @Override
        public <ID, DTO extends AbstractDto<ID>> UpdateReplication<ID, DTO> createReplication(final DTO dto) {
            return new UpdateReplication<>(dto);
        }
    },

    DELETE {
        @Override
        public <ID, DTO extends AbstractDto<ID>> DeleteReplication<ID, DTO> createReplication(final DTO dto) {
            return new DeleteReplication<>(dto);
        }
    };

    public abstract <ID, DTO extends AbstractDto<ID>> Replication<ID, DTO> createReplication(final DTO dto);
}
