# Week 4 Case Study: Therac-25

## Incident Snapshot

Between 1985 and 1987, multiple patients received severe radiation overdoses from the Therac-25 medical linear accelerator. Some incidents were fatal. Earlier hardware interlocks present in previous models were removed, and software became the primary safety barrier.

## Why It Belongs in Week 4

Week 4 focuses on visibility, ordering, and correctness assumptions in concurrent logic. Therac-25 is a historical example of what happens when software state management and safety assumptions are wrong in critical systems.

## Mechanism-Level Reading (Course Lens)

- Shared state assumptions were unsafe.
- Error handling paths were incomplete.
- Safety logic relied on software behavior that was not independently verified under adverse timing/state conditions.
- Defensive physical safeguards were reduced while software complexity increased.

## Concurrency/System Lessons

1. Safety-critical invariants must be explicit, testable, and independently guarded.
2. "Rare interleavings" are still production behavior.
3. Removing hardware/process safeguards requires stronger software verification, not equal verification.
4. Observability is not optional in safety-critical workflows.

## Discussion Prompts (10-15 min)

1. Which invariant would you define first for treatment-state transitions?
2. What failure should force immediate safe-mode shutdown?
3. Which checks must be in hardware/process, not only code?
4. What telemetry would let operators detect unsafe state early?

## What We Would Instrument Today

- State-transition audit log with immutable event ordering.
- Mandatory pre-fire invariant checks with explicit failure reason codes.
- Watchdog on timing/state anomalies.
- Independent safety-channel alerts (separate from primary control path).
- Incident replay package: full timeline, operator actions, device states.

## One-Line Takeaway

If a system can harm people, correctness must survive worst-case timing and state, not just normal workflows.
