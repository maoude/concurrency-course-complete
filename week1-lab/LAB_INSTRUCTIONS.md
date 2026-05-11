# Lab Instructions - Week 1

## Part 1 - Why Concurrency Exists

Run the single-threaded server and observe that requests are handled one
at a time. The main teaching point is queueing: latency grows when work
waits behind earlier work.

## Part 2 - Blocking Threads

Compare the single-threaded server with the multi-threaded blocking
server. Record latency and throughput rather than relying on intuition.

## Part 3 - Pool Size and Little's Law

Use the benchmark scripts to vary pool size, client count, and requests
per client. Look for the point where adding threads stops helping or
starts hurting.

## Part 4 - Amdahl Performance Modeling

Complete or review:

- `W1.P4.Ex1` - Amdahl speedup
- `W1.P4.Ex2` - Karp-Flatt serial fraction

Run the student checks:

```powershell
.\gradlew.bat studentCheck
```

## Deliverables

- Benchmark measurements for the server comparison.
- Short explanation using the course explanation-quality rubric.
- Completed `CONCURRENCY_SCORECARD.md`.
- Green `studentCheck` for implemented Week 1 student exercises.
