@echo off
REM Multiple Bounce Ball JavaFX Application Runner
REM Windows batch script

echo === Multiple Bounce Ball JavaFX Application ===
echo Platform: Windows
echo.

REM Check if Java is available
java -version >nul 2>&1
if errorlevel 1 (
    echo Error: Java is not installed or not in PATH
    pause
    exit /b 1
)

REM Check if Maven is available
mvn -version >nul 2>&1
if errorlevel 1 (
    echo Error: Maven is not installed or not in PATH
    pause
    exit /b 1
)

echo Building project...
mvn clean compile

echo Starting Multiple Bounce Ball application...
mvn javafx:run

echo Application finished.
pause 