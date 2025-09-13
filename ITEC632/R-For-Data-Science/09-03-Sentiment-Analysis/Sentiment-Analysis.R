# Sentiment Analysis of Product Reviews
# Natural Cassandra Bed with USB Port and Drawers

# Load required libraries
library(tidyverse)
library(tidytext)
library(topicmodels)
library(ggplot2)
library(dplyr)
library(stringr)
library(rvest)
library(xml2)
library(SentimentAnalysis)
library(wordcloud)
library(RColorBrewer)
library(gridExtra)
library(corrplot)

# Set options for non-interactive lexicon download
options(tidytext.download_lexicons = TRUE)

# Set working directory
setwd("/Users/vuhung/00.Work/02.ACU/github/ITEC632/R-For-Data-Science/09-03-Sentiment-Analysis")

# =============================================================================
# STEP 10: Parse reviews from the HTML file
# =============================================================================

parse_reviews <- function() {
  cat("Step 10: Parsing reviews from HTML file...\n")
  
  # Read the comments file
  comments_text <- readLines("Product-Comments/Product-Comments.md")
  
  # Parse the comments
  reviews <- data.frame()
  
  # Initialize variables
  current_date <- ""
  current_rating <- ""
  current_location <- ""
  current_comment <- ""
  
  i <- 1
  while (i <= length(comments_text)) {
    line <- trimws(comments_text[i])
    
    # Skip empty lines and headers
    if (line == "" || line == "Customers are saying" || 
        line == "Verified customer reviews, summarised by AI" ||
        line == "Customer photos" || line == "Relevance" ||
        str_detect(line, "Was this helpful") ||
        str_detect(line, "Verified Buyer")) {
      i <- i + 1
      next
    }
    
    # Check for date pattern (e.g., "11 Sep 2025")
    if (str_detect(line, "^\\d{1,2} [A-Za-z]{3} \\d{4}$")) {
      # Save previous review if exists
      if (current_comment != "" && current_date != "") {
        reviews <- rbind(reviews, data.frame(
          date = current_date,
          rating = current_rating,
          location = current_location,
          comment = current_comment,
          stringsAsFactors = FALSE
        ))
      }
      
      # Start new review
      current_date <- line
      current_rating <- ""
      current_location <- ""
      current_comment <- ""
      
      # Look for comment on next line
      i <- i + 1
      if (i <= length(comments_text)) {
        comment_line <- trimws(comments_text[i])
        if (comment_line != "" && !str_detect(comment_line, "Customers are saying") && 
            !str_detect(comment_line, "Verified customer reviews") &&
            !str_detect(comment_line, "Customer photos") && 
            !str_detect(comment_line, "Relevance") &&
            !str_detect(comment_line, "Was this helpful") &&
            !str_detect(comment_line, "Verified Buyer") &&
            !str_detect(comment_line, "^\\d{1,2} [A-Za-z]{3} \\d{4}$") &&
            !str_detect(comment_line, "^[A-Za-z]+,\\s+[A-Za-z\\s]+,\\s+[A-Z]{2,3}")) {
          current_comment <- comment_line
        }
      }
      
      # Look for location on next few lines
      i <- i + 1
      while (i <= length(comments_text)) {
        location_line <- trimws(comments_text[i])
        if (location_line == "") {
          i <- i + 1
          next
        }
        
        if (str_detect(location_line, "^[A-Za-z]+,\\s+[A-Za-z\\s]+,\\s+[A-Z]{2,3}")) {
          current_location <- location_line
          break
        }
        
        if (str_detect(location_line, "Was this helpful") || 
            str_detect(location_line, "Verified Buyer") ||
            str_detect(location_line, "^\\d{1,2} [A-Za-z]{3} \\d{4}$")) {
          break
        }
        
        i <- i + 1
      }
    }
    
    i <- i + 1
  }
  
  # Add the last review
  if (current_comment != "" && current_date != "") {
    reviews <- rbind(reviews, data.frame(
      date = current_date,
      rating = current_rating,
      location = current_location,
      comment = current_comment,
      stringsAsFactors = FALSE
    ))
  }
  
  # Clean up the data
  reviews$comment <- str_trim(reviews$comment)
  reviews$location <- str_trim(reviews$location)
  
  # Extract ratings from comments using heuristics
  reviews$rating <- ifelse(str_detect(reviews$comment, "five stars|5 stars|5/5"), "5",
                   ifelse(str_detect(reviews$comment, "four stars|4 stars|4/5"), "4",
                   ifelse(str_detect(reviews$comment, "three stars|3 stars|3/5"), "3",
                   ifelse(str_detect(reviews$comment, "two stars|2 stars|2/5"), "2",
                   ifelse(str_detect(reviews$comment, "one star|1 star|1/5"), "1", "4")))))
  
  # Convert rating to numeric
  reviews$rating <- as.numeric(reviews$rating)
  
  # Save parsed reviews
  write.csv(reviews, "10.parsed-comments/Natural-Cassandra-Bed-with-USB-Port-and-Drawers-OIKT2041.csv", 
            row.names = FALSE)
  
  cat("Parsed", nrow(reviews), "reviews\n")
  return(reviews)
}

