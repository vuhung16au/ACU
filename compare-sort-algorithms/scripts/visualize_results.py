import os
import re
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns

# Define base directory for proper path handling
BASE_DIR = os.path.abspath(os.path.join(os.path.dirname(__file__), '..'))

# Directory containing the consolidated results
RESULTS_DIR = os.path.join(BASE_DIR, 'analysis')
CONFIG_DIR = os.path.join(BASE_DIR, 'config')

# Read data sizes from config file
def read_data_sizes():
    config_file = os.path.join(CONFIG_DIR, 'number-of-data-points.txt')
    if not os.path.exists(config_file):
        print(f"Error: Config file {config_file} not found.")
        return []
    
    with open(config_file, 'r') as f:
        sizes = [int(line.strip()) for line in f if line.strip() and not line.strip().startswith('#')]
    return sizes

# Generate result file names from data sizes
def get_result_files():
    sizes = read_data_sizes()
    return [f'consolidated_results_{size}.txt' for size in sizes]

# Regular expressions to extract relevant data
LANG_ALGO_HEADER = re.compile(r'^(\w+) \(([^)]+)\):', re.MULTILINE)
EXEC_TIME_LINE = re.compile(r'Average execution time: ([\d\.]+) seconds')
DATA_SIZE_LINE = re.compile(r'Data Size: ([\d,]+) integers')

# Parse all result files and collect data
records = []
for fname in get_result_files():
    path = os.path.join(RESULTS_DIR, fname)
    if not os.path.exists(path):
        print(f"Warning: {path} does not exist. Skipping.")
        continue
    with open(path, 'r') as f:
        content = f.read()
        # Extract data size
        size_match = DATA_SIZE_LINE.search(content)
        if not size_match:
            continue
        data_size = int(size_match.group(1).replace(',', ''))
        
        # Extract all language/algorithm combinations
        for match in LANG_ALGO_HEADER.finditer(content):
            lang, algo = match.group(1), match.group(2)
            # Find the execution time for this combination
            start_pos = match.end()
            time_match = EXEC_TIME_LINE.search(content[start_pos:])
            if time_match:
                exec_time = float(time_match.group(1))
                records.append({
                    'Language': lang,
                    'Algorithm': algo,
                    'Data Size': data_size,
                    'Execution Time': exec_time
                })

# Create DataFrame
results_df = pd.DataFrame(records)

if results_df.empty:
    print("No data found to plot. Please run the performance comparisons first.")
    print("Try running: make run")
    exit(1)

# Plot: log-log plot of execution time vs. data size for each algorithm and language
sns.set(style="whitegrid", font_scale=1.2)
g = sns.relplot(
    data=results_df,
    x='Data Size', y='Execution Time',
    hue='Language', style='Algorithm',
    kind='line', marker='o',
    facet_kws={'sharey': False, 'sharex': True},
    height=8, aspect=1.6,  # Make the plot area larger
    palette='tab10'
)
g.set(xscale="log", yscale="log")
g.set_axis_labels("Data Size (log)", "Execution Time (s, log)")
plt.title("Sorting Algorithm Performance: Execution Time vs. Data Size (log-log)")

g.add_legend(bbox_to_anchor=(1.02, 0.5), loc='center left', frameon=True)
plt.tight_layout(rect=(0, 0, 0.8, 1))  # Leave space for legend

# Save the figure to the images directory
output_path = os.path.join(BASE_DIR, 'images', 'sorting_performance_loglog.png')
g.figure.savefig(output_path, bbox_inches='tight')
print(f"Plot saved to {output_path}")
# plt.show()  # Disabled as requested

# 1. Compare the same algorithm across languages
algorithms = results_df['Algorithm'].unique()
for algo in algorithms:
    subset = results_df[results_df['Algorithm'] == algo]
    g = sns.relplot(
        data=subset,
        x='Data Size', y='Execution Time',
        hue='Language', style='Language',
        kind='line', marker='o',
        height=6, aspect=1.3,
        palette='tab10'
    )
    g.set(xscale="log", yscale="log")
    g.set_axis_labels("Data Size (log)", "Execution Time (s, log)")
    plt.title(f"{algo}: Performance Across Languages (log-log)")
    output_path = os.path.join(BASE_DIR, 'images', f'{algo.lower().replace(" ", "_")}_across_languages.png')
    g.figure.savefig(output_path, bbox_inches='tight')
    print(f"Plot saved to {output_path}")

# 2. Compare the same language across algorithms
languages = results_df['Language'].unique()
for lang in languages:
    subset = results_df[results_df['Language'] == lang]
    g = sns.relplot(
        data=subset,
        x='Data Size', y='Execution Time',
        hue='Algorithm', style='Algorithm',
        kind='line', marker='o',
        height=6, aspect=1.3,
        palette='tab10'
    )
    g.set(xscale="log", yscale="log")
    g.set_axis_labels("Data Size (log)", "Execution Time (s, log)")
    plt.title(f"{lang}: Performance Across Algorithms (log-log)")
    # Sanitize language name for filename
    safe_lang = re.sub(r'[^a-z0-9_]', '', lang.lower().replace(' ', '_'))
    output_path = os.path.join(BASE_DIR, 'images', f'{safe_lang}_across_algorithms.png')
    g.figure.savefig(output_path, bbox_inches='tight')
    print(f"Plot saved to {output_path}")
