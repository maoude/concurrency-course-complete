from __future__ import annotations


def contrast_models() -> dict[str, str]:
    return {
        "python": "CPython has a GIL, so CPU-bound threads do not execute Python bytecode in parallel.",
        "go": "Go uses goroutines scheduled by the runtime, with channels for communication.",
        "erlang": "Erlang uses actors and message passing with supervision for fault tolerance.",
    }
