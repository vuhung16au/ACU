## Exponential Smoothing Time Series Analysis

## 1. Import Required Libraries
import numpy as np
import pandas as pd
import seaborn as sns
from typing import Tuple, Optional, Literal
import matplotlib.pyplot as plt
from scipy import stats
import os

# Set random seed for reproducibility
np.random.seed(2220)

## 2. Helper Functions

def initialize_components(
    data: np.ndarray,
    method: Literal['simple', 'double', 'triple'],
    seasonal_periods: int,
    trend: Literal['add', 'mul'],
    seasonal: Literal['add', 'mul']
) -> Tuple[float, Optional[float], Optional[np.ndarray]]:
    """
    Initialize model components.
    
    Args:
        data: Time series data
        method: Smoothing method ('simple', 'double', or 'triple')
        seasonal_periods: Number of periods in a season
        trend: Trend type ('add' or 'mul')
        seasonal: Seasonality type ('add' or 'mul')
        
    Returns:
        Tuple of (level, trend_component, seasonal_component)
    """
    # Initialize level
    if method == 'simple':
        level = np.mean(data.iloc[:seasonal_periods])
    else:
        level = data.iloc[0]
    
    # Initialize trend
    trend_component = None
    if method in ['double', 'triple']:
        if trend == 'add':
            trend_component = np.mean(np.diff(data.iloc[:seasonal_periods]))
        else:  # multiplicative
            trend_component = np.mean(np.diff(data.iloc[:seasonal_periods]) / data.iloc[:-1])
    
    # Initialize seasonality
    seasonal_component = None
    if method == 'triple':
        if seasonal == 'add':
            seasonal_component = np.zeros(seasonal_periods)
            for i in range(seasonal_periods):
                seasonal_component[i] = np.mean(
                    data.iloc[i::seasonal_periods] - level
                )
        else:  # multiplicative
            seasonal_component = np.ones(seasonal_periods)
            for i in range(seasonal_periods):
                seasonal_component[i] = np.mean(
                    data.iloc[i::seasonal_periods] / level
                )
    
    return level, trend_component, seasonal_component

def calculate_fitted_values(
    level: np.ndarray,
    trend: Optional[np.ndarray],
    seasonal: Optional[np.ndarray],
    method: Literal['simple', 'double', 'triple'],
    trend_type: Literal['add', 'mul'],
    seasonal_type: Literal['add', 'mul']
) -> np.ndarray:
    """
    Calculate fitted values from components.
    
    Args:
        level: Level component
        trend: Trend component
        seasonal: Seasonal component
        method: Smoothing method
        trend_type: Trend type
        seasonal_type: Seasonality type
        
    Returns:
        Fitted values
    """
    n = len(level)
    fitted = np.zeros(n)
    
    if method == 'simple':
        fitted = level
    
    elif method == 'double':
        if trend_type == 'add':
            fitted = level + trend
        else:  # multiplicative
            fitted = level * trend
    
    else:  # triple
        if trend_type == 'add':
            if seasonal_type == 'add':
                fitted = level + trend + seasonal
            else:  # multiplicative
                fitted = (level + trend) * seasonal
        else:  # multiplicative
            if seasonal_type == 'add':
                fitted = level * trend + seasonal
            else:  # multiplicative
                fitted = level * trend * seasonal
    
    return fitted

## 3. Exponential Smoothing Model Implementation

