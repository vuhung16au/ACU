#!/usr/bin/env python3.9
"""
Host-Based Intrusion Detection System
A file integrity monitoring tool that detects changes to files, directories, and symbolic links.
"""

import os
import sys
import argparse
import hashlib
import json
import stat
import time
from pathlib import Path


class Colors:
    """ANSI color codes for terminal output"""
    GREEN = '\033[92m'
    YELLOW = '\033[93m'
    RED = '\033[91m'
    BLUE = '\033[94m'
    RESET = '\033[0m'
    BOLD = '\033[1m'


def calculate_md5(filepath):
    """
    Calculate MD5 checksum of a file.
    Reads file in chunks to handle large files efficiently.
    
    Args:
        filepath: Path to the file
        
    Returns:
        MD5 hash as hexadecimal string, or None if error
    """
    try:
        hash_md5 = hashlib.md5()
        with open(filepath, "rb") as f:
            # Read in 8KB chunks
            for chunk in iter(lambda: f.read(8192), b""):
                hash_md5.update(chunk)
        return hash_md5.hexdigest()
    except Exception as e:
        print(f"Error calculating MD5 for {filepath}: {e}", file=sys.stderr)
        return None


def get_file_info(filepath):
    """
    Collect metadata about a file, directory, or symbolic link.
    Uses follow_symlinks=False to avoid modifying access times.
    
    Args:
        filepath: Path to the file/directory/link
        
    Returns:
        Dictionary containing file metadata
    """
    try:
        # Get file stats without following symlinks
        file_stat = os.stat(filepath, follow_symlinks=False)
        
        # Determine file type
        if stat.S_ISREG(file_stat.st_mode):
            file_type = "regular file"
            # Calculate MD5 only for regular files
            md5_hash = calculate_md5(filepath)
        elif stat.S_ISDIR(file_stat.st_mode):
            file_type = "directory"
            md5_hash = None
        elif stat.S_ISLNK(file_stat.st_mode):
            file_type = "symlink"
            md5_hash = None
        else:
            file_type = "other"
            md5_hash = None
        
        # Convert mode to readable format
        mode_str = stat.filemode(file_stat.st_mode)
        
        info = {
            "type": file_type,
            "mode": mode_str,
            "uid": file_stat.st_uid,
            "gid": file_stat.st_gid,
            "mtime": file_stat.st_mtime,
            "ctime": file_stat.st_ctime,
        }
        
        # Add MD5 only if it's a regular file
        if md5_hash is not None:
            info["md5"] = md5_hash
        
        return info
        
    except Exception as e:
        print(f"Error getting info for {filepath}: {e}", file=sys.stderr)
        return None


def scan_directory(root_path):
    """
    Recursively scan a directory and collect information about all files.
    
    Args:
        root_path: Root directory to scan
        
    Returns:
        Dictionary mapping absolute paths to file information
    """
    files_info = {}
    root_path = os.path.abspath(root_path)
    
    try:
        for dirpath, dirnames, filenames in os.walk(root_path, followlinks=False):
            # Process the directory itself
            abs_dirpath = os.path.abspath(dirpath)
            dir_info = get_file_info(abs_dirpath)
            if dir_info:
                files_info[abs_dirpath] = dir_info
            
            # Process symbolic links in directory
            for dirname in dirnames:
                full_path = os.path.join(dirpath, dirname)
                if os.path.islink(full_path):
                    abs_path = os.path.abspath(full_path)
                    link_info = get_file_info(abs_path)
                    if link_info:
                        files_info[abs_path] = link_info
            
            # Process files (including symbolic links to files)
            for filename in filenames:
                full_path = os.path.join(dirpath, filename)
                abs_path = os.path.abspath(full_path)
                file_info = get_file_info(abs_path)
                if file_info:
                    files_info[abs_path] = file_info
    
    except Exception as e:
        print(f"Error scanning directory {root_path}: {e}", file=sys.stderr)
    
    return files_info


