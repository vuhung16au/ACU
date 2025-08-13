#!/bin/bash

# Test script for Day 5: Data JPA and Database Integration
# This script demonstrates JPA functionality and database operations

BASE_URL=${BASE_URL:-"http://localhost:8080"}
DELAY=${DELAY:-1}

echo "🚀 Testing Day 5: Data JPA and Database Integration"
echo "=================================================="
echo "Base URL: $BASE_URL"
echo ""

# Function to test an endpoint
test_endpoint() {
    local method=$1
    local endpoint=$2
    local description=$3
    local data=$4
    
    echo "📋 Testing: $description"
    echo "   $method $BASE_URL$endpoint"
    
    if [ "$method" = "GET" ]; then
        response=$(curl -s -w "\n%{http_code}" "$BASE_URL$endpoint")
    elif [ "$method" = "POST" ]; then
        response=$(curl -s -w "\n%{http_code}" -X POST -H "Content-Type: application/json" "$BASE_URL$endpoint" -d "$data")
    elif [ "$method" = "PUT" ]; then
        response=$(curl -s -w "\n%{http_code}" -X PUT -H "Content-Type: application/json" "$BASE_URL$endpoint" -d "$data")
    elif [ "$method" = "DELETE" ]; then
        response=$(curl -s -w "\n%{http_code}" -X DELETE "$BASE_URL$endpoint")
    elif [ "$method" = "PATCH" ]; then
        response=$(curl -s -w "\n%{http_code}" -X PATCH "$BASE_URL$endpoint")
    fi
    
    # Extract status code (last line)
    status_code=$(echo "$response" | tail -n1)
    # Extract response body (all lines except last)
    body=$(echo "$response" | head -n -1)
    
    echo "   Status: $status_code"
    
    # Check for pagination
    if echo "$body" | grep -q "pageable"; then
        echo "   ✅ Pagination: Found pageable response"
    fi
    
    # Check for JPA entities
    if echo "$body" | grep -q "id.*createdAt.*updatedAt"; then
        echo "   ✅ JPA: Found entity with auditing fields"
    fi
    
    echo ""
    
    sleep $DELAY
}

# Test Customer API endpoints
echo "👥 Customer Management API"
echo "-------------------------"

# Get all customers with pagination
test_endpoint "GET" "/api/customers?page=0&size=5" "Get customers with pagination"

# Get customer by ID
test_endpoint "GET" "/api/customers/1" "Get customer by ID"

# Get customer by email
test_endpoint "GET" "/api/customers/email/john.doe@example.com" "Get customer by email"

# Create a new customer
customer_data='{
  "firstName": "Test",
  "lastName": "Customer",
  "email": "test.customer@example.com",
  "phone": "+1234567899",
  "status": "ACTIVE"
}'
test_endpoint "POST" "/api/customers" "Create a new customer" "$customer_data"

# Update customer
update_data='{
  "firstName": "Updated",
  "lastName": "Customer",
  "email": "updated.customer@example.com",
  "phone": "+1234567899",
  "status": "ACTIVE"
}'
test_endpoint "PUT" "/api/customers/1" "Update customer" "$update_data"

# Get customers by status
test_endpoint "GET" "/api/customers/status/ACTIVE" "Get customers by status"

# Search customers by name
test_endpoint "GET" "/api/customers/search?firstName=John&lastName=Doe" "Search customers by name"

# Get customers by email domain
test_endpoint "GET" "/api/customers/domain/example.com" "Get customers by email domain"

# Update customer status
test_endpoint "PATCH" "/api/customers/1/status?status=INACTIVE" "Update customer status"

# Get customer statistics
test_endpoint "GET" "/api/customers/statistics" "Get customer statistics"

# Get average orders per customer
test_endpoint "GET" "/api/customers/statistics/average-orders" "Get average orders per customer"

# Test pagination with different parameters
echo "📄 Pagination Tests"
echo "------------------"

test_endpoint "GET" "/api/customers?page=0&size=3&sortBy=firstName&sortDir=asc" "Pagination with sorting"
test_endpoint "GET" "/api/customers?page=1&size=2&sortBy=createdAt&sortDir=desc" "Pagination with date sorting"

# Test Actuator endpoints
echo "📊 Actuator Endpoints"
echo "--------------------"

test_endpoint "GET" "/actuator/health" "Application health status"
test_endpoint "GET" "/actuator/info" "Application information"
test_endpoint "GET" "/actuator/metrics" "Application metrics"

# Test H2 Console (development only)
echo "🗄️  Database Console"
echo "-------------------"

test_endpoint "GET" "/h2-console" "H2 Database Console"

echo "✅ Testing completed!"
echo ""
echo "💡 Key Features Demonstrated:"
echo "   - JPA entity relationships (Customer -> Order -> OrderItem)"
echo "   - Repository query methods with custom queries"
echo "   - Pagination and sorting with Spring Data"
echo "   - Transaction management with @Transactional"
echo "   - Database migrations with Flyway"
echo "   - Auditing with @CreatedDate and @LastModifiedDate"
echo "   - Profile-based database configuration (H2 dev, MySQL prod)"
echo ""
echo "🔧 Next Steps:"
echo "   - Open http://localhost:8080/h2-console for database inspection"
echo "   - Explore JPA relationships in the database"
echo "   - Test different query methods in the repositories"
echo "   - Switch to production profile with MySQL"
echo ""
echo "📖 Learning Resources:"
echo "   - Spring Data JPA: https://spring.io/projects/spring-data-jpa"
echo "   - Hibernate: https://hibernate.org/"
echo "   - Flyway: https://flywaydb.org/"
echo "   - Spring Boot Data Access: https://docs.spring.io/spring-boot/docs/current/reference/html/data.html"