# =============================================================================
# STEP 20: Sentiment Analysis
# =============================================================================

perform_sentiment_analysis <- function(reviews) {
  cat("Step 20: Performing sentiment analysis...\n")
  
  # Tokenize comments
  tokens <- reviews %>%
    unnest_tokens(word, comment) %>%
    anti_join(stop_words, by = "word") %>%
    filter(!str_detect(word, "^\\d+$")) # Remove numbers
  
  # Create a simple sentiment dictionary
  positive_words <- c("good", "great", "excellent", "amazing", "wonderful", "fantastic", 
                      "love", "perfect", "beautiful", "awesome", "brilliant", "outstanding",
                      "happy", "satisfied", "pleased", "impressed", "recommend", "best",
                      "easy", "simple", "quick", "fast", "sturdy", "solid", "well", "nice")
  
  negative_words <- c("bad", "terrible", "awful", "horrible", "disappointed", "hate",
                      "worst", "poor", "cheap", "broken", "damaged", "difficult", "hard",
                      "slow", "complicated", "problem", "issue", "faulty", "defective",
                      "unhappy", "unsatisfied", "regret", "waste", "useless", "wrong")
  
  # Calculate sentiment scores
  sentiment_scores <- tokens %>%
    mutate(sentiment_score = case_when(
      word %in% positive_words ~ 1,
      word %in% negative_words ~ -1,
      TRUE ~ 0
    )) %>%
    group_by(date, rating, location) %>%
    summarise(sentiment_score = sum(sentiment_score), .groups = "drop")
  
  # Get sentiment labels
  sentiment_labels <- tokens %>%
    mutate(sentiment = case_when(
      word %in% positive_words ~ "positive",
      word %in% negative_words ~ "negative",
      TRUE ~ "neutral"
    )) %>%
    count(date, rating, location, sentiment) %>%
    pivot_wider(names_from = sentiment, values_from = n, values_fill = 0) %>%
    mutate(sentiment_label = ifelse(positive > negative, "positive",
                            ifelse(negative > positive, "negative", "neutral")))
  
  # Combine sentiment analysis results
  sentiment_results <- reviews %>%
    left_join(sentiment_scores, by = c("date", "rating", "location")) %>%
    left_join(sentiment_labels %>% select(date, rating, location, sentiment_label), 
              by = c("date", "rating", "location")) %>%
    mutate(
      sentiment_score = ifelse(is.na(sentiment_score), 0, sentiment_score),
      sentiment_label = ifelse(is.na(sentiment_label), "neutral", sentiment_label)
    )
  
  # Save sentiment analysis results
  write.csv(sentiment_results, "20.sentiment-analysis/Natural-Cassandra-Bed-with-USB-Port-and-Drawers-OIKT2041.csv", 
            row.names = FALSE)
  
  cat("Sentiment analysis completed\n")
  return(sentiment_results)
}

# =============================================================================
# STEP 30: Topic Modelling
# =============================================================================

