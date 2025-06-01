#!/usr/bin/env python3
"""
Generate random lists of different sizes and save to files.
"""
import random
import sys
import os
import shutil

def generate_random_list(size=100000, min_val=1, max_val=1000000):
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
    # Read data point sizes from configuration file
    def read_data_points_config():
        script_dir = os.path.dirname(os.path.abspath(__file__))
        project_root = os.path.dirname(script_dir) if os.path.basename(script_dir) == 'scripts' else os.getcwd()
        config_file = os.path.join(project_root, 'config', 'number-of-data-points.txt')
        """Read data point sizes from configuration file."""
        try:
            with open(config_file, 'r') as f:
                return [int(line.strip()) for line in f if line.strip()]
        except FileNotFoundError:
            print(f"Error: {config_file} not found. Using default sizes.")
            return [10, 100000, 1000000]
    
    # Allow command line argument to specify which dataset(s) to generate
    if len(sys.argv) > 1:
        size = int(sys.argv[1])
        name = f"{size}_elements"
        generate_dataset(size, name)
    else:
        # Read sizes from configuration file
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
            
        # Also create the default random_list.txt for backward compatibility
        script_dir = os.path.dirname(os.path.abspath(__file__))
        project_root = os.path.dirname(script_dir) if os.path.basename(script_dir) == 'scripts' else os.getcwd()
        datasets_dir = os.path.join(project_root, 'datasets')
        
        if 100000 in data_sizes:
            print("\nCreating backward compatibility file...")
            src_file = os.path.join(datasets_dir, "random_list_100000.txt")
            dst_file = os.path.join(datasets_dir, "random_list.txt")
            shutil.copy(src_file, dst_file)
            print("Created datasets/random_list.txt (copy of random_list_100000.txt)")
        else:
            # Use the first size if 100000 is not in the list
            first_size = data_sizes[0]
            print(f"\nCreating backward compatibility file using size {first_size}...")
            src_file = os.path.join(datasets_dir, f"random_list_{first_size}.txt")
            dst_file = os.path.join(datasets_dir, "random_list.txt")
            shutil.copy(src_file, dst_file)
            print(f"Created datasets/random_list.txt (copy of random_list_{first_size}.txt)")
