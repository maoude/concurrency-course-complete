/*
 * Course: Concurrency and Distributed Systems
 * Week:   6 - Parallelism and Fork/Join
 * Author: Dr. Mohamad AOUDE
 *
 * Discussion demo: blocking work can starve common-pool workers.
 */
package edu.lu.concurrency.week6.day1.part3_parallel_streams;

public final class Demo07_BlockingInCommonPoolRisk {

    public static String riskStatement() {
        return "Blocking I/O inside the common ForkJoinPool can starve CPU-bound work.";
    }

    private Demo07_BlockingInCommonPoolRisk() {
    }
}
