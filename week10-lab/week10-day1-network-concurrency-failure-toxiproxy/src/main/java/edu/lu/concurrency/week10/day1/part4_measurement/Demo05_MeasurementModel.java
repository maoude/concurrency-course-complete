package edu.lu.concurrency.week10.day1.part4_measurement;

public final class Demo05_MeasurementModel {

    public record MeasurementSummary(long requests, double throughputPerSecond, long p95LatencyMillis, int timeouts) {
    }

    public static MeasurementSummary example() {
        return new MeasurementSummary(100, 40.0, 250, 3);
    }

    private Demo05_MeasurementModel() {
    }
}
