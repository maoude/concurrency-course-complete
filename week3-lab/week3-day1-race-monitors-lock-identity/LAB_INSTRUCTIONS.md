# Lab Instructions - Week 3 Day 1

## Canonical Lab

Use this folder as the canonical Week 3 student exercise lab:

```text
week3-lab/week3-day1-race-monitors-lock-identity
```

The sibling `week3-day1-locks-monitors` folder currently contains extra
demos and should be treated as legacy/demo-only until its useful material
is merged.

## Part 1 - Races

Run or inspect the Part 1 exercises and tests.

Observe:

- Unsynchronized shared mutable state loses updates.
- A bank-account invariant can break under concurrent withdrawals.
- Transfer between accounts needs a consistent lock order to avoid
  deadlock.

## Part 2 - Monitors

Observe:

- `synchronized` methods lock on `this`.
- `synchronized(lock)` protects only code using the same lock identity.
- Lock splitting can reduce contention when state is independent.

## Part 3 - Lock Identity

Observe:

- `synchronized(new Object())` is useless for coordination.
- A method-local lock object is a different monitor on every call.
- Static locks coordinate across instances; instance locks do not.

## Part 4 - Thread States

Observe:

- A thread waiting to enter a synchronized block is BLOCKED.
- A thread inside `wait()` is WAITING and has released the monitor.
- Producer-consumer code must use `while` around `wait()`.

## Part 5 - Reentrancy

Observe:

- A thread can re-enter a monitor it already owns.
- `ReentrantLock` must be paired with `unlock()` in `finally`.

## Deliverables

- Green `studentCheck`.
- Completed `CONCURRENCY_SCORECARD.md`.
- Short explanation using the course explanation-quality rubric.
- For the deadlock/transfer task: state diagram or lock-order explanation.
