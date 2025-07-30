@echo off
REM Prime Numbers Algorithms Demo - Windows Execution Script
REM This script builds and runs the JavaFX application

echo === Prime Numbers Algorithms Demo ===
echo Building and running the JavaFX application...
echo.

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

echo Java version:
java -version 2>&1 | findstr "version"
echo.
echo Maven version:
mvn -version 2>&1 | findstr "Apache Maven"
echo.

REM Clean and compile
echo Cleaning and compiling...
mvn clean compile

REM Run the application
echo Starting JavaFX application...
echo ==================================
mvn javafx:run

echo.
echo Application finished.
pause 