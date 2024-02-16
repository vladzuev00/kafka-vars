package by.aurorasoft.kafka.replication.configuration;

import by.aurorasoft.kafka.replication.aop.ReplicationAspect;
import by.aurorasoft.kafka.replication.model.TransportableReplication;
import by.aurorasoft.kafka.replication.producer.ReplicationProducersFactory;
import by.nhorushko.crudgeneric.v2.service.AbsServiceRUD;
import org.apache.avro.Schema;
import org.apache.avro.reflect.ReflectData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.List;

@Configuration
@Import({ReplicationProducersFactory.class})
public class ReplicationConfiguration {

    @Bean
    public Schema replicationSchema() {
        return ReflectData.get().getSchema(TransportableReplication.class);
    }

    @Bean
    public ReplicationAspect replicationKafkaProducers(final List<AbsServiceRUD<?, ?, ?, ?, ?>> services,
                                                       final ReplicationProducersFactory producersFactory) {
        return new ReplicationAspect(producersFactory.create(services));
    }
}
