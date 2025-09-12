# 2016 U.S. Presidential Race Dataset: Columns and Description

This dataset aggregates multiple sources used to analyze rhetoric and public opinion during the 2016 U.S. Presidential race.

## Folders
- debates/: Separate .txt transcripts for Clinton and Trump for each of the 3 debates.
- speeches/: Post-convention speech transcripts (.txt) for Clinton and Trump.
- primaries/: Selected primary campaign speeches for Clinton, Sanders, and Trump.
- polls/fivethirtyeight/: president_general_polls_2016.csv from FiveThirtyEight.
- polls/usc_daybreak/: popvote_votergroups.csv and readme.txt from USC Dornsife/LA Times.

## Poll CSV Columns (FiveThirtyEight)
Key columns in polls/fivethirtyeight/president_general_polls_2016.csv include:
- cycle: Election cycle (e.g., 2016)
- branch: Office (President)
- type: Model type (e.g., polls-plus)
- matchup: Candidates in the poll
- forecastdate: Forecast date
- state: State or U.S. national
- startdate, enddate: Field dates
- pollster, grade: Pollster name and FiveThirtyEight grade
- samplesize, population: Sample size and population type (lv, rv)
- rawpoll_* / adjpoll_*: Raw and adjusted poll percentages for the listed candidates
- url: Source link
- poll_id, question_id: Internal identifiers
- createddate, timestamp: Metadata

## Poll CSV Columns (USC Daybreak)
Key columns in polls/usc_daybreak/popvote_votergroups.csv include:
- date: Polling date
- N: Total sample size that day
- For each subgroup, pairs of columns for candidate shares and intervals, e.g.:
  - Trump_Male, Clinton_Male, sediff_Male, Lo_Male, Up_Male, Nsubgrp_Male
  - Similar sets for Female, White, Black, Hispanic, OtherRace, education tiers, age groups, income groups
- Trump_Overall, Clinton_Overall, sediff_Overall, Lo_Overall, Up_Overall: Overall metrics

## Text Files
All .txt files are UTF-8 text transcripts suitable for tokenization and NLP operations.

## Usage in Text Mining
The R script 09-01-Text-Mining/Text-Mining.R and notebook 09-01-Text-Mining/Text-Mining.Rmd demonstrate:
- Tokenization, stopwords, case normalization, filtering by minimum length
- Stemming and lemmatization
- N-grams (bigrams, trigrams)
- Token replacement/substitution
- Document-term matrix and a simple Naive Bayes classification demo
- White-background figures saved under 09-01-Text-Mining/images/
