# Lab Instructions - Week 4

## Goals

1. Reproduce and explain stale-read style behavior.
2. Fix visibility with `volatile` where applicable.
3. Demonstrate that `volatile` does not make `counter++` atomic.
4. Repair broken DCL using `volatile` + synchronized block.
5. Build a lock-free reader path using immutable snapshots.

## Deliverables

1. Green `StudentWeek4*` tests.
2. Short explanation for each part:
   - Why it broke.
   - Which guarantee fixed it (visibility, atomicity, publication).
3. A sequence diagram for immutable snapshot flow.
   - Submit file: `SEQUENCE_DIAGRAM.md`.
4. Updated scorecard.
