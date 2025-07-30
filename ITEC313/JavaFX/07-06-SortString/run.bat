@echo off
REM JavaFX String Sorting Demo - Windows Execution Script
REM This script builds and runs the JavaFX application

echo JavaFX String Sorting Demo
echo ==========================

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
echo.

REM Display Maven version
echo Maven version:
mvn -version
echo.

REM Clean and compile
echo Building project...
mvn clean compile

REM Run the application
echo Running JavaFX application...
mvn javafx:run

echo Application finished.
pause 