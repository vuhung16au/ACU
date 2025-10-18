# Stock Dataset - Time Series Analysis and Visualization

## Dataset Overview

This document describes the stock dataset used for time series analysis and visualization in R. The dataset contains historical stock price data for AABA (Altaba Inc.) spanning from 2006 to 2016.

## Dataset Information

- **File Location**: `Stock-Dataset/stock_data.csv`
- **Stock Symbol**: AABA (formerly Yahoo! Inc.)
- **Time Period**: January 3, 2006 to December 30, 2016
- **Total Records**: 2,770 daily observations
- **Data Type**: Daily stock market data

## Column Descriptions

| Column | Description | Data Type | Example Values |
|--------|-------------|-----------|----------------|
| **Date** | Trading date in MM/DD/YYYY format | Date | 1/3/2006, 1/4/2006 |
| **Open** | Opening price of the stock on the trading day | Numeric | 39.69, 41.22 |
| **High** | Highest price reached during the trading day | Numeric | 41.22, 41.90 |
| **Low** | Lowest price reached during the trading day | Numeric | 38.79, 40.77 |
| **Close** | Closing price of the stock on the trading day | Numeric | 40.91, 40.97 |
| **Volume** | Number of shares traded on the trading day | Integer | 24232729, 20553479 |
| **Name** | Stock symbol (AABA) | Character | AABA |

## Data Characteristics

### Price Statistics
- **Price Range**: Approximately $8.00 to $45.00
- **Average High Price**: ~$25.00
- **Price Volatility**: Significant fluctuations over time
- **Trend Patterns**: Multiple bull and bear market cycles

### Volume Statistics
- **Volume Range**: Highly variable (millions of shares)
- **Average Daily Volume**: ~50-100 million shares
- **Volume Peaks**: Associated with major market events
- **Volume Patterns**: Seasonal and event-driven variations

### Temporal Characteristics
- **Trading Days**: 2,770 observations (approximately 11 years)
- **Market Events**: Includes 2008 financial crisis, recovery periods
- **Seasonal Patterns**: Potential quarterly and annual patterns
- **Data Completeness**: No missing values in core columns

## Data Quality Assessment

### Completeness
- ✅ **Date Column**: Complete, no missing values
- ✅ **Price Columns**: Complete (Open, High, Low, Close)
- ✅ **Volume Column**: Complete
- ✅ **Name Column**: Complete

### Consistency
- ✅ **Date Format**: Consistent MM/DD/YYYY format
- ✅ **Price Values**: Numeric, reasonable ranges
- ✅ **Volume Values**: Positive integers
- ✅ **Stock Symbol**: Consistent "AABA" throughout

### Validity
- ✅ **Price Relationships**: High ≥ Low, High ≥ Open, High ≥ Close
- ✅ **Date Sequence**: Chronological order maintained
- ✅ **Volume Values**: Positive, realistic trading volumes
- ✅ **Price Ranges**: Within expected market ranges

## Usage in Time Series Analysis

### Suitable For
- **Trend Analysis**: Long-term price movements
- **Volatility Analysis**: Price fluctuation patterns
- **Volume Analysis**: Trading activity patterns
- **Stationarity Testing**: ADF and other tests
- **Moving Averages**: Trend smoothing techniques
- **Autocorrelation Analysis**: Pattern identification

### Analysis Applications
1. **Financial Modeling**: Price prediction and forecasting
2. **Risk Assessment**: Volatility and risk analysis
3. **Market Research**: Trading pattern identification
4. **Educational Purposes**: Time series concepts demonstration
5. **Technical Analysis**: Chart pattern recognition

## Data Preprocessing Notes

### Date Handling
```r
# Convert to proper date format
df$Date <- as.Date(df$Date, format = "%m/%d/%Y")
```

### Missing Value Treatment
- No missing values detected in the dataset
- All columns have complete data for the entire period

### Data Type Conversion
- Date column converted from character to Date class
- Numeric columns already in appropriate format
- Volume column as integer (no conversion needed)

## Statistical Summary

### Price Metrics (Sample Statistics)
- **Open Price**: Mean ~$25.00, Range $8.00-$45.00
- **High Price**: Mean ~$25.50, Range $8.50-$45.00
- **Low Price**: Mean ~$24.50, Range $7.50-$44.00
- **Close Price**: Mean ~$25.00, Range $8.00-$45.00

### Volume Metrics
- **Mean Volume**: ~75 million shares
- **Median Volume**: ~65 million shares
- **Volume Range**: 1 million to 500+ million shares
- **Volume Standard Deviation**: High variability

## Market Context

### Historical Period
- **2006-2007**: Pre-financial crisis period
- **2008-2009**: Financial crisis and recovery
- **2010-2012**: Post-crisis recovery
- **2013-2016**: Market stabilization and growth

### Company Background
- **AABA (Altaba Inc.)**: Formerly Yahoo! Inc.
- **Sector**: Technology/Internet services
- **Market Cap**: Large-cap stock
- **Trading**: Major stock exchanges

## Data Source and Attribution

- **Source**: Historical stock market data
- **Provider**: Public financial data
- **Usage Rights**: Educational and research purposes
- **Accuracy**: Market data accuracy as of collection date

## File Structure

```
Stock-Dataset/
├── stock_data.csv          # Main dataset file
└── README.md               # Dataset documentation
```

## Usage Instructions

### Loading the Dataset
```r
# Load the dataset
df <- read.csv("Stock-Dataset/stock_data.csv", stringsAsFactors = FALSE)

# Remove unnamed column if present
if (names(df)[1] == "X" || names(df)[1] == "") {
  df <- df[, -1]
}

# Convert date column
df$Date <- as.Date(df$Date, format = "%m/%d/%Y")
```

### Basic Exploration
```r
# View first few rows
head(df)

# Summary statistics
summary(df)

# Date range
range(df$Date)
```

## Notes for Analysis

1. **Time Series Structure**: Data is already in chronological order
2. **Frequency**: Daily data (252 trading days per year)
3. **Seasonality**: Consider quarterly and annual patterns
4. **Outliers**: Check for extreme price movements
5. **Stationarity**: Test for stationarity before modeling
6. **Differencing**: May be required for stationarity
7. **Volume Analysis**: Consider volume-price relationships

This dataset provides an excellent foundation for learning and applying time series analysis techniques in R, with sufficient data points and complexity to demonstrate various analytical methods.
