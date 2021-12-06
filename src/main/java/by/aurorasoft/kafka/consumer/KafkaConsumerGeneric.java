package by.aurorasoft.kafka.consumer;

import by.aurorasoft.kafka.variables.KafkaVars;
import org.apache.avro.generic.GenericRecord;
import org.springframework.kafka.annotation.KafkaListener;

import java.time.Instant;

public abstract class KafkaConsumerGeneric<T> {

    public abstract void listen(GenericRecord genericRecord);

    protected abstract T map(GenericRecord record);
}
