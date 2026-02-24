# =============================================================================
# Store Sales - Time Series Forecasting
# =============================================================================
# Aligns with Kaggle Learn Time Series (https://www.kaggle.com/learn/time-series):
#   - Linear Regression With Time Series (features as predictors)
#   - Trend (trend_idx, rolling stats)
#   - Seasonality (Fourier terms, day_of_week, month)
#   - Time Series as Features (lags, rolling mean/std)
#   - Hybrid Models (classifier + regressor combined)
#   - Forecasting With Machine Learning (XGBoost)
# =============================================================================

library(tidyverse)
library(data.table)
library(lubridate)
library(xgboost)
library(zoo)
library(future.apply)

set.seed(379)
setDTthreads(4)

fprint <- function(text) {
  current_time_ms <- format(now(tzone = "America/New_York"), "%H:%M:%OS3")
  print(paste0("[", current_time_ms, "] ", text))
  flush.console()
}

# -----------------------------------------------------------------------------
# Data Loading
# -----------------------------------------------------------------------------
fprint("Loading data")

data_dir <- "Store-Dataset"

load_data <- function(file_name, col_types, use_cols = NULL) {
  path <- file.path(data_dir, file_name)
  if (!file.exists(path)) stop(paste("File not found:", path))
  if (!is.null(use_cols)) {
    df <- fread(path, select = use_cols, colClasses = col_types)
  } else {
    df <- fread(path, colClasses = col_types)
  }
  if ("date" %in% names(df)) df[, date := as.Date(date, format = "%Y-%m-%d")]
  return(df)
}

holidays_events <- load_data(
  "holidays_events.csv",
  c(date = "character", type = "factor", locale = "factor",
    locale_name = "factor", description = "factor", transferred = "logical")
)
setkey(holidays_events, date)

store_sales <- load_data(
  "train.csv",
  c(store_nbr = "factor", family = "factor", date = "character",
    sales = "numeric", onpromotion = "integer"),
  use_cols = c("store_nbr", "family", "date", "sales", "onpromotion")
)
setkey(store_sales, store_nbr, family, date)

df_test <- load_data(
  "test.csv",
  c(id = "integer", date = "character", store_nbr = "factor",
    family = "factor", onpromotion = "integer")
)
setkey(df_test, store_nbr, family, date)

oil_prices <- load_data("oil.csv", c(date = "character", dcoilwtico = "numeric"))
setkey(oil_prices, date)

fprint("Data ready")

# -----------------------------------------------------------------------------
# Feature Generation (Trend, Seasonality, Time Series as Features)
# -----------------------------------------------------------------------------
use_feature_cache <- TRUE
feature_cache_file <- "full_data_grid_features_043.RData"

