#!/bin/bash

# Script to build all Android projects in this folder.

echo "🏗️ Android Projects Build Script"
echo "=========================================="

# Color codes for output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Counter for built projects
built_count=0

# Resolve paths relative to this script so it works from any starting directory.
script_dir=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)

# Prefer Java 17 for Gradle because some Android projects fail under newer JDKs.
java_17_home=$(/usr/libexec/java_home -v 17 2>/dev/null || true)
if [ -n "$java_17_home" ]; then
    export JAVA_HOME="$java_17_home"
    export PATH="$JAVA_HOME/bin:$PATH"
    echo "Using JAVA_HOME: $JAVA_HOME"
fi

# Find all directories containing gradlew files (Gradle project roots)
while read -r gradle_file; do
    project_dir=$(dirname "$gradle_file")
    project_name=$(basename "$project_dir")

    echo ""
    echo -e "${YELLOW}Building: $project_name${NC}"
    echo "Location: $project_dir"

    cd "$project_dir" || exit 1

    if [ ! -x "./gradlew" ]; then
        echo -e "${RED}✗ Error: gradlew not executable${NC}"
        exit 1
    fi

    if ./gradlew build; then
        echo -e "${GREEN}✓ Successfully built${NC}"
        built_count=$((built_count + 1))
    else
        echo -e "${RED}✗ Build failed${NC}"
        exit 1
    fi
done < <(find "$script_dir" -name "gradlew" -type f | sort)

echo ""
echo "=========================================="
echo -e "${GREEN}Build complete!${NC}"
echo "Projects built: $built_count"
