<#
.SYNOPSIS
Build lecture PDFs from local LaTeX sources.

.DESCRIPTION
The repository is configured to keep Lectures/*.tex private and publish only
Lectures/*.pdf. This script compiles the local .tex files with latexmk so the
PDFs are ready before a student-facing GitHub push.

Examples:
  .\tools\build-lectures.ps1
  .\tools\build-lectures.ps1 -Tex Lectures\syllabus_v2.tex
  .\tools\build-lectures.ps1 -Clean
#>

[CmdletBinding()]
param(
    [string[]] $Tex,
    [switch] $Clean,
    [switch] $List,
    [ValidateSet('auto', 'latexmk', 'pdflatex')]
    [string] $Engine = 'auto'
)

$ErrorActionPreference = 'Stop'

$repoRoot = Split-Path -Parent $PSScriptRoot
$lecturesDir = Join-Path $repoRoot 'Lectures'

if (-not (Test-Path -LiteralPath $lecturesDir)) {
    throw "Lectures folder not found: $lecturesDir"
}

$latexmk = Get-Command latexmk -ErrorAction SilentlyContinue
$pdflatex = Get-Command pdflatex -ErrorAction SilentlyContinue
$perl = Get-Command perl -ErrorAction SilentlyContinue

if ($Engine -eq 'latexmk' -and -not $latexmk) {
    throw "latexmk was not found on PATH. Install MiKTeX/TeX Live or add latexmk to PATH."
}
if ($Engine -eq 'pdflatex' -and -not $pdflatex) {
    throw "pdflatex was not found on PATH. Install MiKTeX/TeX Live or add pdflatex to PATH."
}

$useLatexmk = $false
if ($Engine -eq 'latexmk') {
    $useLatexmk = $true
} elseif ($Engine -eq 'auto') {
    $useLatexmk = [bool] $latexmk -and [bool] $perl
}

if (-not $useLatexmk -and -not $pdflatex) {
    throw "Neither usable latexmk nor pdflatex was found on PATH."
}

if ($Engine -eq 'auto' -and $latexmk -and -not $perl) {
    Write-Warning "latexmk is installed, but Perl is missing. Falling back to pdflatex. Install Perl for latexmk watch mode."
}

if ($Tex -and $Tex.Count -gt 0) {
    $texFiles = foreach ($item in $Tex) {
        $path = if ([IO.Path]::IsPathRooted($item)) { $item } else { Join-Path $repoRoot $item }
        Get-Item -LiteralPath $path
    }
} else {
    $texFiles = Get-ChildItem -LiteralPath $lecturesDir -Filter '*.tex' -File |
        Sort-Object Name
}

if (-not $texFiles -or $texFiles.Count -eq 0) {
    Write-Host "No .tex files found in $lecturesDir"
    exit 0
}

if ($List) {
    $texFiles | ForEach-Object { $_.FullName }
    exit 0
}

Push-Location $lecturesDir
try {
    if ($Clean) {
        foreach ($file in $texFiles) {
            Write-Host "Cleaning LaTeX byproducts for $($file.Name)"
            if ($useLatexmk) {
                & $latexmk.Source -c $file.Name
                if ($LASTEXITCODE -ne 0) {
                    throw "latexmk clean failed for $($file.Name)"
                }
            } else {
                $stem = [IO.Path]::GetFileNameWithoutExtension($file.Name)
                Get-ChildItem -LiteralPath $lecturesDir -File |
                    Where-Object { $_.BaseName -eq $stem -and $_.Extension -in @(
                        '.aux', '.bbl', '.bcf', '.blg', '.fdb_latexmk', '.fls',
                        '.lof', '.log', '.lot', '.nav', '.out', '.run.xml',
                        '.snm', '.synctex.gz', '.toc', '.vrb'
                    ) } |
                    Remove-Item -Force
            }
        }
        exit 0
    }

    foreach ($file in $texFiles) {
        Write-Host "Building $($file.Name)"
        if ($useLatexmk) {
            & $latexmk.Source -pdf -interaction=nonstopmode -halt-on-error -file-line-error $file.Name
            if ($LASTEXITCODE -ne 0) {
                throw "latexmk failed for $($file.Name)"
            }
        } else {
            for ($pass = 1; $pass -le 2; $pass++) {
                & $pdflatex.Source -interaction=nonstopmode -halt-on-error -file-line-error $file.Name
                if ($LASTEXITCODE -ne 0) {
                    throw "pdflatex failed for $($file.Name) on pass $pass"
                }
            }
        }
    }
} finally {
    Pop-Location
}

Write-Host "Lecture PDF build complete."
