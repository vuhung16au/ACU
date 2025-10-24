# Members Dataset Description

## Overview
This dataset contains information about 100 fictional members with various personal and demographic details. The data is stored in CSV format and is designed for practicing regular expressions and data manipulation techniques.

## Dataset Fields

### 1. No (Number)
- **Type**: Integer
- **Description**: Sequential member number from 1 to 100
- **Example**: 1, 2, 3, ..., 100

### 2. Full name
- **Type**: String
- **Description**: Complete name of the member (First Name + Last Name)
- **Format**: "FirstName LastName"
- **Examples**: "John Smith", "Jane Doe", "Michael Johnson"

### 3. DoB (Date of Birth)
- **Type**: Date
- **Description**: Birth date of the member
- **Format**: YYYY-MM-DD
- **Examples**: "1990-05-15", "1985-08-22", "1992-03-10"
- **Range**: 1985-1995

### 4. Email
- **Type**: String
- **Description**: Email address of the member
- **Format**: Various email domains (.com, .org, .net)
- **Examples**: "john.smith@email.com", "jane.doe@company.com", "mike.j@example.org"

### 5. Address
- **Type**: String
- **Description**: Full address including street, city, state, and ZIP code
- **Format**: "Street Address, City, State ZIP"
- **Examples**: "123 Main St, New York, NY 10001", "456 Oak Ave, Los Angeles, CA 90210"

### 6. Credit score
- **Type**: Integer
- **Description**: Credit score of the member
- **Range**: 680-890
- **Examples**: 750, 820, 680, 790

### 7. Gender
- **Type**: String
- **Description**: Gender of the member
- **Values**: "Male", "Female"

### 8. Height (cm)
- **Type**: Integer
- **Description**: Height in centimeters
- **Range**: 155-190 cm
- **Examples**: 175, 162, 180, 168

### 9. Weight (kg)
- **Type**: Integer
- **Description**: Weight in kilograms
- **Range**: 50-95 kg
- **Examples**: 70, 55, 85, 62

## Data Characteristics

### Geographic Distribution
- **States**: Primarily US states including CA, TX, NY, FL, IL, PA, OH, etc.
- **Cities**: Major US cities and metropolitan areas
- **ZIP Codes**: Valid US ZIP code format (5 digits)

### Email Domains
- **Common domains**: email.com, company.com, example.org, test.com, test.net
- **Format variations**: Different naming patterns (firstname.lastname, firstname.lastname, etc.)

### Name Patterns
- **First names**: Common English names
- **Last names**: Common English surnames
- **Format**: "FirstName LastName" (space-separated)

### Date Patterns
- **Format**: ISO 8601 format (YYYY-MM-DD)
- **Year range**: 1985-1995
- **Month range**: 01-12
- **Day range**: 01-31

## Use Cases for Regular Expression Practice

This dataset is perfect for practicing:

1. **Email validation**: Extract and validate email addresses
2. **Date parsing**: Extract dates in YYYY-MM-DD format
3. **Address parsing**: Extract city, state, ZIP codes
4. **Name parsing**: Split full names into first and last names
5. **Data cleaning**: Remove or replace specific patterns
6. **Data extraction**: Extract specific fields or patterns
7. **Data validation**: Check format compliance
8. **Data transformation**: Convert between different formats

## File Format
- **File type**: CSV (Comma-Separated Values)
- **Encoding**: UTF-8
- **Header row**: Yes (contains field names)
- **Delimiter**: Comma (,)
- **Quote character**: Double quotes (") for fields containing commas
- **Line endings**: Unix-style (LF)

## Sample Data Preview
```
No,Full name,DoB,Email,Address,Credit score,Gender,Height (cm),Weight (kg)
1,John Smith,1990-05-15,john.smith@email.com,"123 Main St, New York, NY 10001",750,Male,175,70
2,Jane Doe,1985-08-22,jane.doe@company.com,"456 Oak Ave, Los Angeles, CA 90210",820,Female,162,55
```

This dataset provides a realistic and comprehensive foundation for learning and practicing regular expressions across different programming languages and tools.
