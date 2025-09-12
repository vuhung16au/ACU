# Text Mining (R) — 2016 U.S. Presidential Race

This module demonstrates basic text mining in R, following the structure of the reference notebook.

## Inputs
- Dataset folder: `09-01-Text-Mining/2016-US-Presidential-Race-Dataset/`

## Outputs
- `09-01-Text-Mining/Text-Mining.R`
- `09-01-Text-Mining/Text-Mining.Rmd` → renders to `Text-Mining.html` and `Text-Mining.md`
- `/images/` (white-background PNGs):
  - `top_tokens_per_candidate.png`
  - `top_bigrams.png`
  - `top_trigrams.png`
  - `classification_metrics.csv`
- Dataset description: `09-01-Text-Mining/2016-US-Presidential-Race-Dataset-Text-Mining.md`

## How to Run
From the repo root or the `09-01-Text-Mining/` folder:

1) Generate images with the R script
```bash
Rscript 09-01-Text-Mining/Text-Mining.R
```

2) Render the R Markdown to HTML and Markdown
```bash
R -e "rmarkdown::render('09-01-Text-Mining/Text-Mining.Rmd', output_format = c('html_document','md_document'))"
```

If any packages are missing, they will be installed automatically from CRAN.

## What’s Demonstrated
- What text mining is, where it’s used, benefits
- Formats: `.txt`, `.csv` columns, etc.
- Operators: tokenization, stopwords, case normalization, min-length filtering
- Stemming and lemmatization
- N-grams (bigrams, trigrams)
- Token replacement (substitution)
- Document-term matrix and a simple Naive Bayes classification
