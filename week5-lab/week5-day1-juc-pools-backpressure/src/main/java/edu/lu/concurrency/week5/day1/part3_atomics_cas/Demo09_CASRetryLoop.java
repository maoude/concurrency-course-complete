package edu.lu.concurrency.week5.day1.part3_atomics_cas;

import java.util.concurrent.atomic.AtomicInteger;

public class Demo09_CASRetryLoop {
    private final AtomicInteger value = new AtomicInteger();

    public int addPositive(int delta) {
        if (delta < 0) {
            throw new IllegalArgumentException("delta must be non-negative");
        }
        while (true) {
            int current = value.get();
            int next = current + delta;
            if (value.compareAndSet(current, next)) {
                return next;
            }
        }
    }
}
