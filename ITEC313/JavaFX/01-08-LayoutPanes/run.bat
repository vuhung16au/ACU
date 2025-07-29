@echo off
REM JavaFX Layout Panes Demo - Windows Execution Script
REM This script builds and runs the JavaFX application

echo JavaFX Layout Panes Demo - Build and Run Script
echo ================================================

REM Check if Maven is installed
where mvn >nul 2>nul
if %errorlevel% neq 0 (
    echo Error: Maven is not installed or not in PATH
    echo Please install Maven and try again
    pause
    exit /b 1
)

REM Check if Java is installed
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

echo.
echo Building and running the application...
echo.

REM Clean and compile
echo Cleaning previous build...
mvn clean

echo Compiling the application...
mvn compile

echo Running the application...
mvn javafx:run

echo.
echo Application finished.
pause 