def create_verification_file(verification_file, target_path="test-folder"):
    """
    Create a verification file containing metadata about all files in target path.
    
    Args:
        verification_file: Path to the verification file to create
        target_path: Directory to scan (default: test-folder)
    """
    # Determine the project root (parent of this script)
    script_dir = os.path.dirname(os.path.abspath(__file__))
    target_full_path = os.path.join(script_dir, target_path)
    
    if not os.path.exists(target_full_path):
        print(f"Error: Target path '{target_full_path}' does not exist.", file=sys.stderr)
        sys.exit(1)
    
    print(f"Scanning directory: {target_full_path}")
    files_info = scan_directory(target_full_path)
    
    # Create verification database
    verification_data = {
        "metadata": {
            "created": time.time(),
            "created_readable": time.strftime("%Y-%m-%d %H:%M:%S"),
            "root_path": target_full_path,
            "file_count": len(files_info)
        },
        "files": files_info
    }
    
    # Write to JSON file
    try:
        with open(verification_file, 'w') as f:
            json.dump(verification_data, f, indent=2)
        print(f"File created")
        print(f"Verification database saved to: {verification_file}")
        print(f"Total files/directories tracked: {len(files_info)}")
    except Exception as e:
        print(f"Error writing verification file: {e}", file=sys.stderr)
        sys.exit(1)


def compare_file_info(baseline_info, current_info, filepath):
    """
    Compare baseline and current file information.
    
    Args:
        baseline_info: File info from verification database
        current_info: Current file info
        filepath: Path to the file
        
    Returns:
        List of differences (empty if no changes)
    """
    differences = []
    
    # Check file type
    if baseline_info.get("type") != current_info.get("type"):
        differences.append(f"Type changed: {baseline_info.get('type')} -> {current_info.get('type')}")
    
    # Check permissions
    if baseline_info.get("mode") != current_info.get("mode"):
        differences.append(f"Permissions changed: {baseline_info.get('mode')} -> {current_info.get('mode')}")
    
    # Check ownership
    if baseline_info.get("uid") != current_info.get("uid"):
        differences.append(f"Owner UID changed: {baseline_info.get('uid')} -> {current_info.get('uid')}")
    
    if baseline_info.get("gid") != current_info.get("gid"):
        differences.append(f"Group GID changed: {baseline_info.get('gid')} -> {current_info.get('gid')}")
    
    # Check modification time (with small tolerance for filesystem precision)
    mtime_baseline = baseline_info.get("mtime", 0)
    mtime_current = current_info.get("mtime", 0)
    if abs(mtime_baseline - mtime_current) > 0.01:
        differences.append(f"Modification time changed")
    
    # Check MD5 for regular files
    if baseline_info.get("type") == "regular file":
        if baseline_info.get("md5") != current_info.get("md5"):
            differences.append(f"MD5 checksum mismatch")
    
    return differences


