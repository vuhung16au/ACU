#!/usr/bin/env python3
import os
import subprocess
import sys

print("==========================================")
print("COMPLETE SORTING ALGORITHMS PERFORMANCE STUDY")
print("==========================================\n")

# Get base and script directories
SCRIPT_DIR = os.path.dirname(os.path.abspath(__file__))
BASE_DIR = os.path.abspath(os.path.join(SCRIPT_DIR, '..'))

# Make scripts executable (if needed)
run_comparison_path = os.path.join(SCRIPT_DIR, 'run_comparison.sh')
try:
    os.chmod(run_comparison_path, 0o755)
except Exception as e:
    print(f"Warning: Could not set executable permission for run_comparison.sh: {e}")

# Step 1: Run the performance comparison
print("Phase 1: Running performance comparison...")
result = subprocess.run([run_comparison_path])
if result.returncode != 0:
    print("Error: Performance comparison failed")
    sys.exit(1)

print("\nPhase 2: Generating detailed analysis...")

# Step 2: Generate detailed analysis
analyze_script = os.path.join(SCRIPT_DIR, 'analyze_results.py')
result = subprocess.run([sys.executable, analyze_script])
if result.returncode != 0:
    print("Error: Analysis generation failed")
    sys.exit(1)

print("\n==========================================")
print("STUDY COMPLETED SUCCESSFULLY")
print("==========================================\n")
print("Generated files:")
print("• random_list.txt - Test data (100,000 integers)")
print("• results_*.txt - Individual language results")
print("• consolidated_results.txt - Combined results")
print("• performance_analysis.txt - Detailed analysis\n")
print("To view the complete analysis:")
print("cat performance_analysis.txt\n")
print("To view the quick summary:")
print("cat consolidated_results.txt\n")
print("Algorithms included in this study:")
print("1. Bubble Sort")
print("2. Selection Sort")
print("3. Insertion Sort")
print("4. Quick Sort")
print("5. Merge Sort")
print("6. Counting Sort")
print("7. Radix Sort") 