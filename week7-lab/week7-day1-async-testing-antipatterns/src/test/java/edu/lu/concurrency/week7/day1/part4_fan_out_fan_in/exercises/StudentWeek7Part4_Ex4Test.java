package edu.lu.concurrency.week7.day1.part4_fan_out_fan_in.exercises;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StudentWeek7Part4_Ex4Test {

    @Test
    void aggregatesAllServiceResultsAndMarksFallback() {
        Ex4_FanOutFanInPipeline.CombinedResponse response =
                Ex4_FanOutFanInPipeline.run(Duration.ofMillis(30)).join();

        assertEquals(4, response.results().size());
        assertTrue(response.usedFallback());
        assertTrue(response.results().stream().anyMatch(result -> result.name().equals("inventory") && result.fallback()));
        assertTrue(response.results().stream().anyMatch(result -> result.name().equals("recommendations") && result.fallback()));
    }
}
