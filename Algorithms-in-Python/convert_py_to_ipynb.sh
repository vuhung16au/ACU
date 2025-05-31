#!/bin/bash

# Script to convert all Python files to Jupyter notebooks
# Navigate to the main project directory
cd /Users/vuhung/Desktop/ACU/Algorithms-in-Python

# Find all Python files and convert them to Jupyter notebooks
for py_file in $(find . -name "*.py" | sort); do
    # Get the directory path
    dir_path=$(dirname "$py_file")
    # Get the file name without extension
    base_name=$(basename "$py_file" .py)
    # Calculate the relative path from dir_path to the project root
    # Count the number of directories to go up
    depth=$(echo "$dir_path" | tr -cd '/' | wc -c)
    rel_path=""
    for ((i=0; i<depth; i++)); do
        rel_path="../$rel_path"
    done
    
    # Convert the Python file
    echo "Converting $py_file to Jupyter notebook..."
    (cd "$dir_path" && $rel_path.venv/bin/p2j "$base_name.py")
    # Verify the notebook was created
    if [ -f "$dir_path/$base_name.ipynb" ]; then
        echo "✓ Notebook $dir_path/$base_name.ipynb created successfully."
    else
        echo "✗ Failed to create $dir_path/$base_name.ipynb"
    fi
done

# Count the total conversions
total_py=$(find . -name "*.py" | wc -l | tr -d ' ')
total_ipynb=$(find . -name "*.ipynb" | wc -l | tr -d ' ')

echo "Conversion completed! Converted $total_ipynb out of $total_py Python files."
