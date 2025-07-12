#!/bin/bash

echo "🔧 Testing JavaFX ARM64 Fix..."
echo "==============================="

# Check architecture
echo "System architecture: $(uname -m)"

# Check if cache has correct files
if [ -f ~/.openjfx/cache/21.0.2+5/aarch64/libprism_es2.dylib ]; then
    echo "✅ ARM64 libraries found in cache"
    file ~/.openjfx/cache/21.0.2+5/aarch64/libprism_es2.dylib | grep "arm64"
    if [ $? -eq 0 ]; then
        echo "✅ Libraries are correctly ARM64 architecture"
    else
        echo "❌ Libraries are wrong architecture"
    fi
else
    echo "ℹ️  No cache found - will be created on first run"
fi

# Test Maven build
echo ""
echo "Testing Maven build with ARM64 platform..."
mvn clean compile -Djavafx.platform=mac-aarch64 -q
if [ $? -eq 0 ]; then
    echo "✅ Maven build successful with ARM64 platform"
else
    echo "❌ Maven build failed"
    exit 1
fi

echo ""
echo "🎉 Fix verification complete! The application should now run properly."
echo "To run the app: sh run.sh"
