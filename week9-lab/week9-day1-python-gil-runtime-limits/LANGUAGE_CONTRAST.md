# Go and Erlang Contrast - Week 9

## Python

Python threads are convenient for I/O-bound work, but CPU-bound Python bytecode
is constrained by the Global Interpreter Lock in the standard CPython runtime.

## Go

Go uses goroutines scheduled by the Go runtime. Communication is commonly
modeled with channels. CPU-bound goroutines can run in parallel across OS
threads when the runtime has multiple processors available.

## Erlang

Erlang uses lightweight processes and message passing. Its model emphasizes
isolation, supervision, and fault tolerance.

## Course Point

Concurrency APIs are not interchangeable. Each runtime makes different tradeoffs
around scheduling, memory sharing, communication, and failure.
