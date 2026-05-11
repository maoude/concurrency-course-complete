/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 3
 * Lab Title: Day 1 - Locks, Monitors and Reentrancy
 * Week 3 – Part 2: Monitors
 * Demo07 – Parking Simulator: Real-World Lock Splitting
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * This demo applies lock splitting to a real-world scenario:
 * a parking lot with sensors tracking cars and motorcycles.
 *
 * KEY IDEA — Lock Splitting with two independent monitors:
 *
 *   controlCars        → protects numberCars  only
 *   controlMotorcycles → protects numberMotorcycles only
 *
 * Because cars and motorcycles are independent resources, there is
 * no need for a single coarse lock that serializes ALL updates.
 * Two threads can update each counter simultaneously without conflict.
 *
 * Without lock splitting (coarse single lock):
 *   carComeIn()  and  motoComeIn()  would BLOCK each other —
 *   even though they touch different data.
 *
 * With lock splitting:
 *   carComeIn()  and  motoComeIn()  run concurrently — correct AND fast.
 *
 * ParkingCash uses synchronized(this) for cash — a separate concern.
 * Calling vehiclePay() from carGoOut() / motoGoOut() works safely
 * because the car/moto lock is released BEFORE calling cash.vehiclePay().
 * This avoids lock ordering issues.
 *
 * Architecture:
 *   ParkingCash    — synchronized cash register (one lock: this)
 *   ParkingStats   — two-lock counter (controlCars, controlMotorcycles)
 *   Sensor         — Runnable: simulates 10 cycles of arrivals/departures
 *   main           — 2×CPUs sensor threads, join all, print summary
 *
 * Expected totals (all sensors finish balanced):
 *   numberCars        = 0  (each sensor: +2×10, −2×10)
 *   numberMotorcycles = 0  (each sensor: +1×10, −1×10)
 *   cash              = sensors × 10 cycles × 3 departures × $2
 *
 * ================================================================
 */

package edu.lu.concurrency.week3.day1.part2_monitors;

import java.util.concurrent.TimeUnit;

public final class Demo07_ParkingSimulatorLockSplitting {

    // ----------------------------------------------------------------
    // ParkingCash — synchronized on 'this'; one lock for cash totals.
    // Shared across all sensors.
    // ----------------------------------------------------------------
    static final class ParkingCash {

        private static final int COST = 2;

        // Shared cash counter — ALL vehicle payments go here.
        private long cash;

        ParkingCash() { cash = 0; }

        // synchronized on 'this' — only one thread pays at a time.
        public synchronized void vehiclePay() {
            cash += COST;
        }

        public void close() {
            long total;
            // Atomic read-and-reset under the same lock used by vehiclePay()
            synchronized (this) {
                total = cash;
                cash = 0;
            }
            System.out.printf("[Cash] Total collected: $%d%n", total);
        }
    }

    // ----------------------------------------------------------------
    // ParkingStats — the lock-splitting core.
    // Two independent monitor objects protect two independent counters.
    // Threads touching only cars do NOT block threads touching motorcycles.
    // ----------------------------------------------------------------
    static final class ParkingStats {

        private long numberCars;
        private long numberMotorcycles;

        // Two SEPARATE locks — one per independent resource.
        // Using 'new Object()' (not 'this') makes the split explicit.
        private final Object controlCars        = new Object();
        private final Object controlMotorcycles = new Object();

        private final ParkingCash cash;

        ParkingStats(ParkingCash cash) {
            this.cash = cash;
        }

        // Car arrives — only acquires controlCars lock.
        public void carComeIn() {
            synchronized (controlCars) { numberCars++; }
        }

        // Car leaves — releases controlCars before calling vehiclePay()
        // (avoids nested-lock ordering problems).
        public void carGoOut() {
            synchronized (controlCars) { numberCars--; }
            cash.vehiclePay(); // cash lock acquired AFTER car lock released
        }

        // Motorcycle arrives — only acquires controlMotorcycles lock.
        public void motoComeIn() {
            synchronized (controlMotorcycles) { numberMotorcycles++; }
        }

        // Motorcycle leaves — same pattern: release own lock first, then pay.
        public void motoGoOut() {
            synchronized (controlMotorcycles) { numberMotorcycles--; }
            cash.vehiclePay();
        }

        public long getNumberCars() {
            synchronized (controlCars) { return numberCars; }
        }

        public long getNumberMotorcycles() {
            synchronized (controlMotorcycles) { return numberMotorcycles; }
        }
    }

    // ----------------------------------------------------------------
    // Sensor — Runnable that simulates a parking sensor.
    // Each sensor runs 10 arrival/departure cycles.
    //
    // Per cycle:
    //   2 cars arrive → 50 ms pause → 1 motorcycle arrives
    //   → 50 ms pause → 1 motorcycle leaves → 2 cars leave
    //
    // Net per cycle: 0 cars, 0 motorcycles (balanced in/out)
    // Cash per cycle: 3 departures × $2 = $6
    // ----------------------------------------------------------------
    static final class Sensor implements Runnable {

        private final ParkingStats stats;

        Sensor(ParkingStats stats) { this.stats = stats; }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                stats.carComeIn();
                stats.carComeIn();
                sleep(50);
                stats.motoComeIn();
                sleep(50);
                stats.motoGoOut();
                stats.carGoOut();
                stats.carGoOut();
            }
        }

        private static void sleep(long ms) {
            try { TimeUnit.MILLISECONDS.sleep(ms); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
    }

    // ----------------------------------------------------------------
    // main
    // ----------------------------------------------------------------
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Demo07: Parking Simulator — Real-World Lock Splitting ===");

        ParkingCash  cash  = new ParkingCash();
        ParkingStats stats = new ParkingStats(cash);

        int numSensors = 2 * Runtime.getRuntime().availableProcessors();
        System.out.printf("Launching %d sensor threads (%d × available CPUs)%n",
                numSensors, 2);
        System.out.println();

        Thread[] threads = new Thread[numSensors];
        for (int i = 0; i < numSensors; i++) {
            threads[i] = new Thread(new Sensor(stats), "Sensor-" + i);
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        System.out.printf("[Stats] Cars remaining       : %d%n", stats.getNumberCars());
        System.out.printf("[Stats] Motorcycles remaining: %d%n", stats.getNumberMotorcycles());

        // Expected cash: numSensors × 10 cycles × 3 departures × $2
        long expectedCash = (long) numSensors * 10 * 3 * 2;
        System.out.printf("[Cash]  Expected: $%d%n", expectedCash);
        cash.close();

        System.out.println();
        System.out.println("TAKEAWAY:");
        System.out.println("  - controlCars and controlMotorcycles are separate monitors.");
        System.out.println("  - Sensors updating cars do NOT block sensors updating motorcycles.");
        System.out.println("  - Cash lock is acquired only AFTER the per-resource lock is released.");
        System.out.println("  - Lock splitting increases throughput without sacrificing correctness.");
    }

    private Demo07_ParkingSimulatorLockSplitting() {}
}
