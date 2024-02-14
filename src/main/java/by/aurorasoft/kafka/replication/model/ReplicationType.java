package by.aurorasoft.kafka.replication.model;

import by.aurorasoft.kafka.replication.consumer.ReplicationConsumingContext;
import by.aurorasoft.kafka.replication.model.replication.Replication;
import by.aurorasoft.kafka.replication.model.replication.SaveReplication;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;

public enum ReplicationType {
    SAVE {
//        @Override
//        public Replication createReplication(final TransportableReplication replication,
//                                             final ReplicationConsumingContext context) {
//            final DTO dto = context.getDtoMapper().apply(replication.getDto());
//            return new SaveReplication(dto);
//        }
    },

    UPDATE, DELETE;

//    public abstract Replication createReplication(final TransportableReplication replication,
//                                                  final ReplicationConsumingContext context);
}
