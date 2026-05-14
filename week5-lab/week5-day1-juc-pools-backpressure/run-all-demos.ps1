$ErrorActionPreference = "Stop"

$projectRoot = $PSScriptRoot
Push-Location $projectRoot
try {
    Write-Host "Running all Week 5 demos"
    .\gradlew.bat run --console=plain --no-daemon --args="all"
}
finally {
    Pop-Location
}
