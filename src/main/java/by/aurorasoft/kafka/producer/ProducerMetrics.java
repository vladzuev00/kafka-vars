package by.aurorasoft.kafka.producer;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.concurrent.atomic.AtomicLong;

@Value
@AllArgsConstructor
public class ProducerMetrics {
    AtomicLong sentCounter;
    AtomicLong successCounter;
    AtomicLong skipCounter;
    AtomicLong failureCounter;


    public ProducerMetrics(Long sentCounter, Long successCounter, Long skipCounter, Long failureCounter) {
        this.sentCounter = new AtomicLong(sentCounter);
        this.successCounter = new AtomicLong(successCounter);
        this.skipCounter = new AtomicLong(skipCounter);
        this.failureCounter = new AtomicLong(failureCounter);
    }

    public ProducerMetrics() {
        sentCounter = new AtomicLong(0);
        successCounter = new AtomicLong(0);
        skipCounter = new AtomicLong(0);
        failureCounter = new AtomicLong(0);
    }

    public void sentCounterIncrement() {
        sentCounter.incrementAndGet();
    }

    public void successCounterIncrement() {
        successCounter.incrementAndGet();
    }

    public void failureCounterIncrement() {
        failureCounter.incrementAndGet();
    }

    public long getNotApprovedCount() {
        return (sentCounter.get() - (successCounter.get() + failureCounter.get()));
    }

    public void skipCounterIncrement() {
        skipCounter.incrementAndGet();
    }

    public ProducerMetrics instance() {
        return new ProducerMetrics(
                new AtomicLong(sentCounter.get()),
                new AtomicLong(successCounter.get()),
                new AtomicLong(skipCounter.get()),
                new AtomicLong(failureCounter.get())
        );
    }
}
