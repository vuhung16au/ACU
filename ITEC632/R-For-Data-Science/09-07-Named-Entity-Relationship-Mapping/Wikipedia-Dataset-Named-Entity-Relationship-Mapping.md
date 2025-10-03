# Wikipedia Dataset for Named Entity Relationship Mapping

## Dataset Overview

This dataset contains Wikipedia articles specifically selected for Named Entity Recognition (NER) and relationship mapping analysis. The articles are chosen to provide rich content with various types of entities and their relationships.

## Dataset Information

### Source
- **Platform**: English Wikipedia
- **Collection Method**: WikipediR package API
- **Date Collected**: 2024
- **Language**: English

### Article Selection Criteria
Articles are selected based on:
- **Entity Richness**: High density of named entities
- **Relationship Density**: Clear entity relationships
- **Content Quality**: Well-written, comprehensive articles
- **Domain Coverage**: Technology, business, and geography

## Dataset Contents

### Technology Companies (5 articles)
1. **Apple Inc.**
   - Content: Company history, products, executives
   - Entities: Tim Cook, iPhone, Cupertino, California
   - Relationships: CEO-Company, Product-Company, Company-Location

2. **Microsoft Corporation**
   - Content: Company overview, products, leadership
   - Entities: Bill Gates, Satya Nadella, Windows, Redmond
   - Relationships: Founder-Company, CEO-Company, Product-Company

3. **Google LLC**
   - Content: Company history, services, executives
   - Entities: Sundar Pichai, Larry Page, Sergey Brin, Mountain View
   - Relationships: CEO-Company, Founders-Company, Company-Location

4. **Tesla Inc.**
   - Content: Company information, products, leadership
   - Entities: Elon Musk, Model S, Fremont, California
   - Relationships: CEO-Company, Product-Company, Company-Location

5. **Amazon.com**
   - Content: Company overview, services, leadership
   - Entities: Jeff Bezos, Andy Jassy, Seattle, Washington
   - Relationships: Founder-Company, CEO-Company, Company-Location

### Business Executives (5 articles)
1. **Elon Musk**
   - Content: Biography, companies, achievements
   - Entities: Tesla, SpaceX, Neuralink, South Africa
   - Relationships: Person-Company, Person-Location, Person-Achievement

2. **Tim Cook**
   - Content: Biography, career, Apple
   - Entities: Apple Inc., Steve Jobs, Cupertino, Alabama
   - Relationships: Person-Company, Person-Location, Successor-Predecessor

3. **Bill Gates**
   - Content: Biography, Microsoft, philanthropy
   - Entities: Microsoft, Melinda Gates, Seattle, Washington
   - Relationships: Person-Company, Person-Location, Person-Family

4. **Sundar Pichai**
   - Content: Biography, Google, career
   - Entities: Google LLC, Alphabet Inc., Mountain View, India
   - Relationships: Person-Company, Person-Location, Person-Nationality

5. **Jeff Bezos**
   - Content: Biography, Amazon, Blue Origin
   - Entities: Amazon.com, Blue Origin, Seattle, Washington
   - Relationships: Person-Company, Person-Location, Person-Achievement

### Geographic Locations (5 articles)
1. **Silicon Valley**
   - Content: Region description, companies, history
   - Entities: San Francisco, Palo Alto, Stanford University, California
   - Relationships: Location-Company, Location-University, Location-Region

2. **Seattle**
   - Content: City information, companies, geography
   - Entities: Microsoft, Amazon, Washington, Puget Sound
   - Relationships: Location-Company, City-State, Location-Geography

3. **San Francisco**
   - Content: City overview, technology, geography
   - Entities: Silicon Valley, California, Bay Area, Golden Gate
   - Relationships: City-Region, City-State, Location-Landmark

4. **New York City**
   - Content: City information, business, geography
   - Entities: Manhattan, Brooklyn, Wall Street, New York
   - Relationships: City-Borough, City-State, Location-Business

