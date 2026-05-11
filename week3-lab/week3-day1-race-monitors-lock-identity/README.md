# Week 3 - Day 1 Lab
## Race, Monitors, Lock Identity, and Reentrancy

This is the canonical Week 3 student-exercise lab.

The sibling folder `../week3-day1-locks-monitors` currently remains as a
demo-rich legacy variant. Its best demos should be migrated here before
students are given a single final Week 3 folder.

Current migration status is tracked in `../WEEK3_MIGRATION_MAP.md`.

---

## Part 1 - Race Conditions

- Broken bank account invariant
- Lost updates under contention
- Lock ordering for deadlock-free transfer
- Demo support migrated under `part1_races`

## Part 2 - Monitors

- `synchronized` methods vs explicit monitor objects
- Lock splitting
- Correctly preserving invariants with one lock discipline
- Demo support migrated under `part2_monitors`

## Part 3 - Lock Identity

- Why a method-local lock does not coordinate threads
- Why static/shared locks have different behavior than instance locks
- Avoiding public or shared accidental monitors
- Demo support migrated under `part3_lock_identity`

## Part 4 - Thread States

- BLOCKED: waiting to enter a monitor
- WAITING: parked in `wait()` / coordination
- Producer-consumer with `wait()` / `notifyAll()`
- Demo support migrated under `part4_states`

## Part 5 - Reentrancy

- Intrinsic monitor reentrancy
- `ReentrantLock` with `try/finally`
- Demo support migrated under `part5_reentrancy`

---

## Running Checks

Run all tests:

```powershell
.\gradlew.bat test
```

Run only student exercise tests:

```powershell
.\gradlew.bat studentCheck
```

Run one part:

```powershell
.\gradlew.bat studentCheck --tests "*StudentWeek3Part2*"
```

See `EXERCISES.md` for the exercise/test matrix and `CHECKLIST.md` for
the student completion checklist.

---

## Explanation Quality Rubric

- Poor: "It's synchronized / it works now."
- Good: "Synchronization enforces mutual exclusion on this monitor, preventing interleaving that violates invariant X."
- Excellent: "This establishes happens-before between operations A and B, preventing reordering/visibility issues, preserving invariant X under schedule Y; tradeoff is contention cost Z."
