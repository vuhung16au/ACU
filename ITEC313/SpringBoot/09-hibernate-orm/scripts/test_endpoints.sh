#!/bin/bash

# Test script for Hibernate ORM endpoints
BASE_URL="http://localhost:8090"

echo "Testing Hibernate ORM Endpoints"
echo "==============================="

# Test Authors endpoints
echo -e "\n1. Testing Authors Endpoints:"
echo "-------------------------------"

# Get all authors with pagination
echo "Getting all authors with pagination..."
curl -X GET "$BASE_URL/api/authors?page=0&size=5"

# Get author by ID
echo -e "\n\nGetting author by ID..."
curl -X GET "$BASE_URL/api/authors/1"

# Get author with books (eager loading)
echo -e "\n\nGetting author with books (eager loading)..."
curl -X GET "$BASE_URL/api/authors/1/with-books"

# Search authors by keyword
echo -e "\n\nSearching authors by keyword..."
curl -X GET "$BASE_URL/api/authors/search?keyword=Rowling"

# Search authors by name with pagination
echo -e "\n\nSearching authors by name with pagination..."
curl -X GET "$BASE_URL/api/authors/search/name?name=King&page=0&size=5"

# Get authors with books
echo -e "\n\nGetting authors with books..."
curl -X GET "$BASE_URL/api/authors/with-books"

# Get authors with books (eager loading)
echo -e "\n\nGetting authors with books (eager loading)..."
curl -X GET "$BASE_URL/api/authors/with-books/eager"

# Get authors with at least N books
echo -e "\n\nGetting authors with at least 2 books..."
curl -X GET "$BASE_URL/api/authors/min-books/2"

# Get authors born after a date
echo -e "\n\nGetting authors born after 1950..."
curl -X GET "$BASE_URL/api/authors/born-after?date=1950-01-01"

# Get statistics
echo -e "\n\nGetting statistics..."
curl -X GET "$BASE_URL/api/authors/stats/more-than-books?minBooks=1"

# Get author's books
echo -e "\n\nGetting author's books..."
curl -X GET "$BASE_URL/api/authors/1/books"

# Create a new author
echo -e "\n\nCreating a new author..."
curl -X POST "$BASE_URL/api/authors" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test Author",
    "email": "test.author@example.com",
    "biography": "A test author for demonstration",
    "birthDate": "1980-01-01"
  }'

# Test Books endpoints
echo -e "\n\n2. Testing Books Endpoints:"
echo "-----------------------------"

# Get all books with pagination
echo "Getting all books with pagination..."
curl -X GET "$BASE_URL/api/books?page=0&size=5"

# Get book by ID
echo -e "\n\nGetting book by ID..."
curl -X GET "$BASE_URL/api/books/1"

# Search books by title
echo -e "\n\nSearching books by title..."
curl -X GET "$BASE_URL/api/books/search?title=Harry"

# Get books by author
echo -e "\n\nGetting books by author..."
curl -X GET "$BASE_URL/api/books/author/1"

# Test Tags endpoints
echo -e "\n\n3. Testing Tags Endpoints:"
echo "----------------------------"

# Get all tags
echo "Getting all tags..."
curl -X GET "$BASE_URL/api/tags"

# Get tag by ID
echo -e "\n\nGetting tag by ID..."
curl -X GET "$BASE_URL/api/tags/1"

# Get books with specific tag
echo -e "\n\nGetting books with Fantasy tag..."
curl -X GET "$BASE_URL/api/tags/1/books"

# Test Actuator endpoints
echo -e "\n\n4. Testing Actuator Endpoints:"
echo "--------------------------------"

echo "Health check..."
curl -X GET "$BASE_URL/actuator/health"

echo -e "\n\nApplication info..."
curl -X GET "$BASE_URL/actuator/info"

echo -e "\n\nMetrics..."
curl -X GET "$BASE_URL/actuator/metrics"

echo -e "\n\nTesting completed!"
echo "===================="
echo "Application URLs:"
echo "- REST API: $BASE_URL"
echo "- Actuator: $BASE_URL/actuator"
