@echo off
REM JavaFX PathTransition Demo - Windows Execution Script
REM This script builds and runs the JavaFX PathTransition demo application

echo ==========================================
echo JavaFX PathTransition Demo
echo ==========================================

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
    echo Please install Java 24 and try again
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
echo Running JavaFX PathTransition Demo...
mvn javafx:run

echo ==========================================
echo Application finished
echo ==========================================
pause 