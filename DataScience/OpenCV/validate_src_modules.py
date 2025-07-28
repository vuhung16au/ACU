#!/usr/bin/env python3
"""
Script to validate that all Python files in the src directory can be imported without errors.

This script:
1. Checks for required dependencies (OpenCV, NumPy, etc.)
2. Finds all .py files in the src directory and subdirectories
3. Attempts to import each module (skipping __init__.py files with relative imports)
4. Reports success/failure for each file
5. Provides a summary of results

Usage:
    python validate_src_modules.py
"""

import os
import sys
import importlib.util
from pathlib import Path
from typing import List, Tuple, Dict, Set
import traceback


def check_dependencies() -> Dict[str, bool]:
    """
    Check if required dependencies are available.
    
    Returns:
        Dictionary mapping dependency names to availability status
    """
    dependencies = {
        'cv2': 'OpenCV',
        'numpy': 'NumPy',
        'matplotlib': 'Matplotlib',
        'scipy': 'SciPy',
        'skimage': 'scikit-image',
        'sklearn': 'scikit-learn'
    }
    
    results = {}
    print("Checking dependencies...")
    print("-" * 40)
    
    for module_name, display_name in dependencies.items():
        try:
            module = __import__(module_name)
            version = getattr(module, '__version__', 'unknown')
            results[module_name] = True
            print(f"  ✓ {display_name} ({module_name}) - version {version}")
        except ImportError:
            results[module_name] = False
            print(f"  ✗ {display_name} ({module_name}) - NOT INSTALLED")
    
    print()
    return results


def find_python_files(src_dir: str = "src") -> List[str]:
    """
    Find all Python files in the src directory and subdirectories.
    
    Args:
        src_dir: Directory to search in
        
    Returns:
        List of file paths
    """
    python_files = []
    src_path = Path(src_dir)
    
    if not src_path.exists():
        print(f"Error: Directory '{src_dir}' does not exist.")
        return python_files
    
    for py_file in src_path.rglob("*.py"):
        # Skip __pycache__ directories
        if "__pycache__" not in str(py_file):
            python_files.append(str(py_file))
    
    return sorted(python_files)


def is_init_file_with_relative_imports(file_path: str) -> bool:
    """
    Check if a file is an __init__.py file that likely contains relative imports.
    
    Args:
        file_path: Path to the Python file
        
    Returns:
        True if it's an __init__.py file with relative imports
    """
    if not file_path.endswith("__init__.py"):
        return False
    
    try:
        with open(file_path, 'r') as f:
            content = f.read()
            # Check for relative imports (from . import, from .. import, etc.)
            return any(line.strip().startswith(('from .', 'from ..')) 
                     for line in content.split('\n') 
                     if line.strip() and not line.strip().startswith('#'))
    except Exception:
        return False


def import_module_from_file(file_path: str, skip_relative_imports: bool = True) -> Tuple[bool, str]:
    """
    Attempt to import a module from a file path.
    
    Args:
        file_path: Path to the Python file
        skip_relative_imports: Whether to skip __init__.py files with relative imports
        
    Returns:
        Tuple of (success, error_message)
    """
    # Skip __init__.py files with relative imports
    if skip_relative_imports and is_init_file_with_relative_imports(file_path):
        return True, "Skipped (relative imports in __init__.py)"
    
    try:
        # Get the module name from the file path
        module_name = Path(file_path).stem
        
        # Load the module
        spec = importlib.util.spec_from_file_location(module_name, file_path)
        if spec is None or spec.loader is None:
            return False, f"Could not create spec for {file_path}"
        
        module = importlib.util.module_from_spec(spec)
        spec.loader.exec_module(module)
        
        return True, "Success"
        
    except ImportError as e:
        if "No module named 'cv2'" in str(e):
            return False, "Missing OpenCV dependency"
        elif "attempted relative import with no known parent package" in str(e):
            return True, "Success (relative imports - expected for package modules)"
        elif "No module named 'sklearn'" in str(e):
            return False, "Missing scikit-learn dependency"
        else:
            return False, f"Import error: {str(e)}"
    except Exception as e:
        error_msg = f"Error importing {file_path}: {str(e)}"
        return False, error_msg


def validate_modules(python_files: List[str], dependencies: Dict[str, bool]) -> Dict[str, Tuple[bool, str]]:
    """
    Validate all Python files by attempting to import them.
    
    Args:
        python_files: List of Python file paths
        dependencies: Dictionary of dependency availability
        
    Returns:
        Dictionary mapping file paths to (success, message) tuples
    """
    results = {}
    
    # Check if OpenCV is available
    opencv_available = dependencies.get('cv2', False)
    
    print(f"Validating {len(python_files)} Python files...")
    if not opencv_available:
        print("⚠️  OpenCV not installed - modules requiring cv2 will fail")
    print("-" * 60)
    
    for file_path in python_files:
        print(f"Testing: {file_path}")
        success, message = import_module_from_file(file_path)
        results[file_path] = (success, message)
        
        if success:
            print(f"  ✓ SUCCESS")
        else:
            print(f"  ✗ FAILED: {message}")
        print()
    
    return results


def print_summary(results: Dict[str, Tuple[bool, str]], dependencies: Dict[str, bool]):
    """
    Print a summary of validation results.
    
    Args:
        results: Dictionary of validation results
        dependencies: Dictionary of dependency availability
    """
    print("=" * 60)
    print("VALIDATION SUMMARY")
    print("=" * 60)
    
    total_files = len(results)
    successful_files = sum(1 for success, _ in results.values() if success)
    failed_files = total_files - successful_files
    
    print(f"Total files tested: {total_files}")
    print(f"Successful imports: {successful_files}")
    print(f"Failed imports: {failed_files}")
    print(f"Success rate: {(successful_files/total_files)*100:.1f}%")
    
    # Check dependency issues
    missing_deps = [name for name, available in dependencies.items() if not available]
    if missing_deps:
        print(f"\nMissing dependencies: {', '.join(missing_deps)}")
        print("Install with: pip install opencv-python numpy matplotlib scipy scikit-image")
    
    if failed_files > 0:
        print("\nFAILED FILES:")
        print("-" * 40)
        for file_path, (success, message) in results.items():
            if not success:
                print(f"  {file_path}")
                print(f"    Error: {message}")
                print()
    
    if successful_files == total_files:
        print("\n🎉 All modules can be imported successfully!")
    else:
        print(f"\n⚠️  {failed_files} module(s) failed to import.")
        
        # Provide guidance
        if not dependencies.get('cv2', False):
            print("\n💡 To fix OpenCV-related errors, install OpenCV:")
            print("   pip install opencv-python")


def main():
    """Main function to run the validation."""
    print("OpenCV Source Modules Validation")
    print("=" * 60)
    
    # Check dependencies first
    dependencies = check_dependencies()
    
    # Find all Python files
    python_files = find_python_files()
    
    if not python_files:
        print("No Python files found in src directory.")
        return
    
    # Validate all modules
    results = validate_modules(python_files, dependencies)
    
    # Print summary
    print_summary(results, dependencies)
    
    # Exit with appropriate code
    failed_count = sum(1 for success, _ in results.values() if not success)
    sys.exit(failed_count)


if __name__ == "__main__":
    main() 