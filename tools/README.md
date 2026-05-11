# Course Build Tools

These scripts are for the instructor workflow.

## Build Lecture PDFs

Build every local `Lectures/*.tex` file into a sibling PDF:

```powershell
.\tools\build-lectures.ps1
```

The script uses `latexmk` when Perl is available. If MiKTeX has `pdflatex`
but not Perl, it falls back to two `pdflatex` passes for one-shot builds.

Build one lecture:

```powershell
.\tools\build-lectures.ps1 -Tex Lectures\syllabus_v2.tex
```

Clean LaTeX byproducts:

```powershell
.\tools\build-lectures.ps1 -Clean
```

## Watch While Editing

Use this beside TeXstudio. Every save triggers a rebuild:

```powershell
.\tools\watch-lecture.ps1 -Tex Lectures\Lecture_04_01_nar.tex
```

Watch mode requires `latexmk` plus Perl. With MiKTeX on Windows, install
Strawberry Perl if `latexmk` reports that it cannot find the Perl script
engine.

Git ignores `Lectures/*.tex` and LaTeX byproducts, but allows
`Lectures/*.pdf`, so the student-facing repository receives PDFs only.
