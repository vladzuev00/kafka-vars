package by.aurorasoft.kafka.replication.it;

import by.aurorasoft.kafka.base.AbstractSpringBootTest;
import by.aurorasoft.kafka.replication.annotation.ReplicatedService;
import by.aurorasoft.kafka.replication.consumer.KafkaConsumerReplication;
import by.aurorasoft.kafka.serialize.AvroGenericRecordDeserializer;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.mapper.AbsMapperEntityDto;
import by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.*;
import lombok.*;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.hibernate.Hibernate;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeoutException;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static jakarta.persistence.GenerationType.IDENTITY;
import static java.lang.Thread.currentThread;
import static java.util.Objects.hash;
import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.stream.IntStream.rangeClosed;
import static org.apache.kafka.clients.admin.AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.kafka.listener.ContainerProperties.AckMode.MANUAL_IMMEDIATE;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

//TODO: put after @Sql annotation for class
@Import(ReplicationIT.TestConfiguration.class)
public class ReplicationIT extends AbstractSpringBootTest {

    @Autowired
    private PersonService personService;

    @Autowired
    private ReplicatedPersonService replicatedPersonService;

    @Autowired
    private KafkaConsumerPersonReplication replicationConsumer;

    @Test
    @Transactional(propagation = NOT_SUPPORTED)
    @Sql(value = "classpath:sql-scripts/replication/it/delete-person.sql", executionPhase = AFTER_TEST_METHOD)
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

        replicationConsumer.setExpectedReplicationCount(1);
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
    @Sql(value = "classpath:sql-scripts/replication/it/delete-person.sql", executionPhase = AFTER_TEST_METHOD)
    public void personsAndReplicatedPersonsShouldBeSaved() {
        final String givenFirstPersonName = "Vlad";
        final String givenFirstPersonSurname = "Zuev";
        final String givenFirstPersonPatronymic = "Sergeevich";
        final LocalDate givenFirstPersonBirthDate = LocalDate.of(2000, 2, 18);

        final String givenSecondPersonName = "Ivan";
        final String givenSecondPersonSurname = "Ivanov";
        final String givenSecondPersonPatronymic = "Ivanovich";
        final LocalDate givenSecondPersonBirthDate = LocalDate.of(2001, 3, 19);

        final List<Person> givenPersons = List.of(
                Person.builder()
                        .name(givenFirstPersonName)
                        .surname(givenFirstPersonSurname)
                        .patronymic(givenFirstPersonPatronymic)
                        .birthDate(givenFirstPersonBirthDate)
                        .build(),
                Person.builder()
                        .name(givenSecondPersonName)
                        .surname(givenSecondPersonSurname)
                        .patronymic(givenSecondPersonPatronymic)
                        .birthDate(givenSecondPersonBirthDate)
                        .build()
        );

        replicationConsumer.setExpectedReplicationCount(2);
        final List<Person> actualSavedPersons = personService.saveAll(givenPersons);

        final Long expectedFirstSavedPersonId = 1L;
        final Long expectedSecondSavedPersonId = 2L;
        final List<Person> expectedSavedPersons = List.of(
                Person.builder()
                        .id(expectedFirstSavedPersonId)
                        .name(givenFirstPersonName)
                        .surname(givenFirstPersonSurname)
                        .patronymic(givenFirstPersonPatronymic)
                        .birthDate(givenFirstPersonBirthDate)
                        .build(),
                Person.builder()
                        .id(expectedSecondSavedPersonId)
                        .name(givenSecondPersonName)
                        .surname(givenSecondPersonSurname)
                        .patronymic(givenSecondPersonPatronymic)
                        .birthDate(givenSecondPersonBirthDate)
                        .build()
        );
        assertEquals(expectedSavedPersons, actualSavedPersons);

        assertTrue(replicationConsumer.isSuccessConsuming());

        final List<Person> actualReplicatedPersons = personService.getById(
                List.of(
                        expectedFirstSavedPersonId,
                        expectedSecondSavedPersonId
                )
        );
        final List<Person> expectedReplicatedPersons = List.of(
                Person.builder()
                        .id(expectedFirstSavedPersonId)
                        .name(givenFirstPersonName)
                        .surname(givenFirstPersonSurname)
                        .patronymic(givenFirstPersonPatronymic)
                        .birthDate(givenFirstPersonBirthDate)
                        .build(),
                Person.builder()
                        .id(expectedSecondSavedPersonId)
                        .name(givenSecondPersonName)
                        .surname(givenSecondPersonSurname)
                        .patronymic(givenSecondPersonPatronymic)
                        .birthDate(givenSecondPersonBirthDate)
                        .build()
        );
        assertEquals(expectedReplicatedPersons, actualReplicatedPersons);
    }

