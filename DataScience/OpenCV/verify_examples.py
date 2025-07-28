#!/usr/bin/env python3
"""
Script to verify that all Python files in the examples/ directory can run without errors.
This script attempts to execute each .py file and reports any issues encountered.
"""

import os
import sys
import subprocess
import importlib.util
from pathlib import Path
from typing import List, Tuple, Dict
import traceback


def get_example_files() -> List[Path]:
    """Get all Python files in the examples directory."""
    examples_dir = Path("examples")
    if not examples_dir.exists():
        print(f"❌ Error: {examples_dir} directory not found!")
        return []
    
    py_files = list(examples_dir.glob("*.py"))
    if not py_files:
        print(f"❌ No Python files found in {examples_dir}")
        return []
    
    return sorted(py_files)


def run_file_with_subprocess(file_path: Path) -> Tuple[bool, str]:
    """
    Run a Python file using subprocess and capture any errors.
    Returns (success, output/error_message)
    """
    try:
        # Set up environment with proper Python path
        env = os.environ.copy()
        current_dir = Path.cwd()
        src_path = str(current_dir / "src")
        
        # Add src directory to PYTHONPATH
        if "PYTHONPATH" in env:
            env["PYTHONPATH"] = f"{src_path}:{env['PYTHONPATH']}"
        else:
            env["PYTHONPATH"] = src_path
        
        # Set longer timeout for advanced techniques demo
        timeout = 60 if "advanced_techniques" in file_path.name else 30
        
        # Run the file with Python interpreter
        result = subprocess.run(
            [sys.executable, str(file_path)],
            capture_output=True,
            text=True,
            timeout=timeout,  # Dynamic timeout based on file
            env=env,
            cwd=current_dir  # Ensure we're in the project root
        )
        
        if result.returncode == 0:
            return True, result.stdout
        else:
            return False, result.stderr
    
    except subprocess.TimeoutExpired:
        timeout_used = 60 if "advanced_techniques" in file_path.name else 30
        return False, f"Execution timed out after {timeout_used} seconds"
    except Exception as e:
        return False, f"Subprocess error: {str(e)}"


def run_file_with_import(file_path: Path) -> Tuple[bool, str]:
    """
    Import and run a Python file as a module to test importability.
    Returns (success, output/error_message)
    """
    try:
        # Add src directory to sys.path for imports
        current_dir = Path.cwd()
        src_path = str(current_dir / "src")
        if src_path not in sys.path:
            sys.path.insert(0, src_path)
        
        # Load the module
        spec = importlib.util.spec_from_file_location(file_path.stem, file_path)
        if spec is None or spec.loader is None:
            return False, "Could not create module spec"
        
        module = importlib.util.module_from_spec(spec)
        
        # Execute the module
        spec.loader.exec_module(module)
        
        return True, "Module imported and executed successfully"
    
    except Exception as e:
        return False, f"Import/execution error: {str(e)}\n{traceback.format_exc()}"


def verify_file(file_path: Path, method: str = "subprocess") -> Dict[str, any]:
    """Verify a single Python file can run without errors."""
    print(f"🔍 Testing {file_path.name}...")
    
    if method == "subprocess":
        success, output = run_file_with_subprocess(file_path)
    else:
        success, output = run_file_with_import(file_path)
    
    result = {
        "file": file_path.name,
        "success": success,
        "output": output,
        "method": method
    }
    
    if success:
        print(f"✅ {file_path.name} - PASSED")
    else:
        print(f"❌ {file_path.name} - FAILED")
        print(f"   Error: {output[:200]}{'...' if len(output) > 200 else ''}")
    
    return result


def main():
    """Main verification function."""
    print("🚀 Starting verification of examples/*.py files...")
    print("=" * 60)
    
    # Get all example files
    example_files = get_example_files()
    if not example_files:
        return
    
    print(f"Found {len(example_files)} Python files to verify:")
    for file in example_files:
        print(f"  - {file.name}")
    print()
    
    # Verify each file
    results = []
    for file_path in example_files:
        result = verify_file(file_path)
        results.append(result)
        print()
    
    # Summary
    print("=" * 60)
    print("📊 VERIFICATION SUMMARY")
    print("=" * 60)
    
    passed = sum(1 for r in results if r["success"])
    failed = len(results) - passed
    
    print(f"Total files tested: {len(results)}")
    print(f"✅ Passed: {passed}")
    print(f"❌ Failed: {failed}")
    
    if failed > 0:
        print("\n❌ FAILED FILES:")
        for result in results:
            if not result["success"]:
                print(f"  - {result['file']}")
                print(f"    Error: {result['output'][:100]}...")
        print()
        return False
    else:
        print("\n🎉 All example files passed verification!")
        return True


if __name__ == "__main__":
    success = main()
    sys.exit(0 if success else 1) 