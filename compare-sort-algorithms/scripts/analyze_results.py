#!/usr/bin/env python3
"""
Performance Analysis and Reporting Script
Analyzes the sorting algorithms performance results and generates comprehensive insights.
"""
import os
import re
import statistics

# Define base directory for proper path handling
BASE_DIR = os.path.abspath(os.path.join(os.path.dirname(__file__), '..'))

class PerformanceAnalyzer:
    def __init__(self):
        self.results = {}
        self.data_sizes = self.load_data_sizes()
        self.load_results()
    
    def load_data_sizes(self):
        """Load data sizes from config file."""
        config_file = os.path.join(BASE_DIR, "config", "number-of-data-points.txt")
        try:
            with open(config_file, 'r') as f:
                return [int(line.strip()) for line in f if line.strip()]
        except FileNotFoundError:
            print(f"Warning: Config file {config_file} not found. Using default size of 500000.")
            return [500000]
    
    def get_result_file(self, lang, algo, size):
        # Compose the expected filename patterns
        base = f"results_{lang.lower()}_{algo.lower()}_{size}"
        avg_file = os.path.join(BASE_DIR, "results", f"{base}_avg.txt")
        single_file = os.path.join(BASE_DIR, "results", f"{base}.txt")
        if os.path.exists(avg_file):
            return avg_file
        elif os.path.exists(single_file):
            return single_file
        else:
            return None

    def load_results(self):
        """Load performance results from all result files, preferring *_avg.txt files."""
        # Define the mapping of language/algorithm to (lang, algo)
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
        
        # Initialize results structure
        self.results = {size: {} for size in self.data_sizes}
        
        # Load results for each size
        for size in self.data_sizes:
            for label, (lang, algo) in lang_algo_map.items():
                result_file = self.get_result_file(lang, algo, size)
                if result_file:
                    self.results[size][label] = self.parse_result_file(result_file)
    
    def parse_result_file(self, filename):
        """Parse a result file and extract performance metrics."""
        try:
            with open(filename, 'r') as f:
                content = f.read()
            
            if not content.strip():
                return {'execution_time': "N/A", 'data_size': 0, 'elements_per_second': 0, 'sorted_correctly': False}
            
            result = {}
            
            # Check if the result contains N/A
            if "N/A" in content:
                result['execution_time'] = "N/A"
            else:
                # Extract execution time
                time_match = re.search(r'Execution time: ([\d.]+) seconds', content)
                if time_match:
                    result['execution_time'] = float(time_match.group(1))
                else:
                    result['execution_time'] = "N/A"
            
            # Extract data size
            size_match = re.search(r'Data size: (\d+)', content)
            if size_match:
                result['data_size'] = int(size_match.group(1))
            else:
                result['data_size'] = 0
            
            # Extract elements per second
            eps_match = re.search(r'Elements per second: ([\d.]+)', content)
            if eps_match:
                result['elements_per_second'] = float(eps_match.group(1))
            else:
                result['elements_per_second'] = 0
            
            # Extract correctness
            correct_match = re.search(r'Sorted correctly: (true|True|false|False)', content)
            if correct_match:
                result['sorted_correctly'] = correct_match.group(1).lower() == 'true'
            else:
                result['sorted_correctly'] = False
            
            return result
        except Exception as e:
            print(f"Warning: Error parsing {filename}: {str(e)}")
            return {'execution_time': "N/A", 'data_size': 0, 'elements_per_second': 0, 'sorted_correctly': False}
    
    def generate_analysis(self):
        """Generate comprehensive analysis of the results."""
        if not self.results:
            return "No results found to analyze."
        
        analysis = []
        analysis.append("SORTING ALGORITHMS PERFORMANCE ANALYSIS")
        analysis.append("=" * 50)
        analysis.append("")
        
        # Analyze each data size separately
        for size in self.data_sizes:
            if not self.results[size]:
                continue
                
            analysis.append(f"\nANALYSIS FOR DATA SIZE: {size:,} ELEMENTS")
            analysis.append("=" * 40)
            analysis.append("")
            
            # Basic statistics
            analysis.append("1. EXECUTION TIME COMPARISON")
            analysis.append("-" * 30)
            
            times = []
            for lang, data in self.results[size].items():
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
            fastest_time = 0
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
                    elif fastest_time > 0:
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
            
            for lang in self.results[size]:
                analysis.append(f"\n{lang}:")
                
                # Extract base language (Python, C++, Java, etc.)
                base_lang = lang.split(' ')[0]
                analysis.append(f"  {language_insights.get(base_lang, 'No specific language insights available.')}")
                
                # Extract algorithm name if present
                if '(' in lang and ')' in lang:
                    algo = lang[lang.find('(')+1:lang.find(')')]
                    analysis.append(f"  {algorithm_insights.get(algo, 'No specific algorithm insights available.')}")
                
                if lang in self.results[size] and 'elements_per_second' in self.results[size][lang]:
                    eps = self.results[size][lang]['elements_per_second']
                    analysis.append(f"  Throughput: {eps:,.0f} elements/second")
            
            analysis.append("")
        
        # Add cross-size analysis
        analysis.append("\nCROSS-SIZE PERFORMANCE ANALYSIS")
        analysis.append("=" * 35)
        analysis.append("")
        
        # Analyze performance scaling across sizes
        analysis.append("1. PERFORMANCE SCALING")
        analysis.append("-" * 25)
        
        # Group results by language/algorithm
        implementations = {}
        for size in self.data_sizes:
            for lang, data in self.results[size].items():
                if lang not in implementations:
                    implementations[lang] = []
                if 'execution_time' in data and data['execution_time'] != "N/A":
                    implementations[lang].append((size, data['execution_time']))
        
        # Analyze scaling for each implementation
        for lang, times in implementations.items():
            if len(times) > 1:
                times.sort(key=lambda x: x[0])  # Sort by size
                sizes = [t[0] for t in times]
                execution_times = [t[1] for t in times]
                
                analysis.append(f"\n{lang}:")
                for i in range(len(times)):
                    if i > 0:
                        size_ratio = sizes[i] / sizes[i-1]
                        time_ratio = execution_times[i] / execution_times[i-1]
                        analysis.append(f"  • {sizes[i-1]:,} → {sizes[i]:,} elements:")
                        analysis.append(f"    - Size increase: {size_ratio:.1f}x")
                        analysis.append(f"    - Time increase: {time_ratio:.1f}x")
                        analysis.append(f"    - Scaling factor: {time_ratio/size_ratio:.2f}")
        
        analysis.append("")
        
        # Conclusions
        analysis.append("CONCLUSIONS")
        analysis.append("=" * 15)
        
        analysis.append("• Size Impact:")
        analysis.append("  - Smaller datasets (10,000 elements):")
        analysis.append("    * All algorithms perform reasonably well")
        analysis.append("    * O(n²) algorithms are still viable")
        analysis.append("  - Medium datasets (100,000-250,000 elements):")
        analysis.append("    * O(n log n) algorithms show clear advantage")
        analysis.append("    * Some O(n²) algorithms may become impractical")
        analysis.append("  - Large datasets (500,000+ elements):")
        analysis.append("    * Only efficient algorithms are practical")
        analysis.append("    * Memory usage becomes significant")
        
        analysis.append("\n• Language Performance:")
        analysis.append("  - C & C++ maintain consistent performance across sizes")
        analysis.append("  - Go shows good scaling with size")
        analysis.append("  - Java's JIT compilation becomes more effective with larger sizes")
        analysis.append("  - JavaScript's performance varies more with size")
        analysis.append("  - Python's performance gap increases with size")
        
        analysis.append("\n• Algorithm Selection Guidelines:")
        analysis.append("  - Small datasets (< 10,000): Any algorithm is suitable")
        analysis.append("  - Medium datasets (10,000-100,000): Quick Sort or Merge Sort recommended")
        analysis.append("  - Large datasets (> 100,000): Quick Sort, Merge Sort, or specialized algorithms")
        analysis.append("  - Very large datasets (> 500,000): Consider parallel implementations")
        
        return "\n".join(analysis)
    
    def save_analysis(self, filename=None):
        """Save the analysis to a file."""
        if filename is None:
            filename = os.path.join(BASE_DIR, "analysis", "performance_analysis.txt")
        
        # Ensure the analysis directory exists
        os.makedirs(os.path.dirname(filename), exist_ok=True)
        
        analysis = self.generate_analysis()
        with open(filename, 'w') as f:
            f.write(analysis)
        print(f"Detailed analysis saved to {filename}")
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
