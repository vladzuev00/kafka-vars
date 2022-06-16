package by.aurorasoft.kafka.producer;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.atomic.AtomicLong;

@Getter
@AllArgsConstructor
public class ProducerMetrics {
    private final AtomicLong sentCounter;
    private final AtomicLong successCounter;
    private final AtomicLong failureCounter;

    public ProducerMetrics(Long sentCounter, Long successCounter, Long failureCounter) {
        this.sentCounter = new AtomicLong(sentCounter);
        this.successCounter = new AtomicLong(successCounter);
        this.failureCounter = new AtomicLong(failureCounter);
    }

    public ProducerMetrics() {
        sentCounter = new AtomicLong(0);
        successCounter = new AtomicLong(0);
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

    public ProducerMetrics instance(){
        return new ProducerMetrics(
                new AtomicLong(sentCounter.get()),
                new AtomicLong(successCounter.get()),
                new AtomicLong(failureCounter.get())
        );
    }
}
