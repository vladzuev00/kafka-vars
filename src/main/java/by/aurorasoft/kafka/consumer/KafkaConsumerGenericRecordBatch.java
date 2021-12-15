package by.aurorasoft.kafka.consumer;

import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.List;

public abstract class KafkaConsumerGenericRecordBatch<TOPIC_KEY, POJO> extends KafkaConsumerGenericRecord<TOPIC_KEY, POJO> {

    public abstract void listen(List<ConsumerRecord<TOPIC_KEY, GenericRecord>> records);

    @Override
    public void listen(ConsumerRecord<TOPIC_KEY, GenericRecord> record) {
        throw new UnsupportedOperationException();
    }
}
