#!/usr/bin/env python3
import os
import shutil
import subprocess
from datetime import datetime
import argparse

# Sorting algorithms and display names
DEFAULT_ALGORITHMS = ["bubble", "selection", "insertion", "quick", "merge", "counting", "radix"]
DEFAULT_ALGORITHM_NAMES = ["Bubble Sort", "Selection Sort", "Insertion Sort", "Quick Sort", "Merge Sort", "Counting Sort", "Radix Sort"]

# Languages and result file patterns
LANGUAGES = ["Python", "C++", "Java", "JavaScript", "Go", "C"]

RESULTS_DIR = "results"
ANALYSIS_DIR = "analysis"
DOCS_DIR = "docs"
SRC_DIR = "src"
DATASET_DIR = "datasets"
CONFIG_SIZES_FILE = "config/number-of-data-points.txt"


def read_sizes_from_config():
    sizes = []
    if os.path.exists(CONFIG_SIZES_FILE):
        with open(CONFIG_SIZES_FILE) as f:
            for line in f:
                line = line.strip()
                if line and line.isdigit():
                    sizes.append(int(line))
    return sizes

def get_size_names(sizes):
    # Use default names for first four, then fallback to 'size-<N>'
    default_names = ["small", "medium", "large", "extra-large"]
    names = []
    for i, s in enumerate(sizes):
        if i < len(default_names):
            names.append(default_names[i])
        else:
            names.append(f"size-{s}")
    return names

def clean_previous_results():
    print("Cleaning up previous results...")
    for d in [RESULTS_DIR, ANALYSIS_DIR]:
        if os.path.exists(d):
            for f in os.listdir(d):
                if f.startswith("results_") or f.startswith("consolidated_results_") or f.startswith("performance_analysis_"):
                    os.remove(os.path.join(d, f))
    for ext in ["_sort_cpp", ".class", "_sort_c"]:
        for f in os.listdir(SRC_DIR):
            if f.endswith(ext):
                os.remove(os.path.join(SRC_DIR, f))
    md_path = os.path.join(DOCS_DIR, "MULTI_SIZE_PERFORMANCE_STUDY.md")
    if os.path.exists(md_path):
        os.remove(md_path)
    print("")

def ensure_directories():
    for d in [RESULTS_DIR, ANALYSIS_DIR, DOCS_DIR]:
        os.makedirs(d, exist_ok=True)

def generate_datasets():
    print("Step 1: Generating datasets for all sizes...")
    result = subprocess.run(["python3", "scripts/generate_data.py"], check=False)
    if result.returncode != 0:
        print("Error: Failed to generate datasets")
        exit(1)
    print("")

def extract_time(file):
    if os.path.isfile(file):
        with open(file) as f:
            content = f.read()
            if "N/A" in content:
                return "N/A"
            for line in content.splitlines():
                if 'Execution time:' in line:
                    try:
                        return float(line.split()[2])
                    except Exception:
                        return None
    return None

def extract_throughput(file):
    if os.path.isfile(file):
        with open(file) as f:
            for line in f:
                if 'Elements per second:' in line:
                    try:
                        return int(float(line.split()[3]))
                    except Exception:
                        return None
    return None

