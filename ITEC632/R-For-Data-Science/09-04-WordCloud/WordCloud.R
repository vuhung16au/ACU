#!/usr/bin/env Rscript

suppressPackageStartupMessages({
  `%||%` <- function(a, b) if (!is.null(a) && !is.na(a) && a != "") a else b
  if (!requireNamespace("tidyverse", quietly = TRUE)) install.packages("tidyverse", repos = "https://cloud.r-project.org")
  if (!requireNamespace("tidytext", quietly = TRUE)) install.packages("tidytext", repos = "https://cloud.r-project.org")
  if (!requireNamespace("tm", quietly = TRUE)) install.packages("tm", repos = "https://cloud.r-project.org")
  if (!requireNamespace("wordcloud", quietly = TRUE)) install.packages("wordcloud", repos = "https://cloud.r-project.org")
  if (!requireNamespace("RColorBrewer", quietly = TRUE)) install.packages("RColorBrewer", repos = "https://cloud.r-project.org")
  if (!requireNamespace("SnowballC", quietly = TRUE)) install.packages("SnowballC", repos = "https://cloud.r-project.org")
})

suppressPackageStartupMessages({
  library(tidyverse)
  library(tidytext)
  library(tm)
  library(wordcloud)
  library(RColorBrewer)
  library(SnowballC)
})

# Resolve this script's directory so outputs are saved next to it
args_all <- commandArgs(trailingOnly = FALSE)
script_arg <- grep("^--file=", args_all, value = TRUE)
script_path <- if (length(script_arg) > 0) sub("^--file=", "", script_arg[1]) else NA_character_
script_path <- tryCatch(normalizePath(script_path), error = function(e) NA_character_)
if (is.na(script_path) || script_path == "") script_path <- tryCatch(normalizePath("09-04-WordCloud/WordCloud.R"), error = function(e) getwd())
base_dir <- dirname(script_path)

dataset_dir <- file.path(base_dir, "Australia-Dataset")
images_dir <- file.path(base_dir, "images")
if (!dir.exists(images_dir)) dir.create(images_dir, recursive = TRUE)

save_plot <- function(p, filename, width = 10, height = 6) {
  p_out <- p + theme_bw() + theme(plot.background = element_rect(fill = "white", color = NA), panel.background = element_rect(fill = "white"))
  ggsave(filename = file.path(images_dir, filename), plot = p_out, width = width, height = height, dpi = 150, bg = "white")
}

# Function to read all text files from the dataset directory
read_texts <- function(root_dir) {
  files <- list.files(root_dir, pattern = "\\.txt$", full.names = TRUE)
  if (length(files) == 0) stop("No text files found in dataset directory.")
  
  tibble(
    doc_id = basename(files),
    path = files,
    category = case_when(
      grepl("geography", tolower(files)) ~ "Geography",
      grepl("cities", tolower(files)) ~ "Cities",
      grepl("wildlife", tolower(files)) ~ "Wildlife",
      grepl("culture", tolower(files)) ~ "Culture",
      grepl("tourism", tolower(files)) ~ "Tourism",
      grepl("history", tolower(files)) ~ "History",
      grepl("economy", tolower(files)) ~ "Economy",
      grepl("sports", tolower(files)) ~ "Sports",
      TRUE ~ "Other"
    ),
    text = map_chr(files, ~ paste(readLines(.x, warn = FALSE, encoding = "UTF-8"), collapse = " \n "))
  )
}

# Load the dataset
docs <- read_texts(dataset_dir)
cat("Loaded", nrow(docs), "documents from Australia dataset\n")

# Tokenize and clean the text
tokens <- docs %>% 
  mutate(text_lower = str_to_lower(text)) %>% 
  unnest_tokens(token, text_lower)

# Remove stop words and filter tokens
tokens_filtered <- tokens %>% 
  anti_join(stop_words, by = c("token" = "word")) %>% 
  filter(str_detect(token, "^[a-z]+$"), nchar(token) >= 3)

# Get word frequencies
word_freq <- tokens_filtered %>% 
  count(token, sort = TRUE)

cat("Total unique words after filtering:", nrow(word_freq), "\n")
cat("Top 10 most frequent words:\n")
print(head(word_freq, 10))

# Create word frequency plot
p1 <- word_freq %>% 
  slice_max(n, n = 20) %>% 
  mutate(token = reorder(token, n)) %>% 
  ggplot(aes(token, n)) + 
  geom_col(fill = "steelblue") + 
  coord_flip() + 
  labs(title = "Top 20 Most Frequent Words in Australia Dataset", 
       x = "Word", y = "Frequency") +
  theme_minimal()

