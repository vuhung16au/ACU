#!/bin/bash

# Data Validation Script
# This script demonstrates data validation using regular expressions

echo "=== Data Validation Examples ==="
echo

# Set the data file path
DATA_FILE="../members.csv"

# Check if data file exists
if [ ! -f "$DATA_FILE" ]; then
    echo "Error: Data file $DATA_FILE not found!"
    exit 1
fi

# Function to validate email format
validate_email() {
    local email="$1"
    if [[ $email =~ ^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$ ]]; then
        return 0  # Valid
    else
        return 1  # Invalid
    fi
}

# Function to validate date format
validate_date() {
    local date="$1"
    if [[ $date =~ ^[0-9]{4}-[0-9]{2}-[0-9]{2}$ ]]; then
        return 0  # Valid
    else
        return 1  # Invalid
    fi
}

# Function to validate credit score
validate_credit_score() {
    local score="$1"
    if [[ $score =~ ^[0-9]+$ ]] && [ "$score" -ge 300 ] && [ "$score" -le 850 ]; then
        return 0  # Valid
    else
        return 1  # Invalid
    fi
}

# Function to validate height
validate_height() {
    local height="$1"
    if [[ $height =~ ^[0-9]+$ ]] && [ "$height" -ge 100 ] && [ "$height" -le 250 ]; then
        return 0  # Valid
    else
        return 1  # Invalid
    fi
}

# Function to validate weight
validate_weight() {
    local weight="$1"
    if [[ $weight =~ ^[0-9]+$ ]] && [ "$weight" -ge 30 ] && [ "$weight" -le 200 ]; then
        return 0  # Valid
    else
        return 1  # Invalid
    fi
}

echo "1. Email Validation"
echo "-------------------"
echo "Validating all email addresses..."
valid_emails=0
invalid_emails=0

while IFS=',' read -r no name dob email address credit gender height weight; do
    if [ "$no" != "No" ]; then  # Skip header
        if validate_email "$email"; then
            ((valid_emails++))
        else
            echo "✗ Invalid email: $email (Line: $no)"
            ((invalid_emails++))
        fi
    fi
done < "$DATA_FILE"

echo "Valid emails: $valid_emails"
echo "Invalid emails: $invalid_emails"
echo

echo "2. Date Validation"
echo "------------------"
echo "Validating all dates..."
valid_dates=0
invalid_dates=0

while IFS=',' read -r no name dob email address credit gender height weight; do
    if [ "$no" != "No" ]; then  # Skip header
        if validate_date "$dob"; then
            ((valid_dates++))
        else
            echo "✗ Invalid date: $dob (Line: $no)"
            ((invalid_dates++))
        fi
    fi
done < "$DATA_FILE"

echo "Valid dates: $valid_dates"
echo "Invalid dates: $invalid_dates"
echo

echo "3. Credit Score Validation"
echo "--------------------------"
echo "Validating all credit scores..."
valid_scores=0
invalid_scores=0

while IFS=',' read -r no name dob email address credit gender height weight; do
    if [ "$no" != "No" ]; then  # Skip header
        if validate_credit_score "$credit"; then
            ((valid_scores++))
        else
            echo "✗ Invalid credit score: $credit (Line: $no)"
            ((invalid_scores++))
        fi
    fi
done < "$DATA_FILE"

echo "Valid credit scores: $valid_scores"
echo "Invalid credit scores: $invalid_scores"
echo

echo "4. Height Validation"
echo "-------------------"
echo "Validating all heights..."
valid_heights=0
invalid_heights=0

while IFS=',' read -r no name dob email address credit gender height weight; do
    if [ "$no" != "No" ]; then  # Skip header
        if validate_height "$height"; then
            ((valid_heights++))
        else
            echo "✗ Invalid height: $height (Line: $no)"
            ((invalid_heights++))
        fi
    fi
done < "$DATA_FILE"

echo "Valid heights: $valid_heights"
echo "Invalid heights: $invalid_heights"
echo

echo "5. Weight Validation"
echo "------------------"
echo "Validating all weights..."
valid_weights=0
invalid_weights=0

while IFS=',' read -r no name dob email address credit gender height weight; do
    if [ "$no" != "No" ]; then  # Skip header
        if validate_weight "$weight"; then
            ((valid_weights++))
        else
            echo "✗ Invalid weight: $weight (Line: $no)"
            ((invalid_weights++))
        fi
    fi
done < "$DATA_FILE"

echo "Valid weights: $valid_weights"
echo "Invalid weights: $invalid_weights"
echo

echo "6. Gender Validation"
echo "-------------------"
echo "Validating all genders..."
valid_genders=0
invalid_genders=0

while IFS=',' read -r no name dob email address credit gender height weight; do
    if [ "$no" != "No" ]; then  # Skip header
        if [[ "$gender" == "Male" || "$gender" == "Female" ]]; then
            ((valid_genders++))
        else
            echo "✗ Invalid gender: $gender (Line: $no)"
            ((invalid_genders++))
        fi
    fi