def generate_analysis_for_size(size, name, algorithms, algorithm_names):
    os.makedirs(ANALYSIS_DIR, exist_ok=True)
    consolidated_file = os.path.join(ANALYSIS_DIR, f"consolidated_results_{size}.txt")
    with open(consolidated_file, "w") as out:
        out.write(f"Sorting Algorithms Performance Comparison Results - Dataset Size: {size} ({name})\n")
        out.write("="*69 + "\n")
        out.write(f"Date: {datetime.now()}\nData Size: {size} integers\n\nIndividual Results by Algorithm:\n\n")
        for algo, algo_name in zip(algorithms, algorithm_names):
            out.write("-"*45 + "\n")
            out.write(f"{algo_name} Results:\n")
            out.write("-"*45 + "\n")
            languages = ["Python", "C++", "Java", "JavaScript", "Go", "C"]
            files = [
                f"{RESULTS_DIR}/results_python_{algo}_{size}.txt",
                f"{RESULTS_DIR}/results_cpp_{algo}_{size}.txt",
                f"{RESULTS_DIR}/results_java_{algo}_{size}.txt",
                f"{RESULTS_DIR}/results_javascript_{algo}_{size}.txt",
                f"{RESULTS_DIR}/results_go_{algo}_{size}.txt",
                f"{RESULTS_DIR}/results_c_{algo}_{size}.txt",
            ]
            for lang, file in zip(languages, files):
                out.write(f"{lang}:\n")
                if os.path.isfile(file):
                    with open(file) as fin:
                        out.write(fin.read())
                else:
                    out.write(f"Results file not found: {file}\n")
                out.write("\n")
        # Performance ranking
        out.write("Overall Performance Ranking (fastest to slowest):\n")
        out.write("="*45 + "\n")
        results = []
        for lang in LANGUAGES:
            for algo, algo_name in zip(algorithms, algorithm_names):
                if lang == "C++":
                    file = f"{RESULTS_DIR}/results_cpp_{algo}_{size}.txt"
                elif lang == "JavaScript":
                    file = f"{RESULTS_DIR}/results_javascript_{algo}_{size}.txt"
                else:
                    file = f"{RESULTS_DIR}/results_{lang.lower()}_{algo}_{size}.txt"
                t = extract_time(file)
                if t is not None:
                    results.append((f"{lang} - {algo_name}", t))
        numeric_results = [r for r in results if r[1] != "N/A"]
        na_results = [r for r in results if r[1] == "N/A"]
        if numeric_results:
            numeric_results.sort(key=lambda x: x[1])
            fastest_time = numeric_results[0][1]
            sorted_results = numeric_results + na_results
            out.write(f"{'Implementation':<25} {'Time (sec)':<12} {'Relative Speed':<15}\n")
            out.write("-"*60 + "\n")
            for impl, t in sorted_results:
                if t == "N/A":
                    out.write(f"{impl:<25} {'N/A':<12} {'N/A':<15}\n")
                else:
                    rel = t / fastest_time
                    out.write(f"{impl:<25} {t:<12.6f} {rel:<15.2f}x\n")
        else:
            out.write("No valid results found for size {size}\n")
        # Algorithm-specific comparisons
        out.write("\nAlgorithm-Specific Comparisons:\n")
        for algo, algo_name in zip(algorithms, algorithm_names):
            algo_results = []
            for lang in LANGUAGES:
                if lang == "C++":
                    file = f"{RESULTS_DIR}/results_cpp_{algo}_{size}.txt"
                elif lang == "JavaScript":
                    file = f"{RESULTS_DIR}/results_javascript_{algo}_{size}.txt"
                else:
                    file = f"{RESULTS_DIR}/results_{lang.lower()}_{algo}_{size}.txt"
                t = extract_time(file)
                if t is not None:
                    algo_results.append((lang, t))
            numeric_results = [r for r in algo_results if r[1] != "N/A"]
            na_results = [r for r in algo_results if r[1] == "N/A"]
            if algo_results:
                out.write(f"\n{algo_name} Comparison:\n")
                out.write("-"*40 + "\n")
                if numeric_results:
                    numeric_results.sort(key=lambda x: x[1])
                    fastest_time = numeric_results[0][1]
                    sorted_results = numeric_results + na_results
                else:
                    sorted_results = na_results
                out.write(f"{'Language':<12} {'Time (sec)':<12} {'Relative Speed':<15}\n")
                out.write("-"*40 + "\n")
                for lang, t in sorted_results:
                    if t == "N/A":
                        out.write(f"{lang:<12} {'N/A':<12} {'N/A':<15}\n")
                    else:
                        rel = t / fastest_time
                        out.write(f"{lang:<12} {t:<12.6f} {rel:<15.2f}x\n")
    print(f"Results for size {size} saved to {consolidated_file}")

