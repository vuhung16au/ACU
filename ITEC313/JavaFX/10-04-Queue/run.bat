@echo off
REM JavaFX Queue Demo - Windows Execution Script
REM This script builds and runs the JavaFX application

echo === JavaFX Queue Demo ===
echo Building and running the application...

REM Check if Maven is installed
where mvn >nul 2>nul
if %errorlevel% neq 0 (
    echo Error: Maven is not installed or not in PATH
    echo Please install Maven and try again
    pause
    exit /b 1
)

REM Check if Java is installed
where java >nul 2>nul
if %errorlevel% neq 0 (
    echo Error: Java is not installed or not in PATH
    echo Please install Java 24 or later and try again
    pause
    exit /b 1
)

REM Check Java version
for /f "tokens=3" %%g in ('java -version 2^>^&1 ^| findstr /i "version"') do (
    set JAVA_VERSION=%%g
    goto :check_version
)
:check_version
echo Found Java version: %JAVA_VERSION%

REM Clean and compile
echo Cleaning and compiling...
call mvn clean compile
if %errorlevel% neq 0 (
    echo Build failed!
    pause
    exit /b 1
)

REM Run the application
echo Starting JavaFX application...
call mvn javafx:run
if %errorlevel% neq 0 (
    echo Application failed to start!
    pause
    exit /b 1
)

echo Application finished.
pause 