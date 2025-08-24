#!/bin/bash

# Hibernate ORM Demo - Service Startup Script
# This script helps you start PostgreSQL and pgAdmin services

set -e

echo "🚀 Starting Hibernate ORM Demo Services..."
echo "=========================================="

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker is not running. Please start Docker first."
    exit 1
fi

# Navigate to docker directory
cd "$(dirname "$0")/../docker"

echo "📦 Starting PostgreSQL database..."
docker-compose up -d postgres

echo "⏳ Waiting for PostgreSQL to be ready..."
sleep 5

# Check if PostgreSQL is healthy
if docker-compose ps postgres | grep -q "healthy"; then
    echo "✅ PostgreSQL is running and healthy!"
else
    echo "⚠️  PostgreSQL is starting, please wait a moment..."
    sleep 10
fi

echo ""
echo "🌐 Starting pgAdmin..."
docker-compose --profile tools up -d pgadmin

echo "⏳ Waiting for pgAdmin to start..."
sleep 5

echo "✅ pgAdmin is running!"
echo ""
echo "📋 pgAdmin Access Information:"
echo "   URL: http://localhost:8080"
echo "   Email: 313@acu.edu.au"
echo "   Password: password"
echo ""
echo "🔗 Database Connection Details:"
echo "   Host: postgres"
echo "   Port: 5432"
echo "   Database: hibernate_demo"
echo "   Username: postgres"
echo "   Password: password"

echo ""
echo "🎯 Next Steps:"
echo "1. Run the Hibernate demo: mvn spring-boot:run"
echo "2. Watch the console output for demonstrations"
echo "3. Use pgAdmin to inspect the database (if started)"
echo ""
echo "🛑 To stop services: cd docker && docker-compose down"
echo "=========================================="
echo "✅ Services started successfully!"
