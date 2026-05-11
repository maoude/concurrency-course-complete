package edu.lu.concurrency.week5.day1.part3_atomics_cas;

import java.util.concurrent.atomic.AtomicInteger;

public class Demo08_AtomicCounter {
    private final AtomicInteger value = new AtomicInteger();

    public int increment() {
        return value.incrementAndGet();
    }

    public int get() {
        return value.get();
    }
}
