package by.aurorasoft.kafka.replication.it.crud.service;

import by.aurorasoft.kafka.replication.it.crud.dto.PersonReplication;
import by.aurorasoft.kafka.replication.it.crud.entity.PersonReplicationEntity;
import by.aurorasoft.kafka.replication.it.crud.mapper.PersonReplicationMapper;
import by.aurorasoft.kafka.replication.it.crud.repository.PersonReplicationRepository;
import by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PersonReplicationService extends AbsServiceCRUD<Long, PersonReplicationEntity, PersonReplication, PersonReplicationRepository> {

    public PersonReplicationService(final PersonReplicationMapper mapper, final PersonReplicationRepository repository) {
        super(mapper, repository);
    }
}