perform_topic_modelling <- function(reviews) {
  cat("Step 30: Performing topic modelling...\n")
  
  # Prepare document-term matrix
  dtm <- reviews %>%
    unnest_tokens(word, comment) %>%
    anti_join(stop_words, by = "word") %>%
    filter(!str_detect(word, "^\\d+$")) %>%
    count(date, word) %>%
    cast_dtm(date, word, n)
  
  # Perform LDA topic modelling
  lda_model <- LDA(dtm, k = 5, control = list(seed = 1234))
  
  # Get topic probabilities for each document
  topic_probs <- tidy(lda_model, matrix = "gamma") %>%
    pivot_wider(names_from = topic, values_from = gamma, names_prefix = "topic_")
  
  # Get top terms for each topic
  topic_terms <- tidy(lda_model, matrix = "beta") %>%
    group_by(topic) %>%
    slice_max(beta, n = 10) %>%
    ungroup() %>%
    arrange(topic, -beta)
  
  # Combine with original reviews
  topic_results <- reviews %>%
    left_join(topic_probs, by = c("date" = "document")) %>%
    mutate(
      dominant_topic = apply(select(., starts_with("topic_")), 1, which.max)
    )
  
  # Convert list columns to character for CSV export
  topic_results_clean <- topic_results %>%
    mutate(across(where(is.list), ~ sapply(., function(x) paste(x, collapse = "; "))))
  
  # Save topic modelling results
  write.csv(topic_results_clean, "30.topic-modelling/Natural-Cassandra-Bed-with-USB-Port-and-Drawers-OIKT2041.csv", 
            row.names = FALSE)
  
  # Save topic terms
  write.csv(topic_terms, "30.topic-modelling/topic_terms.csv", row.names = FALSE)
  
  cat("Topic modelling completed\n")
  return(list(topic_results = topic_results_clean, topic_terms = topic_terms))
}

# =============================================================================
# STEP 40: Word Frequency Analysis
# =============================================================================

perform_word_frequency_analysis <- function(reviews) {
  cat("Step 40: Performing word frequency analysis...\n")
  
  # Tokenize and clean text
  word_freq <- reviews %>%
    unnest_tokens(word, comment) %>%
    anti_join(stop_words, by = "word") %>%
    filter(!str_detect(word, "^\\d+$")) %>%
    filter(nchar(word) > 2) %>% # Remove very short words
    count(word, sort = TRUE) %>%
    mutate(proportion = n / sum(n))
  
  # Get bigrams
  bigrams <- reviews %>%
    unnest_tokens(bigram, comment, token = "ngrams", n = 2) %>%
    separate(bigram, c("word1", "word2"), sep = " ") %>%
    filter(!word1 %in% stop_words$word) %>%
    filter(!word2 %in% stop_words$word) %>%
    filter(!str_detect(word1, "^\\d+$")) %>%
    filter(!str_detect(word2, "^\\d+$")) %>%
    unite(bigram, word1, word2, sep = " ") %>%
    count(bigram, sort = TRUE)
  
  # Word frequency by sentiment
  positive_words <- c("good", "great", "excellent", "amazing", "wonderful", "fantastic", 
                      "love", "perfect", "beautiful", "awesome", "brilliant", "outstanding",
                      "happy", "satisfied", "pleased", "impressed", "recommend", "best",
                      "easy", "simple", "quick", "fast", "sturdy", "solid", "well", "nice")
  
  negative_words <- c("bad", "terrible", "awful", "horrible", "disappointed", "hate",
                      "worst", "poor", "cheap", "broken", "damaged", "difficult", "hard",
                      "slow", "complicated", "problem", "issue", "faulty", "defective",
                      "unhappy", "unsatisfied", "regret", "waste", "useless", "wrong")
  
  word_freq_by_sentiment <- reviews %>%
    unnest_tokens(word, comment) %>%
    anti_join(stop_words, by = "word") %>%
    filter(!str_detect(word, "^\\d+$")) %>%
    filter(nchar(word) > 2) %>%
    mutate(sentiment = case_when(
      word %in% positive_words ~ "positive",
      word %in% negative_words ~ "negative",
      TRUE ~ "neutral"
    )) %>%
    filter(sentiment != "neutral") %>%
    count(word, sentiment, sort = TRUE) %>%
    pivot_wider(names_from = sentiment, values_from = n, values_fill = 0) %>%
    mutate(sentiment_score = positive - negative)
  
  # Save word frequency results
  write.csv(word_freq, "40.word-frequency-analysis/word_frequency.csv", row.names = FALSE)
  write.csv(bigrams, "40.word-frequency-analysis/bigrams.csv", row.names = FALSE)
  write.csv(word_freq_by_sentiment, "40.word-frequency-analysis/word_frequency_by_sentiment.csv", row.names = FALSE)
  
  cat("Word frequency analysis completed\n")
  return(list(word_freq = word_freq, bigrams = bigrams, word_freq_by_sentiment = word_freq_by_sentiment))
}

