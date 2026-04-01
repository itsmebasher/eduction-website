@echo off
REM Education Platform - Windows Launch Script
REM This script runs the Education Platform JavaFX application

REM Check if Java is installed
where java >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ❌ Error: Java is not installed.
    echo Please install Java 17 or higher from: https://www.oracle.com/java/technologies/downloads/
    echo.
    pause
    exit /b 1
)

REM Check Java version
for /f tokens^=2 %%i in ('java -version 2^>^&1 ^| find "version"') do (
    for /f delims^=_. %%j in ('echo %%i') do (
        set JAVA_VERSION=%%j
        goto CheckVersion
    )
)

:CheckVersion
if %JAVA_VERSION% LSS 17 (
    echo.
    echo ❌ Error: Java 17 or higher is required (found Java %JAVA_VERSION%)
    echo.
    pause
    exit /b 1
)

REM Get the directory where this batch file is located
set SCRIPT_DIR=%~dp0

REM Launch the application
echo.
echo 🚀 Starting Education Platform...
echo.
java -jar "%SCRIPT_DIR%education-platform-v1.0.0.jar"
pause
