@echo off
REM Bin Packing Demo - Run Script for Windows
REM This script compiles and runs the Bin Packing Problem Solver

echo === Bin Packing Problem Solver ===
echo Compiling and running the application...
echo.

REM Change to the project directory
cd /d "%~dp0"

REM Clean and compile
echo Cleaning and compiling...
mvn clean compile

if %errorlevel% equ 0 (
    echo Compilation successful!
    echo.
    echo Running tests...
    mvn test
    
    if %errorlevel% equ 0 (
        echo All tests passed!
        echo.
        echo Starting JavaFX application...
        mvn javafx:run
    ) else (
        echo Tests failed. Please check the output above.
        exit /b 1
    )
) else (
    echo Compilation failed. Please check the errors above.
    exit /b 1
)
