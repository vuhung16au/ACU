#!/bin/bash

echo "Starting Student Server..."
echo "Server will listen on port 8000"
echo "Press Ctrl+C to stop the server"
echo ""

# Compile and run the server
mvn compile exec:java -Dexec.mainClass="com.example.StudentServer"
