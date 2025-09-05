# R for Data Science — Examples and Mini Projects

This repository is a curated collection of practical R examples for data science: data preparation, visualization, modeling (classification and regression), evaluation, and statistical simulations. Each topic is organized as a small, self-contained module with:

- R scripts (`.R`) for quick runs
- R Markdown (`.Rmd`) for reproducible reports
- Rendered Markdown/HTML outputs and figures under `images/`
- Small example datasets where applicable

Use this repo as a reference, a set of templates, or a starting point for your own work.

## Repository structure

- `00-00-ggplot2/` — ggplot2 basics for data visualization (script, doc, images).
- `00-01-corrplot/` — correlation matrix visualization with `corrplot` (script, doc, images).
- `00-02-ggplot-advanced/` — prompts/notes toward advanced ggplot use (work in progress).
- `02-01-Data-Cleansing/` — end-to-end data cleansing workflow: setup, loading, assessment, standardization, type conversion, missing values, outliers, text normalization, validation, duplicates, derived variables, visuals, and export. Includes modular scripts, docs, and images.
- `05-01-k-NN/` — k-NN classification (Pumpkin Seeds dataset) with code, results, images, and findings.
- `05-02-Linear-Discriminant-Analysis/` — Linear Discriminant Analysis (Pokemon dataset) with code, results, images, and findings.
- `05-03-Quadratic-Discriminant-Analysis/` — Quadratic Discriminant Analysis (Heart Disease dataset) with code, results, images, and findings.
- `05-04-Naive-Bayes-Classifier/` — Naive Bayes (Adult Income dataset) with code, results, images, dataset notes, and findings.
- `05-05-Triangulation-Methodology-with-k-NN/` — Triangulation via cross-validation and multiple metrics (accuracy, AUC, lift, confusion matrix) using k-NN (Pokemon dataset).
- `06-01-Linear-Regression/` — Linear Regression (Salary dataset) with code, results, and images.
- `06-02-Logistic-Regression/` — Logistic Regression (Weather dataset) with code, results, and images.
- `99.MultivariateNormal/` — Multivariate Normal simulation and density/probability demos (`MASS::mvrnorm`, `mvtnorm`).
- `images/` — shared/root-level images if needed.
- `Prompt.md`, `TODO.md` — planning notes and backlog for upcoming modules.

Each subfolder typically contains its own `README.md` with details and how to run.

## Getting started

- Install R (4.x recommended) and common packages used across modules (e.g., tidyverse, ggplot2, corrplot, MASS, mvtnorm, caret, e1071, pROC, rmarkdown, knitr). Individual READMEs list exact needs.
- Run scripts directly (e.g., `Rscript path/to/script.R`) or render Rmd files.
- See each module `README.md` for dataset paths and steps.

## Roadmap — more examples coming

I will keep adding more R code examples to cover:

- 01 Data preparation
- 03 CORRELATION ANALYSIS
- 04 k-Means
- 07 decision trees
- 08 neural network
- 09 text mining
- 10 Model Evaluation
- 11-01 Time Series Forecasting
- 11-02 Anomaly Detection
- 12 Ethics

Contributions and suggestions are welcome. Stay tuned for new modules and improvements.
