package edu.lu.concurrency.week10.day1.part4_measurement.exercises;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class StudentWeek10Part4_Ex4Test {

    @Test
    void summarizesMeasurements() {
        var summary = Ex4_MeasurementSummary.summarize();

        assertTrue(summary.requests() > 0);
        assertTrue(summary.throughputPerSecond() > 0.0);
        assertTrue(summary.p95LatencyMillis() > 0);
        assertTrue(summary.timeouts() >= 0);
    }
}
