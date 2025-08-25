#!/bin/bash

# Script to build all Dockerfiles and compare their image sizes
# Author: Docker Optimization Analysis
# Date: $(date)

set -e  # Exit on any error

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
PURPLE='\033[0;35m'
CYAN='\033[0;36m'
NC='\033[0m' # No Color

# Function to print colored output
print_header() {
    echo -e "${BLUE}========================================${NC}"
    echo -e "${BLUE}$1${NC}"
    echo -e "${BLUE}========================================${NC}"
}

print_success() {
    echo -e "${GREEN}✅ $1${NC}"
}

print_warning() {
    echo -e "${YELLOW}⚠️  $1${NC}"
}

print_error() {
    echo -e "${RED}❌ $1${NC}"
}

print_info() {
    echo -e "${CYAN}ℹ️  $1${NC}"
}

# Function to format bytes to human readable format
format_bytes() {
    local bytes=$1
    if [ $bytes -gt 1073741824 ]; then
        echo "$(echo "scale=2; $bytes/1073741824" | bc) GB"
    elif [ $bytes -gt 1048576 ]; then
        echo "$(echo "scale=2; $bytes/1048576" | bc) MB"
    elif [ $bytes -gt 1024 ]; then
        echo "$(echo "scale=2; $bytes/1024" | bc) KB"
    else
        echo "${bytes} B"
    fi
}

# Function to get image size in bytes
get_image_size() {
    local image_name=$1
    docker images --format "table {{.Repository}}:{{.Tag}}\t{{.Size}}" | grep "^${image_name}:" | awk '{print $2}' | head -1
}

# Function to get image size in bytes (raw)
get_image_size_bytes() {
    local image_name=$1
    local size_str=$(get_image_size "$image_name")
    if [[ $size_str =~ ([0-9.]+)([KMGT]?B) ]]; then
        local size=${BASH_REMATCH[1]}
        local unit=${BASH_REMATCH[2]}
        case $unit in
            "B") echo "$size" ;;
            "KB") echo "$(echo "$size * 1024" | bc | cut -d. -f1)" ;;
            "MB") echo "$(echo "$size * 1048576" | bc | cut -d. -f1)" ;;
            "GB") echo "$(echo "$size * 1073741824" | bc | cut -d. -f1)" ;;
        esac
    else
        echo "0"
    fi
}

# Function to build Docker image
build_image() {
    local dockerfile=$1
    local image_name=$2
    local build_args=$3
    
    print_info "Building $dockerfile -> $image_name"
    
    if [ -n "$build_args" ]; then
        docker build -f "$dockerfile" -t "$image_name" $build_args .
    else
        docker build -f "$dockerfile" -t "$image_name" .
    fi
    
    if [ $? -eq 0 ]; then
        print_success "Successfully built $image_name"
    else
        print_error "Failed to build $image_name"
        return 1
    fi
}

# Function to clean up images
cleanup_images() {
    print_info "Cleaning up test images..."
    for dockerfile in Dockerfile*; do
        if [ -f "$dockerfile" ]; then
            local image_name="springboot-app-${dockerfile#Dockerfile}"
            if [ "$image_name" = "springboot-app-" ]; then
                image_name="springboot-app"
            fi
            docker rmi "$image_name" 2>/dev/null || true
        fi
    done
    print_success "Cleanup completed"
}

