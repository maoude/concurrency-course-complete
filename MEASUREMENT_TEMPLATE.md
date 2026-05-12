# Measurement Template

Use this template for Week 5 and later labs when a deliverable asks for a measurement table.

## Method

- JDK version:
- CPU / cores:
- OS:
- Workload:
- Warm-up runs:
- Measured runs:
- Timing method: `System.nanoTime`, JFR, or other:
- Notes about background load:

## Results

| Configuration | Workload | Warm-up runs | Measured runs | p50 latency | p99 latency | Throughput | Notes |
| --- | --- | ---: | ---: | ---: | ---: | ---: | --- |
| Sequential / baseline | | | | | | | |
| Fixed pool | | | | | | | |
| Cached pool | | | | | | | |
| Bounded pool + rejection | | | | | | | |

## Tradeoff Paragraph

Write 5-8 sentences:

- What configuration performed best for this workload?
- What resource was bounded?
- What happened under overload?
- What liveness risk remains?
- Why should these numbers not be treated as universal?
