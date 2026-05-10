/*
 * ================================================================
 * EXERCISE W3.P1.Ex2 - Break Two Invariants With transfer()
 * ----------------------------------------------------------------
 * Goal:        Extend the bank-account race to a `transfer` operation
 *              between two accounts and observe that it can break TWO
 *              invariants at once: "no negative balance" AND "total
 *              money is conserved across both accounts".
 *
 * Given:       A two-account ledger (accountA, accountB), each int.
 *
 * Your task:
 *   1) Implement `transfer(from, to, amount)` as a deliberate
 *      check-then-act WITHOUT synchronization:
 *        - read source balance
 *        - if source >= amount, sleep 1ms, source -= amount, dest += amount
 *   2) Provide getA(), getB(), getTotal().
 *   3) Provide reset(initialA, initialB).
 * Pass when:   StudentWeek3Part1_Ex2Test is green - it runs many
 *              concurrent transfers and asserts that AT LEAST ONE of
 *              the two invariants is observed broken.
 * Hint:        The race is in the gap between the read and the write.
 *              Sleep widens the window so the test is reliable.
 * ================================================================
 */
package edu.lu.concurrency.week3.day1.part1_races.exercises;

public final class Ex2_TransferRace {

    public enum Account { A, B }

    private int accountA;
    private int accountB;

    public void reset(int initialA, int initialB) {
        // TODO: set accountA and accountB.
    }

    public int getA()     { return accountA; }
    public int getB()     { return accountB; }
    public int getTotal() { return accountA + accountB; }

    public boolean transfer(Account from, Account to, int amount) {
        // TODO: check-then-act, NO synchronization.
        // Use Thread.sleep(1) between the check and the mutation.
        return false;
    }
}
