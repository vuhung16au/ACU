@echo off
REM Multiplayer Tic-Tac-Toe Game Runner Script for Windows
REM This script helps you run the primary multiplayer tic-tac-toe game

echo === Multiplayer Tic-Tac-Toe Game ===
echo.

REM Check if Maven is available
mvn --version >nul 2>&1
if errorlevel 1 (
    echo Error: Maven is not installed or not in PATH
    echo Please install Maven and try again
    pause
    exit /b 1
)

REM Function to start server
:start_server
if "%1"=="server" (
    echo Starting Multiplayer TicTacToe Server...
    echo Server will listen on port 8000
    echo Press Ctrl+C to stop the server
    echo.
    mvn javafx:run
    goto :eof
)

REM Function to start client
:start_client
if "%1"=="client" (
    echo Starting Multiplayer TicTacToe Client...
    echo Connecting to server at localhost:8000
    echo.
    mvn exec:java -Dexec.mainClass="com.example.TicTacToeClient"
    goto :eof
)

REM Function to build the project
:build_project
if "%1"=="build" (
    echo Building the project...
    mvn clean compile
    if errorlevel 1 (
        echo Build failed!
        pause
        exit /b 1
    ) else (
        echo Build successful!
    )
    goto :eof
)

REM Show usage
:show_usage
echo Usage: %0 [server^|client^|build^|help]
echo.
echo Commands:
echo   server  - Start the game server
echo   client  - Start a game client
echo   build   - Build the project
echo   help    - Show this help message
echo.
echo Examples:
echo   %0 server    # Start the server
echo   %0 client    # Start a client
echo   %0 build     # Build the project
echo.
echo To play a game:
echo 1. Run '%0 server' in one command prompt
echo 2. Run '%0 client' in another command prompt
echo 3. Run '%0 client' in a third command prompt
echo.
pause
goto :eof

REM Main script logic
if "%1"=="" goto show_usage
if "%1"=="help" goto show_usage
if "%1"=="server" goto start_server
if "%1"=="client" goto start_client
if "%1"=="build" goto build_project

echo Unknown command: %1
goto show_usage
