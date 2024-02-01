package by.aurorasoft.kafka.model.dtoevent;

import by.aurorasoft.kafka.model.DtoTransportable;

public abstract class NewDtoEventTransportable<SOURCE extends DtoTransportable<?>> extends DtoEventTransportable<SOURCE> {

    public NewDtoEventTransportable(final SOURCE source) {
        super(source);
    }
}