# Main execution
main() {
    print_header "Docker Image Size Comparison Tool"
    echo
    
    # Check if Docker is running
    if ! docker info >/dev/null 2>&1; then
        print_error "Docker is not running. Please start Docker and try again."
        exit 1
    fi
    
    # Check if we're in the right directory
    if [ ! -f "pom.xml" ]; then
        print_error "pom.xml not found. Please run this script from the project root directory."
        exit 1
    fi
    
    # Check if target directory exists
    if [ ! -d "target" ] || [ ! -f "target"/*.jar ]; then
        print_warning "No JAR file found in target directory. Building the project first..."
        if [ -f "mvnw" ]; then
            ./mvnw clean package -DskipTests
        else
            mvn clean package -DskipTests
        fi
    fi
    
    # Array to store results
    declare -a results
    declare -a image_names
    
    # Build all Dockerfiles
    print_header "Building Docker Images"
    echo
    
    for dockerfile in Dockerfile*; do
        if [ -f "$dockerfile" ]; then
            local image_name="springboot-app-${dockerfile#Dockerfile}"
            if [ "$image_name" = "springboot-app-" ]; then
                image_name="springboot-app"
            fi
            
            print_info "Processing $dockerfile"
            
            # Build the image
            if build_image "$dockerfile" "$image_name"; then
                # Get image size
                local size=$(get_image_size "$image_name")
                local size_bytes=$(get_image_size_bytes "$image_name")
                
                results+=("$size_bytes")
                image_names+=("$image_name")
                
                print_success "$image_name: $size"
            else
                print_error "Skipping $dockerfile due to build failure"
            fi
            echo
        fi
    done
    
    # Compare and display results
    print_header "Image Size Comparison Results"
    echo
    
    # Create a temporary file for sorting
    temp_file=$(mktemp)
    
    for i in "${!image_names[@]}"; do
        local size_formatted=$(format_bytes "${results[$i]}")
        echo "${results[$i]} ${image_names[$i]} ${size_formatted}" >> "$temp_file"
    done
    
    # Sort by size (smallest first) and display
    echo -e "${CYAN}Rank | Image Name | Size${NC}"
    echo -e "${CYAN}-----|------------|------${NC}"
    
    sort -n "$temp_file" | while read -r line; do
        local size_bytes=$(echo "$line" | awk '{print $1}')
        local image_name=$(echo "$line" | awk '{print $2}')
        local size_formatted=$(echo "$line" | awk '{print $3}')
        
        # Determine rank color based on size
        if [ "$size_bytes" -eq "$(sort -n "$temp_file" | head -1 | awk '{print $1}')" ]; then
            echo -e "${GREEN}🥇   | $image_name | $size_formatted${NC}"
        elif [ "$size_bytes" -eq "$(sort -n "$temp_file" | head -2 | tail -1 | awk '{print $1}')" ]; then
            echo -e "${YELLOW}🥈   | $image_name | $size_formatted${NC}"
        elif [ "$size_bytes" -eq "$(sort -n "$temp_file" | head -3 | tail -1 | awk '{print $1}')" ]; then
            echo -e "${PURPLE}🥉   | $image_name | $size_formatted${NC}"
        else
            echo -e "     | $image_name | $size_formatted"
        fi
    done
    
    # Clean up temp file
    rm "$temp_file"
    
    echo
    print_header "Analysis Summary"
    echo
    
    # Calculate statistics
    local smallest_size=$(printf '%s\n' "${results[@]}" | sort -n | head -1)
    local largest_size=$(printf '%s\n' "${results[@]}" | sort -n | tail -1)
    local smallest_image=""
    local largest_image=""
    
    for i in "${!image_names[@]}"; do
        if [ "${results[$i]}" -eq "$smallest_size" ]; then
            smallest_image="${image_names[$i]}"
        fi
        if [ "${results[$i]}" -eq "$largest_size" ]; then
            largest_image="${image_names[$i]}"
        fi
    done
    
    local size_difference=$((largest_size - smallest_size))
    local size_reduction_percent=$(echo "scale=2; ($size_difference * 100) / $largest_size" | bc)
    
    print_success "Smallest image: $smallest_image ($(format_bytes $smallest_size))"
    print_error "Largest image: $largest_image ($(format_bytes $largest_size))"
    print_info "Size difference: $(format_bytes $size_difference)"
    print_info "Size reduction: ${size_reduction_percent}%"
    
    echo
    print_header "Recommendations"
    echo
    
    if [[ "$smallest_image" == *"jlink-auto-optimized"* ]]; then
        print_success "Use $smallest_image for production - it's the most optimized!"
        print_info "This image uses automatic module detection with jdeps for maximum optimization."
    elif [[ "$smallest_image" == *"jlink-optimized"* ]]; then
        print_success "Use $smallest_image for production - it's well optimized!"
        print_info "This image uses manual module selection with jlink for good optimization."
    elif [[ "$smallest_image" == *"03-optimized"* ]]; then
        print_success "Use $smallest_image for production - it's reasonably optimized!"
        print_info "This image uses Alpine Linux and JRE-only base for good size reduction."
    else
        print_warning "Consider using a more optimized Dockerfile for production."
        print_info "The jlink-optimized or jlink-auto-optimized versions provide better size optimization."
    fi
    
    echo
    print_info "To clean up all test images, run: docker rmi \$(docker images | grep springboot-app | awk '{print \$3}')"
    echo
    print_header "Build Complete! 🐳"
}

# Handle script arguments
case "${1:-}" in
    --cleanup)
        cleanup_images
        exit 0
        ;;
    --help|-h)
        echo "Usage: $0 [OPTIONS]"
        echo
        echo "Options:"
        echo "  --cleanup    Clean up all test images"
        echo "  --help, -h   Show this help message"
        echo
        echo "This script builds all Dockerfile* files and compares their image sizes."
        exit 0
        ;;
    *)
        main
        ;;
esac
