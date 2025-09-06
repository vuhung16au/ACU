@echo off
REM Anagram Checker Demo - Run Script for Windows
REM This script compiles and runs the Anagram Checker Demo application

echo Anagram Checker Demo - Starting...
echo ==================================

REM Check if Maven is available
mvn --version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Maven is not installed or not in PATH
    echo Please install Maven and try again
    pause
    exit /b 1
)

REM Check if Java is available
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Java is not installed or not in PATH
    echo Please install Java and try again
    pause
    exit /b 1
)

echo Running tests...
mvn test

if %errorlevel% equ 0 (
    echo Tests passed! Starting application...
    echo ==================================
    mvn javafx:run
) else (
    echo Tests failed! Please fix the issues before running the application.
    pause
    exit /b 1
)

pause
