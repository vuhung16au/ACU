#!/usr/bin/env python3
"""
Quick Notebook Test Runner

A lightweight script to quickly test if notebooks can run.
Designed for rapid development and testing cycles.
"""

import subprocess
import sys
from pathlib import Path


def quick_test(notebook_path):
    """Quickly test a single notebook."""
    try:
        cmd = [
            'jupyter', 'nbconvert',
            '--to', 'notebook',
            '--execute',
            '--ExecutePreprocessor.timeout=60',
            '--stdout',
            str(notebook_path)
        ]
        
        result = subprocess.run(cmd, capture_output=True, text=True, timeout=90)
        return result.returncode == 0, result.stderr
    except Exception as e:
        return False, str(e)


def main():
    """Main function."""
    if len(sys.argv) < 2:
        print("Usage: python quick_test.py <notebook_path>")
        print("Example: python quick_test.py notebooks/01_basic_operations.ipynb")
        sys.exit(1)
    
    notebook_path = Path(sys.argv[1])
    
    if not notebook_path.exists():
        print(f"❌ Notebook not found: {notebook_path}")
        sys.exit(1)
    
    print(f"🧪 Quick testing: {notebook_path.name}")
    
    success, error = quick_test(notebook_path)
    
    if success:
        print(f"✅ {notebook_path.name} executed successfully!")
        sys.exit(0)
    else:
        print(f"❌ {notebook_path.name} failed:")
        print(f"Error: {error}")
        sys.exit(1)


if __name__ == "__main__":
    main()
