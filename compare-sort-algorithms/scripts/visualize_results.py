import os
import re
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns

# Directory containing the consolidated results
RESULTS_DIR = os.path.join(os.path.dirname(__file__), '../analysis')

# List of consolidated results files to parse
RESULT_FILES = [
    'consolidated_results_10000.txt',
    'consolidated_results_100000.txt',
    'consolidated_results_1000000.txt',
    'consolidated_results_1500000.txt',
]

# Regular expressions to extract relevant data
LANG_ALGO_HEADER = re.compile(r'^(\w+) \(([^)]+)\):')
RESULT_LINE = re.compile(r'(\w+) ([\w ]+) Results')
DATA_SIZE_LINE = re.compile(r'Data size: (\d+)')
EXEC_TIME_LINE = re.compile(r'Execution time: ([\d\.]+) seconds')

# Parse all result files and collect data
records = []
for fname in RESULT_FILES:
    path = os.path.join(RESULTS_DIR, fname)
    with open(path, 'r') as f:
        lang = algo = None
        data_size = exec_time = None
        for line in f:
            m = LANG_ALGO_HEADER.match(line)
            if m:
                lang, algo = m.group(1), m.group(2)
                continue
            m = RESULT_LINE.match(line)
            if m:
                lang, algo = m.group(1), m.group(2).replace(' Sort', '')
                continue
            m = DATA_SIZE_LINE.match(line)
            if m:
                data_size = int(m.group(1))
                continue
            m = EXEC_TIME_LINE.match(line)
            if m:
                exec_time = float(m.group(1))
                if lang and algo and data_size and exec_time is not None:
                    records.append({
                        'Language': lang,
                        'Algorithm': algo,
                        'Data Size': data_size,
                        'Execution Time': exec_time
                    })
                lang = algo = data_size = exec_time = None

# Create DataFrame
results_df = pd.DataFrame(records)

# Plot: log-log plot of execution time vs. data size for each algorithm and language
sns.set(style="whitegrid", font_scale=1.2)
g = sns.relplot(
    data=results_df,
    x='Data Size', y='Execution Time',
    hue='Algorithm', style='Language',
    kind='line', marker='o',
    facet_kws={'sharey': False, 'sharex': True},
    height=8, aspect=1.6 # Make the plot area larger
)
g.set(xscale="log", yscale="log")
g.set_axis_labels("Data Size (log)", "Execution Time (s, log)")
plt.title("Sorting Algorithm Performance: Execution Time vs. Data Size (log-log)")

g.add_legend(bbox_to_anchor=(1.02, 0.5), loc='center left', frameon=True)
plt.tight_layout(rect=(0, 0, 0.8, 1))  # Leave space for legend

# Save the figure to the docs directory
output_path = os.path.join(os.path.dirname(__file__), '../docs/sorting_performance_loglog.png')
g.figure.savefig(output_path, bbox_inches='tight')
print(f"Plot saved to {output_path}")
# plt.show()  # Disabled as requested
