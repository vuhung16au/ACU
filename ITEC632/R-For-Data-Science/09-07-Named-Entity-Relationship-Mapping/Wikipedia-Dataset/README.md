# Wikipedia Dataset for Named Entity Relationship Mapping

## Dataset Overview

This dataset contains Wikipedia articles that will be used for Named Entity Recognition (NER) and relationship mapping. The articles are selected to provide rich content with various types of entities (people, organizations, locations) and their relationships.

## Dataset Contents

The dataset includes Wikipedia articles on the following topics:

1. **Technology Companies**: Articles about major technology companies and their executives
2. **Historical Figures**: Biographical articles about notable people
3. **Geographic Locations**: Articles about cities, countries, and landmarks
4. **Organizations**: Articles about companies, institutions, and government bodies

## Data Collection Method

The articles are collected using:
- **WikipediR package**: For accessing Wikipedia API
- **rvest package**: For web scraping additional content when needed

## File Structure

```
Wikipedia-Dataset/
├── README.md                    # This file
├── articles/                    # Individual article files
│   ├── technology_companies.txt
│   ├── historical_figures.txt
│   ├── geographic_locations.txt
│   └── organizations.txt
└── metadata/                    # Article metadata
    └── article_info.csv
```

## Article Selection Criteria

Articles are selected based on:
- **Entity Richness**: Articles containing multiple types of entities
- **Relationship Density**: Articles with clear entity relationships
- **Content Quality**: Well-written, comprehensive articles
- **Diversity**: Covering different domains and topics

## Usage

This dataset is designed for:
- Named Entity Recognition (NER) training and testing
- Entity relationship extraction
- Dependency parsing analysis
- Text mining and NLP research

## Data Format

Each article is stored as plain text with:
- **Title**: Article title
- **Content**: Full article text
- **Metadata**: Source URL, date accessed, word count

## Notes

- Articles are collected from the English Wikipedia
- Content is cleaned and preprocessed for analysis
- Original formatting is preserved where relevant
- All articles are publicly available under Wikipedia's license
