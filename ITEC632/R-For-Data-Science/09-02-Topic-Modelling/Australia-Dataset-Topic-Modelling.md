# Australia Dataset for Topic Modeling

## Dataset Overview

The **Australia Dataset** is a synthetic text collection designed for topic modeling analysis. It consists of 6 text files, each focusing on different aspects of Australian life, culture, geography, and society.

## Dataset Structure

| File Name | Topic Area | Description |
|-----------|------------|-------------|
| `australian_cities.txt` | Geography & Cities | Information about major Australian cities including Sydney, Melbourne, Brisbane, Perth, Adelaide, and Canberra |
| `australian_animals.txt` | Wildlife & Nature | Unique Australian animals including kangaroos, koalas, platypus, wombats, emus, and other native species |
| `australian_landmarks.txt` | Tourism & Landmarks | Famous Australian landmarks and natural wonders like Sydney Opera House, Great Barrier Reef, Uluru |
| `australian_sports.txt` | Sports & Recreation | Popular Australian sports including AFL, cricket, rugby, tennis, swimming, and surfing |
| `australian_food.txt` | Cuisine & Food | Traditional Australian foods including Vegemite, meat pies, lamingtons, pavlova, and wine |
| `australian_culture.txt` | Culture & Society | Australian cultural aspects including Aboriginal heritage, language, traditions, and social values |

## Dataset Characteristics

### Text Statistics
- **Total Files**: 6 text documents
- **Total Words**: Approximately 1,200 words across all files
- **Average Words per File**: ~200 words
- **Language**: English
- **Encoding**: UTF-8

### Content Themes
Each file represents a distinct thematic area:

1. **Geographic Information**: Cities, locations, landmarks
2. **Natural World**: Animals, wildlife, environment
3. **Cultural Heritage**: Traditions, arts, society
4. **Recreation**: Sports, activities, entertainment
5. **Culinary**: Food, beverages, dining
6. **Social Aspects**: Culture, history, values

## File Descriptions

### australian_cities.txt
Contains information about major Australian cities:
- Sydney (largest city, Opera House, Harbour Bridge)
- Melbourne (cultural capital, coffee culture)
- Brisbane (Queensland capital, warm climate)
- Perth (Western Australia, isolated location)
- Adelaide (South Australia, wine regions)
- Canberra (national capital, ACT)

### australian_animals.txt
Describes unique Australian wildlife:
- Marsupials: kangaroos, koalas, wombats, Tasmanian devils
- Monotremes: platypus, echidna
- Birds: emu, kookaburra
- Other: dingoes, venomous snakes and spiders

### australian_landmarks.txt
Covers famous Australian landmarks:
- Sydney Opera House and Harbour Bridge
- Great Barrier Reef (natural wonder)
- Uluru/Ayers Rock (sacred site)
- Twelve Apostles (coastal formations)
- Blue Mountains and Great Ocean Road
- UNESCO World Heritage Sites

### australian_sports.txt
Details popular Australian sports:
- Australian Rules Football (AFL)
- Cricket (international success)
- Rugby (League and Union)
- Tennis (Australian Open)
- Swimming and surfing
- Melbourne Cup (horse racing)

### australian_food.txt
Describes traditional Australian cuisine:
- Vegemite (iconic spread)
- Meat pies (traditional food)
- Lamingtons and pavlova (desserts)
- Tim Tams and Anzac biscuits
- Australian wine regions
- Coffee culture

### australian_culture.txt
Explores Australian cultural aspects:
- Aboriginal and Torres Strait Islander heritage
- Didgeridoo and traditional music
- Australian English and expressions
- National holidays and commemorations
- Literature, cinema, and arts
- Egalitarian social values

## Dataset Usage

### For Topic Modeling
This dataset is ideal for topic modeling because:
- **Clear Topic Separation**: Each file represents a distinct thematic area
- **Balanced Content**: Similar length and complexity across files
- **Rich Vocabulary**: Diverse terminology across different domains
- **Real-world Relevance**: Authentic content about a specific country/culture

### Expected Topics
When analyzed with LDA, the dataset should reveal topics such as:
1. **Geographic/Urban**: Cities, landmarks, locations
2. **Natural/Environmental**: Animals, wildlife, nature
3. **Cultural/Social**: Traditions, arts, society, food

### Preprocessing Considerations
- Remove common English stop words
- Handle proper nouns (city names, animal names)
- Consider stemming for word variations
- Address domain-specific terminology

## Data Quality

### Strengths
- **Thematic Consistency**: Each file maintains focus on its topic area
- **Rich Content**: Comprehensive coverage of Australian topics
- **Balanced Length**: Similar document sizes for fair comparison
- **Diverse Vocabulary**: Wide range of terms across different domains

### Considerations
- **Synthetic Nature**: Artificially created for educational purposes
- **Limited Size**: Small dataset for demonstration purposes
- **Single Language**: English-only content
- **Geographic Focus**: Australia-specific content may limit generalizability

## File Format

Each text file is stored as plain text (.txt) with:
- **Encoding**: UTF-8
- **Line Endings**: Unix-style (LF)
- **Format**: Continuous text (no special formatting)
- **Size**: 1-3 KB per file

## Usage Instructions

1. **Load Dataset**: Use `DirSource()` from the `tm` package
2. **Preprocess**: Apply standard text preprocessing steps
3. **Create DTM**: Build Document-Term Matrix
4. **Apply LDA**: Use topic modeling algorithms
5. **Analyze Results**: Interpret discovered topics and document assignments

## Related Files

- `README.md`: Project overview and usage instructions
- `Topic-Modelling.R`: Main analysis script
- `Topic-Modelling.Rmd`: R Markdown documentation
- `images/`: Generated visualizations and plots

## License

This dataset is created for educational purposes as part of the R-For-Data-Science course materials.
