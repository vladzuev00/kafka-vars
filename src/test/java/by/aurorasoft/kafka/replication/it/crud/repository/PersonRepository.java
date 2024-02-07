package by.aurorasoft.kafka.replication.it.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import by.aurorasoft.kafka.replication.it.crud.entity.PersonEntity;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

}
