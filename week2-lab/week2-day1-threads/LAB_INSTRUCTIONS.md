# Lab Instructions - Week 2 Day 1

## Part 1 - Thread Mental Model

Run demos in `src/main/java/.../part1_basics`.

Observe:

- `start()` creates a new thread; `run()` is an ordinary method call.
- Interleaving changes between runs.
- `sleep()` may widen timing windows, but it does not provide correctness.

## Part 2 - Thread Lifecycle

Run demos in `src/main/java/.../part2_lifecycle`.

Observe:

- Thread states: NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING, TERMINATED.
- Priority is a scheduling hint, not a correctness tool.
- Sleeping while holding a lock does not release that lock.
- Interrupt handling should preserve cancellation intent.

## Part 3 - Coordination Core

Run `Demo14` through `Demo18` in `part3_coordination`.

Observe:

- `join()` gives a real completion relationship.
- `sleep()` only waits for time to pass.
- `join(timeout)` can return before work is complete.

## Preview Demos

The later `part3_coordination` demos and `part4_race_conditions` are useful
previews for Weeks 3, 5, and 7. Treat them as enrichment until Week 2
student exercises are added.

## Deliverables

- Priority experiment results in `data/priority-results-template.csv`.
- Short explanation:
  - why `run()` does not create a new thread
  - why `sleep()` is unreliable for ordering
  - difference between BLOCKED and WAITING
  - what interruption does and does not guarantee
- Completed `CONCURRENCY_SCORECARD.md`.
- Green demo tests with `.\gradlew.bat test`.