def generate_comprehensive_analysis(sizes, size_names, algorithms, algorithm_names):
    print("Generating comprehensive multi-size analysis...")
    os.makedirs(DOCS_DIR, exist_ok=True)
    md_path = os.path.join(DOCS_DIR, "MULTI_SIZE_PERFORMANCE_STUDY.md")
    with open(md_path, "w") as md:
        md.write(f"# Multi-Size Sorting Algorithms Performance Study\n\n")
        md.write(f"**Date:** {datetime.now().strftime('%B %d, %Y')}  \n")
        md.write(f"**Test Environment:** macOS  \n")
        md.write(f"**Datasets:** N = 10, 100K, 250K, 500K random integers  \n")
        md.write(f"**Algorithms:** Bubble Sort, Selection Sort, Insertion Sort, Quick Sort, Merge Sort, Counting Sort, Radix Sort\n\n")
        md.write("## Executive Summary\n\nThis comprehensive study analyzes seven sorting algorithms across six programming languages (Python, C++, Java, JavaScript, Go, C) using four different dataset sizes to understand how performance scales with input size and algorithm choice.\n\n")
        md.write("## Test Configuration\n\n")
        md.write("- **Small Dataset (N=10):** Measures overhead and initialization costs\n")
        md.write("- **Medium Dataset (N=100K):** Standard benchmark size\n")
        md.write("- **Large Dataset (N=250K):** Tests algorithm efficiency \n")
        md.write("- **Extra-Large Dataset (N=500K):** Tests scalability and memory efficiency\n\n")
        md.write("## Performance Results by Dataset Size and Algorithm\n\n")
        for size, name in zip(sizes, size_names):
            md.write(f"### Dataset Size: {size} ({name})\n\n")
            # Table header
            results = []
            for lang in LANGUAGES:
                for algo, algo_name in zip(algorithms, algorithm_names):
                    if lang == "C++":
                        file = f"{RESULTS_DIR}/results_cpp_{algo}_{size}.txt"
                    elif lang == "JavaScript":
                        file = f"{RESULTS_DIR}/results_javascript_{algo}_{size}.txt"
                    else:
                        file = f"{RESULTS_DIR}/results_{lang.lower()}_{algo}_{size}.txt"
                    t = extract_time(file)
                    throughput = extract_throughput(file)
                    if t is not None and throughput is not None:
                        results.append((f"{lang} - {algo_name}", t, throughput))
            if results:
                results.sort(key=lambda x: x[1])
                md.write("| Rank | Implementation | Time (seconds) | Elements/Second | Relative Speed |\n")
                md.write("|------|---------------|----------------|-----------------|----------------|\n")
                fastest_time = results[0][1]
                for i, (impl, t, throughput) in enumerate(results, 1):
                    rel = t / fastest_time
                    md.write(f"| {i} | **{impl}** | {t:.6f} | {throughput:,} | {rel:.2f}x |\n")
                md.write("\n#### Algorithm-Specific Comparisons\n\n")
                for algo, algo_name in zip(algorithms, algorithm_names):
                    algo_results = []
                    for lang in LANGUAGES:
                        if lang == "C++":
                            file = f"{RESULTS_DIR}/results_cpp_{algo}_{size}.txt"
                        elif lang == "JavaScript":
                            file = f"{RESULTS_DIR}/results_javascript_{algo}_{size}.txt"
                        else:
                            file = f"{RESULTS_DIR}/results_{lang.lower()}_{algo}_{size}.txt"
                        t = extract_time(file)
                        throughput = extract_throughput(file)
                        if t is not None and throughput is not None:
                            algo_results.append((lang, t, throughput))
                    if algo_results:
                        algo_results.sort(key=lambda x: x[1])
                        md.write(f"**{algo_name}:**\n\n")
                        md.write("| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |\n")
                        md.write("|------|----------|----------------|-----------------|----------------|\n")
                        fastest_time = algo_results[0][1]
                        for i, (lang, t, throughput) in enumerate(algo_results, 1):
                            rel = t / fastest_time
                            md.write(f"| {i} | **{lang}** | {t:.6f} | {throughput:,} | {rel:.2f}x |\n")
                        md.write("\n")
            else:
                md.write(f"No valid results found for size {size}\n\n")
        md.write("## Scaling Analysis\n\n### Algorithm Performance Across Dataset Sizes\n\n")
        # Algorithm scaling table (C reference)
        md.write("| Algorithm | N=10 | N=100K | N=250K | N=500K | 250K/100K Ratio | 500K/250K Ratio | Big O |\n")
        md.write("|-----------|------|--------|--------|--------|-----------------|-----------------|-------|\n")
        for algo, algo_name in zip(algorithms, algorithm_names):
            times = []
            for size in sizes:
                file = f"{RESULTS_DIR}/results_c_{algo}_{size}.txt"
                t = extract_time(file)
                times.append(t)
            if all(t is not None for t in times):
                formatted_times = [f"{t:.6f}" if t != "N/A" else "N/A" for t in times]
                # Only compute ratios if enough sizes are present
                if len(times) > 2 and times[1] not in (None, "N/A") and times[2] not in (None, "N/A") and times[1] > 0:
                    ratio_2 = f"{times[2] / times[1]:.1f}x"
                else:
                    ratio_2 = "N/A"
                if len(times) > 3 and times[2] not in (None, "N/A") and times[3] not in (None, "N/A") and times[2] > 0:
                    ratio_3 = f"{times[3] / times[2]:.1f}x"
                else:
                    ratio_3 = "N/A"
                if "Bubble" in algo_name or "Selection" in algo_name or "Insertion" in algo_name:
                    big_o = "O(n²)"
                elif "Quick" in algo_name or "Merge" in algo_name:
                    big_o = "O(n log n)"
                elif "Counting" in algo_name or "Radix" in algo_name:
                    big_o = "O(n)"
                else:
                    big_o = "-"
                md.write(f"| {algo_name} | {' | '.join(formatted_times)} | {ratio_2} | {ratio_3} | {big_o} |\n")
            else:
                md.write(f"| {algo_name} | - | - | - | - | - | - | - |\n")
        md.write("\n### Language Performance Across Dataset Sizes\n\nThe following analysis shows how each language's performance scales with dataset size using Quick Sort as reference:\n\n")
        md.write("| Language | " + " | ".join([f"N={s}" for s in sizes]) + " | "
                 + ("100K/10 Ratio | 250K/100K Ratio | 500K/250K Ratio |" if len(sizes) >= 4 else "") + "\n")
        md.write("|----------|" + "------|" * len(sizes) + ("---------------|-----------------|-----------------|" if len(sizes) >= 4 else "") + "\n")
        for lang in LANGUAGES:
            times = []
            for size in sizes:
                if lang == "C++":
                    file = f"{RESULTS_DIR}/results_cpp_quick_{size}.txt"
                elif lang == "JavaScript":
                    file = f"{RESULTS_DIR}/results_javascript_quick_{size}.txt"
                else:
                    file = f"{RESULTS_DIR}/results_{lang.lower()}_quick_{size}.txt"
                t = extract_time(file)
                times.append(t)
            if all(t is not None for t in times):
                time_values = [f"{t:.6f}" if t != "N/A" else "N/A" for t in times]
                # Only compute ratios if enough sizes are present
                ratio_1 = f"{times[1] / times[0]:.1f}x" if len(times) > 1 and times[0] not in (None, "N/A") and times[1] not in (None, "N/A") and times[0] > 0 else "N/A"
                ratio_2 = f"{times[2] / times[1]:.1f}x" if len(times) > 2 and times[1] not in (None, "N/A") and times[2] not in (None, "N/A") and times[1] > 0 else "N/A"
                ratio_3 = f"{times[3] / times[2]:.1f}x" if len(times) > 3 and times[2] not in (None, "N/A") and times[3] not in (None, "N/A") and times[2] > 0 else "N/A"
                md.write(f"| {lang} | {' | '.join(time_values)} | ")
                if len(times) >= 4:
                    md.write(f"{ratio_1} | {ratio_2} | {ratio_3} |")
                md.write("\n")
            else:
                md.write(f"| {lang} | " + " | ".join(["-" for _ in times]) + " | " + ("- | - | - |" if len(times) >= 4 else "") + "\n")
        md.write("\n### Key Observations\n\n1. **Algorithm Efficiency:** O(n log n) and O(n) algorithms show significantly better scaling\n2. **Quadratic Impact:** Bubble, Selection and Insertion sorts deteriorate rapidly with size\n3. **Linear Algorithms:** Counting and Radix sorts maintain consistent ratios as size increases\n4. **Language Overhead:** Low-level languages maintain better scaling characteristics\n5. **Small Dataset Impact:** With N=10, implementation details outweigh algorithmic differences\n\n")
        md.write("## Conclusions\n\n### Algorithm Performance Hierarchy\n\nBased on the multi-size analysis:\n\n1. **O(n) Algorithms:** Counting Sort and Radix Sort perform best for large datasets with limited range\n2. **O(n log n) Algorithms:** Quick Sort and Merge Sort provide excellent general-purpose performance\n3. **O(n²) Algorithms:** Bubble, Selection, and Insertion sorts are only suitable for tiny datasets\n\n### Language Performance Hierarchy\n\n1. **C/C++:** Consistently fastest across all dataset sizes due to native compilation and memory efficiency\n2. **Go:** Excellent performance with simple concurrency model\n3. **Java:** Strong JIT-optimized performance with good scaling characteristics\n4. **JavaScript:** Surprisingly efficient V8 optimization, especially for medium-sized datasets\n5. **Python:** Convenient but slower due to interpreter overhead\n\n### Practical Recommendations\n\n| Dataset Size | Recommended Algorithm | Recommended Language | Reasoning |\n|--------------|----------------------|---------------------|-----------|\n| **Tiny (N < 100)** | Insertion Sort | Any language | Low overhead for nearly sorted data |\n| **Small (N < 10K)** | Quick Sort | Any language | Performance differences negligible |\n| **Medium (N ~ 100K)** | Quick Sort | C++ or Go | Good balance of performance and simplicity |\n| **Large (N > 1M)** | Counting/Radix Sort* | C | Maximum performance for memory-intensive operations |\n| **Very Large (N > 10M)** | Merge Sort | C++ | Better stability and consistent performance |\n\n*When data range is limited\n\n---\n\n**Study Generated:** {datetime.now()}  \n**Total Test Executions:** {len(algorithms) * len(sizes) * 6} (7 algorithms × 4 dataset sizes × 6 languages)  \n**Files Generated:** {len(algorithms) * len(sizes) * 6} result files + {len(sizes)} consolidated reports + this analysis\n\n")
    print(f"Comprehensive analysis saved to {md_path}")

