# Anomaly Detection in Time Series Data

## Project Overview

This project demonstrates comprehensive anomaly detection techniques for time series data using R. We analyze ambient temperature data from a system that experienced failures to identify anomalous patterns and system failures using multiple detection methods.

## Dataset

The project uses the **System Failure Dataset** (`ambient_temperature_system_failure.csv`) from the Numenta Anomaly Benchmark (NAB), which contains:
- **timestamp**: Date and time of temperature readings
- **value**: Ambient temperature measurements in Fahrenheit
- **7,267 observations** spanning from July 2013 to December 2014

## Anomaly Detection Methods Implemented

### 1. Statistical Anomaly Detection (Z-Score)
- **Method**: Identifies anomalies using Z-score threshold (|z| > 3)
- **Results**: Detected 19 anomalies (0.26% of data)
- **Strengths**: Simple, interpretable, good for normally distributed data
- **Use Case**: Baseline statistical approach

### 2. IQR-based Anomaly Detection
- **Method**: Uses Interquartile Range (IQR) with 1.5×IQR rule
- **Results**: Detected 35 anomalies (0.48% of data)
- **Strengths**: Robust to outliers, works well with skewed distributions
- **Use Case**: Non-parametric outlier detection

### 3. Time Series Decomposition
- **Method**: STL decomposition with IQR-based anomaly detection on remainder
- **Results**: Detected 0 anomalies (0% of data)
- **Strengths**: Accounts for trend and seasonality
- **Use Case**: Complex time series with temporal patterns

### 4. Isolation Forest
- **Method**: Unsupervised machine learning algorithm
- **Results**: Detected 1,219 anomalies (16.77% of data)
- **Strengths**: Captures complex non-linear patterns
- **Use Case**: High-dimensional anomaly detection

## Key Findings

### Performance Comparison
| Method | Anomalies Detected | Percentage | Characteristics |
|--------|-------------------|------------|-----------------|
| Z-Score | 19 | 0.26% | Conservative, statistical |
| IQR | 35 | 0.48% | Moderate, robust |
| Decomposition | 0 | 0.00% | Very conservative |
| Isolation Forest | 1,219 | 16.77% | Aggressive, ML-based |

### Dataset Statistics
- **Total Observations**: 7,267
- **Temperature Range**: 57.46°F to 86.22°F
- **Mean Temperature**: 71.24°F
- **Standard Deviation**: 4.25°F
- **Time Period**: July 2013 to December 2014

## Visualizations Generated

The analysis produces comprehensive visualizations:

1. **Time Series Overview**: Basic temperature plot over time
2. **Z-Score Anomalies**: Statistical outliers highlighted in red
3. **IQR Anomalies**: Quartile-based outliers with threshold lines
4. **Decomposition Results**: Time series decomposition with anomaly detection
5. **Isolation Forest**: Machine learning detected anomalies
6. **Method Comparison**: Bar chart comparing all methods
7. **Comprehensive View**: Combined visualization of multiple methods

## Technical Implementation

### Required R Packages
```r
library(tidyverse)    # Data manipulation and visualization
library(lubridate)    # Date/time handling
library(anomalize)    # Time series anomaly detection
library(ggplot2)      # Advanced plotting
library(gridExtra)    # Multiple plot arrangements
library(plotly)       # Interactive visualizations
library(zoo)          # Time series operations
library(isotree)      # Isolation Forest implementation
```

### Code Structure
- **Data Loading**: Robust timestamp parsing with error handling
- **Preprocessing**: Data cleaning and validation
- **Multiple Methods**: Four different detection approaches
- **Visualization**: White-background plots for publication
- **Error Handling**: Graceful fallbacks for package issues

## Usage Instructions

### Running the R Script
```bash
cd 11-02-AnomalyDetection-TimeSeries
Rscript AnomalyDetection-TimeSeries.R
```

### Rendering the R Markdown
```bash
Rscript -e "rmarkdown::render('AnomalyDetection-TimeSeries.Rmd')"
```

### Output Files
- **Images**: All plots saved to `images/` directory
- **HTML Report**: `AnomalyDetection-TimeSeries.html`
- **Documentation**: `README.md` and dataset description

## Applications

This implementation is suitable for:

### 1. System Monitoring
- **Predictive Maintenance**: Early warning systems for equipment failures
- **Quality Control**: Process monitoring and anomaly detection
- **Performance Monitoring**: System health assessment

### 2. Research and Development
- **Algorithm Comparison**: Benchmarking different detection methods
- **Method Evaluation**: Testing detection accuracy and performance
- **Educational Purposes**: Learning anomaly detection concepts

### 3. Industrial Applications
- **Temperature Monitoring**: HVAC and environmental control systems
- **Sensor Data Analysis**: IoT device anomaly detection
- **Process Control**: Manufacturing and production monitoring

## Method Selection Guidelines

### Choose Z-Score When:
- Data is normally distributed
- Simple statistical approach is sufficient
- Need interpretable results
- Baseline comparison required

### Choose IQR When:
- Data has outliers or is skewed
- Robust statistical method needed
- Non-parametric approach preferred
- Moderate sensitivity desired

### Choose Decomposition When:
- Time series has clear trends/seasonality
- Temporal patterns are important
- Need to separate trend from anomalies
- Complex time series analysis required

### Choose Isolation Forest When:
- Complex, non-linear patterns exist
- High-dimensional data
- Machine learning approach preferred
- Maximum detection sensitivity needed

## Performance Considerations

### Computational Requirements
- **Memory**: Moderate (handles 7K+ observations efficiently)
- **Processing Time**: Fast for statistical methods, slower for ML
- **Scalability**: Good for medium-sized datasets

### Accuracy Trade-offs
- **Conservative Methods** (Z-Score, IQR): Lower false positives, may miss subtle anomalies
- **Aggressive Methods** (Isolation Forest): Higher sensitivity, more false positives
- **Balanced Approach**: Use multiple methods for comprehensive analysis

## Future Enhancements

### Potential Improvements
1. **Ensemble Methods**: Combine multiple detection approaches
2. **Real-time Processing**: Stream processing capabilities
3. **Interactive Dashboards**: Shiny applications for live monitoring
4. **Advanced ML**: Deep learning approaches for complex patterns
5. **Custom Thresholds**: User-defined sensitivity parameters

### Additional Methods
1. **LSTM Networks**: Deep learning for sequential data
2. **One-Class SVM**: Support vector machine approach
3. **DBSCAN Clustering**: Density-based anomaly detection
4. **Change Point Detection**: Structural break identification

## References

- [Numenta Anomaly Benchmark (NAB)](https://github.com/numenta/NAB)
- [Anomaly Detection in Time Series Data - GeeksforGeeks](https://www.geeksforgeeks.org/machine-learning/anomaly-detection-in-time-series-data/)
- [R anomalize package documentation](https://business-science.github.io/timetk/articles/TK08_Automatic_Anomaly_Detection.html)
- [Isolation Forest Algorithm](https://en.wikipedia.org/wiki/Isolation_forest)

## Conclusion

This project successfully demonstrates multiple approaches to anomaly detection in time series data. The implementation provides:

- **Comprehensive Analysis**: Four different detection methods
- **Robust Implementation**: Error handling and fallback methods
- **Clear Visualizations**: Publication-ready plots with white backgrounds
- **Educational Value**: Well-documented code and explanations
- **Practical Applications**: Real-world system monitoring capabilities

The results show that different methods are suitable for different scenarios, and the choice of method should depend on the specific requirements of the application, including the nature of the data, computational resources, and the need for interpretability.
