## Overview

This document demonstrates basic text mining in R using speeches,
debates, and primary texts from the 2016 U.S. Presidential race. It
mirrors the concepts from the reference notebook and covers:

- Tokenization, stopword filtering, case normalization
- Minimum-length filtering, stemming, lemmatization
- N-gram construction (bigrams, trigrams)
- Token replacement (synonym normalization)
- Document-term matrix and a simple text classification demo

We also briefly explain what text mining is, common formats, and typical
NLP operators.

## Libraries and Data

``` r
pkgs <- c("tidyverse", "tidytext", "tm", "SnowballC", "textstem", "quanteda", "here")
for (p in pkgs) if (!requireNamespace(p, quietly = TRUE)) install.packages(p, repos = "https://cloud.r-project.org")

library(tidyverse)
library(tidytext)
library(tm)
library(SnowballC)
library(textstem)
library(quanteda)
library(here)
```

``` r
# Robust dataset path resolution for multiple render locations
candidates <- c(
  tryCatch(here::here("09-01-Text-Mining", "2016-US-Presidential-Race-Dataset"), error = function(e) NA_character_),
  file.path("09-01-Text-Mining", "2016-US-Presidential-Race-Dataset"),
  "2016-US-Presidential-Race-Dataset",
  file.path("..", "09-01-Text-Mining", "2016-US-Presidential-Race-Dataset")
)
root_dir <- candidates[which(dir.exists(candidates))][1]
stopifnot(!is.na(root_dir))

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
  stopifnot(length(files) > 0)
  tibble(
    doc_id = basename(files),
    path = files,
    category = dplyr::case_when(
      grepl("/speeches/", files) ~ "speech",
      grepl("/debates/", files) ~ "debate",
      grepl("/primaries/", files) ~ "primary",
      TRUE ~ "other"
    ),
    candidate = dplyr::case_when(
      grepl("clinton", tolower(files)) ~ "Clinton",
      grepl("trump", tolower(files)) ~ "Trump",
      grepl("sanders", tolower(files)) ~ "Sanders",
      TRUE ~ "Unknown"
    ),
    text = purrr::map_chr(files, ~ paste(readLines(.x, warn = FALSE, encoding = "UTF-8"), collapse = " \n "))
  )
}

docs <- read_texts(root_dir)
```

## Tokenization, Stopwords, Case, Filtering

``` r
tokens <- docs %>% mutate(text_lower = stringr::str_to_lower(text)) %>% tidytext::unnest_tokens(token, text_lower)
tokens_filtered <- tokens %>% anti_join(tidytext::stop_words, by = c("token" = "word")) %>% filter(stringr::str_detect(token, "^[a-z]+$"), nchar(token) >= 3)
tokens_filtered %>% count(token, sort = TRUE) %>% head(10)
```

    ## # A tibble: 10 × 2
    ##    token         n
    ##    <chr>     <int>
    ##  1 people     2359
    ##  2 country    1669
    ##  3 american   1209
    ##  4 jobs       1072
    ##  5 america    1040
    ##  6 hillary     956
    ##  7 clinton     921
    ##  8 president   781
    ##  9 time        689
    ## 10 world       591

## Stemming and Lemmatization

``` r
tokens_stemmed <- tokens_filtered %>% mutate(stem = SnowballC::wordStem(token, language = "en"))
tokens_lemma   <- tokens_filtered %>% mutate(lemma = textstem::lemmatize_words(token))
```

## N-grams and Visualization

