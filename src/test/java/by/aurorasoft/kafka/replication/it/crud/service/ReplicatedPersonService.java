package by.aurorasoft.kafka.replication.it.crud.service;

import by.aurorasoft.kafka.replication.it.crud.dto.ReplicatedPerson;
import by.aurorasoft.kafka.replication.it.crud.entity.ReplicatedPersonEntity;
import by.aurorasoft.kafka.replication.it.crud.mapper.ReplicatedPersonMapper;
import by.aurorasoft.kafka.replication.it.crud.repository.ReplicatedPersonRepository;
import by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD;
import org.springframework.stereotype.Service;

@Service
public class ReplicatedPersonService extends AbsServiceCRUD<Long, ReplicatedPersonEntity, ReplicatedPerson, ReplicatedPersonRepository> {

    public ReplicatedPersonService(final ReplicatedPersonMapper mapper, final ReplicatedPersonRepository repository) {
        super(mapper, repository);
    }
}
