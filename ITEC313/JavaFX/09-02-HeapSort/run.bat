@echo off
REM Heap Sort Visualization - Run Script for Windows
REM This script builds and runs the JavaFX application

echo === Heap Sort Visualization ===
echo Building and running the application...

REM Check if Java is installed
java -version >nul 2>&1
if errorlevel 1 (
    echo Error: Java is not installed or not in PATH
    pause
    exit /b 1
)

REM Check Java version
for /f "tokens=3" %%g in ('java -version 2^>^&1 ^| findstr /i "version"') do (
    set JAVA_VERSION=%%g
)
echo Java version: %JAVA_VERSION%

REM Check if Maven is installed
mvn -version >nul 2>&1
if errorlevel 1 (
    echo Error: Maven is not installed or not in PATH
    pause
    exit /b 1
)

for /f "tokens=3" %%g in ('mvn -version ^| findstr /i "Apache Maven"') do (
    echo Maven version: %%g
)

REM Clean and compile
echo Cleaning previous build...
call mvn clean

echo Compiling the application...
call mvn compile

REM Run the application
echo Starting the application...
call mvn javafx:run

echo Application finished.
pause 