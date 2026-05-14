param(
    [Parameter(Position = 0)]
    [string] $Demo = "all"
)

$ErrorActionPreference = "Stop"

$projectRoot = $PSScriptRoot
Push-Location $projectRoot
try {
    Write-Host "Running Week 5 demo: $Demo"
    .\gradlew.bat run --console=plain --no-daemon --args="$Demo"
}
finally {
    Pop-Location
}
