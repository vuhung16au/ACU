# Notebook Verification System

This directory contains tools to verify that all Jupyter notebooks in the `notebooks/` directory can execute without errors. This ensures that the tutorials and examples work correctly for users.

## Overview

The verification system includes:

1. **Python Script** (`verify_notebooks.py`) - Comprehensive verification with detailed reporting
2. **Bash Script** (`verify_notebooks.sh`) - Simple shell-based verification
3. **Makefile** - Easy-to-use targets for common verification tasks

## Quick Start

### Using the Makefile (Recommended)

```bash
# Test if all dependencies are installed
make test-setup

# Verify all notebooks
make verify-notebooks

# Verify with verbose output
make verify-notebooks-verbose

# Quick verification (shorter timeout)
make verify-notebooks-quick
```

### Using the Python Script Directly

```bash
# Basic verification
python verify_notebooks.py

# Verbose mode with custom timeout
python verify_notebooks.py --verbose --timeout 600

# Exclude specific notebooks
python verify_notebooks.py --exclude "07_advanced_techniques.ipynb,08_practical_applications.ipynb"

# Stop on first error and save report
python verify_notebooks.py --stop-on-error --save-report results.json
```

### Using the Bash Script

```bash
# Basic verification
./verify_notebooks.sh

# Verbose mode
./verify_notebooks.sh --verbose

# Custom timeout
./verify_notebooks.sh --timeout 600
```

## Features

### Python Script Features

- ✅ **Comprehensive Error Reporting** - Detailed error messages and stack traces
- ✅ **Timeout Management** - Configurable timeout per notebook
- ✅ **Execution Timing** - Reports execution time for each notebook
- ✅ **JSON Reports** - Save detailed results to JSON files
- ✅ **Selective Execution** - Exclude specific notebooks
- ✅ **Dependency Checking** - Verify required packages are installed
- ✅ **Progress Tracking** - Real-time progress updates
- ✅ **Stop on Error** - Option to halt on first failure

### Bash Script Features

- ✅ **Lightweight** - No additional Python dependencies
- ✅ **Colored Output** - Easy-to-read status indicators
- ✅ **Simple Configuration** - Command-line options
- ✅ **Fast Execution** - Minimal overhead

## Dependencies

### Required Packages

```bash
pip install jupyter nbconvert opencv-python numpy matplotlib
```

### Optional Packages (for specific notebooks)

```bash
pip install scikit-learn scipy scikit-image
```

## Configuration Options

### Python Script Options

| Option | Description | Default |
|--------|-------------|---------|
| `--notebooks-dir` | Directory containing notebooks | `notebooks/` |
| `--timeout` | Timeout per notebook (seconds) | `300` |
| `--verbose` | Enable verbose output | `False` |
| `--stop-on-error` | Stop on first error | `False` |
| `--exclude` | Comma-separated notebook names to exclude | None |
| `--save-report` | Save results to JSON file | None |

### Environment Variables

```bash
# Set default timeout
export NOTEBOOK_TIMEOUT=600

# Set notebooks directory
export NOTEBOOKS_DIR=notebooks

# Enable verbose mode
export NOTEBOOK_VERBOSE=true
```

## Usage Examples

### Development Workflow

```bash
# Before committing changes, verify all notebooks work
make verify-notebooks

# During development, test specific notebooks
python verify_notebooks.py --exclude "08_practical_applications.ipynb"

# For debugging, use verbose mode and stop on first error
python verify_notebooks.py --verbose --stop-on-error
```

### CI/CD Integration

```bash
# In CI pipeline, use timeout and generate report
python verify_notebooks.py --timeout 300 --save-report ci_report.json

# For faster CI, exclude long-running notebooks
python verify_notebooks.py --exclude "07_advanced_techniques.ipynb" --timeout 180
```

### Testing Specific Scenarios

```bash
# Test only basic notebooks
make verify-basic-only

# Test with custom exclusions
make verify-notebooks EXCLUDE="07_advanced_techniques.ipynb,08_practical_applications.ipynb"

# Test with extended timeout
make verify-notebooks TIMEOUT=900
```

## Troubleshooting

### Common Issues

#### 1. Jupyter Not Found
```bash
# Install Jupyter
pip install jupyter nbconvert

# Or using conda
conda install jupyter nbconvert
```

#### 2. Missing Dependencies
```bash
# Check what's missing
make test-setup

# Install all requirements
pip install -r requirements.txt
```

#### 3. Timeout Errors
```bash
# Increase timeout for long-running notebooks
python verify_notebooks.py --timeout 600

# Or exclude problematic notebooks during development
python verify_notebooks.py --exclude "slow_notebook.ipynb"
```

#### 4. Memory Issues
```bash
# Run notebooks one at a time (default behavior)
# Or reduce image sizes in notebooks
# Or increase system memory limits
```

### Debugging Failed Notebooks

1. **Use Verbose Mode**: See detailed error messages
   ```bash
   python verify_notebooks.py --verbose
   ```

2. **Run Individual Notebook**: Test specific notebook manually
   ```bash
   jupyter nbconvert --to notebook --execute notebooks/problematic_notebook.ipynb
   ```

3. **Check Dependencies**: Ensure all required packages are installed
   ```bash
   make test-setup
   ```

4. **Review Error Logs**: Check the error output for specific issues

## Report Format

When using `--save-report`, the JSON report includes:

```json
{
  "success": true,
  "notebooks": [
    {
      "notebook": "01_basic_operations.ipynb",
      "path": "notebooks/01_basic_operations.ipynb",
      "success": true,
      "execution_time": 45.2,
      "error": null,
      "output": "..."
    }
  ],
  "summary": {
    "total": 8,
    "passed": 8,
    "failed": 0,
    "total_time": 234.5
  }
}
```

## Integration with Development Workflow

### Pre-commit Hook

Add to `.git/hooks/pre-commit`:

```bash
#!/bin/bash
echo "Verifying notebooks before commit..."
make verify-notebooks-quick
if [ $? -ne 0 ]; then
    echo "❌ Notebook verification failed. Commit aborted."
    exit 1
fi
```

### GitHub Actions

```yaml
name: Verify Notebooks
on: [push, pull_request]
jobs:
  verify:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - uses: actions/setup-python@v2
        with:
          python-version: '3.8'
      - run: pip install -r requirements.txt
      - run: make verify-notebooks
```

## Best Practices

1. **Regular Verification**: Run verification before commits
2. **Timeout Management**: Set appropriate timeouts for different notebook types
3. **Selective Testing**: Use exclusions during development, full verification for releases
4. **Documentation**: Keep notebooks well-documented with clear error handling
5. **Dependencies**: Ensure all notebook dependencies are in `requirements.txt`
6. **Clean Outputs**: Clear notebook outputs before committing to avoid conflicts

## Contributing

When adding new notebooks:

1. Ensure they can run independently
2. Add appropriate error handling
3. Test with the verification system
4. Update exclusion lists if needed for development
5. Document any special requirements
