# Triangulation Methodology with k-NN (Pokemon Dataset)

## Overview
This example demonstrates triangulation of model quality using k-NN on a simple Pokemon dataset. We combine multiple complementary metrics to validate results:

- Cross-Validation (select best k)
- Accuracy (Overall Performance)
- AUC and ROC Curve (Discriminative Power)
- Lift Chart (Business Relevance)
- Confusion Matrix (Detailed Breakdown)

Outputs (images, metrics) are saved under `05-05-Triangulation-Methodology-with-k-NN/`.

## Files
- `k-NN.R` — Script that runs CV, trains k-NN, evaluates metrics, and saves images/text outputs
- `k-NN.Rmd` — Notebook (GitHub Markdown) version; rendering produces `k-NN.md`
- `k-NN.md` — Rendered Markdown report with figures
- `Pokemon-Dataset/pokemon.csv` — Dataset (copied or minimal fallback)
- `images/` — Generated figures (CV vs k, ROC, Lift, Confusion Matrix)

## Dataset
The dataset contains minimal numeric features:
- `attack`, `defense`, `speed`, plus a derived binary target: `Fast = speed >= median(speed)`

If the original dataset is not found, a small fallback CSV is created to keep the demo runnable.

## How to Run

### 1) Run the R script
This will generate all images and metric summaries.

```r
# From the repo root
Rscript "05-05-Triangulation-Methodology-with-k-NN/k-NN.R"
```

Artifacts produced:
- `images/01_cv_accuracy_vs_k.png`
- `images/02_roc_curve.png` (if both classes in test)
- `images/03_lift_chart.png` (if baseline > 0)
- `images/04_confusion_matrix.png`
- `confusion_matrix.txt`, `metrics_summary.txt`

### 2) Render the Rmd to Markdown
This produces `k-NN.md` and an HTML preview.

```r
Rscript -e "rmarkdown::render('05-05-Triangulation-Methodology-with-k-NN/k-NN.Rmd', output_dir='05-05-Triangulation-Methodology-with-k-NN')"
```

## Notes on Robustness
- The code handles small datasets by:
  - Capping `k` by fold training size
  - Ensuring a minimal, class-representative test set when possible
  - Guarding ROC/AUC and Lift chart when data are insufficient
- All plots are saved with white backgrounds for easy embedding.

## Example Metrics
- Best k from CV
- Test Accuracy
- Test AUC (if available)
- Confusion Matrix with per-class stats

## Requirements
R packages: `readr`, `dplyr`, `ggplot2`, `class`, `caret`, `pROC`, `rmarkdown`

Install if needed:

```r
install.packages(c('readr','dplyr','ggplot2','class','caret','pROC','rmarkdown'), repos='https://cloud.r-project.org')
```

