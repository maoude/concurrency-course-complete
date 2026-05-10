package edu.lu.concurrency.week3.day1.part2_monitors;

import edu.lu.concurrency.week3.day1.TestIO;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Demo05_SafeBankAccountTest {

    @RepeatedTest(5)
    void demo05_invariant_holds() throws Exception {
        String out = TestIO.captureStdout(() -> Demo05_SafeBankAccount.main(new String[0]));
        // Initial balance = 15, two withdrawals of 10. Only one may succeed.
        // Final balance must be 5 and the invariant flag must be ok.
        assertTrue(out.contains("[RESULT] final balance = 5"),
                "Safe account must end with balance 5. Output:\n" + out);
        assertTrue(out.contains("[RESULT] invariant ok? = true"),
                "Invariant flag must be true. Output:\n" + out);
    }
}