def run_algorithm_for_size(algo, algo_name, size, size_name):
    print(f"--------------------------------------------")
    print(f"Testing {algo_name} with dataset size: {size} ({size_name})")
    print(f"--------------------------------------------")
    dataset_file = f"{DATASET_DIR}/random_list_{size}.txt"
    if not os.path.isfile(dataset_file):
        print(f"Error: Dataset file {dataset_file} not found")
        return
    if algo in ["bubble", "insertion", "selection"] and size > 10000:
        print(f"Skipping {algo_name} for large dataset (N={size})...")
        for lang in ["python", "cpp", "java", "javascript", "go", "c"]:
            with open(f"{RESULTS_DIR}/results_{lang}_{algo}_{size}.txt", "w") as f:
                f.write("Execution time: N/A (skipped for large dataset)\n")
        print(f"Created N/A result files for {algo_name} (N={size})\n")
        return
    print(f"Running Python {algo_name} (N={size})...")
    subprocess.run(["python3", f"{SRC_DIR}/{algo}_sort.py", "--datafile", dataset_file, "--results", f"{RESULTS_DIR}/results_python_{algo}_{size}.txt"], check=False)
    print(f"Compiling and running C++ {algo_name} (N={size})...")
    cpp_src = f"{SRC_DIR}/{algo}_sort.cpp"
    cpp_bin = f"{SRC_DIR}/{algo}_sort_cpp"
    if os.path.exists(cpp_src):
        if subprocess.run(["g++", "-O2", "-std=c++17", "-o", cpp_bin, cpp_src], check=False).returncode == 0:
            subprocess.run([cpp_bin, dataset_file, f"{RESULTS_DIR}/results_cpp_{algo}_{size}.txt"], check=False)
    print(f"Compiling and running Java {algo_name} (N={size})...")
    java_class = algo.capitalize() + "Sort"
    java_src = f"{SRC_DIR}/{java_class}.java"
    if os.path.exists(java_src):
        if subprocess.run(["javac", java_src], check=False).returncode == 0:
            subprocess.run(["java", "-cp", SRC_DIR, java_class, dataset_file, f"{RESULTS_DIR}/results_java_{algo}_{size}.txt"], check=False)
    print(f"Running JavaScript {algo_name} (N={size})...")
    js_src = f"{SRC_DIR}/{algo}_sort.js"
    if os.path.exists(js_src):
        subprocess.run(["node", js_src, dataset_file, f"{RESULTS_DIR}/results_javascript_{algo}_{size}.txt"], check=False)
    print(f"Running Go {algo_name} (N={size})...")
    go_src = f"{SRC_DIR}/{algo}_sort.go"
    if os.path.exists(go_src):
        subprocess.run(["/opt/homebrew/bin/go", "run", go_src, "-file", dataset_file, "-output", f"{RESULTS_DIR}/results_go_{algo}_{size}.txt"], check=False)
    print(f"Compiling and running C {algo_name} (N={size})...")
    c_src = f"{SRC_DIR}/{algo}_sort.c"
    c_bin = f"{SRC_DIR}/{algo}_sort_c"
    if os.path.exists(c_src):
        if subprocess.run(["gcc", "-O2", "-o", c_bin, c_src], check=False).returncode == 0:
            subprocess.run([c_bin, dataset_file, f"{RESULTS_DIR}/results_c_{algo}_{size}.txt"], check=False)
    print("")

