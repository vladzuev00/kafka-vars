package by.aurorasoft.kafka.replication.it.kafka.configuration;

import by.aurorasoft.kafka.replication.it.model.TransportablePersonReplication;
import org.apache.avro.Schema;
import org.apache.avro.reflect.ReflectData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchemaConfiguration {

    @Bean
    public ReflectData reflectData() {
        return ReflectData.get();
    }

    @Bean
    public Schema transportablePersonReplicationSchema(final ReflectData reflectData) {
        return reflectData.getSchema(TransportablePersonReplication.class);
    }
}
