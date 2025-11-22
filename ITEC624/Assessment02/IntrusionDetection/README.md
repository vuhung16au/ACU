# Host-Based Intrusion Detection System

## Overview

This project implements a **Host-Based Intrusion Detection System (IDS)** in Python for file integrity monitoring. The system helps administrators detect unauthorized changes to files, directories, and symbolic links by creating a baseline verification database and comparing the current file system state against it.

### What is Host-Based Intrusion Detection?

Host-based intrusion detection monitors the integrity of files on a computer system to detect potential security breaches. By tracking file attributes such as:
- MD5 checksums (content verification)
- File permissions and ownership
- Modification timestamps
- File types (regular files, directories, symbolic links)

The system can alert administrators to unauthorized modifications, deletions, or additions to critical system files.

### Key Features

- **MD5 Checksums**: Cryptographic hash verification for detecting content changes
- **Permission Tracking**: Monitors changes to file access modes
- **Ownership Monitoring**: Tracks user and group ownership changes
- **Timestamp Detection**: Identifies when files were last modified
- **Comprehensive Coverage**: Monitors regular files, directories, and symbolic links
- **Detailed Reporting**: Color-coded terminal output and text file reports
- **Automated Testing**: Includes test scripts for validation

### Project Structure

```
IntrusionDetection/
├── ids.py                      # Main IDS script
├── README.md                   # This file
├── .gitignore                  # Git ignore rules
├── .venv/                      # Python virtual environment
├── scripts/                    # Test automation scripts
│   ├── setup_test_env.sh      # Creates test environment
│   ├── test_no_changes.sh     # Tests baseline verification
│   ├── test_intrusion.sh      # Tests intrusion detection
│   ├── run_all_tests.sh       # Runs all tests
│   └── cleanup.sh             # Cleans up test artifacts
└── test-folder/               # Test directory (created by scripts)
```

---

## How to Run the Project

### Prerequisites

- **Python 3.9+** installed on your system
- **Platform**: macOS or Linux
- **No external dependencies** required (uses Python built-ins only)

### Installation

1. **Navigate to the project directory**:

```bash
cd IntrusionDetection
```

2. **Create a virtual environment** (recommended):

```bash
python3.9 -m venv .venv
```

3. **Activate the virtual environment**:

```bash
# On macOS/Linux
source .venv/bin/activate
```

4. **Verify installation**:

```bash
python ids.py -h
```

---

## Script Parameters

The IDS script accepts the following command-line parameters:

### `-c <verification_file>`

**Creates a verification database** containing metadata about all files in the target directory (default: `test-folder/`).

**What it does**:
- Recursively scans the target directory
- Collects file metadata (type, permissions, ownership, timestamps)
- Calculates MD5 checksums for regular files
- Stores all information in a JSON-formatted verification file
- Displays "File created" message upon success

**Example**:
```bash
python ids.py -c baseline.txt
```

**Output**:
- Creates `baseline.txt` with the verification database
- Prints file creation confirmation and statistics

---

### `-o <output_file> <verification_file>`

**Verifies the current file system state** against a previously created verification database.

**What it does**:
- Loads the baseline verification database
- Scans the current state of monitored directories
- Compares each file's current metadata against the baseline
- Detects changes, deletions, and additions
- Displays results on screen with color coding
- Saves detailed report to the output file

**Parameters**:
- `output_file`: Path where the verification report will be saved
- `verification_file`: Path to the baseline verification database (positional argument)

**Example**:
```bash
python ids.py -o results.txt baseline.txt
```

**Output**:
- Displays verification results on screen (color-coded)
- Saves detailed report to `results.txt`
- Shows summary statistics

**Detection Types**:
- `[OK]` - No changes detected (green)
- `[CHANGED]` - File modified (yellow)
- `[DELETED]` - File removed (red)
- `[ADDED]` - New file not in baseline (blue)

---

### `-h, --help`

Displays usage information and examples.

```bash
python ids.py -h
```

---

## Example Command Lines

### Basic Usage

#### 1. Create a Baseline Verification File

```bash
python ids.py -c baseline.txt
```

**Output**:
```
Scanning directory: /path/to/IntrusionDetection/test-folder
File created
Verification database saved to: baseline.txt
Total files/directories tracked: 15
```

#### 2. Verify Files Against Baseline

```bash
python ids.py -o results.txt baseline.txt
```

**Output** (when no changes):
```
Verifying files against baseline: baseline.txt
Root path: /path/to/IntrusionDetection/test-folder
Baseline created: 2025-11-22 10:30:00
================================================================================
[OK] /path/to/test-folder/important.txt
[OK] /path/to/test-folder/config.conf
...
================================================================================

No intrusions detected. All 15 files verified successfully.
Summary: 15 OK, 0 changed, 0 deleted, 0 added

Results saved to: results.txt
```

**Output** (when intrusions detected):
```
Verifying files against baseline: baseline.txt
Root path: /path/to/IntrusionDetection/test-folder
Baseline created: 2025-11-22 10:30:00
================================================================================
[CHANGED] /path/to/test-folder/important.txt (MD5 checksum mismatch)
[CHANGED] /path/to/test-folder/config.conf (Permissions changed: -rw-r--r-- -> -rwxrwxrwx)
[DELETED] /path/to/test-folder/data.bin
[ADDED] /path/to/test-folder/backdoor.sh
[OK] /path/to/test-folder/empty.txt
...
================================================================================

INTRUSION DETECTED!
Summary: 11 OK, 2 changed, 1 deleted, 1 added

Results saved to: results.txt
```

---

### Using Automated Test Scripts

The project includes automated test scripts for easy validation:

#### Setup Test Environment

```bash
./scripts/setup_test_env.sh
```