def run_tests_for_size(size, name, algorithms, algorithm_names):
    print(f"============================================")
    print(f"Testing with dataset size: {size} ({name})")
    print(f"============================================\n")
    dataset_file = f"{DATASET_DIR}/random_list_{size}.txt"
    if not os.path.isfile(dataset_file):
        print(f"Error: Dataset file {dataset_file} not found")
        return
    for algo, algo_name in zip(algorithms, algorithm_names):
        run_algorithm_for_size(algo, algo_name, size, name)
    os.makedirs(RESULTS_DIR, exist_ok=True)
    print(f"Generating analysis for size {size}...")
    generate_analysis_for_size(size, name, algorithms, algorithm_names)
    print("")

def main():
    parser = argparse.ArgumentParser(description="Multi-Size Sorting Algorithms Performance Comparison Study")
    parser.add_argument('--sizes', nargs='+', type=int, default=None, help='List of dataset sizes to test (overrides config file)')
    parser.add_argument('--algorithms', nargs='+', type=str, default=DEFAULT_ALGORITHMS, help='List of algorithms to test')
    args = parser.parse_args()
    if args.sizes is not None:
        sizes = args.sizes
    else:
        sizes = read_sizes_from_config()
    size_names = get_size_names(sizes)
    algorithms = args.algorithms
    algorithm_names = [DEFAULT_ALGORITHM_NAMES[DEFAULT_ALGORITHMS.index(a)] for a in algorithms]
    print("===========================================================")
    print("Multi-Size Sorting Algorithms Performance Comparison Study")
    print(f"Testing datasets: N = {', '.join(map(str, sizes))}")
    print(f"Testing algorithms: {', '.join(algorithm_names)}")
    print("===========================================================\n")
    ensure_directories()
    clean_previous_results()
    generate_datasets()
    print("Starting multi-size performance comparison...\n")
    for size, name in zip(sizes, size_names):
        run_tests_for_size(size, name, algorithms, algorithm_names)
    generate_comprehensive_analysis(sizes, size_names, algorithms, algorithm_names)
    print("\nCleaning up compiled files...")
    for ext in ["_sort_cpp", ".class", "_sort_c"]:
        for f in os.listdir(SRC_DIR):
            if f.endswith(ext):
                os.remove(os.path.join(SRC_DIR, f))
    print("\n===========================================================")
    print("Multi-Size Sorting Algorithm Performance Comparison Study COMPLETED!")
    print("===========================================================\n")
    print("Generated Files:")
    print(f"- Dataset files: {', '.join([f'random_list_{s}.txt' for s in sizes])}")
    print("- Result files: results_*_*.txt")
    print("- Consolidated reports: consolidated_results_*.txt")
    print("- Comprehensive study: docs/MULTI_SIZE_PERFORMANCE_STUDY.md\n")
    print("Performance comparison across all sorting algorithms and dataset sizes completed successfully!")

if __name__ == "__main__":
    main() 