if (use_feature_cache && file.exists(feature_cache_file)) {
  fprint("Loading features from cache...")
  load(feature_cache_file)
} else {
  fprint("Starting feature generation process...")

  all_store_nbrs <- unique(store_sales$store_nbr)
  all_families <- unique(store_sales$family)
  min_date_overall <- min(min(store_sales$date), min(df_test$date))
  max_date_overall <- max(max(store_sales$date), max(df_test$date))
  all_dates_combined <- seq(min_date_overall, max_date_overall, by = "day")

  fprint("Creating full data grid...")
  full_data_grid <- CJ(date = all_dates_combined, store_nbr = all_store_nbrs, family = all_families)

  fprint("Merging sales and promotion data...")
  full_data_grid <- merge(full_data_grid, store_sales, by = c("date", "store_nbr", "family"), all.x = TRUE)
  full_data_grid <- merge(full_data_grid, df_test[, .(id, date, store_nbr, family, onpromotion_test = onpromotion)],
    by = c("date", "store_nbr", "family"), all.x = TRUE)
  full_data_grid[, onpromotion := fifelse(!is.na(onpromotion), onpromotion, onpromotion_test)]
  full_data_grid[, onpromotion_test := NULL]
  full_data_grid[is.na(onpromotion), onpromotion := 0]

  fprint("Merging oil price data...")
  full_data_grid <- merge(full_data_grid, oil_prices, by = "date", all.x = TRUE)
  full_data_grid[, dcoilwtico := nafill(dcoilwtico, type = "locf")]
  mean_dcoil <- full_data_grid[!is.na(dcoilwtico), mean(dcoilwtico)]
  full_data_grid[is.na(dcoilwtico), dcoilwtico := mean_dcoil]

  # --- Trend & Seasonality: date-based and Fourier features ---
  fprint("Generating date, time, and holiday features...")
  df_features <- data.table(date = all_dates_combined)
  df_features[, `:=`(
    trend_idx = 1:.N,
    fourier_sin_y1 = sin(2 * pi * 1 * (1:.N) / 365.25),
    fourier_cos_y1 = cos(2 * pi * 1 * (1:.N) / 365.25),
    fourier_sin_y2 = sin(2 * pi * 2 * (1:.N) / 365.25),
    fourier_cos_y2 = cos(2 * pi * 2 * (1:.N) / 365.25),
    fourier_sin_y3 = sin(2 * pi * 3 * (1:.N) / 365.25),
    fourier_cos_y3 = cos(2 * pi * 3 * (1:.N) / 365.25),
    fourier_sin_w1 = sin(2 * pi * 1 * (1:.N) / 7),
    day_of_week = factor((wday(date) + 5) %% 7 + 1),
    month_of_year = factor(month(date)),
    day_of_year = factor(yday(date))
  )]
  holidays_features <- as.data.table(holidays_events)[, .(date, type, locale, transferred)]
  holidays_features[, is_national_holiday := as.numeric(type == "Holiday" & locale == "National")]
  holidays_features[, is_regional_holiday := as.numeric(type == "Holiday" & locale == "Regional")]
  holidays_features[, is_local_holiday := as.numeric(type == "Holiday" & locale == "Local")]
  holidays_features[, is_transfer := as.numeric(transferred)]
  holidays_features <- holidays_features[, lapply(.SD, max), by = date, .SDcols = patterns("^is_")]
  df_features <- merge(df_features, holidays_features, by = "date", all.x = TRUE)
  for (col in c("is_national_holiday", "is_regional_holiday", "is_local_holiday", "is_transfer")) {
    set(df_features, i = which(is.na(df_features[[col]])), j = col, value = 0)
  }
  full_data_grid <- merge(full_data_grid, df_features, by = "date", all.x = TRUE)

  fprint("Calculating target encoding...")
  split_date_for_encoding <- as.Date("2017-07-01")
  train_data_for_encoding <- full_data_grid[!is.na(sales) & date < split_date_for_encoding]
  enc_store_family_map <- train_data_for_encoding[, .(enc_store_nbr_family = mean(sales, na.rm = TRUE)), by = c("store_nbr", "family")]
  full_data_grid <- merge(full_data_grid, enc_store_family_map, by = c("store_nbr", "family"), all.x = TRUE)
  global_mean_sales <- mean(train_data_for_encoding$sales, na.rm = TRUE)
  full_data_grid[is.na(enc_store_nbr_family), enc_store_nbr_family := global_mean_sales]

  # --- Time Series as Features: lags and rolling stats per store-family ---
  fprint("Computing time series features in parallel...")
  setorder(full_data_grid, store_nbr, family, date)
  future::plan("multisession", workers = 4)
  group_list <- split(full_data_grid, by = c("store_nbr", "family"), keep.by = TRUE)

  compute_ts_features <- function(dt) {
    dt[, `:=`(
      dcoilwtico_lag_3 = shift(dcoilwtico, 3),
      dcoilwtico_lag_7 = shift(dcoilwtico, 7),
      dcoilwtico_mean_14d = frollmean(dcoilwtico, 14, align = "right"),
      dcoilwtico_median_28d = frollapply(dcoilwtico, 28, median, align = "right"),
      onpromotion_lag_1 = shift(onpromotion, 1),
      onpromotion_lag_3 = shift(onpromotion, 3),
      onpromotion_lag_7 = shift(onpromotion, 7),
      onpromotion_sum_7d = frollsum(onpromotion, 7, align = "right"),
      onpromotion_mean_14d = frollmean(onpromotion, 14, align = "right"),
      sales_lag_1 = shift(sales, 1),
      sales_lag_7 = shift(sales, 7),
      sales_lag_14 = shift(sales, 14),
      sales_lag_28 = shift(sales, 28),
      sales_mean_28d = frollmean(sales, 28, align = "right"),
      sales_std_28d = frollapply(sales, 28, sd, align = "right"),
      rolling_min_14d = frollapply(sales, 14, min, align = "right"),
      rolling_max_14d = frollapply(sales, 14, max, align = "right")
    )]
    return(dt)
  }

  group_list <- future_lapply(group_list, compute_ts_features)
  full_data_grid <- rbindlist(group_list)

  fprint("Imputing remaining NAs...")
  cols_to_impute <- names(full_data_grid)[sapply(full_data_grid, is.numeric)]
  cols_to_impute <- cols_to_impute[!cols_to_impute %in% c("sales", "id")]
  for (col in cols_to_impute) {
    if (any(is.na(full_data_grid[[col]]))) {
      full_data_grid[, (col) := nafill(get(col), type = "locf"), by = .(store_nbr, family)]
      full_data_grid[is.na(get(col)), (col) := 0]
    }
  }

  fprint("Converting categorical features to integers...")
  factor_cols <- c("store_nbr", "family", "day_of_week", "month_of_year", "day_of_year")
  for (col in factor_cols) {
    full_data_grid[, (col) := as.integer(get(col))]
  }

  fprint("Features generation complete!")
  save(full_data_grid, file = feature_cache_file)
  fprint("Features saved to cache.")
}

