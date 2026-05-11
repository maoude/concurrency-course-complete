# LaTeX Lab Manuals - Status and Template

**Date Created:** May 11, 2026  
**Template Source:** `Lectures/Setup.tex` and `Lectures/Lect_00_Setup.tex`

## Completed Manuals

### ✅ Week 2: Threads and Lifecycle
- **File:** `Lectures/Lab_Week02_ThreadsAndLifecycle.tex`
- **Topics:** Thread creation, lifecycle states, join, coordination primitives (wait/notify, latches, semaphores)
- **Structure:** 3 parts, 22 demos (Demo01-Demo22), 3 exercises
- **Lines:** ~350 lines of LaTeX

### ✅ Week 5: java.util.concurrent, Pools, and Backpressure
- **File:** `Lectures/Lab_Week05_JavaUtilConcurrent.tex`
- **Topics:** BlockingQueue, ExecutorService, Callable/Future, rejection policies, atomics, read-write locks, ThreadLocal
- **Structure:** 5 parts, 17 demos (Demo01-Demo17), 5 exercises
- **Lines:** ~500 lines of LaTeX

## Standard Template Structure

Each lab manual uses:

1. **Preamble** (same for all weeks)
   - Document class: `article`, 11pt, A4
   - Packages: `fancyhdr`, `listings`, `xcolor`, `booktabs`, hyperref, amssymb, amsmath
   - Custom commands: `\course`, `\univ`, `\faculty`, `\instructor`
   - Fancy page headers/footers with LU branding, copyright, page numbers

2. **Title Section**
   - Centered title with week number and topic
   - Subtitle: Topic name

3. **Learning Objectives**
   - Bulleted list of 5-8 key learning outcomes

4. **Lab Structure Table**
   - 3 columns: Part | Topic | Demos + Exercises
   - Shows how the week is organized

5. **Part Sections** (one per part)
   - Each part has 2-5 subsections (one per demo/exercise)
   - Subsection structure:
     - **Goal:** (1 sentence)
     - **Logic:** (itemized steps)
     - **Key Insight:** (bold statement)
     - Optional: **Code Pattern:** (Java code listing)

6. **Summary Section**
   - Recap: how all pieces fit together
   - Numbered list of key concepts

7. **Checkpoint Tie-In** (if applicable)
   - Shows how exercises support project checkpoints

## Remaining Lab Manuals (Template Available)

To create these, follow the template in `/memories/repo/latex_lab_manual_template.md`:

| Week | Topic | Estimated Demos | File Name |
|------|-------|-----------------|-----------|
| 1 | Foundations & Benchmarking | 15-20 | `Lab_Week01_Foundations.tex` |
| 3 | Races, Monitors, Lock Identity | 20-25 | `Lab_Week03_RacesAndMonitors.tex` |
| 4 | Memory Visibility & Immutability | 15-20 | `Lab_Week04_MemoryVisibility.tex` |

## How to Create New Manuals

1. **Copy the preamble** from Week 2 or Week 5 (lines 1-60)
2. **Update the title** to match the week/topic
3. **Add Learning Objectives** (5-8 key concepts)
4. **Create Lab Structure table** showing parts, topics, demos
5. **For each part:**
   - Create a `\section{Part N: Topic Name}`
   - For each demo: `\subsection{DemoNN: Name}` with Goal, Logic, Key Insight
   - For exercises: `\subsection{Exercise N: Name}` with Goal, Requirements
6. **Add Summary** section recapping all ideas
7. **Add Checkpoint Tie-In** if applicable
8. **End with `\end{document}`**

## Compilation

To generate PDFs:

```bash
cd Lectures
pdflatex Lab_Week02_ThreadsAndLifecycle.tex
pdflatex Lab_Week05_JavaUtilConcurrent.tex
```

Output files:
- `Lab_Week02_ThreadsAndLifecycle.pdf` (12-15 pages)
- `Lab_Week05_JavaUtilConcurrent.pdf` (15-20 pages)

## Integration Notes

- **Location:** All manuals in `Lectures/` folder alongside lecture PDFs
- **Naming:** `Lab_WeekXX_TopicName.tex`
- **Watermark:** Optional synapse_ai_logo_t.png (LaTeX auto-skips if missing)
- **Student Access:** Include PDFs in course repository; source .tex files optional

## Design Philosophy

Each manual:
- **Explains the logic:** not just code, but WHY each demo matters
- **Shows progression:** from simple (Demo01) to complex (final demo/exercise)
- **Provides code patterns:** idiomatic Java examples students can reuse
- **Captures key insights:** one bold statement per concept
- **Ties to exercises:** each part's exercises reinforce demos

This approach makes the manuals **self-contained teaching resources**, not just lab descriptions.

## Next Steps

1. ✅ Create Week 2 manual (DONE)
2. ✅ Create Week 5 manual (DONE)
3. ⏳ Create Week 1 manual (Foundations & Benchmarking)
4. ⏳ Create Week 3 manual (Races & Monitors)
5. ⏳ Create Week 4 manual (Memory Visibility)

All follow the same template; est. 300-500 lines of LaTeX each.
