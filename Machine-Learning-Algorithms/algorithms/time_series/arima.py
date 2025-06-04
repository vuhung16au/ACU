## ARIMA (AutoRegressive Integrated Moving Average) Time Series Analysis

## 1. Import Required Libraries
import numpy as np
import pandas as pd
import seaborn as sns
from typing import Tuple, Optional, List
from scipy import stats
from statsmodels.tsa.stattools import adfuller
from statsmodels.tsa.arima.model import ARIMA as StatsmodelsARIMA
from statsmodels.graphics.tsaplots import plot_acf
import matplotlib.pyplot as plt
import os

# Set random seed for reproducibility
np.random.seed(2220)

## 2. Helper Functions

def check_stationarity(data: np.ndarray) -> Tuple[bool, float]:
    """
    Check if time series is stationary using Augmented Dickey-Fuller test.
    
    Args:
        data: Time series data
            
    Returns:
        Tuple of (is_stationary, p_value)
    """
    result = adfuller(data)
    return result[1] < 0.05, result[1]

def difference(data: np.ndarray, order: int = 1) -> np.ndarray:
    """
    Difference time series.
    
    Args:
        data: Time series data
        order: Order of differencing
            
    Returns:
        Differenced time series
    """
    diff = data.copy()
    for _ in range(order):
        diff = np.diff(diff)
    return diff

def inverse_difference(data: np.ndarray, original: np.ndarray, order: int = 1) -> np.ndarray:
    """
    Inverse difference time series.
    
    Args:
        data: Differenced time series
        original: Original time series
        order: Order of differencing
            
    Returns:
        Original time series
    """
    undiff = data.copy()
    for i in range(order):
        undiff = np.cumsum(undiff)
        if i == 0:
            undiff = np.concatenate([original[:1], undiff])
    return undiff

## 3. ARIMA Model Implementation

def fit_arima(
    data: np.ndarray,
    p: int = 1,  # Order of autoregressive term
    d: int = 1,  # Order of differencing
    q: int = 1,  # Order of moving average term
    seasonal: bool = False,  # Whether to use seasonal ARIMA
    P: int = 0,  # Order of seasonal autoregressive term
    D: int = 0,  # Order of seasonal differencing
    Q: int = 0,  # Order of seasonal moving average term
    m: int = 1   # Number of periods in each season
) -> Tuple[StatsmodelsARIMA, np.ndarray, np.ndarray]:
    """
    Fit ARIMA model to data.
    
    Hyperparameters explained:
    - p: Number of lag observations in the model (AR term)
    - d: Number of times the data is differenced to make it stationary
    - q: Size of the moving average window (MA term)
    - seasonal: Whether to use seasonal ARIMA (SARIMA)
    - P: Seasonal AR term order
    - D: Seasonal differencing order
    - Q: Seasonal MA term order
    - m: Number of periods in each season
    
    Args:
        data: Time series data
        p, d, q: Non-seasonal ARIMA parameters
        seasonal: Whether to use seasonal ARIMA
        P, D, Q, m: Seasonal ARIMA parameters
            
    Returns:
        Tuple of (fitted model, fitted values, residuals)
    """
    # Check stationarity
    is_stationary, p_value = check_stationarity(data)
    if not is_stationary and d == 0:
        print(f"Warning: Data is not stationary (p-value: {p_value:.4f})")
        print("Consider increasing differencing order (d)")
    
    # Create and fit model
    if seasonal:
        order = (p, d, q)
        seasonal_order = (P, D, Q, m)
        model = StatsmodelsARIMA(
            data,
            order=order,
            seasonal_order=seasonal_order
        )
    else:
        model = StatsmodelsARIMA(data, order=(p, d, q))
    
    results = model.fit()
    fitted_values = results.fittedvalues
    residuals = results.resid
    
    return results, fitted_values, residuals

def forecast_arima(
    model_results,
    steps: int
) -> Tuple[np.ndarray, np.ndarray, np.ndarray]:
    """
    Forecast future values.
    
    Args:
        model_results: Fitted ARIMA model results
        steps: Number of steps to forecast
            
    Returns:
        Tuple of (forecast, lower_bound, upper_bound)
    """
    if model_results is None:
        raise ValueError("Model must be fitted before forecasting")
    
    # Use get_forecast for proper confidence intervals
    forecast_obj = model_results.get_forecast(steps=steps)
    forecast = forecast_obj.predicted_mean
    conf_int = forecast_obj.conf_int(alpha=0.05)
    lower_bound = conf_int.iloc[:, 0]
    upper_bound = conf_int.iloc[:, 1]
    
    return forecast, lower_bound, upper_bound