save_plot(p1, "word_frequency_plot.png")

# Create word cloud
png(file.path(images_dir, "wordcloud_basic.png"), width = 800, height = 600, bg = "white")
wordcloud(words = word_freq$token, 
          freq = word_freq$n, 
          max.words = 100, 
          random.order = FALSE, 
          rot.per = 0.35, 
          colors = brewer.pal(8, "Dark2"))
dev.off()

# Create word cloud with different colors
png(file.path(images_dir, "wordcloud_colors.png"), width = 800, height = 600, bg = "white")
wordcloud(words = word_freq$token, 
          freq = word_freq$n, 
          max.words = 100, 
          random.order = FALSE, 
          rot.per = 0.35, 
          colors = brewer.pal(8, "Set2"))
dev.off()

# Category-wise analysis
category_freq <- tokens_filtered %>% 
  count(category, token, sort = TRUE) %>% 
  group_by(category) %>% 
  slice_max(n, n = 10) %>% 
  ungroup()

# Top words by category
p2 <- category_freq %>% 
  mutate(token = reorder_within(token, n, category)) %>% 
  ggplot(aes(token, n, fill = category)) + 
  geom_col(show.legend = FALSE) + 
  facet_wrap(~ category, scales = "free_y") + 
  scale_x_reordered() + 
  coord_flip() + 
  labs(title = "Top Words by Category", 
       x = "Word", y = "Frequency") +
  theme_minimal()

save_plot(p2, "word_frequency_by_category.png")

# Create word clouds for each category
categories <- unique(docs$category)
for (cat in categories) {
  cat_words <- tokens_filtered %>% 
    filter(category == cat) %>% 
    count(token, sort = TRUE)
  
  if (nrow(cat_words) > 0) {
    filename <- paste0("wordcloud_", tolower(gsub(" ", "_", cat)), ".png")
    png(file.path(images_dir, filename), width = 800, height = 600, bg = "white")
    wordcloud(words = cat_words$token, 
              freq = cat_words$n, 
              max.words = 50, 
              random.order = FALSE, 
              rot.per = 0.35, 
              colors = brewer.pal(8, "Dark2"),
              main = paste("Word Cloud for", cat))
    dev.off()
  }
}

# Bigram analysis
bigrams <- docs %>% 
  mutate(text_lower = str_to_lower(text)) %>% 
  unnest_tokens(bigram, text_lower, token = "ngrams", n = 2)

# Clean bigrams
bigrams_clean <- bigrams %>% 
  separate(bigram, c("w1", "w2"), sep = " ", fill = "right", remove = FALSE) %>% 
  filter(!is.na(w2)) %>% 
  anti_join(stop_words, by = c("w1" = "word")) %>% 
  anti_join(stop_words, by = c("w2" = "word")) %>% 
  count(bigram, sort = TRUE)

# Top bigrams plot
p3 <- bigrams_clean %>% 
  slice_max(n, n = 20) %>% 
  mutate(bigram = reorder(bigram, n)) %>% 
  ggplot(aes(bigram, n)) + 
  geom_col(fill = "seagreen") + 
  coord_flip() + 
  labs(title = "Top 20 Bigrams in Australia Dataset", 
       x = "Bigram", y = "Frequency") +
  theme_minimal()

save_plot(p3, "bigram_frequency_plot.png")

# Create bigram word cloud
png(file.path(images_dir, "bigram_wordcloud.png"), width = 800, height = 600, bg = "white")
wordcloud(words = bigrams_clean$bigram, 
          freq = bigrams_clean$n, 
          max.words = 50, 
          random.order = FALSE, 
          rot.per = 0.35, 
          colors = brewer.pal(8, "Dark2"))
dev.off()

# Document-term matrix for analysis
dtm <- tokens_filtered %>% 
  count(doc_id, token) %>% 
  cast_dtm(document = doc_id, term = token, value = n)

# Summary statistics
cat("\nDataset Summary:\n")
cat("Total documents:", nrow(docs), "\n")
cat("Total unique words:", nrow(word_freq), "\n")
cat("Total bigrams:", nrow(bigrams_clean), "\n")
cat("Document-term matrix dimensions:", dim(dtm), "\n")

# Save word frequency data
write_csv(word_freq, file.path(images_dir, "word_frequencies.csv"))
write_csv(bigrams_clean, file.path(images_dir, "bigram_frequencies.csv"))
write_csv(category_freq, file.path(images_dir, "category_word_frequencies.csv"))

message("WordCloud analysis completed. Outputs written to ", normalizePath(images_dir))
