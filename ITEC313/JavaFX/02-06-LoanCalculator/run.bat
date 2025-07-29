@echo off
REM JavaFX Loan Calculator - Windows Execution Script
REM This script builds and runs the JavaFX Loan Calculator application

echo === JavaFX Loan Calculator ===
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

REM Display Java version
echo Java version:
java -version

REM Display Maven version
echo Maven version:
mvn -version

REM Clean and compile
echo Cleaning and compiling...
mvn clean compile

REM Run the application
echo Starting the Loan Calculator application...
mvn javafx:run

echo Application finished.
pause 