#!/usr/bin/env python3
"""
Generate random lists of different sizes and save to files.
"""
import random
import sys
import os
import shutil
import argparse
import glob

def generate_random_list(size=100000, min_val=1, max_val=5000000):
    """Generate a list of random integers."""
    return [random.randint(min_val, max_val) for _ in range(size)]

def save_list_to_file(data, filename):
    """Save list to file, one number per line."""
    # Make sure datasets directory exists
    # Determine script location and set paths relative to project root
    script_dir = os.path.dirname(os.path.abspath(__file__))
    project_root = os.path.dirname(script_dir) if os.path.basename(script_dir) == 'scripts' else os.getcwd()
    datasets_dir = os.path.join(project_root, 'datasets')
    
    os.makedirs(datasets_dir, exist_ok=True)
    full_path = os.path.join(datasets_dir, filename)
    with open(full_path, 'w') as f:
        for num in data:
            f.write(f"{num}\n")

def generate_dataset(size, name):
    """Generate and save a dataset of given size."""
    print(f"Generating {size:,} random integers for {name} dataset...")
    random_list = generate_random_list(size)
    filename = f"random_list_{size}.txt"
    save_list_to_file(random_list, filename)
    print(f"Random list saved to datasets/{filename}")
    print(f"List size: {len(random_list):,}")
    print(f"Sample values: {random_list[:5]}...")
    print()

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Generate random integer datasets.")
    parser.add_argument(
        "--size",
        type=str,
        help="Comma-separated list of sizes to generate (e.g., 1000,5000,10000). Overrides config file if specified.",
    )
    parser.add_argument(
        "--clean",
        action="store_true",
        help="Delete all datasets/random_list_*.txt files and exit."
    )
    args, unknown = parser.parse_known_args()

    script_dir = os.path.dirname(os.path.abspath(__file__))
    project_root = os.path.dirname(script_dir) if os.path.basename(script_dir) == 'scripts' else os.getcwd()
    datasets_dir = os.path.join(project_root, 'datasets')

    if args.clean:
        pattern = os.path.join(datasets_dir, "random_list_*.txt")
        files = glob.glob(pattern)
        if not files:
            print("No files to delete.")
        else:
            for file in files:
                try:
                    os.remove(file)
                    print(f"Deleted {file}")
                except Exception as e:
                    print(f"Failed to delete {file}: {e}")
        print("Cleanup complete.")
        sys.exit(0)

    if args.size:
        try:
            data_sizes = [int(s.strip()) for s in args.size.split(",") if s.strip()]
        except ValueError:
            print("Error: --size must be a comma-separated list of integers.")
            sys.exit(1)
        print("Generating datasets for performance comparison...")
        print("Configuration loaded from --size argument")
        print("=" * 60)
    else:
        # Read sizes from configuration file
        def read_data_points_config():
            script_dir = os.path.dirname(os.path.abspath(__file__))
            project_root = os.path.dirname(script_dir) if os.path.basename(script_dir) == 'scripts' else os.getcwd()
            config_file = os.path.join(project_root, 'config', 'number-of-data-points.txt')
            """Read data point sizes from configuration file."""
            try:
                with open(config_file, 'r') as f:
                    return [int(line.strip()) for line in f if line.strip()]
            except FileNotFoundError:
                print(f"Error: {config_file} not found. Please create this file with the desired dataset sizes (one per line). Aborting.")
                sys.exit(1)
        data_sizes = read_data_points_config()
        print("Generating datasets for performance comparison...")
        print("Configuration loaded from config/number-of-data-points.txt")
        print("=" * 60)
    
    for size in data_sizes:
        name = f"{size}_elements"
        generate_dataset(size, name)
    
    print("All datasets generated successfully!")
    print("Files created:")
    for size in data_sizes:
        print(f"  - datasets/random_list_{size}.txt")
