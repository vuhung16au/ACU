#!/usr/bin/env python3
"""
Script to generate performance comparison graphs for Python vs Cython radix sort.
"""

import time
import matplotlib.pyplot as plt
import numpy as np
from radix_sort import radix_sort, load_list_from_file
from radix_sort_cy import radix_sort_cython_wrapper
import os


def generate_test_data(size, filename):
    """Generate test data for a given size."""
    if not os.path.exists(filename):
        print(f"Generating {size:,} random numbers...")
        import subprocess
        subprocess.run([".venv/bin/python", "generate_random_list.py", "-n", str(size), "-o", filename], check=True)
    return load_list_from_file(filename)


def measure_performance(arr, implementation_name, sort_func):
    """Measure the performance of a sorting implementation."""
    test_arr = arr.copy()
    start_time = time.time()
    result = sort_func(test_arr)
    end_time = time.time()
    return end_time - start_time, result


def run_performance_tests():
    """Run performance tests for different data sizes."""
    sizes = [1000, 10000, 100000, 250000, 500000, 1000000]
    python_times = []
    cython_times = []
    
    print("Running performance tests for graph generation...")
    print("=" * 60)
    
    for size in sizes:
        filename = f"graph_test_{size}.txt"
        print(f"\nTesting with {size:,} elements...")
        
        # Generate or load test data
        arr = generate_test_data(size, filename)
        
        # Test Python implementation
        print(f"  Testing Python implementation...")
        python_time, python_result = measure_performance(arr, "Python", radix_sort)
        python_times.append(python_time)
        
        # Test Cython implementation
        print(f"  Testing Cython implementation...")
        cython_time, cython_result = measure_performance(arr, "Cython", radix_sort_cython_wrapper)
        cython_times.append(cython_time)
        
        # Verify results match
        if python_result == cython_result:
            print(f"  ✓ Results match")
        else:
            print(f"  ✗ Results do not match!")
        
        # Calculate speedup
        speedup = python_time / cython_time if cython_time > 0 else 0
        print(f"  Python: {python_time:.4f}s, Cython: {cython_time:.4f}s, Speedup: {speedup:.2f}x")
    
    return sizes, python_times, cython_times


def create_performance_graph(sizes, python_times, cython_times):
    """Create and save the performance comparison graph."""
    print("\nGenerating performance graph...")
    
    # Create the plot
    plt.figure(figsize=(12, 8))
    
    # Convert sizes to strings for x-axis labels
    size_labels = [f"{size//1000}K" if size < 1000000 else f"{size//1000000}M" for size in sizes]
    
    # Plot the data
    plt.plot(size_labels, python_times, 'b-o', label='Python', linewidth=2, markersize=8)
    plt.plot(size_labels, cython_times, 'r-s', label='Cython', linewidth=2, markersize=8)
    
    # Customize the plot
    plt.xlabel('Data Size', fontsize=14, fontweight='bold')
    plt.ylabel('Execution Time (seconds)', fontsize=14, fontweight='bold')
    plt.title('Python vs Cython Radix Sort Performance Comparison', fontsize=16, fontweight='bold')
    plt.legend(fontsize=12)
    plt.grid(True, alpha=0.3)
    
    # Set y-axis to log scale for better visualization
    plt.yscale('log')
    
    # Add value annotations on the points
    for i, (size, py_time, cy_time) in enumerate(zip(size_labels, python_times, cython_times)):
        plt.annotate(f'{py_time:.3f}s', (i, py_time), textcoords="offset points", 
                    xytext=(0,10), ha='center', fontsize=9, color='blue')
        plt.annotate(f'{cy_time:.3f}s', (i, cy_time), textcoords="offset points", 
                    xytext=(0,-15), ha='center', fontsize=9, color='red')
    
    # Add speedup annotations
    for i, (size, py_time, cy_time) in enumerate(zip(size_labels, python_times, cython_times)):
        speedup = py_time / cy_time if cy_time > 0 else 0
        plt.annotate(f'{speedup:.1f}x', (i, (py_time + cy_time) / 2), 
                    textcoords="offset points", xytext=(0,0), ha='center', 
                    fontsize=10, fontweight='bold', color='green',
                    bbox=dict(boxstyle="round,pad=0.3", facecolor="lightgreen", alpha=0.7))
    
    # Adjust layout and save
    plt.tight_layout()
    plt.savefig('python_vs_cython.png', dpi=300, bbox_inches='tight')
    print("Graph saved as 'python_vs_cython.png'")
    
    # Also create a speedup comparison subplot
    plt.figure(figsize=(12, 6))
    speedups = [py_time / cy_time if cy_time > 0 else 0 for py_time, cy_time in zip(python_times, cython_times)]
    
    plt.bar(size_labels, speedups, color='green', alpha=0.7, edgecolor='darkgreen', linewidth=1)
    plt.xlabel('Data Size', fontsize=14, fontweight='bold')
    plt.ylabel('Speedup Factor', fontsize=14, fontweight='bold')
    plt.title('Cython Speedup Over Python', fontsize=16, fontweight='bold')
    plt.grid(True, alpha=0.3, axis='y')
    
    # Add value labels on bars
    for i, speedup in enumerate(speedups):
        plt.text(i, speedup + 0.5, f'{speedup:.1f}x', ha='center', va='bottom', 
                fontsize=12, fontweight='bold')
    
    plt.tight_layout()
    plt.savefig('cython_speedup.png', dpi=300, bbox_inches='tight')
    print("Speedup graph saved as 'cython_speedup.png'")
    
    return speedups


def print_summary(sizes, python_times, cython_times, speedups):
    """Print a summary of the performance results."""
    print("\n" + "=" * 80)
    print("PERFORMANCE SUMMARY")
    print("=" * 80)
    print(f"{'Size':<10} {'Python (s)':<12} {'Cython (s)':<12} {'Speedup':<10} {'Improvement':<15}")
    print("-" * 80)
    
    for size, py_time, cy_time, speedup in zip(sizes, python_times, cython_times, speedups):
        size_str = f"{size//1000}K" if size < 1000000 else f"{size//1000000}M"
        improvement = f"{((py_time - cy_time) / py_time * 100):.1f}%"
        print(f"{size_str:<10} {py_time:<12.4f} {cy_time:<12.4f} {speedup:<10.2f} {improvement:<15}")
    
    avg_speedup = np.mean(speedups)
    print("-" * 80)
    print(f"Average Speedup: {avg_speedup:.2f}x")
    print(f"Best Speedup: {max(speedups):.2f}x at {sizes[speedups.index(max(speedups))]//1000}K elements")
    print("=" * 80)


def main():
    """Main function to generate performance graphs."""
    print("Python vs Cython Radix Sort Performance Graph Generator")
    print("=" * 60)
    
    # Run performance tests
    sizes, python_times, cython_times = run_performance_tests()
    
    # Create graphs
    speedups = create_performance_graph(sizes, python_times, cython_times)
    
    # Print summary
    print_summary(sizes, python_times, cython_times, speedups)
    
    print(f"\nGraphs generated successfully!")
    print(f"- python_vs_cython.png: Performance comparison")
    print(f"- cython_speedup.png: Speedup visualization")


if __name__ == "__main__":
    main()
