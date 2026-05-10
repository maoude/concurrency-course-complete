# Course TODO - Concurrency & Distributed Systems

Master plan for the 14-week semester. Tracks every deliverable
implied by `Lectures/syllabus_v2.tex`. The live state lives in the
TaskList tool; this file is the durable copy.

Legend: `[x]` done, `[~]` in progress, `[ ]` pending,
`(blocked by #N)` means waits for that task.

---

## Foundation (already done)

- [x] **#7** Define exercise/solution convention - `CONVENTIONS.md`
- [x] **#8** EXERCISES.md template + `gradlew studentCheck` task
  (wired into all three existing labs)

---

## Existing labs - finish exercises

### Week 1 - Why concurrency exists (W1-lab)

- [ ] **#9**  W1.P1 Single-threaded server exercises
- [ ] **#10** W1.P2 Multi-threaded server exercises
- [ ] **#11** W1.P3 Thread-pool tuning + Little's Law exercises
- [x] **#12** W1.P4 Amdahl's Law exercises (Ex1 speedup, Ex2 Karp-Flatt)

### Week 2 - Threads, lifecycle (W2-lab)

- [ ] **#13** W2.P1 Thread creation basics
- [ ] **#14** W2.P2 Thread lifecycle states
- [ ] **#15** W2.P3 Coordination - join vs sleep
- [ ] **#16** W2.P4 Race conditions

### Week 3 - Synchronization & Locks (W3-lab)

- [x] **#17** W3.P1 Races (counter + bank account)
- [x] **#18** W3.P2 Monitors / synchronized
- [x] **#19** W3.P3 Lock identity
- [~] **#20** W3.P4 BLOCKED vs WAITING (in progress - Ex1 stub written)
- [ ] **#21** W3.P5 Reentrancy

### Week 3 - Missing labs from syllabus (added after syllabus check)

- [ ] **#29** Lab 3.2 Transfer A↔B deadlock + fix by lock ordering
       (also: state-diagram deliverable)
