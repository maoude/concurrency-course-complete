/*
 * Course: Concurrency and Distributed Systems
 * Week:   7 - Async Design, Testing, and Anti-Patterns
 * Author: Dr. Mohamad AOUDE
 *
 * Mini decision table for common async anti-patterns.
 */
package edu.lu.concurrency.week7.day1.part5_nio_counterpoint;

import java.util.List;

public final class Demo08_AsyncAntiPatterns {

    public static List<String> antiPatterns() {
        return List.of(
                "thread-per-request under sustained load",
                "unbounded queues",
                "missing timeouts",
                "silent failure swallowing");
    }

    private Demo08_AsyncAntiPatterns() {
    }
}
