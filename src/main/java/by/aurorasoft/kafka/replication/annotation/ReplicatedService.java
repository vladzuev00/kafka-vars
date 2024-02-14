package by.aurorasoft.kafka.replication.annotation;

import by.aurorasoft.kafka.replication.producer.KafkaProducerReplication;
import org.springframework.stereotype.Service;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Service
@Target(TYPE)
@Retention(RUNTIME)
public @interface ReplicatedService {
    Class<? extends KafkaProducerReplication<?, ?>> replicationProducer();
}
