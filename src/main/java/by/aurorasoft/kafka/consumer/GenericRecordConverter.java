package by.aurorasoft.kafka.consumer;

import org.apache.avro.generic.GenericRecord;

import java.time.Instant;

import static java.lang.Enum.valueOf;

public interface GenericRecordConverter {
    default long getLong(GenericRecord record, String name) {
        return (long) record.get(name);
    }

    default int getInt(GenericRecord record, String name) {
        return (int) record.get(name);
    }

    default long getLongObj(GenericRecord record, String name) {
        return (Long) record.get(name);
    }

    default float getFloat(GenericRecord record, String name) {
        return (float) record.get(name);
    }

    default double getDouble(GenericRecord record, String name) {
        return (double) record.get(name);
    }

    default String getString(GenericRecord record, String name) {
        return record.get(name).toString();
    }

    default boolean getBoolean(GenericRecord record, String name) {
        return (boolean) record.get(name);
    }

    default Instant getInstant(GenericRecord record, String name) {
        return Instant.ofEpochSecond((long) record.get(name));
    }

    default <T> T getObject(GenericRecord record, String name) {
        return (T) record.get(name);
    }

    //TODO: test
    default <E extends Enum<E>> E getEnumObject(final GenericRecord record, final String name, final Class<E> enumType) {
        return valueOf(enumType, getString(record, name));
    }
}
