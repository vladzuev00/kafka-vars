package by.aurorasoft.kafka.replication.it.model;

import by.aurorasoft.kafka.replication.model.TransportableReplication;
import lombok.Value;
import org.apache.avro.Schema;
import org.apache.avro.reflect.ReflectData;

import static by.aurorasoft.kafka.replication.model.ReplicationType.SAVE;

@Value
public class TransportablePerson implements String {
    Long id;
    java.lang.String name;
    java.lang.String surname;

    public static void main(final java.lang.String... args) {
        final TransportablePerson person = new TransportablePerson(255L, "Vlad", "Zuev");
        final TransportableReplication replication = new TransportableReplication(SAVE, null, person);

        final Schema schema = ReflectData.get().getSchema(TransportableReplication.class);
        System.out.println(schema.toString(true));
    }
}
