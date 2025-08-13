#!/bin/bash

# Test script for Spring MVC endpoints
BASE_URL="http://localhost:8089"

echo "Testing Spring MVC Endpoints"
echo "============================"

# Test home page
echo -e "\n1. Testing Home Page:"
echo "----------------------"
curl -X GET "$BASE_URL/"

# Test about page
echo -e "\n\n2. Testing About Page:"
echo "----------------------"
curl -X GET "$BASE_URL/about"

# Test contact form page
echo -e "\n\n3. Testing Contact Form Page:"
echo "--------------------------------"
curl -X GET "$BASE_URL/contact"

# Test contact form submission (valid data)
echo -e "\n\n4. Testing Contact Form Submission (Valid):"
echo "---------------------------------------------"
curl -X POST "$BASE_URL/contact" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "name=Test%20User&email=test@example.com&message=This%20is%20a%20test%20message%20with%20enough%20characters%20to%20pass%20validation"

# Test contact form submission (invalid data)
echo -e "\n\n5. Testing Contact Form Submission (Invalid):"
echo "-----------------------------------------------"
curl -X POST "$BASE_URL/contact" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "name=&email=invalid-email&message=short"

# Test file upload page
echo -e "\n\n6. Testing File Upload Page:"
echo "-----------------------------"
curl -X GET "$BASE_URL/upload"

# Test file upload (create a test file first)
echo -e "\n\n7. Testing File Upload:"
echo "-------------------------"
echo "Creating test file..."
echo "This is a test file content" > test_file.txt

curl -X POST "$BASE_URL/upload" \
  -F "file=@test_file.txt"

# Clean up test file
rm -f test_file.txt

# Test 404 error page
echo -e "\n\n8. Testing 404 Error Page:"
echo "----------------------------"
curl -X GET "$BASE_URL/nonexistent-page"

# Test actuator endpoints
echo -e "\n\n9. Testing Actuator Endpoints:"
echo "--------------------------------"
echo "Health check..."
curl -X GET "$BASE_URL/actuator/health"

echo -e "\n\nApplication info..."
curl -X GET "$BASE_URL/actuator/info"

# Test static resources
echo -e "\n\n10. Testing Static Resources:"
echo "-------------------------------"
echo "CSS file..."
curl -X GET "$BASE_URL/static/css/style.css" -I

echo -e "\n\nJavaScript file..."
curl -X GET "$BASE_URL/static/js/app.js" -I

echo -e "\n\nTesting completed!"
echo "===================="
echo "Application URLs:"
echo "- Home: $BASE_URL/"
echo "- About: $BASE_URL/about"
echo "- Contact: $BASE_URL/contact"
echo "- File Upload: $BASE_URL/upload"
echo "- 404 Test: $BASE_URL/nonexistent"
