# Stock Dataset Description

## Overview
This dataset contains historical stock price data for AABA (Altaba Inc.) from 2006 to 2016. The dataset includes daily stock market information with various price metrics and trading volume.

## Dataset Information
- **File**: `stock_data.csv`
- **Time Period**: January 3, 2006 to December 30, 2016
- **Total Records**: 2,770 observations
- **Stock Symbol**: AABA (formerly Yahoo! Inc.)

## Column Descriptions

| Column | Description | Data Type |
|--------|-------------|-----------|
| **Date** | Trading date in MM/DD/YYYY format | Date |
| **Open** | Opening price of the stock on the trading day | Numeric |
| **High** | Highest price reached during the trading day | Numeric |
| **Low** | Lowest price reached during the trading day | Numeric |
| **Close** | Closing price of the stock on the trading day | Numeric |
| **Volume** | Number of shares traded on the trading day | Integer |
| **Name** | Stock symbol (AABA) | Character |

## Data Characteristics
- **Price Range**: Stock prices range from approximately $8 to $45
- **Volume Range**: Daily trading volume varies significantly, with some days having over 100 million shares traded
- **Market Events**: The dataset includes various market events, economic cycles, and company-specific developments
- **Missing Values**: The dataset is complete with no missing values in the core price and volume columns

## Usage Notes
- The dataset is suitable for time series analysis, trend analysis, and financial modeling
- Can be used for technical analysis, moving averages, and volatility studies
- Ideal for demonstrating time series visualization techniques in R
- Contains sufficient data points for seasonal and trend decomposition

## Data Source
This dataset represents real historical stock market data and can be used for educational and research purposes in time series analysis and financial modeling.
