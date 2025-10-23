#!/bin/bash

# Script to calculate the sum of integers from 1 to N using a loop

echo -n "Enter a number: "
read N

# Validate input
if ! [[ "$N" =~ ^[0-9]+$ ]]; then
    echo "Error: Please enter a positive integer"
    exit 1
fi

if [ "$N" -lt 1 ]; then
    echo "Error: Please enter a positive integer greater than 0"
    exit 1
fi

# Initialize sum
sum=0

# Calculate sum using a loop
for ((i=1; i<=N; i++)); do
    sum=$((sum + i))
done

# Display result
echo "The sum of integers from 1 to $N is $sum"
