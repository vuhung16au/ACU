## Prophet Time Series Analysis

## 1. Import Required Libraries
import numpy as np
import pandas as pd
import seaborn as sns
from typing import List, Dict, Optional, Union, Tuple
from scipy import stats
import matplotlib.pyplot as plt
from sklearn.linear_model import LinearRegression
from sklearn.preprocessing import StandardScaler

# Set random seed for reproducibility
np.random.seed(2220)

## 2. Helper Functions

def get_changepoints(
    dates: pd.Series,
    changepoint_range: float = 0.8,
    n_changepoints: int = 25
) -> np.ndarray:
    """
    Get changepoint dates.
    
    Args:
        dates: Series of dates
        changepoint_range: Proportion of history to consider for changepoints
        n_changepoints: Number of changepoints
        
    Returns:
        Array of changepoint dates
    """
    n = len(dates)
    changepoint_range = int(n * changepoint_range)
    changepoint_dates = dates.iloc[:changepoint_range]
    
    if n_changepoints > len(changepoint_dates):
        n_changepoints = len(changepoint_dates)
    
    indices = np.linspace(0, len(changepoint_dates) - 1, n_changepoints, dtype=int)
    return changepoint_dates.iloc[indices].values

def get_trend_features(
    dates: pd.Series,
    changepoint_range: float = 0.8,
    n_changepoints: int = 25
) -> Tuple[np.ndarray, np.ndarray]:
    """
    Get trend features.
    
    Args:
        dates: Series of dates
        changepoint_range: Proportion of history to consider for changepoints
        n_changepoints: Number of changepoints
        
    Returns:
        Tuple of (trend features, changepoints)
    """
    n = len(dates)
    t = np.arange(n)
    
    # Get changepoints
    changepoints = get_changepoints(dates, changepoint_range, n_changepoints)
    changepoint_indices = np.array([np.where(dates == cp)[0][0] for cp in changepoints])
    
    # Create trend features
    A = np.zeros((n, len(changepoint_indices)))
    for i, idx in enumerate(changepoint_indices):
        A[idx:, i] = t[idx:] - t[idx]
    
    return A, changepoints

def get_seasonal_features(
    dates: pd.Series,
    yearly_seasonality: bool = True,
    weekly_seasonality: bool = True,
    daily_seasonality: bool = False
) -> Dict[str, np.ndarray]:
    """
    Get seasonal features.
    
    Args:
        dates: Series of dates
        yearly_seasonality: Whether to include yearly seasonality
        weekly_seasonality: Whether to include weekly seasonality
        daily_seasonality: Whether to include daily seasonality
        
    Returns:
        Dictionary of seasonal features
    """
    features = {}
    
    if yearly_seasonality:
        # Yearly seasonality (Fourier series)
        year = dates.dt.year + dates.dt.dayofyear / 365.25
        for i in range(1, 11):  # 10 Fourier terms
            features[f'yearly_sin_{i}'] = np.sin(2 * np.pi * i * year)
            features[f'yearly_cos_{i}'] = np.cos(2 * np.pi * i * year)
    
    if weekly_seasonality:
        # Weekly seasonality (dummy variables)
        for i in range(6):  # 6 dummy variables for 7 days
            features[f'weekly_{i}'] = (dates.dt.dayofweek == i).astype(float)
    
    if daily_seasonality:
        # Daily seasonality (Fourier series)
        hour = dates.dt.hour + dates.dt.minute / 60
        for i in range(1, 4):  # 3 Fourier terms
            features[f'daily_sin_{i}'] = np.sin(2 * np.pi * i * hour / 24)
            features[f'daily_cos_{i}'] = np.cos(2 * np.pi * i * hour / 24)
    
    return features

def get_holiday_features(
    dates: pd.Series,
    holidays: Optional[pd.DataFrame] = None
) -> np.ndarray:
    """
    Get holiday features.
    
    Args:
        dates: Series of dates
        holidays: DataFrame with holiday information
        
    Returns:
        Array of holiday features
    """
    if holidays is None:
        return np.zeros((len(dates), 0))
    
    # Create holiday features
    holiday_features = np.zeros((len(dates), len(holidays)))
    for i, (_, holiday) in enumerate(holidays.iterrows()):
        mask = (dates >= holiday['ds']) & (dates <= holiday['ds'] + pd.Timedelta(days=holiday.get('days', 0)))
        holiday_features[mask, i] = 1
    
    return holiday_features

## 3. Prophet Model Implementation