# =============================================================================
# STEP 50: Visualizations
# =============================================================================

create_visualizations <- function(reviews, sentiment_results, topic_results, word_freq_results) {
  cat("Step 50: Creating visualizations...\n")
  
  # 1. Sentiment Analysis Visualization
  sentiment_summary <- sentiment_results %>%
    count(sentiment_label) %>%
    mutate(percentage = n / sum(n) * 100)
  
  p1 <- ggplot(sentiment_summary, aes(x = sentiment_label, y = n, fill = sentiment_label)) +
    geom_bar(stat = "identity") +
    geom_text(aes(label = paste0(n, " (", round(percentage, 1), "%)")), 
              vjust = -0.5, size = 3) +
    labs(title = "Sentiment Distribution of Reviews",
         x = "Sentiment", y = "Number of Reviews") +
    theme_minimal() +
    scale_fill_manual(values = c("positive" = "green", "negative" = "red", "neutral" = "gray")) +
    theme(panel.background = element_rect(fill = "white", color = NA),
          plot.background = element_rect(fill = "white", color = NA))
  
  # 2. Rating Distribution
  rating_summary <- reviews %>%
    count(rating) %>%
    mutate(percentage = n / sum(n) * 100)
  
  p2 <- ggplot(rating_summary, aes(x = factor(rating), y = n, fill = factor(rating))) +
    geom_bar(stat = "identity") +
    geom_text(aes(label = paste0(n, " (", round(percentage, 1), "%)")), 
              vjust = -0.5, size = 3) +
    labs(title = "Rating Distribution",
         x = "Rating", y = "Number of Reviews") +
    theme_minimal() +
    scale_fill_viridis_d() +
    theme(panel.background = element_rect(fill = "white", color = NA),
          plot.background = element_rect(fill = "white", color = NA))
  
  # 3. Average Rating
  avg_rating <- mean(reviews$rating, na.rm = TRUE)
  
  p3 <- ggplot(data.frame(rating = avg_rating), aes(x = "", y = rating)) +
    geom_col(fill = "steelblue", width = 0.5) +
    geom_text(aes(label = paste0("Average Rating: ", round(rating, 2))), 
              vjust = -0.5, size = 4) +
    labs(title = "Average Rating", x = "", y = "Rating") +
    theme_minimal() +
    ylim(0, 5) +
    theme(panel.background = element_rect(fill = "white", color = NA),
          plot.background = element_rect(fill = "white", color = NA))
  
  # 4. Location Analysis
  location_summary <- reviews %>%
    mutate(state = str_extract(location, "\\b(NSW|VIC|QLD|SA|WA|TAS|NT|ACT)\\b")) %>%
    filter(!is.na(state) & state != "") %>%
    count(state) %>%
    arrange(desc(n))
  
  p4 <- ggplot(location_summary, aes(x = reorder(state, n), y = n, fill = state)) +
    geom_bar(stat = "identity") +
    geom_text(aes(label = n), hjust = -0.5, size = 3) +
    labs(title = "Reviews by State",
         x = "State", y = "Number of Reviews") +
    theme_minimal() +
    coord_flip() +
    scale_fill_viridis_d() +
    theme(panel.background = element_rect(fill = "white", color = NA),
          plot.background = element_rect(fill = "white", color = NA))
  
  # 5. Word Cloud
  word_freq <- word_freq_results$word_freq
  p5 <- wordcloud(words = word_freq$word, freq = word_freq$n, 
                  max.words = 100, random.order = FALSE, 
                  colors = brewer.pal(8, "Dark2"))
  
  # 6. Top Words by Sentiment
  top_words_sentiment <- word_freq_results$word_freq_by_sentiment %>%
    filter(sentiment_score != 0) %>%
    mutate(sentiment = ifelse(sentiment_score > 0, "positive", "negative")) %>%
    group_by(sentiment) %>%
    slice_max(abs(sentiment_score), n = 10) %>%
    ungroup()
  
  p6 <- ggplot(top_words_sentiment, aes(x = reorder(word, sentiment_score), y = sentiment_score, fill = sentiment)) +
    geom_col() +
    labs(title = "Top Words by Sentiment",
         x = "Word", y = "Sentiment Score") +
    theme_minimal() +
    coord_flip() +
    scale_fill_manual(values = c("positive" = "green", "negative" = "red")) +
    theme(panel.background = element_rect(fill = "white", color = NA),
          plot.background = element_rect(fill = "white", color = NA))
  
  # 7. Topic Distribution
  topic_dist <- topic_results$topic_results %>%
    select(dominant_topic) %>%
    count(dominant_topic) %>%
    mutate(percentage = n / sum(n) * 100)
  
  p7 <- ggplot(topic_dist, aes(x = factor(dominant_topic), y = n, fill = factor(dominant_topic))) +
    geom_bar(stat = "identity") +
    geom_text(aes(label = paste0(n, " (", round(percentage, 1), "%)")), 
              vjust = -0.5, size = 3) +
    labs(title = "Topic Distribution",
         x = "Topic", y = "Number of Reviews") +
    theme_minimal() +
    scale_fill_viridis_d() +
    theme(panel.background = element_rect(fill = "white", color = NA),
          plot.background = element_rect(fill = "white", color = NA))
  
  # Save individual plots with white background
  ggsave("50.visualisations/01_sentiment_distribution.png", p1, width = 10, height = 6, 
         bg = "white", dpi = 300)
  ggsave("50.visualisations/02_rating_distribution.png", p2, width = 10, height = 6, 
         bg = "white", dpi = 300)
  ggsave("50.visualisations/03_average_rating.png", p3, width = 10, height = 6, 
         bg = "white", dpi = 300)
  ggsave("50.visualisations/04_location_analysis.png", p4, width = 10, height = 6, 
         bg = "white", dpi = 300)
  ggsave("50.visualisations/06_top_words_sentiment.png", p6, width = 10, height = 6, 
         bg = "white", dpi = 300)
  ggsave("50.visualisations/07_topic_distribution.png", p7, width = 10, height = 6, 
         bg = "white", dpi = 300)
  
  # Create combined visualization
  combined_plot <- grid.arrange(p1, p2, p3, p4, p6, p7, ncol = 2)
  ggsave("50.visualisations/Natural-Cassandra-Bed-with-USB-Port-and-Drawers-OIKT2041.png", 
         combined_plot, width = 16, height = 12, bg = "white", dpi = 300)
  
  cat("Visualizations created and saved\n")
}

