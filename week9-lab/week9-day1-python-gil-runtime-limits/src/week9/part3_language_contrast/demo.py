from __future__ import annotations


def language_contrast() -> dict[str, str]:
    return {
        "python": "threads are useful for I/O, but CPU-bound bytecode is constrained by the GIL",
        "go": "goroutines are scheduled by the runtime and often communicate with channels",
        "erlang": "actors communicate by message passing and are designed around fault tolerance",
    }
