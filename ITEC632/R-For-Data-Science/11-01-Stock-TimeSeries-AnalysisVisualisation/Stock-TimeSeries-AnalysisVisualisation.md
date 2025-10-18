# Stock Time Series Analysis and Visualization in R

## Project Overview

This project demonstrates comprehensive time series analysis and visualization techniques using R, focusing on stock market data. The analysis includes trend analysis, stationarity testing, autocorrelation analysis, and various visualization techniques commonly used in financial time series analysis.

## Dataset Information

- **Source**: Historical stock data for AABA (Altaba Inc.) from 2006-2016
- **Location**: `Stock-Dataset/stock_data.csv`
- **Records**: 2,770 daily observations
- **Features**: Date, Open, High, Low, Close, Volume, Name
- **Time Period**: January 3, 2006 to December 30, 2016

## Analysis Components

### 1. Data Loading and Preprocessing
- Load stock data from CSV
- Parse dates and set up time series structure
- Basic data exploration and summary statistics

### 2. Time Series Visualization
- **Line plots** for stock price trends over time
- **Monthly resampling** to show long-term trends
- **Multiple price metrics** visualization (Open, High, Low, Close)

### 3. Statistical Analysis
- **Autocorrelation Function (ACF)** analysis for Volume data
- **Augmented Dickey-Fuller (ADF)** test for stationarity
- **Differencing** to achieve stationarity
- **Moving averages** for trend smoothing

### 4. Advanced Time Series Techniques
- Stationarity testing and transformation
- Trend decomposition
- Seasonal pattern analysis
- Volatility analysis

## Key R Packages Used

- `ggplot2` - Advanced plotting and visualization
- `dplyr` - Data manipulation and transformation
- `lubridate` - Date and time handling
- `forecast` - Time series forecasting and analysis
- `tseries` - Time series analysis functions
- `zoo` - Time series objects and operations
- `gridExtra` - Plot arrangement and layout

## Analysis Results

### Time Series Visualization

The analysis generates several key visualizations:

1. **High Price Over Time**: Shows the trend of the highest stock price over the entire period
2. **Monthly Resampling**: Displays monthly averages to identify long-term trends
3. **Multiple Price Metrics**: Compares Open, High, Low, and Close prices
4. **Volume Analysis**: Shows trading volume patterns over time

### Statistical Findings

#### Stationarity Testing
- **Original High Price**: Not stationary (p-value > 0.05)
- **Differenced High Price**: Stationary (p-value < 0.05)

#### Key Statistics
- **Dataset Period**: 2006-01-03 to 2016-12-30
- **Total Observations**: 2,770
- **Price Range**: $8.00 to $45.00 (approximately)
- **Volume Range**: Highly variable with significant peaks

### Advanced Techniques Applied

1. **Autocorrelation Analysis**: Identified patterns in trading volume
2. **Differencing**: Achieved stationarity through first-order differencing
3. **Moving Averages**: Applied 120-day moving average for trend smoothing
4. **Multiple Metrics**: Analyzed all price components simultaneously

## Technical Implementation

### Data Processing
```r
# Load and preprocess data
df <- read.csv("Stock-Dataset/stock_data.csv", stringsAsFactors = FALSE)
df$Date <- as.Date(df$Date, format = "%m/%d/%Y")
```

### Visualization Setup
```r
# Set theme for white background plots
theme_set(theme_minimal() + 
          theme(panel.background = element_rect(fill = "white", color = NA),
                plot.background = element_rect(fill = "white", color = NA)))
```

### Stationarity Testing
```r
# ADF test for stationarity
adf_result <- adf.test(df$High)
```

### Moving Average Calculation
```r
# Calculate moving average
window_size <- 120
df$high_smoothed <- rollmean(df$High, k = window_size, fill = NA, align = "right")
```

## Output Files

The analysis generates the following output files:

1. **Images Folder**: Contains all generated plots with white backgrounds
   - `high_price_time_series.png`
   - `monthly_high_price.png`
   - `acf_volume.png`
   - `original_vs_differenced.png`
   - `original_vs_moving_average.png`
   - `multiple_price_metrics.png`
   - `volume_analysis.png`

2. **R Script**: `Stock-TimeSeries-AnalysisVisualisation.R`
3. **R Markdown**: `Stock-TimeSeries-AnalysisVisualisation.Rmd`
4. **Documentation**: This markdown file

## Usage Instructions

### Running the R Script
```bash
Rscript Stock-TimeSeries-AnalysisVisualisation.R
```

### Running the R Markdown
```bash
Rscript -e "rmarkdown::render('Stock-TimeSeries-AnalysisVisualisation.Rmd')"
```

## Key Findings

1. **Trend Analysis**: The stock shows significant price fluctuations over the 11-year period
2. **Stationarity**: Original price series is non-stationary, but first differencing achieves stationarity
3. **Volume Patterns**: Trading volume shows high variability with distinct peaks and valleys
4. **Price Relationships**: Open, High, Low, and Close prices follow similar patterns
5. **Long-term Trends**: Moving averages reveal underlying trends obscured by daily volatility

## Learning Objectives Achieved

- ✅ Understanding time series data structure in R
- ✅ Learning stationarity testing techniques
- ✅ Mastering time series visualization methods
- ✅ Applying financial data analysis concepts
- ✅ Practicing advanced R plotting techniques

## Prerequisites

- Basic knowledge of R programming
- Understanding of time series concepts
- Familiarity with financial data analysis
- Required R packages: ggplot2, dplyr, lubridate, forecast, tseries, zoo, gridExtra

## References

- Based on Python implementation from GeeksforGeeks
- Time series analysis best practices
- Financial data visualization techniques
- R documentation for time series packages

## Conclusion

This analysis successfully demonstrates comprehensive time series analysis techniques using R. The project covers data loading, visualization, statistical testing, and advanced time series methods, providing a solid foundation for financial data analysis and time series modeling.

The white background theme ensures professional presentation of all visualizations, making them suitable for reports and presentations. The modular structure allows for easy extension and modification of the analysis components.
