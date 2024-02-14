package by.aurorasoft.kafka.replication.consumer;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor
@Getter
public final class ReplicationConsumingContext {
    private final Function<String, ? extends AbstractDto<?>> dtoMapper;
}
