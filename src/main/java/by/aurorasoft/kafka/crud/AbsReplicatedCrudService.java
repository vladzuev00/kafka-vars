package by.aurorasoft.kafka.crud;

import by.aurorasoft.kafka.model.entityevent.DeleteReplicatedEntityEvent;
import by.aurorasoft.kafka.model.entityevent.ReplicatedEntityEvent;
import by.aurorasoft.kafka.producer.entityevent.KafkaProducerDeleteReplicatedEntityEvent;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.domain.AbstractEntity;
import by.nhorushko.crudgeneric.v2.mapper.AbsMapperEntityDto;
import by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public abstract class AbsReplicatedCrudService<
        ENTITY extends AbstractEntity<UUID>,
        DTO extends AbstractDto<UUID>,
        REPOSITORY extends JpaRepository<ENTITY, UUID>,
        TRANSPORTABLE_DTO>
        extends AbsServiceCRUD<UUID, ENTITY, DTO, REPOSITORY> {
    private final KafkaProducerDeleteReplicatedEntityEvent producerDeletedEntityEvent;

    public AbsReplicatedCrudService(final AbsMapperEntityDto<ENTITY, DTO> mapper,
                                    final REPOSITORY repository,
                                    final String topicName,
                                    final KafkaTemplate<UUID, UUID> kafkaTemplate) {
        super(mapper, repository);
        producerDeletedEntityEvent = new KafkaProducerDeleteReplicatedEntityEvent(topicName, kafkaTemplate);
    }

    @Override
    public final DTO save(final DTO dto) {
        final DTO savedDto = super.save(dto);
        final ReplicatedEntityEvent<DTO> event = new ReplicatedEntityEvent<>(savedDto, NEW);
        producerEntityEvent.send(event);
        return savedDto;
    }

    @Override
    public final List<DTO> saveAll(final Collection<DTO> dtos) {
        final List<DTO> savedDtos = super.saveAll(dtos);
        savedDtos.stream()
                .map(savedDto -> new ReplicatedEntityEvent<>(savedDto, NEW))
                .forEach(producerEntityEvent::send);
        return savedDtos;
    }

    @Override
    public final DTO update(final DTO dto) {
        final DTO updatedDto = super.update(dto);
        final ReplicatedEntityEvent<DTO> event = new ReplicatedEntityEvent<>(updatedDto, UPDATED);
        producerEntityEvent.send(event);
        return updatedDto;
    }

    @Override
    public final void delete(final UUID id) {
        super.delete(id);
        final DeleteReplicatedEntityEvent event = new DeleteReplicatedEntityEvent(id);
        producerDeletedEntityEvent.send(event);
    }
}
