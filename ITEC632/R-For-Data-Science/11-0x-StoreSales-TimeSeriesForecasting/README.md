# Store Sales – Time Series Forecasting

Time series forecasting project for the [Kaggle Store Sales - Time Series Forecasting](https://www.kaggle.com/competitions/store-sales-time-series-forecasting/overview) setting, using R with **XGBoost** in a two-stage (classifier + regressor) pipeline. The implementation follows concepts from [Kaggle Learn – Time Series](https://www.kaggle.com/learn/time-series).

TODO: This project is a work in progress. 

## Topics covered

| Topic | Where it appears in this project |
|--------|-----------------------------------|
| **Linear Regression With Time Series** | Features (trend, seasonality, lags) are used as predictors in a linear way inside tree-based models; the same feature set could be used in a linear model. |
| **Trend** | `trend_idx` (time index), rolling means/medians over 14d and 28d for oil and sales. |
| **Seasonality** | Fourier terms (yearly and weekly), `day_of_week`, `month_of_year`, `day_of_year`, and holiday flags. |
| **Time Series as Features** | Lags (1, 3, 7, 14, 28) and rolling stats (mean, std, min, max) for sales, oil, and promotions per store–family. |
| **Hybrid Models** | Two-stage model: (1) binary classifier for “sale happened” and (2) regressor for log(sales+1); final prediction = P(sale) × predicted amount. |
| **Forecasting With Machine Learning** | XGBoost for both the classifier and the regressor, with validation and early stopping. |

## Setup

1. **R** (e.g. 4.x) and **RStudio** or any R IDE/CLI.

2. **Install R dependencies** (run once):

   ```r
   source("install_dependencies.R")
   ```

   Or install manually:

   ```r
   install.packages(c("tidyverse", "data.table", "lubridate", "xgboost", "zoo", "future.apply"))
   ```

3. **Data**: Use the `Store-Dataset/` folder with the competition CSVs:
   - `train.csv`, `test.csv`, `holidays_events.csv`, `oil.csv` (and optionally `stores.csv`, `transactions.csv`).
   - Get data from [Kaggle Store Sales - Time Series Forecasting](https://www.kaggle.com/competitions/store-sales-time-series-forecasting/data) and place the files in `Store-Dataset/`.

## How to run

From the project root (directory containing `store_sales_forecast.R` and `Store-Dataset/`):

```bash
Rscript store_sales_forecast.R
```

Or in R:

```r
setwd("/path/to/11-0x-StoreSales-TimeSeriesForecasting")
source("store_sales_forecast.R")
```

- First run builds the feature grid and can take a while; it is cached in `full_data_grid_features_043.RData` for later runs.
- Output: `submission.csv` (columns `id`, `sales`) ready for upload to Kaggle.

## Project layout

- `store_sales_forecast.R` – Main script: load data, build features, train classifier + regressor, predict, write submission.
- `install_dependencies.R` – Installs required R packages.
- `Store-Dataset/` – Competition data (train, test, oil, holidays, etc.).
- `sample-code-in-R.txt` – Reference R code and notes.
- `TODO.md` – Checklist of Kaggle Learn time series topics (implemented as in the table above).

## References

- [Kaggle Learn – Time Series](https://www.kaggle.com/learn/time-series)
- [Store Sales - Time Series Forecasting](https://www.kaggle.com/competitions/store-sales-time-series-forecasting/overview)
- [Reference notebook (Doron Fingold)](https://www.kaggle.com/code/doronfingold/store-sales)
