/*
 * ================================================================
 * EXERCISE W3.P2.Ex3 - Synchronized transfer() Between Two Accounts
 * ----------------------------------------------------------------
 * Goal:        Fix Ex2_TransferRace from Part 1. Two invariants must
 *              hold under any interleaving:
 *                I1: no account ever goes negative
 *                I2: total money is conserved
 *
 * Given:       Same shape as Ex2_TransferRace but you may add lock state.
 *
 * Your task:
 *   1) Lock BOTH accounts before checking and mutating.
 *   2) Acquire the two locks in a CONSISTENT order to avoid deadlock
 *      (e.g. always lock the account with the lower System.identityHashCode
 *      first, OR a fixed A-then-B ordering since there are only two).
 *   3) Return false (without mutating) if the source has insufficient funds.
 *
 * Pass when:   StudentWeek3Part2_Ex3Test is green - it runs many random
 *              transfers and asserts BOTH invariants every run.
 * Hint:        With only two accounts, "always lock A first, then B" is
 *              enough. If you grow this to N accounts, use identity hash.
 * ================================================================
 */
package edu.lu.concurrency.week3.day1.part2_monitors.exercises;

public final class Ex3_SafeTransfer {

    public enum Account { A, B }

    private int accountA;
    private int accountB;

    private final Object lockA = new Object();
    private final Object lockB = new Object();

    public void reset(int initialA, int initialB) {
        // TODO: set accountA / accountB under both locks (or before threads start).
    }

    public int getA()     { /* TODO */ return accountA; }
    public int getB()     { /* TODO */ return accountB; }
    public int getTotal() { /* TODO: must be consistent */ return accountA + accountB; }

    public boolean transfer(Account from, Account to, int amount) {
        // TODO: acquire BOTH locks in a fixed order, then check + mutate.
        return false;
    }
}
