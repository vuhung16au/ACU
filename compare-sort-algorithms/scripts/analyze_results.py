#!/usr/bin/env python3
"""
Performance Analysis and Reporting Script
Analyzes the sorting algorithms performance results and generates comprehensive insights.
"""
import os
import re
import statistics

class PerformanceAnalyzer:
    def __init__(self):
        self.results = {}
        self.load_results()
    
    def get_result_file(self, lang, algo, size):
        # Compose the expected filename patterns
        base = f"results_{lang.lower()}_{algo.lower()}_{size}"
        avg_file = os.path.join("../results", f"{base}_avg.txt")
        single_file = os.path.join("../results", f"{base}.txt")
        if os.path.exists(avg_file):
            return avg_file
        elif os.path.exists(single_file):
            return single_file
        else:
            return None

    def load_results(self):
        """Load performance results from all result files, preferring *_avg.txt files."""
        # Define the mapping of language/algorithm to (lang, algo) and expected size
        # For demonstration, let's assume size is provided or fixed (e.g., 500000)
        # In practice, you may want to pass size as an argument or detect it
        size = 500000  # <-- You may want to make this dynamic
        lang_algo_map = {
            'Python (Bubble Sort)':    ('python', 'bubble'),
            'Python (Selection Sort)': ('python', 'selection'),
            'Python (Insertion Sort)': ('python', 'insertion'),
            'Python (Quick Sort)':     ('python', 'quick'),
            'Python (Merge Sort)':     ('python', 'merge'),
            'Python (Counting Sort)':  ('python', 'counting'),
            'Python (Radix Sort)':     ('python', 'radix'),
            'C++ (Bubble Sort)':       ('cpp', 'bubble'),
            'C++ (Selection Sort)':    ('cpp', 'selection'),
            'C++ (Insertion Sort)':    ('cpp', 'insertion'),
            'C++ (Quick Sort)':        ('cpp', 'quick'),
            'C++ (Merge Sort)':        ('cpp', 'merge'),
            'C++ (Counting Sort)':     ('cpp', 'counting'),
            'C++ (Radix Sort)':        ('cpp', 'radix'),
            'Java (Bubble Sort)':      ('java', 'bubble'),
            'Java (Selection Sort)':   ('java', 'selection'),
            'Java (Insertion Sort)':   ('java', 'insertion'),
            'Java (Quick Sort)':       ('java', 'quick'),
            'Java (Merge Sort)':       ('java', 'merge'),
            'Java (Counting Sort)':    ('java', 'counting'),
            'Java (Radix Sort)':       ('java', 'radix'),
            'JavaScript (Bubble Sort)':    ('javascript', 'bubble'),
            'JavaScript (Selection Sort)': ('javascript', 'selection'),
            'JavaScript (Insertion Sort)': ('javascript', 'insertion'),
            'JavaScript (Quick Sort)':     ('javascript', 'quick'),
            'JavaScript (Merge Sort)':     ('javascript', 'merge'),
            'JavaScript (Counting Sort)':  ('javascript', 'counting'),
            'JavaScript (Radix Sort)':     ('javascript', 'radix'),
            'Go (Bubble Sort)':        ('go', 'bubble'),
            'Go (Selection Sort)':     ('go', 'selection'),
            'Go (Insertion Sort)':     ('go', 'insertion'),
            'Go (Quick Sort)':         ('go', 'quick'),
            'Go (Merge Sort)':         ('go', 'merge'),
            'Go (Counting Sort)':      ('go', 'counting'),
            'Go (Radix Sort)':         ('go', 'radix'),
            'C (Bubble Sort)':         ('c', 'bubble'),
            'C (Selection Sort)':      ('c', 'selection'),
            'C (Insertion Sort)':      ('c', 'insertion'),
            'C (Quick Sort)':          ('c', 'quick'),
            'C (Merge Sort)':          ('c', 'merge'),
            'C (Counting Sort)':       ('c', 'counting'),
            'C (Radix Sort)':          ('c', 'radix'),
        }
        for label, (lang, algo) in lang_algo_map.items():
            result_file = self.get_result_file(lang, algo, size)
            if result_file:
                self.results[label] = self.parse_result_file(result_file)
    
    def parse_result_file(self, filename):
        """Parse a result file and extract performance metrics."""
        with open(filename, 'r') as f:
            content = f.read()
        
        result = {}
        
        # Check if the result contains N/A
        if "N/A" in content:
            result['execution_time'] = "N/A"
        else:
            # Extract execution time
            time_match = re.search(r'Execution time: ([\d.]+) seconds', content)
            if time_match:
                result['execution_time'] = float(time_match.group(1))
        
        # Extract data size
        size_match = re.search(r'Data size: (\d+)', content)
        if size_match:
            result['data_size'] = int(size_match.group(1))
        
        # Extract elements per second
        eps_match = re.search(r'Elements per second: ([\d.]+)', content)
        if eps_match:
            result['elements_per_second'] = float(eps_match.group(1))
        
        # Extract correctness
        correct_match = re.search(r'Sorted correctly: (true|True|false|False)', content)
        if correct_match:
            result['sorted_correctly'] = correct_match.group(1).lower() == 'true'
        
        return result
    
    def generate_analysis(self):
        """Generate comprehensive analysis of the results."""
        if not self.results:
            return "No results found to analyze."
        
        analysis = []
        analysis.append("SORTING ALGORITHMS PERFORMANCE ANALYSIS")
        analysis.append("=" * 50)
        analysis.append("")
        
        # Basic statistics
        analysis.append("1. EXECUTION TIME COMPARISON")
        analysis.append("-" * 30)
        
        times = []
        for lang, data in self.results.items():
            if 'execution_time' in data:
                time = data['execution_time']
                times.append((lang, time))
                if time == "N/A":
                    analysis.append(f"{lang:<12}: N/A (skipped for large dataset)")
                else:
                    analysis.append(f"{lang:<12}: {time:.6f} seconds")
        
        analysis.append("")
        
        # Define default values for variables that need to be defined even if there are no valid times
        fastest_lang = "N/A"
        fastest_time = 0  # Using 0 as default will cause errors in division, but we'll handle that
        slowest_lang = "N/A"
        slowest_time = 0
        sorted_times = []
        
        if times:
            # Filter out N/A values for sorting
            numeric_times = [t for t in times if t[1] != "N/A"]
            na_times = [t for t in times if t[1] == "N/A"]
            
            # Sort only numeric values by performance
            if numeric_times:
                numeric_times.sort(key=lambda x: x[1])
                fastest_lang, fastest_time = numeric_times[0]
                slowest_lang, slowest_time = numeric_times[-1]
                
                # Combine sorted numeric results with N/A results at the end
                sorted_times = numeric_times + na_times
            else:
                sorted_times = na_times
            
            analysis.append("2. PERFORMANCE RANKING")
            analysis.append("-" * 25)
            analysis.append(f"{'Rank':<6} {'Language':<12} {'Time (sec)':<12} {'vs Fastest':<12}")
            analysis.append("-" * 45)
            
            for i, (lang, time) in enumerate(sorted_times, 1):
                if time == "N/A":
                    analysis.append(f"{i:<6} {lang:<12} {'N/A':<12} {'N/A':<12}")
                elif fastest_time > 0:  # Make sure we don't divide by zero
                    relative = time / fastest_time
                    analysis.append(f"{i:<6} {lang:<12} {time:<12.6f} {relative:<12.2f}x")
                else:
                    analysis.append(f"{i:<6} {lang:<12} {time:<12.6f} {'N/A':<12}")
            
            analysis.append("")
            
            # Performance insights
            analysis.append("3. PERFORMANCE INSIGHTS")
            analysis.append("-" * 25)
            
            if fastest_time != 0 and fastest_lang != "N/A":
                analysis.append(f"• Fastest: {fastest_lang} ({fastest_time:.6f} seconds)")
            else:
                analysis.append("• Fastest: N/A")
                
            if slowest_time != 0 and slowest_lang != "N/A":
                analysis.append(f"• Slowest: {slowest_lang} ({slowest_time:.6f} seconds)")
            else:
                analysis.append("• Slowest: N/A")
                
            if fastest_time > 0 and slowest_time > 0:
                analysis.append(f"• Speed difference: {slowest_time/fastest_time:.2f}x")
            else:
                analysis.append("• Speed difference: N/A")
            
            # Calculate statistics (only for numeric values)
            numeric_values = [t[1] for t in times if t[1] != "N/A"]
            if len(numeric_values) > 1:
                analysis.append(f"• Average time: {statistics.mean(numeric_values):.6f} seconds")
                if len(numeric_values) > 2:
                    analysis.append(f"• Standard deviation: {statistics.stdev(numeric_values):.6f} seconds")
            
            analysis.append("")
        
        # Language-specific analysis
        analysis.append("4. LANGUAGE AND ALGORITHM ANALYSIS")
        analysis.append("-" * 32)
        
        language_insights = {
            'Python': "Interpreted language with dynamic typing. Generally slower but highly readable and maintainable.",
            'C++': "Compiled language with manual memory management. Typically fastest due to low-level optimizations.",
            'Java': "Compiled to bytecode, runs on JVM. Good performance with automatic memory management.",
            'JavaScript': "Interpreted/JIT compiled. Performance varies by engine (V8 is highly optimized).",
            'Go': "Compiled language with garbage collection, designed for concurrency and performance.",
            'C': "Low-level compiled language with manual memory management. Typically very fast."
        }
        
        algorithm_insights = {
            'Quick Sort': "Divide-and-conquer algorithm with average O(n log n) complexity. Good for general-purpose sorting.",
            'Bubble Sort': "Simple comparison-based algorithm with O(n²) complexity. Inefficient for large datasets.",
            'Selection Sort': "Simple comparison-based algorithm with O(n²) complexity. Performs poorly on large datasets.",
            'Insertion Sort': "Simple comparison-based algorithm with O(n²) complexity. Efficient for small or nearly sorted data.",
            'Merge Sort': "Divide-and-conquer algorithm with O(n log n) complexity. Stable sort with predictable performance.",
            'Counting Sort': "Non-comparative integer sorting algorithm with O(n+k) complexity where k is the range of input.",
            'Radix Sort': "Non-comparative integer sorting algorithm with O(d*(n+k)) complexity where d is the number of digits."
        }
        
        for lang in self.results:
            analysis.append(f"\n{lang}:")
            
            # Extract base language (Python, C++, Java, etc.)
            base_lang = lang.split(' ')[0]
            analysis.append(f"  {language_insights.get(base_lang, 'No specific language insights available.')}")
            
            # Extract algorithm name if present (e.g., "C (Quick Sort)" -> "Quick Sort")
            if '(' in lang and ')' in lang:
                algo = lang[lang.find('(')+1:lang.find(')')]
                analysis.append(f"  {algorithm_insights.get(algo, 'No specific algorithm insights available.')}")
            
            if lang in self.results and 'elements_per_second' in self.results[lang]:
                eps = self.results[lang]['elements_per_second']
                analysis.append(f"  Throughput: {eps:,.0f} elements/second")
        
        analysis.append("")
        
        # Technical factors
        analysis.append("5. FACTORS AFFECTING PERFORMANCE")
        analysis.append("-" * 35)
        analysis.append("• Compilation vs Interpretation:")
        analysis.append("  - C++: Compiled to native machine code (fastest)")
        analysis.append("  - Java: Compiled to bytecode, JIT compilation")
        analysis.append("  - JavaScript: JIT compilation with V8 engine")
        analysis.append("  - Python: Interpreted (slowest)")
        analysis.append("")
        analysis.append("• Memory Management:")
        analysis.append("  - C++: Manual memory management (most efficient)")
        analysis.append("  - Java/JavaScript: Garbage collection overhead")
        analysis.append("  - Python: Reference counting + garbage collection")
        analysis.append("")
        analysis.append("• Optimization:")
        analysis.append("  - C++: Aggressive compiler optimizations (-O2)")
        analysis.append("  - Java: JVM runtime optimizations")
        analysis.append("  - JavaScript: V8 engine optimizations")
        analysis.append("  - Python: Limited optimization opportunities")
        
        analysis.append("")
        
        # Algorithm performance analysis
        analysis.append("6. ALGORITHM COMPARISON")
        analysis.append("-" * 25)
        
        # Group results by algorithm type
        algo_times = {}
        for lang, result in self.results.items():
            if 'execution_time' not in result:
                continue
                
            if '(' in lang and ')' in lang:
                algo = lang[lang.find('(')+1:lang.find(')')]
                if algo not in algo_times:
                    algo_times[algo] = []
                algo_times[algo].append((lang, result['execution_time']))
            elif lang == 'Python' or lang == 'C++' or lang == 'Java' or lang == 'JavaScript' or lang == 'Go':
                # These are all Quick Sort implementations
                if 'Quick Sort' not in algo_times:
                    algo_times['Quick Sort'] = []
                algo_times['Quick Sort'].append((lang, result['execution_time']))
        
        # Analyze algorithm performance
        for algo, times_list in algo_times.items():
            if times_list:
                analysis.append(f"\n{algo} Performance:")
                
                # Filter out N/A values for sorting and statistics
                numeric_times = [t for t in times_list if t[1] != "N/A"]
                na_times = [t for t in times_list if t[1] == "N/A"]
                
                # Only sort and calculate stats if we have numeric values
                if numeric_times:
                    numeric_times.sort(key=lambda x: x[1])  # Sort by execution time
                    fastest = numeric_times[0]
                    slowest = numeric_times[-1]
                    
                    analysis.append(f"  • Fastest: {fastest[0]} ({fastest[1]:.6f} seconds)")
                    analysis.append(f"  • Slowest: {slowest[0]} ({slowest[1]:.6f} seconds)")
                    analysis.append(f"  • Speed difference: {slowest[1]/fastest[1]:.2f}x")
                    
                    # Calculate statistics
                    time_values = [t[1] for t in numeric_times]
                    if len(time_values) > 1:
                        analysis.append(f"  • Average time: {statistics.mean(time_values):.6f} seconds")
                        if len(time_values) > 2:
                            analysis.append(f"  • Standard deviation: {statistics.stdev(time_values):.6f} seconds")
                
                # List any N/A entries
                if na_times:
                    analysis.append(f"  • Skipped implementations (too large): {', '.join(t[0] for t in na_times)}")
        
        analysis.append("\n")
        
        # Conclusions
        analysis.append("7. CONCLUSIONS")
        analysis.append("-" * 15)
        
        if times:
            # Language performance conclusions
            cpp_time = next((t for lang, t in times if lang == 'C++'), None)
            c_time = next((t for lang, t in times if lang.startswith('C (')), None)
            python_time = next((t for lang, t in times if lang == 'Python'), None)
            
            analysis.append("• Language Performance:")
            analysis.append("  - C & C++ typically offer the best performance due to direct compilation to machine code")
            analysis.append("  - Go provides good performance with simplified memory management")
            analysis.append("  - Java's JIT compilation offers competitive performance")
            analysis.append("  - JavaScript's V8 engine can sometimes rival compiled languages")
            analysis.append("  - Python generally has lower performance due to interpretation overhead")
            analysis.append("")
            
            # Algorithm performance conclusions
            analysis.append("• Algorithm Performance:")
            analysis.append("  - O(n log n) algorithms (Quick Sort, Merge Sort) are fastest for general-purpose sorting")
            analysis.append("  - O(n²) algorithms (Bubble, Selection, Insertion) perform poorly on large datasets")
            analysis.append("  - Non-comparative algorithms (Counting, Radix) can be very fast for specific data distributions")
            analysis.append("  - Quick Sort typically offers best average-case performance but has worst-case concerns")
            analysis.append("  - Merge Sort provides consistent performance with stable sorting guarantees")
        
        analysis.append("")
        
        # Recommendations
        analysis.append("8. RECOMMENDATIONS")
        analysis.append("-" * 20)
        analysis.append("• Language recommendations:")
        analysis.append("  - For performance-critical applications: Use C/C++")
        analysis.append("  - For balanced performance and concurrency: Use Go")
        analysis.append("  - For enterprise applications: Use Java")
        analysis.append("  - For web applications: JavaScript (Node.js) is viable")
        analysis.append("  - For rapid prototyping and development: Python")
        analysis.append("")
        analysis.append("• Algorithm recommendations:")
        analysis.append("  - General purpose sorting: Quick Sort or Merge Sort")
        analysis.append("  - Small datasets: Insertion Sort")
        analysis.append("  - When stability matters: Merge Sort")
        analysis.append("  - Integer data with limited range: Counting Sort")
        analysis.append("  - Integer data with large range: Radix Sort")
        analysis.append("")
        analysis.append("• Future improvements:")
        analysis.append("  - Test with different input patterns (sorted, reverse-sorted)")
        analysis.append("  - Compare with built-in sorting algorithms")
        analysis.append("  - Test with different data sizes")
        analysis.append("  - Profile memory usage")
        analysis.append("  - Test parallel/multi-threaded implementations")
        analysis.append("  - Optimize algorithm implementations (e.g., Hybrid sorts)")
        analysis.append("  - Analyze sorting algorithm stability")
        
        return "\n".join(analysis)
    
    def save_analysis(self, filename="../analysis/performance_analysis.txt"):
        """Save the analysis to a file."""
        # Make sure analysis directory exists
        os.makedirs(os.path.dirname(filename), exist_ok=True)
        analysis = self.generate_analysis()
        with open(filename, 'w') as f:
            f.write(analysis)
        return filename

def main():
    print("Generating performance analysis...")
    analyzer = PerformanceAnalyzer()
    
    # Print analysis to console
    analysis = analyzer.generate_analysis()
    print(analysis)
    
    # Save to file
    filename = analyzer.save_analysis()
    print(f"\nDetailed analysis saved to {filename}")

if __name__ == "__main__":
    main()
