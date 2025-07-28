# OpenCV Source Modules Validation

This directory contains scripts to validate that all Python modules in the `src` directory can be imported and run without errors.

## Scripts

### 1. `validate_src_modules.py`
**Purpose**: Validates that all Python files in the `src` directory can be imported without errors.

**Features**:
- ✅ Checks for required dependencies (OpenCV, NumPy, Matplotlib, etc.)
- ✅ Handles relative imports in `__init__.py` files gracefully
- ✅ Provides detailed error reporting
- ✅ Shows success/failure summary with statistics
- ✅ Gives helpful guidance for fixing issues

**Usage**:
```bash
python3 validate_src_modules.py
```

**Output Example**:
```
OpenCV Source Modules Validation
============================================================
Checking dependencies...
----------------------------------------
  ✗ OpenCV (cv2) - NOT INSTALLED
  ✓ NumPy (numpy) - version 2.3.1
  ✗ Matplotlib (matplotlib) - NOT INSTALLED

Validating 32 Python files...
⚠️  OpenCV not installed - modules requiring cv2 will fail
------------------------------------------------------------
Testing: src/basic_operations/image_io.py
  ✗ FAILED: Missing OpenCV dependency

============================================================
VALIDATION SUMMARY
============================================================
Total files tested: 32
Successful imports: 9
Failed imports: 23
Success rate: 28.1%

💡 To fix OpenCV-related errors, install OpenCV:
   pip install opencv-python
```

### 2. `install_dependencies.py`
**Purpose**: Installs all required dependencies for the OpenCV project.

**Usage**:
```bash
python3 install_dependencies.py
```

**Installs**:
- opencv-python (OpenCV)
- numpy (NumPy)
- matplotlib (Matplotlib)
- scipy (SciPy)
- scikit-image (scikit-image)
- jupyter (Jupyter)
- pytest (pytest)

## Workflow

1. **First time setup**:
   ```bash
   python3 install_dependencies.py
   ```

2. **Validate modules**:
   ```bash
   python3 validate_src_modules.py
   ```

3. **If validation fails**, check the error messages and install missing dependencies manually:
   ```bash
   pip3 install opencv-python numpy matplotlib scipy scikit-image
   ```

## What the Validation Checks

The validation script checks that:

1. **Dependencies are available** - All required packages (cv2, numpy, etc.) can be imported
2. **Modules can be imported** - Each `.py` file can be imported without syntax errors
3. **Relative imports work** - `__init__.py` files with relative imports are handled correctly
4. **No import errors** - No missing module errors or circular import issues

## Exit Codes

- **0**: All modules validated successfully
- **Non-zero**: Number of modules that failed validation

This allows the script to be used in CI/CD pipelines or automated testing.

## Troubleshooting

### Common Issues

1. **"No module named 'cv2'"**
   - Solution: `pip3 install opencv-python`

2. **"attempted relative import with no known parent package"**
   - This is expected for `__init__.py` files and is handled by the script

3. **"No module named 'numpy'"**
   - Solution: `pip3 install numpy`

### Manual Installation

If the automatic installation fails, install dependencies manually:

```bash
pip3 install opencv-python numpy matplotlib scipy scikit-image jupyter pytest
```

## Notes

- The script skips `__init__.py` files that contain relative imports, as these are meant to be imported as part of a package
- All other Python files are tested for importability
- The script provides clear guidance on how to fix dependency issues
- Success rate shows the percentage of files that can be imported successfully 