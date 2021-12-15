package by.aurorasoft.kafka.consumer;

import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.common.internals.Topic;

import java.time.Instant;

public abstract class KafkaConsumerGenericRecord<TOPIC_KEY, POJO> extends KafkaConsumerAbstract<TOPIC_KEY, GenericRecord> {

    protected abstract POJO map(GenericRecord record);

    protected long getLong(GenericRecord record, String name) {
        return (long) record.get(name);
    }

    protected int getInt(GenericRecord record, String name) {
        return (int) record.get(name);
    }

    protected long getLongObj(GenericRecord record, String name) {
        return (Long) record.get(name);
    }

    protected float getFloat(GenericRecord record, String name) {
        return (float) record.get(name);
    }

    protected String getString(GenericRecord record, String name) {
        return record.get(name).toString();
    }

    protected boolean getBoolean(GenericRecord record, String name) {
        return (boolean) record.get(name);
    }

    protected Instant getInstant(GenericRecord record, String name) {
        return Instant.ofEpochSecond((long) record.get(name));
    }
}
