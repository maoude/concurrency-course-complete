@echo off
setlocal
pushd "%~dp0"
call gradlew.bat run --console=plain --no-daemon --args="all"
set EXIT_CODE=%ERRORLEVEL%
popd
exit /b %EXIT_CODE%
