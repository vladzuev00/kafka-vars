package by.aurorasoft.kafka.replication.model;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor
@Getter
public final class ReplicationProducingContext<DTO extends AbstractDto<?>> {
    private final Function<DTO, TransportableDto> dtoToTransportableMapper;
}
