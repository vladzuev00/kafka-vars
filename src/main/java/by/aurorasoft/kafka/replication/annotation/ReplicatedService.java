package by.aurorasoft.kafka.replication.annotation;

import by.aurorasoft.kafka.replication.mapper.TransportableReplicatedDtoFactory;
import by.aurorasoft.kafka.replication.producer.KafkaProducerReplication;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
public @interface ReplicatedService {
    Class<? extends KafkaProducerReplication<?>> replicationProducer();
    Class<? extends TransportableReplicatedDtoFactory<?>> transportableDtoFactory();
}