done < "$DATA_FILE"

echo "Valid genders: $valid_genders"
echo "Invalid genders: $invalid_genders"
echo

echo "7. Name Validation"
echo "-----------------"
echo "Validating all names..."
valid_names=0
invalid_names=0

while IFS=',' read -r no name dob email address credit gender height weight; do
    if [ "$no" != "No" ]; then  # Skip header
        if [[ $name =~ ^[A-Z][a-z]+ [A-Z][a-z]+$ ]]; then
            ((valid_names++))
        else
            echo "✗ Invalid name format: $name (Line: $no)"
            ((invalid_names++))
        fi
    fi
done < "$DATA_FILE"

echo "Valid names: $valid_names"
echo "Invalid names: $invalid_names"
echo

echo "8. Address Validation"
echo "--------------------"
echo "Validating all addresses..."
valid_addresses=0
invalid_addresses=0

while IFS=',' read -r no name dob email address credit gender height weight; do
    if [ "$no" != "No" ]; then  # Skip header
        if [[ $address =~ ^".*", [A-Za-z ]+, [A-Z]{2} [0-9]{5}$ ]]; then
            ((valid_addresses++))
        else
            echo "✗ Invalid address format: $address (Line: $no)"
            ((invalid_addresses++))
        fi
    fi
done < "$DATA_FILE"

echo "Valid addresses: $valid_addresses"
echo "Invalid addresses: $invalid_addresses"
echo

echo "9. Comprehensive Data Validation"
echo "--------------------------------"
echo "Running comprehensive validation on all fields..."

total_records=0
valid_records=0
invalid_records=0

while IFS=',' read -r no name dob email address credit gender height weight; do
    if [ "$no" != "No" ]; then  # Skip header
        ((total_records++))
        record_valid=true
        
        # Validate each field
        if ! validate_email "$email"; then
            echo "✗ Invalid email in record $no: $email"
            record_valid=false
        fi
        
        if ! validate_date "$dob"; then
            echo "✗ Invalid date in record $no: $dob"
            record_valid=false
        fi
        
        if ! validate_credit_score "$credit"; then
            echo "✗ Invalid credit score in record $no: $credit"
            record_valid=false
        fi
        
        if ! validate_height "$height"; then
            echo "✗ Invalid height in record $no: $height"
            record_valid=false
        fi
        
        if ! validate_weight "$weight"; then
            echo "✗ Invalid weight in record $no: $weight"
            record_valid=false
        fi
        
        if [[ "$gender" != "Male" && "$gender" != "Female" ]]; then
            echo "✗ Invalid gender in record $no: $gender"
            record_valid=false
        fi
        
        if [[ ! $name =~ ^[A-Z][a-z]+ [A-Z][a-z]+$ ]]; then
            echo "✗ Invalid name format in record $no: $name"
            record_valid=false
        fi
        
        if [[ ! $address =~ ^".*", [A-Za-z ]+, [A-Z]{2} [0-9]{5}$ ]]; then
            echo "✗ Invalid address format in record $no: $address"
            record_valid=false
        fi
        
        if [ "$record_valid" = true ]; then
            ((valid_records++))
        else
            ((invalid_records++))
        fi
    fi
done < "$DATA_FILE"

echo
echo "Validation Summary:"
echo "Total records: $total_records"
echo "Valid records: $valid_records"
echo "Invalid records: $invalid_records"
echo "Validation success rate: $(( (valid_records * 100) / total_records ))%"
echo

echo "10. Data Quality Report"
echo "----------------------"
echo "Generating data quality report..."

# Count unique values
echo "Unique email domains: $(awk -F',' 'NR>1 {print $4}' "$DATA_FILE" | sed 's/.*@\(.*\)/\1/' | sort | uniq | wc -l)"
echo "Unique states: $(awk -F',' 'NR>1 {print $5}' "$DATA_FILE" | sed 's/.*, \([A-Z]{2}\) [0-9].*/\1/' | sort | uniq | wc -l)"
echo "Unique genders: $(awk -F',' 'NR>1 {print $7}' "$DATA_FILE" | sort | uniq | wc -l)"

# Calculate statistics
echo "Credit score range: $(awk -F',' 'NR>1 {print $6}' "$DATA_FILE" | sort -n | head -1) - $(awk -F',' 'NR>1 {print $6}' "$DATA_FILE" | sort -n | tail -1)"
echo "Height range: $(awk -F',' 'NR>1 {print $8}' "$DATA_FILE" | sort -n | head -1) - $(awk -F',' 'NR>1 {print $8}' "$DATA_FILE" | sort -n | tail -1)"
echo "Weight range: $(awk -F',' 'NR>1 {print $9}' "$DATA_FILE" | sort -n | head -1) - $(awk -F',' 'NR>1 {print $9}' "$DATA_FILE" | sort -n | tail -1)"

echo
echo "=== End of Data Validation Examples ==="
