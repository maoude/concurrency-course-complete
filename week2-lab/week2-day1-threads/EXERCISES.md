# Exercises - Week 2 Day 1 - Threads

Week 2 focuses on thread creation, lifecycle, scheduler reality, and
coordination basics. The syllabus core is intentionally smaller than the
current demo set.

## Status Matrix

| Handle | Path | Test | Topic | Status |
|---|---|---|---|---|
| W2.P1.Ex1 | pending | pending | Thread creation: `Thread`, `Runnable`, lambda, `start()` vs `run()` | TODO |
| W2.P2.Ex1 | pending | pending | Lifecycle states and thread dump observation | TODO |
| W2.P3.Ex1 | pending | pending | `join()` correctness vs `sleep()` timing trap | TODO |
| W2.P4.Ex1 | pending | pending | Race-condition preview exercise | TODO |

## Core Week 2 Material

These are aligned with the syllabus for Week 2:

- `part1_basics` - thread mental model, `start()` vs `run()`, interleaving
- `part2_lifecycle` - states, priority experiment, interruption, thread dumps
- `part3_coordination/Demo14` through `Demo18` - sleep vs join, happens-before,
  join timeout pitfalls

## Preview Material

These demos are useful, but they belong conceptually to later syllabus
weeks unless explicitly kept as previews:

- `Demo22_SemaphoreBarberShop` - Week 5 backpressure / bounded resources preview
- `Demo23_CallableAndFuture` - Week 5/7 `java.util.concurrent` and async preview
- `Demo24_ParallelFuturesPolling` - Week 7 async composition preview
- `Demo25_InvokeAnyUserValidation` - Week 7 async racing/fallback preview
- `Demo26_CompletionServiceReportProcessing` - Week 7 fan-in preview
- `part4_race_conditions` - Week 3 race-condition bridge / preview

## Current Student Checks

There are no `StudentWeek2*` exercise tests yet. `studentCheck` is wired
to the standard command shape and will start running Week 2 student tests
as soon as the W2 exercise stubs/tests are added.

Run all demo tests:

```powershell
.\gradlew.bat test
```

Run current student-test contract:

```powershell
.\gradlew.bat studentCheck
```
