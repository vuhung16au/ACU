#!/usr/bin/env python3
"""
Test runner for Streamlit components.
"""

import sys
import os
import pytest
from pathlib import Path

# Add the parent directory to the Python path
sys.path.insert(0, str(Path(__file__).parent.parent))

def run_tests():
    """Run all unit tests."""
    # Get the directory containing this script
    test_dir = Path(__file__).parent
    
    # Run pytest with verbose output
    args = [
        str(test_dir),
        "-v",
        "--tb=short",
        "--color=yes",
        "--durations=10"
    ]
    
    # Add coverage if available
    try:
        import coverage
        args.extend(["--cov=components", "--cov-report=html", "--cov-report=term"])
    except ImportError:
        print("Coverage not available. Install with: pip install pytest-cov")
    
    # Run the tests
    exit_code = pytest.main(args)
    
    return exit_code

if __name__ == "__main__":
    print("Running Streamlit component tests...")
    print("=" * 50)
    
    exit_code = run_tests()
    
    print("=" * 50)
    if exit_code == 0:
        print("✅ All tests passed!")
    else:
        print("❌ Some tests failed!")
    
    sys.exit(exit_code) 