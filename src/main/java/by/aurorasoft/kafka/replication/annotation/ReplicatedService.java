package by.aurorasoft.kafka.replication.annotation;

import org.apache.kafka.common.serialization.Serializer;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Service
@Target(TYPE)
@Retention(RUNTIME)
public @interface ReplicatedService {
    String topicName();
    Class<? extends Serializer<?>> keySerializer();
}
