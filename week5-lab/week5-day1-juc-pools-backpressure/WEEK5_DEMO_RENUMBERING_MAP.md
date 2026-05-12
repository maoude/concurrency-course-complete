# Week 5 Demo Numbering Note

Week 5 keeps globally unique demo numbers, but Part 2 intentionally contains later demos:

| Current Demo | Location | Reason |
| --- | --- | --- |
| Demo01-Demo03 | Part 1 - BlockingQueue | Backpressure foundation |
| Demo04-Demo06 | Part 2 - Thread pools | Original pool and rejection sequence |
| Demo07-Demo09 | Part 3 - Atomics/CAS | Counter and CAS sequence |
| Demo10-Demo11 | Part 4 - Read-write locks | Cache sequence |
| Demo12-Demo13 | Part 5 - ThreadLocal | Context and leak sequence |
| Demo14-Demo17 | Part 2 - Thread pools | Added later to cover Callable/Future, cached pools, throughput, and executor contrast |
| Demo18 | Part 4 - Read-write locks | Added later for writer starvation / fairness discussion |
| Demo19 | Part 5 - ThreadLocal | Added later for measured memory retention |

The source files are not renumbered to avoid breaking lecture references, tests, and existing student notes. New demos continue from Demo18.
