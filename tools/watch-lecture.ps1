<#
.SYNOPSIS
Continuously rebuild one lecture PDF when its .tex source changes.

.DESCRIPTION
Use this while editing in TeXstudio. It runs latexmk in preview-continuous
mode, so saving the selected .tex file triggers a PDF rebuild.

Example:
  .\tools\watch-lecture.ps1 -Tex Lectures\Lecture_04_01_nar.tex
#>

[CmdletBinding()]
param(
    [Parameter(Mandatory = $true)]
    [string] $Tex
)

$ErrorActionPreference = 'Stop'

$repoRoot = Split-Path -Parent $PSScriptRoot
$path = if ([IO.Path]::IsPathRooted($Tex)) { $Tex } else { Join-Path $repoRoot $Tex }
$file = Get-Item -LiteralPath $path

$latexmk = Get-Command latexmk -ErrorAction SilentlyContinue
if (-not $latexmk) {
    throw "latexmk was not found on PATH. Install MiKTeX/TeX Live or add latexmk to PATH."
}

$perl = Get-Command perl -ErrorAction SilentlyContinue
if (-not $perl) {
    throw "latexmk watch mode requires Perl. Install Strawberry Perl or another Perl distribution, then reopen PowerShell."
}

Push-Location $file.DirectoryName
try {
    & $latexmk.Source -pdf -pvc -interaction=nonstopmode -halt-on-error -file-line-error $file.Name
    if ($LASTEXITCODE -ne 0) {
        throw "latexmk watch failed for $($file.Name)"
    }
} finally {
    Pop-Location
}
