package edu.lu.concurrency.week8.day1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

class InstructorWeek8SolutionsTest {

    @Test
    void solutionReportIsComplete() throws Exception {
        Class<?> type = Class.forName("edu.lu.concurrency.week8.day1.part1_diagnosis.solutions.Sol1_DiagnosisReport");
        Object report = type.getMethod("report").invoke(null);

        assertTrue(report.getClass().getMethod("bug").invoke(report).toString().contains("wait"));
        assertTrue(report.getClass().getMethod("evidence").invoke(report).toString().contains("thread"));
        assertTrue(report.getClass().getMethod("mechanism").invoke(report).toString().contains("timeout"));
        assertTrue(report.getClass().getMethod("fix").invoke(report).toString().contains("completeOnTimeout"));
    }

    @Test
    void solutionHandlerFallsBack() throws Exception {
        Class<?> type = Class.forName("edu.lu.concurrency.week8.day1.part2_async_server.solutions.Sol2_FixAsyncServer");
        Method handle = type.getMethod("handle", Duration.class);
        Object future = handle.invoke(null, Duration.ofMillis(20));

        assertEquals("fallback-response", ((CompletableFuture<?>) future).get(1, TimeUnit.SECONDS));
    }

    @Test
    void solutionTradeoffIsConcrete() throws Exception {
        Class<?> type = Class.forName("edu.lu.concurrency.week8.day1.part3_tradeoffs.solutions.Sol3_PerformanceTradeoff");
        Object tradeoff = type.getMethod("explain").invoke(null);

        assertTrue(tradeoff.getClass().getMethod("benefit").invoke(tradeoff).toString().contains("waiting"));
        assertTrue(tradeoff.getClass().getMethod("cost").invoke(tradeoff).toString().contains("fallback"));
        assertTrue(tradeoff.getClass().getMethod("measurement").invoke(tradeoff).toString().contains("latency"));
    }

    @Test
    void solutionKafkaPreviewNamesCoreUses() throws Exception {
        Class<?> type = Class.forName(
                "edu.lu.concurrency.week8.day1.part4_kafka_preview.solutions.Sol4_KafkaMentalModel");

        @SuppressWarnings("unchecked")
        List<String> uses = (List<String>) type.getMethod("logUses").invoke(null);
        String explanation = type.getMethod("explain").invoke(null).toString().toLowerCase();

        assertTrue(uses.containsAll(List.of("history", "replay", "recovery", "audit", "decoupling")));
        assertTrue(explanation.contains("replay"));
        assertTrue(explanation.contains("decouple"));
    }
}
