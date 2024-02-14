package by.aurorasoft.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public abstract class KafkaConsumerAbstract<TOPIC_KEY, TOPIC_VALUE> {

    public abstract void listen(ConsumerRecord<TOPIC_KEY, TOPIC_VALUE> record);
}
