package by.aurorasoft.kafka.model.dtoevent;

public abstract class NewDtoEventTransportable<SOURCE extends DtoTransportable<?>> extends DtoEventTransportable<SOURCE> {

    public NewDtoEventTransportable(final SOURCE source) {
        super(source);
    }
}