def fit_exponential_smoothing(
    data: np.ndarray,
    method: Literal['simple', 'double', 'triple'] = 'simple',
    alpha: float = 0.3,  # Smoothing parameter for level
    beta: float = 0.1,   # Smoothing parameter for trend
    gamma: float = 0.1,  # Smoothing parameter for seasonality
    seasonal_periods: int = 12,  # Number of periods in a season
    trend: Literal['add', 'mul'] = 'add',  # Trend type
    seasonal: Literal['add', 'mul'] = 'add',  # Seasonality type
    damped: bool = False,  # Whether to use damped trend
    phi: float = 0.9  # Damping parameter
) -> Tuple[np.ndarray, np.ndarray, float, Optional[float], Optional[np.ndarray]]:
    """
    Fit exponential smoothing model.
    
    Hyperparameters explained:
    - alpha: Controls the weight of the most recent observation (0 < alpha < 1)
    - beta: Controls the weight of the trend component (0 < beta < 1)
    - gamma: Controls the weight of the seasonal component (0 < gamma < 1)
    - seasonal_periods: Number of periods in a season (e.g., 12 for monthly data)
    - trend: 'add' for additive trend, 'mul' for multiplicative trend
    - seasonal: 'add' for additive seasonality, 'mul' for multiplicative seasonality
    - damped: Whether to use damped trend to prevent explosive forecasts
    - phi: Damping parameter for trend (0 < phi < 1)
    
    Args:
        data: Time series data
        method: Smoothing method
        alpha, beta, gamma: Smoothing parameters
        seasonal_periods: Number of periods in a season
        trend: Trend type
        seasonal: Seasonality type
        damped: Whether to use damped trend
        phi: Damping parameter
        
    Returns:
        Tuple of (fitted values, residuals, final level, final trend, final seasonal)
    """
    n = len(data)
    level, trend_component, seasonal_component = initialize_components(
        data, method, seasonal_periods, trend, seasonal
    )
    
    # Initialize arrays for components
    level_arr = np.zeros(n)
    trend_arr = np.zeros(n) if method in ['double', 'triple'] else None
    seasonal_arr = np.zeros(n) if method == 'triple' else None
    
    # Set initial values
    level_arr[0] = level
    if trend_arr is not None:
        trend_arr[0] = trend_component
    if seasonal_arr is not None:
        seasonal_arr[:seasonal_periods] = seasonal_component
    
    # Fit model
    for t in range(1, n):
        if method == 'simple':
            level_arr[t] = alpha * data.iloc[t] + (1 - alpha) * level_arr[t-1]
        
        elif method == 'double':
            if trend == 'add':
                level_arr[t] = alpha * data.iloc[t] + (1 - alpha) * (level_arr[t-1] + trend_arr[t-1])
                trend_arr[t] = beta * (level_arr[t] - level_arr[t-1]) + (1 - beta) * trend_arr[t-1]
            else:  # multiplicative
                level_arr[t] = alpha * data.iloc[t] + (1 - alpha) * (level_arr[t-1] * trend_arr[t-1])
                trend_arr[t] = beta * (level_arr[t] / level_arr[t-1]) + (1 - beta) * trend_arr[t-1]
        
        else:  # triple
            if trend == 'add':
                if seasonal == 'add':
                    level_arr[t] = alpha * (data.iloc[t] - seasonal_arr[t-seasonal_periods]) + \
                                 (1 - alpha) * (level_arr[t-1] + trend_arr[t-1])
                    trend_arr[t] = beta * (level_arr[t] - level_arr[t-1]) + (1 - beta) * trend_arr[t-1]
                    seasonal_arr[t] = gamma * (data.iloc[t] - level_arr[t]) + \
                                    (1 - gamma) * seasonal_arr[t-seasonal_periods]
                else:  # multiplicative seasonality
                    level_arr[t] = alpha * (data.iloc[t] / seasonal_arr[t-seasonal_periods]) + \
                                 (1 - alpha) * (level_arr[t-1] + trend_arr[t-1])
                    trend_arr[t] = beta * (level_arr[t] / level_arr[t-1]) + (1 - beta) * trend_arr[t-1]
                    seasonal_arr[t] = gamma * (data.iloc[t] / level_arr[t]) + \
                                    (1 - gamma) * seasonal_arr[t-seasonal_periods]
            else:  # multiplicative trend
                if seasonal == 'add':
                    level_arr[t] = alpha * (data.iloc[t] - seasonal_arr[t-seasonal_periods]) + \
                                 (1 - alpha) * (level_arr[t-1] * trend_arr[t-1])
                    trend_arr[t] = beta * (level_arr[t] / level_arr[t-1]) + (1 - beta) * trend_arr[t-1]
                    seasonal_arr[t] = gamma * (data.iloc[t] - level_arr[t]) + \
                                    (1 - gamma) * seasonal_arr[t-seasonal_periods]
                else:  # multiplicative seasonality
                    level_arr[t] = alpha * (data.iloc[t] / seasonal_arr[t-seasonal_periods]) + \
                                 (1 - alpha) * (level_arr[t-1] * trend_arr[t-1])
                    trend_arr[t] = beta * (level_arr[t] / level_arr[t-1]) + (1 - beta) * trend_arr[t-1]
                    seasonal_arr[t] = gamma * (data.iloc[t] / level_arr[t]) + \
                                    (1 - gamma) * seasonal_arr[t-seasonal_periods]
    
    # Calculate fitted values
    fitted_values = calculate_fitted_values(
        level_arr, trend_arr, seasonal_arr,
        method, trend, seasonal
    )
    residuals = data - fitted_values
    
    return (
        fitted_values,
        residuals,
        level_arr[-1],
        trend_arr[-1] if trend_arr is not None else None,
        seasonal_arr[-seasonal_periods:] if seasonal_arr is not None else None
    )

