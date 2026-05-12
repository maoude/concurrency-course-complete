# Checkpoint 2 Rubric - Week 5 Thread-Pooled Server

Checkpoint 2 is worth **15% of the portfolio**.

The deliverable is a thread-pooled server design that uses bounded resources, explicit overload behavior, observable metrics, and clear liveness notes. The Week 5 exercises are the building blocks for the checkpoint.

## Required Submission

- Working code for the checkpoint server or server component.
- Passing Week 5 `studentCheck`.
- Completed Week 5 `CONCURRENCY_SCORECARD.md`.
- Measurement table using `MEASUREMENT_TEMPLATE.md`.
- Short explanation paragraph for each major concurrency decision.

## Grading Criteria

| Area | Weight | Evidence |
| --- | ---: | --- |
| Bounded request queue | 20% | Server uses a bounded queue or equivalent bounded admission path; behavior when full is explicit. |
| Bounded executor policy | 20% | Worker count and queue capacity are configured deliberately; rejection/backpressure policy is documented and demonstrated. |
| Correctness under contention | 20% | Shared metrics/state are race-free; tests or repeated runs show correct counts and no lost updates. |
| Liveness and fairness notes | 15% | Submission explains blocking, rejection, starvation, and fairness risks, including read-heavy or overloaded cases where relevant. |
| Measurement evidence | 15% | Table includes workload, configuration, repetitions, p50/p99 or equivalent latency notes, throughput, and tradeoff paragraph. |
| Request context cleanup | 10% | Per-request context does not leak across pooled workers; cleanup uses `finally`, try-with-resources, or equivalent discipline. |

## Exercise Mapping

| Week 5 Exercise | Checkpoint Role |
| --- | --- |
| Ex1 `BoundedBuffer` | Request queue / admission control |
| Ex2 `BoundedExecutor` | Server thread pool and overload policy |
| Ex3 `AtomicMetrics` | Request count, latency, and observability |
| Ex4 `ReadMostlyCache` | Optional read-heavy shared state or cache layer |
| Ex5 `RequestContext` | Per-request isolation and cleanup |

## Explanation Quality

Each written explanation should include:

- **Symptom:** what failure or risk appears under load.
- **Mechanism:** why the concurrency primitive behaves that way.
- **Fix:** what design choice was made.
- **Tradeoff:** what cost or residual liveness risk remains.

## Minimum Passing Bar

A submission cannot pass if it:

- uses an unbounded queue for request admission without justification;
- creates one new thread per request;
- hides overload by silently dropping required work;
- reports measurements without workload/configuration details;
- leaks request context across pooled worker tasks.

## Suggested Demo Script

1. Start the server.
2. Send a small load and show successful processing.
3. Send overload and show the bounded behavior: block, reject, or caller-runs.
4. Print metrics before and after the load.
5. Show the completed scorecard and explain one remaining liveness risk.