Creates a test directory with sample files, directories, and symbolic links.

#### Run No-Changes Test

```bash
./scripts/test_no_changes.sh
```

Tests that the IDS correctly reports no changes when files are unchanged.

#### Run Intrusion Detection Test

```bash
./scripts/test_intrusion.sh
```

Simulates various types of intrusions and verifies detection:
- File content modification
- Permission changes
- File deletion
- New file addition
- Subdirectory file modification

#### Run All Tests

```bash
./scripts/run_all_tests.sh
```

Executes all test scenarios and provides a comprehensive summary.

#### Clean Up Test Artifacts

```bash
./scripts/cleanup.sh
```

Removes all test files, verification databases, and output reports.

---

## Testing

### Automated Test Suite

The project includes a comprehensive test suite with two main scenarios:

#### Test Scenario 1: No Changes Detection

**Purpose**: Verify that the IDS correctly identifies an unchanged file system.

**Process**:
1. Create fresh test environment
2. Generate baseline verification file
3. Immediately verify (no modifications)
4. Expect all files to show `[OK]` status

**Expected Result**: "No intrusions detected" message

**Run**:
```bash
./scripts/test_no_changes.sh
```

---

#### Test Scenario 2: Intrusion Detection

**Purpose**: Verify that the IDS detects various types of file system changes.

**Simulated Intrusions**:
1. **Content Modification**: `important.txt` - appends "HACKED!" text
2. **Permission Change**: `config.conf` - changes from 644 to 777
3. **File Deletion**: `data.bin` - removes file
4. **File Addition**: `backdoor.sh` - creates new executable
5. **Subdirectory Modification**: `documents/secret.txt` - modifies content

**Expected Results**:
- All 5 intrusions detected correctly
- `[CHANGED]` tags for modifications
- `[DELETED]` tag for removed file
- `[ADDED]` tag for new file
- "INTRUSION DETECTED!" alert

**Run**:
```bash
./scripts/test_intrusion.sh
```

---

### Running All Tests

Execute the complete test suite:

```bash
./scripts/run_all_tests.sh
```

**Sample Output**:
```
======================================================================
Host-Based IDS - Test Suite
======================================================================

Cleaning up previous test artifacts...

Running Test 1: No Changes Detection...
----------------------------------------------------------------------
======================================================================
Test Case 1: No Changes Detection
======================================================================
...
✓ PASS: No Changes Test
======================================================================

Running Test 2: Intrusion Detection...
----------------------------------------------------------------------
======================================================================
Test Case 2: Intrusion Detection
======================================================================
...
✓ PASS: Intrusion Detection Test
======================================================================

======================================================================
Test Suite Summary
======================================================================

Total Tests: 2
Passed: 2
Failed: 0

======================================================================
✓ ALL TESTS PASSED
======================================================================
```

---

### Manual Testing

You can also manually test the IDS:

1. **Create test environment**:
```bash
./scripts/setup_test_env.sh
```

2. **Create baseline**:
```bash
python ids.py -c my_baseline.txt
```

3. **Make some changes**:
```bash
echo "Modified!" >> test-folder/important.txt
chmod 777 test-folder/config.conf
rm test-folder/data.bin
```

4. **Verify and detect changes**:
```bash
python ids.py -o my_report.txt my_baseline.txt
```

5. **Review the report**:
```bash
cat my_report.txt
```

---

## Technical Details

### File Metadata Collected

For each file, directory, and symbolic link, the IDS collects:

| Attribute | Description | Used For |
|-----------|-------------|----------|
| **Path** | Absolute file path | Identification |
| **Type** | File type (regular, directory, symlink) | Classification |
| **Mode** | Access permissions in text format | Permission monitoring |
| **UID** | Owner user ID | Ownership tracking |
| **GID** | Owner group ID | Ownership tracking |
| **mtime** | Modification timestamp | Change detection |
| **ctime** | Status change timestamp | Change detection |
| **MD5** | Content hash (regular files only) | Content verification |

### Verification Database Format

The verification file is stored in JSON format for easy parsing:

```json
{
  "metadata": {
    "created": 1732234567.890,
    "created_readable": "2025-11-22 10:30:00",
    "root_path": "/absolute/path/to/test-folder",
    "file_count": 15
  },
  "files": {
    "/absolute/path/to/file.txt": {
      "type": "regular file",
      "mode": "-rw-r--r--",
      "uid": 501,
      "gid": 20,
      "mtime": 1732234560.123,
      "ctime": 1732234560.123,
      "md5": "5d41402abc4b2a76b9719d911017c592"
    }
  }
}
```

---

## Troubleshooting

### Issue: "Target path does not exist"

**Solution**: Ensure the `test-folder` directory exists. Run:
```bash
./scripts/setup_test_env.sh
```

### Issue: "Verification file not found"

**Solution**: Create a baseline verification file first:
```bash
python ids.py -c baseline.txt
```

### Issue: Permission denied

**Solution**: Ensure scripts are executable:
```bash
chmod +x scripts/*.sh
```

### Issue: Python not found

**Solution**: Use the full path to Python 3.9:
```bash
/opt/homebrew/bin/python3.9 ids.py -h
```

Or activate the virtual environment:
```bash
source .venv/bin/activate
```

---

## License

This project is created for educational purposes as part of the ITEC624 Assessment.

---

## Authors

Developed for ACU ITEC624 - Host-Based Intrusion Detection System Assessment

---

## References

- [Tripwire File Integrity Monitoring](https://www.tripwire.com)
- [AIDE - Advanced Intrusion Detection Environment](https://aide.github.io)
- [Python hashlib Documentation](https://docs.python.org/3/library/hashlib.html)
- [Python os.stat Documentation](https://docs.python.org/3/library/os.html#os.stat)

