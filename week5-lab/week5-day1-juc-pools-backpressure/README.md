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
- `MEASUREMENT_TEMPLATE.md` - use this format for the fixed-vs-cached pool observation.
- `CHECKPOINT2_RUBRIC.md` - use this for the Week 5 thread-pooled server checkpoint.

## Explanation Quality Rubric

A complete answer must name the symptom, explain the mechanism, state the fix, and mention the remaining tradeoff or liveness risk.
