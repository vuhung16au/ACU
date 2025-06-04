import os
import subprocess
import time
from datetime import datetime

# Print header
print("=== Running Machine Learning Algorithms ===")
print(f"Started at: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}")
print("----------------------------------------")

# Prepare Markdown output file
output_file = "docs/run-all-py-files.md"
with open(output_file, 'w') as f:
    f.write("| Directory | Python File | Result | Run Time (s) |\n")
    f.write("|-----------|-------------|--------|-------------|\n")

# Path to Python interpreter
python_exec = os.path.join('.venv', 'bin', 'python')

# Main execution
script_start_time = time.time()
algorithms_dir = 'algorithms'

for dir_name in sorted(os.listdir(algorithms_dir)):
    dir_path = os.path.join(algorithms_dir, dir_name)
    if os.path.isdir(dir_path):
        print(f"Processing directory: {dir_path}")
        for file_name in sorted(os.listdir(dir_path)):
            if file_name.endswith('.py'):
                py_file = os.path.join(dir_path, file_name)
                print(f"Running: {py_file}")
                start_time = time.time()
                try:
                    result = subprocess.run([
                        python_exec, py_file
                    ], stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL)
                    status = "Success" if result.returncode == 0 else "Failed"
                except Exception:
                    status = "Failed"
                end_time = time.time()
                run_time = end_time - start_time
                with open(output_file, 'a') as f:
                    f.write(f"| {dir_name} | {file_name} | {status} | {run_time:.3f} |\n")

script_end_time = time.time()
total_run_time = script_end_time - script_start_time
completion_time = datetime.now().strftime('%Y-%m-%d %H:%M:%S')

print("=== All algorithms completed ===")
print(f"Finished at: {completion_time}")
print(f"Total time to run all .py files: {total_run_time:.3f} seconds")

# Append completion information to the markdown file
with open(output_file, 'a') as f:
    f.write("\n---\n")
    f.write(f"Finished at: {completion_time}\n")
    f.write(f"Total time to run all .py files: {total_run_time:.3f} seconds\n") 