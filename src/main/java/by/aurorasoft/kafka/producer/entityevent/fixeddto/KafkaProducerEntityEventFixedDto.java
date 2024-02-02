package by.aurorasoft.kafka.producer.entityevent.fixeddto;

import by.aurorasoft.kafka.model.entityevent.fixeddto.ReplicatedEntityEventFixingDto;
import by.aurorasoft.kafka.producer.entityevent.KafkaProducerReplicatedEntityEvent;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.function.Function;

public abstract class KafkaProducerEntityEventFixedDto<
        ID,
        DTO extends AbstractDto<ID>,
        TRANSPORTABLE_DTO,
        EVENT extends ReplicatedEntityEventFixingDto<ID, DTO>
        >
        extends KafkaProducerReplicatedEntityEvent<ID, TRANSPORTABLE_DTO, EVENT> {

    public KafkaProducerEntityEventFixedDto(final String topicName,
                                            final KafkaTemplate<ID, GenericRecord> kafkaTemplate,
                                            final Schema schema,
                                            final Function<DTO, TRANSPORTABLE_DTO> dtoMapper) {
        super(topicName, kafkaTemplate, schema);
    }


    @Override
    protected final TRANSPORTABLE_DTO convertModelToTransportable(final EVENT event) {
        return mapToTransportable(event.getDto());
    }

    protected abstract TRANSPORTABLE_DTO mapToTransportable(final DTO dto);
}
