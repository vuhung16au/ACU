#!/usr/bin/env python3
"""
Notebook Verification Script

This script executes all Jupyter notebooks in the notebooks/ directory to verify
they can run without errors. It provides detailed reporting of execution status,
timing, and any errors encountered.

Usage:
    python verify_notebooks.py [options]

Options:
    --notebooks-dir: Directory containing notebooks (default: notebooks/)
    --timeout: Timeout for each notebook in seconds (default: 300)
    --verbose: Enable verbose output
    --stop-on-error: Stop execution on first error
    --exclude: Notebooks to exclude (comma-separated)
"""

import os
import sys
import time
import argparse
import traceback
from pathlib import Path
from typing import List, Dict, Tuple, Optional
import subprocess
import json
import tempfile


class NotebookVerifier:
    """Verifies that Jupyter notebooks can execute without errors."""
    
    def __init__(self, notebooks_dir: str = "notebooks", timeout: int = 300, verbose: bool = False):
        """
        Initialize the notebook verifier.
        
        Args:
            notebooks_dir: Directory containing notebooks
            timeout: Timeout for each notebook execution in seconds
            verbose: Enable verbose output
        """
        self.notebooks_dir = Path(notebooks_dir)
        self.timeout = timeout
        self.verbose = verbose
        self.results = {}
        
    def find_notebooks(self, exclude: Optional[List[str]] = None) -> List[Path]:
        """
        Find all Jupyter notebooks in the specified directory.
        
        Args:
            exclude: List of notebook names to exclude
            
        Returns:
            List of notebook paths
        """
        if not self.notebooks_dir.exists():
            raise FileNotFoundError(f"Notebooks directory not found: {self.notebooks_dir}")
            
        exclude = exclude or []
        notebooks = []
        
        for notebook_path in self.notebooks_dir.glob("*.ipynb"):
            if notebook_path.name not in exclude:
                notebooks.append(notebook_path)
                
        # Sort notebooks for consistent execution order
        notebooks.sort(key=lambda x: x.name)
        
        if self.verbose:
            print(f"Found {len(notebooks)} notebooks:")
            for nb in notebooks:
                print(f"  - {nb.name}")
                
        return notebooks
    
    def check_dependencies(self) -> bool:
        """
        Check if required dependencies are installed.
        
        Returns:
            True if all dependencies are available
        """
        required_packages = ['nbconvert', 'jupyter']
        missing_packages = []
        
        for package in required_packages:
            try:
                __import__(package)
            except ImportError:
                missing_packages.append(package)
                
        if missing_packages:
            print(f"❌ Missing required packages: {', '.join(missing_packages)}")
            print("Install them with: pip install nbconvert jupyter")
            return False
            
        return True
    
    def execute_notebook(self, notebook_path: Path) -> Dict:
        """
        Execute a single notebook and return execution results.
        
        Args:
            notebook_path: Path to the notebook
            
        Returns:
            Dictionary with execution results
        """
        start_time = time.time()
        result = {
            'notebook': notebook_path.name,
            'path': str(notebook_path),
            'success': False,
            'execution_time': 0,
            'error': None,
            'output': None
        }
        
        try:
            if self.verbose:
                print(f"  📖 Executing {notebook_path.name}...")
                
            # Create a temporary output file
            with tempfile.NamedTemporaryFile(mode='w', suffix='.ipynb', delete=False) as temp_file:
                temp_output = temp_file.name
                
            try:
                # Execute notebook using nbconvert
                cmd = [
                    'jupyter', 'nbconvert',
                    '--to', 'notebook',
                    '--execute',
                    '--ExecutePreprocessor.timeout=' + str(self.timeout),
                    '--output', temp_output,
                    str(notebook_path)
                ]
                
                if self.verbose:
                    print(f"    Command: {' '.join(cmd)}")
                
                # Execute the command
                process = subprocess.run(
                    cmd,
                    capture_output=True,
                    text=True,
                    timeout=self.timeout + 30  # Add buffer to nbconvert timeout
                )
                
                if process.returncode == 0:
                    result['success'] = True
                    result['output'] = process.stdout
                    if self.verbose:
                        print(f"    ✅ Success!")
                else:
                    result['error'] = process.stderr
                    if self.verbose:
                        print(f"    ❌ Failed with return code {process.returncode}")
                        print(f"    Error: {process.stderr}")
                        
            finally:
                # Clean up temporary file
                if os.path.exists(temp_output):
                    os.unlink(temp_output)
                    
        except subprocess.TimeoutExpired:
            result['error'] = f"Notebook execution timed out after {self.timeout} seconds"
            if self.verbose:
                print(f"    ⏰ Timeout after {self.timeout} seconds")
                
        except Exception as e:
            result['error'] = str(e)
            if self.verbose:
                print(f"    💥 Exception: {e}")
                
        result['execution_time'] = time.time() - start_time
        return result
    
    def verify_all_notebooks(self, exclude: Optional[List[str]] = None, stop_on_error: bool = False) -> Dict:
        """
        Verify all notebooks in the directory.
        
        Args:
            exclude: List of notebook names to exclude
            stop_on_error: Stop execution on first error
            
        Returns:
            Dictionary with verification results
        """
        if not self.check_dependencies():
            return {'success': False, 'error': 'Missing dependencies'}
            
        print("🔍 Starting notebook verification...")
        print("=" * 60)
        
        notebooks = self.find_notebooks(exclude)
        if not notebooks:
            print("⚠️  No notebooks found to verify")
            return {'success': True, 'notebooks': [], 'summary': {'total': 0, 'passed': 0, 'failed': 0}}
        
        results = []
        total_time = time.time()
        
        for i, notebook_path in enumerate(notebooks, 1):
            print(f"\n📊 [{i}/{len(notebooks)}] Verifying {notebook_path.name}")
            
            result = self.execute_notebook(notebook_path)
            results.append(result)
            
            if result['success']:
                print(f"✅ PASSED ({result['execution_time']:.1f}s)")
            else:
                print(f"❌ FAILED ({result['execution_time']:.1f}s)")
                if result['error']:
                    print(f"   Error: {result['error']}")
                    
                if stop_on_error:
                    print("\n🛑 Stopping on first error (--stop-on-error)")
                    break
        
        total_time = time.time() - total_time
        
        # Generate summary
        passed = sum(1 for r in results if r['success'])
        failed = len(results) - passed
        
        summary = {
            'total': len(results),
            'passed': passed,
            'failed': failed,
            'total_time': total_time
        }
        
        self.print_summary(summary, results)
        
        return {
            'success': failed == 0,
            'notebooks': results,
            'summary': summary
        }
    
    def print_summary(self, summary: Dict, results: List[Dict]):
        """Print execution summary."""
        print("\n" + "=" * 60)
        print("📈 VERIFICATION SUMMARY")
        print("=" * 60)
        
        print(f"Total notebooks: {summary['total']}")
        print(f"✅ Passed: {summary['passed']}")
        print(f"❌ Failed: {summary['failed']}")
        print(f"⏱️  Total time: {summary['total_time']:.1f}s")
        
        if summary['failed'] > 0:
            print(f"\n💥 Failed notebooks:")
            for result in results:
                if not result['success']:
                    print(f"  - {result['notebook']}: {result['error']}")
        
        print("\n" + "=" * 60)
        
        if summary['failed'] == 0:
            print("🎉 All notebooks executed successfully!")
        else:
            print(f"⚠️  {summary['failed']} notebook(s) failed verification")
    
    def save_report(self, results: Dict, output_file: str = "notebook_verification_report.json"):
        """Save verification results to a JSON report."""
        try:
            with open(output_file, 'w') as f:
                json.dump(results, f, indent=2, default=str)
            print(f"📄 Report saved to: {output_file}")
        except Exception as e:
            print(f"⚠️  Failed to save report: {e}")


