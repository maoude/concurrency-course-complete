# Concurrency Pitfalls Cheat Sheet

A growing reference. Each "Release" is unlocked at the week it
appears in the syllabus. Earlier releases stay relevant for the rest
of the course - new releases extend, never replace.

Every weekly lab README links to the relevant section.

> **Reading rule:** for each pitfall, read the *Symptom*, then the
> *Mechanism*, then the *Fix*. If you can only explain the symptom,
> your understanding is one level too shallow.

---

## Release 1 - Week 2 (Threads & Lifecycle)

### P1.1 - Using `sleep()` for synchronization

- **Symptom:** "If I sleep 100 ms, the other thread will surely be done by then."
- **Mechanism:** `Thread.sleep` is a *suggestion to the scheduler*, not a
  contract about other threads. The thread you were waiting for might
  be slower than you guessed (GC pause, I/O stall, cold cache, busy
  CPU, virtualized host). Even worse: your code becomes wrong only
  *sometimes*, which makes the bug invisible until production.
- **Fix:** use a real coordination primitive - `join`, `CountDownLatch`,
  `Semaphore`, `Object.wait/notify`, `BlockingQueue`. They all express
  a *happens-before* relationship; `sleep` does not.
- **Demo (week2):** `Demo14_SleepVsJoinFailure`, `Demo16_SleepDoesNotGuaranteeOrder`.

### P1.2 - Assuming thread order or fairness

- **Symptom:** "Thread A starts before B, so A's output appears first."
- **Mechanism:** the JVM, the OS scheduler, NUMA topology, and CPU
  pinning all conspire to interleave threads in any legal order.
  Java's intrinsic monitors are not fair by default; even
  `ReentrantLock` is unfair unless you ask for `new ReentrantLock(true)`.
  Priorities are *hints*, not guarantees.
- **Fix:** never rely on observed order. If order matters, *enforce*
  it with `join`, a queue, or a barrier. If fairness matters, use a
  fair lock or a queue with FIFO semantics - and accept the throughput
  cost.
- **Demo (week2):** `Demo04_Interleaving`, `Demo09_PriorityExperiment`.

---

## Release 2 - Week 5 (java.util.concurrent)

### P2.1 - Oversizing thread pools

- **Symptom:** "More threads = more throughput. Let's set
  `Executors.newFixedThreadPool(500)`."
- **Mechanism:** every additional thread costs about 0.5-1 MB of stack,
  contends for CPU, contends for locks, and contends for the cache.
  Past the workload's natural concurrency point, adding threads makes
  things slower while looking busier (high context-switch rate, low
  IPC).
- **Fix:**
  - CPU-bound work: pool size ~ `Runtime.getRuntime().availableProcessors()`.
  - I/O-bound work: pool size ~ `cores * (1 + waitTime/cpuTime)` (Little's Law from W1.P3).
  - Always *measure*. Pick the knee, not the cliff.
- **Callbacks:** W1.P3 ThreadPoolTuner / AutoTuner; W5.5.2 pool sizing exercise.

### P2.2 - Unbounded queues

- **Symptom:** "I'll just use `LinkedBlockingQueue` with the default
  size."
- **Mechanism:** the default capacity is `Integer.MAX_VALUE`. If
  producers outpace consumers, the queue grows until you OOM. Worse,
  there is no backpressure - producers get fast acks while the system
  is silently failing. Tail latency explodes long before the OOM.
- **Fix:** *always* bound the queue. Decide what to do at the bound:
  block (`put`), reject (`offer` + reject policy), or drop. The
  decision is a business decision, not a default.
- **Demo (week5):** `Lab 5.1 - bounded BlockingQueue: full vs empty behaviour`.

---

## Release 3 - Week 8 (Midterm)

### P3.1 - Optimizing before measuring

- **Symptom:** "I added `parallel()` everywhere and it got faster - I think."
- **Mechanism:** speedup intuition is wrong roughly half the time
  (boxing, partitioning, contention, cache invalidation, GC, JIT
  warm-up). "Faster" measured on one untracked iteration is meaningless.
- **Fix:** baseline first. Use JMH for micro-benchmarks, JFR /
  async-profiler for macro. Report median + p99, not "average."
  Apply the Karp-Flatt formula (W1.P4.Ex2) to detect
  scalability decay early.

### P3.2 - "Async = faster"

- **Symptom:** "We rewrote it with `CompletableFuture` and it didn't
  speed up."
- **Mechanism:** async eliminates *blocked threads*, not work. If your
  bottleneck is CPU-bound or already non-blocking, async only adds
  overhead (continuation allocation, thread hops, scheduler pressure).
  Async wins when many requests sit on slow I/O at the same time -
  the NIO counterpoint (W7.5) makes this concrete.
- **Fix:** ask "what is currently waiting?" before reaching for async.
  If the answer is "nothing," you don't need it.

### P3.3 - Fixing the symptom, not the mechanism

- **Symptom:** "The test was flaky so I added a 200 ms sleep before
  the assertion."
- **Mechanism:** the test was telling you that something downstream is
  not deterministic. Hiding the signal makes the underlying race
  *worse*, because now CI is happy.
- **Fix:** find the real synchronization point - latch, future,
  callback, polled condition - and assert on it. If the production
  code can't expose one, that's a *production bug*, not a test bug.

---

## Release 4 - Week 11 (Networks)

### P4.1 - Assuming network reliability

- **Symptom:** "It works on my machine, in the lab, with the server
  next door."
- **Mechanism:** networks lose packets, reorder, duplicate, partition,
  and lie about success (TCP `write` returning OK only means it left
  the kernel buffer). Cross-AZ latency varies by 100x in seconds
  during a noisy-neighbour event. The Three Laws of Networks (W10):
  latency is never zero, bandwidth is never infinite, failures are
  partial and invisible.
- **Fix:** every remote call gets a *timeout*, a *retry* (with
  jitter), and an *idempotency key* if it mutates state. Use
  Toxiproxy in tests to inject the failures you'll see in production.

### P4.2 - Network calls without timeouts

- **Symptom:** thread dump shows hundreds of threads parked on
  `socketRead0`. The service has been frozen for 40 minutes.
- **Mechanism:** the default JDK socket has *no read timeout*. A
  silent drop, a half-closed connection, or a blackholed firewall
  rule will keep the call blocked until the OS gives up - which can
  be hours. Your thread pool fills, queues fill, upstreams retry,
  and one slow downstream becomes a cascading outage.
- **Fix:** set explicit connect *and* read timeouts on every client
  (`HttpClient.Builder.connectTimeout`, `Socket.setSoTimeout`,
  gRPC deadlines). Pair them with circuit breakers (W13) so the
  failure becomes a *fast* error rather than a slow one.

---

## Quick check - "do I still believe these?"

1. Does my code anywhere use `Thread.sleep` to *coordinate* (not
   throttle)? - **delete it**.
2. Is any of my queues unbounded? - **bound it**.
3. Is any remote call without a timeout? - **add one**.
4. Does my retry path mutate state without an idempotency key? -
   **add one**.
5. Did I measure before I optimized? - if not, **revert**.
