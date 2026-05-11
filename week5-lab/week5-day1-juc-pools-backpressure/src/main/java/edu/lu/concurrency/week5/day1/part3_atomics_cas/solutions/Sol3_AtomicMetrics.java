package edu.lu.concurrency.week5.day1.part3_atomics_cas.solutions;

import java.util.concurrent.atomic.AtomicInteger;

public class Sol3_AtomicMetrics {
    private final AtomicInteger successes = new AtomicInteger();
    private final AtomicInteger failures = new AtomicInteger();

    public void recordSuccess() {
        successes.incrementAndGet();
    }

    public void recordFailure() {
        failures.incrementAndGet();
    }

    public int successes() {
        return successes.get();
    }

    public int failures() {
        return failures.get();
    }

    public int total() {
        return successes() + failures();
    }

    public double successRatePercent() {
        int total = total();
        if (total == 0) {
            return 0.0;
        }
        return (successes() * 100.0) / total;
    }
}
