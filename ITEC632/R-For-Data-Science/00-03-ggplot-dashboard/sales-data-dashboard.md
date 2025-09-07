# Sales Dataset Description

## Overview

The sales dataset contains comprehensive sales transaction data with detailed information about orders, products, customers, and geographic distribution. This dataset is used for creating a sales dashboard with various key performance indicators and visualizations.

## Dataset Information

- **File**: `sales-dataset/sales.csv`
- **Format**: CSV (Comma-Separated Values)
- **Encoding**: UTF-8
- **Rows**: 2,825 records
- **Columns**: 25 attributes

## Column Descriptions

### Order Information
| Column | Type | Description |
|--------|------|-------------|
| `ORDERNUMBER` | Integer | Unique identifier for each order |
| `ORDERDATE` | Date | Date when the order was placed (MM/DD/YYYY format) |
| `ORDERLINENUMBER` | Integer | Line number within the order |
| `STATUS` | String | Order status (e.g., "Shipped", "Cancelled", "On Hold") |
| `QTR_ID` | Integer | Quarter identifier (1-4) |
| `MONTH_ID` | Integer | Month identifier (1-12) |
| `YEAR_ID` | Integer | Year identifier (2023-2024) |

### Product Information
| Column | Type | Description |
|--------|------|-------------|
| `PRODUCTCODE` | String | Unique product identifier |
| `PRODUCTLINE` | String | Category of product (e.g., "Motorcycles", "Classic Cars", "Trucks and Buses") |
| `MSRP` | Numeric | Manufacturer's Suggested Retail Price |
| `QUANTITYORDERED` | Integer | Number of units ordered |
| `PRICEEACH` | Numeric | Price per unit |
| `SALES` | Numeric | Total sales amount (QUANTITYORDERED × PRICEEACH) |

### Customer Information
| Column | Type | Description |
|--------|------|-------------|
| `CUSTOMERNAME` | String | Name of the customer/company |
| `CONTACTFIRSTNAME` | String | First name of the contact person |
| `CONTACTLASTNAME` | String | Last name of the contact person |
| `PHONE` | String | Customer phone number |
| `ADDRESSLINE1` | String | Primary address line |
| `ADDRESSLINE2` | String | Secondary address line (optional) |
| `CITY` | String | City name |
| `STATE` | String | State/Province name |
| `POSTALCODE` | String | Postal/ZIP code |
| `COUNTRY` | String | Country name |
| `TERRITORY` | String | Sales territory (NA, EMEA, APAC) |
| `DEALSIZE` | String | Size of the deal (Small, Medium, Large) |

## Data Characteristics

### Time Period
- **Start Date**: February 24, 2023
- **End Date**: November 2, 2024
- **Duration**: Approximately 21 months
- **Years Covered**: 2023, 2024

### Product Lines
The dataset includes various product categories:
- Motorcycles
- Classic Cars
- Trucks and Buses
- Vintage Cars
- Planes
- Ships
- Trains

### Geographic Distribution
- **Countries**: Multiple countries including USA, France, Norway, Australia, Finland
- **Territories**: 
  - NA (North America)
  - EMEA (Europe, Middle East, Africa)
  - APAC (Asia Pacific)

### Deal Sizes
- **Small**: Lower value transactions
- **Medium**: Moderate value transactions
- **Large**: High value transactions

## Key Metrics for Dashboard

### Sales Metrics
- **Total Sales**: Sum of all SALES values
- **Average Order Value**: Total sales divided by number of orders
- **Sales by Year**: Aggregated sales by YEAR_ID
- **Sales by Month**: Aggregated sales by MONTH_ID

### Order Metrics
- **Total Orders**: Count of unique ORDERNUMBER values
- **Orders by Year**: Count of orders by YEAR_ID
- **Orders by Month**: Count of orders by MONTH_ID

### Quantity Metrics
- **Total Quantity**: Sum of all QUANTITYORDERED values
- **Quantity by Year**: Aggregated quantity by YEAR_ID
- **Quantity by Month**: Aggregated quantity by MONTH_ID

## Data Quality Notes

### Completeness
- Most fields are well-populated
- Some optional fields (ADDRESSLINE2) may be empty
- Date fields are consistently formatted

### Consistency
- All monetary values are in the same currency
- Date formats are consistent (MM/DD/YYYY)
- Territory codes follow standard abbreviations

### Potential Issues
- Some phone numbers may have different formats
- Address fields may have varying formats across countries
- Some postal codes may be missing for certain countries

## Usage in Dashboard

This dataset is ideal for creating a comprehensive sales dashboard because it provides:

1. **Temporal Analysis**: Date fields enable time-series analysis
2. **Geographic Analysis**: Location fields support regional analysis
3. **Product Analysis**: Product information enables category analysis
4. **Customer Analysis**: Customer data supports segmentation analysis
5. **Financial Analysis**: Sales and pricing data enable revenue analysis

## Sample Data

```csv
ORDERNUMBER,QUANTITYORDERED,PRICEEACH,ORDERLINENUMBER,SALES,ORDERDATE,STATUS,QTR_ID,MONTH_ID,YEAR_ID,PRODUCTLINE,MSRP,PRODUCTCODE,CUSTOMERNAME,PHONE,ADDRESSLINE1,ADDRESSLINE2,CITY,STATE,POSTALCODE,COUNTRY,TERRITORY,CONTACTLASTNAME,CONTACTFIRSTNAME,DEALSIZE
10107,30,95.7,2,2871,2/24/2023 0:00,Shipped,1,2,2023,Motorcycles,95,S10_1678,Land of Toys Inc.,2125557818,897 Long Airport Avenue,,NYC,NY,10022,USA,NA,Yu,Kwai,Small
```

## Technical Notes

- Date parsing requires handling of MM/DD/YYYY format
- Numeric fields may need type conversion
- String fields should be trimmed for consistency
- Geographic data may need standardization for analysis

This dataset provides a rich foundation for creating meaningful sales analytics and business intelligence visualizations.
