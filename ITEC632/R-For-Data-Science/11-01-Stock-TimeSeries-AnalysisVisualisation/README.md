# Stock Time Series Analysis and Visualization in R

## Project Overview
This project demonstrates comprehensive time series analysis and visualization techniques using R, focusing on stock market data. The analysis includes trend analysis, stationarity testing, autocorrelation analysis, and various visualization techniques commonly used in financial time series analysis.

## Dataset
- **Source**: Historical stock data for AABA (Altaba Inc.) from 2006-2016
- **Location**: `Stock-Dataset/stock_data.csv`
- **Records**: 2,770 daily observations
- **Features**: Date, Open, High, Low, Close, Volume, Name

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

## Files Structure

```
11-01-Stock-TimeSeries-AnalysisVisualisation/
├── README.md                                    # This file
├── Stock-TimeSeries-AnalysisVisualisation.R     # Main R script
├── Stock-TimeSeries-AnalysisVisualisation.Rmd   # R Markdown file
├── Stock-TimeSeries-AnalysisVisualisation.md    # Generated documentation
├── Stock-Dataset-Stock-TimeSeries-AnalysisVisualisation.md  # Dataset description
├── images/                                      # Generated plots and visualizations
└── Stock-Dataset/
    ├── stock_data.csv                          # Stock dataset
    └── README.md                               # Dataset description
```

## Key R Packages Used
- `ggplot2` - Advanced plotting and visualization
- `dplyr` - Data manipulation and transformation
- `lubridate` - Date and time handling
- `forecast` - Time series forecasting and analysis
- `tseries` - Time series analysis functions
- `zoo` - Time series objects and operations
- `gridExtra` - Plot arrangement and layout

## Usage

### Running the R Script
```bash
Rscript Stock-TimeSeries-AnalysisVisualisation.R
```

### Running the R Markdown
```bash
Rscript -e "rmarkdown::render('Stock-TimeSeries-AnalysisVisualisation.Rmd')"
```

## Output
The analysis generates:
- Multiple time series plots with white backgrounds
- Statistical test results
- Trend analysis visualizations
- Stationarity test outputs
- Autocorrelation plots
- Moving average comparisons

## Learning Objectives
- Understand time series data structure in R
- Learn stationarity testing techniques
- Master time series visualization methods
- Apply financial data analysis concepts
- Practice advanced R plotting techniques

## Prerequisites
- Basic knowledge of R programming
- Understanding of time series concepts
- Familiarity with financial data analysis
- R packages: ggplot2, dplyr, lubridate, forecast, tseries, zoo, gridExtra

## References
- Based on Python implementation from GeeksforGeeks
- Time series analysis best practices
- Financial data visualization techniques
