package by.aurorasoft.kafka.consumer;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;

public abstract class KafkaConsumerEntityEvent<ID, DTO extends AbstractDto<ID>> extends KafkaConsumerGenericRecord<ID, DTO> {

}
