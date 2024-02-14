package by.aurorasoft.kafka.replication.producer;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor
public final class ReplicationProducingContext<ID, DTO extends AbstractDto<ID>> {
    private final Function<DTO, Object> dtoToJsonViewMapper;
    private final ObjectMapper objectMapper;

    public String mapToJsonView(final DTO dto) {
        try {
            final Object jsonView = dtoToJsonViewMapper.apply(dto);
            return objectMapper.writeValueAsString(jsonView);
        } catch (final JsonProcessingException cause) {
            throw new ReplicationProducingException(cause);
        }
    }

    private static final class ReplicationProducingException extends RuntimeException {

        @SuppressWarnings("unused")
        public ReplicationProducingException() {

        }

        @SuppressWarnings("unused")
        public ReplicationProducingException(final String description) {
            super(description);
        }

        public ReplicationProducingException(final Exception cause) {
            super(cause);
        }

        @SuppressWarnings("unused")
        public ReplicationProducingException(final String description, final Exception cause) {
            super(description, cause);
        }
    }
}
