from __future__ import annotations

import unittest

from week9.part1_threads_vs_processes.solutions import explain_cpu_bound_choice
from week9.part2_visual_proof.solutions import visual_proof_plan
from week9.part3_language_contrast.solutions import contrast_models
from week9.part4_case_study.solutions import discussion_points


class InstructorSolutionsTest(unittest.TestCase):
    def test_solutions_match_student_contract(self) -> None:
        part1 = explain_cpu_bound_choice()
        self.assertIn("gil", part1["threads"].lower())
        self.assertIn("core", part1["multiprocessing"].lower())
        self.assertIn("overhead", part1["cost"].lower())

        part2 = " ".join(visual_proof_plan()).lower()
        self.assertIn("thread", part2)
        self.assertIn("process", part2)
        self.assertTrue("task manager" in part2 or "htop" in part2)

        part3 = {key: value.lower() for key, value in contrast_models().items()}
        self.assertIn("gil", part3["python"])
        self.assertIn("goroutines", part3["go"])
        self.assertIn("actors", part3["erlang"])

        part4 = " ".join(discussion_points()).lower()
        self.assertIn("deployment", part4)
        self.assertIn("stale", part4)
        self.assertIn("controls", part4)


if __name__ == "__main__":
    unittest.main()