def fit_prophet(
    df: pd.DataFrame,
    growth: str = 'linear',  # Growth type ('linear' or 'logistic')
    changepoint_prior_scale: float = 0.05,  # Flexibility of trend changes
    seasonality_prior_scale: float = 10.0,  # Flexibility of seasonal components
    holidays_prior_scale: float = 10.0,  # Flexibility of holiday effects
    seasonality_mode: str = 'additive',  # Seasonality type ('additive' or 'multiplicative')
    changepoint_range: float = 0.8,  # Proportion of history to consider for changepoints
    n_changepoints: int = 25,  # Number of changepoints
    yearly_seasonality: bool = True,  # Whether to include yearly seasonality
    weekly_seasonality: bool = True,  # Whether to include weekly seasonality
    daily_seasonality: bool = False,  # Whether to include daily seasonality
    holidays: Optional[pd.DataFrame] = None  # DataFrame with holiday information
) -> Tuple[LinearRegression, StandardScaler, Dict[str, np.ndarray], np.ndarray, np.ndarray]:
    """
    Fit Prophet model.
    
    Hyperparameters explained:
    - growth: Type of growth model ('linear' for linear growth, 'logistic' for logistic growth)
    - changepoint_prior_scale: Controls flexibility of trend changes (higher = more flexible)
    - seasonality_prior_scale: Controls flexibility of seasonal components (higher = more flexible)
    - holidays_prior_scale: Controls flexibility of holiday effects (higher = more flexible)
    - seasonality_mode: Type of seasonality ('additive' for constant amplitude, 'multiplicative' for varying amplitude)
    - changepoint_range: Proportion of history to consider for changepoints (0 to 1)
    - n_changepoints: Number of potential changepoints to consider
    - yearly_seasonality: Whether to include yearly seasonality
    - weekly_seasonality: Whether to include weekly seasonality
    - daily_seasonality: Whether to include daily seasonality
    - holidays: DataFrame with holiday information (columns: 'ds' for date, 'holiday' for name, optional 'days' for duration)
    
    Args:
        df: DataFrame with 'ds' (dates) and 'y' (values) columns
        growth: Growth type
        changepoint_prior_scale: Flexibility of trend changes
        seasonality_prior_scale: Flexibility of seasonal components
        holidays_prior_scale: Flexibility of holiday effects
        seasonality_mode: Seasonality type
        changepoint_range: Proportion of history to consider for changepoints
        n_changepoints: Number of changepoints
        yearly_seasonality: Whether to include yearly seasonality
        weekly_seasonality: Whether to include weekly seasonality
        daily_seasonality: Whether to include daily seasonality
        holidays: DataFrame with holiday information
        
    Returns:
        Tuple of (fitted model, scaler, seasonal features, changepoints, trend features)
    """
    # Extract data
    dates = pd.to_datetime(df['ds'])
    y = df['y'].values
    
    # Get features
    trend_features, changepoints = get_trend_features(dates, changepoint_range, n_changepoints)
    seasonal_features = get_seasonal_features(
        dates, yearly_seasonality, weekly_seasonality, daily_seasonality
    )
    holiday_features = get_holiday_features(dates, holidays)
    
    # Combine features
    X = np.column_stack([
        trend_features,
        *seasonal_features.values(),
        holiday_features
    ])
    
    # Scale features
    scaler = StandardScaler()
    X = scaler.fit_transform(X)
    
    # Fit model
    model = LinearRegression()
    model.fit(X, y)
    
    return model, scaler, seasonal_features, changepoints, trend_features

def predict_prophet(
    df: pd.DataFrame,
    model: LinearRegression,
    scaler: StandardScaler,
    seasonal_features: Dict[str, np.ndarray],
    changepoints: np.ndarray,
    trend_features: np.ndarray,
    holidays: Optional[pd.DataFrame] = None
) -> pd.DataFrame:
    """
    Make predictions using fitted Prophet model.
    
    Args:
        df: DataFrame with 'ds' (dates) column
        model: Fitted Prophet model
        scaler: Fitted scaler
        seasonal_features: Seasonal features
        changepoints: Changepoints
        trend_features: Trend features
        holidays: DataFrame with holiday information
        
    Returns:
        DataFrame with predictions
    """
    # Extract dates
    dates = pd.to_datetime(df['ds'])
    
    # Get features
    trend_features, _ = get_trend_features(dates)
    seasonal_features = get_seasonal_features(dates)
    holiday_features = get_holiday_features(dates, holidays)
    
    # Combine features
    X = np.column_stack([
        trend_features,
        *seasonal_features.values(),
        holiday_features
    ])
    
    # Scale features
    X = scaler.transform(X)
    
    # Make predictions
    trend = X[:, :len(trend_features)] @ model.coef_[:len(trend_features)]
    seasonal = np.zeros(len(dates))
    for i, (name, _) in enumerate(seasonal_features.items()):
        seasonal += X[:, len(trend_features) + i * 2:len(trend_features) + (i + 1) * 2] @ \
                   model.coef_[len(trend_features) + i * 2:len(trend_features) + (i + 1) * 2]
    holiday = X[:, -len(holiday_features[0]):] @ model.coef_[-len(holiday_features[0]):]
    
    # Combine components
    yhat = trend + seasonal + holiday
    
    # Create output DataFrame
    result = df.copy()
    result['yhat'] = yhat
    result['trend'] = trend
    result['seasonal'] = seasonal
    result['holiday'] = holiday
    
    return result

