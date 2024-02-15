package by.aurorasoft.kafka.replication.it.crud.repository;

import by.aurorasoft.kafka.replication.it.crud.entity.ReplicatedPersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplicatedPersonRepository extends JpaRepository<ReplicatedPersonEntity, Long> {

}
