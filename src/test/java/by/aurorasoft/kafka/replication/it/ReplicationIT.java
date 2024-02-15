package by.aurorasoft.kafka.replication.it;

import by.aurorasoft.kafka.base.kafka.AbstractKafkaContainerTest;
import by.aurorasoft.kafka.replication.it.crud.dto.Person;
import by.aurorasoft.kafka.replication.it.crud.dto.ReplicatedPerson;
import by.aurorasoft.kafka.replication.it.crud.mapper.PersonMapper;
import by.aurorasoft.kafka.replication.it.crud.mapper.ReplicatedPersonMapper;
import by.aurorasoft.kafka.replication.it.crud.repository.PersonRepository;
import by.aurorasoft.kafka.replication.it.crud.repository.ReplicatedPersonRepository;
import by.aurorasoft.kafka.replication.it.crud.service.PersonService;
import by.aurorasoft.kafka.replication.it.crud.service.ReplicatedPersonService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

public class ReplicationIT extends AbstractKafkaContainerTest {
    private static final int WAIT_REPLICATION_SECONDS = 5;

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ReplicatedPersonService replicatedPersonService;

    @Autowired
    private ReplicatedPersonRepository replicationRepository;

    @Autowired
    private ReplicatedPersonMapper replicationMapper;

    @Autowired
    private PersonMapper mapper;

    @Test
//    @Transactional(propagation = NOT_SUPPORTED)
    @Sql(statements = "DELETE FROM persons", executionPhase = AFTER_TEST_METHOD)
    @Sql(statements = "DELETE FROM person_replications", executionPhase = AFTER_TEST_METHOD)
    public void personShouldBeSavedWithReplication() {
        final Person givenPerson = Person.builder()
                .name("Vlad")
                .surname("Zuev")
                .patronymic("Sergeevich")
                .build();

        personService.save(givenPerson);

        waitReplication();

//        final PersonReplicationEntity givenReplication = PersonReplicationEntity.builder()
//                .id(256L)
//                .name("Vlad")
//                .surname("Zuev")
//                .build();
//
//        PersonReplicationEntity savedEntity = replicationRepository.save(givenReplication);
//        entityManager.flush();
    }

    @Test
    @Transactional(propagation = NOT_SUPPORTED)
    @Sql(statements = "INSERT INTO persons(id, name, surname, patronymic, birth_date) VALUES(255, 'Vlad', 'Zuev', 'Sergeevich', '2000-02-18')")
    @Sql(statements = "INSERT INTO replicated_persons(id, name, surname, birth_date) VALUES(255, 'Vlad', 'Zuev', '2000-02-18')")
    @Sql(statements = "DELETE FROM persons", executionPhase = AFTER_TEST_METHOD)
    @Sql(statements = "DELETE FROM replicated_persons", executionPhase = AFTER_TEST_METHOD)
    public void replicatedPersonShouldBeUpdated() {
        final Long givenId = 255L;
        final String givenNewName = "Ivan";
        final String givenNewSurname = "Ivanov";
        final String givenNewPatronymic = "Ivanovich";
        final LocalDate givenNewBirthDate = LocalDate.of(2000, 2, 19);
        final Person givenNewPerson = Person.builder()
                .id(givenId)
                .name(givenNewName)
                .surname(givenNewSurname)
                .patronymic(givenNewPatronymic)
                .birthDate(givenNewBirthDate)
                .build();

        final Person actualUpdatedPerson = personService.update(givenNewPerson);
        assertEquals(givenNewPerson, actualUpdatedPerson);

        waitReplication();

        final ReplicatedPerson actualReplication = replicatedPersonService.getById(givenId);
        final ReplicatedPerson expectedReplication = ReplicatedPerson.builder()
                .id(givenId)
                .name(givenNewName)
                .surname(givenNewSurname)
                .birthDate(givenNewBirthDate)
                .build();
        assertEquals(expectedReplication, actualReplication);
    }

    @Test
    @Transactional(propagation = NOT_SUPPORTED)
    @Sql(statements = "INSERT INTO persons(id, name, surname, patronymic, birth_date) VALUES(255, 'Vlad', 'Zuev', 'Sergeevich', '2000-02-18')")
    @Sql(statements = "INSERT INTO replicated_persons(id, name, surname, birth_date) VALUES(255, 'Vlad', 'Zuev', '2000-02-18')")
    public void replicatedPersonShouldBeDeleted() {
        final Long givenId = 255L;

        personService.delete(givenId);
        waitReplication();
        final boolean successDeleting = !personService.isExist(givenId) && !replicatedPersonService.isExist(givenId);
        assertFalse(successDeleting);
    }

    private static void waitReplication() {
        try {
            SECONDS.sleep(WAIT_REPLICATION_SECONDS);
        } catch (final InterruptedException exception) {
            currentThread().interrupt();
        }
    }
}
