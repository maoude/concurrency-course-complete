# Week 6 Day 1 - Parallelism and Fork/Join

Week 6 moves from bounded task execution to CPU-oriented parallel decomposition.

Topics:

- sequential compute baselines
- `ForkJoinPool`, `RecursiveTask`, and work stealing
- task granularity and threshold selection
- break-even measurement between sequential and Fork/Join execution
- parallel-stream anti-examples and the four slowdown causes

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
