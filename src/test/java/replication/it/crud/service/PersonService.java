package replication.it.crud.service;

import by.aurorasoft.kafka.replication.annotation.ReplicatedSave;
import by.aurorasoft.kafka.replication.annotation.ReplicatedService;
import by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD;
import org.springframework.stereotype.Component;
import replication.it.crud.dto.Person;
import replication.it.crud.entity.PersonEntity;
import replication.it.crud.mapper.PersonMapper;
import replication.it.crud.repository.PersonRepository;
import replication.it.eventproducer.KafkaProducerPersonReplication;

@Component
@ReplicatedService(replicationProducer = KafkaProducerPersonReplication.class)
public class PersonService extends AbsServiceCRUD<Long, PersonEntity, Person, PersonRepository> {

    public PersonService(final PersonMapper mapper, final PersonRepository repository) {
        super(mapper, repository);
    }

    @Override
    @ReplicatedSave
    public Person save(final Person person) {
        return super.save(person);
    }
}
