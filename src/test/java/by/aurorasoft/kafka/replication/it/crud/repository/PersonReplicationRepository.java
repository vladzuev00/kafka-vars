package by.aurorasoft.kafka.replication.it.crud.repository;

import by.aurorasoft.kafka.replication.it.crud.entity.PersonReplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonReplicationRepository extends JpaRepository<PersonReplicationEntity, Long> {

}
