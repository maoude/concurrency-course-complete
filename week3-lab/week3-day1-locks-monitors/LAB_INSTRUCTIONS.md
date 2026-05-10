# Lab Instructions (Week 3 - Day 1)

## Part 1 - Races
Run demos in: src/main/java/.../part1_races

Observe:
- Demo01: actual count is less than expected (lost updates).
- Demo02: with starting balance 15, two withdrawals of 10 can both succeed.

## Part 2 - Monitors
Run demos in: src/main/java/.../part2_monitors

Observe:
- Demo03: a single shared lock fixes the counter.
- Demo04: `synchronized` method and `synchronized` block produce equivalent
  results, but the block form gives you private lock identity.
- Demo05: the same shared-lock idea fixes the bank-account invariant.

## Part 3 - Lock Identity
Run demos in: src/main/java/.../part3_lock_identity

Observe:
- Demo06: `synchronized (new Object())` and a method-scoped `Object lock`
  both produce broken behaviour identical to no synchronization at all.

## Part 4 - States
Run demos in: src/main/java/.../part4_states

Observe:
- Demo07: one thread is BLOCKED on a monitor; another is WAITING in `wait()`.
  Capture a `jstack` dump while the demo is running and identify both states.

## Part 5 - Reentrancy
Run demos in: src/main/java/.../part5_reentrancy

Observe:
- Demo08: `outer()` calls `inner()` while already holding the monitor;
  the same thread re-enters without deadlocking.
