#!/usr/bin/env python3
# filepath: ./run-all-py.py

import os
import subprocess
import glob
import sys

def find_py_files():
    """Find all Python files in Chapter folders."""
    chapter_pattern = os.path.join(os.path.dirname(__file__), "Chapter*", "*.py")
    files = sorted(glob.glob(chapter_pattern))
    
    # No longer skipping any files
    return files

def run_python_file(file_path):
    """Run a Python file and return if execution was successful."""
    filename = os.path.basename(file_path)
    print(f"Running: {filename}")
    
    # Prepare input for files that need it
    input_text = None
    if filename == "chapter01-user-input.py":
        # Provide input for name, age, and height questions
        input_text = "Alice\n25\n175\n"
    elif filename == "chapter01-basic-python.py":
        # Provide input for favorite color, number, and divisor questions
        input_text = "Blue\n42\n10\n"
    
    try:
        # If the script needs input, provide it
        if input_text:
            print(f"  Providing automatic input for {filename}")
            result = subprocess.run(
                [sys.executable, file_path],
                input=input_text,
                check=True,
                capture_output=True,
                text=True,
                timeout=60  # Set a timeout of 60 seconds for each script
            )
        else:
            result = subprocess.run(
                [sys.executable, file_path],
                check=True,
                capture_output=True,
                text=True,
                timeout=60  # Set a timeout of 60 seconds for each script
            )
        
        # Print output for files with input (so user can see the interaction)
        if input_text and result.stdout:
            print(f"  Output: {result.stdout.strip()}")
            
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