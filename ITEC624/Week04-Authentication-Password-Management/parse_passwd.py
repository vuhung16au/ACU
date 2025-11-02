#!/usr/bin/env python3
"""
Parse /etc/passwd file and export user information to CSV format.

Usage:
    python parse_passwd.py [input_file] [output_file]

Arguments:
    input_file:  Path to /etc/passwd file (default: /etc/passwd)
    output_file: Path to output CSV file (default: passwd_users.csv)
"""

import csv
import sys
from pathlib import Path


def parse_passwd_file(passwd_path):
    """
    Parse /etc/passwd file and return list of user dictionaries.

    Args:
        passwd_path: Path to /etc/passwd file

    Returns:
        List of dictionaries containing user information
    """
    users = []

    try:
        with open(passwd_path, 'r', encoding='utf-8') as f:
            for line_num, line in enumerate(f, start=1):
                line = line.strip()

                # Skip empty lines and comments
                if not line or line.startswith('#'):
                    continue

                # Split by colon - /etc/passwd has 7 fields
                fields = line.split(':')

                if len(fields) != 7:
                    print(f"Warning: Line {line_num} has {len(fields)} fields (expected 7), skipping.",
                          file=sys.stderr)
                    continue

                user_info = {
                    'username': fields[0],
                    'password_field': fields[1],
                    'uid': fields[2],
                    'gid': fields[3],
                    'gecos': fields[4],
                    'home_directory': fields[5],
                    'shell': fields[6]
                }

                # Parse GECOS field if it contains comma-separated values
                gecos_parts = user_info['gecos'].split(',')
                user_info['full_name'] = gecos_parts[0] if gecos_parts[0] else ''
                user_info['room_number'] = gecos_parts[1] if len(gecos_parts) > 1 and gecos_parts[1] else ''
                user_info['work_phone'] = gecos_parts[2] if len(gecos_parts) > 2 and gecos_parts[2] else ''
                user_info['home_phone'] = gecos_parts[3] if len(gecos_parts) > 3 and gecos_parts[3] else ''
                user_info['other'] = ','.join(gecos_parts[4:]) if len(gecos_parts) > 4 else ''

                users.append(user_info)

    except FileNotFoundError:
        print(f"Error: File '{passwd_path}' not found.", file=sys.stderr)
        sys.exit(1)
    except PermissionError:
        print(f"Error: Permission denied reading '{passwd_path}'.", file=sys.stderr)
        print("Hint: You may need to run with sudo or copy the file first.", file=sys.stderr)
        sys.exit(1)
    except Exception as e:
        print(f"Error reading file '{passwd_path}': {e}", file=sys.stderr)
        sys.exit(1)

    return users


def write_csv(users, output_path):
    """
    Write user information to CSV file.

    Args:
        users: List of user dictionaries
        output_path: Path to output CSV file
    """
    # CSV column headers
    fieldnames = [
        'username',
        'uid',
        'gid',
        'full_name',
        'room_number',
        'work_phone',
        'home_phone',
        'other',
        'home_directory',
        'shell',
        'password_field'
    ]

    try:
        with open(output_path, 'w', newline='', encoding='utf-8') as csvfile:
            writer = csv.DictWriter(csvfile, fieldnames=fieldnames)

            # Write header
            writer.writeheader()

            # Write user data
            for user in users:
                writer.writerow({
                    'username': user['username'],
                    'uid': user['uid'],
                    'gid': user['gid'],
                    'full_name': user['full_name'],
                    'room_number': user['room_number'],
                    'work_phone': user['work_phone'],
                    'home_phone': user['home_phone'],
                    'other': user['other'],
                    'home_directory': user['home_directory'],
                    'shell': user['shell'],
                    'password_field': user['password_field']
                })

        print(f"Successfully exported {len(users)} users to '{output_path}'")

    except Exception as e:
        print(f"Error writing CSV file '{output_path}': {e}", file=sys.stderr)
        sys.exit(1)


def main():
    """Main function to parse /etc/passwd and export to CSV."""
    # Default paths
    default_input = '/etc/passwd'
    default_output = 'passwd_users.csv'

    # Parse command line arguments
    if len(sys.argv) >= 2:
        input_file = sys.argv[1]
    else:
        input_file = default_input

    if len(sys.argv) >= 3:
        output_file = sys.argv[2]
    else:
        output_file = default_output

    # Check if input file exists
    if not Path(input_file).exists():
        print(f"Error: Input file '{input_file}' does not exist.", file=sys.stderr)
        sys.exit(1)

    # Parse /etc/passwd
    print(f"Parsing '{input_file}'...")
    users = parse_passwd_file(input_file)

    if not users:
        print("Warning: No users found in file.", file=sys.stderr)
        sys.exit(0)

    # Write to CSV
    write_csv(users, output_file)

    # Print summary
    print(f"\nSummary:")
    print(f"  Total users: {len(users)}")
    print(f"  System users (UID < 1000): {sum(1 for u in users if int(u['uid']) < 1000)}")
    print(f"  Regular users (UID >= 1000): {sum(1 for u in users if int(u['uid']) >= 1000)}")
    print(f"  Users with login shell: {sum(1 for u in users if 'nologin' not in u['shell'] and 'false' not in u['shell'])}")


if __name__ == '__main__':
    main()

