# Australia Sales Dataset Documentation

## Dataset Overview

The Australia Sales Dataset contains retail transaction data from three major Australian cities: Sydney, Melbourne, and Perth. This dataset provides comprehensive information about sales transactions, customer demographics, product categories, and payment methods.

## Dataset Information

- **Source**: Retail sales data from Australian stores
- **Time Period**: January 2019 - March 2019
- **Total Records**: 1,000 transactions
- **Cities**: Sydney, Melbourne, Perth
- **Branches**: A, B, C (corresponding to different store locations)

## Column Descriptions

### Transaction Identifiers
- **Invoice ID**: Unique identifier for each transaction (e.g., "750-67-8428")
- **Branch**: Store branch identifier (A, B, C)
- **City**: Location of the store (Sydney, Melbourne, Perth)

### Customer Information
- **Customer type**: Membership status (Member, Normal)
- **Gender**: Customer gender (Male, Female)

### Product Information
- **Product line**: Category of products sold
  - Health and beauty
  - Electronic accessories
  - Home and lifestyle
  - Sports and travel
  - Food and beverages
  - Fashion accessories

### Financial Data
- **Unit price**: Price per unit of the product (AUD)
- **Quantity**: Number of units purchased
- **Tax 5%**: Tax amount (5% of subtotal)
- **Total**: Total amount including tax
- **cogs**: Cost of goods sold
- **gross margin percentage**: Profit margin percentage
- **gross income**: Gross profit amount

### Transaction Details
- **Date**: Transaction date (MM/DD/YYYY format)
- **Time**: Transaction time (HH:MM format)
- **Payment**: Payment method
  - Ewallet
  - Cash
  - Credit card
- **Rating**: Customer satisfaction rating (1-10 scale)

## Data Quality Considerations

### Potential Issues
1. **Date Format**: Dates are in MM/DD/YYYY format (US style)
2. **Missing Values**: Some fields may contain missing data
3. **Data Types**: Numeric fields stored as text may need conversion
4. **Outliers**: Extreme values in price, quantity, or rating fields
5. **Inconsistencies**: Potential spelling variations in categorical fields

### Data Cleaning Requirements
1. **Standardize column names** to snake_case format
2. **Convert data types** appropriately (numeric, date, factor)
3. **Handle missing values** with appropriate strategies
4. **Validate data ranges** and identify outliers
5. **Standardize categorical values** for consistency
6. **Parse dates and times** correctly
7. **Check for duplicates** and data integrity

## Business Context

This dataset represents typical retail transaction data that would be found in a modern point-of-sale system. The data includes:

- **Multi-location operations** across three major Australian cities
- **Diverse product categories** catering to different customer needs
- **Multiple payment methods** reflecting modern retail practices
- **Customer segmentation** through membership programs
- **Performance tracking** through ratings and financial metrics

## Use Cases

This dataset is suitable for:
- **Sales analysis** and performance tracking
- **Customer behavior** analysis
- **Product performance** evaluation
- **Geographic sales** patterns
- **Payment method** preferences
- **Data cleaning** and preprocessing demonstrations
- **Business intelligence** and reporting
- **Machine learning** model training

## Data Dictionary

| Column | Type | Description | Example Values |
|--------|------|-------------|----------------|
| Invoice ID | Character | Unique transaction identifier | "750-67-8428" |
| Branch | Character | Store branch code | "A", "B", "C" |
| City | Character | Store location | "Sydney", "Melbourne", "Perth" |
| Customer type | Character | Membership status | "Member", "Normal" |
| Gender | Character | Customer gender | "Male", "Female" |
| Product line | Character | Product category | "Health and beauty" |
| Unit price | Numeric | Price per unit (AUD) | 74.69 |
| Quantity | Integer | Number of units | 7 |
| Tax 5% | Numeric | Tax amount | 26.1415 |
| Total | Numeric | Total amount | 548.9715 |
| Date | Character | Transaction date | "1/5/2019" |
| Time | Character | Transaction time | "13:08" |
| Payment | Character | Payment method | "Ewallet" |
| cogs | Numeric | Cost of goods sold | 522.83 |
| gross margin percentage | Numeric | Profit margin % | 4.761904762 |
| gross income | Numeric | Gross profit | 26.1415 |
| Rating | Numeric | Customer rating (1-10) | 9.1 |

## File Information

- **File Name**: `australia_sales.csv`
- **Format**: CSV (Comma-Separated Values)
- **Encoding**: UTF-8
- **Delimiter**: Comma (,)
- **Header**: Yes (first row contains column names)
- **File Size**: Approximately 100KB
