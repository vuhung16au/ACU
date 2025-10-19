# Anomaly Detection in Time Series Data

## Project Overview

This project demonstrates anomaly detection techniques for time series data using R. We analyze ambient temperature data from a system that experienced failures to identify anomalous patterns and system failures.

## Dataset

The project uses the **System Failure Dataset** (`ambient_temperature_system_failure.csv`) which contains:
- **timestamp**: Date and time of temperature readings
- **value**: Ambient temperature measurements in Fahrenheit

## Files Structure

```
11-02-AnomalyDetection-TimeSeries/
├── README.md                                    # This file
├── AnomalyDetection-TimeSeries.R               # Main R script
├── AnomalyDetection-TimeSeries.Rmd             # R Markdown file
├── AnomalyDetection-TimeSeries.md              # Generated documentation
├── SystemFailure-Dataset-AnomalyDetection-TimeSeries.md  # Dataset description
├── images/                                     # Generated plots and visualizations
└── SystemFailure-Dataset/
    ├── ambient_temperature_system_failure.csv  # Dataset file
    └── README.md                               # Dataset description
```

## Key Features

### Anomaly Detection Methods
1. **Statistical Methods**: Z-score and IQR-based detection
2. **Time Series Decomposition**: STL decomposition with anomaly detection
3. **Machine Learning**: Isolation Forest for unsupervised anomaly detection
4. **Visualization**: Comprehensive plots showing original data and detected anomalies

### Analysis Components
- **Data Loading and Preprocessing**: Handle timestamp conversion and data cleaning
- **Exploratory Data Analysis**: Basic statistics and time series visualization
- **Multiple Detection Algorithms**: Compare different anomaly detection approaches
- **Performance Evaluation**: Metrics for detection accuracy
- **Interactive Visualizations**: Clear plots with white backgrounds

## Usage

### Running the R Script
```bash
Rscript AnomalyDetection-TimeSeries.R
```

### Rendering the R Markdown
```bash
Rscript -e "rmarkdown::render('AnomalyDetection-TimeSeries.Rmd')"
```

## Dependencies

Required R packages:
- `tidyverse` - Data manipulation and visualization
- `lubridate` - Date/time handling
- `anomalize` - Time series anomaly detection
- `isolationForest` - Machine learning anomaly detection
- `ggplot2` - Advanced plotting
- `plotly` - Interactive visualizations
- `gridExtra` - Multiple plot arrangements

## Output

The analysis generates:
- Time series plots with anomaly highlighting
- Detection method comparisons
- Performance metrics
- Statistical summaries
- Interactive visualizations

## Applications

This project is useful for:
- **System Monitoring**: Detecting equipment failures
- **Quality Control**: Identifying process anomalies
- **Predictive Maintenance**: Early warning systems
- **Research**: Understanding anomaly detection methods

## References

- [Numenta Anomaly Benchmark (NAB)](https://github.com/numenta/NAB)
- [Anomaly Detection in Time Series Data - GeeksforGeeks](https://www.geeksforgeeks.org/machine-learning/anomaly-detection-in-time-series-data/)
- [R anomalize package documentation](https://business-science.github.io/timetk/articles/TK08_Automatic_Anomaly_Detection.html)
