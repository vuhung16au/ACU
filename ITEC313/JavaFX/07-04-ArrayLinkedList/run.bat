@echo off
REM Array and LinkedList Demo - Run Script for Windows
REM Cross-platform JavaFX application runner

echo ================================
echo   Array and LinkedList Demo     
echo ================================

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

echo Maven version:
mvn -version

echo Building and running the application...

REM Clean and compile
echo Cleaning previous build...
mvn clean

REM Compile
echo Compiling...
mvn compile

REM Run with direct Java command
echo Running application...
java --module-path "%cd%\target\dependency" --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.base -cp target/classes com.acu.javafx.arraylinkedlist.ArrayLinkedListDemo

echo Application finished.
pause 