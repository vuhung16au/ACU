# Sentiment Analysis Results Summary

## Project Overview
**Product**: Natural Cassandra Bed with USB Port and Drawers (OIKT2041)  
**Source**: Temple & Webster customer reviews  
**Analysis Date**: September 13, 2025  
**Total Reviews Analyzed**: 164

## Key Findings

### 1. Overall Sentiment Distribution
- **Positive Reviews**: 120 (73.2%)
- **Neutral Reviews**: 37 (22.6%)
- **Negative Reviews**: 7 (4.3%)

### 2. Rating Analysis
- **Average Rating**: 4.02 out of 5
- **Rating Distribution**: Predominantly 4-star ratings
- **Customer Satisfaction**: High overall satisfaction

### 3. Geographic Distribution
- Reviews analyzed from various Australian states
- Location data parsing needs improvement for detailed geographic analysis

### 4. Topic Analysis
- **5 Main Topics Identified** through LDA topic modelling
- Topics cover different aspects of customer feedback
- Topic distribution shows balanced coverage across themes

### 5. Word Frequency Analysis
**Top 10 Most Frequent Words:**
1. bed (125 occurrences)
2. easy (49 occurrences)
3. assemble (30 occurrences)
4. love (24 occurrences)
5. quality (23 occurrences)
6. drawers (22 occurrences)
7. happy (22 occurrences)
8. storage (21 occurrences)
9. usb (19 occurrences)
10. frame (17 occurrences)

## Business Insights

### Strengths
- **High Customer Satisfaction**: 73.2% positive sentiment
- **Easy Assembly**: Frequently mentioned as a positive aspect
- **USB Functionality**: Well-received feature
- **Storage Features**: Drawers and storage space appreciated
- **Quality Perception**: Positive quality feedback

### Areas for Improvement
- **Assembly Instructions**: Some customers found setup complex
- **Component Durability**: Minor concerns about certain parts
- **USB Light Issue**: Bright blue light mentioned as a concern

### Recommendations
1. **Enhance Assembly Instructions**: Provide clearer, more detailed setup guides
2. **Address USB Light**: Consider dimmer or switchable LED options
3. **Quality Assurance**: Continue monitoring component durability
4. **Marketing Focus**: Highlight ease of assembly and USB features

## Technical Implementation

### Data Processing Pipeline
1. **Step 00**: Web scraping of product reviews
2. **Step 10**: Text parsing and data cleaning
3. **Step 20**: Sentiment analysis using custom lexicon
4. **Step 30**: Topic modelling with LDA
5. **Step 40**: Word frequency and bigram analysis
6. **Step 50**: Comprehensive visualization generation

### Tools and Technologies
- **R Programming Language**
- **tidyverse**: Data manipulation and visualization
- **tidytext**: Text mining and sentiment analysis
- **topicmodels**: Latent Dirichlet Allocation
- **ggplot2**: Statistical visualizations
- **rmarkdown**: Report generation

### Data Quality
- **164 reviews successfully parsed**
- **High data quality** with minimal missing values
- **Consistent rating format** across reviews
- **Rich text content** for comprehensive analysis

## Files Generated

### Data Files
- `10.parsed-comments/Natural-Cassandra-Bed-with-USB-Port-and-Drawers-OIKT2041.csv`
- `20.sentiment-analysis/Natural-Cassandra-Bed-with-USB-Port-and-Drawers-OIKT2041.csv`
- `30.topic-modelling/Natural-Cassandra-Bed-with-USB-Port-and-Drawers-OIKT2041.csv`
- `40.word-frequency-analysis/word_frequency.csv`
- `40.word-frequency-analysis/bigrams.csv`
- `40.word-frequency-analysis/word_frequency_by_sentiment.csv`

### Visualizations
- `50.visualisations/01_sentiment_distribution.png`
- `50.visualisations/02_rating_distribution.png`
- `50.visualisations/03_average_rating.png`
- `50.visualisations/04_location_analysis.png`
- `50.visualisations/06_top_words_sentiment.png`
- `50.visualisations/07_topic_distribution.png`
- `50.visualisations/Natural-Cassandra-Bed-with-USB-Port-and-Drawers-OIKT2041.png`

### Reports
- `Sentiment-Analysis.html`: Complete HTML report
- `Sentiment-Analysis.Rmd`: R Markdown source
- `README.md`: Project documentation

## Conclusion

The sentiment analysis reveals a highly positive customer perception of the Natural Cassandra Bed with USB Port and Drawers. With 73.2% positive sentiment and an average rating of 4.02/5, the product demonstrates strong customer satisfaction. Key strengths include ease of assembly, USB functionality, and storage features. Minor areas for improvement include assembly instructions and USB light design.

The analysis provides valuable insights for product development, marketing strategy, and customer service improvements. The comprehensive data pipeline ensures reproducible results and can be easily adapted for other product reviews.

## Next Steps

1. **Monitor Ongoing Reviews**: Track sentiment trends over time
2. **Expand Analysis**: Include more product variants
3. **Competitive Analysis**: Compare with similar products
4. **Customer Journey**: Analyze sentiment at different touchpoints
5. **Predictive Modeling**: Develop models to predict customer satisfaction

---

*Analysis completed using R-based sentiment analysis pipeline with custom lexicons and topic modelling techniques.*
