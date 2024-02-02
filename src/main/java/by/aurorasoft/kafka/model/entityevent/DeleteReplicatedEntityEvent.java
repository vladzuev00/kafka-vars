package by.aurorasoft.kafka.model.entityevent;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public final class DeleteReplicatedEntityEvent implements ReplicatedEntityEvent {
    private final UUID entityId;
}
