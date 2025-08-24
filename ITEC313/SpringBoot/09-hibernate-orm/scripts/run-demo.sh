#!/bin/bash

# Hibernate ORM Demo - Run Application Script
# This script runs the Hibernate demonstration application

set -e

echo "🚀 Starting Hibernate ORM Demo Application..."
echo "============================================="

# Check if we're in the project root
if [ ! -f "pom.xml" ]; then
    echo "❌ Please run this script from the project root directory."
    exit 1
fi

# Check if Maven is available
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven is not installed or not in PATH."
    echo "Please install Maven and try again."
    exit 1
fi

echo "📦 Building and running the application..."
echo "⏳ This will start the Hibernate demonstrations..."
echo ""

# Run the application
mvn spring-boot:run

echo ""
echo "✅ Demo completed!"
echo "============================================="
