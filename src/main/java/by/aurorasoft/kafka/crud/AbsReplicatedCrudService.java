package by.aurorasoft.kafka.crud;

import by.aurorasoft.kafka.model.entityevent.DeleteReplicatedEntityEvent;
import by.aurorasoft.kafka.model.entityevent.ReplicatedEntityEvent;
import by.aurorasoft.kafka.producer.entityevent.KafkaProducerDeletedEntityEvent;
import by.aurorasoft.kafka.producer.entityevent.fixeddto.KafkaProducerNewEntityEvent;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.domain.AbstractEntity;
import by.nhorushko.crudgeneric.v2.mapper.AbsMapperEntityDto;
import by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
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
    private final KafkaProducerDeletedEntityEvent<ENTITY_ID> producerDeletedEntityEvent;
    private final KafkaProducerNewEntityEvent<ENTITY_ID, DTO, TRANSPORTABLE_DTO>

    public AbsReplicatedCrudService(final AbsMapperEntityDto<ENTITY, DTO> mapper, final REPOSITORY repository,
                                    final String topicName,
                                    final KafkaTemplate<ENTITY_ID, GenericRecord> kafkaTemplate,
                                    final Schema schema) {
        super(mapper, repository);
        producerDeletedEntityEvent = new KafkaProducerDeletedEntityEvent<>(topicName, kafkaTemplate, schema);
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
    public final void delete(final ENTITY_ID id) {
        super.delete(id);
        final DeleteReplicatedEntityEvent event = new DeleteReplicatedEntityEvent(id);
        producerDeletedEntityEvent.send(event);
    }
}
