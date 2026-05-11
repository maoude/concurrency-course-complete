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
