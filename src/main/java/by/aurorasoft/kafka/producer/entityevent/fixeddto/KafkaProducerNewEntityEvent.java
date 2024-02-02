package by.aurorasoft.kafka.producer.entityevent.fixeddto;

import by.aurorasoft.kafka.model.entityevent.fixeddto.CreateReplicatedEntityEvent;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.springframework.kafka.core.KafkaTemplate;

public final class KafkaProducerNewEntityEvent<ID, DTO extends AbstractDto<ID>, TRANSPORTABLE_DTO>
        extends KafkaProducerEntityEventFixedDto<ID, DTO, TRANSPORTABLE_DTO, CreateReplicatedEntityEvent<ID, DTO>> {

    public KafkaProducerNewEntityEvent(final String topicName,
                                       final KafkaTemplate<ID, GenericRecord> kafkaTemplate,
                                       final Schema schema) {
        super(topicName, kafkaTemplate, schema);
    }
}
