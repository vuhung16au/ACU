#!/bin/bash

# Script to generate a secure random password
# Requirements: 12+ characters, at least one uppercase, lowercase, number, and special character

# Function to generate a secure random password
generate_password() {
    # Character sets
    uppercase="ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    lowercase="abcdefghijklmnopqrstuvwxyz"
    numbers="0123456789"
    special_chars="!@#$%^&*()_+-=[]{}|;:,.<>?"
    
    # Ensure we have at least one character from each required set
    password=""
    password+="${uppercase:$((RANDOM % ${#uppercase})):1}"  # One uppercase
    password+="${lowercase:$((RANDOM % ${#lowercase})):1}"  # One lowercase
    password+="${numbers:$((RANDOM % ${#numbers})):1}"      # One number
    password+="${special_chars:$((RANDOM % ${#special_chars})):1}"  # One special char
    
    # Combine all character sets for remaining characters
    all_chars="${uppercase}${lowercase}${numbers}${special_chars}"
    
    # Add remaining characters to reach minimum 12 characters
    remaining_length=$((12 - ${#password}))
    for ((i=0; i<remaining_length; i++)); do
        password+="${all_chars:$((RANDOM % ${#all_chars})):1}"
    done
    
    # Shuffle the password to randomize positions (macOS compatible)
    echo "$password" | fold -w1 | sort -R | tr -d '\n'
}

# Generate and display the password
secure_password=$(generate_password)
echo "Your secure password is: $secure_password"
