## Findings: Triangulation Methodology with k-NN (Pokemon Dataset)

### Overview
We evaluated a k-NN classifier using triangulation: multiple complementary metrics to validate performance. The goal was to avoid relying on a single score and instead cross-check results from different perspectives.

### Data and Target
- Features: `attack`, `defense`, `speed`
- Target: `Fast` (Yes/No), defined as `speed >= median(speed)`
- Notes: For reproducibility and robustness on small datasets, we use stratified splitting and guard metrics when classes are missing.

### Methodology
- Cross-Validation (CV): 2–5 folds (adaptive to data size), used to select `k`.
- Final Model: k-NN trained with standardized features; `k` chosen via CV.
- Evaluation Metrics:
  - Accuracy (overall performance)
  - ROC/AUC (discriminative power)
  - Lift Chart (business/targeting relevance)
  - Confusion Matrix (detailed error breakdown)

### Key Results (example run)
- Best k (CV): See `metrics_summary.txt` for the exact value used.
- Accuracy: Reported in `metrics_summary.txt` and console output during run.
- AUC: Reported in `metrics_summary.txt` (guarded when test set is single-class).
- Confusion Matrix: Saved to `confusion_matrix.txt` and visualized.

Because the demo can run on a very small dataset (fallback), metrics may be conservative (e.g., AUC ~ 0.50) and sensitive to splits. The triangulation still provides a stable framework to interpret performance.

### Artifacts
- Images (`05-05-Triangulation-Methodology-with-k-NN/images/`):
  - `01_cv_accuracy_vs_k.png` — CV Accuracy vs k (best k highlighted)
  - `02_roc_curve.png` — ROC curve with AUC label (if both classes present)
  - `03_lift_chart.png` — Lift by decile (if baseline > 0)
  - `04_confusion_matrix.png` — Confusion Matrix heatmap
- Text Summaries:
  - `metrics_summary.txt` — Accuracy, AUC, best k, baseline positive rate
  - `confusion_matrix.txt` — Full confusion matrix with statistics
- Notebook and Report:
  - `k-NN.Rmd` → `k-NN.md` (rendered GitHub Markdown report)

### Interpretation Guidance
- Use CV Accuracy vs k to ensure the chosen `k` is stable and not overfitted.
- Accuracy provides a quick check; always corroborate with the confusion matrix to understand error types.
- ROC/AUC reveals ranking/discrimination independent of thresholds.
- Lift highlights practical value for targeting the top-scored segments compared to random selection.

### Conclusion
Triangulation—combining CV, Accuracy, ROC/AUC, Lift, and Confusion Matrix—offers a robust, multi-angle assessment of the k-NN model. Even with tiny samples, this approach reduces blind spots and supports more trustworthy decisions.
