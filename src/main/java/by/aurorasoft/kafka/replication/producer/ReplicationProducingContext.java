package by.aurorasoft.kafka.replication.producer;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor
public final class ReplicationProducingContext<ID, DTO extends AbstractDto<ID>> {
    private final Function<DTO, Object> dtoProjector;
    private final ObjectMapper objectMapper;

    public String projectDtoAsJson(final DTO dto) {
        try {
            final Object project = dtoProjector.apply(dto);
            return objectMapper.writeValueAsString(project);
        } catch (final JsonProcessingException cause) {
            throw new DtoProjectionException(cause);
        }
    }

    static final class DtoProjectionException extends RuntimeException {

        @SuppressWarnings("unused")
        public DtoProjectionException() {

        }

        @SuppressWarnings("unused")
        public DtoProjectionException(final String description) {
            super(description);
        }

        public DtoProjectionException(final Exception cause) {
            super(cause);
        }

        @SuppressWarnings("unused")
        public DtoProjectionException(final String description, final Exception cause) {
            super(description, cause);
        }
    }
}
