package by.aurorasoft.kafka.replication.it.kafka.configuration;

import by.aurorasoft.kafka.replication.model.TransportableReplication;
import org.apache.avro.Schema;
import org.apache.avro.reflect.ReflectData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchemaConfiguration {

    @Bean
    public Schema transportableReplicationSchema() {
        return ReflectData.get().getSchema(TransportableReplication.class);
    }
}
