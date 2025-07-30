@echo off
REM LinkedList Demo - Windows Execution Script
REM This script builds and runs the JavaFX LinkedList Demo application

echo === LinkedList Demo - Build and Run Script ===
echo Platform: %OS% %PROCESSOR_ARCHITECTURE%
echo.

REM Check if Java is installed
java -version >nul 2>&1
if errorlevel 1 (
    echo ❌ Error: Java is not installed or not in PATH
    pause
    exit /b 1
)

REM Check if Maven is installed
mvn -version >nul 2>&1
if errorlevel 1 (
    echo ❌ Error: Maven is not installed or not in PATH
    pause
    exit /b 1
)

REM Clean and compile
echo 🔨 Building project...
call mvn clean compile

REM Run the application
echo 🚀 Running LinkedList Demo...
call mvn javafx:run

echo ✅ Application finished
pause
