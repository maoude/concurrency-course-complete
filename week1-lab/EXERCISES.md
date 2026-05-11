# Exercises - Week 1 - Concurrency Foundations

Week 1 introduces why concurrency exists: queueing delay, throughput vs
latency, server concurrency, thread-pool tuning, and Amdahl performance
limits.

## Status Matrix

| Handle | Path | Test | Topic | Status |
|---|---|---|---|---|
| W1.P1.Ex1 | pending | pending | Single-threaded server measurements | TODO |
| W1.P2.Ex1 | pending | pending | Multi-threaded blocking server comparison | TODO |
| W1.P3.Ex1 | pending | pending | Thread-pool tuning + Little's Law | TODO |
| W1.P4.Ex1 | `src/main/java/edu/lu/concurrency/week1/lab1/part4_amdahl/exercises/Ex1_AmdahlSpeedup.java` | `StudentWeek1Part4_Ex1Test` | Amdahl speedup formula | DONE |
| W1.P4.Ex2 | `src/main/java/edu/lu/concurrency/week1/lab1/part4_amdahl/exercises/Ex2_KarpFlatt.java` | `StudentWeek1Part4_Ex2Test` | Karp-Flatt serial fraction | DONE |

## Implemented Student Exercises

### W1.P4.Ex1 `Ex1_AmdahlSpeedup`

Implement Amdahl's Law:

```text
S = 1 / ((1 - P) + P / N)
```

where `P` is the parallel fraction and `N` is the number of cores.
Validate inputs before computing the result.

Run:

```powershell
.\gradlew.bat studentCheck --tests "*StudentWeek1Part4_Ex1Test"
```

### W1.P4.Ex2 `Ex2_KarpFlatt`

Recover the experimentally determined serial fraction:

```text
e = (1/S - 1/N) / (1 - 1/N)
```

Then compute the estimated parallel fraction as `1 - e`.

Run:

```powershell
.\gradlew.bat studentCheck --tests "*StudentWeek1Part4_Ex2Test"
```

## Pending Syllabus Exercises

The syllabus also requires Week 1 tasks around single-threaded vs
multi-threaded blocking servers, latency/throughput measurement, and
thread-pool tuning. Those are currently represented by demos, benchmark
scripts, and smoke tests, but not yet by `StudentWeek*` exercise stubs.

Tracked in `../COURSE_TODO.md`:

- `#9` W1.P1 Single-threaded server exercises
- `#10` W1.P2 Multi-threaded server exercises
- `#11` W1.P3 Thread-pool tuning + Little's Law exercises
