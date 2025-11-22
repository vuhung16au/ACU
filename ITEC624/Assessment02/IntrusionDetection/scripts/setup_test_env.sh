#!/bin/bash

# Setup Test Environment for IDS Testing
# This script creates a test-folder with diverse file types, directories, and symbolic links

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_DIR="$(dirname "$SCRIPT_DIR")"
TEST_DIR="$PROJECT_DIR/test-folder"

# Remove existing test folder if it exists
if [ -d "$TEST_DIR" ]; then
    rm -rf "$TEST_DIR"
fi

# Create main test directory
mkdir -p "$TEST_DIR"

# Create subdirectories
mkdir -p "$TEST_DIR/documents"
mkdir -p "$TEST_DIR/logs"

# Create regular files
echo "This is an important file containing sensitive data." > "$TEST_DIR/important.txt"
echo "# Configuration File" > "$TEST_DIR/config.conf"
echo -e "server=localhost\nport=8080\nssl=true" >> "$TEST_DIR/config.conf"

# Create binary file (random data)
dd if=/dev/urandom of="$TEST_DIR/data.bin" bs=1024 count=10 2>/dev/null

# Create empty file
touch "$TEST_DIR/empty.txt"

# Create files in documents subdirectory
echo "Top Secret Information" > "$TEST_DIR/documents/secret.txt"
echo "Public Information" > "$TEST_DIR/documents/public.txt"
echo "Meeting notes from 2025-11-22" > "$TEST_DIR/documents/notes.txt"

# Create files in logs subdirectory
echo "[2025-11-22 10:00:00] System started" > "$TEST_DIR/logs/system.log"
echo "[2025-11-22 10:05:00] User logged in" > "$TEST_DIR/logs/access.log"

# Create symbolic links
ln -s "$TEST_DIR/important.txt" "$TEST_DIR/link_to_important.txt"
ln -s "$TEST_DIR/documents" "$TEST_DIR/link_to_documents"

# Set different permissions
chmod 644 "$TEST_DIR/important.txt"      # rw-r--r--
chmod 644 "$TEST_DIR/config.conf"        # rw-r--r--
chmod 600 "$TEST_DIR/data.bin"           # rw-------
chmod 644 "$TEST_DIR/empty.txt"          # rw-r--r--
chmod 755 "$TEST_DIR/documents"          # rwxr-xr-x
chmod 700 "$TEST_DIR/logs"               # rwx------
chmod 644 "$TEST_DIR/documents/secret.txt"
chmod 644 "$TEST_DIR/documents/public.txt"
chmod 644 "$TEST_DIR/documents/notes.txt"
chmod 644 "$TEST_DIR/logs/system.log"
chmod 644 "$TEST_DIR/logs/access.log"

echo "Test environment created at: $TEST_DIR"
echo "Files created:"
find "$TEST_DIR" -type f | wc -l | xargs echo "  Regular files:"
find "$TEST_DIR" -type d | wc -l | xargs echo "  Directories:"
find "$TEST_DIR" -type l | wc -l | xargs echo "  Symbolic links:"

