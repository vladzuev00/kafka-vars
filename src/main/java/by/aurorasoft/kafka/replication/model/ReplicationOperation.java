package by.aurorasoft.kafka.replication.model;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD;
import org.apache.avro.generic.GenericRecord;

public enum ReplicationOperation {
    SAVE, UPDATE, DELETE;

    public abstract <ID, DTO extends AbstractDto<ID>> void execute(final Replication replication,
                                                                   final AbsServiceCRUD<ID, ?, DTO, ?> service);
    public abstract Replication createReplication(final GenericRecord record, final )
}
