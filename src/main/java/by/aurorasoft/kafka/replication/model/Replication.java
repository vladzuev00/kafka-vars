package by.aurorasoft.kafka.replication.model;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class Replication<ID, DTO extends AbstractDto<ID>> {
    private final ReplicationOperation operation;
    private final DTO dto;
}
