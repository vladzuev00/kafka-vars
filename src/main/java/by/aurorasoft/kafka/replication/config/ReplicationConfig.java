package by.aurorasoft.kafka.replication.config;

import by.aurorasoft.kafka.replication.aop.ReplicationAspect;
import by.aurorasoft.kafka.replication.holder.KafkaProducerReplicationHolder;
import by.aurorasoft.kafka.replication.model.TransportableReplication;
import by.aurorasoft.kafka.replication.producer.KafkaProducerReplicationHolderFactory;
import org.apache.avro.Schema;
import org.apache.avro.reflect.ReflectData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(
        {
                KafkaProducerReplicationHolderFactory.class,
                ReplicationAspect.class
        }
)
public class ReplicationConfig {

    @Bean
    public Schema replicationSchema() {
        return ReflectData.get().getSchema(TransportableReplication.class);
    }

    @Bean
    public KafkaProducerReplicationHolder kafkaProducerReplicationHolder(final KafkaProducerReplicationHolderFactory factory) {
        return factory.create();
    }
}
