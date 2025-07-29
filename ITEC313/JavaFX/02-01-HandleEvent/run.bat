@echo off
REM JavaFX HandleEvent Demo - Windows Execution Script
REM This script builds and runs the JavaFX application

echo === JavaFX HandleEvent Demo ===
echo Building and running the application...

REM Check if Maven is available
where mvn >nul 2>nul
if %errorlevel% neq 0 (
    echo Error: Maven is not installed or not in PATH
    echo Please install Maven and try again
    pause
    exit /b 1
)

REM Check if Java is available
where java >nul 2>nul
if %errorlevel% neq 0 (
    echo Error: Java is not installed or not in PATH
    echo Please install Java 24 and try again
    pause
    exit /b 1
)

REM Display Java version
echo Java version:
java -version

REM Display Maven version
echo Maven version:
mvn -version

REM Clean and compile
echo Cleaning and compiling...
mvn clean compile

REM Run the application
echo Running the application...
mvn javafx:run

echo Application finished.
pause 