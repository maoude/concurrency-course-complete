package edu.lu.concurrency.week3.day1.part1_races;

import edu.lu.concurrency.week3.day1.TestIO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Demo02_BankAccountRaceTest {

    @Test
    void demo02_runs_and_prints_result() throws Exception {
        String out = TestIO.captureStdout(() -> Demo02_BankAccountRace.main(new String[0]));
        assertTrue(out.contains("BankAccountRace"));
        assertTrue(out.contains("[RESULT] final balance"));
        assertTrue(out.contains("[TAKEAWAY]"));
    }
}
