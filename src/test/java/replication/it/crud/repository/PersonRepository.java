package replication.it.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import replication.it.crud.entity.PersonEntity;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

}
