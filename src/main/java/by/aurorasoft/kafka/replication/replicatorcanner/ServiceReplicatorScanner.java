package by.aurorasoft.kafka.replication.replicatorcanner;

import by.aurorasoft.kafka.replication.annotation.ReplicatedService;
import by.aurorasoft.kafka.replication.replicator.ServiceReplicator;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public final class ServiceReplicatorScanner {

    public List<ServiceReplicator> scan() {
        final Reflections reflections = new Reflections(System.getProperty("java.class.path"));
        Set<Class<?>> serviceTypes = reflections.getTypesAnnotatedWith(ReplicatedService.class);
        return serviceTypes.stream()
                .map(type -> new ServiceReplicator(type, type.getAnnotation(ReplicatedService.class).replicationProducer()))
    }
}
