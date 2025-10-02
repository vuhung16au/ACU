# Topic Modeling in R
# Implementation of Latent Dirichlet Allocation (LDA) for text analysis
# Dataset: Australia-Dataset

# Load required libraries
library(tm)
library(topicmodels)
library(tidytext)
library(dplyr)
library(ggplot2)
library(wordcloud)
library(RColorBrewer)
library(SnowballC)

# Set working directory to the script location
# Note: This assumes the script is run from the 09-02-Topic-Modelling directory

# Create images directory if it doesn't exist
if (!dir.exists("images")) {
  dir.create("images", recursive = TRUE)
}

# Set white background for all plots
par(bg = "white")

# 1. Load and preprocess the dataset
cat("Loading and preprocessing the Australia dataset...\n")

# Define the directory containing the text files
data_dir <- "Australia-Dataset/"

# Read all text files into a corpus
corpus <- VCorpus(DirSource(data_dir), readerControl = list(language = "en"))

# Display basic information about the corpus
cat("Number of documents:", length(corpus), "\n")
cat("Document names:", names(corpus), "\n")

# Preprocess the text
cat("Preprocessing text data...\n")
corpus_clean <- tm_map(corpus, content_transformer(tolower))
corpus_clean <- tm_map(corpus_clean, removePunctuation)
corpus_clean <- tm_map(corpus_clean, removeNumbers)
corpus_clean <- tm_map(corpus_clean, removeWords, stopwords("english"))
corpus_clean <- tm_map(corpus_clean, stripWhitespace)
corpus_clean <- tm_map(corpus_clean, stemDocument)

# Create a Document-Term Matrix
dtm <- DocumentTermMatrix(corpus_clean)

# Remove sparse terms (terms that appear in less than 2 documents)
dtm <- removeSparseTerms(dtm, 0.8)

# Display DTM information
cat("Document-Term Matrix dimensions:", dim(dtm), "\n")
cat("Sparsity:", round(sum(dtm == 0) / (nrow(dtm) * ncol(dtm)), 3), "\n")

# 2. Word Frequency Analysis
cat("Analyzing word frequencies...\n")

# Convert the DTM to a matrix and calculate word frequencies
word_freq <- colSums(as.matrix(dtm))
word_freq <- sort(word_freq, decreasing = TRUE)

# Convert to a data frame
word_freq_df <- data.frame(word = names(word_freq), freq = word_freq, stringsAsFactors = FALSE)

# Display top 20 most frequent words
cat("Top 20 most frequent words:\n")
print(head(word_freq_df, 20))

# Create word frequency plot
png("images/word_frequency_plot.png", width = 800, height = 600, bg = "white")
top_words <- head(word_freq_df, 15)
barplot(top_words$freq, names.arg = top_words$word, 
        main = "Top 15 Most Frequent Words in Australia Dataset",
        xlab = "Words", ylab = "Frequency", 
        col = "steelblue", las = 2)
dev.off()

# 3. Generate Word Cloud
cat("Generating word cloud...\n")

# Set seed for reproducibility
set.seed(123)

# Generate word cloud
png("images/wordcloud.png", width = 800, height = 600, bg = "white")
wordcloud(words = word_freq_df$word, freq = word_freq_df$freq, 
          min.freq = 1, max.words = 100, 
          random.order = FALSE, 
          colors = brewer.pal(8, "Dark2"),
          scale = c(3, 0.5))
dev.off()

# 4. Topic Modeling using LDA
cat("Performing topic modeling with LDA...\n")

# Set the number of topics (we'll try 3 topics for our dataset)
k <- 3

# Fit the LDA model
set.seed(1234)
lda_model <- LDA(dtm, k = k, control = list(seed = 1234))

# Extract the topics using tidytext
topics <- tidy(lda_model, matrix = "beta")

# Get top terms for each topic
top_terms <- topics %>%
  group_by(topic) %>%
  top_n(10, beta) %>%
  ungroup() %>%
  arrange(topic, -beta)

