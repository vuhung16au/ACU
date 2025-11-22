# Python Host-Based Intrusion Detection System - Implementation Plan

## Project Overview

Build a host-based intrusion detection system in Python 3.9 with standard venv, automated test scripts, and comprehensive documentation covering no-change and intrusion scenarios for macOS and Linux.

---

## Project Structure

Create `IntrusionDetection/` directory with:
- `ids.py` - Main intrusion detection script
- `test-folder/` - Test directory with diverse file types
- `README.md` - Comprehensive documentation
- `.venv/` - Virtual environment (Python 3.9, standard venv)
- `.gitignore` - Exclude venv, test outputs, pyc files
- `scripts/` - Test automation scripts
  - `setup_test_env.sh` - Initialize test environment
  - `test_no_changes.sh` - Test baseline verification (no changes)
  - `test_intrusion.sh` - Test intrusion detection scenarios
  - `run_all_tests.sh` - Master test runner
  - `cleanup.sh` - Reset test environment

---

## Core Components

### 1. Environment Setup

- Create Python 3.9 virtual environment using standard venv module
- All required modules are Python built-ins (os, sys, argparse, hashlib, json, stat, time)
- Platform: macOS and Linux compatible

### 2. Test Environment Setup

Populate `test-folder/` with:
- **Regular files:**
  - `important.txt` - Sample text file
  - `config.conf` - Configuration file
  - `data.bin` - Binary file
  - `empty.txt` - Empty file
- **Subdirectories:**
  - `documents/` with nested files
  - `logs/` with log files
- **Symbolic links:**
  - `link_to_important.txt` - Symlink to regular file
  - `link_to_documents` - Symlink to directory
- **Different permissions:**
  - Files with 644, 755, 600 permissions
  - Directories with 755, 700 permissions

### 3. Verification File Creation (`-c` option)

Implement data collection to capture:
- Full path and filename
- File type (regular file, directory, symlink)
- Access mode in text format (e.g., `-rwxr--r--`)
- Owner ID and group ID
- Last modification time (mtime)
- Last status change time (ctime)
- MD5 checksum (for regular files only)

Store data in JSON format for easy parsing and human readability.

### 4. File Verification Logic (`-o` option) - Option A

**Command Format:**
```bash
python ids.py -c baseline.txt                    # Create verification file
python ids.py -o results.txt baseline.txt        # Verify using baseline.txt
```

Compare current file system state against verification file:
- Read verification file (positional argument after -o)
- Check each file/directory for existence
- Compare all properties (permissions, ownership, timestamps, MD5)
- Detect new files not in verification database
- Report: CHANGED, ADDED, DELETED, or OK status

Display results to screen and save to output file.

### 5. Command-Line Interface

Arguments:
- `-c <verification_file>` - Create verification file and display "File created"
- `-o <output_file> <verification_file>` - Display results on screen and save to output file
- `-h, --help` - Show usage information

Examples:
```bash
# Create baseline
python ids.py -c baseline.txt

# Verify against baseline, output to screen and file
python ids.py -o results.txt baseline.txt
```

### 6. README.md Structure

**Overview:**
- Purpose: Host-based intrusion detection system for file integrity monitoring
- What is host-based intrusion detection
- Key features: MD5 checksums, permission tracking, timestamp monitoring

**Requirements:**
- Python 3.9+
- Platform: macOS and Linux
- No external dependencies (uses Python built-ins)

**Installation:**
```bash
cd IntrusionDetection
python3.9 -m venv .venv
source .venv/bin/activate  # macOS/Linux
```

**Script Parameters:**
- `-c <verification_file>` - Creates a verification database containing file metadata
- `-o <output_file> <verification_file>` - Verifies files against baseline, outputs to file and screen
- `-h` - Display help message

**Example Command Lines:**
```bash
# Create baseline verification file
python ids.py -c baseline.txt

# Check for intrusions
python ids.py -o intrusion_report.txt baseline.txt

# Using from scripts
./scripts/test_no_changes.sh
./scripts/test_intrusion.sh
./scripts/run_all_tests.sh
```

**Testing:**
- Automated test scripts in `scripts/` directory
- `test_no_changes.sh` - Verifies clean baseline (no changes)
- `test_intrusion.sh` - Tests detection of modifications, additions, deletions
- `run_all_tests.sh` - Runs all tests sequentially with color-coded output

### 7. Test Scripts

**`scripts/setup_test_env.sh`:**
- Create fresh test-folder structure
- Populate with initial files and directories
- Create symbolic links
- Set appropriate permissions (644, 755, 600)
- Output: "Test environment created"

**`scripts/test_no_changes.sh`:**
1. Run setup_test_env.sh
2. Create baseline: `python ids.py -c baseline.txt`
3. Immediately verify: `python ids.py -o results.txt baseline.txt`
4. Check output for all files showing "[OK]" status
5. Display green "✓ PASS: No changes test" or red "✗ FAIL"

