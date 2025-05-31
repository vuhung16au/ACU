#!/usr/bin/env python3
# filepath: /Users/vuhung/Desktop/ACU/Algorithms-in-Python/run-all-py.py

import os
import subprocess
import glob
import sys

def find_py_files():
    """Find all Python files in Chapter folders."""
    chapter_pattern = os.path.join(os.path.dirname(__file__), "Chapter*", "*.py")
    files = sorted(glob.glob(chapter_pattern))
    
    # Files known to require user input or might hang
    skip_files = [
        "chapter01-user-input.py",
        "chapter01-basic-python.py"
    ]
    
    # Filter out files to skip
    filtered_files = []
    for file in files:
        filename = os.path.basename(file)
        if filename in skip_files:
            print(f"Skipping {filename} (known to require user input)")
        else:
            filtered_files.append(file)
            
    return filtered_files

def run_python_file(file_path):
    """Run a Python file and return if execution was successful."""
    print(f"Running: {os.path.basename(file_path)}")
    try:
        result = subprocess.run(
            [sys.executable, file_path],
            check=True,
            capture_output=True,
            text=True,
            timeout=60  # Set a timeout of 60 seconds for each script
        )
        return True, None
    except subprocess.CalledProcessError as e:
        return False, f"Error code: {e.returncode}\nOutput: {e.stdout}\nError: {e.stderr}"
    except subprocess.TimeoutExpired:
        return False, "Execution timed out (60s)"
    except Exception as e:
        return False, str(e)

def main():
    python_files = find_py_files()
    print(f"Found {len(python_files)} Python files to run")
    
    failures = []
    
    for py_file in python_files:
        success, error_msg = run_python_file(py_file)
        if not success:
            failures.append((py_file, error_msg))
            print(f"❌ Failed: {os.path.basename(py_file)}")
        else:
            print(f"✅ Success: {os.path.basename(py_file)}")
    
    print("\n" + "="*50)
    if failures:
        print(f"❌ {len(failures)} out of {len(python_files)} files failed to execute:")
        for file_path, error_msg in failures:
            print(f"\n📄 {file_path}:")
            print(f"   {error_msg.replace(os.linesep, os.linesep + '   ')}")
        print("\nExecution completed with errors.")
    else:
        print(f"✅ All {len(python_files)} files ran successfully!")
        print("\nrun success")

if __name__ == "__main__":
    main()