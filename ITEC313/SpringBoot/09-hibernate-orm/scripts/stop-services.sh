#!/bin/bash

# Hibernate ORM Demo - Service Stop Script
# This script helps you stop PostgreSQL and pgAdmin services

set -e

echo "🛑 Stopping Hibernate ORM Demo Services..."
echo "=========================================="

# Navigate to docker directory
cd "$(dirname "$0")/../docker"

echo "📦 Stopping all services..."
docker-compose down

echo ""
echo "🧹 Optional: Remove volumes (this will delete all data)?"
read -p "Do you want to remove volumes? (y/n): " -n 1 -r
echo

if [[ $REPLY =~ ^[Yy]$ ]]; then
    echo "🗑️  Removing volumes..."
    docker-compose down -v
    echo "✅ All data has been removed!"
else
    echo "ℹ️  Volumes preserved. Data will be available on next start."
fi

echo ""
echo "✅ Services stopped successfully!"
echo "=========================================="
