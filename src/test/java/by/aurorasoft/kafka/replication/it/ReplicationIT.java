package by.aurorasoft.kafka.replication.it;

import by.aurorasoft.kafka.base.AbstractSpringBootTest;
import by.aurorasoft.kafka.replication.it.crud.dto.Person;
import by.aurorasoft.kafka.replication.it.crud.dto.PersonReplication;
import by.aurorasoft.kafka.replication.it.crud.entity.PersonReplicationEntity;
import by.aurorasoft.kafka.replication.it.crud.mapper.PersonReplicationMapper;
import by.aurorasoft.kafka.replication.it.crud.repository.PersonReplicationRepository;
import by.aurorasoft.kafka.replication.it.crud.repository.PersonRepository;
import by.aurorasoft.kafka.replication.it.crud.service.PersonReplicationService;
import by.aurorasoft.kafka.replication.it.crud.service.PersonService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.System.out;
import static java.lang.Thread.currentThread;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

public class ReplicationIT extends AbstractSpringBootTest {
    private static final int WAIT_REPLICATION_PERFORMING_SECONDS = 5;

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonReplicationService replicationService;

    @Autowired
    private PersonReplicationRepository replicationRepository;

    @Autowired
    private PersonReplicationMapper replicationMapper;

    @Test
//    @Transactional(propagation = NOT_SUPPORTED)
    public void personShouldBeSavedWithReplication() {
        final PersonReplication givenReplication = PersonReplication.builder()
                .id(256L)
                .name("Vlad")
                .surname("Zuev")
                .build();
        final PersonReplicationEntity givenEntity = replicationMapper.toEntity(givenReplication);

        final PersonReplicationEntity savedEntity = replicationRepository.save(givenEntity);
        out.println("Saved entity: " + savedEntity);


//        final PersonReplicationEntity givenReplication = PersonReplicationEntity.builder()
//                .id(256L)
//                .name("Vlad")
//                .surname("Zuev")
//                .build();
//
//        replicationRepository.save(givenReplication);
//        entityManager.flush();
    }

    @Test
    @Transactional(propagation = NOT_SUPPORTED)
    @Sql(statements = "INSERT INTO persons(id, name, surname) VALUES(255, 'Vlad', 'Zuev')")
    @Sql(statements = "INSERT INTO person_replications(id, name, surname) VALUES(255, 'Vlad', 'Zuev')")
    @Sql(statements = "DELETE FROM persons", executionPhase = AFTER_TEST_METHOD)
    @Sql(statements = "DELETE FROM person_replications", executionPhase = AFTER_TEST_METHOD)
    public void personShouldBeUpdatedWithReplication() {
        final Long givenId = 255L;
        final String givenNewName = "Ivan";
        final String givenNewSurname = "Ivanov";
        final Person givenPerson = new Person(givenId, givenNewName, givenNewSurname);

        final Person actualPerson = personService.update(givenPerson);
        assertEquals(givenPerson, actualPerson);

        waitReplicationPerforming();

        final PersonReplication actualReplication = replicationService.getById(givenId);
        final PersonReplication expectedReplication = new PersonReplication(givenId, givenNewName, givenNewSurname);
        assertEquals(expectedReplication, actualReplication);
    }

    private static void waitReplicationPerforming() {
        try {
            SECONDS.sleep(WAIT_REPLICATION_PERFORMING_SECONDS);
        } catch (final InterruptedException exception) {
            currentThread().interrupt();
        }
    }
}
