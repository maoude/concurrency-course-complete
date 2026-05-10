package edu.lu.concurrency.week3.day1.part3_lock_identity;

import edu.lu.concurrency.week3.day1.TestIO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Demo06_LockIdentityTrapTest {

    @Test
    void demo06_runs_and_prints_result() throws Exception {
        String out = TestIO.captureStdout(() -> Demo06_LockIdentityTrap.main(new String[0]));
        assertTrue(out.contains("LockIdentityTrap"));
        assertTrue(out.contains("[RESULT] BadCounter expected = 400000"));
        assertTrue(out.contains("[RESULT] BadCounter actual"));
        assertTrue(out.contains("[RESULT] BadBankAccount final balance"));
        assertTrue(out.contains("[TAKEAWAY]"));
    }
}