def verify_files(verification_file, output_file):
    """
    Verify current file system state against verification database.
    Outputs results to screen and file.
    
    Args:
        verification_file: Path to the verification database
        output_file: Path to save the verification report
    """
    # Load verification database
    try:
        with open(verification_file, 'r') as f:
            verification_data = json.load(f)
    except FileNotFoundError:
        print(f"Error: Verification file '{verification_file}' not found.", file=sys.stderr)
        sys.exit(1)
    except json.JSONDecodeError as e:
        print(f"Error: Invalid verification file format: {e}", file=sys.stderr)
        sys.exit(1)
    
    baseline_files = verification_data.get("files", {})
    root_path = verification_data.get("metadata", {}).get("root_path")
    
    if not root_path or not os.path.exists(root_path):
        print(f"Error: Root path from verification file does not exist: {root_path}", file=sys.stderr)
        sys.exit(1)
    
    print(f"Verifying files against baseline: {verification_file}")
    print(f"Root path: {root_path}")
    print(f"Baseline created: {verification_data.get('metadata', {}).get('created_readable')}")
    print("=" * 80)
    
    # Scan current directory state
    current_files = scan_directory(root_path)
    
    # Track statistics
    stats = {
        "ok": 0,
        "changed": 0,
        "deleted": 0,
        "added": 0
    }
    
    results = []
    
    # Check files in baseline
    for filepath, baseline_info in baseline_files.items():
        if filepath in current_files:
            # File exists, compare info
            current_info = current_files[filepath]
            differences = compare_file_info(baseline_info, current_info, filepath)
            
            if differences:
                status = "CHANGED"
                stats["changed"] += 1
                color = Colors.YELLOW
                details = "; ".join(differences)
                result_line = f"[{status}] {filepath} ({details})"
            else:
                status = "OK"
                stats["ok"] += 1
                color = Colors.GREEN
                result_line = f"[{status}] {filepath}"
            
            # Print to screen with color
            print(f"{color}{result_line}{Colors.RESET}")
            # Store plain text for file
            results.append(result_line)
        else:
            # File was deleted
            status = "DELETED"
            stats["deleted"] += 1
            color = Colors.RED
            result_line = f"[{status}] {filepath}"
            print(f"{color}{result_line}{Colors.RESET}")
            results.append(result_line)
    
    # Check for new files
    for filepath in current_files:
        if filepath not in baseline_files:
            status = "ADDED"
            stats["added"] += 1
            color = Colors.BLUE
            result_line = f"[{status}] {filepath}"
            print(f"{color}{result_line}{Colors.RESET}")
            results.append(result_line)
    
    # Print summary
    print("=" * 80)
    
    intrusion_detected = stats["changed"] > 0 or stats["deleted"] > 0 or stats["added"] > 0
    
    if intrusion_detected:
        summary = f"\n{Colors.RED}{Colors.BOLD}INTRUSION DETECTED!{Colors.RESET}"
        print(summary)
        summary_plain = "\nINTRUSION DETECTED!"
    else:
        summary = f"\n{Colors.GREEN}No intrusions detected. All {stats['ok']} files verified successfully.{Colors.RESET}"
        print(summary)
        summary_plain = f"\nNo intrusions detected. All {stats['ok']} files verified successfully."
    
    stats_summary = f"Summary: {stats['ok']} OK, {stats['changed']} changed, {stats['deleted']} deleted, {stats['added']} added"
    print(stats_summary)
    
    # Write results to output file
    try:
        with open(output_file, 'w') as f:
            f.write(f"File Integrity Verification Report\n")
            f.write(f"Generated: {time.strftime('%Y-%m-%d %H:%M:%S')}\n")
            f.write(f"Verification file: {verification_file}\n")
            f.write(f"Root path: {root_path}\n")
            f.write("=" * 80 + "\n\n")
            
            for result in results:
                f.write(result + "\n")
            
            f.write("\n" + "=" * 80 + "\n")
            f.write(summary_plain + "\n")
            f.write(stats_summary + "\n")
        
        print(f"\nResults saved to: {output_file}")
    except Exception as e:
        print(f"Error writing output file: {e}", file=sys.stderr)
        sys.exit(1)


def main():
    """Main entry point for the IDS application"""
    parser = argparse.ArgumentParser(
        description="Host-Based Intrusion Detection System - File Integrity Monitor",
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog="""
Examples:
  Create verification baseline:
    python ids.py -c baseline.txt
  
  Verify files against baseline:
    python ids.py -o results.txt baseline.txt
        """
    )
    
    parser.add_argument(
        '-c',
        metavar='VERIFICATION_FILE',
        dest='create_verification',
        help='Create a verification database file'
    )
    
    parser.add_argument(
        '-o',
        metavar='OUTPUT_FILE',
        dest='output_file',
        help='Verify files and save results to OUTPUT_FILE'
    )
    
    parser.add_argument(
        'verification_file',
        nargs='?',
        help='Verification database file to use for verification (required with -o)'
    )
    
    args = parser.parse_args()
    
    # Validate arguments
    if not args.create_verification and not args.output_file:
        parser.error("Either -c or -o option must be specified")
    
    if args.create_verification:
        # Create verification file
        create_verification_file(args.create_verification)
    
    if args.output_file:
        # Verify files
        if not args.verification_file:
            parser.error("Verification file must be specified when using -o option")
        verify_files(args.verification_file, args.output_file)


if __name__ == "__main__":
    main()

