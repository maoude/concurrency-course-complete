# Week 7 Day 1 - Async Design, Testing, and Anti-Patterns

Week 7 moves from CPU-bound parallel decomposition to asynchronous
composition, realistic testing, and async anti-patterns.

Topics:

- `CompletableFuture` pipelines
- timeouts and fallbacks
- testing traps and reproduction strategies
- JCStress-style stress testing concepts
- fan-out / fan-in async aggregation
- Java NIO as a counterpoint to multi-threaded blocking I/O
- async anti-patterns: thread-per-request, unbounded queues, missing timeouts,
  silent failure swallowing

## Requirements

- JDK 21 or newer
- PowerShell on Windows
- No global Gradle install required

## Run

```powershell
.\gradlew.bat classes
.\gradlew.bat test
.\gradlew.bat studentCheck
```

`test` runs the demo/smoke checks. `studentCheck` runs the exercise
grading tests and is expected to fail until the TODO stubs are implemented.

Run one demo:

```powershell
.\run-demo.ps1 04
```

Run all demos:

```powershell
.\run-all-demos.ps1
```

The batch files provide the same commands for `cmd.exe`:

```bat
run-demo.bat 04
run-all-demos.bat
```

Instructor solutions are excluded by default. Include them only for instructor checks:

```powershell
.\gradlew.bat -Pinstructor=true instructorCheck
```

## Student Contract

Implement only the classes under `exercises/`.

The TODO stubs compile but should fail `studentCheck` until implemented.

## Required References

- `ANTI_PATTERNS.md` - review before implementing timeout and fallback behavior.
- `ASYNC_PIPELINE_SEQUENCE.md` - complete/adapt for the Part 4 deliverable.
- `QUIZ_QUESTIONS.md` - quick review questions and answer key.
- `CONCURRENCY_SCORECARD.md` - complete after finishing the exercises.
