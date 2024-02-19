package by.aurorasoft.kafka.replication.serviceholder;

import by.aurorasoft.kafka.replication.annotation.ReplicatedService;
import by.nhorushko.crudgeneric.v2.service.AbsServiceRUD;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.aop.framework.AopProxyUtils.ultimateTargetClass;

@Component
@Getter
public final class ReplicatedServiceHolder {
    private final List<AbsServiceRUD<?, ?, ?, ?, ?>> services;

    public ReplicatedServiceHolder(final List<AbsServiceRUD<?, ?, ?, ?, ?>> services) {
        this.services = findReplicated(services);
    }

    private static List<AbsServiceRUD<?, ?, ?, ?, ?>> findReplicated(final List<AbsServiceRUD<?, ?, ?, ?, ?>> services) {
        return services.stream()
                .filter(ReplicatedServiceHolder::isReplicated)
                .toList();
    }

    private static boolean isReplicated(final AbsServiceRUD<?, ?, ?, ?, ?> service) {
        return ultimateTargetClass(service).isAnnotationPresent(ReplicatedService.class);
    }
}
