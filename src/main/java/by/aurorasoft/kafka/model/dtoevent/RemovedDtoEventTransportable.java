package by.aurorasoft.kafka.model.dtoevent;

public abstract class RemovedDtoEventTransportable<SOURCE extends DtoTransportable<?>>
        extends DtoEventTransportable<SOURCE> {

    public RemovedDtoEventTransportable(final SOURCE source) {
        super(source);
    }

}
