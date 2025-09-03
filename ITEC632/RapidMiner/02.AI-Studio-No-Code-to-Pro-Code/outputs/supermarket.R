# RapidMiner AI Studio 2025 equivalent in R (arules)
# Operators mirrored: Retrieve -> Set Role -> Numerical to Binominal -> FP-Growth -> Create Association Rules
# Dataset path (relative to project root): inputs/supermarket.csv

suppressPackageStartupMessages({
	if (!requireNamespace("arules", quietly = TRUE)) install.packages("arules", repos = "https://cloud.r-project.org")
	library(arules)
})

# 1) Load data (robust to comma- or tab-delimited)
read_supermarket <- function(path) {
	# Try CSV first; if it collapses to one column, try TSV
	df <- tryCatch(read.csv(path, check.names = TRUE, stringsAsFactors = FALSE), error = function(e) NULL)
	if (is.null(df) || ncol(df) == 1) {
		df <- read.delim(path, check.names = TRUE, stringsAsFactors = FALSE)
	}
	return(df)
}

input_path <- "inputs/supermarket.csv"
raw_df <- read_supermarket(input_path)
if (is.null(raw_df) || nrow(raw_df) == 0) stop("Failed to read inputs/supermarket.csv or file is empty")

# 2) Set Role: transaction id
if (!"receipt_id" %in% names(raw_df)) stop("Column 'receipt_id' not found in input data")
transaction_ids <- as.character(raw_df$receipt_id)

# 3) Numerical to Binominal: convert all product columns to logical (0/1 -> FALSE/TRUE)
items_df <- raw_df[setdiff(names(raw_df), "receipt_id")]
# Coerce any non-numeric to numeric where possible
suppressWarnings({ items_df[] <- lapply(items_df, function(col) as.numeric(col)) })
# Threshold at > 0 as TRUE
items_df[is.na(items_df)] <- 0
items_df[] <- lapply(items_df, function(col) col > 0)

# 4) Build transactions from dummy-coded columns
trans <- as(items_df, "transactions")
# Attach transaction IDs
transactionInfo(trans)$transactionID <- transaction_ids

# Helper: mine frequent itemsets using Eclat (FP-Growth analogue) at given support
mine_itemsets <- function(transactions, supp = 0.2, maxlen = 0) {
	params <- list(supp = supp)
	if (maxlen > 0) params$maxlen <- maxlen
	fi <- eclat(transactions, parameter = params)
	return(fi)
}

# Helper: create rules from frequent itemsets (mirrors Create Association Rules)
create_rules_from_itemsets <- function(itemsets, confidence = 0.75) {
	# ruleInduction derives rules from precomputed itemsets
	rules <- ruleInduction(itemsets, confidence = confidence)
	# compute additional interest measures
	quality(rules)$lift <- interestMeasure(rules, measure = "lift", transactions = trans)
	quality(rules)$leverage <- interestMeasure(rules, measure = "leverage", transactions = trans)
	return(rules)
}

# 5) Tuning ranges akin to Altair settings
min_support_candidates <- c(0.2, 0.15, 0.1)  # try higher to lower
min_confidence_default <- 0.75

chosen_itemsets <- NULL
chosen_support <- NA_real_
for (s in min_support_candidates) {
	fis <- mine_itemsets(trans, supp = s, maxlen = 0)
	if (length(fis) > 0) {
		chosen_itemsets <- fis
		chosen_support <- s
		break
	}
}
if (is.null(chosen_itemsets)) {
	# last resort: very low support (may be heavy on 100K rows)
	chosen_support <- 0.05
	chosen_itemsets <- mine_itemsets(trans, supp = chosen_support, maxlen = 0)
}

rules <- create_rules_from_itemsets(chosen_itemsets, confidence = min_confidence_default)

# 6) Reporting helpers
summary_itemsets <- function(fis, top_n = 20) {
	if (length(fis) == 0) return(data.frame())
	df <- as(fis, "data.frame")
	# support is present in 'support' column
	df <- df[order(-df$support), , drop = FALSE]
	utils::head(df, top_n)
}

summary_rules <- function(rules, top_n = 20, sort_by = "lift") {
	if (length(rules) == 0) return(data.frame())
	df <- as(rules, "data.frame")
	# ensure lift present
	if (!"lift" %in% names(df)) df$lift <- interestMeasure(rules, measure = "lift", transactions = trans)
	df <- df[order(-df[[sort_by]]), , drop = FALSE]
	utils::head(df, top_n)
}

cat(sprintf("Chosen min_support: %.3f\n", chosen_support))
cat(sprintf("Used min_confidence: %.2f\n\n", min_confidence_default))

cat("Top frequent itemsets:\n")
print(summary_itemsets(chosen_itemsets, top_n = 20))

cat("\nTop rules by lift:\n")
print(summary_rules(rules, top_n = 20, sort_by = "lift"))

# 7) Optional: write CSV summaries alongside script output
out_dir <- "outputs"
if (!dir.exists(out_dir)) dir.create(out_dir, recursive = TRUE)
utils::write.csv(summary_itemsets(chosen_itemsets, 100), file = file.path(out_dir, "frequent_itemsets.csv"), row.names = FALSE)
utils::write.csv(summary_rules(rules, 100, sort_by = "lift"), file = file.path(out_dir, "association_rules.csv"), row.names = FALSE)

cat("\nSaved: outputs/frequent_itemsets.csv and outputs/association_rules.csv\n")
