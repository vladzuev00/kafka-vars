package by.aurorasoft.kafka.model.dtoevent;

import by.aurorasoft.kafka.model.DtoTransportable;

public abstract class UpdatedDtoEventTransportable<SOURCE extends DtoTransportable<?>>
        extends DtoEventTransportable<SOURCE> {

    public UpdatedDtoEventTransportable(final SOURCE source) {
        super(source);
    }
}
