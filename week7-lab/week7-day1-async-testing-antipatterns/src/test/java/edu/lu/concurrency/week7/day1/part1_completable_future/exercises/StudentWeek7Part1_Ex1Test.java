package edu.lu.concurrency.week7.day1.part1_completable_future.exercises;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentWeek7Part1_Ex1Test {

    @Test
    void returnsRealValueWhenServiceCompletesBeforeTimeout() {
        assertEquals("real-value", Ex1_AsyncTimeoutFallback
                .call(Duration.ofMillis(1), Duration.ofMillis(100))
                .join());
    }

    @Test
    void returnsFallbackValueWhenServiceTimesOut() {
        assertEquals("fallback-value", Ex1_AsyncTimeoutFallback
                .call(Duration.ofMillis(100), Duration.ofMillis(1))
                .join());
    }
}
