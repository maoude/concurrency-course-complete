/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week 3 - Part 2: Monitors
 * Demo05 - SafeBankAccount (Fixing the Check-Then-Act Race)
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * Demo02 broke the invariant "balance never goes negative" because
 * the check `balance >= amount` and the mutation `balance -= amount`
 * could be split apart by a context switch.
 *
 * The fix is to make the WHOLE compound operation atomic, not just
 * the subtraction. We do that by wrapping the critical section in
 * a single synchronized block on a per-account lock.
 *
 * Notice:
 *   - The lock lives on the account object itself.
 *     Different accounts have different locks. They do not contend.
 *     Same account, two threads: they serialize cleanly.
 *
 *   - We keep the Thread.sleep(10) inside the block to PROVE that
 *     mutual exclusion holds even when the critical section is slow.
 *     The second thread waits in BLOCKED state for the lock, then
 *     re-checks `balance >= amount` and correctly fails.
 *
 * Real-world relevance: ledger services, ticket inventory, seat
 * holds, idempotency-key processing. All of them are check-then-act
 * problems and all of them use this pattern (or its database cousin,
 * SELECT ... FOR UPDATE).
 *
 * Key lesson:
 *
 *   Atomicity must cover the WHOLE invariant, not just the write.
 *
 * ================================================================
 */

package edu.lu.concurrency.week3.day1.part2_monitors;

public final class Demo05_SafeBankAccount {

    /*
     * Per-account safety: each account owns its own monitor.
     * Two different accounts never serialize each other.
     */
    static final class SafeBankAccount {
        private int balance;
        private final Object lock = new Object();

        SafeBankAccount(int initialBalance) {
            this.balance = initialBalance;
        }

        /*
         * The whole check-then-act sequence runs under the same
         * monitor. Once T1 enters, T2 cannot enter until T1 leaves.
         * When T2 finally enters, the balance has already been
         * reduced, so its `balance >= amount` check correctly fails.
         */
        public boolean withdraw(int amount) {
            synchronized (lock) {
                if (balance >= amount) {
                    try {
                        Thread.sleep(10); // exaggerate the window
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    balance -= amount;
                    return true;
                }
                return false;
            }
        }

        public int getBalance() {
            synchronized (lock) {
                return balance;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== Demo05  SafeBankAccount ===");

        SafeBankAccount account = new SafeBankAccount(15);

        Runnable task = () -> {
            boolean ok = account.withdraw(10);
            System.out.println(Thread.currentThread().getName() + " withdraw success = " + ok);
        };

        Thread t1 = new Thread(task, "T1");
        Thread t2 = new Thread(task, "T2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        int finalBalance = account.getBalance();

        System.out.println("[RESULT] final balance = " + finalBalance);
        System.out.println("[RESULT] invariant ok? = " + (finalBalance >= 0));
        System.out.println("[TAKEAWAY] one withdrawal succeeds, the other correctly fails.");
    }

    private Demo05_SafeBankAccount() {}
}
