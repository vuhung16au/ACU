#!/usr/bin/env Rscript

suppressPackageStartupMessages({
  `%||%` <- function(a, b) if (!is.null(a) && !is.na(a) && a != "") a else b
  if (!requireNamespace("tidyverse", quietly = TRUE)) install.packages("tidyverse", repos = "https://cloud.r-project.org")
  if (!requireNamespace("tidytext", quietly = TRUE)) install.packages("tidytext", repos = "https://cloud.r-project.org")
  if (!requireNamespace("tm", quietly = TRUE)) install.packages("tm", repos = "https://cloud.r-project.org")
  if (!requireNamespace("SnowballC", quietly = TRUE)) install.packages("SnowballC", repos = "https://cloud.r-project.org")
  if (!requireNamespace("textstem", quietly = TRUE)) install.packages("textstem", repos = "https://cloud.r-project.org")
  if (!requireNamespace("e1071", quietly = TRUE)) install.packages("e1071", repos = "https://cloud.r-project.org")
})

suppressPackageStartupMessages({
  library(tidyverse)
  library(tidytext)
  library(tm)
  library(SnowballC)
  library(textstem)
  library(e1071)
})

# Resolve this script's directory so outputs are saved next to it
args_all <- commandArgs(trailingOnly = FALSE)
script_arg <- grep("^--file=", args_all, value = TRUE)
script_path <- if (length(script_arg) > 0) sub("^--file=", "", script_arg[1]) else NA_character_
script_path <- tryCatch(normalizePath(script_path), error = function(e) NA_character_)
if (is.na(script_path) || script_path == "") script_path <- tryCatch(normalizePath("09-01-Text-Mining/Text-Mining.R"), error = function(e) getwd())
base_dir <- dirname(script_path)

dataset_dir <- file.path(base_dir, "2016-US-Presidential-Race-Dataset")
images_dir <- file.path(base_dir, "images")
if (!dir.exists(images_dir)) dir.create(images_dir, recursive = TRUE)

save_plot <- function(p, filename, width = 10, height = 6) {
  p_out <- p + theme_bw() + theme(plot.background = element_rect(fill = "white", color = NA), panel.background = element_rect(fill = "white"))
  ggsave(filename = file.path(images_dir, filename), plot = p_out, width = width, height = height, dpi = 150, bg = "white")
}

read_texts <- function(root_dir) {
  subdirs <- c(
    file.path(root_dir, "speeches", "clinton"),
    file.path(root_dir, "speeches", "trump"),
    file.path(root_dir, "debates"),
    file.path(root_dir, "primaries", "clinton"),
    file.path(root_dir, "primaries", "sanders"),
    file.path(root_dir, "primaries", "trump")
  )
  files <- unlist(lapply(subdirs, function(d) if (dir.exists(d)) list.files(d, pattern = "\\.txt$", full.names = TRUE) else character()))
  if (length(files) == 0) stop("No text files found under dataset folders.")
  tibble(
    doc_id = basename(files),
    path = files,
    category = case_when(
      grepl("/speeches/", files) ~ "speech",
      grepl("/debates/", files) ~ "debate",
      grepl("/primaries/", files) ~ "primary",
      TRUE ~ "other"
    ),
    candidate = case_when(
      grepl("clinton", tolower(files)) ~ "Clinton",
      grepl("trump", tolower(files)) ~ "Trump",
      grepl("sanders", tolower(files)) ~ "Sanders",
      TRUE ~ "Unknown"
    ),
    text = map_chr(files, ~ paste(readLines(.x, warn = FALSE, encoding = "UTF-8"), collapse = " \n "))
  )
}

docs <- read_texts(dataset_dir)

tokens <- docs %>% mutate(text_lower = str_to_lower(text)) %>% unnest_tokens(token, text_lower)
tokens_filtered <- tokens %>% anti_join(stop_words, by = c("token" = "word")) %>% filter(str_detect(token, "^[a-z]+$"), nchar(token) >= 3)

top_terms <- tokens_filtered %>% count(candidate, token, sort = TRUE) %>% group_by(candidate) %>% slice_max(n, n = 15) %>% ungroup()

p1 <- top_terms %>% mutate(token = tidytext::reorder_within(token, n, candidate)) %>% ggplot(aes(token, n, fill = candidate)) + geom_col(show.legend = FALSE) + facet_wrap(~ candidate, scales = "free_y") + tidytext::scale_x_reordered() + labs(title = "Top tokens per candidate", x = "Token", y = "Frequency") + coord_flip()
save_plot(p1, "top_tokens_per_candidate.png")

bigrams <- docs %>% mutate(text_lower = str_to_lower(text)) %>% unnest_tokens(bigram, text_lower, token = "ngrams", n = 2)
trigrams <- docs %>% mutate(text_lower = str_to_lower(text)) %>% unnest_tokens(trigram, text_lower, token = "ngrams", n = 3)

library(tidyr)
p2 <- bigrams %>% separate(bigram, c("w1", "w2"), sep = " ", fill = "right", remove = FALSE) %>% filter(!is.na(w2)) %>% anti_join(stop_words, by = c("w1" = "word")) %>% anti_join(stop_words, by = c("w2" = "word")) %>% count(bigram, sort = TRUE) %>% slice_max(n, n = 20) %>% mutate(bigram = reorder(bigram, n)) %>% ggplot(aes(bigram, n)) + geom_col(fill = "steelblue") + coord_flip() + labs(title = "Top bigrams")
save_plot(p2, "top_bigrams.png")

p3 <- trigrams %>% separate(trigram, c("w1", "w2", "w3"), sep = " ", fill = "right", remove = FALSE) %>% filter(!is.na(w3)) %>% anti_join(stop_words, by = c("w1" = "word")) %>% anti_join(stop_words, by = c("w2" = "word")) %>% anti_join(stop_words, by = c("w3" = "word")) %>% count(trigram, sort = TRUE) %>% slice_max(n, n = 20) %>% mutate(trigram = reorder(trigram, n)) %>% ggplot(aes(trigram, n)) + geom_col(fill = "seagreen") + coord_flip() + labs(title = "Top trigrams")
save_plot(p3, "top_trigrams.png")

dtm <- tokens_filtered %>% count(doc_id, token) %>% tidytext::cast_dtm(document = doc_id, term = token, value = n)
docs_tbl <- tibble(doc_id = rownames(as.matrix(dtm))) %>% left_join(tokens_filtered %>% distinct(doc_id, candidate), by = "doc_id")
set.seed(42)
idx <- sample.int(nrow(as.matrix(dtm)), size = max(1, floor(0.8 * nrow(as.matrix(dtm)))))
x_train <- as.matrix(dtm)[idx, , drop = FALSE]
x_test  <- as.matrix(dtm)[-idx, , drop = FALSE]
y_train <- as.factor(docs_tbl$candidate[idx])
y_test  <- as.factor(docs_tbl$candidate[-idx])
if (length(unique(y_train)) >= 2 && nrow(x_test) > 0) {
  model <- e1071::naiveBayes(x = x_train, y = y_train, laplace = 1)
  preds <- predict(model, x_test)
  acc <- mean(as.character(preds) == as.character(y_test))
  readr::write_csv(tibble(metric = "accuracy", value = acc), file.path(images_dir, "classification_metrics.csv"))
}

message("Done. Outputs written to ", normalizePath(images_dir))


