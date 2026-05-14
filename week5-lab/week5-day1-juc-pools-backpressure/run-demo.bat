@echo off
setlocal
set DEMO=%~1
if "%DEMO%"=="" set DEMO=all
pushd "%~dp0"
call gradlew.bat run --console=plain --no-daemon --args="%DEMO%"
set EXIT_CODE=%ERRORLEVEL%
popd
exit /b %EXIT_CODE%
