package by.aurorasoft.kafka.crud;

import by.aurorasoft.kafka.model.entityevent.EntityEventTransportable;
import by.aurorasoft.kafka.producer.KafkaProducerEntityEvent;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.domain.AbstractEntity;
import by.nhorushko.crudgeneric.v2.mapper.AbsMapperEntityDto;
import by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

import static by.aurorasoft.kafka.model.entityevent.EntityEventTransportable.EntityEventType.NEW;
import static by.aurorasoft.kafka.model.entityevent.EntityEventTransportable.EntityEventType.UPDATED;

public abstract class AbsReplicatedCrudService<
        ENTITY_ID,
        ENTITY extends AbstractEntity<ENTITY_ID>,
        DTO extends AbstractDto<ENTITY_ID>,
        REPOSITORY extends JpaRepository<ENTITY, ENTITY_ID>,
        TRANSPORTABLE
        >
        extends AbsServiceCRUD<ENTITY_ID, ENTITY, DTO, REPOSITORY> {
    private final KafkaProducerEntityEvent<ENTITY_ID, DTO, TRANSPORTABLE> producerEntityEvent;

    public AbsReplicatedCrudService(final AbsMapperEntityDto<ENTITY, DTO> mapper,
                                    final REPOSITORY repository,
                                    final KafkaProducerEntityEvent<ENTITY_ID, DTO, TRANSPORTABLE> producerEntityEvent) {
        super(mapper, repository);
        this.producerEntityEvent = producerEntityEvent;
    }

    @Override
    public final DTO save(final DTO dto) {
        final DTO savedDto = super.save(dto);
        final EntityEventTransportable<DTO> event = new EntityEventTransportable<>(savedDto, NEW);
        producerEntityEvent.send(event);
        return savedDto;
    }

    @Override
    public final List<DTO> saveAll(final Collection<DTO> dtos) {
        final List<DTO> savedDtos = super.saveAll(dtos);
        savedDtos.stream()
                .map(savedDto -> new EntityEventTransportable<>(savedDto, NEW))
                .forEach(producerEntityEvent::send);
        return savedDtos;
    }

    @Override
    public final DTO update(final DTO dto) {
        final DTO updatedDto = super.update(dto);
        final EntityEventTransportable<DTO> event = new EntityEventTransportable<>(updatedDto, UPDATED);
        producerEntityEvent.send(event);
        return updatedDto;
    }

    @Override
    public final void delete(final ENTITY_ID id) {
        super.delete();
        final DTO removedDto = repository.getOne()
    }
}