cat("Top terms for each topic:\n")
print(top_terms)

# Create topic visualization
png("images/topic_terms_plot.png", width = 1000, height = 600, bg = "white")
top_terms %>%
  mutate(term = reorder(term, beta)) %>%
  ggplot(aes(term, beta, fill = factor(topic))) +
  geom_col(show.legend = FALSE) +
  facet_wrap(~ topic, scales = "free") +
  coord_flip() +
  labs(title = "Top Terms for Each Topic",
       x = "Terms", y = "Beta (Probability)") +
  theme_minimal() +
  theme(plot.background = element_rect(fill = "white", color = NA),
        panel.background = element_rect(fill = "white", color = NA))
dev.off()

# 5. Document-topic probabilities
cat("Analyzing document-topic probabilities...\n")

# Get document-topic probabilities
doc_topics <- tidy(lda_model, matrix = "gamma")

# Display document-topic assignments
cat("Document-topic probabilities:\n")
print(doc_topics)

# Create document-topic visualization
png("images/document_topic_plot.png", width = 800, height = 600, bg = "white")
doc_topics %>%
  mutate(document = reorder(document, gamma)) %>%
  ggplot(aes(factor(topic), gamma)) +
  geom_boxplot() +
  facet_wrap(~ document) +
  labs(title = "Document-Topic Probabilities",
       x = "Topic", y = "Gamma (Probability)") +
  theme_minimal() +
  theme(plot.background = element_rect(fill = "white", color = NA),
        panel.background = element_rect(fill = "white", color = NA))
dev.off()

# 6. Alternative LDA implementations
cat("Testing alternative LDA implementations...\n")

# Try with different number of topics
k_values <- c(2, 3, 4, 5)
perplexity_scores <- numeric(length(k_values))

for (i in seq_along(k_values)) {
  set.seed(1234)
  lda_temp <- LDA(dtm, k = k_values[i], control = list(seed = 1234))
  perplexity_scores[i] <- perplexity(lda_temp, dtm)
  cat("K =", k_values[i], "Perplexity =", perplexity_scores[i], "\n")
}

# Create perplexity plot
png("images/perplexity_plot.png", width = 800, height = 600, bg = "white")
plot(k_values, perplexity_scores, type = "b", 
     main = "Perplexity vs Number of Topics",
     xlab = "Number of Topics (K)", ylab = "Perplexity",
     col = "steelblue", pch = 19)
grid()
dev.off()

# 7. Summary and results
cat("\n=== TOPIC MODELING SUMMARY ===\n")
cat("Dataset: Australia Dataset\n")
cat("Number of documents:", length(corpus), "\n")
cat("Number of terms in DTM:", ncol(dtm), "\n")
cat("Number of topics (K):", k, "\n")
cat("Total word frequency:", sum(word_freq), "\n")
cat("Most frequent word:", word_freq_df$word[1], "(", word_freq_df$freq[1], "times)\n")

# Display topic assignments for each document
cat("\nTopic assignments for each document:\n")
for (i in 1:length(corpus)) {
  doc_name <- names(corpus)[i]
  doc_topic_probs <- doc_topics[doc_topics$document == doc_name, ]
  if (nrow(doc_topic_probs) > 0) {
    dominant_topic <- doc_topic_probs$topic[which.max(doc_topic_probs$gamma)]
    cat("Document", i, "(", doc_name, "): Topic", dominant_topic, 
        "(probability:", round(max(doc_topic_probs$gamma), 3), ")\n")
  } else {
    cat("Document", i, "(", doc_name, "): No topic assignment found\n")
  }
}

cat("\nAnalysis complete! Check the 'images' folder for generated visualizations.\n")
cat("Generated files:\n")
cat("- word_frequency_plot.png\n")
cat("- wordcloud.png\n")
cat("- topic_terms_plot.png\n")
cat("- document_topic_plot.png\n")
cat("- perplexity_plot.png\n")
