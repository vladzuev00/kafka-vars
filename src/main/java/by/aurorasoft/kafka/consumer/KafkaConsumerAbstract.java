package by.aurorasoft.kafka.consumer;

import org.apache.avro.generic.GenericRecord;

public abstract class KafkaConsumerAbstract<TOPIC_VALUE> {

    public abstract void listen(TOPIC_VALUE topicValue);
}
