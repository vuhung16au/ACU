# Atlassian Dataset for Financial Sentiment Classification

## Dataset Overview

This dataset contains 3 HTML news articles about Atlassian Corporation (NASDAQ: TEAM) that were collected for financial sentiment analysis. The articles cover recent business developments and acquisitions that could impact the company's stock price.

## Dataset Structure

### Files
- `atlassian-takes-on-google-apple-with-1b-ai-powered-bowser-play-20250905-p5msld.html` - Australian Financial Review article
- `theregister-on-Atlassian.html` - The Register technology news article  
- `unleash-on-Atlassian.html` - UNLEASH HR technology article

### Data Format
- **Format**: HTML files
- **Encoding**: UTF-8
- **Language**: English
- **Source**: Financial and technology news websites

## Article Descriptions

### 1. Australian Financial Review Article
- **Title**: "Atlassian (TEAM NASDAQ) acquires AI browser maker The Browser Company for $1 billion"
- **Source**: Australian Financial Review (AFR)
- **Date**: September 5, 2025
- **Content**: Covers Atlassian's $1 billion acquisition of The Browser Company, positioning it as a competitor to Google and Apple in the browser space
- **Sentiment Context**: Major acquisition news that could be viewed positively (growth, expansion) or negatively (high cost, integration risks)

### 2. The Register Article
- **Title**: "Atlassian buys The Browser Company, challenges ChromeOS"
- **Source**: The Register
- **Date**: September 4, 2025
- **Content**: Technology-focused coverage of the acquisition, emphasizing the competitive challenge to ChromeOS
- **Sentiment Context**: Focuses on competitive positioning and technological innovation

### 3. UNLEASH Article
- **Title**: "Atlassian targets AI browser for future of work with $610M acquisition of The Browser Company"
- **Source**: UNLEASH
- **Date**: September 5, 2025
- **Content**: HR technology perspective on the acquisition, emphasizing future of work applications
- **Sentiment Context**: Business strategy and workplace technology focus

## Data Characteristics

### Text Content
- **Length**: Articles range from 1,000 to 3,000+ words
- **Topics**: Corporate acquisitions, technology strategy, competitive positioning
- **Financial Terms**: Acquisition values, stock symbols, market positioning
- **Technical Terms**: AI, browser technology, cloud computing, enterprise software

### Sentiment Indicators
- **Positive Indicators**: Growth, acquisition, innovation, market expansion, competitive advantage
- **Negative Indicators**: High cost, integration challenges, market competition, execution risk
- **Neutral Indicators**: Factual reporting, technical descriptions, market analysis

## Data Quality

### Strengths
- Recent news articles (September 2025)
- Diverse sources (financial, technology, HR perspectives)
- Rich financial and technical vocabulary
- Complete HTML content with metadata

### Challenges
- HTML markup requires parsing
- Mixed content (headlines, body text, metadata)
- Varying article lengths and structures
- Need for manual sentiment labeling

## Preprocessing Requirements

### HTML Parsing
- Extract main article content
- Remove navigation, ads, and metadata
- Preserve paragraph structure
- Handle special characters and encoding

### Text Cleaning
- Remove HTML tags and attributes
- Normalize whitespace and formatting
- Handle special characters and symbols
- Preserve financial and technical terms

### Content Extraction
- Identify article titles and headlines
- Extract main body text
- Preserve paragraph breaks
- Handle embedded content (quotes, references)

## Sentiment Analysis Considerations

### Financial Context
- Acquisition announcements typically positive for stock
- High acquisition values may indicate confidence
- Competitive positioning affects market perception
- Integration risks may create uncertainty

### Text Features
- Financial terminology and metrics
- Corporate strategy language
- Market positioning statements
- Technical innovation descriptions

### Classification Approach
- Binary classification: Positive vs Negative sentiment
- Financial sentiment lexicons
- Domain-specific feature engineering
- Context-aware sentiment scoring

## Usage in Analysis

This dataset is designed for:
- Financial sentiment classification
- Text mining and NLP techniques
- Machine learning model training
- Financial news analysis
- Stock price impact prediction

## Data Privacy and Ethics

- All articles are publicly available news content
- No personal or sensitive information included
- Used for educational and research purposes
- Respects website terms of service
- No commercial use intended

## Future Enhancements

- Add more recent articles
- Include earnings call transcripts
- Add social media sentiment data
- Include stock price data for validation
- Expand to other technology companies
