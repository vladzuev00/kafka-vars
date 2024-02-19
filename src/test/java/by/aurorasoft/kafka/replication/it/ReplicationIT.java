package by.aurorasoft.kafka.replication.it;

import by.aurorasoft.kafka.base.AbstractSpringBootTest;
import by.aurorasoft.kafka.replication.it.crud.dto.Person;
import by.aurorasoft.kafka.replication.it.crud.dto.ReplicatedPerson;
import by.aurorasoft.kafka.replication.it.crud.service.PersonService;
import by.aurorasoft.kafka.replication.it.crud.service.ReplicatedPersonService;
import by.aurorasoft.kafka.replication.it.kafka.consumer.KafkaConsumerPersonReplication;
import org.junit.Test;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

public class ReplicationIT extends AbstractSpringBootTest {

    @Autowired
    private PersonService personService;

    @Autowired
    private ReplicatedPersonService replicatedPersonService;

    @Autowired
    private KafkaConsumerPersonReplication replicationConsumer;

    @Test
    @Transactional(propagation = NOT_SUPPORTED)
    @Sql(value = "classpath:sql-scripts/replication/it/after.sql", executionPhase = AFTER_TEST_METHOD)
    public void personAndReplicatedPersonShouldBeSaved() {
        final String givenName = "Vlad";
        final String givenSurname = "Zuev";
        final String givenPatronymic = "Sergeevich";
        final LocalDate givenBirthDate = LocalDate.of(2000, 2, 18);
        final Person givenPerson = Person.builder()
                .name(givenName)
                .surname(givenSurname)
                .patronymic(givenPatronymic)
                .birthDate(givenBirthDate)
                .build();

        final Person actualSavedPerson = personService.save(givenPerson);

        final Long expectedSavedPersonId = 1L;
        final Person expectedSavedPerson = Person.builder()
                .id(expectedSavedPersonId)
                .name(givenName)
                .surname(givenSurname)
                .patronymic(givenPatronymic)
                .birthDate(givenBirthDate)
                .build();
        assertEquals(expectedSavedPerson, actualSavedPerson);

        assertTrue(replicationConsumer.isSuccessConsuming());

        final ReplicatedPerson actualReplicatedPerson = replicatedPersonService.getById(expectedSavedPersonId);
        final ReplicatedPerson expectedReplicatedPerson = ReplicatedPerson.builder()
                .id(expectedSavedPersonId)
                .name(givenName)
                .surname(givenSurname)
                .birthDate(givenBirthDate)
                .build();
        assertEquals(expectedReplicatedPerson, actualReplicatedPerson);
    }

    @Test
    @Sql(value = "classpath:sql-scripts/replication/it/after.sql", executionPhase = AFTER_TEST_METHOD)
    public void personsAndReplicatedPersonsShouldBeSaved() {

    }

    @Test
    @Transactional(propagation = NOT_SUPPORTED)
    @Sql("classpath:sql-scripts/replication/it/insert-person.sql")
    @Sql(value = "classpath:sql-scripts/replication/it/after.sql", executionPhase = AFTER_TEST_METHOD)
    public void personAndReplicatedPersonShouldBeUpdated() {
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

        assertTrue(replicationConsumer.isSuccessConsuming());

        final ReplicatedPerson actualUpdatedReplication = replicatedPersonService.getById(givenId);
        final ReplicatedPerson expectedUpdatedReplication = ReplicatedPerson.builder()
                .id(givenId)
                .name(givenNewName)
                .surname(givenNewSurname)
                .birthDate(givenNewBirthDate)
                .build();
        assertEquals(expectedUpdatedReplication, actualUpdatedReplication);
    }

    @Test
    @Transactional(propagation = NOT_SUPPORTED)
    @Sql("classpath:sql-scripts/replication/it/insert-person.sql")
    @Sql(value = "classpath:sql-scripts/replication/it/after.sql", executionPhase = AFTER_TEST_METHOD)
    public void personAndReplicatedPersonShouldBeDeleted() {
        final Long givenId = 255L;

        personService.delete(givenId);

        assertTrue(replicationConsumer.isSuccessConsuming());

        final boolean successDeleting = !personService.isExist(givenId) && !replicatedPersonService.isExist(givenId);
        assertTrue(successDeleting);
    }
}
