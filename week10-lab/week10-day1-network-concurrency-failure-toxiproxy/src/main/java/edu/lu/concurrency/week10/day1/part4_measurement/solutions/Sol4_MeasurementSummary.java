package edu.lu.concurrency.week10.day1.part4_measurement.solutions;

public final class Sol4_MeasurementSummary {

    public record MeasurementSummary(long requests, double throughputPerSecond, long p95LatencyMillis, int timeouts) {
    }

    public static MeasurementSummary summarize() {
        return new MeasurementSummary(100, 40.0, 250, 3);
    }

    private Sol4_MeasurementSummary() {
    }
}