def plot_prophet_components(
    df: pd.DataFrame,
    forecast: pd.DataFrame
) -> None:
    """
    Plot Prophet model components using seaborn and save to PNG file.
    
    Args:
        df: Original DataFrame with 'ds' and 'y' columns
        forecast: DataFrame with predictions and components
    """
    # Create directory if it doesn't exist
    import os
    os.makedirs('algorithms/time_series/prophet', exist_ok=True)
    
    # Set seaborn style
    sns.set_style("whitegrid")
    
    # Create figure with subplots
    fig, axes = plt.subplots(4, 1, figsize=(12, 12))
    
    # Plot actual vs predicted
    sns.lineplot(data=df, x='ds', y='y', label='Actual', ax=axes[0])
    sns.lineplot(data=forecast, x='ds', y='yhat', label='Predicted', ax=axes[0])
    axes[0].set_title('Actual vs Predicted')
    axes[0].set_xlabel('Date')
    axes[0].set_ylabel('Value')
    
    # Plot trend
    sns.lineplot(data=forecast, x='ds', y='trend', ax=axes[1])
    axes[1].set_title('Trend')
    axes[1].set_xlabel('Date')
    axes[1].set_ylabel('Trend')
    
    # Plot seasonality
    sns.lineplot(data=forecast, x='ds', y='seasonal', ax=axes[2])
    axes[2].set_title('Seasonality')
    axes[2].set_xlabel('Date')
    axes[2].set_ylabel('Seasonal')
    
    # Plot holidays
    sns.lineplot(data=forecast, x='ds', y='holiday', ax=axes[3])
    axes[3].set_title('Holiday Effects')
    axes[3].set_xlabel('Date')
    axes[3].set_ylabel('Holiday')
    
    plt.tight_layout()
    
    # Save plot to PNG file
    plt.savefig('algorithms/time_series/prophet/prophet-components.png')
    plt.close()

## 4. Example Usage with CO2 Dataset

# Load CO2 dataset
from statsmodels.datasets import co2
data = co2.load().data
data = data['co2'].resample('ME').mean().ffill()

# Prepare data
df = pd.DataFrame({
    'ds': data.index,
    'y': data.values
})

# Split into train and test
train_size = int(len(df) * 0.8)
train_df = df[:train_size]
test_df = df[train_size:]

# Create and fit Prophet model
# Hyperparameters explained:
# growth='linear': Use linear growth model
# changepoint_prior_scale=0.05: Moderate flexibility for trend changes
# seasonality_prior_scale=10.0: High flexibility for seasonal components
# holidays_prior_scale=10.0: High flexibility for holiday effects
# seasonality_mode='additive': Use additive seasonality
# changepoint_range=0.8: Consider 80% of history for changepoints
# n_changepoints=25: Consider 25 potential changepoints
# yearly_seasonality=True: Include yearly seasonality
# weekly_seasonality=True: Include weekly seasonality
# daily_seasonality=False: Don't include daily seasonality
model, scaler, seasonal_features, changepoints, trend_features = fit_prophet(
    train_df,
    growth='linear',
    changepoint_prior_scale=0.05,
    seasonality_prior_scale=10.0,
    holidays_prior_scale=10.0,
    seasonality_mode='additive',
    changepoint_range=0.8,
    n_changepoints=25,
    yearly_seasonality=True,
    weekly_seasonality=True,
    daily_seasonality=False
)

# Make predictions
forecast = predict_prophet(
    test_df,
    model,
    scaler,
    seasonal_features,
    changepoints,
    trend_features
)

# Plot results
plot_prophet_components(df, forecast)