@echo off
REM ArrayList Demo - Windows Execution Script
REM This script builds and runs the JavaFX ArrayList demonstration application

echo === ArrayList Implementation Demo ===
echo Building and running the JavaFX application...

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
    echo Please install Java 24 and try again
    pause
    exit /b 1
)

echo Java version:
java -version 2>&1 | findstr "version"
echo Maven version:
mvn -version 2>&1 | findstr "Apache Maven"

REM Clean and compile
echo Cleaning previous build...
call mvn clean

echo Compiling project...
call mvn compile

echo Running JavaFX application...
call mvn javafx:run

echo Application finished.
pause 