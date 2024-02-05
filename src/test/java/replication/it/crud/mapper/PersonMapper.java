package replication.it.crud.mapper;

import by.nhorushko.crudgeneric.v2.mapper.AbsMapperEntityDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import replication.it.crud.dto.Person;
import replication.it.crud.entity.PersonEntity;

import javax.persistence.EntityManager;

@Component
public final class PersonMapper extends AbsMapperEntityDto<PersonEntity, Person> {

    public PersonMapper(final ModelMapper modelMapper, final EntityManager entityManager) {
        super(modelMapper, entityManager, PersonEntity.class, Person.class);
    }

    @Override
    protected Person create(final PersonEntity entity) {
        return new Person(entity.getId(), entity.getName(), entity.getSurname());
    }
}