5. **California**
   - Content: State information, geography, economy
   - Entities: Los Angeles, San Francisco, Silicon Valley, Sacramento
   - Relationships: State-City, State-Region, Location-Capital

## Data Format

### Article Structure
Each article contains:
- **Title**: Article title
- **Content**: Full article text (cleaned and preprocessed)
- **URL**: Source Wikipedia URL
- **Word Count**: Number of words in the article
- **Entity Count**: Number of named entities extracted
- **Relationship Count**: Number of entity relationships found

### Entity Types
- **PERSON**: People, executives, founders
- **ORGANIZATION**: Companies, institutions, government bodies
- **LOCATION**: Cities, states, countries, regions
- **OTHER**: Products, technologies, concepts

### Relationship Types
- **subject_of**: Entity is the subject of a relationship
- **object_of**: Entity is the object of a relationship
- **related_to**: General relationship between entities
- **modifier_of**: Descriptive relationship
- **associated_with**: Association relationship

## Data Quality

### Text Quality
- **Completeness**: Full article content included
- **Accuracy**: Original Wikipedia content preserved
- **Consistency**: Standardized formatting applied
- **Relevance**: Content relevant to entity extraction

### Entity Quality
- **Accuracy**: Entities correctly identified and classified
- **Completeness**: All relevant entities extracted
- **Consistency**: Consistent entity classification
- **Relationships**: Valid entity relationships identified

## Usage Guidelines

### For Named Entity Recognition
- Use the preprocessed text for NER training
- Focus on entity type classification
- Analyze entity frequency patterns
- Study entity co-occurrence patterns

### For Relationship Mapping
- Analyze dependency patterns
- Study relationship types
- Map entity networks
- Identify relationship clusters

### For Network Analysis
- Create entity relationship graphs
- Analyze network properties
- Identify central entities
- Study relationship patterns

## Technical Specifications

### File Format
- **Text Files**: Plain text format
- **Encoding**: UTF-8
- **Line Endings**: Unix (LF)
- **Size**: Variable (depending on article length)

### Processing Requirements
- **Memory**: Minimum 4GB RAM recommended
- **Storage**: Approximately 50MB for all articles
- **Processing Time**: 5-10 minutes for full analysis
- **Dependencies**: R packages for NLP processing

## Dataset Statistics

### Article Statistics
- **Total Articles**: 15
- **Average Word Count**: ~2,000 words per article
- **Total Word Count**: ~30,000 words
- **Average Entity Count**: ~100 entities per article
- **Total Entity Count**: ~1,500 entities

### Entity Distribution
- **PERSON**: ~40% of entities
- **ORGANIZATION**: ~35% of entities
- **LOCATION**: ~20% of entities
- **OTHER**: ~5% of entities

### Relationship Distribution
- **subject_of**: ~30% of relationships
- **object_of**: ~25% of relationships
- **related_to**: ~20% of relationships
- **modifier_of**: ~15% of relationships
- **associated_with**: ~10% of relationships

## Access and Usage

### Data Access
- **Location**: `Wikipedia-Dataset/` directory
- **Format**: Individual text files
- **Metadata**: Article information included
- **License**: Wikipedia content (CC BY-SA)

### Usage Examples
```r
# Load article content
article_content <- readLines("Wikipedia-Dataset/apple_inc.txt")

# Process with UDPipe
entities <- extract_named_entities(article_content, udpipe_model)

# Extract relationships
relationships <- extract_entity_relationships(annotation_df)
```

## Notes and Considerations

### Data Limitations
- **Language**: English only
- **Domain**: Technology and business focused
- **Temporal**: Content as of collection date
- **Bias**: Wikipedia content may contain bias

### Best Practices
- **Preprocessing**: Clean text before analysis
- **Validation**: Verify entity classifications
- **Context**: Consider article context
- **Updates**: Refresh data periodically

### Future Enhancements
- **Multilingual**: Add articles in other languages
- **Diverse Domains**: Include articles from other domains
- **Temporal**: Add historical article versions
- **Quality**: Improve entity classification accuracy
