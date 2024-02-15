package by.aurorasoft.kafka.replication.it.kafka.consumer;

import by.aurorasoft.kafka.replication.consumer.KafkaConsumerReplication;
import by.aurorasoft.kafka.replication.it.crud.dto.ReplicatedPerson;
import by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeoutException;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.TimeUnit.SECONDS;

@Component
public final class KafkaConsumerPersonReplication extends KafkaConsumerReplication<Long, ReplicatedPerson> {
    private static final int WAIT_CONSUMING_SECONDS = 5;

    private final Phaser phaser;

    public KafkaConsumerPersonReplication(final AbsServiceCRUD<Long, ?, ReplicatedPerson, ?> service,
                                          final ObjectMapper objectMapper) {
        super(service, objectMapper, ReplicatedPerson.class);
        phaser = new Phaser(2);
    }

    @Override
    @KafkaListener(
            topics = "${kafka.topic.sync-person.name}",
            groupId = "${kafka.topic.sync-person.consumer.group-id}",
            containerFactory = "listenerContainerFactorySyncPerson"
    )
    public void listen(final List<ConsumerRecord<Long, GenericRecord>> records) {
        super.listen(records);
        phaser.arrive();
    }

    public boolean isSuccessConsuming() {
        try {
            final int phaseNumber = phaser.arrive();
            phaser.awaitAdvanceInterruptibly(phaseNumber, WAIT_CONSUMING_SECONDS, SECONDS);
            return true;
        } catch (final InterruptedException exception) {
            currentThread().interrupt();
            return false;
        } catch (final TimeoutException exception) {
            return false;
        }
    }
}
