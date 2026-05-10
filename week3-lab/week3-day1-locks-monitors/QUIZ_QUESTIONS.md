1) Why does `count++` lose updates under multiple threads?
2) What does it mean for two threads to "share a lock"?
3) Why is `synchronized (new Object())` useless for coordination?
4) What is the difference between a `synchronized` method and a `synchronized` block?
5) What thread state is reported for a thread waiting to enter a `synchronized` block?
6) What thread state is reported for a thread inside `Object.wait()`?
7) Why doesn't a thread deadlock against itself when calling its own `synchronized` method recursively?
