package by.aurorasoft.kafka.replication.consumer;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ReplicationConsumingContext<ID, DTO extends AbstractDto<ID>> {
    private final ObjectMapper objectMapper;
    private final Class<DTO> dtoType;

    public DTO deserializeDto(final String json) {
        try {
            return objectMapper.readValue(json, dtoType);
        } catch (final JsonProcessingException cause) {
            throw new DtoJsonDeserializationException(cause);
        }
    }

    static final class DtoJsonDeserializationException extends RuntimeException {

        @SuppressWarnings("unused")
        public DtoJsonDeserializationException() {

        }

        @SuppressWarnings("unused")
        public DtoJsonDeserializationException(final String description) {
            super(description);
        }

        public DtoJsonDeserializationException(final Exception cause) {
            super(cause);
        }

        @SuppressWarnings("unused")
        public DtoJsonDeserializationException(final String description, final Exception cause) {
            super(description, cause);
        }
    }
}
