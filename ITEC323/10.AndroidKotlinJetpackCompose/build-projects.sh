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

# Define the projects to build
projects=(
    "01.HelloWorldKotlin"
    "02.ComposeBasics"
    "03.MaterialDesign3"
    "04.ViewModelState"
    "05.NavigationBasics"
    "06.ComprehensiveApp"
)

# Build each specified project
for project_name in "${projects[@]}"; do
    echo ""
    echo -e "${YELLOW}Building: $project_name${NC}"

    project_path="$script_dir/$project_name"

    if [ ! -d "$project_path" ]; then
        echo -e "${RED}✗ Error: Directory $project_name not found${NC}"
        exit 1
    fi

    echo "Location: $project_path"

    cd "$project_path" || exit 1

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
done

echo ""
echo "=========================================="
echo -e "${GREEN}Build complete!${NC}"
echo "Projects built: $built_count"
