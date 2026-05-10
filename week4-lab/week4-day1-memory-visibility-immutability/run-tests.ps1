$ErrorActionPreference = "Stop"

$proj = $PSScriptRoot
Push-Location $proj
try {
  Write-Host "`n=== WEEK 4 LAB: FULL TEST SUITE ===`n"
  .\gradlew.bat clean test --console=plain --no-daemon | Out-Host

  Write-Host "`n=== WEEK 4 LAB: STUDENT CHECK ===`n"
  .\gradlew.bat test --tests "*StudentWeek*" --console=plain --no-daemon | Out-Host

  $report = Join-Path $proj "build\reports\tests\test\index.html"
  if (Test-Path $report) {
    Write-Host "`nReport: $report`n"
  }

  Write-Host "`nWeek 4 tests finished.`n"
}
finally {
  Pop-Location
}