# -----------------------------------------------------------------------------
# Data Preparation for Modeling
# -----------------------------------------------------------------------------
fprint("Preparing data...")
setDT(full_data_grid)

full_data_grid[, sale_happened := as.integer(sales > 0)]

val_period_start_date <- as.IDate("2016-08-16")
val_period_end_date <- as.IDate("2016-08-31")
train_start_date <- as.IDate("2015-01-01")

all_potential_features <- c(
  "store_nbr", "family", "trend_idx",
  "fourier_sin_y1", "fourier_cos_y1", "fourier_sin_y2", "fourier_cos_y2", "fourier_sin_y3", "fourier_cos_y3",
  "fourier_sin_w1", "day_of_week", "month_of_year", "day_of_year", "is_national_holiday", "is_regional_holiday",
  "is_local_holiday", "is_transfer", "onpromotion", "onpromotion_lag_1", "onpromotion_lag_3", "onpromotion_lag_7",
  "onpromotion_sum_7d", "onpromotion_mean_14d", "dcoilwtico", "dcoilwtico_lag_3", "dcoilwtico_lag_7",
  "dcoilwtico_mean_14d", "dcoilwtico_median_28d", "sales_lag_1", "sales_lag_7", "sales_lag_14", "sales_lag_28",
  "sales_std_28d", "enc_store_nbr_family", "sales_mean_28d", "rolling_min_14d", "rolling_max_14d"
)
common_features <- intersect(all_potential_features, colnames(full_data_grid))
fprint(paste("Using", length(common_features), "features for all models."))

train_all_dates <- full_data_grid[!is.na(sales)]
test_df_full <- full_data_grid[is.na(sales)]

val_subset <- train_all_dates[date >= val_period_start_date & date <= val_period_end_date]
train_subset <- train_all_dates[!(date >= val_period_start_date & date <= val_period_end_date) & date >= train_start_date]

setorder(test_df_full, id)
fprint("Data preparation complete.")

# -----------------------------------------------------------------------------
# Hybrid Model: (1) Classifier + (2) Regressor (Forecasting With ML)
# -----------------------------------------------------------------------------
fprint("Training Classifier (Model 1)...")

dtrain_clf <- xgb.DMatrix(data = as.matrix(train_subset[, .SD, .SDcols = common_features]), label = train_subset$sale_happened)
dval_clf <- xgb.DMatrix(data = as.matrix(val_subset[, .SD, .SDcols = common_features]), label = val_subset$sale_happened)
watchlist_clf <- list(train = dtrain_clf, val = dval_clf)

clf_params <- list(
  objective = "binary:logistic",
  eval_metric = "logloss",
  eta = 0.04, max_depth = 8, subsample = 0.8,
  colsample_bytree = 0.8, min_child_weight = 5, lambda = 3
)
nrounds <- 5000
early_stop <- 20

classifier_model <- xgb.train(
  params = clf_params, data = dtrain_clf, nrounds = nrounds,
  watchlist = watchlist_clf, early_stopping_rounds = early_stop,
  print_every_n = 25, verbose = 1
)
fprint("Classifier training complete.")

fprint("Training Regressor (Model 2)...")

dtrain_reg <- xgb.DMatrix(data = as.matrix(train_subset[, .SD, .SDcols = common_features]), label = log(train_subset$sales + 1))
dval_reg <- xgb.DMatrix(data = as.matrix(val_subset[, .SD, .SDcols = common_features]), label = log(val_subset$sales + 1))
watchlist_reg <- list(train = dtrain_reg, val = dval_reg)

reg_params <- list(
  objective = "reg:squarederror", eval_metric = "rmse",
  eta = 0.04, max_depth = 10, subsample = 0.8,
  colsample_bytree = 0.8, min_child_weight = 5, lambda = 3
)

regressor_model <- xgb.train(
  params = reg_params, data = dtrain_reg, nrounds = nrounds,
  watchlist = watchlist_reg, early_stopping_rounds = early_stop,
  print_every_n = 25, verbose = 1
)
fprint("Regressor training complete.")

# -----------------------------------------------------------------------------
# Generate & Combine Predictions (Hybrid: prob * amount)
# -----------------------------------------------------------------------------
fprint("Generating and combining predictions...")

dtest <- xgb.DMatrix(data = as.matrix(test_df_full[!is.na(id), .SD, .SDcols = common_features]))

prob_of_sale <- predict(classifier_model, dtest)
log_amount_pred <- predict(regressor_model, dtest)
amount_pred <- exp(log_amount_pred) - 1
final_preds <- prob_of_sale * amount_pred
final_preds <- pmax(0, final_preds)

submission <- data.table(id = test_df_full[!is.na(id)]$id, sales = final_preds)
fwrite(submission, "submission.csv")

fprint("submission.csv written successfully with two-stage model predictions.")
