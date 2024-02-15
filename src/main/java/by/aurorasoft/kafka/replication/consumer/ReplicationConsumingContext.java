package by.aurorasoft.kafka.replication.consumer;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ReplicationConsumingContext<ID, DTO extends AbstractDto<ID>> {
    private final ObjectMapper objectMapper;
    private final Class<DTO> dtoType;

    public DTO deserializeDto(final String view) {
        try {
            return objectMapper.readValue(view, dtoType);
        } catch (final JsonProcessingException cause) {
            throw new ReplicationConsumingException(cause);
        }
    }

    static final class ReplicationConsumingException extends RuntimeException {

        @SuppressWarnings("unused")
        public ReplicationConsumingException() {

        }

        @SuppressWarnings("unused")
        public ReplicationConsumingException(final String description) {
            super(description);
        }

        public ReplicationConsumingException(final Exception cause) {
            super(cause);
        }

        @SuppressWarnings("unused")
        public ReplicationConsumingException(final String description, final Exception cause) {
            super(description, cause);
        }
    }
}
