package by.aurorasoft.kafka.replication.it.crud.service;

import by.aurorasoft.kafka.replication.annotation.ReplicatedSave;
import by.aurorasoft.kafka.replication.annotation.ReplicatedService;
import by.aurorasoft.kafka.replication.annotation.ReplicatedUpdate;
import by.aurorasoft.kafka.replication.it.crud.dto.Person;
import by.aurorasoft.kafka.replication.it.crud.entity.PersonEntity;
import by.aurorasoft.kafka.replication.it.crud.mapper.PersonMapper;
import by.aurorasoft.kafka.replication.it.crud.repository.PersonRepository;
import by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD;
import org.springframework.stereotype.Service;

@Service
//@ReplicatedService(replicationProducer = KafkaProducerPersonReplication.class)
public class PersonService extends AbsServiceCRUD<Long, PersonEntity, Person, PersonRepository> {

    public PersonService(final PersonMapper mapper, final PersonRepository repository) {
        super(mapper, repository);
    }

    @Override
    @ReplicatedSave
    public Person save(final Person person) {
        return super.save(person);
    }

    @Override
    @ReplicatedUpdate
    public Person update(final Person person) {
        return super.update(person);
    }
}