``` r
# Resolve module directory from this file location; save images under <module>/images
this_file <- knitr::current_input()
module_dir <- dirname(normalizePath(this_file))
img_dir <- file.path(module_dir, "images")
dir.create(img_dir, showWarnings = FALSE, recursive = TRUE)
save_plot <- function(p, filename, width = 10, height = 6) {
  p_out <- p + theme_bw() + theme(plot.background = element_rect(fill = "white", color = NA), panel.background = element_rect(fill = "white"))
  ggsave(filename = file.path(img_dir, filename), plot = p_out, width = width, height = height, dpi = 150, bg = "white")
}

top_terms <- tokens_filtered %>% count(candidate, token, sort = TRUE) %>% group_by(candidate) %>% slice_max(n, n = 15) %>% ungroup()
p1 <- top_terms %>% mutate(token = tidytext::reorder_within(token, n, candidate)) %>% ggplot(aes(token, n, fill = candidate)) + geom_col(show.legend = FALSE) + facet_wrap(~ candidate, scales = "free_y") + tidytext::scale_x_reordered() + labs(title = "Top tokens per candidate", x = "Token", y = "Frequency") + coord_flip()
save_plot(p1, "top_tokens_per_candidate.png")

bigrams <- docs %>% mutate(text_lower = stringr::str_to_lower(text)) %>% tidytext::unnest_tokens(bigram, text_lower, token = "ngrams", n = 2)
trigrams <- docs %>% mutate(text_lower = stringr::str_to_lower(text)) %>% tidytext::unnest_tokens(trigram, text_lower, token = "ngrams", n = 3)

p2 <- bigrams %>% tidyr::separate(bigram, c("w1", "w2"), sep = " ", fill = "right", remove = FALSE) %>% filter(!is.na(w2)) %>% anti_join(tidytext::stop_words, by = c("w1" = "word")) %>% anti_join(tidytext::stop_words, by = c("w2" = "word")) %>% count(bigram, sort = TRUE) %>% slice_max(n, n = 20) %>% mutate(bigram = reorder(bigram, n)) %>% ggplot(aes(bigram, n)) + geom_col(fill = "steelblue") + coord_flip() + labs(title = "Top bigrams")
save_plot(p2, "top_bigrams.png")

p3 <- trigrams %>% tidyr::separate(trigram, c("w1", "w2", "w3"), sep = " ", fill = "right", remove = FALSE) %>% filter(!is.na(w3)) %>% anti_join(tidytext::stop_words, by = c("w1" = "word")) %>% anti_join(tidytext::stop_words, by = c("w2" = "word")) %>% anti_join(tidytext::stop_words, by = c("w3" = "word")) %>% count(trigram, sort = TRUE) %>% slice_max(n, n = 20) %>% mutate(trigram = reorder(trigram, n)) %>% ggplot(aes(trigram, n)) + geom_col(fill = "seagreen") + coord_flip() + labs(title = "Top trigrams")
save_plot(p3, "top_trigrams.png")
```

## Replace Tokens (Substitution)

``` r
demo <- tibble(text = c("This product is a great device", "The item is similar to that product"))
mapping <- c("product" = "device", "item" = "device")
demo %>% tidytext::unnest_tokens(token, text) %>% mutate(token_replaced = if_else(token %in% names(mapping), mapping[token], token))
```

    ## # A tibble: 13 × 2
    ##    token   token_replaced
    ##    <chr>   <chr>         
    ##  1 this    this          
    ##  2 product device        
    ##  3 is      is            
    ##  4 a       a             
    ##  5 great   great         
    ##  6 device  device        
    ##  7 the     the           
    ##  8 item    device        
    ##  9 is      is            
    ## 10 similar similar       
    ## 11 to      to            
    ## 12 that    that          
    ## 13 product device

## Document-Term Matrix and Classification Demo

``` r
dtm <- tokens_filtered %>% count(doc_id, token) %>% tidytext::cast_dtm(document = doc_id, term = token, value = n)
docs_tbl <- tibble(doc_id = rownames(as.matrix(dtm))) %>% left_join(tokens_filtered %>% distinct(doc_id, candidate), by = "doc_id")
set.seed(42)
idx <- sample.int(nrow(as.matrix(dtm)), size = max(1, floor(0.8 * nrow(as.matrix(dtm)))))
x_train <- as.matrix(dtm)[idx, , drop = FALSE]
x_test  <- as.matrix(dtm)[-idx, , drop = FALSE]
y_train <- as.factor(docs_tbl$candidate[idx])
y_test  <- as.factor(docs_tbl$candidate[-idx])
if (length(unique(y_train)) >= 2 && nrow(x_test) > 0) {
  if (!requireNamespace("e1071", quietly = TRUE)) install.packages("e1071", repos = "https://cloud.r-project.org")
  library(e1071)
  model <- e1071::naiveBayes(x = x_train, y = y_train, laplace = 1)
  preds <- predict(model, x_test)
  mean(as.character(preds) == as.character(y_test))
}
```

    ## [1] 0
