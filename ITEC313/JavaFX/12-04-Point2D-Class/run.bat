@echo off

REM Point2D Class Demo - Run Script for Windows
REM This script compiles and runs the Point2D JavaFX demonstration

echo === Point2D Class Demo - ACU JavaFX Course ===
echo Compiling and running the Point2D demonstration...
echo.

REM Check if Maven is available
where mvn >nul 2>nul
if %errorlevel% neq 0 (
    echo Error: Maven is not installed or not in PATH
    echo Please install Maven and try again
    pause
    exit /b 1
)

REM Clean and compile the project
echo Cleaning and compiling project...
mvn clean compile

if %errorlevel% neq 0 (
    echo Error: Compilation failed
    pause
    exit /b 1
)

echo Compilation successful!
echo.

REM Ask user which demo to run
echo Which demo would you like to run?
echo 1) JavaFX GUI Demo (Point2DDemo)
echo 2) CLI Demo (Point2DCLIDemo)
echo 3) Run Tests
echo 4) Exit
echo.

set /p choice="Enter your choice (1-4): "

if "%choice%"=="1" (
    echo Starting JavaFX GUI Demo...
    mvn javafx:run
) else if "%choice%"=="2" (
    echo Starting CLI Demo...
    mvn exec:java
) else if "%choice%"=="3" (
    echo Running tests...
    mvn test
) else if "%choice%"=="4" (
    echo Exiting...
    exit /b 0
) else (
    echo Invalid choice. Exiting...
    exit /b 1
)

pause