def main():
    """Main entry point for the script."""
    parser = argparse.ArgumentParser(
        description="Verify that all Jupyter notebooks can execute without errors",
        formatter_class=argparse.RawDescriptionHelpFormatter
    )
    
    parser.add_argument(
        '--notebooks-dir',
        default='notebooks',
        help='Directory containing notebooks (default: notebooks/)'
    )
    
    parser.add_argument(
        '--timeout',
        type=int,
        default=300,
        help='Timeout for each notebook in seconds (default: 300)'
    )
    
    parser.add_argument(
        '--verbose', '-v',
        action='store_true',
        help='Enable verbose output'
    )
    
    parser.add_argument(
        '--stop-on-error',
        action='store_true',
        help='Stop execution on first error'
    )
    
    parser.add_argument(
        '--exclude',
        help='Notebooks to exclude (comma-separated names)'
    )
    
    parser.add_argument(
        '--save-report',
        help='Save results to JSON file (default: notebook_verification_report.json)'
    )
    
    args = parser.parse_args()
    
    # Parse excluded notebooks
    exclude = []
    if args.exclude:
        exclude = [name.strip() for name in args.exclude.split(',')]
    
    # Initialize verifier
    verifier = NotebookVerifier(
        notebooks_dir=args.notebooks_dir,
        timeout=args.timeout,
        verbose=args.verbose
    )
    
    try:
        # Run verification
        results = verifier.verify_all_notebooks(
            exclude=exclude,
            stop_on_error=args.stop_on_error
        )
        
        # Save report if requested
        if args.save_report:
            verifier.save_report(results, args.save_report)
        
        # Exit with appropriate code
        sys.exit(0 if results['success'] else 1)
        
    except KeyboardInterrupt:
        print("\n🛑 Verification interrupted by user")
        sys.exit(1)
    except Exception as e:
        print(f"💥 Unexpected error: {e}")
        if args.verbose:
            traceback.print_exc()
        sys.exit(1)


if __name__ == "__main__":
    main()
