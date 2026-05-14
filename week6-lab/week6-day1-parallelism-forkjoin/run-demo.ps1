param(
    [Parameter(Position = 0)]
    [string] $Demo = "all"
)

$ErrorActionPreference = "Stop"

$projectRoot = $PSScriptRoot
Push-Location $projectRoot
try {
    Write-Host "Running Week 6 demo: $Demo"
    .\gradlew.bat run --console=plain --args="$Demo"
}
finally {
    Pop-Location
}
