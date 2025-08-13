#!/bin/bash

# Test script for Day 3: Web App with Thymeleaf and i18n
# This script demonstrates the web application features

BASE_URL=${BASE_URL:-"http://localhost:8080"}
DELAY=${DELAY:-1}

echo "🌐 Testing Day 3: Web App with Thymeleaf and i18n"
echo "=================================================="
echo "Base URL: $BASE_URL"
echo ""

# Function to test a web page
test_page() {
    local method=$1
    local endpoint=$2
    local description=$3
    local expected_content=$4
    
    echo "📄 Testing: $description"
    echo "   $method $BASE_URL$endpoint"
    
    if [ "$method" = "GET" ]; then
        response=$(curl -s -w "\n%{http_code}" "$BASE_URL$endpoint")
    elif [ "$method" = "POST" ]; then
        response=$(curl -s -w "\n%{http_code}" -X POST "$BASE_URL$endpoint")
    fi
    
    # Extract status code (last line)
    status_code=$(echo "$response" | tail -n1)
    # Extract response body (all lines except last)
    body=$(echo "$response" | head -n -1)
    
    echo "   Status: $status_code"
    
    if [ "$expected_content" != "" ]; then
        if echo "$body" | grep -q "$expected_content"; then
            echo "   ✅ Content verification: PASSED"
        else
            echo "   ❌ Content verification: FAILED (expected: $expected_content)"
        fi
    fi
    
    echo ""
    
    sleep $DELAY
}

# Function to test form submission
test_form() {
    local endpoint=$2
    local description=$3
    local form_data=$4
    
    echo "📝 Testing: $description"
    echo "   POST $BASE_URL$endpoint"
    echo "   Data: $form_data"
    
    response=$(curl -s -w "\n%{http_code}" -X POST "$BASE_URL$endpoint" \
        -H "Content-Type: application/x-www-form-urlencoded" \
        -d "$form_data")
    
    # Extract status code (last line)
    status_code=$(echo "$response" | tail -n1)
    # Extract response body (all lines except last)
    body=$(echo "$response" | head -n -1)
    
    echo "   Status: $status_code"
    
    if [ "$status_code" = "302" ] || [ "$status_code" = "303" ]; then
        echo "   ✅ Form submission: PASSED (redirect)"
    else
        echo "   ⚠️  Form submission: Status $status_code"
    fi
    
    echo ""
    
    sleep $DELAY
}

# Test web pages
echo "📄 Web Pages"
echo "-------------"

test_page "GET" "/" "Home page" "Welcome to Spring Boot Web App"
test_page "GET" "/about" "About page" "About"
test_page "GET" "/contact" "Contact form page" "Contact Us"
test_page "GET" "/languages" "Languages page" "Languages"

# Test API endpoints
echo "🔌 API Endpoints"
echo "----------------"

test_page "GET" "/api/time" "Get current time (default format)" ""
test_page "GET" "/api/time?format=short" "Get current time (short format)" ""

# Test form submissions
echo "📝 Form Submissions"
echo "-------------------"

# Valid form submission
test_form "POST" "/contact" "Valid contact form submission" \
    "name=John%20Doe&email=john@example.com&message=This%20is%20a%20test%20message%20with%20more%20than%2010%20characters&language=English"

# Invalid form submission (empty name)
test_form "POST" "/contact" "Invalid form submission (empty name)" \
    "name=&email=john@example.com&message=Test%20message&language=English"

# Test language switching
echo "🌍 Language Switching"
echo "--------------------"

echo "📄 Testing language switching to Spanish..."
curl -s "$BASE_URL/?lang=es_ES" | grep -q "Bienvenido" && echo "   ✅ Spanish: PASSED" || echo "   ❌ Spanish: FAILED"

echo "📄 Testing language switching to French..."
curl -s "$BASE_URL/?lang=fr_FR" | grep -q "Welcome" && echo "   ✅ French fallback: PASSED" || echo "   ⚠️  French: Not implemented"

echo "📄 Testing language switching to German..."
curl -s "$BASE_URL/?lang=de_DE" | grep -q "Welcome" && echo "   ✅ German fallback: PASSED" || echo "   ⚠️  German: Not implemented"

echo ""

# Test static resources
echo "📁 Static Resources"
echo "-------------------"

test_page "GET" "/css/style.css" "Custom CSS file" "Custom styles for Spring Boot Web App"
test_page "GET" "/js/app.js" "Custom JavaScript file" "Custom JavaScript for Spring Boot Web App"

# Test actuator endpoints
echo "📊 Actuator Endpoints"
echo "--------------------"

test_page "GET" "/actuator/health" "Application health status" ""
test_page "GET" "/actuator/info" "Application information" ""

echo "✅ Testing completed!"
echo ""
echo "💡 Tips:"
echo "   - Open http://localhost:8080 in your browser to see the web application"
echo "   - Try switching languages using the language dropdown"
echo "   - Test the contact form with valid and invalid data"
echo "   - Check the browser console for JavaScript functionality"
echo "   - Explore the responsive design on different screen sizes"
echo ""
echo "🔧 Development:"
echo "   - Thymeleaf templates are in src/main/resources/templates/"
echo "   - Static resources are in src/main/resources/static/"
echo "   - Message files for i18n are in src/main/resources/"
echo "   - Run 'mvn spring-boot:run' to start the application"
