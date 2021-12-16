package by.aurorasoft.kafka.producer;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.springframework.kafka.core.KafkaTemplate;

public abstract class KafkaProducerGenericRecordSimple<TOPIC_KEY, MODEL> extends KafkaProducerGenericRecordIntermediary<TOPIC_KEY, MODEL, MODEL> {

    public KafkaProducerGenericRecordSimple(String topicName, KafkaTemplate<TOPIC_KEY, GenericRecord> kafkaTemplate, Schema schema) {
        super(topicName, kafkaTemplate, schema);
    }

    @Override
    protected MODEL convertModelToTransportable(MODEL model) {
        return model;
    }
}
