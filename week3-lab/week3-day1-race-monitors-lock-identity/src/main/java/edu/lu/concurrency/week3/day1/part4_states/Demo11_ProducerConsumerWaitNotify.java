/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 3
 * Lab Title: Day 1 - Locks, Monitors and Reentrancy
 * Week 3 - Part 4: Thread States
 * Demo11 - ProducerConsumerWaitNotify
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * This is the classic intrinsic-monitor producer/consumer pattern.
 * The shared monitor protects both the queue and the completion flag.
 *
 * wait() releases the monitor and parks the thread until another
 * thread calls notifyAll() on the same monitor.
 *
 * The important correction over a naive version is the use of while
 * loops around wait(): threads must re-check the buffer state after
 * waking because wakeups can be spurious or may race with other
 * consumers.
 * ================================================================
 */

package edu.lu.concurrency.week3.day1.part4_states;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class Demo11_ProducerConsumerWaitNotify {

    private static final class Runner {

        private final List<Integer> integers = new ArrayList<>();
        private final Random random = new Random();
        private final int maxCount = 10;
        private final int totalItems;

        private int nextValue = 0;
        private int producedCount = 0;
        private int consumedCount = 0;
        private boolean closed = false;

        private Runner(int totalItems) {
            this.totalItems = totalItems;
        }

        public void produce() throws InterruptedException {
            while (true) {
                synchronized (this) {
                    while (integers.size() == maxCount) {
                        wait();
                    }

                    if (producedCount == totalItems) {
                        closed = true;
                        notifyAll();
                        return;
                    }

                    integers.add(nextValue);
                    System.out.println(nextValue + " added. Size is now " + integers.size());
                    nextValue++;
                    producedCount++;
                    notifyAll();
                }

                Thread.sleep(new Random().nextInt(3) * 1000L);
            }
        }

        public void consume(String consumerName) throws InterruptedException {
            while (true) {
                synchronized (this) {
                    while (integers.isEmpty() && !closed) {
                        wait();
                    }

                    if (integers.isEmpty()) {
                        return;
                    }

                    System.out.println(consumerName + ": " + integers.remove(0)
                            + " removed. Size is now " + integers.size());
                    consumedCount++;
                    notifyAll();
                }

                Thread.sleep(random.nextInt(3) * 1000L);
            }
        }

        public int getProducedCount() {
            return producedCount;
        }

        public int getConsumedCount() {
            return consumedCount;
        }

        public int getRemainingCount() {
            return integers.size();
        }
    }

    public static void main(String[] args) {
        new Demo11_ProducerConsumerWaitNotify().runApp();
    }

    private void runApp() {
        final Runner runner = new Runner(20);

        Thread producerThread = new Thread(() -> {
            try {
                runner.produce();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "producer-thread");

        Thread consumer1 = new Thread(() -> {
            try {
                runner.consume("consumer-1");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "consumer-1");

        Thread consumer2 = new Thread(() -> {
            try {
                runner.consume("consumer-2");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "consumer-2");

        producerThread.start();
        consumer1.start();
        consumer2.start();

        try {
            producerThread.join();
            consumer1.join();
            consumer2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Produced=" + runner.getProducedCount()
                + " Consumed=" + runner.getConsumedCount()
                + " Remaining=" + runner.getRemainingCount());
    }
}
