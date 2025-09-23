# Deep Learning with Keras in R — Credit Card Default Prediction

This module trains a simple feed-forward neural network to predict `default.payment.next.month` using the UCI Credit Card dataset.

## Files
- `Deep-Learning-with-Keras.R`: Script to run the full workflow and save figures to `images/`.
- `Deep-Learning-with-Keras.Rmd`: R Markdown that renders analysis, figures, and evaluation.
- `Deep-Learning-with-Keras.md`: Rendered markdown output from the Rmd.
- `images/`: Generated figures (white background PNGs) and metrics text.
- `CreditCard-Dataset-Deep-Learning-with-Keras.md`: Dataset description and column glossary.

## Data
- Input CSV: `CreditCard-Dataset/CreditCard.csv`

## Run
From this directory:

```bash
Rscript Deep-Learning-with-Keras.R
Rscript -e "rmarkdown::render('Deep-Learning-with-Keras.Rmd', output_format='md_document')"
```

If Keras/TensorFlow is not installed, the scripts attempt a non-interactive CPU installation.


