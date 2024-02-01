package by.aurorasoft.kafka.model.dtoevent;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
@Getter
@Accessors(makeFinal = true)
public abstract class DtoEventTransportable<SOURCE extends DtoTransportable<?>> {
    private final SOURCE source;
}
