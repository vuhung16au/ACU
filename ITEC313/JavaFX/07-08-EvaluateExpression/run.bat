@echo off
REM EvaluateExpression JavaFX Application Runner
REM This script runs the JavaFX application using Maven

echo Starting EvaluateExpression JavaFX Application...

REM Check if Maven is installed
mvn -version >nul 2>&1
if errorlevel 1 (
    echo Error: Maven is not installed or not in PATH
    echo Please install Maven and try again
    pause
    exit /b 1
)

REM Check if Java is installed
java -version >nul 2>&1
if errorlevel 1 (
    echo Error: Java is not installed or not in PATH
    echo Please install Java 24 or later and try again
    pause
    exit /b 1
)

REM Clean and compile
echo Building project...
call mvn clean compile

if errorlevel 1 (
    echo Error: Build failed
    pause
    exit /b 1
)

REM Run the application
echo Launching application...
call mvn javafx:run

echo Application finished.
pause 