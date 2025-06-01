#!/bin/bash

# Sorting Algorithms Performance Comparison Script
# This script runs different sorting algorithm implementations in Python, C++, Java, JavaScript, Go, and C
# and compares their performance.

echo "=========================================="
echo "Sorting Algorithms Performance Comparison"
echo "=========================================="
echo ""

# Set base directory
BASE_DIR=$(cd "$(dirname "$0")/.." && pwd)
echo "Base directory: $BASE_DIR"

# Define sorting algorithms
ALGORITHMS=("bubble" "selection" "insertion" "quick" "merge" "counting" "radix")
ALGORITHM_NAMES=("Bubble Sort" "Selection Sort" "Insertion Sort" "Quick Sort" "Merge Sort" "Counting Sort" "Radix Sort")

# Make sure results directory exists
mkdir -p $BASE_DIR/results

# Clean up previous results
echo "Cleaning up previous results..."
rm -f $BASE_DIR/results/results_*.txt $BASE_DIR/src/*_sort_cpp $BASE_DIR/src/*.class $BASE_DIR/src/*_sort_c
echo ""

# Step 1: Generate random data
echo "Step 1: Generating random data..."
cd $BASE_DIR/scripts
python3 generate_data.py
if [ $? -ne 0 ]; then
    echo "Error: Failed to generate random data"
    exit 1
fi
cd $BASE_DIR
echo ""

# Function to run tests for a specific algorithm
run_algorithm_tests() {
    local algo=$1
    local algo_name=$2
    
    echo "============================================"
    echo "Testing $algo_name Implementation"
    echo "============================================"
    echo ""
    
    # Check if this is bubble sort with a large dataset
    # Get dataset size from the filename
    local dataset_file="$BASE_DIR/datasets/random_list_100000.txt"
    local data_size=$(echo "$dataset_file" | grep -o '[0-9]\+')
    
    # Skip bubble, insertion, and selection sort for large datasets (N > 10000)
    if [[ ("$algo" == "bubble" || "$algo" == "insertion" || "$algo" == "selection") && "$data_size" -gt 10000 ]]; then
        echo "Skipping $algo_name for large dataset (N=$data_size)..."
        
        # Create N/A result files for the skipped algorithm
        echo "Execution time: N/A (skipped for large dataset)" > $BASE_DIR/results/results_python_${algo}.txt
        echo "Execution time: N/A (skipped for large dataset)" > $BASE_DIR/results/results_cpp_${algo}.txt
        echo "Execution time: N/A (skipped for large dataset)" > $BASE_DIR/results/results_java_${algo}.txt
        echo "Execution time: N/A (skipped for large dataset)" > $BASE_DIR/results/results_javascript_${algo}.txt
        echo "Execution time: N/A (skipped for large dataset)" > $BASE_DIR/results/results_go_${algo}.txt
        echo "Execution time: N/A (skipped for large dataset)" > $BASE_DIR/results/results_c_${algo}.txt
        
        echo "Created N/A result files for $algo_name (N=$data_size)"
        echo ""
        return
    fi
    
    # Run Python implementation
    echo "Running Python $algo_name..."
    python3 $BASE_DIR/src/${algo}_sort.py -c $BASE_DIR/config/number-of-data-points.txt -d $BASE_DIR/datasets/random_list_100000.txt -r $BASE_DIR/results/results_python_${algo}.txt
    if [ $? -ne 0 ]; then
        echo "Warning: Python $algo_name implementation failed"
    fi
    echo ""

    # Compile and run C++ implementation
    echo "Compiling and running C++ $algo_name..."
    g++ -O2 -std=c++17 -o $BASE_DIR/src/${algo}_sort_cpp $BASE_DIR/src/${algo}_sort.cpp
    if [ $? -ne 0 ]; then
        echo "Warning: C++ $algo_name compilation failed"
    else
        $BASE_DIR/src/${algo}_sort_cpp $BASE_DIR/datasets/random_list_100000.txt $BASE_DIR/results/results_cpp_${algo}.txt
        if [ $? -ne 0 ]; then
            echo "Warning: C++ $algo_name implementation failed"
        fi
    fi
    echo ""

    # Compile and run Java implementation
    echo "Compiling and running Java $algo_name..."
    # Convert first letter to uppercase for Java class name
    java_class="$(tr '[:lower:]' '[:upper:]' <<< ${algo:0:1})${algo:1}Sort"
    cd $BASE_DIR
    javac src/${java_class}.java
    if [ $? -ne 0 ]; then
        echo "Warning: Java $algo_name compilation failed"
    else
        java -cp src ${java_class} datasets/random_list_100000.txt results/results_java_${algo}.txt
        if [ $? -ne 0 ]; then
            echo "Warning: Java $algo_name implementation failed"
        fi
    fi
    echo ""

    # Run JavaScript implementation
    echo "Running JavaScript $algo_name..."
    cd $BASE_DIR
    node src/${algo}_sort.js datasets/random_list_100000.txt results/results_javascript_${algo}.txt
    if [ $? -ne 0 ]; then
        echo "Warning: JavaScript $algo_name implementation failed"
    fi
    echo ""

    # Run Go implementation
    echo "Running Go $algo_name..."
    /opt/homebrew/bin/go run $BASE_DIR/src/${algo}_sort.go -file $BASE_DIR/datasets/random_list_100000.txt -output $BASE_DIR/results/results_go_${algo}.txt
    if [ $? -ne 0 ]; then
        echo "Warning: Go $algo_name implementation failed"
    fi
    echo ""

    # Compile and run C implementation
    echo "Compiling and running C $algo_name..."
    cd $BASE_DIR
    gcc -O2 -o src/${algo}_sort_c src/${algo}_sort.c
    if [ $? -ne 0 ]; then
        echo "Warning: C $algo_name compilation failed"
    else
        ./src/${algo}_sort_c datasets/random_list_100000.txt results/results_c_${algo}.txt
        if [ $? -ne 0 ]; then
            echo "Warning: C $algo_name implementation failed"
        fi
    fi
    echo ""
}

# Run each algorithm's tests
for i in "${!ALGORITHMS[@]}"; do
    run_algorithm_tests "${ALGORITHMS[$i]}" "${ALGORITHM_NAMES[$i]}"
done

# Step 8: Compile results
echo "Step 8: Compiling results..."
echo "=========================================="
echo "Performance Comparison Results"
echo "=========================================="
echo ""

# Create consolidated results file
mkdir -p $BASE_DIR/analysis
cat > $BASE_DIR/analysis/consolidated_results.txt << EOF
Sorting Algorithms Performance Comparison Results
=========================================
Date: $(date)
Data Size: 100,000 integers

Individual Results:
EOF

# Process Python results for each algorithm
echo "Python Results:"
for i in "${!ALGORITHMS[@]}"; do
    algo="${ALGORITHMS[$i]}"
    algo_name="${ALGORITHM_NAMES[$i]}"
    
    if [ -f "$BASE_DIR/results/results_python_${algo}.txt" ]; then
        echo "${algo_name}:" 
        cat $BASE_DIR/results/results_python_${algo}.txt
        echo "" >> $BASE_DIR/analysis/consolidated_results.txt
        echo "Python (${algo_name}):" >> $BASE_DIR/analysis/consolidated_results.txt
        cat $BASE_DIR/results/results_python_${algo}.txt >> $BASE_DIR/analysis/consolidated_results.txt
        echo "" >> $BASE_DIR/analysis/consolidated_results.txt
    else
        echo "Python ${algo_name} results not found"
    fi
    echo ""
done

# Process C++ results for each algorithm
echo "C++ Results:"
for i in "${!ALGORITHMS[@]}"; do
    algo="${ALGORITHMS[$i]}"
    algo_name="${ALGORITHM_NAMES[$i]}"
    
    if [ -f "$BASE_DIR/results/results_cpp_${algo}.txt" ]; then
        echo "${algo_name}:" 
        cat $BASE_DIR/results/results_cpp_${algo}.txt
        echo "C++ (${algo_name}):" >> $BASE_DIR/analysis/consolidated_results.txt
        cat $BASE_DIR/results/results_cpp_${algo}.txt >> $BASE_DIR/analysis/consolidated_results.txt
        echo "" >> $BASE_DIR/analysis/consolidated_results.txt
    else
        echo "C++ ${algo_name} results not found"
    fi
    echo ""
done

# Process Java results for each algorithm
echo "Java Results:"
for i in "${!ALGORITHMS[@]}"; do
    algo="${ALGORITHMS[$i]}"
    algo_name="${ALGORITHM_NAMES[$i]}"
    
    if [ -f "$BASE_DIR/results/results_java_${algo}.txt" ]; then
        echo "${algo_name}:" 
        cat $BASE_DIR/results/results_java_${algo}.txt
        echo "Java (${algo_name}):" >> $BASE_DIR/analysis/consolidated_results.txt
        cat $BASE_DIR/results/results_java_${algo}.txt >> $BASE_DIR/analysis/consolidated_results.txt
        echo "" >> $BASE_DIR/analysis/consolidated_results.txt
    else
        echo "Java ${algo_name} results not found"
    fi
    echo ""
done

# Process JavaScript results for each algorithm
echo "JavaScript Results:"
for i in "${!ALGORITHMS[@]}"; do
    algo="${ALGORITHMS[$i]}"
    algo_name="${ALGORITHM_NAMES[$i]}"
    
    if [ -f "$BASE_DIR/results/results_javascript_${algo}.txt" ]; then
        echo "${algo_name}:" 
        cat $BASE_DIR/results/results_javascript_${algo}.txt
        echo "JavaScript (${algo_name}):" >> $BASE_DIR/analysis/consolidated_results.txt
        cat $BASE_DIR/results/results_javascript_${algo}.txt >> $BASE_DIR/analysis/consolidated_results.txt
        echo "" >> $BASE_DIR/analysis/consolidated_results.txt
    else
        echo "JavaScript ${algo_name} results not found"
    fi
    echo ""
done

# Process Go results for each algorithm
echo "Go Results:"
for i in "${!ALGORITHMS[@]}"; do
    algo="${ALGORITHMS[$i]}"
    algo_name="${ALGORITHM_NAMES[$i]}"
    
    if [ -f "$BASE_DIR/results/results_go_${algo}.txt" ]; then
        echo "${algo_name}:" 
        cat $BASE_DIR/results/results_go_${algo}.txt
        echo "Go (${algo_name}):" >> $BASE_DIR/analysis/consolidated_results.txt
        cat $BASE_DIR/results/results_go_${algo}.txt >> $BASE_DIR/analysis/consolidated_results.txt
        echo "" >> $BASE_DIR/analysis/consolidated_results.txt
    else
        echo "Go ${algo_name} results not found"
    fi
    echo ""
done

# Process C results for each algorithm
echo "C Results:"
for i in "${!ALGORITHMS[@]}"; do
    algo="${ALGORITHMS[$i]}"
    algo_name="${ALGORITHM_NAMES[$i]}"
    
    if [ -f "$BASE_DIR/results/results_c_${algo}.txt" ]; then
        echo "${algo_name}:" 
        cat $BASE_DIR/results/results_c_${algo}.txt
        echo "C (${algo_name}):" >> $BASE_DIR/analysis/consolidated_results.txt
        cat $BASE_DIR/results/results_c_${algo}.txt >> $BASE_DIR/analysis/consolidated_results.txt
        echo "" >> $BASE_DIR/analysis/consolidated_results.txt
    else
        echo "C ${algo_name} results not found"
    fi
done
echo ""

# Extract and compare execution times
echo "Performance Summary:"
echo "==================="

# Create a summary table
export BASE_DIR="$BASE_DIR"
export ALGORITHMS="${ALGORITHMS[*]}"
export ALGORITHM_NAMES="${ALGORITHM_NAMES[*]}"
python3 << 'EOF'
import os
import re

def extract_time(filename):
    if os.path.exists(filename):
        with open(filename, 'r') as f:
            content = f.read()
            if "N/A" in content:
                return "N/A"
            match = re.search(r'Execution time: ([\d.]+) seconds', content)
            if match:
                return float(match.group(1))
    return None

basedir = os.environ.get('BASE_DIR', os.getcwd())

# Get algorithms list from environment
algorithms = os.environ.get('ALGORITHMS', '').split()
algorithm_names = os.environ.get('ALGORITHM_NAMES', '').split()

# Map algorithm names for better display
algorithm_display = {algo: name for algo, name in zip(algorithms, algorithm_names)}

# Collect all results
results = []
languages = ['Python', 'C++', 'Java', 'JavaScript', 'Go', 'C']

for lang in languages:
    for algo in algorithms:
        prefix = 'results_'
        if lang == 'C++':
            prefix = 'results_cpp_'
        elif lang == 'JavaScript':
            prefix = 'results_javascript_'
        elif lang == 'C':
            prefix = 'results_c_'
        elif lang == 'Java':
            prefix = 'results_java_'
        elif lang == 'Go':
            prefix = 'results_go_'
        elif lang == 'Python':
            prefix = 'results_python_'
            
        filename = f"{basedir}/results/{prefix}{algo}.txt"
        time = extract_time(filename)
        if time is not None:
            display_name = algorithm_display.get(algo, algo.capitalize() + ' Sort')
            results.append((f"{lang} ({display_name})", time))

if results:
    # Filter out N/A results for sorting
    numeric_results = [r for r in results if r[1] != "N/A"]
    na_results = [r for r in results if r[1] == "N/A"]
    
    # Sort by execution time (numeric results only)
    if numeric_results:
        numeric_results.sort(key=lambda x: x[1])
        fastest_time = numeric_results[0][1]
    
    # Combine sorted numeric results with N/A results at the end
    sorted_results = numeric_results + na_results
    
    print(f"{'Language/Algorithm':<30} {'Time (sec)':<12} {'Relative Speed':<15}")
    print("-" * 65)
    
    for lang, time in sorted_results:
        if time == "N/A":
            print(f"{lang:<30} {'N/A':<12} {'N/A':<15}")
        else:
            relative = time / fastest_time
            print(f"{lang:<30} {time:<12.6f} {relative:<15.2f}x")
    
    # Add to consolidated results
    with open(f"{basedir}/analysis/consolidated_results.txt", 'a') as f:
        f.write("\nPerformance Ranking (fastest to slowest):\n")
        f.write("=" * 65 + "\n")
        f.write(f"{'Language/Algorithm':<30} {'Time (sec)':<12} {'Relative Speed':<15}\n")
        f.write("-" * 65 + "\n")
        
        for lang, time in sorted_results:
            if time == "N/A":
                f.write(f"{lang:<30} {'N/A':<12} {'N/A':<15}\n")
            else:
                relative = time / fastest_time
                f.write(f"{lang:<30} {time:<12.6f} {relative:<15.2f}x\n")
    
    # Also compare algorithms within each language
    print("\nAlgorithm Comparison Within Languages:")
    print("-" * 65)
    
    for language in languages:
        lang_results = []
        
        for algo in algorithms:
            prefix = 'results_'
            if language == 'C++':
                prefix = 'results_cpp_'
            elif language == 'JavaScript':
                prefix = 'results_javascript_'
            elif language == 'C':
                prefix = 'results_c_'
            elif language == 'Java':
                prefix = 'results_java_'
            elif language == 'Go':
                prefix = 'results_go_'
            elif language == 'Python':
                prefix = 'results_python_'
                
            filename = f"{basedir}/results/{prefix}{algo}.txt"
            time = extract_time(filename)
            if time is not None:
                display_name = algorithm_display.get(algo, algo.capitalize() + ' Sort')
                lang_results.append((display_name, time))
        
        if lang_results:
            # Filter out N/A results for sorting
            numeric_results = [r for r in lang_results if r[1] != "N/A"]
            na_results = [r for r in lang_results if r[1] == "N/A"]
            
            # Sort by execution time (numeric results only)
            if numeric_results:
                numeric_results.sort(key=lambda x: x[1])
                fastest_time = numeric_results[0][1]
            
            # Combine sorted numeric results with N/A results at the end
            sorted_results = numeric_results + na_results
            
            print(f"\n{language} Algorithm Performance:")
            print(f"{'Algorithm':<20} {'Time (sec)':<12} {'Relative Speed':<15}")
            print("-" * 50)
            
            for algo, time in sorted_results:
                if time == "N/A":
                    print(f"{algo:<20} {'N/A':<12} {'N/A':<15}")
                else:
                    relative = time / fastest_time
                    print(f"{algo:<20} {time:<12.6f} {relative:<15.2f}x")
            
            # Add to consolidated results
            with open(f"{basedir}/analysis/consolidated_results.txt", 'a') as f:
                f.write(f"\n{language} Algorithm Performance:\n")
                f.write("=" * 50 + "\n")
                f.write(f"{'Algorithm':<20} {'Time (sec)':<12} {'Relative Speed':<15}\n")
                f.write("-" * 50 + "\n")
                
                for algo, time in sorted_results:
                    if time == "N/A":
                        f.write(f"{algo:<20} {'N/A':<12} {'N/A':<15}\n")
                    else:
                        relative = time / fastest_time
                        f.write(f"{algo:<20} {time:<12.6f} {relative:<15.2f}x\n")
else:
    print("No valid results found")
EOF

echo ""
echo "All results saved to analysis/consolidated_results.txt"
echo ""
echo "Cleaning up compiled files..."
rm -f $BASE_DIR/src/*_sort_cpp $BASE_DIR/src/*.class $BASE_DIR/src/*_sort_c

echo ""
echo "=========================================="
echo "Performance comparison completed!"
echo "=========================================="
