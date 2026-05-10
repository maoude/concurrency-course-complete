package edu.lu.concurrency.week3.day1.part2_monitors.exercises;

import org.junit.jupiter.api.RepeatedTest;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

public class StudentWeek3Part2_Ex3Test {

    @RepeatedTest(5)
    void invariants_hold_under_random_transfers() throws Exception {
        Ex3_SafeTransfer ledger = new Ex3_SafeTransfer();
        ledger.reset(50, 50);
        int initialTotal = ledger.getTotal();

        Thread[] ts = new Thread[8];
        for (int i = 0; i < ts.length; i++) {
            ts[i] = new Thread(() -> {
                for (int j = 0; j < 500; j++) {
                    Ex3_SafeTransfer.Account from =
                            ThreadLocalRandom.current().nextBoolean()
                                    ? Ex3_SafeTransfer.Account.A
                                    : Ex3_SafeTransfer.Account.B;
                    Ex3_SafeTransfer.Account to =
                            (from == Ex3_SafeTransfer.Account.A)
                                    ? Ex3_SafeTransfer.Account.B
                                    : Ex3_SafeTransfer.Account.A;
                    ledger.transfer(from, to, 10);
                }
            });
        }
        for (Thread t : ts) t.start();
        for (Thread t : ts) t.join();

        assertTrue(ledger.getA() >= 0, "A went negative: " + ledger.getA());
        assertTrue(ledger.getB() >= 0, "B went negative: " + ledger.getB());
        assertEquals(initialTotal, ledger.getTotal(),
                "Total money must be conserved by transfer().");
    }
}
