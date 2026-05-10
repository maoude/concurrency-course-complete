## Common Issues
- Counter is "always correct" in Demo01: increase ITERATIONS or THREADS,
  pin to a single CPU, or run on a busier machine.
- BadCounter in Demo06 looks correct: it never will if the JVM happens to
  serialize threads, but on any modern multi-core box it is broken.
- Demo07 shows BOTH threads as BLOCKED: you didn't give the lock-holder
  enough time to take the monitor before starting the others. Increase
  the initial sleep.
- jstack not found: use `jcmd <pid> Thread.print`.
- "IllegalMonitorStateException": you called `wait()` / `notify()` without
  holding the monitor. They must be called from inside `synchronized` on
  the same object.
