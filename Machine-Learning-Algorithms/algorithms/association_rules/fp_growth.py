#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Simplified FP-Growth Example (Demo Version)
This version is minimal and runs instantly for demonstration purposes.
"""
from itertools import combinations

# Tiny sample transaction data
transactions = [
    ['milk', 'bread', 'butter'],
    ['bread', 'butter'],
    ['milk', 'butter'],
    ['bread', 'milk'],
    ['milk', 'bread', 'butter'],
    ['bread', 'butter'],
    ['milk', 'bread'],
]

# Parameters for demo
min_support = 0.5  # 50% support
min_confidence = 0.5

# Count item frequencies
item_counts = {}
for transaction in transactions:
    for item in transaction:
        item_counts[item] = item_counts.get(item, 0) + 1
n_transactions = len(transactions)

# Find frequent 1-itemsets
frequent_items = {item for item, count in item_counts.items() if count / n_transactions >= min_support}

# Find frequent 2-itemsets
pair_counts = {}
for transaction in transactions:
    for pair in combinations(sorted(set(transaction) & frequent_items), 2):
        pair_counts[pair] = pair_counts.get(pair, 0) + 1
frequent_pairs = {pair: count for pair, count in pair_counts.items() if count / n_transactions >= min_support}

# Print frequent itemsets
print("Frequent 1-itemsets:")
for item in frequent_items:
    print(f"{item}")
print("\nFrequent 2-itemsets:")
for pair, count in frequent_pairs.items():
    print(f"{pair}: support={count / n_transactions:.2f}")

# Generate simple rules from frequent pairs
print("\nAssociation Rules:")
for (a, b), count in frequent_pairs.items():
    support = count / n_transactions
    conf_ab = count / item_counts[a]
    conf_ba = count / item_counts[b]
    if conf_ab >= min_confidence:
        print(f"{a} -> {b}: support={support:.2f}, confidence={conf_ab:.2f}")
    if conf_ba >= min_confidence:
        print(f"{b} -> {a}: support={support:.2f}, confidence={conf_ba:.2f}")