@echo off
setlocal enabledelayedexpansion

REM JavaFX Button Demo - Run Script for Windows
REM This script compiles and runs the JavaFX Button application

echo === JavaFX Button Demo ===
echo Platform: Windows
echo.

REM Check if Maven is installed
where mvn >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Error: Maven is not installed or not in PATH
    echo Please install Maven and try again.
    pause
    exit /b 1
)

REM Check if Java is installed
where java >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Error: Java is not installed or not in PATH
    echo Please install Java 11 or later and try again.
    pause
    exit /b 1
)

REM Display versions
echo Java Version:
java -version 2>&1 | findstr "version"
echo.
echo Maven Version:
mvn -version 2>&1 | findstr "Apache Maven"
echo.

REM Clean and compile the project
echo 🔧 Cleaning and compiling project...
mvn clean compile

if %errorlevel% neq 0 (
    echo ❌ Error: Compilation failed
    pause
    exit /b 1
)

echo ✅ Compilation successful
echo.

REM Run the JavaFX application
echo 🚀 Starting JavaFX Button Demo application...
echo Note: A window should open with the JavaFX Button application
echo.

REM Use JavaFX Maven plugin to run the application
mvn javafx:run

if %errorlevel% neq 0 (
    echo ❌ Error: Application failed to start
    pause
    exit /b 1
)

echo.
echo ✅ Application completed successfully
echo 👋 Thanks for using the JavaFX Button Demo!
pause 