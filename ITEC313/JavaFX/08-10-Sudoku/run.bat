@echo off
REM Sudoku Solver - Run Script for Windows
REM This script compiles and runs the Sudoku Solver application

echo === Sudoku Solver - JavaFX Application ===
echo Building and running the Sudoku Solver...

REM Check if Maven is available
where mvn >nul 2>nul
if %errorlevel% neq 0 (
    echo Error: Maven is not installed or not in PATH
    echo Please install Maven and try again
    exit /b 1
)

REM Check if Java is available
where java >nul 2>nul
if %errorlevel% neq 0 (
    echo Error: Java is not installed or not in PATH
    echo Please install Java 24 or later and try again
    exit /b 1
)

REM Clean and compile
echo Cleaning and compiling...
mvn clean compile

if %errorlevel% neq 0 (
    echo Error: Compilation failed
    exit /b 1
)

REM Run tests
echo Running tests...
mvn test

if %errorlevel% neq 0 (
    echo Warning: Some tests failed, but continuing...
)

REM Run the application
echo Starting Sudoku Solver application...
mvn javafx:run

echo Sudoku Solver application finished.
pause
