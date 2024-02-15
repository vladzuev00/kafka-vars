package by.aurorasoft.kafka.replication.producer;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ReplicationProducingContext<ID, DTO extends AbstractDto<ID>> {
    private final ObjectMapper objectMapper;

    public String serializeToJson(final DTO dto) {
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (final JsonProcessingException cause) {
            throw new DtoJsonSerializationException(cause);
        }
    }

    static final class DtoJsonSerializationException extends RuntimeException {

        @SuppressWarnings("unused")
        public DtoJsonSerializationException() {

        }

        @SuppressWarnings("unused")
        public DtoJsonSerializationException(final String description) {
            super(description);
        }

        public DtoJsonSerializationException(final Exception cause) {
            super(cause);
        }

        @SuppressWarnings("unused")
        public DtoJsonSerializationException(final String description, final Exception cause) {
            super(description, cause);
        }
    }
}
