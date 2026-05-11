/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 3
 * Lab Title: Day 1 - Locks, Monitors and Reentrancy
 * ================================================================
 */

package edu.lu.concurrency.week3.day1.part2_monitors;

import edu.lu.concurrency.week3.day1.TestIO;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Demo07_ParkingSimulatorLockSplittingTest {

    @Test
    void demo07_prints_expected_output_labels() throws Exception {
        String out = TestIO.captureStdout(() -> Demo07_ParkingSimulatorLockSplitting.main(new String[0]));
        assertTrue(out.contains("Demo07"), "Should print demo header\n" + out);
        assertTrue(out.contains("Cars remaining"), "Should print cars remaining\n" + out);
        assertTrue(out.contains("Motorcycles remaining"), "Should print motorcycles remaining\n" + out);
        assertTrue(out.contains("Total collected"), "Should print cash total\n" + out);
        assertTrue(out.contains("TAKEAWAY"), "Should print takeaway\n" + out);
    }

    @RepeatedTest(3)
    void demo07_counters_are_balanced_and_cash_is_correct() throws Exception {
        // Run the simulation directly (not via main) for deterministic verification.
        Demo07_ParkingSimulatorLockSplitting.ParkingCash cash =
                new Demo07_ParkingSimulatorLockSplitting.ParkingCash();
        Demo07_ParkingSimulatorLockSplitting.ParkingStats stats =
                new Demo07_ParkingSimulatorLockSplitting.ParkingStats(cash);

        int numSensors = 4; // fixed count for deterministic expected values
        Thread[] threads = new Thread[numSensors];
        for (int i = 0; i < numSensors; i++) {
            threads[i] = new Thread(
                    new Demo07_ParkingSimulatorLockSplitting.Sensor(stats), "Sensor-" + i);
            threads[i].start();
        }
        for (Thread t : threads) t.join(10_000);

        // Every sensor does 10 cycles of +2cars/-2cars and +1moto/-1moto → net 0.
        assertEquals(0, stats.getNumberCars(),
                "All cars should have left (net 0) — lock splitting must not lose updates");
        assertEquals(0, stats.getNumberMotorcycles(),
                "All motorcycles should have left (net 0)");

        // Each sensor: 10 cycles × 3 departures × $2 = $60
        // numSensors × $60 total
        String cashOut = TestIO.captureStdout(cash::close);
        long expectedCash = (long) numSensors * 10 * 3 * 2;
        assertTrue(cashOut.contains("$" + expectedCash),
                "Expected cash $" + expectedCash + " but got:\n" + cashOut);
    }
}
