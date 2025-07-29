# Examples Verification

This directory contains scripts to verify that all Python example files can run without errors.

## Files

- `verify_examples.py` - Main Python verification script
- `verify_examples.sh` - Shell script wrapper for easy execution
- `VERIFICATION_README.md` - This documentation file

## Usage

### Option 1: Using the shell script (Recommended)

```bash
./verify_examples.sh
```

This script will:
1. Check if a virtual environment exists, create one if needed
2. Install dependencies from `requirements.txt`
3. Run the verification script
4. Report results

### Option 2: Manual execution

```bash
# Create and activate virtual environment
python3 -m venv venv
source venv/bin/activate

# Install dependencies
pip install -r requirements.txt

# Run verification
python verify_examples.py
```

## What the verification does

The verification script:

1. **Finds all Python files** in the `examples/` directory
2. **Sets up the environment** with proper Python path for imports
3. **Executes each file** using subprocess with timeout protection
4. **Reports results** with detailed error messages if any files fail
5. **Provides summary** of passed/failed files

## Timeout settings

- **Regular demos**: 30 seconds timeout
- **Advanced techniques demo**: 60 seconds timeout (due to complexity)

## Expected output

```
🚀 Starting verification of examples/*.py files...
============================================================
Found 8 Python files to verify:
  - 01_basic_operations_demo.py
  - 02_image_filtering_demo.py
  - 03_transformations_demo.py
  - 04_morphological_ops_demo.py
  - 05_feature_detection_demo.py
  - 06_color_processing_demo.py
  - 07_advanced_techniques_demo.py
  - 08_practical_applications_demo.py

🔍 Testing advanced_techniques_demo.py...
✅ advanced_techniques_demo.py - PASSED

[... more files ...]

============================================================
📊 VERIFICATION SUMMARY
============================================================
Total files tested: 8
✅ Passed: 8
❌ Failed: 0

🎉 All example files passed verification!
```

## Troubleshooting

### Common issues:

1. **Missing dependencies**: Run `pip install -r requirements.txt`
2. **Import errors**: Ensure the virtual environment is activated
3. **Timeout errors**: The advanced techniques demo may take longer to run
4. **Permission errors**: Make sure the shell script is executable: `chmod +x verify_examples.sh`

### Exit codes:

- `0`: All files passed verification
- `1`: One or more files failed verification

## Adding new examples

When adding new example files to the `examples/` directory:

1. The verification script will automatically detect and test them
2. Ensure the file has proper imports and error handling
3. Test the file manually before running verification
4. Consider timeout requirements for complex demos 