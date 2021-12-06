package by.aurorasoft.kafka.consumer;

import org.apache.avro.generic.GenericRecord;

import java.time.Instant;

public abstract class KafkaConsumerGenericRecord <POJO> extends KafkaConsumerAbstract<GenericRecord> {

    protected abstract POJO map(GenericRecord record);

    protected long getLong(GenericRecord record, String name) {
        return (long) record.get(name);
    }

    protected int getInt(GenericRecord record, String name){
        return (int) record.get(name);
    }

    protected long getLongObj(GenericRecord record, String name) {
        return (Long) record.get(name);
    }

    protected String getString(GenericRecord record, String name){
        return record.get(name).toString();
    }

    protected Instant getInstant(GenericRecord record, String name){
        return Instant.ofEpochSecond((long) record.get(name));
    }
}
