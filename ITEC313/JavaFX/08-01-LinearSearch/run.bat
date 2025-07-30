@echo off
REM Linear Search JavaFX Demo - Windows Execution Script
REM This script builds and runs the JavaFX application

echo === Linear Search JavaFX Demo ===
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
)
echo Java version: %JAVA_VERSION%

REM Clean and compile
echo Cleaning previous build...
call mvn clean

echo Compiling and running...
call mvn javafx:run

echo Application finished.
pause 