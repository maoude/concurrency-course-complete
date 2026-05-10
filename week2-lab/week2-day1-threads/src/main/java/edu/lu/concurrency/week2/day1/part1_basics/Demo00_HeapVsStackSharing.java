/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week 2 – Thread Memory Model Fundamentals
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * This program demonstrates one of the most fundamental realities 
 * of concurrent system design:
 *
 *     ➤ Stack memory is private to each thread.
 *     ➤ Heap memory is shared across threads.
 *
 * In real-world engineering systems (cloud services, robotics,
 * trading systems, manufacturing control, distributed databases),
 * misunderstanding this distinction leads to:
 *
 *     - Race conditions
 *     - Data corruption
 *     - Inconsistent system states
 *     - Production failures
 *
 * This example shows how multiple threads interact with shared 
 * heap state while maintaining their own independent stack frames.
 *
 * The core lesson:
 *
 *     ➤ Threads do NOT share stack variables.
 *     ➤ Threads DO share heap objects.
 *     ➤ Unsynchronized access to shared heap data is dangerous.
 *
 * This is the conceptual foundation for:
 *     - Locks
 *     - Synchronization
 *     - Atomic variables
 *     - Memory visibility
 *     - The Java Memory Model (JMM)
 *
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.part1_basics;

public class Demo00_HeapVsStackSharing {

    /*
     * ------------------------------------------------------------
     * Inner static class representing a shared mutable object.
     * ------------------------------------------------------------
     *
     * This object is allocated on the HEAP.
     *
     * Important:
     *   Every thread will reference the SAME instance of Counter.
     *
     * In engineering systems:
     *   This could represent:
     *     - A shared cache value
     *     - A database counter
     *     - A global configuration value
     *     - A shared sensor reading
     */
    static class Counter {
        int value = 0;   // Shared mutable state (NOT thread-safe)
    }

    public static void main(String[] args) throws InterruptedException {

        /*
         * --------------------------------------------------------
         * Heap allocation
         * --------------------------------------------------------
         *
         * The Counter object is created ONCE.
         *
         * Both threads will hold a reference to the SAME object.
         *
         * Memory location:
         *   sharedCounter → Heap
         */
        Counter sharedCounter = new Counter(); // Heap object

        /*
         * --------------------------------------------------------
         * Runnable task definition
         * --------------------------------------------------------
         *
         * This lambda will be executed by multiple threads.
         *
         * Each thread will:
         *   - Have its own stack
         *   - Share the same heap object
         */
        Runnable task = () -> {

            /*
             * ----------------------------------------------------
             * Stack variable
             * ----------------------------------------------------
             *
             * localCopy is stored on the THREAD STACK.
             *
             * Each thread gets its OWN copy of localCopy.
             *
             * Even though both threads read from sharedCounter,
             * they do NOT share localCopy.
             *
             * This line demonstrates:
             *
             *     Heap → read once
             *     Stack → independent per thread
             */
            int localCopy = sharedCounter.value;

            /*
             * ----------------------------------------------------
             * Increment shared state
             * ----------------------------------------------------
             *
             * This loop modifies shared heap memory.
             *
             * Critical observation:
             *
             *     sharedCounter.value++
             *
             * Is NOT atomic.
             *
             * It expands internally to:
             *
             *     1. Read value
             *     2. Increment
             *     3. Write back
             *
             * Two threads executing this concurrently can cause:
             *
             *     - Lost updates
             *     - Race conditions
             */
            for (int i = 0; i < 5; i++) {

                sharedCounter.value++; // UNSAFE shared mutation

                /*
                 * Print current state.
                 *
                 * localCopy remains constant per thread.
                 * shared value changes globally.
                 *
                 * This allows us to observe:
                 *
                 *     - Stack isolation
                 *     - Heap sharing
                 */
                System.out.println(
                        Thread.currentThread().getName()
                        + " | localCopy=" + localCopy
                        + " | shared=" + sharedCounter.value
                );
            }
        };

        /*
         * --------------------------------------------------------
         * Thread creation
         * --------------------------------------------------------
         *
         * Two independent threads execute the SAME Runnable.
         *
         * Each thread:
         *   - Own stack
         *   - Shared heap access
         */
        Thread t1 = new Thread(task, "T1");
        Thread t2 = new Thread(task, "T2");

        /*
         * --------------------------------------------------------
         * Start execution
         * --------------------------------------------------------
         *
         * After start():
         *
         *  - Threads execute concurrently
         *  - Interleaving becomes unpredictable
         *  - Order of increments is non-deterministic
         */
        t1.start();
        t2.start();

        /*
         * --------------------------------------------------------
         * join() ensures main waits for completion.
         *
         * Without join():
         *     main might exit before threads finish.
         *
         * join() provides coordination but NOT synchronization
         * of shared data access.
         */
        t1.join();
        t2.join();

        /*
         * Final result.
         *
         * Expected mathematically:
         *     5 increments per thread × 2 threads = 10
         *
         * But due to race conditions,
         * the final value MAY be less than 10.
         *
         * This demonstrates:
         *     Non-atomic shared updates are unsafe.
         */
        System.out.println("Final shared value: " + sharedCounter.value);
    }
}