    @Test
    @Transactional(propagation = NOT_SUPPORTED)
    @Sql("classpath:sql-scripts/replication/it/insert-person.sql")
    @Sql(value = "classpath:sql-scripts/replication/it/delete-person.sql", executionPhase = AFTER_TEST_METHOD)
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

        replicationConsumer.setExpectedReplicationCount(1);
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
    @Sql(value = "classpath:sql-scripts/replication/it/delete-person.sql", executionPhase = AFTER_TEST_METHOD)
    public void personAndReplicatedPersonShouldBeUpdatedPartially() {
        final Long givenId = 255L;

        final String givenNewName = "Ivan";
        final String givenNewSurname = "Ivanov";
        final String givenNewPatronymic = "Ivanovich";
        final PersonName givenPartial = new PersonName(givenNewName, givenNewSurname, givenNewPatronymic);

        replicationConsumer.setExpectedReplicationCount(1);
        final Person actualUpdatedPerson = personService.updatePartial(givenId, givenPartial);
        final LocalDate expectedBirthDate = LocalDate.of(2000, 2, 18);
        final Person expectedUpdatedPerson = Person.builder()
                .id(givenId)
                .name(givenNewName)
                .surname(givenNewSurname)
                .patronymic(givenNewPatronymic)
                .birthDate(expectedBirthDate)
                .build();
        assertEquals(expectedUpdatedPerson, actualUpdatedPerson);

        assertTrue(replicationConsumer.isSuccessConsuming());

        final ReplicatedPerson actualUpdatedReplication = replicatedPersonService.getById(givenId);
        final ReplicatedPerson expectedUpdatedReplication = ReplicatedPerson.builder()
                .id(givenId)
                .name(givenNewName)
                .surname(givenNewSurname)
                .birthDate(expectedBirthDate)
                .build();
        assertEquals(expectedUpdatedReplication, actualUpdatedReplication);
    }

    @Test
    @Transactional(propagation = NOT_SUPPORTED)
    @Sql("classpath:sql-scripts/replication/it/insert-person.sql")
    @Sql(value = "classpath:sql-scripts/replication/it/delete-person.sql", executionPhase = AFTER_TEST_METHOD)
    public void personAndReplicatedPersonShouldBeDeleted() {
        final Long givenId = 255L;

        replicationConsumer.setExpectedReplicationCount(1);
        personService.delete(givenId);

        assertTrue(replicationConsumer.isSuccessConsuming());

        final boolean successDeleting = !personService.isExist(givenId) && !replicatedPersonService.isExist(givenId);
        assertTrue(successDeleting);
    }

    @Configuration
    @EnableJpaRepositories(considerNestedRepositories = true)
    @Import(
            {
                    PersonMapper.class,
                    ReplicatedPersonMapper.class,
                    PersonService.class,
                    ReplicatedPersonService.class,
                    KafkaConsumerPersonReplication.class
            }
    )
    static class TestConfiguration {
        private static final String SCHEMA_PROPERTY_NAME = "SCHEMA";

        @Value("${spring.kafka.bootstrap-servers}")
        private String bootstrapAddress;

        @Bean
        public ModelMapper modelMapper() {
            return new ModelMapper();
        }

        @Bean
        public ObjectMapper objectMapper() {
            return new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        }

