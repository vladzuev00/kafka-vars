package by.aurorasoft.kafka.model.dtoevent;

import by.aurorasoft.kafka.model.DtoTransportable;

public abstract class RemovedDtoEventTransportable<SOURCE extends DtoTransportable<?>>
        extends DtoEventTransportable<SOURCE> {

    public RemovedDtoEventTransportable(final SOURCE source) {
        super(source);
    }

}
