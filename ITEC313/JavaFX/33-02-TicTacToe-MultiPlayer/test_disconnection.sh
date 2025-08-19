#!/bin/bash

echo "=== Testing Disconnection Handling ==="
echo "This script will test the server's ability to handle client disconnections gracefully"
echo ""

# Start the server in the background
echo "Starting server..."
sh run-multiplayer.sh server &
SERVER_PID=$!

# Wait for server to start
sleep 3

echo "Server started with PID: $SERVER_PID"
echo ""

# Start first client
echo "Starting first client..."
sh run-multiplayer.sh client &
CLIENT1_PID=$!

# Wait for first client to connect
sleep 2

# Start second client
echo "Starting second client..."
sh run-multiplayer.sh client &
CLIENT2_PID=$!

# Wait for both clients to connect and game to start
sleep 3

echo "Both clients connected. Game should be running."
echo ""

# Simulate client 1 disconnection by killing it
echo "Simulating client 1 disconnection..."
kill $CLIENT1_PID

# Wait a moment to see server response
sleep 2

echo "Client 1 disconnected. Server should handle this gracefully."
echo ""

# Clean up
echo "Cleaning up..."
kill $CLIENT2_PID 2>/dev/null
kill $SERVER_PID 2>/dev/null

echo "Test completed. Check server logs for disconnection handling."
