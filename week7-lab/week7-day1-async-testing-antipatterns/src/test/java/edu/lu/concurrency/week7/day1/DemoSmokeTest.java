package edu.lu.concurrency.week7.day1;

import edu.lu.concurrency.week7.day1.part1_completable_future.Demo01_CompletableFuturePipeline;
import edu.lu.concurrency.week7.day1.part1_completable_future.Demo02_TimeoutFallback;
import edu.lu.concurrency.week7.day1.part2_testing_traps.Demo03_PassesUntilAmplified;
import edu.lu.concurrency.week7.day1.part2_testing_traps.Demo04_ReproductionStrategy;
import edu.lu.concurrency.week7.day1.part3_stress_testing.Demo05_StressOutcomeClassification;
import edu.lu.concurrency.week7.day1.part4_fan_out_fan_in.Demo06_FanOutFanInPipeline;
import edu.lu.concurrency.week7.day1.part5_nio_counterpoint.Demo07_NioVsBlockingCounterpoint;
import edu.lu.concurrency.week7.day1.part5_nio_counterpoint.Demo08_AsyncAntiPatterns;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class DemoSmokeTest {

    @Test
    void demosRun() throws Exception {
        assertEquals("PROFILE:REQ-7", Demo01_CompletableFuturePipeline.fetchAndFormat("req-7"));
        assertEquals("real-profile",
                Demo02_TimeoutFallback.callWithFallback(Duration.ofMillis(1), Duration.ofMillis(100)));
        assertEquals("fallback-profile",
                Demo02_TimeoutFallback.callWithFallback(Duration.ofMillis(100), Duration.ofMillis(1)));

        int observed = Demo03_PassesUntilAmplified.racyIncrement(2, 10, true);
        assertTrue(observed > 0);
        assertTrue(Demo04_ReproductionStrategy.amplified().iterations() >= 10_000);

        assertEquals(
                Demo05_StressOutcomeClassification.Outcome.ACCEPTABLE,
                Demo05_StressOutcomeClassification.classifyCounterResult(10, 10));
        assertTrue(Demo05_StressOutcomeClassification.jcstressIdeas().contains("outcomes"));

        Demo06_FanOutFanInPipeline.CombinedResponse response =
                Demo06_FanOutFanInPipeline.runPipeline(Duration.ofMillis(20));
        assertEquals(4, response.results().size());
        assertTrue(response.usedFallback());

        Demo07_NioVsBlockingCounterpoint.Comparison comparison =
                Demo07_NioVsBlockingCounterpoint.compare(100);
        assertEquals(100, comparison.blockingWorkerThreads());
        assertEquals(1, comparison.nioSelectorThreads());
        assertEquals(4, Demo08_AsyncAntiPatterns.antiPatterns().size());
    }
}
