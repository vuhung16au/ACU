#!/bin/bash

# Spring Boot MongoDB Demo Script
# This script demonstrates how to use Spring Boot with MongoDB

echo "=========================================="
echo "Spring Boot MongoDB Demo"
echo "=========================================="

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Function to print colored output
print_status() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Check if Docker is running
print_status "Checking Docker status..."
if ! docker info > /dev/null 2>&1; then
    print_error "Docker is not running. Please start Docker and try again."
    exit 1
fi
print_success "Docker is running"

# Check if MongoDB container is running
print_status "Checking MongoDB container status..."
if ! docker ps | grep -q mongodb-demo; then
    print_warning "MongoDB container is not running. Starting it now..."
    cd docker
    docker-compose up -d
    cd ..
    sleep 5
fi
print_success "MongoDB container is running"

# Check if Maven is available
print_status "Checking Maven installation..."
if ! command -v mvn &> /dev/null; then
    print_error "Maven is not installed. Please install Maven and try again."
    exit 1
fi
print_success "Maven is available"

# Clean and compile the project
print_status "Cleaning and compiling the project..."
if mvn clean compile -q; then
    print_success "Project compiled successfully"
else
    print_error "Failed to compile the project"
    exit 1
fi

# Run tests
print_status "Running tests..."
if mvn test -q; then
    print_success "All tests passed"
else
    print_error "Some tests failed"
    exit 1
fi

# Show database content
print_status "Showing current database content..."
echo ""
echo "Connecting to MongoDB and showing customers:"
docker exec mongodb-demo mongosh customerdb --eval "
db.customers.find().forEach(function(customer) {
    print('Customer: ' + customer.firstName + ' ' + customer.lastName + 
          ' - ' + customer.city + ', ' + customer.country);
});
"

echo ""
print_status "Showing statistics:"
docker exec mongodb-demo mongosh customerdb --eval "
print('Total customers: ' + db.customers.count());
print('Customers in Sydney: ' + db.customers.find({city: 'Sydney'}).count());
print('Customers in Australia: ' + db.customers.find({country: 'Australia'}).count());
"

echo ""
print_status "Demo completed successfully!"
echo ""
echo "Next steps:"
echo "1. Run 'mvn spring-boot:run' to start the application"
echo "2. The application will be available at http://localhost:8080"
echo "3. Use the CustomerRepository to interact with the database"
echo ""
echo "Repository methods available:"
echo "- findAll() - Get all customers"
echo "- findByFirstName(String firstName) - Find by first name"
echo "- findByLastName(String lastName) - Find by last name"
echo "- findByCity(String city) - Find by city"
echo "- findByCountry(String country) - Find by country"
echo ""
echo "Sample queries you can try:"
echo "- Find all customers from Sydney"
echo "- Find all customers from Australia"
echo "- Find customer with first name 'Alice'"
echo ""
print_success "Demo script completed!"
