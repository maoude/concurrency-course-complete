package edu.lu.concurrency.week10.day1;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.util.List;
import org.junit.jupiter.api.Test;

class InstructorWeek10SolutionsTest {

    @Test
    void solutionContractsPass() throws Exception {
        Class<?> lawsType = Class.forName(
                "edu.lu.concurrency.week10.day1.part1_network_laws.solutions.Sol1_ThreeNetworkLaws");
        @SuppressWarnings("unchecked")
        List<String> laws = (List<String>) lawsType.getMethod("laws").invoke(null);
        assertTrue(String.join(" ", laws).contains("latency"));

        Class<?> protocolType = Class.forName(
                "edu.lu.concurrency.week10.day1.part2_socket_queue.solutions.Sol2_SocketQueueProtocol");
        Object protocol = protocolType.getMethod("define").invoke(null);
        @SuppressWarnings("unchecked")
        List<String> commands = (List<String>) protocol.getClass().getMethod("commands").invoke(protocol);
        assertTrue(String.join(" ", commands).contains("PUT"));

        Class<?> planType = Class.forName(
                "edu.lu.concurrency.week10.day1.part3_failure_injection.solutions.Sol3_ToxiproxyPlan");
        @SuppressWarnings("unchecked")
        List<String> plan = (List<String>) planType.getMethod("plan").invoke(null);
        assertTrue(String.join(" ", plan).contains("latency"));

        Class<?> summaryType = Class.forName(
                "edu.lu.concurrency.week10.day1.part4_measurement.solutions.Sol4_MeasurementSummary");
        Method summarize = summaryType.getMethod("summarize");
        Object summary = summarize.invoke(null);
        assertTrue((long) summary.getClass().getMethod("requests").invoke(summary) > 0);
    }
}
