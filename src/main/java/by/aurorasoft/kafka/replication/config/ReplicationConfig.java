package by.aurorasoft.kafka.replication.config;

import by.aurorasoft.kafka.replication.aop.ReplicationAspect;
import by.aurorasoft.kafka.replication.model.TransportableReplication;
import by.aurorasoft.kafka.replication.producer.ReplicationProducerFactory;
import by.nhorushko.crudgeneric.v2.service.AbsServiceRUD;
import org.apache.avro.Schema;
import org.apache.avro.reflect.ReflectData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.List;

@Configuration
@Import({ReplicationProducerFactory.class})
public class ReplicationConfig {

    @Bean
    public Schema replicationSchema() {
        return ReflectData.get().getSchema(TransportableReplication.class);
    }

    @Bean
    public ReplicationAspect replicationKafkaProducers(final List<AbsServiceRUD<?, ?, ?, ?, ?>> services,
                                                       final ReplicationProducerFactory producersFactory) {
        return new ReplicationAspect(producersFactory.create(services));
    }
}
