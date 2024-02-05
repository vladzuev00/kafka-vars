package by.aurorasoft.kafka.replication.model;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD;

public enum ReplicationOperation {
    SAVE {
        @Override
        public <ID, DTO extends AbstractDto<ID>> void execute(final AbsServiceCRUD<ID, ?, DTO, ?> service, final DTO dto) {
            service.save(dto);
        }
    },

    UPDATE {
        @Override
        public <ID, DTO extends AbstractDto<ID>> void execute(final AbsServiceCRUD<ID, ?, DTO, ?> service, final DTO dto) {
            service.update(dto);
        }
    },

    DELETE {
        @Override
        public <ID, DTO extends AbstractDto<ID>> void execute(final AbsServiceCRUD<ID, ?, DTO, ?> service, final DTO dto) {
            service.delete(dto.getId());
        }
    };

    public abstract <ID, DTO extends AbstractDto<ID>> void execute(final AbsServiceCRUD<ID, ?, DTO, ?> service, final DTO dto);
}