- [ ] **#30** Lab 3.3 Interruption handling - InterruptedException +
       restored interrupt status (blocked by #29)

---

## Cross-cutting recurring artifacts

- [x] **#25** `CONCURRENCY_SCORECARD.md` template + per-lab copy
       (6 items: thread-safe / visibility / deadlock-free / liveness /
       bounded resources / failure recovery). Backfill into W1, W2, W3.
- [x] **#26** Explanation Quality Rubric snippet in every lab README
       (Poor / Good / Excellent levels).
- [x] **#27** `PITFALLS.md` released in 4 progressive versions:
       R1 W2  - sleep-for-sync, order/fairness assumptions
       R2 W5  - oversized pools, unbounded queues
       R3 W8  - measure-before-optimize, async != faster
       R4 W11 - network reliability, always time out network calls
- [x] **#28** Case-study handouts:
       Therac-25 in W4, Knight Capital in W9, AWS S3 in W14.
- [ ] **#57** Repo-root `SEMESTER_PROJECT.md` - the evolving project arc
       tying W3 / W5 / W8 / W14 checkpoints together.

---

## Phase 1 - Shared-Memory Concurrency (continued)

### Week 4 - Memory Visibility & Immutability

- [ ] **#31** W4 lab scaffold (blocked by #22 _template-week)
- [ ] **#32** W4 exercises (blocked by #31):
       Ex4.1 stale read fixed by `volatile`
       Ex4.2 broken counter - prove `volatile` alone is not enough
       Ex4.3 double-checked locking with `volatile`
       Ex4.4 immutable snapshot configuration cache + sequence diagram

### Week 5 - java.util.concurrent (Checkpoint 2 - 15%)

- [ ] **#33** W5 lab scaffold (blocked by #22)
- [ ] **#34** W5 exercises (blocked by #33):
       5.1 Bounded BlockingQueue / backpressure
       5.2 Pool sizing (I/O vs CPU bound, callback to W1.P3)
       5.3 CAS counter vs synchronized counter (measurement table)
       5.4 ReentrantReadWriteLock vs synchronized (read:write > 10:1)
       5.5 ThreadLocal request context + leak demo
- [ ] **#35** W5 Checkpoint 2 rubric (15% of portfolio)

### Week 6 - Parallelism & Fork/Join

- [ ] **#36** W6 lab scaffold (blocked by #22)
- [ ] **#37** W6 exercises (blocked by #36):
       6.1 ForkJoin vs sequential
       6.2 Break-even point measurement (numeric target)
       6.3 Parallel-streams anti-example with the four causes

### Week 7 - Async, Testing, Anti-patterns

- [ ] **#38** W7 lab scaffold (blocked by #22)
- [ ] **#39** W7 exercises (blocked by #38):
       7.1 Async + timeout/fallback
       7.2 "Passes 1000, fails at 1001" + reproduction strategy
       7.3 JCStress-style stress test
       7.4 Fan-out / fan-in pipeline (REQUIRED) + sequence diagram
       7.5 NIO vs multi-threaded blocking (REQUIRED)
- [ ] **#40** W7 Anti-patterns module handout (blocked by #38)

### Week 8 - Midterm (25%)

- [ ] **#41** W8 midterm packet (blocked by #34 and #39):
       broken concurrent program, thread dump, diagnosis report,
       fix, performance tradeoff, 5-min recorded explanation.
       Includes Kafka mental-model preview lecture handout.

---

## Phase 2 - Language Models & Limits

### Week 9 - Python GIL

- [ ] **#42** W9 Python lab (separate non-Gradle project):
       9.1 threads vs multiprocessing (CPU-bound)
       9.2 htop / py-spy visual proof
       + 15-min Go/Erlang contrast handout
       + Knight Capital war-story discussion

---

## Phase 3 - Distributed Systems

### Week 10 - Network = Concurrency + Failure (Toxiproxy intro)

- [ ] **#43** W10 lab scaffold (blocked by #22):
       Toxiproxy via Docker Compose, Three Laws of Networks handout
- [ ] **#44** W10 exercises (blocked by #43):
       Lab 10.1 - reuse W5 BlockingQueue logic over a socket;
       inject latency, packet loss, connection reset; measurement.

### Week 11 - Client-Server, Serialization, Versioning

- [ ] **#45** W11 lab scaffold (blocked by #22)
- [ ] **#46** W11 exercises (blocked by #45):
       11.1 Multi-client + immutability/defensive-copy fix
       11.2 Versioning - Protobuf field evolution (recommended)
            OR Java serialization with serialVersionUID
       + sequence diagram of message flow
- [ ] **#47** W11 Peer-review checkpoint instructions (blocked by #45)

### Week 12 - Kafka

- [ ] **#48** W12 lab scaffold (blocked by #22) - Kafka via Docker Compose
- [ ] **#49** W12 exercises (blocked by #48):
       12.1 Partitions + same-key routing + ordering
       12.2 Consumer groups + rebalance (2 consumers / 4 partitions)
       12.3 Backpressure + lag (10k msg/s flood, slow consumer)
- [ ] **#50** W12 Partition decision exercise (blocked by #48):
       1M msg/s, 3 consumers, 100ms max lag - propose partitions
       + LinkedIn Kafka case study

### Week 13 - RPC, Cascading Failure, Tracing, Idempotency, Clock Drift

- [ ] **#51** W13 lab scaffold (blocked by #22):
       gRPC starter + Resilience4j (or pseudocode state machine)
- [ ] **#52** W13 exercises (blocked by #51):
       13.1 Cascading failure - 6 steps from baseline through
            circuit breaker + idempotency
       13.2 Tracing - 3-service request-ID propagation, manual
            timeline reconstruction
       13.3 Clock drift - timestamp ordering violation demo

### Week 14 - Final Synthesis (30%)

- [ ] **#53** W14 final synthesis packet (blocked by #44 #46 #49 #52):
       portfolio rubric + 5-minute presentation guide +
       student-facing checklist
- [ ] **#54** W14 Architecture Decision Memo template (20%):
       decision matrix + 40/30/20/10 rubric
- [ ] **#55** W14 Tool Evaluation Framework template (5 questions)
- [ ] **#56** W14 Annotated bibliography template:
       Actor models, CRDTs, Raft/Paxos, Observability, Formal testing

---

## Future-week scaffolding & instructor mode

- [ ] **#22** Create `_template-week/` (blocks every weekly scaffold)
- [ ] **#23** Wire `solutions/` source set behind `-Pinstructor=true`
       (already partially done for W1, W2, W3 build.gradle - needs
       template-week + a CONVENTIONS update)
- [ ] **#24** Update each existing week's README with the new
       Exercises section + `gradlew studentCheck` (blocked by all
       per-part tasks #9-#21).

---

## Portfolio weighting (from syllabus)

| Checkpoint        | Weight | Tied to Tasks            |
|-------------------|-------:|--------------------------|
| W3  Thread-safe   |   10% | #17-#21, #29, #30        |
| W5  Pooled        |   15% | #34, #35                 |
| W8  Midterm       |   25% | #41                      |
| W14 Final         |   30% | #53                      |
| Decision Memo     |   20% | #54                      |

(Note: percentages above sum past 100 in the syllabus because the
"portfolio" is 80% and the memo is 20% - portfolio sub-weights are
within the 80%.)

---

## Rule of the course

> *You don't move forward by adding features - you move forward by
> surviving failure.*
