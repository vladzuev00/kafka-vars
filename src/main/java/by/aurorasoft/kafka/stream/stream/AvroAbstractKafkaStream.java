package by.aurorasoft.kafka.stream.stream;

import by.aurorasoft.kafka.consumer.GenericRecordConverter;
import org.apache.kafka.streams.StreamsBuilder;

import java.util.Properties;

public abstract class AvroAbstractKafkaStream extends AbstractKafkaStream implements GenericRecordConverter {

}