        @Bean
        public KafkaAdmin kafkaAdmin() {
            final Map<String, Object> configurationByNames = Map.of(BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
            return new KafkaAdmin(configurationByNames);
        }

        @Bean
        @Autowired
        public ConsumerFactory<Long, GenericRecord> consumerFactorySyncPerson(
                @Value("${kafka.topic.sync-person.consumer.group-id}") final String groupId,
                @Value("${kafka.topic.sync-person.consumer.max-poll-records}") final int maxPollRecords,
                @Value("${kafka.topic.sync-person.consumer.fetch-max-wait-ms}") final int fetchMaxWaitMs,
                @Value("${kafka.topic.sync-person.consumer.fetch-min-bytes}") final int fetchMinBytes,
                @Qualifier("replicationSchema") final Schema schema
        ) {
            return createConsumerFactory(groupId, maxPollRecords, fetchMaxWaitMs, fetchMinBytes, schema);
        }

        @Bean
        @Autowired
        public ConcurrentKafkaListenerContainerFactory<Long, GenericRecord> listenerContainerFactorySyncPerson(
                @Qualifier("consumerFactorySyncPerson") final ConsumerFactory<Long, GenericRecord> consumerFactory
        ) {
            return createListenerContainerFactory(consumerFactory);
        }

        private <K, V> ConsumerFactory<K, V> createConsumerFactory(final String groupId,
                                                                   final int maxPollRecords,
                                                                   final int fetchMaxWaitMs,
                                                                   final int fetchMinBytes,
                                                                   final Schema schema) {
            final Map<String, Object> propertiesByNames = Map.of(
                    BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress,
                    GROUP_ID_CONFIG, groupId,
                    KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class,
                    VALUE_DESERIALIZER_CLASS_CONFIG, AvroGenericRecordDeserializer.class,
                    MAX_POLL_RECORDS_CONFIG, maxPollRecords,
                    FETCH_MAX_WAIT_MS_CONFIG, fetchMaxWaitMs,
                    FETCH_MIN_BYTES_CONFIG, fetchMinBytes,
                    ENABLE_AUTO_COMMIT_CONFIG, false,
                    SCHEMA_PROPERTY_NAME, schema
            );
            return new DefaultKafkaConsumerFactory<>(propertiesByNames);
        }

        private static <K, V> ConcurrentKafkaListenerContainerFactory<K, V> createListenerContainerFactory(
                final ConsumerFactory<K, V> consumerFactory
        ) {
            final ConcurrentKafkaListenerContainerFactory<K, V> factory = new ConcurrentKafkaListenerContainerFactory<>();
            factory.setBatchListener(true);
            factory.setConsumerFactory(consumerFactory);
            factory.getContainerProperties().setAckMode(MANUAL_IMMEDIATE);
            return factory;
        }
    }

    //---------------------------ENTITY---------------------------
    static abstract class AbstractEntity<ID> implements by.nhorushko.crudgeneric.v2.domain.AbstractEntity<ID> {

        @Override
        @SuppressWarnings({"unchecked", "EqualsWhichDoesntCheckParameterClass"})
        public final boolean equals(final Object otherObject) {
            if (this == otherObject) {
                return true;
            }
            if (otherObject == null) {
                return false;
            }
            if (Hibernate.getClass(this) != Hibernate.getClass(otherObject)) {
                return false;
            }
            final AbstractEntity<ID> other = (AbstractEntity<ID>) otherObject;
            return Objects.equals(getId(), other.getId());
        }

        @Override
        public final int hashCode() {
            return hash(getId());
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    @ToString
    @Builder
    @Entity
    @Table(name = "persons")
    static class PersonEntity extends AbstractEntity<Long> {

        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = IDENTITY)
        private Long id;

        @Column(name = "name")
        private String name;

        @Column(name = "surname")
        private String surname;

        @Column(name = "patronymic")
        private String patronymic;

        @Column(name = "birth_date")
        private LocalDate birthDate;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    @ToString
    @Builder
    @Entity
    @Table(name = "replicated_persons")
    static class ReplicatedPersonEntity extends AbstractEntity<Long> {

        @Id
        @Column(name = "id")
        private Long id;

        @Column(name = "name")
        private String name;

        @Column(name = "surname")
        private String surname;

        @Column(name = "birth_date")
        private LocalDate birthDate;
    }

    //---------------------------DTO---------------------------
    @lombok.Value
    @AllArgsConstructor
    @Builder
    static class Person implements AbstractDto<Long> {
        Long id;
        String name;
        String surname;
        String patronymic;
        LocalDate birthDate;
    }

    @lombok.Value
    static class ReplicatedPerson implements AbstractDto<Long> {
        Long id;
        String name;
        String surname;
        LocalDate birthDate;

        @Builder
        @JsonCreator
        public ReplicatedPerson(@JsonProperty("id") final Long id,
                                @JsonProperty("name") final String name,
                                @JsonProperty("surname") final String surname,
                                @JsonProperty("birthDate") final LocalDate birthDate) {
            this.id = id;
            this.name = name;
            this.surname = surname;
            this.birthDate = birthDate;
        }
    }

    //---------------------------REPOSITORY---------------------------
    interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    }

