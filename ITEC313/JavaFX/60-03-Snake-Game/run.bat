@echo off
REM Snake Game - Run Script for Windows
REM This script compiles and runs the Snake game using Maven

echo 🐍 Starting Snake Game...
echo ================================

REM Check if Maven is installed
mvn --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Maven is not installed. Please install Maven first.
    echo    Visit: https://maven.apache.org/install.html
    pause
    exit /b 1
)

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Java is not installed. Please install Java 24 or later.
    echo    Visit: https://openjdk.java.net/
    pause
    exit /b 1
)

echo ✅ Maven and Java found
echo 🔨 Compiling project...

REM Clean and compile
mvn clean compile

if %errorlevel% neq 0 (
    echo ❌ Compilation failed. Please check the errors above.
    pause
    exit /b 1
)

echo ✅ Compilation successful
echo 🚀 Starting Snake Game...

REM Run the game
mvn javafx:run

echo 👋 Thanks for playing Snake Game!
pause
