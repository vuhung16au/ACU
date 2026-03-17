#!/bin/bash

# Script to clean up all Gradle build directories in Android projects
# This will significantly reduce disk space usage

echo "🧹 Android Projects Build Cleanup Script"
echo "=========================================="

# Color codes for output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Counter for cleaned projects
cleaned_count=0

# Resolve paths relative to this script so it works from any starting directory.
script_dir=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)

# Prefer Java 17 for Gradle because some Android projects fail under newer JDKs.
java_17_home=$(/usr/libexec/java_home -v 17 2>/dev/null || true)
if [ -n "$java_17_home" ]; then
    export JAVA_HOME="$java_17_home"
    export PATH="$JAVA_HOME/bin:$PATH"
    echo "Using JAVA_HOME: $JAVA_HOME"
fi

# Define the projects to clean
projects=(
    "01.HelloWorldKotlin"
    "02.ComposeBasics"
    "03.MaterialDesign3"
    "04.ViewModelState"
    "05.NavigationBasics"
    "06.ComprehensiveApp"
)

# Clean each specified project
for project_name in "${projects[@]}"; do
    echo ""
    echo -e "${YELLOW}Cleaning: $project_name${NC}"

    project_path="$script_dir/$project_name"
    
    if [ ! -d "$project_path" ]; then
        echo "⚠ Warning: Directory $project_name not found, skipping"
        continue
    fi
    
    echo "Location: $project_path"
    
    # Change to project directory and run gradlew clean
    cd "$project_path" || continue
    
    if [ -x "./gradlew" ]; then
        ./gradlew clean --quiet
        if [ $? -eq 0 ]; then
            echo -e "${GREEN}✓ Successfully cleaned${NC}"
            cleaned_count=$((cleaned_count + 1))
        else
            echo "⚠ Warning: Clean command completed with errors"
        fi
    else
        echo "⚠ Warning: gradlew not executable, skipping"
    fi
    
done

echo ""
echo "=========================================="
echo -e "${GREEN}Cleanup complete!${NC}"
echo "Projects cleaned: $cleaned_count"
echo ""
echo "💡 Tip: You can also run './gradlew clean' manually in any project directory"
