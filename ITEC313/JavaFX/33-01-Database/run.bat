@echo off
REM JavaFX Database Application Runner Script for Windows
REM This script compiles and runs the JavaFX database application

echo JavaFX Database Application Runner
echo ==================================

REM Check if Java is installed
java -version >nul 2>&1
if errorlevel 1 (
    echo Error: Java is not installed or not in PATH
    pause
    exit /b 1
)

REM Check if Maven is installed
mvn -version >nul 2>&1
if errorlevel 1 (
    echo Error: Maven is not installed or not in PATH
    pause
    exit /b 1
)

echo Java version:
java -version

echo.
echo Maven version:
mvn -version

echo.
echo Building the project...
mvn clean compile

echo.
echo Running the application...
echo Note: The application will create a SQLite database file 'employee.sqlite' in the current directory
echo.

REM Run with Maven
mvn javafx:run

echo.
echo Application finished.
pause
