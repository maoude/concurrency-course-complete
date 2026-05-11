/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 3
 * Lab Title: Day 1 - Locks, Monitors and Reentrancy
 * Week 3 – Part 4: Thread States
 * Demo12 - Event Storage: Bounded Buffer with wait() / notify()
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * This demo shows the simplest correct form of the bounded-buffer
 * (producer-consumer) pattern using only Java intrinsic monitors:
 *   • synchronized methods on the shared EventStorage
 *   • wait()  — park the calling thread and release the monitor
 *   • notify() — wake ONE waiting thread when the condition changes
 *
 * Compared to Demo11, this version uses:
 *   - A Queue<Date> instead of a List<Integer>
 *   - A SINGLE producer thread and a SINGLE consumer thread
 *   - notify() instead of notifyAll()
 *
 * WHY notify() IS SAFE HERE (but risky in general)
 * ---------------------------------------------------------------
 * With exactly one producer waiting on "full" and one consumer
 * waiting on "empty", notify() wakes the right thread every time.
 * With multiple producers OR multiple consumers, notify() can wake
 * the wrong thread → starvation. Prefer notifyAll() for safety.
 *
 * WAIT/NOTIFY CONTRACT
 * ---------------------------------------------------------------
 *   1. Acquire the monitor (synchronized)
 *   2. Check the condition in a WHILE loop (not if)
 *      - while (full)  { wait(); }   // producer waits until space
 *      - while (empty) { wait(); }   // consumer waits until item
 *   3. Do the work
 *   4. notify() to wake the other side
 *   5. Release the monitor on method exit
 *
 * Step 2 must use while (not if) because:
 *   - Spurious wakeups can occur (JVM/OS-level artifact)
 *   - Another thread might consume the item before this one re-enters
 *
 * Architecture:
 *   EventStorage  — bounded Queue<Date> (max 10); synchronized set/get
 *   Producer      — Runnable; calls set() 100 times
 *   Consumer      — Runnable; calls get() 100 times
 *   main          — wires them together; starts consumer first
 *
 * ================================================================
 */

package edu.lu.concurrency.week3.day1.part4_states;

import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

public final class Demo12_EventStorageProducerConsumer {

    // ----------------------------------------------------------------
    // EventStorage — the bounded buffer; the single shared monitor.
    // All state is protected by synchronized on 'this'.
    // ----------------------------------------------------------------
    static final class EventStorage {

        private final int maxSize = 10;

        // The event queue — each event is the timestamp of its creation.
        private final Queue<Date> storage = new LinkedList<>();

        /** Producer calls this. Blocks while the buffer is full. */
        public synchronized void set() {
            // WHILE loop — guard against spurious wakeups and multi-producer races.
            while (storage.size() == maxSize) {
                try {
                    wait(); // release monitor; park until consumer makes room
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            storage.add(new Date()); // store the event (a timestamp)
            System.out.printf("[Set] queue size: %d%n", storage.size());
            notify(); // wake ONE waiting consumer (safe with 1 consumer)
        }

        /** Consumer calls this. Blocks while the buffer is empty. */
        public synchronized void get() {
            // WHILE loop — guard against spurious wakeups and multi-consumer races.
            while (storage.isEmpty()) {
                try {
                    wait(); // release monitor; park until producer adds an event
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            String element = storage.poll().toString(); // remove oldest event
            System.out.printf("[Get] queue size: %d | event: %s%n", storage.size(), element);
            notify(); // wake ONE waiting producer (safe with 1 producer)
        }

        public int size() {
            synchronized (this) { return storage.size(); }
        }
    }

    // ----------------------------------------------------------------
    // Producer — generates 100 events (timestamps) into the storage.
    // ----------------------------------------------------------------
    static final class Producer implements Runnable {

        private final EventStorage storage;

        Producer(EventStorage storage) { this.storage = storage; }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                storage.set();
            }
        }
    }

    // ----------------------------------------------------------------
    // Consumer — consumes 100 events from the storage.
    // ----------------------------------------------------------------
    static final class Consumer implements Runnable {

        private final EventStorage storage;

        Consumer(EventStorage storage) { this.storage = storage; }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                storage.get();
            }
        }
    }

    // ----------------------------------------------------------------
    // main
    // ----------------------------------------------------------------
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Demo12: Event Storage - Bounded Buffer with wait()/notify() ===");
        System.out.printf("Buffer capacity: 10 | events to exchange: 100%n%n");

        EventStorage storage = new EventStorage();

        // Consumer starts first — it will immediately block on wait() since
        // the buffer is empty. This is intentional: demonstrates wait() in
        // the WAITING state before any production begins.
        Thread consumer = new Thread(new Consumer(storage), "consumer");
        Thread producer = new Thread(new Producer(storage), "producer");

        consumer.start();
        producer.start();

        // Wait for both to finish before printing the summary.
        producer.join();
        consumer.join();

        System.out.printf("%n[Done] Buffer remaining: %d%n", storage.size());
        System.out.println();
        System.out.println("TAKEAWAY:");
        System.out.println("  - synchronized + wait()/notify() implement a bounded buffer.");
        System.out.println("  - WHILE loops around wait() guard against spurious wakeups.");
        System.out.println("  - notify() is safe here (1 producer, 1 consumer).");
        System.out.println("  - With multiple producers/consumers, use notifyAll() instead.");
    }

    private Demo12_EventStorageProducerConsumer() {}
}
