#!/bin/bash
# Simple notebook verification script
# Executes all notebooks in the notebooks/ directory and reports results

set -e

NOTEBOOKS_DIR="notebooks"
TIMEOUT=300
VERBOSE=false
STOP_ON_ERROR=false

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Parse command line arguments
while [[ $# -gt 0 ]]; do
    case $1 in
        --notebooks-dir)
            NOTEBOOKS_DIR="$2"
            shift 2
            ;;
        --timeout)
            TIMEOUT="$2"
            shift 2
            ;;
        --verbose|-v)
            VERBOSE=true
            shift
            ;;
        --stop-on-error)
            STOP_ON_ERROR=true
            shift
            ;;
        --help|-h)
            echo "Usage: $0 [options]"
            echo "Options:"
            echo "  --notebooks-dir DIR   Directory containing notebooks (default: notebooks/)"
            echo "  --timeout SECONDS     Timeout for each notebook (default: 300)"
            echo "  --verbose, -v         Enable verbose output"
            echo "  --stop-on-error       Stop on first error"
            echo "  --help, -h            Show this help"
            exit 0
            ;;
        *)
            echo "Unknown option: $1"
            exit 1
            ;;
    esac
done

# Check if notebooks directory exists
if [ ! -d "$NOTEBOOKS_DIR" ]; then
    printf "${RED}❌ Notebooks directory not found: $NOTEBOOKS_DIR${NC}\n"
    exit 1
fi

# Check if jupyter is installed
if ! command -v jupyter &> /dev/null; then
    printf "${RED}❌ Jupyter is not installed. Install with: pip install jupyter nbconvert${NC}\n"
    exit 1
fi

printf "${BLUE}🔍 Starting notebook verification...${NC}\n"
echo "======================================"
echo "Directory: $NOTEBOOKS_DIR"
echo "Timeout: ${TIMEOUT}s"
echo "======================================"

# Find all notebooks
notebooks=($(find "$NOTEBOOKS_DIR" -name "*.ipynb" | sort))

if [ ${#notebooks[@]} -eq 0 ]; then
    printf "${YELLOW}⚠️  No notebooks found in $NOTEBOOKS_DIR${NC}\n"
    exit 0
fi

printf "${BLUE}Found ${#notebooks[@]} notebooks${NC}\n"

# Counters
total=0
passed=0
failed=0
start_time=$(date +%s)

# Process each notebook
for notebook in "${notebooks[@]}"; do
    ((total++))
    notebook_name=$(basename "$notebook")
    
    echo ""
    printf "${BLUE}📊 [$total/${#notebooks[@]}] Verifying $notebook_name${NC}\n"
    
    if [ "$VERBOSE" = true ]; then
        echo "  Path: $notebook"
    fi
    
    # Create temporary output file
    temp_output=$(mktemp -t notebook_output_XXXXXX).ipynb
    
    # Execute notebook
    notebook_start=$(date +%s)
    
    if jupyter nbconvert \
        --to notebook \
        --execute \
        --ExecutePreprocessor.timeout=$TIMEOUT \
        --output "$temp_output" \
        "$notebook" > /dev/null 2>&1; then
        
        ((passed++))
        notebook_end=$(date +%s)
        execution_time=$((notebook_end - notebook_start))
        printf "${GREEN}✅ PASSED (${execution_time}s)${NC}\n"
        
    else
        ((failed++))
        notebook_end=$(date +%s)
        execution_time=$((notebook_end - notebook_start))
        printf "${RED}❌ FAILED (${execution_time}s)${NC}\n"
        
        if [ "$VERBOSE" = true ]; then
            printf "${RED}   Error details:${NC}\n"
            jupyter nbconvert \
                --to notebook \
                --execute \
                --ExecutePreprocessor.timeout=$TIMEOUT \
                --output "$temp_output" \
                "$notebook" 2>&1 | sed 's/^/   /'
        fi
        
        if [ "$STOP_ON_ERROR" = true ]; then
            printf "${RED}🛑 Stopping on first error${NC}\n"
            rm -f "$temp_output"
            break
        fi
    fi
    
    # Clean up temporary file
    rm -f "$temp_output"
done

# Calculate total time
end_time=$(date +%s)
total_time=$((end_time - start_time))

# Print summary
echo ""
echo "======================================"
printf "${BLUE}📈 VERIFICATION SUMMARY${NC}\n"
echo "======================================"
echo "Total notebooks: $total"
printf "✅ Passed: ${GREEN}$passed${NC}\n"
printf "❌ Failed: ${RED}$failed${NC}\n"
echo "⏱️  Total time: ${total_time}s"

if [ $failed -eq 0 ]; then
    echo ""
    printf "${GREEN}🎉 All notebooks executed successfully!${NC}\n"
    exit 0
else
    echo ""
    printf "${RED}⚠️  $failed notebook(s) failed verification${NC}\n"
    exit 1
fi
