from __future__ import annotations

import unittest

from week9.part1_threads_vs_processes.exercises import explain_cpu_bound_choice


class StudentWeek9Part1Test(unittest.TestCase):
    def test_explains_threads_processes_and_cost(self) -> None:
        answer = explain_cpu_bound_choice()

        self.assertIn("gil", answer["threads"].lower())
        self.assertIn("core", answer["multiprocessing"].lower())
        self.assertTrue(
            "serialization" in answer["cost"].lower()
            or "memory" in answer["cost"].lower()
            or "startup" in answer["cost"].lower()
        )


if __name__ == "__main__":
    unittest.main()