# =============================================================================
# MAIN EXECUTION
# =============================================================================

main <- function() {
  cat("Starting Sentiment Analysis of Product Reviews\n")
  cat("==============================================\n")
  
  # Step 10: Parse reviews
  reviews <- parse_reviews()
  
  # Step 20: Sentiment analysis
  sentiment_results <- perform_sentiment_analysis(reviews)
  
  # Step 30: Topic modelling
  topic_results <- perform_topic_modelling(reviews)
  
  # Step 40: Word frequency analysis
  word_freq_results <- perform_word_frequency_analysis(reviews)
  
  # Step 50: Visualizations
  create_visualizations(reviews, sentiment_results, topic_results, word_freq_results)
  
  # Generate summary report
  cat("\n=== SUMMARY REPORT ===\n")
  cat("Total reviews analyzed:", nrow(reviews), "\n")
  cat("Average rating:", round(mean(reviews$rating, na.rm = TRUE), 2), "\n")
  
  sentiment_summary <- table(sentiment_results$sentiment_label)
  cat("Sentiment distribution:\n")
  print(sentiment_summary)
  
  location_summary <- reviews %>%
    mutate(state = str_extract(location, "\\b(NSW|VIC|QLD|SA|WA|TAS|NT|ACT)\\b")) %>%
    filter(!is.na(state)) %>%
    count(state) %>%
    arrange(desc(n))
  cat("Top states by review count:\n")
  print(head(location_summary, 5))
  
  cat("\nAnalysis completed successfully!\n")
  cat("Results saved in respective directories.\n")
}

# Run the main function
main()
