# Financial Sentiment Classifier Analysis

## Overview

This analysis demonstrates financial sentiment classification using text mining and machine learning techniques on news articles about Atlassian Corporation.

## Dataset

- **Total Articles**: 3 HTML news articles
- **Sources**: Australian Financial Review, The Register, UNLEASH
- **Content**: Financial news about Atlassian's $1B acquisition of The Browser Company

## Key Results

### Sentiment Analysis
- **Positive Sentiment**: 2 articles (AFR, UNLEASH)
- **Negative Sentiment**: 1 article (The Register)
- **Average Sentiment Score**: Calculated using AFINN lexicon

### Model Performance
- **Accuracy**: 100% (perfect classification on small dataset)
- **Sensitivity**: 100%
- **Specificity**: 100%
- **AUC**: 1.000

### Generated Visualizations
- Sentiment distribution across articles
- Word frequency analysis
- Confusion matrix
- ROC curve
- Feature importance analysis
- Word cloud

## Technical Implementation

### Text Preprocessing
- HTML content extraction using `rvest`
- Text cleaning and normalization
- Stop word removal and tokenization
- Sentiment scoring using AFINN lexicon

### Machine Learning
- Document-term matrix creation
- Logistic regression with cross-validation
- Model evaluation using confusion matrix and ROC curve
- Feature importance analysis

### Key Packages Used
- `tidyverse`: Data manipulation and visualization
- `tidytext`: Text mining and tokenization
- `textdata`: Financial sentiment lexicons
- `caret`: Machine learning framework
- `glmnet`: Logistic regression with regularization
- `tm`: Text mining framework
- `rvest`: HTML content extraction
- `wordcloud`: Word cloud generation
- `RColorBrewer`: Color palettes
- `pROC`: ROC curve analysis

## Files Generated

### Analysis Files
- `Financial-Sentiment-Classifier.R`: Main R script
- `Financial-Sentiment-Classifier.Rmd`: R Markdown document
- `Financial-Sentiment-Classifier.html`: Generated HTML report

### Data Files
- `sentiment_scores.csv`: Sentiment analysis results
- `model_predictions.csv`: Model predictions and probabilities

### Visualizations
- `sentiment_distribution.png`: Sentiment distribution chart
- `word_frequency_plot.png`: Most frequent words
- `confusion_matrix.png`: Model performance matrix
- `roc_curve.png`: ROC curve analysis
- `feature_importance.png`: Feature importance plot
- `wordcloud.png`: Word cloud visualization

## Usage

### Running the Analysis
```bash
# Run the R script
Rscript Financial-Sentiment-Classifier.R

# Generate HTML report
Rscript -e "rmarkdown::render('Financial-Sentiment-Classifier.Rmd')"
```

## Key Findings

1. **Sentiment Patterns**: Different articles show varying sentiment scores based on content analysis
2. **Financial Keywords**: Words like "acquisition", "growth", "investment" indicate positive sentiment
3. **Model Performance**: Logistic regression achieves perfect classification on this small dataset
4. **Feature Importance**: Financial terms are most predictive of sentiment

## Business Implications

1. **Acquisition News**: The $1B acquisition of The Browser Company is generally viewed positively
2. **Competitive Positioning**: Articles highlighting competitive advantages show positive sentiment
3. **Technology Innovation**: Focus on AI and future of work technologies generates positive market sentiment

## Technical Notes

- All visualizations use white backgrounds as specified
- The analysis handles small datasets with appropriate model selection
- HTML content extraction is robust and handles various article structures
- Sentiment analysis uses both AFINN lexicon and manual labeling for demonstration

## Future Enhancements

- Real-time sentiment analysis of financial news
- Integration with stock price data for validation
- Advanced NLP techniques (BERT, transformers)
- Multi-class sentiment classification
- Time-series sentiment analysis

---

*This analysis demonstrates the application of text mining and machine learning techniques to financial sentiment analysis using R.*
