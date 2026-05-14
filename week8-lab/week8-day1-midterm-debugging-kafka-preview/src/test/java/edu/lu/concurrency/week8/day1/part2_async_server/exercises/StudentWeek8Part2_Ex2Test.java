package edu.lu.concurrency.week8.day1.part2_async_server.exercises;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

class StudentWeek8Part2_Ex2Test {

    @Test
    void handlerReturnsFallbackAfterTimeout() throws Exception {
        assertEquals("fallback-response",
                Ex2_FixAsyncServer.handle(Duration.ofMillis(20)).get(1, TimeUnit.SECONDS));
    }
}
