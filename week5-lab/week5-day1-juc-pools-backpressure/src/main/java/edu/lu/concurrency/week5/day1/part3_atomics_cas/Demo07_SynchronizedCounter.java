package edu.lu.concurrency.week5.day1.part3_atomics_cas;

public class Demo07_SynchronizedCounter {
    private int value;

    public synchronized void increment() {
        value++;
    }

    public synchronized int get() {
        return value;
    }
}