    interface ReplicatedPersonRepository extends JpaRepository<ReplicatedPersonEntity, Long> {

    }


    //---------------------------MAPPER---------------------------
    @Component
    static final class PersonMapper extends AbsMapperEntityDto<PersonEntity, Person> {

        public PersonMapper(final ModelMapper modelMapper, final EntityManager entityManager) {
            super(modelMapper, entityManager, PersonEntity.class, Person.class);
        }

        @Override
        protected Person create(final PersonEntity entity) {
            return new Person(
                    entity.getId(),
                    entity.getName(),
                    entity.getSurname(),
                    entity.getPatronymic(),
                    entity.getBirthDate()
            );
        }
    }

    @Component
    static final class ReplicatedPersonMapper extends AbsMapperEntityDto<ReplicatedPersonEntity, ReplicatedPerson> {

        public ReplicatedPersonMapper(final ModelMapper modelMapper, final EntityManager entityManager) {
            super(modelMapper, entityManager, ReplicatedPersonEntity.class, ReplicatedPerson.class);
        }

        @Override
        protected ReplicatedPerson create(final ReplicatedPersonEntity entity) {
            return new ReplicatedPerson(
                    entity.getId(),
                    entity.getName(),
                    entity.getSurname(),
                    entity.getBirthDate()
            );
        }
    }

    //---------------------------SERVICE---------------------------
    @ReplicatedService(topicName = "sync-person", keySerializer = LongSerializer.class)
    static class PersonService extends AbsServiceCRUD<Long, PersonEntity, Person, PersonRepository> {

        public PersonService(final PersonMapper mapper, final PersonRepository repository) {
            super(mapper, repository);
        }
    }

    @Service
    static class ReplicatedPersonService extends AbsServiceCRUD<Long, ReplicatedPersonEntity, ReplicatedPerson, ReplicatedPersonRepository> {

        public ReplicatedPersonService(final ReplicatedPersonMapper mapper, final ReplicatedPersonRepository repository) {
            super(mapper, repository);
        }
    }

    @Component
    static final class KafkaConsumerPersonReplication extends KafkaConsumerReplication<Long, ReplicatedPerson> {
        private static final int WAIT_CONSUMING_SECONDS = 5;

        private final Phaser phaser;

        public KafkaConsumerPersonReplication(final AbsServiceCRUD<Long, ?, ReplicatedPerson, ?> service,
                                              final ObjectMapper objectMapper) {
            super(service, objectMapper, ReplicatedPerson.class);
            phaser = createNotTerminatedPhaser();
        }

        public void setExpectedReplicationCount(final int expectedConsumedRecordCount) {
            rangeClosed(1, phaser.getUnarrivedParties()).forEach(i -> phaser.arriveAndDeregister());
            phaser.bulkRegister(expectedConsumedRecordCount + 1);
        }

        @Override
        @KafkaListener(
                topics = "${kafka.topic.sync-person.name}",
                groupId = "${kafka.topic.sync-person.consumer.group-id}",
                containerFactory = "listenerContainerFactorySyncPerson"
        )
        public void listen(final List<ConsumerRecord<Long, GenericRecord>> records) {
            super.listen(records);
            records.forEach(i -> phaser.arriveAndDeregister());
        }

        public boolean isSuccessConsuming() {
            try {
                final int phaseNumber = phaser.arriveAndDeregister();
                phaser.awaitAdvanceInterruptibly(phaseNumber, WAIT_CONSUMING_SECONDS, SECONDS);
                return true;
            } catch (final InterruptedException exception) {
                currentThread().interrupt();
                return false;
            } catch (final TimeoutException exception) {
                return false;
            }
        }

        private static Phaser createNotTerminatedPhaser() {
            return new Phaser() {
                @Override
                protected boolean onAdvance(final int phase, final int registeredParties) {
                    return false;
                }
            };
        }
    }

    @lombok.Value
    static class PersonName {
        String name;
        String surname;
        String patronymic;
    }

}
