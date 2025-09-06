@echo off

REM Recursive Zero Finder - Run Script for Windows
REM This script compiles and runs the JavaFX application

echo === Recursive Zero Finder - JavaFX Demo ===
echo Compiling and running the application...
echo.

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
    echo Please install Java 24 or later and try again
    pause
    exit /b 1
)

REM Clean and compile
echo Cleaning and compiling...
mvn clean compile

if %errorlevel% neq 0 (
    echo Error: Compilation failed
    pause
    exit /b 1
)

echo Compilation successful!
echo.

REM Run the application
echo Starting the JavaFX application...
mvn javafx:run

echo.
echo Application finished.
pause