def forecast_exponential_smoothing(
    level: float,
    trend: Optional[float],
    seasonal: Optional[np.ndarray],
    steps: int,
    method: Literal['simple', 'double', 'triple'],
    trend_type: Literal['add', 'mul'],
    seasonal_type: Literal['add', 'mul'],
    seasonal_periods: int,
    damped: bool = False,
    phi: float = 0.9
) -> Tuple[np.ndarray, np.ndarray, np.ndarray]:
    """
    Forecast future values.
    
    Args:
        level: Final level component
        trend: Final trend component
        seasonal: Final seasonal component
        steps: Number of steps to forecast
        method: Smoothing method
        trend_type: Trend type
        seasonal_type: Seasonality type
        seasonal_periods: Number of periods in a season
        damped: Whether to use damped trend
        phi: Damping parameter
        
    Returns:
        Tuple of (forecast, lower_bound, upper_bound)
    """
    # Initialize forecast array
    forecast = np.zeros(steps)
    
    # Generate forecast
    for t in range(steps):
        if method == 'simple':
            forecast[t] = level
        
        elif method == 'double':
            if trend_type == 'add':
                if damped:
                    forecast[t] = level + phi * trend * (1 - phi ** (t + 1)) / (1 - phi)
                else:
                    forecast[t] = level + trend * (t + 1)
            else:  # multiplicative
                if damped:
                    forecast[t] = level * (trend ** (phi * (t + 1)))
                else:
                    forecast[t] = level * (trend ** (t + 1))
        
        else:  # triple
            if trend_type == 'add':
                if seasonal_type == 'add':
                    if damped:
                        forecast[t] = level + phi * trend * (1 - phi ** (t + 1)) / (1 - phi) + \
                                    seasonal[t % seasonal_periods]
                    else:
                        forecast[t] = level + trend * (t + 1) + seasonal[t % seasonal_periods]
                else:  # multiplicative
                    if damped:
                        forecast[t] = (level + phi * trend * (1 - phi ** (t + 1)) / (1 - phi)) * \
                                    seasonal[t % seasonal_periods]
                    else:
                        forecast[t] = (level + trend * (t + 1)) * seasonal[t % seasonal_periods]
            else:  # multiplicative
                if seasonal_type == 'add':
                    if damped:
                        forecast[t] = level * (trend ** (phi * (t + 1))) + seasonal[t % seasonal_periods]
                    else:
                        forecast[t] = level * (trend ** (t + 1)) + seasonal[t % seasonal_periods]
                else:  # multiplicative
                    if damped:
                        forecast[t] = level * (trend ** (phi * (t + 1))) * seasonal[t % seasonal_periods]
                    else:
                        forecast[t] = level * (trend ** (t + 1)) * seasonal[t % seasonal_periods]
    
    # Calculate confidence intervals
    z_value = stats.norm.ppf(0.975)  # 95% confidence interval
    forecast_std = np.std(forecast) * np.sqrt(np.arange(1, steps + 1))
    lower_bound = forecast - z_value * forecast_std
    upper_bound = forecast + z_value * forecast_std
    
    return forecast, lower_bound, upper_bound

def plot_diagnostics(residuals: np.ndarray) -> None:
    """
    Plot model diagnostics using seaborn and save to PNG file.
    
    Args:
        residuals: Model residuals
    """
    # Create directory if it doesn't exist
    os.makedirs('algorithms/time_series/exponential_smoothing', exist_ok=True)
    
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
    from statsmodels.graphics.tsaplots import plot_acf
    plot_acf(residuals, ax=axes[1, 0])
    axes[1, 0].set_title('ACF of Residuals')
    
    # Plot QQ plot
    stats.probplot(residuals, dist="norm", plot=axes[1, 1])
    axes[1, 1].set_title('Q-Q Plot')
    
    plt.tight_layout()
    plt.savefig('algorithms/time_series/exponential_smoothing/exponential_smoothing-diagnostics.png')
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

# Create and fit exponential smoothing model
# Hyperparameters explained:
# method='triple': Use triple exponential smoothing (Holt-Winters)
# alpha=0.3: Moderate weight for level component
# beta=0.1: Low weight for trend component
# gamma=0.1: Low weight for seasonal component
# seasonal_periods=12: Monthly data
# trend='add': Additive trend
# seasonal='add': Additive seasonality
# damped=True: Use damped trend to prevent explosive forecasts
# phi=0.9: Moderate damping parameter
fitted_values, residuals, final_level, final_trend, final_seasonal = fit_exponential_smoothing(
    train_data,
    method='triple',
    alpha=0.3,
    beta=0.1,
    gamma=0.1,
    seasonal_periods=12,
    trend='add',
    seasonal='add',
    damped=True,
    phi=0.9
)

# Forecast
forecast, lower, upper = forecast_exponential_smoothing(
    final_level,
    final_trend,
    final_seasonal,
    steps=len(test_data),
    method='triple',
    trend_type='add',
    seasonal_type='add',
    seasonal_periods=12,
    damped=True,
    phi=0.9
)

# Create directory if it doesn't exist
os.makedirs('algorithms/time_series/exponential_smoothing', exist_ok=True)

# Plot results using seaborn
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

plt.title('CO2 Levels Forecast')
plt.xlabel('Time')
plt.ylabel('CO2 Level')
plt.legend()

# Save plot to PNG file
plt.savefig('algorithms/time_series/exponential_smoothing/exponential_smoothing-forecast.png')
plt.close()

# Plot diagnostics
plot_diagnostics(residuals)