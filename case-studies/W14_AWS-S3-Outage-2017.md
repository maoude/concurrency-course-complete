# Week 14 Case Study: AWS S3 Outage (2017)

## Incident Snapshot

On February 28, 2017, an operational mistake during routine debugging/maintenance triggered removal of more S3 capacity than intended in a region, causing broad service disruption and cascading impact across dependent systems.

## Why It Belongs in Week 14

Week 14 synthesizes distributed failure handling: partial failure, dependency blast radius, observability, and recovery planning. S3 illustrates how one control-plane event can cascade across many services.

## Mechanism-Level Reading (Course Lens)

- Control-plane operation exceeded intended scope.
- Recovery required subsystem restarts and state convergence.
- Many customers had implicit single-region dependency assumptions.
- Monitoring and status propagation lag amplified uncertainty.

## Distributed Systems Lessons

1. Dependency concentration creates systemic blast radius.
2. Control-plane actions need strict guardrails and scoped execution.
3. Fast detection is insufficient without rehearsed recovery runbooks.
4. Region/zone resilience is an application responsibility, not only provider responsibility.

## Discussion Prompts (10-15 min)

1. Which of your services fail closed vs fail degraded when storage is unavailable?
2. Which operations in your platform need two-person or staged confirmation?
3. What is your maximum tolerated control-plane recovery time?
4. Which user journeys still work in a regional storage outage?

## What We Would Instrument Today

- Explicit dependency map with critical-path tagging.
- SLOs per dependency and customer-facing impact indicators.
- Automated failover/fallback drills with recorded MTTR.
- Guarded admin operations: scoped dry-run, approval steps, rate-limited execution.
- End-to-end tracing to separate primary failure from secondary cascade.

## One-Line Takeaway

Distributed reliability is engineered through blast-radius control, explicit dependencies, and practiced recovery.
