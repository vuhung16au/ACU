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

# Find all directories containing gradlew files (Gradle project roots)
while read -r gradle_file; do
    project_dir=$(dirname "$gradle_file")
    project_name=$(basename "$project_dir")
    
    echo ""
    echo -e "${YELLOW}Cleaning: $project_name${NC}"
    echo "Location: $project_dir"
    
    # Change to project directory and run gradlew clean
    cd "$project_dir" || continue
    
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

done < <(find "$script_dir" -name "gradlew" -type f)

echo ""
echo "=========================================="
echo -e "${GREEN}Cleanup complete!${NC}"
echo "Projects cleaned: $cleaned_count"
echo ""
echo "💡 Tip: You can also run './gradlew clean' manually in any project directory"
