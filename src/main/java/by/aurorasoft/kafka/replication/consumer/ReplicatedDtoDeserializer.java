package by.aurorasoft.kafka.replication.consumer;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ReplicatedDtoDeserializer<DTO extends AbstractDto<?>> {
    private final ObjectMapper objectMapper;
    private final Class<DTO> dtoType;

    public DTO deserializeDto(final String json) {
        try {
            return objectMapper.readValue(json, dtoType);
        } catch (final JsonProcessingException cause) {
            throw new ReplicatedDtoDeserializationException(cause);
        }
    }

    static final class ReplicatedDtoDeserializationException extends RuntimeException {

        @SuppressWarnings("unused")
        public ReplicatedDtoDeserializationException() {

        }

        @SuppressWarnings("unused")
        public ReplicatedDtoDeserializationException(final String description) {
            super(description);
        }

        public ReplicatedDtoDeserializationException(final Exception cause) {
            super(cause);
        }

        @SuppressWarnings("unused")
        public ReplicatedDtoDeserializationException(final String description, final Exception cause) {
            super(description, cause);
        }
    }
}
