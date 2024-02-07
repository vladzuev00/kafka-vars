package by.aurorasoft.kafka.replication.it.crud.dto;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor
@Builder
public class PersonReplication implements AbstractDto<Long> {
    Long id;
    String name;
    String surname;
}
