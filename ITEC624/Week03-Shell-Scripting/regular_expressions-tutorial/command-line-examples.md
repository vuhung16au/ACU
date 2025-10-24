# Command Line Regular Expression Examples

This document demonstrates how to use regular expressions with common command-line tools like `grep`, `sed`, and `awk` using the `members.csv` dataset.

## Table of Contents
1. [grep Examples](#grep-examples)
2. [sed Examples](#sed-examples)
3. [awk Examples](#awk-examples)
4. [Combined Examples](#combined-examples)
5. [Performance Tips](#performance-tips)

## grep Examples

### Basic grep Usage
```bash
# Search for lines containing "John"
grep "John" members.csv

# Case-insensitive search
grep -i "john" members.csv

# Show line numbers
grep -n "John" members.csv

# Count matches
grep -c "John" members.csv
```

### Email Pattern Matching
```bash
# Find all email addresses
grep -E "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}" members.csv

# Find emails from specific domain
grep -E "@email\.com" members.csv

# Find emails with specific pattern
grep -E "[a-zA-Z]+\.[a-zA-Z]+@[a-zA-Z]+\.[a-zA-Z]+" members.csv
```

### Date Pattern Matching
```bash
# Find all dates in YYYY-MM-DD format
grep -E "\d{4}-\d{2}-\d{2}" members.csv

# Find dates from specific year
grep -E "199[0-9]-\d{2}-\d{2}" members.csv

# Find dates from specific month
grep -E "\d{4}-0[1-3]-\d{2}" members.csv
```

### Address Pattern Matching
```bash
# Find addresses in specific state
grep -E ", [A-Z]{2} \d{5}$" members.csv

# Find addresses with specific ZIP code pattern
grep -E "\d{5}$" members.csv

# Find addresses in California
grep -E ", CA \d{5}$" members.csv
```

### Credit Score Patterns
```bash
# Find high credit scores (800+)
grep -E "8[0-9][0-9]" members.csv

# Find credit scores in specific range
grep -E "7[0-9][0-9]" members.csv

# Find exact credit score
grep -E "750" members.csv
```

### Name Patterns
```bash
# Find names starting with specific letter
grep -E "^[0-9]+,J" members.csv

# Find names with specific pattern
grep -E "[A-Z][a-z]+ [A-Z][a-z]+" members.csv

# Find names ending with specific letter
grep -E "[A-Z][a-z]+ [A-Z][a-z]+[n]" members.csv
```

## sed Examples

### Basic sed Usage
```bash
# Replace first occurrence
sed 's/John/Jonathan/' members.csv

# Replace all occurrences
sed 's/John/Jonathan/g' members.csv

# Replace with case-insensitive
sed 's/John/Jonathan/gi' members.csv
```

### Email Domain Replacement
```bash
# Change all email domains to example.com
sed 's/@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}/@example.com/g' members.csv

# Change specific domain
sed 's/@email\.com/@company.com/g' members.csv

# Add prefix to all emails
sed 's/\([a-zA-Z0-9._%+-]\+\)@/\1+tag@/g' members.csv
```

### Date Format Conversion
```bash
# Convert YYYY-MM-DD to MM/DD/YYYY
sed 's/\([0-9]\{4\}\)-\([0-9]\{2\}\)-\([0-9]\{2\}\)/\2\/\3\/\1/g' members.csv

# Convert to European format DD/MM/YYYY
sed 's/\([0-9]\{4\}\)-\([0-9]\{2\}\)-\([0-9]\{2\}\)/\3\/\2\/\1/g' members.csv
```

### Address Formatting
```bash
# Standardize state abbreviations
sed 's/, \([A-Z][A-Z]\) /, \1 /g' members.csv

# Add quotes around addresses
sed 's/"\([^"]*\)"/"\1"/g' members.csv

# Remove extra spaces
sed 's/  \+/ /g' members.csv
```

### Data Extraction
```bash
# Extract only names and emails
sed 's/^[0-9]\+,\([^,]*\),[^,]*,\("[^"]*"\),.*/\1,\2/' members.csv

# Extract credit scores
sed 's/.*,\([0-9]\{3\}\),.*/\1/' members.csv

# Extract ZIP codes
sed 's/.*, \([A-Z][A-Z]\) \([0-9]\{5\}\).*/\2/' members.csv
```

## awk Examples

### Basic awk Usage
```bash
# Print specific columns
awk -F',' '{print $2, $4}' members.csv

# Print lines matching pattern
awk -F',' '/John/ {print $0}' members.csv

# Print lines with specific field value
awk -F',' '$7 == "Male" {print $0}' members.csv
```

### Pattern Matching with awk
```bash
# Find high credit scores
awk -F',' '$6 > 800 {print $2, $6}' members.csv

# Find specific age range (assuming current year 2024)
awk -F',' '{
    split($3, date, "-")
    age = 2024 - date[1]
    if (age >= 30 && age <= 40) print $2, age
}' members.csv

# Find specific height range
awk -F',' '$8 >= 170 && $8 <= 180 {print $2, $8}' members.csv
```

### Data Processing with awk
```bash
# Calculate average credit score
awk -F',' 'NR>1 {sum+=$6; count++} END {print "Average credit score:", sum/count}' members.csv

# Count by gender
awk -F',' 'NR>1 {gender[$7]++} END {for (g in gender) print g, gender[g]}' members.csv

# Find tallest person
awk -F',' 'NR>1 {if ($8 > max) {max=$8; name=$2}} END {print "Tallest:", name, max "cm"}' members.csv
```

### Complex Pattern Matching
```bash
# Find people with specific name pattern
awk -F',' '$2 ~ /^[A-Z][a-z]+ [A-Z][a-z]+$/ {print $2}' members.csv

# Find emails with specific pattern
awk -F',' '$4 ~ /@[a-zA-Z]+\.com$/ {print $2, $4}' members.csv

# Find addresses in specific states
awk -F',' '$5 ~ /, (CA|NY|TX) / {print $2, $5}' members.csv
```

## Combined Examples

### Complex Data Processing
```bash
# Find high-credit-score males in California
grep -E ", CA [0-9]{5}$" members.csv | \
awk -F',' '$6 > 800 && $7 == "Male" {print $2, $6, $5}'

# Extract and format specific data
sed 's/^[0-9]*,\([^,]*\),[^,]*,\("[^"]*"\),[^,]*,\("[^"]*"\),[^,]*,[^,]*,[^,]*,[^,]*/\1,\2,\3/' members.csv | \
grep -E "@email\.com"

# Count people by state
awk -F',' 'NR>1 {
    if ($5 ~ /, CA /) state["California"]++
    else if ($5 ~ /, NY /) state["New York"]++
    else if ($5 ~ /, TX /) state["Texas"]++
    else state["Other"]++
} END {for (s in state) print s, state[s]}' members.csv
```

### Data Validation
```bash
# Validate email format
awk -F',' 'NR>1 {
    if ($4 !~ /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/) {
        print "Invalid email:", $2, $4
    }
}' members.csv

# Validate date format
awk -F',' 'NR>1 {
    if ($3 !~ /^[0-9]{4}-[0-9]{2}-[0-9]{2}$/) {
        print "Invalid date:", $2, $3
    }
}' members.csv

# Validate credit score range
awk -F',' 'NR>1 {
    if ($6 < 300 || $6 > 850) {
        print "Invalid credit score:", $2, $6
    }
}' members.csv
```

### Data Transformation
```bash
# Create a summary report
awk -F',' 'BEGIN {print "Name,Email,State,Credit Score"}
NR>1 {
    if ($5 ~ /, CA /) state = "CA"
    else if ($5 ~ /, NY /) state = "NY"
    else if ($5 ~ /, TX /) state = "TX"
    else state = "Other"
    print $2, $4, state, $6
}' members.csv

# Extract specific patterns and format
awk -F',' 'NR>1 {
    if ($4 ~ /@email\.com$/) {
        print "Name: " $2
        print "Email: " $4
        print "Credit Score: " $6
        print "---"
    }
}' members.csv
```

## Performance Tips

### Optimizing grep
```bash
# Use -F for fixed strings (faster)
grep -F "John" members.csv

# Use -E for extended regex (more features)
grep -E "[0-9]{3}" members.csv

# Use -i only when needed
grep -i "john" members.csv
```

### Optimizing sed
```bash
# Use specific line numbers when possible
sed '1,10s/John/Jonathan/g' members.csv

# Combine multiple operations
sed -e 's/John/Jonathan/g' -e 's/Jane/Janet/g' members.csv
```

### Optimizing awk
```bash
# Use specific field references
awk -F',' '$2 ~ /John/ {print $0}' members.csv

# Use built-in functions
awk -F',' 'length($2) > 10 {print $2}' members.csv
```

## Common Pitfalls

1. **Escaping Special Characters**: Remember to escape special characters in shell
2. **Quote Handling**: Be careful with quotes in CSV data
3. **Field Separators**: Use appropriate field separators for your data
4. **Line Endings**: Consider different line ending formats
5. **Performance**: Large files may require different approaches

## Best Practices

1. **Test with Small Data**: Test your patterns on small datasets first
2. **Use Specific Patterns**: More specific patterns are usually faster
3. **Combine Tools**: Use multiple tools together for complex operations
4. **Document Your Patterns**: Comment complex regex patterns
5. **Validate Results**: Always verify your results

## Advanced Examples

### Multi-step Data Processing
```bash
# Step 1: Extract high credit score people
awk -F',' '$6 > 800 {print $0}' members.csv > high_credit.csv

# Step 2: Format the output
sed 's/^[0-9]*,\([^,]*\),[^,]*,\("[^"]*"\),[^,]*,\("[^"]*"\),[^,]*,[^,]*,[^,]*,[^,]*/\1,\2,\3/' high_credit.csv

# Step 3: Sort by credit score
sort -t',' -k6 -nr high_credit.csv
```

### Complex Pattern Matching
```bash
# Find people with specific characteristics
awk -F',' 'NR>1 {
    if ($7 == "Male" && $6 > 750 && $8 > 175) {
        print $2, $4, $6, $8
    }
}' members.csv | \
sort -k3 -nr
```

Remember: These examples demonstrate the power of combining command-line tools with regular expressions for data processing and analysis!
