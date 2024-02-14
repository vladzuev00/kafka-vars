package by.aurorasoft.kafka.replication.it.crud.dto;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
public class PersonReplication implements AbstractDto<Long> {
    Long id;
    String name;
    String surname;

    @Builder
    @JsonCreator
    public PersonReplication(@JsonProperty("id") final Long id,
                             @JsonProperty("name") final String name,
                             @JsonProperty("surname") final String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }
}
