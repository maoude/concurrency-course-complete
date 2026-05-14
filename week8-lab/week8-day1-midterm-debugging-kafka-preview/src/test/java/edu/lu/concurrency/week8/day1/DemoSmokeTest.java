package edu.lu.concurrency.week8.day1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.lu.concurrency.week8.day1.part1_diagnosis.Demo01_ThreadDumpClues;
import edu.lu.concurrency.week8.day1.part1_diagnosis.Demo02_DiagnosisReportModel;
import edu.lu.concurrency.week8.day1.part2_async_server.Demo03_BrokenAsyncServer;
import edu.lu.concurrency.week8.day1.part3_tradeoffs.Demo04_PerformanceTradeoff;
import edu.lu.concurrency.week8.day1.part4_kafka_preview.Demo05_KafkaMentalModel;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

class DemoSmokeTest {

    @Test
    void demosRun() throws Exception {
        assertEquals("WAITING", Demo01_ThreadDumpClues.clue().threadState());
        assertTrue(Demo02_DiagnosisReportModel.modelReport().mechanism().contains("complete"));
        assertEquals("waiting-without-timeout", Demo03_BrokenAsyncServer.brokenRequestState());
        assertEquals("fallback-response",
                Demo03_BrokenAsyncServer.fixedRequest(Duration.ofMillis(5)).get(1, TimeUnit.SECONDS));
        assertTrue(Demo04_PerformanceTradeoff.timeoutFallbackTradeoff().measurement().contains("latency"));
        assertTrue(Demo05_KafkaMentalModel.coreUses().contains("replay"));
    }
}
