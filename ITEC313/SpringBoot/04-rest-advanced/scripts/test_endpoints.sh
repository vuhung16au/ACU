#!/bin/bash

# Test script for Day 4: Advanced REST Services and External API Consumption
# This script demonstrates advanced REST API features

BASE_URL=${BASE_URL:-"http://localhost:8080"}
DELAY=${DELAY:-1}

echo "🚀 Testing Day 4: Advanced REST Services and External API Consumption"
echo "====================================================================="
echo "Base URL: $BASE_URL"
echo ""

# Function to test an endpoint
test_endpoint() {
    local method=$1
    local endpoint=$2
    local description=$3
    local data=$4
    local headers=$5
    
    echo "📋 Testing: $description"
    echo "   $method $BASE_URL$endpoint"
    
    if [ "$method" = "GET" ]; then
        response=$(curl -s -w "\n%{http_code}" "$BASE_URL$endpoint")
    elif [ "$method" = "POST" ]; then
        if [ -n "$headers" ]; then
            response=$(curl -s -w "\n%{http_code}" -X POST -H "$headers" "$BASE_URL$endpoint" -d "$data")
        else
            response=$(curl -s -w "\n%{http_code}" -X POST "$BASE_URL$endpoint" -d "$data")
        fi
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
    
    # Check for HATEOAS links
    if echo "$body" | grep -q "_links"; then
        echo "   ✅ HATEOAS: Links found"
    fi
    
    # Check for validation errors
    if echo "$body" | grep -q "Validation Error"; then
        echo "   ⚠️  Validation: Errors detected"
    fi
    
    echo ""
    
    sleep $DELAY
}

# Test Product API endpoints
echo "🛍️  Product Management API"
echo "-------------------------"

# Get all products
test_endpoint "GET" "/api/v1/products" "Get all products with HATEOAS"

# Get products with filtering
test_endpoint "GET" "/api/v1/products?category=ELECTRONICS" "Get products filtered by category"

# Get product by ID
test_endpoint "GET" "/api/v1/products/1" "Get product by ID with HATEOAS"

# Create a new product
product_data='{
  "name": "Test Product",
  "description": "A test product created via API",
  "price": 99.99,
  "category": "ELECTRONICS",
  "stockQuantity": 50
}'
test_endpoint "POST" "/api/v1/products" "Create a new product" "$product_data" "Content-Type: application/json"

# Create a product with invalid data (should fail validation)
invalid_product='{
  "name": "",
  "description": "Short",
  "price": -10,
  "category": "INVALID_CATEGORY"
}'
test_endpoint "POST" "/api/v1/products" "Create product with invalid data (should fail)" "$invalid_product" "Content-Type: application/json"

# Update a product
update_data='{
  "name": "Updated Test Product",
  "description": "An updated test product",
  "price": 149.99,
  "category": "ELECTRONICS",
  "stockQuantity": 75
}'
test_endpoint "PUT" "/api/v1/products/1" "Update an existing product" "$update_data"

# Update product stock
test_endpoint "PATCH" "/api/v1/products/1/stock?quantity=100" "Update product stock quantity"

# Get products by category
test_endpoint "GET" "/api/v1/products/category/ELECTRONICS" "Get products by category"

# Search products
test_endpoint "GET" "/api/v1/products/search?name=Test" "Search products by name"

# Get product statistics
test_endpoint "GET" "/api/v1/products/statistics" "Get product statistics"

# Get available categories
test_endpoint "GET" "/api/v1/products/categories" "Get available product categories"

# Delete a product
test_endpoint "DELETE" "/api/v1/products/1" "Delete a product"

# Test External API endpoints
echo "🌐 External API Integration"
echo "---------------------------"

# Get external posts
test_endpoint "GET" "/api/v1/external/posts" "Get all posts from external API"

# Get specific external post
test_endpoint "GET" "/api/v1/external/posts/1" "Get specific post from external API"

# Get external user
test_endpoint "GET" "/api/v1/external/users/1" "Get user from external API"

# Get post comments
test_endpoint "GET" "/api/v1/external/posts/1/comments" "Get comments for a post"

# Search external posts
test_endpoint "GET" "/api/v1/external/posts/search?title=qui" "Search posts by title"

# Create external post
external_post='{
  "title": "Test Post",
  "body": "This is a test post created via our API",
  "userId": 1
}'
test_endpoint "POST" "/api/v1/external/posts" "Create a post via external API" "$external_post" "Content-Type: application/json"

# Test Actuator endpoints
echo "📊 Actuator Endpoints"
echo "--------------------"

test_endpoint "GET" "/actuator/health" "Application health status"
test_endpoint "GET" "/actuator/info" "Application information"
test_endpoint "GET" "/actuator/metrics" "Application metrics"

# Test OpenAPI/Swagger endpoints
echo "📚 OpenAPI Documentation"
echo "------------------------"

test_endpoint "GET" "/api-docs" "OpenAPI specification"
test_endpoint "GET" "/swagger-ui.html" "Swagger UI interface"

echo "✅ Testing completed!"
echo ""
echo "💡 Key Features Demonstrated:"
echo "   - HATEOAS (Hypermedia) links in responses"
echo "   - Comprehensive validation with custom error messages"
echo "   - OpenAPI/Swagger documentation"
echo "   - External API consumption with WebClient"
echo "   - Advanced REST patterns (PATCH, filtering, search)"
echo "   - Global exception handling"
echo ""
echo "🔧 Next Steps:"
echo "   - Open http://localhost:8080/swagger-ui.html for interactive API documentation"
echo "   - Explore HATEOAS links in API responses"
echo "   - Test validation with various input combinations"
echo "   - Monitor application metrics via actuator endpoints"
echo ""
echo "📖 Learning Resources:"
echo "   - Spring HATEOAS: https://spring.io/projects/spring-hateoas"
echo "   - OpenAPI: https://springdoc.org/"
echo "   - WebClient: https://docs.spring.io/spring-framework/reference/web/webflux-webclient.html"
