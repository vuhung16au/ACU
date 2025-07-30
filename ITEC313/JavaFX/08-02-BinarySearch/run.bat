@echo off
REM JavaFX Binary Search Demo - Run Script
REM Windows batch script

echo JavaFX Binary Search Demo
echo ========================

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Java is not installed or not in PATH
    pause
    exit /b 1
)

REM Check if Maven is installed
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Maven is not installed or not in PATH
    pause
    exit /b 1
)

echo Java version:
java -version

echo Maven version:
mvn -version

echo.
echo Building and running the application...

REM Clean and compile
echo Cleaning previous build...
mvn clean

REM Compile and run
echo Compiling and running...
mvn javafx:run

echo.
echo Application finished.
pause 