package by.aurorasoft.kafka.replication.producer;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ReplicatedDtoSerializer<ID, DTO extends AbstractDto<ID>> {
    private final ObjectMapper objectMapper;

    public String serialize(final DTO dto) {
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (final JsonProcessingException cause) {
            throw new ReplicatedDtoSerializationException(cause);
        }
    }

    static final class ReplicatedDtoSerializationException extends RuntimeException {

        @SuppressWarnings("unused")
        public ReplicatedDtoSerializationException() {

        }

        @SuppressWarnings("unused")
        public ReplicatedDtoSerializationException(final String description) {
            super(description);
        }

        public ReplicatedDtoSerializationException(final Exception cause) {
            super(cause);
        }

        @SuppressWarnings("unused")
        public ReplicatedDtoSerializationException(final String description, final Exception cause) {
            super(description, cause);
        }
    }
}
