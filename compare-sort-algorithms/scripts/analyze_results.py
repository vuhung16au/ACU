#!/usr/bin/env python3
"""
Performance Analysis and Reporting Script
Analyzes the Quick Sort performance results and generates comprehensive insights.
"""
import os
import re
import statistics

class PerformanceAnalyzer:
    def __init__(self):
        self.results = {}
        self.load_results()
    
    def load_results(self):
        """Load performance results from all result files."""
        files = {
            'Python': '../results/results_python.txt',
            'C++': '../results/results_cpp.txt',
            'Java': '../results/results_java.txt',
            'JavaScript': '../results/results_javascript.txt',
            'Go': '../results/results_go.txt',
            'C': '../results/results_c.txt'
        }
        
        for lang, filename in files.items():
            if os.path.exists(filename):
                self.results[lang] = self.parse_result_file(filename)
    
    def parse_result_file(self, filename):
        """Parse a result file and extract performance metrics."""
        with open(filename, 'r') as f:
            content = f.read()
        
        result = {}
        
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
        analysis.append("QUICK SORT PERFORMANCE ANALYSIS")
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
                analysis.append(f"{lang:<12}: {time:.6f} seconds")
        
        analysis.append("")
        
        if times:
            # Sort by performance
            times.sort(key=lambda x: x[1])
            fastest_lang, fastest_time = times[0]
            slowest_lang, slowest_time = times[-1]
            
            analysis.append("2. PERFORMANCE RANKING")
            analysis.append("-" * 25)
            analysis.append(f"{'Rank':<6} {'Language':<12} {'Time (sec)':<12} {'vs Fastest':<12}")
            analysis.append("-" * 45)
            
            for i, (lang, time) in enumerate(times, 1):
                relative = time / fastest_time
                analysis.append(f"{i:<6} {lang:<12} {time:<12.6f} {relative:<12.2f}x")
            
            analysis.append("")
            
            # Performance insights
            analysis.append("3. PERFORMANCE INSIGHTS")
            analysis.append("-" * 25)
            
            analysis.append(f"• Fastest: {fastest_lang} ({fastest_time:.6f} seconds)")
            analysis.append(f"• Slowest: {slowest_lang} ({slowest_time:.6f} seconds)")
            analysis.append(f"• Speed difference: {slowest_time/fastest_time:.2f}x")
            
            # Calculate statistics
            time_values = [t[1] for t in times]
            if len(time_values) > 1:
                analysis.append(f"• Average time: {statistics.mean(time_values):.6f} seconds")
                analysis.append(f"• Standard deviation: {statistics.stdev(time_values):.6f} seconds")
            
            analysis.append("")
        
        # Language-specific analysis
        analysis.append("4. LANGUAGE-SPECIFIC ANALYSIS")
        analysis.append("-" * 32)
        
        language_insights = {
            'Python': "Interpreted language with dynamic typing. Generally slower but highly readable and maintainable.",
            'C++': "Compiled language with manual memory management. Typically fastest due to low-level optimizations.",
            'Java': "Compiled to bytecode, runs on JVM. Good performance with automatic memory management.",
            'JavaScript': "Interpreted/JIT compiled. Performance varies by engine (V8 is highly optimized)."
        }
        
        for lang in self.results:
            analysis.append(f"\n{lang}:")
            analysis.append(f"  {language_insights.get(lang, 'No specific insights available.')}")
            
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
        
        # Conclusions
        analysis.append("6. CONCLUSIONS")
        analysis.append("-" * 15)
        
        if times:
            cpp_time = next((t for lang, t in times if lang == 'C++'), None)
            python_time = next((t for lang, t in times if lang == 'Python'), None)
            
            analysis.append("• C++ is expected to be the fastest due to:")
            analysis.append("  - Native compilation and aggressive optimization")
            analysis.append("  - Manual memory management")
            analysis.append("  - No runtime overhead")
            analysis.append("")
            
            analysis.append("• Java performance should be competitive due to:")
            analysis.append("  - JIT compilation optimizations")
            analysis.append("  - Mature JVM technology")
            analysis.append("")
            
            analysis.append("• JavaScript may surprise with good performance due to:")
            analysis.append("  - V8 engine's advanced optimizations")
            analysis.append("  - JIT compilation")
            analysis.append("")
            
            analysis.append("• Python is expected to be slowest due to:")
            analysis.append("  - Interpreted nature")
            analysis.append("  - Dynamic typing overhead")
            analysis.append("  - GIL (Global Interpreter Lock) limitations")
        
        analysis.append("")
        
        # Recommendations
        analysis.append("7. RECOMMENDATIONS")
        analysis.append("-" * 20)
        analysis.append("• For performance-critical applications: Use C++")
        analysis.append("• For balanced performance and productivity: Use Java")
        analysis.append("• For web applications: JavaScript (Node.js) is viable")
        analysis.append("• For rapid prototyping and development: Python")
        analysis.append("")
        analysis.append("• Future improvements:")
        analysis.append("  - Test with different input patterns (sorted, reverse-sorted)")
        analysis.append("  - Compare with built-in sorting algorithms")
        analysis.append("  - Test with different data sizes")
        analysis.append("  - Profile memory usage")
        analysis.append("  - Test parallel/multi-threaded implementations")
        
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
