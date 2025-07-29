@echo off
REM JavaFX File Class Demo - Windows Execution Script
REM This script builds and runs the JavaFX application

echo === JavaFX File Class Demo ===
echo Building and running the application...

REM Check if Maven is available
mvn -version >nul 2>&1
if errorlevel 1 (
    echo Error: Maven is not installed or not in PATH
    echo Please install Maven and try again
    pause
    exit /b 1
)

REM Check if Java is available
java -version >nul 2>&1
if errorlevel 1 (
    echo Error: Java is not installed or not in PATH
    echo Please install Java 24 and try again
    pause
    exit /b 1
)

REM Clean and compile
echo Cleaning and compiling...
call mvn clean compile

REM Run the application
echo Starting JavaFX application...
call mvn javafx:run

echo Application finished.
pause 