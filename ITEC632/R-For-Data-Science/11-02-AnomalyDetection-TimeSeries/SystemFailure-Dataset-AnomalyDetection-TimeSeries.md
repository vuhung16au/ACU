# System Failure Dataset - Anomaly Detection Analysis

## Dataset Overview

The **System Failure Dataset** (`ambient_temperature_system_failure.csv`) is part of the Numenta Anomaly Benchmark (NAB) collection. This dataset contains ambient temperature readings from a system that experienced failures, making it an ideal candidate for demonstrating anomaly detection techniques in time series data.

## Dataset Information

- **Source**: Numenta Anomaly Benchmark (NAB)
- **File**: `ambient_temperature_system_failure.csv`
- **Location**: `SystemFailure-Dataset/ambient_temperature_system_failure.csv`
- **Format**: CSV (Comma-Separated Values)
- **Encoding**: UTF-8

## Dataset Structure

The dataset contains **7,260 observations** with **2 columns**:

### Columns Description

| Column | Type | Description | Example |
|--------|------|-------------|---------|
| `timestamp` | String | Date and time of the temperature reading in ISO format | `2013-07-04 00:00:00` |
| `value` | Numeric | Ambient temperature measurement in Fahrenheit | `69.88083514` |

## Data Characteristics

### Temporal Coverage
- **Start Date**: July 4, 2013, 00:00:00
- **End Date**: December 31, 2014, 23:00:00
- **Duration**: Approximately 1.5 years
- **Frequency**: Hourly measurements
- **Total Observations**: 7,260 data points

### Temperature Statistics
- **Range**: Approximately 40°F to 100°F
- **Typical Values**: Most readings fall between 60°F and 80°F
- **Seasonal Patterns**: Expected seasonal variations in ambient temperature
- **Anomalies**: Contains known system failure events

## Data Quality

### Completeness
- **Missing Values**: Minimal or no missing values
- **Data Integrity**: High-quality, preprocessed dataset
- **Consistency**: Regular hourly intervals

### Known Issues
- **System Failures**: The dataset contains periods where the system experienced failures
- **Anomalous Readings**: Some temperature readings may be outside normal ranges
- **Temporal Gaps**: Potential gaps during system downtime

## Use Cases

This dataset is particularly suitable for:

### 1. Anomaly Detection
- **Statistical Methods**: Z-score, IQR-based detection
- **Time Series Methods**: STL decomposition, seasonal decomposition
- **Machine Learning**: Isolation Forest, One-Class SVM

### 2. System Monitoring
- **Predictive Maintenance**: Early warning systems
- **Equipment Monitoring**: Temperature-based failure detection
- **Quality Control**: Process monitoring and control

### 3. Research Applications
- **Algorithm Comparison**: Benchmarking different anomaly detection methods
- **Performance Evaluation**: Testing detection accuracy
- **Method Development**: Developing new anomaly detection techniques

## Anomaly Detection Challenges

### 1. Seasonal Variations
- **Challenge**: Distinguishing between seasonal patterns and anomalies
- **Solution**: Use time series decomposition methods

### 2. Gradual Drifts
- **Challenge**: Detecting slow changes in system behavior
- **Solution**: Implement trend analysis and change point detection

### 3. Contextual Anomalies
- **Challenge**: Anomalies that are contextually significant but not statistically extreme
- **Solution**: Use domain knowledge and contextual analysis

## Preprocessing Requirements

### 1. Data Loading
```r
# Load the dataset
data <- read_csv("SystemFailure-Dataset/ambient_temperature_system_failure.csv")

# Convert timestamp to datetime format
data <- data %>%
  mutate(timestamp = ymd_hms(timestamp)) %>%
  arrange(timestamp)
```

### 2. Data Validation
- Check for missing values
- Validate timestamp format
- Ensure data is sorted chronologically
- Verify temperature ranges are reasonable

### 3. Feature Engineering
- Create time-based features (hour, day, month, season)
- Calculate rolling statistics (mean, standard deviation)
- Generate lag features for temporal analysis

## Expected Anomalies

Based on the dataset characteristics, expected anomalies include:

### 1. Temperature Spikes
- Sudden increases or decreases in temperature
- Values outside normal operating ranges
- Rapid temperature changes

### 2. System Failures
- Periods of system downtime
- Unusual temperature patterns during failures
- Recovery patterns after system restoration

### 3. Seasonal Anomalies
- Unexpected seasonal patterns
- Deviations from expected temperature cycles
- Unusual weather-related anomalies

## Performance Metrics

### Detection Accuracy
- **Precision**: Proportion of detected anomalies that are true anomalies
- **Recall**: Proportion of true anomalies that are detected
- **F1-Score**: Harmonic mean of precision and recall

### Computational Performance
- **Processing Time**: Time required for anomaly detection
- **Memory Usage**: Memory requirements for analysis
- **Scalability**: Performance with larger datasets

## References

- [Numenta Anomaly Benchmark (NAB)](https://github.com/numenta/NAB)
- [NAB Dataset Documentation](https://github.com/numenta/NAB/tree/master/data)
- [Anomaly Detection in Time Series Data](https://www.geeksforgeeks.org/machine-learning/anomaly-detection-in-time-series-data/)

## License

This dataset is part of the Numenta Anomaly Benchmark and is available under the Apache License 2.0. Please refer to the original NAB repository for complete licensing information.

## Citation

If you use this dataset in your research, please cite:

```
Lavin, A., & Ahmad, S. (2015). Evaluating real-time anomaly detection algorithms--the Numenta anomaly benchmark. In 2015 IEEE 14th international conference on machine learning and applications (ICMLA) (pp. 38-44). IEEE.
```
