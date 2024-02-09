package by.aurorasoft.kafka.replication.it;

import by.aurorasoft.kafka.base.AbstractSpringBootTest;
import by.aurorasoft.kafka.replication.it.crud.dto.Person;
import by.aurorasoft.kafka.replication.it.crud.dto.PersonReplication;
import by.aurorasoft.kafka.replication.it.crud.entity.PersonEntity;
import by.aurorasoft.kafka.replication.it.crud.repository.PersonRepository;
import by.aurorasoft.kafka.replication.it.crud.service.PersonReplicationService;
import by.aurorasoft.kafka.replication.it.crud.service.PersonService;
import org.apache.avro.reflect.ReflectData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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

    @Test
    public void personShouldBeSavedWithReplication() {

        final Person givenPerson = Person.builder()
                .id(256L)
                .name("Vlad")
                .surname("Zuev")
                .build();

        personService.save(givenPerson);
//
//        TimeUnit.SECONDS.sleep(10);
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
