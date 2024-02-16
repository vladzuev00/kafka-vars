package by.aurorasoft.kafka.replication.it.crud.service;

import by.aurorasoft.kafka.replication.annotation.ReplicatedService;
import by.aurorasoft.kafka.replication.it.crud.dto.Person;
import by.aurorasoft.kafka.replication.it.crud.entity.PersonEntity;
import by.aurorasoft.kafka.replication.it.crud.mapper.PersonMapper;
import by.aurorasoft.kafka.replication.it.crud.repository.PersonRepository;
import by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD;
import org.apache.kafka.common.serialization.LongSerializer;

@ReplicatedService(topicName = "sync-person", keySerializer = LongSerializer.class)
public class PersonService extends AbsServiceCRUD<Long, PersonEntity, Person, PersonRepository> {

    public PersonService(final PersonMapper mapper, final PersonRepository repository) {
        super(mapper, repository);
    }
}
