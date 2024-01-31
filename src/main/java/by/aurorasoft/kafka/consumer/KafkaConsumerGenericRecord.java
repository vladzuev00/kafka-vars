package by.aurorasoft.kafka.consumer;

import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public abstract class KafkaConsumerGenericRecord<TOPIC_KEY, POJO>
        extends KafkaConsumerAbstract<TOPIC_KEY, GenericRecord>
        implements GenericRecordConverter {

    protected POJO map(ConsumerRecord<TOPIC_KEY, GenericRecord> consumerRecord) {
        return map(consumerRecord.value());
    }

    protected abstract POJO map(GenericRecord record);

}
