from __future__ import annotations

import unittest

from week9.part1_threads_vs_processes.demo import compare_small_cpu_workload, cpu_bound_work
from week9.part2_visual_proof.demo import visual_proof_steps
from week9.part3_language_contrast.demo import language_contrast
from week9.part4_case_study.demo import knight_capital_lessons


class DemoSmokeTest(unittest.TestCase):
    def test_cpu_bound_work_returns_int(self) -> None:
        self.assertIsInstance(cpu_bound_work(100), int)

    def test_demo_summaries_are_present(self) -> None:
        comparison = compare_small_cpu_workload()
        self.assertIn("workers", comparison)
        self.assertIn("GIL", comparison["lesson"])

        steps = visual_proof_steps()
        self.assertTrue(any("thread" in step for step in steps))
        self.assertTrue(any("process" in step for step in steps))

        contrast = language_contrast()
        self.assertIn("GIL", contrast["python"])
        self.assertIn("goroutines", contrast["go"])
        self.assertIn("message", contrast["erlang"])

        lessons = knight_capital_lessons()
        self.assertTrue(any("deployment" in lesson for lesson in lessons))


if __name__ == "__main__":
    unittest.main()
