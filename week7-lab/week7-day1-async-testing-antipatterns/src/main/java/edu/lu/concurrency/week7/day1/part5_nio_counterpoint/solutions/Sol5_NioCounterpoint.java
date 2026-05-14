package edu.lu.concurrency.week7.day1.part5_nio_counterpoint.solutions;

public final class Sol5_NioCounterpoint {

    public record Comparison(int blockingWorkerThreads, int nioSelectorThreads, String explanation) {
    }

    public static Comparison compare(int blockingConnections) {
        return new Comparison(
                blockingConnections,
                1,
                "NIO can use fewer blocked threads, but async does not mean automatic speed or more threads.");
    }

    private Sol5_NioCounterpoint() {
    }
}
