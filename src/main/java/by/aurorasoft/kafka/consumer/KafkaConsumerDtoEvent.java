package by.aurorasoft.kafka.consumer;

import by.aurorasoft.kafka.model.dtoevent.DtoTransportable;

public abstract class KafkaConsumerDtoEvent<ID, DTO extends DtoTransportable<ID>>
        extends KafkaConsumerGenericRecord<ID, DTO> {

}
