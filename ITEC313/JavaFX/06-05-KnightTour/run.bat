@echo off
REM Knight's Tour Demo - Run Script for Windows
REM This script compiles and runs the Knight's Tour JavaFX application

echo === Knight's Tour Demo ===
echo Compiling and running the Knight's Tour application...
echo.

REM Check if Maven is available
mvn --version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Maven is not installed or not in PATH
    echo Please install Maven to run this application
    pause
    exit /b 1
)

REM Check if Java is available
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Java is not installed or not in PATH
    echo Please install Java 24 or later to run this application
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
echo Starting Knight's Tour application...
mvn javafx:run

echo.
echo Application finished.
pause
