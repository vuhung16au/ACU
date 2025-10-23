#!/bin/bash

# Script to search for a specific word in a file and count its occurrences
# Usage: ./04_search_word.sh <filename>

# Check if filename is provided
if [ $# -eq 0 ]; then
    echo "Usage: $0 <filename>"
    echo "Example: $0 01_hello_world.sh"
    exit 1
fi

# Check if file exists
if [ ! -f "$1" ]; then
    echo "Error: File '$1' does not exist."
    exit 1
fi

# Get the filename from command line argument
filename="$1"

# Prompt user for the word to search
echo -n "Enter a word to search for: "
read search_word

# Check if search word is provided
if [ -z "$search_word" ]; then
    echo "Error: No word provided."
    exit 1
fi

# Count occurrences of the word in the file
# Using grep -o to output only the matching parts, then wc -l to count lines
count=$(grep -o "$search_word" "$filename" | wc -l)

# Display the result
echo "The word \"$search_word\" appears $count times in the file."
