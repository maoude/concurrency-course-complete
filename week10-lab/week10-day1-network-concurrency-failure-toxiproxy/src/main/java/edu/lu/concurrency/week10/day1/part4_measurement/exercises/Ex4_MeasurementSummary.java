package edu.lu.concurrency.week10.day1.part4_measurement.exercises;

public final class Ex4_MeasurementSummary {

    public record MeasurementSummary(long requests, double throughputPerSecond, long p95LatencyMillis, int timeouts) {
    }

    public static MeasurementSummary summarize() {
        /* TODO: return a non-empty measurement summary. */
        return new MeasurementSummary(0, 0.0, 0, -1);
    }

    private Ex4_MeasurementSummary() {
    }
}
