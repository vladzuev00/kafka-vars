package by.aurorasoft.kafka.replication.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

//TODO: save only this annotation, other remove
@Target(METHOD)
@Retention(RUNTIME)
public @interface ReplicatedUpdate {

}
