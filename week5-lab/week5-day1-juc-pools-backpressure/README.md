# Week 5 Day 1 - java.util.concurrent, Pools, and Backpressure

Week 5 upgrades thread-safe code into bounded, production-style concurrency.

Topics:

- `BlockingQueue` and backpressure
- `ExecutorService`, thread pools, `Callable`, and `Future`
- producer-consumer coordination with blocking queues
- fixed vs cached thread pools and throughput comparison
- bounded queues and rejection policies
- atomics and CAS-style metrics
- `ReentrantReadWriteLock` for read-heavy state
- `ThreadLocal` request context and cleanup hazards

## Requirements

- JDK 21 or newer
- PowerShell on Windows
- No global Gradle install required

## Run

```powershell
.\gradlew.bat clean test
.\gradlew.bat studentCheck
```

Run one demo:

```powershell
.\run-demo.ps1 03
```

Run all demos:

```powershell
.\run-all-demos.ps1
```

The batch files provide the same commands for `cmd.exe`:

```bat
run-demo.bat 03
run-all-demos.bat
```

Instructor solutions are excluded by default. Include them only for instructor checks:

```powershell
.\gradlew.bat -Pinstructor=true clean test
```

## Student Contract

Implement only the classes under `exercises/`.

The TODO stubs compile but should fail `studentCheck` until implemented.

## Required References

- `CONCURRENCY_SCORECARD.md` - fill this in after completing the exercises.
- `PITFALLS.md` Release 2 - review oversized pools, unbounded queues, and nested task submission.
- Use the measurement table in `LAB_INSTRUCTIONS.md` for the fixed-vs-cached pool observation.
- Use the Checkpoint 2 instructions provided by the instructor for the thread-pooled server checkpoint.

## Explanation Quality Rubric

A complete answer must name the symptom, explain the mechanism, state the fix, and mention the remaining tradeoff or liveness risk.
