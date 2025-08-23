#!/bin/bash

# Test script for Spring Boot Web App with Thymeleaf and i18n
echo "Testing Spring Boot Web App..."
echo "================================"

# Test if application is running
echo "1. Testing application availability..."
if curl -s -o /dev/null -w "%{http_code}" http://localhost:8080 | grep -q "200"; then
    echo "✅ Application is running on http://localhost:8080"
else
    echo "❌ Application is not running"
    exit 1
fi

# Test CSS loading
echo "2. Testing CSS loading..."
if curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/css/style.css | grep -q "200"; then
    echo "✅ CSS file is accessible"
else
    echo "❌ CSS file is not accessible"
fi

# Test JavaScript loading
echo "3. Testing JavaScript loading..."
if curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/js/app.js | grep -q "200"; then
    echo "✅ JavaScript file is accessible"
else
    echo "❌ JavaScript file is not accessible"
fi

# Test layout template
echo "4. Testing layout template..."
if curl -s http://localhost:8080 | grep -q "style.css"; then
    echo "✅ Layout template is working (CSS link found)"
else
    echo "❌ Layout template is not working"
fi

# Test navigation
echo "5. Testing navigation..."
if curl -s http://localhost:8080 | grep -q "navbar"; then
    echo "✅ Navigation is present"
else
    echo "❌ Navigation is missing"
fi

# Test Bootstrap
echo "6. Testing Bootstrap integration..."
if curl -s http://localhost:8080 | grep -q "bootstrap"; then
    echo "✅ Bootstrap is loaded"
else
    echo "❌ Bootstrap is not loaded"
fi

# Test different pages
echo "7. Testing different pages..."
pages=("about" "contact" "languages")
for page in "${pages[@]}"; do
    if curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/$page | grep -q "200"; then
        echo "✅ /$page page is accessible"
    else
        echo "❌ /$page page is not accessible"
    fi
done

# Test API endpoint
echo "8. Testing API endpoint..."
if curl -s http://localhost:8080/api/time | grep -q "2025"; then
    echo "✅ API endpoint is working"
else
    echo "❌ API endpoint is not working"
fi

echo ""
echo "Test completed! 🎉"
echo "Open http://localhost:8080 in your browser to see the styled application."
