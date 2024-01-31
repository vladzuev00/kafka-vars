package by.aurorasoft.kafka.stream.processor;

import by.aurorasoft.kafka.stream.stream.AbstractKafkaStream;
import lombok.RequiredArgsConstructor;


import java.util.List;


@RequiredArgsConstructor
public class KafkaStreamsProcessor {
    private final List<AbstractKafkaStream> kafkaStreams;

    public void run() {
        kafkaStreams.forEach(this::start);
    }

    private void start(AbstractKafkaStream stream) {
        stream.start();
        Runtime.getRuntime().addShutdownHook(new Thread(stream::close));
    }
}