def plot_diagnostics(residuals: np.ndarray) -> None:
    """
    Plot model diagnostics using seaborn and save to PNG files.
    
    Args:
        residuals: Model residuals
    """
    # Create directory if it doesn't exist
    os.makedirs('algorithms/time_series/arima', exist_ok=True)
    
    # Set seaborn style
    sns.set_style("whitegrid")
    
    # Create figure with subplots
    fig, axes = plt.subplots(2, 2, figsize=(12, 8))
    
    # Plot residuals
    sns.lineplot(data=residuals, ax=axes[0, 0])
    axes[0, 0].set_title('Residuals')
    axes[0, 0].set_xlabel('Time')
    axes[0, 0].set_ylabel('Residual')
    
    # Plot residual histogram
    sns.histplot(data=residuals, bins=30, ax=axes[0, 1])
    axes[0, 1].set_title('Residual Histogram')
    axes[0, 1].set_xlabel('Residual')
    axes[0, 1].set_ylabel('Frequency')
    
    # Plot ACF of residuals
    plot_acf(residuals, ax=axes[1, 0])
    axes[1, 0].set_title('ACF of Residuals')
    
    # Plot QQ plot
    stats.probplot(residuals, dist="norm", plot=axes[1, 1])
    axes[1, 1].set_title('Q-Q Plot')
    
    plt.tight_layout()
    plt.savefig('algorithms/time_series/arima/arima-diagnostics.png')
    plt.close()

## 4. Example Usage with CO2 Dataset

# Load CO2 dataset
from statsmodels.datasets import co2
data = co2.load().data
data = data['co2'].resample('ME').mean().ffill()

# Split into train and test
train_size = int(len(data) * 0.8)
train_data = data[:train_size]
test_data = data[train_size:]

# Create and fit ARIMA model
# Hyperparameters explained:
# p=2: Use 2 lag observations for AR term
# d=1: First-order differencing to make data stationary
# q=2: Use 2 lagged forecast errors in MA term
# seasonal=True: Enable seasonal ARIMA
# P=1: First-order seasonal AR term
# D=1: First-order seasonal differencing
# Q=1: First-order seasonal MA term
# m=12: 12 periods per season (monthly data)
model_results, fitted_values, residuals = fit_arima(
    train_data,
    p=2, d=1, q=2,
    seasonal=True,
    P=1, D=1, Q=1, m=12
)

# Print model summary
print("\nModel Summary:")
print(model_results.summary().as_text())

# Forecast
forecast, lower, upper = forecast_arima(model_results, steps=len(test_data))

# Plot results using seaborn
os.makedirs('algorithms/time_series/arima', exist_ok=True)
plt.figure(figsize=(12, 6))
sns.set_style("whitegrid")

# Plot actual data
sns.lineplot(data=data, label='Actual', color='blue')

# Plot forecast
sns.lineplot(data=pd.Series(forecast, index=test_data.index),
             label='Forecast', color='red')

# Plot confidence intervals
plt.fill_between(
    test_data.index,
    lower,
    upper,
    color='red',
    alpha=0.1,
    label='95% Confidence Interval'
)

plt.title('ARIMA Forecast')
plt.xlabel('Time')
plt.ylabel('CO2 Level')
plt.legend()

# Save plot instead of displaying
plt.savefig('algorithms/time_series/arima/arima-forecast.png')
plt.close()

# Ensure directory exists for ACF plot
os.makedirs('algorithms/time_series/arima', exist_ok=True)
# Plot ACF
plt.figure(figsize=(10, 6))
plot_acf(data, lags=40)
plt.title('Autocorrelation Function')
plt.savefig('algorithms/time_series/arima/arima-acf.png')
plt.close()

# Ensure directory exists for PACF plot
os.makedirs('algorithms/time_series/arima', exist_ok=True)
# Plot PACF
from statsmodels.graphics.tsaplots import plot_pacf
plt.figure(figsize=(10, 6))
plot_pacf(data, lags=40)
plt.title('Partial Autocorrelation Function')
plt.savefig('algorithms/time_series/arima/arima-pacf.png')
plt.close()

# Plot diagnostics
plot_diagnostics(residuals)