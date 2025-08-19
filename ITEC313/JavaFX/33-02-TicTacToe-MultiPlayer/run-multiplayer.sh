#!/bin/bash

# Multiplayer Tic-Tac-Toe Game Runner Script
# This script helps you run the primary multiplayer tic-tac-toe game

echo "=== Multiplayer Tic-Tac-Toe Game ==="
echo ""

# Check if Maven is available
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven is not installed or not in PATH"
    echo "Please install Maven and try again"
    exit 1
fi

# Function to start server
    start_server() {
        echo "Starting Multiplayer TicTacToe Server..."
        echo "Server will listen on port 8000"
        echo "Press Ctrl+C to stop the server"
        echo ""
        mvn javafx:run
    }

# Function to start client
    start_client() {
        echo "Starting Multiplayer TicTacToe Client..."
        echo "Connecting to server at localhost:8000"
        echo ""
        mvn exec:java -Dexec.mainClass="com.example.TicTacToeClient"
    }

# Function to build the project
build_project() {
    echo "Building the project..."
    mvn clean compile
    if [ $? -eq 0 ]; then
        echo "Build successful!"
    else
        echo "Build failed!"
        exit 1
    fi
}

# Function to show usage
show_usage() {
    echo "Usage: $0 [server|client|build|help]"
    echo ""
    echo "Commands:"
    echo "  server  - Start the game server"
    echo "  client  - Start a game client"
    echo "  build   - Build the project"
    echo "  help    - Show this help message"
    echo ""
    echo "Examples:"
    echo "  $0 server    # Start the server"
    echo "  $0 client    # Start a client"
    echo "  $0 build     # Build the project"
    echo ""
    echo "To play a game:"
    echo "1. Run '$0 server' in one terminal"
    echo "2. Run '$0 client' in another terminal"
    echo "3. Run '$0 client' in a third terminal"
}

# Main script logic
case "${1:-help}" in
    "server")
        build_project
        start_server
        ;;
    "client")
        build_project
        start_client
        ;;
    "build")
        build_project
        ;;
    "help"|*)
        show_usage
        ;;
esac
