#!/bin/bash

# Quick Sort Performance Comparison Script
# This script runs Quick Sort implementations in Python, C++, Java, JavaScript, Go, and C
# and compares their performance.

echo "=========================================="
echo "Quick Sort Performance Comparison"
echo "=========================================="
echo ""

# Set base directory
BASE_DIR=$(cd "$(dirname "$0")/.." && pwd)
echo "Base directory: $BASE_DIR"

# Make sure results directory exists
mkdir -p $BASE_DIR/results

# Clean up previous results
echo "Cleaning up previous results..."
rm -f $BASE_DIR/results/results_*.txt $BASE_DIR/src/quick_sort_cpp $BASE_DIR/src/QuickSort.class $BASE_DIR/src/quick_sort_c
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

# Step 2: Run Python implementation
echo "Step 2: Running Python Quick Sort..."
python3 $BASE_DIR/src/quick_sort.py -c $BASE_DIR/config/number-of-data-points.txt -d $BASE_DIR/datasets/random_list_100000.txt -r $BASE_DIR/results/results_python.txt
if [ $? -ne 0 ]; then
    echo "Error: Python implementation failed"
    exit 1
fi
echo ""

# Step 3: Compile and run C++ implementation
echo "Step 3: Compiling and running C++ Quick Sort..."
g++ -O2 -std=c++17 -o $BASE_DIR/src/quick_sort_cpp $BASE_DIR/src/quick_sort.cpp
if [ $? -ne 0 ]; then
    echo "Error: C++ compilation failed"
    exit 1
fi
$BASE_DIR/src/quick_sort_cpp $BASE_DIR/datasets/random_list_100000.txt $BASE_DIR/results/results_cpp.txt
if [ $? -ne 0 ]; then
    echo "Error: C++ implementation failed"
    exit 1
fi
echo ""

# Step 4: Compile and run Java implementation
echo "Step 4: Compiling and running Java Quick Sort..."
echo "Current directory: $(pwd)"
ls -la $BASE_DIR/src/QuickSort.java
cd $BASE_DIR
javac src/QuickSort.java
if [ $? -ne 0 ]; then
    echo "Error: Java compilation failed"
    exit 1
fi
ls -la src/QuickSort.class
echo "Running Java with classpath: $(pwd)"
java -cp src QuickSort datasets/random_list_100000.txt results/results_java.txt
if [ $? -ne 0 ]; then
    echo "Error: Java implementation failed"
    exit 1
fi
echo ""

# Step 5: Run JavaScript implementation
echo "Step 5: Running JavaScript Quick Sort..."
cd $BASE_DIR
node src/quick_sort.js datasets/random_list_100000.txt results/results_javascript.txt
if [ $? -ne 0 ]; then
    echo "Error: JavaScript implementation failed"
    exit 1
fi
echo ""

# Step 6: Compile and run Go implementation
echo "Step 6: Running Go Quick Sort..."
/opt/homebrew/bin/go run $BASE_DIR/src/quick_sort.go -file $BASE_DIR/datasets/random_list_100000.txt -output $BASE_DIR/results/results_go.txt
if [ $? -ne 0 ]; then
    echo "Error: Go implementation failed"
    exit 1
fi
echo ""

# Step 7: Compile and run C implementation
echo "Step 7: Compiling and running C Quick Sort..."
cd $BASE_DIR
gcc -O2 -o src/quick_sort_c src/quick_sort.c
if [ $? -ne 0 ]; then
    echo "Error: C compilation failed"
    exit 1
fi
./src/quick_sort_c datasets/random_list_100000.txt results/results_c.txt
if [ $? -ne 0 ]; then
    echo "Error: C implementation failed"
    exit 1
fi
echo ""

# Step 8: Compile results
echo "Step 8: Compiling results..."
echo "=========================================="
echo "Performance Comparison Results"
echo "=========================================="
echo ""

# Create consolidated results file
mkdir -p $BASE_DIR/analysis
cat > $BASE_DIR/analysis/consolidated_results.txt << EOF
Quick Sort Performance Comparison Results
=========================================
Date: $(date)
Data Size: 100,000 integers

Individual Results:
EOF

echo "Python Results:"
if [ -f "$BASE_DIR/results/results_python.txt" ]; then
    cat $BASE_DIR/results/results_python.txt
    echo "" >> $BASE_DIR/analysis/consolidated_results.txt
    echo "Python:" >> $BASE_DIR/analysis/consolidated_results.txt
    cat $BASE_DIR/results/results_python.txt >> $BASE_DIR/analysis/consolidated_results.txt
    echo "" >> $BASE_DIR/analysis/consolidated_results.txt
else
    echo "Python results not found"
