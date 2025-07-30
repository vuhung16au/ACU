@echo off
REM Map and Hash Set Demo - Run Script for Windows
REM This script builds and runs the JavaFX application

echo === Map and Hash Set Demo ===
echo Building and running JavaFX application...

REM Check if Maven is installed
where mvn >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Maven is not installed or not in PATH
    echo Please install Maven and try again
    pause
    exit /b 1
)

REM Check if Java is installed
where java >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Java is not installed or not in PATH
    echo Please install Java 24 or later and try again
    pause
    exit /b 1
)

REM Check Java version
for /f "tokens=3" %%g in ('java -version 2^>^&1 ^| findstr /i "version"') do (
    set JAVA_VERSION=%%g
    goto :version_check
)
:version_check

echo Java version: %JAVA_VERSION%
echo Maven version: 
mvn -version 2>&1 | findstr "Apache Maven"

REM Clean and compile
echo Cleaning and compiling...
call mvn clean compile

REM Run the application
echo Starting JavaFX application...
call mvn javafx:run

echo Application finished.
pause 