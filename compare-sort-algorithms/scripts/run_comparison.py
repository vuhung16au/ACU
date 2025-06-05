#!/usr/bin/env python3
"""
Sorting Algorithms Performance Comparison Script (Python version)
This script runs different sorting algorithm implementations in Python, C++, Java, JavaScript, Go, and C
and compares their performance.
"""
import os
import sys
import shutil
import subprocess
import argparse
from datetime import datetime

# --- Configuration ---
ALGORITHMS = ["bubble", "selection", "insertion", "quick", "merge", "counting", "radix"]
ALGORITHM_NAMES = [
    "Bubble Sort", "Selection Sort", "Insertion Sort", "Quick Sort",
    "Merge Sort", "Counting Sort", "Radix Sort"
]
LANGUAGES = ["Python", "C++", "Java", "JavaScript", "Go", "C"]

# --- Argument Parsing ---
def parse_args():
    parser = argparse.ArgumentParser(add_help=False, description="Sorting Algorithms Performance Comparison")
    parser.add_argument("--size", type=str, default=None, help="Comma-separated list of data sizes to test (e.g., 100000,250000). If not specified, uses config/number-of-data-points.txt")
    parser.add_argument("--algorithm", type=str, default=None, help="Comma-separated list of algorithms to test (e.g., quick,merge)")
    parser.add_argument("--language", type=str, default=None, help="Comma-separated list of languages to test (e.g., cpp,java)")
    parser.add_argument("--generate-data", type=str, default="False", help="Whether to re-generate data before running (Y/Yes/True to enable, default: False)")
    parser.add_argument("--repeat", type=int, default=1, help="Number of times to repeat each test and average the results (default: 1)")
    parser.add_argument("--help", action="store_true", help="Show this help message and exit")
    return parser.parse_args(), parser

# --- Utility Functions ---
def run_cmd(cmd, cwd=None, shell=False, check=False):
    try:
        result = subprocess.run(cmd, cwd=cwd, shell=shell, check=check, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)
        return result.returncode, result.stdout, result.stderr
    except Exception as e:
        return 1, '', str(e)

def ensure_dir(path):
    os.makedirs(path, exist_ok=True)

def clean_files(base_dir, data_size):
    # Remove previous results and compiled files
    for pattern in [
        f"{base_dir}/results/results_*.txt",
        f"{base_dir}/results/*.md",
        f"{base_dir}/src/*_sort_cpp",
        f"{base_dir}/src/*.class",
        f"{base_dir}/src/*_sort_c"
    ]:
        for file in list_files(pattern):
            try:
                os.remove(file)
            except Exception:
                pass

def list_files(pattern):
    import glob
    return glob.glob(pattern)