fi
echo ""

echo "C++ Results:"
if [ -f "$BASE_DIR/results/results_cpp.txt" ]; then
    cat $BASE_DIR/results/results_cpp.txt
    echo "C++:" >> $BASE_DIR/analysis/consolidated_results.txt
    cat $BASE_DIR/results/results_cpp.txt >> $BASE_DIR/analysis/consolidated_results.txt
    echo "" >> $BASE_DIR/analysis/consolidated_results.txt
else
    echo "C++ results not found"
fi
echo ""

echo "Java Results:"
if [ -f "$BASE_DIR/results/results_java.txt" ]; then
    cat $BASE_DIR/results/results_java.txt
    echo "Java:" >> $BASE_DIR/analysis/consolidated_results.txt
    cat $BASE_DIR/results/results_java.txt >> $BASE_DIR/analysis/consolidated_results.txt
    echo "" >> $BASE_DIR/analysis/consolidated_results.txt
else
    echo "Java results not found"
fi
echo ""

echo "JavaScript Results:"
if [ -f "$BASE_DIR/results/results_javascript.txt" ]; then
    cat $BASE_DIR/results/results_javascript.txt
    echo "JavaScript:" >> $BASE_DIR/analysis/consolidated_results.txt
    cat $BASE_DIR/results/results_javascript.txt >> $BASE_DIR/analysis/consolidated_results.txt
    echo "" >> $BASE_DIR/analysis/consolidated_results.txt
else
    echo "JavaScript results not found"
fi
echo ""

echo "Go Results:"
if [ -f "$BASE_DIR/results/results_go.txt" ]; then
    cat $BASE_DIR/results/results_go.txt
    echo "Go:" >> $BASE_DIR/analysis/consolidated_results.txt
    cat $BASE_DIR/results/results_go.txt >> $BASE_DIR/analysis/consolidated_results.txt
    echo "" >> $BASE_DIR/analysis/consolidated_results.txt
else
    echo "Go results not found"
fi
echo ""

echo "C Results:"
if [ -f "$BASE_DIR/results/results_c.txt" ]; then
    cat $BASE_DIR/results/results_c.txt
    echo "C:" >> $BASE_DIR/analysis/consolidated_results.txt
    cat $BASE_DIR/results/results_c.txt >> $BASE_DIR/analysis/consolidated_results.txt
    echo "" >> $BASE_DIR/analysis/consolidated_results.txt
else
    echo "C results not found"
fi
echo ""

# Extract and compare execution times
echo "Performance Summary:"
echo "==================="

# Create a summary table
export BASE_DIR="$BASE_DIR"
python3 << 'EOF'
import os
import re

def extract_time(filename):
    if os.path.exists(filename):
        with open(filename, 'r') as f:
            content = f.read()
            match = re.search(r'Execution time: ([\d.]+) seconds', content)
            if match:
                return float(match.group(1))
    return None

languages = ['Python', 'C++', 'Java', 'JavaScript', 'Go', 'C']
basedir = os.environ.get('BASE_DIR', os.getcwd())
files = [f"{basedir}/results/results_python.txt", f"{basedir}/results/results_cpp.txt", f"{basedir}/results/results_java.txt", 
         f"{basedir}/results/results_javascript.txt", f"{basedir}/results/results_go.txt", f"{basedir}/results/results_c.txt"]

results = []
for lang, file in zip(languages, files):
    time = extract_time(file)
    if time is not None:
        results.append((lang, time))

if results:
    # Sort by execution time
    results.sort(key=lambda x: x[1])
    
    print(f"{'Language':<12} {'Time (sec)':<12} {'Relative Speed':<15}")
    print("-" * 40)
    
    fastest_time = results[0][1]
    for lang, time in results:
        relative = time / fastest_time
        print(f"{lang:<12} {time:<12.6f} {relative:<15.2f}x")
    
    # Add to consolidated results
    with open(f"{basedir}/analysis/consolidated_results.txt", 'a') as f:
        f.write("\nPerformance Ranking (fastest to slowest):\n")
        f.write("=" * 45 + "\n")
        f.write(f"{'Language':<12} {'Time (sec)':<12} {'Relative Speed':<15}\n")
        f.write("-" * 40 + "\n")
        
        for lang, time in results:
            relative = time / fastest_time
            f.write(f"{lang:<12} {time:<12.6f} {relative:<15.2f}x\n")
else:
    print("No valid results found")
EOF

echo ""
echo "All results saved to analysis/consolidated_results.txt"
echo ""
echo "Cleaning up compiled files..."
rm -f $BASE_DIR/src/quick_sort_cpp $BASE_DIR/src/QuickSort.class

echo "Performance comparison completed!"