**`scripts/test_intrusion.sh`:**
1. Run setup_test_env.sh
2. Create baseline: `python ids.py -c baseline.txt`
3. Sleep 2 seconds (ensure timestamp difference)
4. Simulate intrusions:
   - Modify `important.txt` content
   - Change `config.conf` permissions (chmod 777)
   - Delete `data.bin`
   - Add new `backdoor.sh`
   - Modify `documents/secret.txt`
5. Run verification: `python ids.py -o intrusion_report.txt baseline.txt`
6. Verify output shows [CHANGED], [DELETED], [ADDED] tags
7. Display green "✓ PASS: Intrusion detection test" or red "✗ FAIL"

**`scripts/run_all_tests.sh`:**
- Run cleanup first
- Execute test_no_changes.sh
- Execute test_intrusion.sh
- Display summary with pass/fail counts
- Exit with appropriate code (0 for all pass, 1 for any fail)

**`scripts/cleanup.sh`:**
- Remove test-folder directory recursively
- Remove all .txt verification/output files
- Remove __pycache__ if exists
- Output: "Cleanup complete"

### 8. Test Files for Scenarios

**Scenario 1: No Changes**
- Create baseline immediately after setup
- Run verification without any modifications
- Expected: All files report "[OK]" status
- Summary: "No intrusions detected. All N files verified successfully."

**Scenario 2: Intrusion Detected**
Test files and modifications:
- `test-folder/important.txt` - Content modified ("HACKED" appended)
- `test-folder/config.conf` - Permissions changed (644 → 777)
- `test-folder/data.bin` - File deleted
- `test-folder/documents/secret.txt` - Content modified
- `test-folder/backdoor.sh` - New file added (not in baseline)

Expected output format:
```
[CHANGED] test-folder/important.txt (MD5 mismatch)
[CHANGED] test-folder/config.conf (Permissions changed)
[DELETED] test-folder/data.bin
[ADDED] test-folder/backdoor.sh
[CHANGED] test-folder/documents/secret.txt (MD5 mismatch)

INTRUSION DETECTED!
Summary: 5 issues found (3 changed, 1 deleted, 1 added)
```

---

## Implementation Details

### Timestamp Handling

- Use `os.stat()` with `follow_symlinks=False` to avoid modifying access times
- Store mtime and ctime as Unix timestamps (float)
- Compare with tolerance of 0.01 seconds to handle filesystem precision

### MD5 Calculation

- Read files in 8KB chunks for memory efficiency
- Only calculate for regular files (skip directories and symlinks)
- Handle file read errors gracefully

### Permission Format

- Convert `st_mode` to readable string format using `stat.filemode()`
- Format: `-rwxr-xr-x` (includes file type indicator)

### Output Format

- Color-coded in terminal (green [OK], yellow [CHANGED], red [DELETED], blue [ADDED])
- Plain text in output file
- Summary statistics at end
- Both stdout and file receive same content (minus colors for file)

### File Path Handling

- Store absolute paths in verification file
- Use `os.path.abspath()` for consistency
- Support recursive directory traversal

### JSON Structure

```json
{
  "metadata": {
    "created": "timestamp",
    "root_path": "/absolute/path"
  },
  "files": {
    "/absolute/path/file.txt": {
      "type": "regular file",
      "mode": "-rw-r--r--",
      "uid": 501,
      "gid": 20,
      "mtime": 1234567890.123,
      "ctime": 1234567890.123,
      "md5": "abc123..." 
    }
  }
}
```

---

## Implementation Tasks

1. ✅ Create IntrusionDetection/ directory and .gitignore
2. ✅ Create Python 3.9 virtual environment with standard venv
3. ✅ Write scripts/setup_test_env.sh to populate test-folder
4. ✅ Build ids.py with Option A CLI, data collection, verification
5. ✅ Write test_no_changes.sh, test_intrusion.sh, run_all_tests.sh
6. ✅ Write scripts/cleanup.sh for environment reset
7. ✅ Create comprehensive README.md with specified structure
8. ✅ Execute all test scripts and verify functionality

---

## Technical Requirements

- **Language**: Python 3.9
- **Platform**: macOS and Linux
- **Dependencies**: None (Python built-ins only)
- **Virtual Environment**: Standard venv module
- **Testing**: Bash scripts for automation
- **Documentation**: Comprehensive README.md

---

## Success Criteria

- ✅ All test scripts execute successfully
- ✅ No changes test passes (baseline verification)
- ✅ Intrusion detection test passes (detects all 5 types of changes)
- ✅ Color-coded output in terminal
- ✅ Detailed reports saved to files
- ✅ Comprehensive documentation
- ✅ Clean project structure

---

## Plan Version

**Version**: 1.0  
**Date**: 2025-11-22  
**Status**: Completed  
**Python Version**: 3.9  
**Target Platform**: macOS and Linux

---

## Notes

- Option A command-line interface chosen (separate positional argument for verification file)
- No requirements.txt needed (all modules are Python built-ins)
- No uv tool used (standard venv instead)
- Test suite includes both positive (no changes) and negative (intrusion) scenarios
- All 15 implementation tasks completed successfully
- Test results: 2/2 tests passed (100% success rate)