# --- Main Logic ---
def main():
    args, parser = parse_args()
    if args.help:
        print("""
Sorting Algorithms Performance Comparison Script

Usage:
  python scripts/run_comparison.py [OPTIONS]

Options:
  --size SIZE1,SIZE2     Comma-separated list of data sizes to test (e.g., 100000,250000). If not specified, uses config/number-of-data-points.txt
  --algorithm ALGOS      Comma-separated list of algorithms to test (e.g., quick,merge). Default: all
  --language LANGS       Comma-separated list of languages to test (e.g., cpp,java). Default: all
  --generate-data Y/N    Whether to re-generate data before running (Y/Yes/True to enable, default: False)
  --repeat N             Number of times to repeat each test and average the results (default: 1)
  --help                 Show this help message and exit

Examples:
  python scripts/run_comparison.py --algorithm=counting --language=cpp,java --size=250000,500000
  python scripts/run_comparison.py --generate-data=Y --repeat=5
  python scripts/run_comparison.py --help

Available algorithms: bubble, selection, insertion, quick, merge, counting, radix
Available languages: python, cpp, java, javascript, go, c
""")
        return
    BASE_DIR = os.path.abspath(os.path.join(os.path.dirname(__file__), '..'))
    generate_data_flag = str(args.generate_data).strip().lower() in ("y", "yes", "true")
    REPEAT = args.repeat
    # Determine data sizes
    if args.size:
        try:
            DATA_SIZES = [int(s.strip()) for s in args.size.split(',') if s.strip()]
        except ValueError:
            print("Error: --size must be a comma-separated list of integers.")
            sys.exit(1)
    else:
        # Read from config/number-of-data-points.txt
        config_file = os.path.join(BASE_DIR, 'config', 'number-of-data-points.txt')
        try:
            with open(config_file, 'r') as f:
                DATA_SIZES = [int(line.strip()) for line in f if line.strip() and not line.strip().startswith('//')]
        except FileNotFoundError:
            print(f"Error: {config_file} not found. Please create this file with the desired dataset sizes (one per line). Aborting.")
            sys.exit(1)
    # Parse algorithm filter
    if args.algorithm:
        algos = [a.strip().lower() for a in args.algorithm.split(',') if a.strip()]
        selected_algos = [a for a in ALGORITHMS if a in algos]
        selected_algo_names = [ALGORITHM_NAMES[ALGORITHMS.index(a)] for a in selected_algos]
    else:
        selected_algos = ALGORITHMS
        selected_algo_names = ALGORITHM_NAMES
    # Parse language filter
    lang_map = {
        'python': 'Python', 'cpp': 'C++', 'java': 'Java', 'javascript': 'JavaScript', 'go': 'Go', 'c': 'C'
    }
    if args.language:
        langs = [l.strip().lower() for l in args.language.split(',') if l.strip()]
        selected_langs = [lang_map[l] for l in langs if l in lang_map]
    else:
        selected_langs = LANGUAGES
    print("==========================================")
    print("Sorting Algorithms Performance Comparison")
    print("==========================================\n")
    print(f"Base directory: {BASE_DIR}")
    print(f"Data sizes: {', '.join(str(s) for s in DATA_SIZES)}")
    print(f"Algorithms: {', '.join(selected_algo_names)}")
    print(f"Languages: {', '.join(selected_langs)}")
    
    # Create docs directory if it doesn't exist
    ensure_dir(f"{BASE_DIR}/docs")
    
    for DATA_SIZE in DATA_SIZES:
        print(f"\n--- Running for data size: {DATA_SIZE} ---\n")
        ensure_dir(f"{BASE_DIR}/results")
        ensure_dir(f"{BASE_DIR}/analysis")
        clean_files(BASE_DIR, DATA_SIZE)
        print("Cleaning up previous results...\n")
        if generate_data_flag:
            print("Step 1: Generating random data...")
            ret, out, err = run_cmd([sys.executable, "generate_data.py"], cwd=f"{BASE_DIR}/scripts")
            if ret != 0:
                print("Error: Failed to generate random data")
                print(err)
                sys.exit(1)
            print("")
        else:
            print("Step 1: Skipping data generation (use --generate-data to enable)")
        # Step 2: Run/compile all algorithms in all languages
        def run_algorithm_tests(algo, algo_name):
            print("============================================")
            print(f"Testing {algo_name} Implementation")
            print("============================================\n")
            dataset_file = f"{BASE_DIR}/datasets/random_list_{DATA_SIZE}.txt"
            # Skip bubble, insertion, selection for large datasets
            if algo in ("bubble", "insertion", "selection") and DATA_SIZE > 10000:
                print(f"Skipping {algo_name} for large dataset (N={DATA_SIZE})...")
                for lang in [l.lower() for l in selected_langs]:
                    with open(f"{BASE_DIR}/results/results_{lang}_{algo}_{DATA_SIZE}.txt", "w") as f:
                        f.write("Execution time: N/A (skipped for large dataset)\n")
                print(f"Created N/A result files for {algo_name} (N={DATA_SIZE})\n")
                return
            # Helper to run and time a command N times
            import time
            def repeat_and_time(cmd, cwd=None):
                times = []
                outs = []
                errs = []
                for _ in range(REPEAT):
                    start = time.time()
                    ret, out, err = run_cmd(cmd, cwd=cwd)
                    elapsed = time.time() - start
                    times.append(elapsed)
                    outs.append(out)
                    errs.append(err)
                return times, outs, errs
            # Python
            if 'Python' in selected_langs:
                print(f"Running Python {algo_name}...")
                times, outs, errs = repeat_and_time([
                    sys.executable, f"{BASE_DIR}/src/{algo}_sort.py",
                    "-c", f"{BASE_DIR}/config/number-of-data-points.txt",
                    "-d", dataset_file,
                    "-r", f"{BASE_DIR}/results/results_python_{algo}_{DATA_SIZE}.txt"
                ])
                avg_time = sum(times) / len(times)
                with open(f"{BASE_DIR}/results/results_python_{algo}_{DATA_SIZE}.txt", "w") as f:
                    f.write(f"Execution times: {', '.join(f'{t:.6f}' for t in times)} seconds\n")
                    f.write(f"Average execution time: {avg_time:.6f} seconds\n")
                if any(errs):
                    print(f"Warning: Python {algo_name} implementation failed\n{errs}")
                print("")
            # C++
            if 'C++' in selected_langs:
                print(f"Compiling and running C++ {algo_name}...")
                cpp_src = f"{BASE_DIR}/src/{algo}_sort.cpp"
                cpp_bin = f"{BASE_DIR}/src/{algo}_sort_cpp"
                if os.path.exists(cpp_src):
                    ret, out, err = run_cmd(["g++", "-O2", "-std=c++17", "-o", cpp_bin, cpp_src])
                    if ret != 0:
                        print(f"Warning: C++ {algo_name} compilation failed\n{err}")
                    else:
                        times, outs, errs = repeat_and_time([
                            cpp_bin,
                            "-c", f"{BASE_DIR}/config/number-of-data-points.txt",
                            "-d", dataset_file,
                            "-r", f"{BASE_DIR}/results/results_cpp_{algo}_{DATA_SIZE}.txt"
                        ])
                        avg_time = sum(times) / len(times)
                        with open(f"{BASE_DIR}/results/results_cpp_{algo}_{DATA_SIZE}.txt", "w") as f:
                            f.write(f"Execution times: {', '.join(f'{t:.6f}' for t in times)} seconds\n")
                            f.write(f"Average execution time: {avg_time:.6f} seconds\n")
                        if any(errs):
                            print(f"Warning: C++ {algo_name} implementation failed\n{errs}")
                print("")
            # Java
            if 'Java' in selected_langs:
                print(f"Compiling and running Java {algo_name}...")
                java_class = algo.capitalize() + "Sort"
                java_src = f"{BASE_DIR}/src/{java_class}.java"
                if os.path.exists(java_src):
                    ret, out, err = run_cmd(["javac", java_src], cwd=BASE_DIR)
                    if ret != 0:
                        print(f"Warning: Java {algo_name} compilation failed\n{err}")
                    else:
                        times, outs, errs = repeat_and_time([
                            "java", "-cp", "src", java_class,
                            "-c", f"{BASE_DIR}/config/number-of-data-points.txt",
                            "-d", f"datasets/random_list_{DATA_SIZE}.txt",
                            "-r", f"results/results_java_{algo}_{DATA_SIZE}.txt"
                        ], cwd=BASE_DIR)
                        avg_time = sum(times) / len(times)
                        with open(f"{BASE_DIR}/results/results_java_{algo}_{DATA_SIZE}.txt", "w") as f:
                            f.write(f"Execution times: {', '.join(f'{t:.6f}' for t in times)} seconds\n")
                            f.write(f"Average execution time: {avg_time:.6f} seconds\n")
                        if any(errs):
                            print(f"Warning: Java {algo_name} implementation failed\n{errs}")
                print("")
            # JavaScript
            if 'JavaScript' in selected_langs:
                print(f"Running JavaScript {algo_name}...")
                js_src = f"{BASE_DIR}/src/{algo}_sort.js"
                if os.path.exists(js_src):
                    times, outs, errs = repeat_and_time([
                        "node", js_src,
                        "-c", f"{BASE_DIR}/config/number-of-data-points.txt",
                        "-d", dataset_file,
                        "-r", f"{BASE_DIR}/results/results_javascript_{algo}_{DATA_SIZE}.txt"
                    ], cwd=BASE_DIR)
                    avg_time = sum(times) / len(times)
                    with open(f"{BASE_DIR}/results/results_javascript_{algo}_{DATA_SIZE}.txt", "w") as f:
                        f.write(f"Execution times: {', '.join(f'{t:.6f}' for t in times)} seconds\n")
                        f.write(f"Average execution time: {avg_time:.6f} seconds\n")
                    if any(errs):
                        print(f"Warning: JavaScript {algo_name} implementation failed\n{errs}")
                print("")
            # Go
            if 'Go' in selected_langs:
                print(f"Running Go {algo_name}...")
                go_src = f"{BASE_DIR}/src/{algo}_sort.go"
                if os.path.exists(go_src):
                    times, outs, errs = repeat_and_time([
                        "go", "run", go_src,
                        "-file", dataset_file,
                        "-output", f"{BASE_DIR}/results/results_go_{algo}_{DATA_SIZE}.txt"
                    ], cwd=BASE_DIR)
                    avg_time = sum(times) / len(times)
                    with open(f"{BASE_DIR}/results/results_go_{algo}_{DATA_SIZE}.txt", "w") as f:
                        f.write(f"Execution times: {', '.join(f'{t:.6f}' for t in times)} seconds\n")
                        f.write(f"Average execution time: {avg_time:.6f} seconds\n")
                    if any(errs):
                        print(f"Warning: Go {algo_name} implementation failed\n{errs}")
                print("")
            # C
            if 'C' in selected_langs:
                print(f"Compiling and running C {algo_name}...")
                c_src = f"{BASE_DIR}/src/{algo}_sort.c"
                c_bin = f"{BASE_DIR}/src/{algo}_sort_c"
                if os.path.exists(c_src):
                    ret, out, err = run_cmd(["gcc", "-O2", "-o", c_bin, c_src])
                    if ret != 0:
                        print(f"Warning: C {algo_name} compilation failed\n{err}")
                    else:
                        times, outs, errs = repeat_and_time([
                            c_bin,
                            "-c", f"{BASE_DIR}/config/number-of-data-points.txt",
                            "-d", dataset_file,
                            "-r", f"{BASE_DIR}/results/results_c_{algo}_{DATA_SIZE}.txt"
                        ])
                        avg_time = sum(times) / len(times)
                        with open(f"{BASE_DIR}/results/results_c_{algo}_{DATA_SIZE}.txt", "w") as f:
                            f.write(f"Execution times: {', '.join(f'{t:.6f}' for t in times)} seconds\n")
                            f.write(f"Average execution time: {avg_time:.6f} seconds\n")
                        if any(errs):
                            print(f"Warning: C {algo_name} implementation failed\n{errs}")
                print("")

        # Run all algorithms
        for algo, algo_name in zip(selected_algos, selected_algo_names):
            run_algorithm_tests(algo, algo_name)

        # Step 3: Compile results
        print("Step 8: Compiling results...")
        print("==========================================")
        print("Performance Comparison Results")
        print("==========================================\n")
        consolidated_file = f"{BASE_DIR}/analysis/consolidated_results_{DATA_SIZE}.txt"
        with open(consolidated_file, "w") as f:
            f.write("Sorting Algorithms Performance Comparison Results\n")
            f.write("=========================================\n")
            f.write(f"Date: {datetime.now()}\n")
            f.write(f"Data Size: {DATA_SIZE:,} integers\n\n")
            f.write("Individual Results:\n")

        # Helper to append results
        def append_results(lang, prefix):
            if lang not in selected_langs:
                return
            print(f"{lang} Results:")
            for algo, algo_name in zip(selected_algos, selected_algo_names):
                result_file = f"{BASE_DIR}/results/{prefix}{algo}_{DATA_SIZE}.txt"
                if os.path.exists(result_file):
                    print(f"{algo_name}:")
                    with open(result_file) as rf:
                        print(rf.read())
                    with open(consolidated_file, "a") as cf:
                        cf.write(f"\n{lang} ({algo_name}):\n")
                        with open(result_file) as rf:
                            cf.write(rf.read())
                        cf.write("\n")
                else:
                    print(f"{lang} {algo_name} results not found")
                print("")

        append_results("Python", "results_python_")
        append_results("C++", "results_cpp_")
        append_results("Java", "results_java_")
        append_results("JavaScript", "results_javascript_")
        append_results("Go", "results_go_")
        append_results("C", "results_c_")

        # Step 4: Performance summary (reuse the Python code from the shell script)
        print("Performance Summary:")
        print("===================")
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
        algorithm_display = {algo: name for algo, name in zip(ALGORITHMS, ALGORITHM_NAMES)}
        results = []
        for lang in LANGUAGES:
            if lang not in selected_langs:
                continue
            for algo in selected_algos:
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
                filename = f"{BASE_DIR}/results/{prefix}{algo}_{DATA_SIZE}.txt"
                time = extract_time(filename)
                if time is not None:
                    display_name = algorithm_display.get(algo, algo.capitalize() + ' Sort')
                    results.append((f"{lang} ({display_name})", time))
        if results:
            numeric_results = [r for r in results if r[1] != "N/A"]
            na_results = [r for r in results if r[1] == "N/A"]
            if numeric_results:
                numeric_results.sort(key=lambda x: x[1])
                fastest_time = numeric_results[0][1]
            sorted_results = numeric_results + na_results
            print(f"{'Language/Algorithm':<30} {'Time (sec)':<12} {'Relative Speed':<15}")
            print("-" * 65)
            for lang, time in sorted_results:
                if time == "N/A":
                    print(f"{lang:<30} {'N/A':<12} {'N/A':<15}")
                else:
                    relative = time / fastest_time
                    print(f"{lang:<30} {time:<12.6f} {relative:<15.2f}x")
            with open(consolidated_file, 'a') as f:
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
            print("\nAlgorithm Comparison Within Languages:")
            print("-" * 65)
            for language in LANGUAGES:
                if language not in selected_langs:
                    continue
                lang_results = []
                for algo in selected_algos:
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
                    filename = f"{BASE_DIR}/results/{prefix}{algo}_{DATA_SIZE}.txt"
                    time = extract_time(filename)
                    if time is not None:
                        display_name = algorithm_display.get(algo, algo.capitalize() + ' Sort')
                        lang_results.append((display_name, time))
                if lang_results:
                    numeric_results = [r for r in lang_results if r[1] != "N/A"]
                    na_results = [r for r in lang_results if r[1] == "N/A"]
                    if numeric_results:
                        numeric_results.sort(key=lambda x: x[1])
                        fastest_time = numeric_results[0][1]
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
                    with open(consolidated_file, 'a') as f:
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
        print("")
        print(f"All results saved to analysis/consolidated_results_{DATA_SIZE}.txt\n")
        # Move comparison markdown generation BEFORE clean_files
        # After all summary/printing, generate a comparison markdown file
        dt_str = datetime.now().strftime('%Y-%m-%d-%H%M%S')
        comparison_path = f"{BASE_DIR}/results/comparison-{dt_str}.md"
        algo_results = {}
        for algo in selected_algos:
            algo_results[algo] = []
            for lang in LANGUAGES:
                if lang not in selected_langs:
                    continue
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
                filename = f"{BASE_DIR}/results/{prefix}{algo}_{DATA_SIZE}.txt"
                avg_time = None
                if os.path.exists(filename):
                    with open(filename) as f:
                        lines = f.readlines()
                        for line in lines:
                            if line.lower().startswith('average execution time:'):
                                try:
                                    avg_time = float(line.split(':')[1].strip().split()[0])
                                except Exception:
                                    avg_time = None
                                break
                if avg_time is not None:
                    algo_results[algo].append((lang, avg_time))
        with open(comparison_path, 'w') as f:
            f.write(f"# Sorting Algorithms Performance Comparison\n\n")
            f.write(f"Date: {dt_str}\n\n")
            f.write(f"## Data Size: {DATA_SIZE:,} elements\n\n")
            for algo in selected_algos:
                if not algo_results[algo]:
                    continue
                display_name = ALGORITHM_NAMES[ALGORITHMS.index(algo)]
                f.write(f"### {display_name} Performance Comparison\n\n")
                f.write(f"This report compares the average run time of {display_name} across all languages for {DATA_SIZE:,} elements.\n\n")
                sorted_results = sorted(algo_results[algo], key=lambda x: x[1])
                fastest = sorted_results[0][1]
                slowest = sorted_results[-1][1]
                
                # Write the new table format
                f.write("| Rank | Language | Time (seconds) | Relative Speed | Elements/Second |\n")
                f.write("|------|----------|----------------|----------------|----------------|\n")
                
                for rank, (lang, t) in enumerate(sorted_results, 1):
                    relative_speed = f"{t/fastest:.2f}x"
                    elements_per_second = f"{DATA_SIZE/t:,.0f}"
                    time_str = f"{t:<.6f}"
                    if t == fastest:
                        time_str = f"🟢 {time_str}"
                    elif t == slowest:
                        time_str = f"🔴 {time_str}"
                    f.write(f"| {rank} | {lang:<8} | {time_str} | {relative_speed:<14} | {elements_per_second} |\n")
                
                f.write("\n")
                f.write(f"- 🟢 **Fastest**: {sorted_results[0][0]}\n")
                f.write(f"- 🔴 **Slowest**: {sorted_results[-1][0]}\n\n")
                f.write("Lower run time is better (faster). Higher run time is worse (slower).\n\n---\n\n")
                f.write("**Raw Data:**\n\n")
                for lang, t in sorted_results:
                    f.write(f"- {lang}: {t:.6f} s\n")
                f.write(f"\n*Generated: {dt_str}*\n\n")
        print(f"Comparison markdown generated: {comparison_path}\n")
        # Clean up compiled files
        clean_files(BASE_DIR, DATA_SIZE)
        print("==========================================")
        print("Performance comparison completed!")

if __name__ == "__main__":
    main() 