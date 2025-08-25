#!/bin/bash

# Script to build and compare Docker images with different optimization approaches

echo "🚀 Building Docker images with jlink optimization..."

# Build the original optimized image (for comparison)
echo "📦 Building original optimized image..."
docker build -f Dockerfile-03-optimized -t spring-boot-original . 2>/dev/null

# Build manual jlink optimized image
echo "📦 Building manual jlink optimized image..."
docker build -f Dockerfile-jlink-optimized -t spring-boot-jlink-manual . 2>/dev/null

# Build auto jlink optimized image
echo "📦 Building auto jlink optimized image..."
docker build -f Dockerfile-jlink-auto-optimized -t spring-boot-jlink-auto . 2>/dev/null

echo ""
echo "📊 Image Size Comparison:"
echo "=========================="

# Display image sizes
docker images --format "table {{.Repository}}\t{{.Tag}}\t{{.Size}}" | grep spring-boot

echo ""
echo "🔍 Detailed size analysis:"
echo "=========================="

# Get detailed size information
for image in spring-boot-original spring-boot-jlink-manual spring-boot-jlink-auto; do
    if docker images $image >/dev/null 2>&1; then
        size=$(docker images --format "{{.Size}}" $image)
        echo "$image: $size"
    fi
done

echo ""
echo "🧪 Testing images..."
echo "==================="

# Test each image
for image in spring-boot-original spring-boot-jlink-manual spring-boot-jlink-auto; do
    if docker images $image >/dev/null 2>&1; then
        echo "Testing $image..."
        container_id=$(docker run -d -p 0:8080 $image 2>/dev/null)
        if [ $? -eq 0 ]; then
            port=$(docker port $container_id 8080 | cut -d: -f2)
            sleep 5
            response=$(curl -s http://localhost:$port/ 2>/dev/null || echo "Connection failed")
            if [[ $response == *"Hello Docker World"* ]]; then
                echo "✅ $image: Working correctly"
            else
                echo "❌ $image: Not responding correctly"
            fi
            docker stop $container_id >/dev/null 2>&1
            docker rm $container_id >/dev/null 2>&1
        else
            echo "❌ $image: Failed to start"
        fi
    fi
done

echo ""
echo "🎯 Summary:"
echo "==========="
echo "• Original optimized: Uses full JRE (~150MB base)"
echo "• Manual jlink: Custom JRE with predefined modules (~50-80MB)"
echo "• Auto jlink: Custom JRE with automatically detected modules (~30-60MB)"
echo ""
echo "💡 For production, start with the manual approach for stability."
echo "💡 For maximum optimization, use the auto approach after thorough testing."
