package by.aurorasoft.kafka.model.dtoevent;

public abstract class UpdatedDtoEventTransportable<SOURCE extends DtoTransportable<?>>
        extends DtoEventTransportable<SOURCE> {

    public UpdatedDtoEventTransportable(final SOURCE source) {
        super(source);
    }
}
