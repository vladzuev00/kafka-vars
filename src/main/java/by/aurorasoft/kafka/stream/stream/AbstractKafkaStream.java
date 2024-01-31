package by.aurorasoft.kafka.stream.stream;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler;

import java.util.Properties;

/**
 * Abstract class for Kafka Streams applications.
 * Provides a basic structure for creating and managing Kafka Streams pipelines.
 */
@Slf4j
public abstract class AbstractKafkaStream {

    protected KafkaStreams streams;
    protected Properties properties;

    /**
     * Fills in the properties for Kafka Streams.
     * This method should be overridden in subclasses to configure properties.
     *
     * @param properties Properties to configure Kafka Streams
     */
    protected abstract void fillProperties(Properties properties);

    /**
     * Builds the Kafka Streams pipeline.
     * This method should be overridden in subclasses to define the stream logic.
     *
     * @param builder StreamsBuilder for building Kafka Streams pipelines
     */
    protected abstract void buildStream(StreamsBuilder builder);

    /**
     * Starts the Kafka Streams application.
     * Initializes properties, builds, and starts the stream.
     */
    public void start() {
        properties = new Properties();
        fillProperties(properties);

        StreamsBuilder builder = new StreamsBuilder();
        buildStream(builder);

        streams = new KafkaStreams(builder.build(), properties);
        streams.setUncaughtExceptionHandler(uncaughtExceptionHandler());

        streams.start();
    }

    protected StreamsUncaughtExceptionHandler uncaughtExceptionHandler(){
        return e -> StreamsUncaughtExceptionHandler.StreamThreadExceptionResponse.SHUTDOWN_APPLICATION;
    }

    /**
     * Closes the Kafka Streams application.
     * Stops the stream and releases resources.
     */
    public void close() {
        if (streams != null) {
            streams.close();
        }
    }
}
