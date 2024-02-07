package by.aurorasoft.kafka.replication.it.crud.mapper;

import by.aurorasoft.kafka.replication.it.crud.dto.PersonReplication;
import by.aurorasoft.kafka.replication.it.crud.entity.PersonReplicationEntity;
import by.nhorushko.crudgeneric.v2.mapper.AbsMapperEntityDto;
import jakarta.persistence.EntityManager;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public final class PersonReplicationMapper extends AbsMapperEntityDto<PersonReplicationEntity, PersonReplication> {

    public PersonReplicationMapper(final ModelMapper modelMapper, final EntityManager entityManager) {
        super(modelMapper, entityManager, PersonReplicationEntity.class, PersonReplication.class);
    }

    @Override
    protected PersonReplication create(final PersonReplicationEntity entity) {
        return new PersonReplication(entity.getId(), entity.getName(), entity.getSurname());
    }
}
