#!/usr/bin/env python3
# -*- coding: utf-8 -*-

## Apriori Algorithm Implementation
# This notebook demonstrates the implementation of the Apriori algorithm for association rule mining.

## 1. Import Required Libraries
import numpy as np
import pandas as pd
from itertools import combinations
from collections import defaultdict
import seaborn as sns
import matplotlib.pyplot as plt
import os

# Set random seed for reproducibility
np.random.seed(2220)

## 2. Helper Functions for Apriori Implementation

def generate_candidates(items, k):
    """
    Generate candidate k-itemsets from (k-1)-itemsets.
    
    Args:
        items (set): Set of unique items
        k (int): Size of itemsets to generate
        
    Returns:
        list: List of candidate k-itemsets
    """
    return list(combinations(items, k))

def get_frequent_itemsets(transactions, items, k, min_support):
    """
    Find frequent k-itemsets.
    
    Args:
        transactions (list): List of transactions
        items (set): Set of unique items
        k (int): Size of itemsets to find
        min_support (float): Minimum support threshold
        
    Returns:
        dict: Dictionary of frequent itemsets and their support values
    """
    # Count itemset occurrences
    itemset_counts = defaultdict(int)
    for transaction in transactions:
        for itemset in combinations(transaction, k):
            if all(item in items for item in itemset):
                itemset_counts[itemset] += 1
    
    # Filter by minimum support
    n_transactions = len(transactions)
    return {itemset: count/n_transactions 
            for itemset, count in itemset_counts.items() 
            if count/n_transactions >= min_support}

def generate_rules(itemset, support, frequent_itemsets, min_confidence, min_lift):
    """
    Generate association rules from frequent itemset.
    
    Args:
        itemset (frozenset): Frequent itemset
        support (float): Support of the itemset
        frequent_itemsets (dict): Dictionary of frequent itemsets
        min_confidence (float): Minimum confidence threshold
        min_lift (float): Minimum lift threshold
        
    Returns:
        list: List of association rules
    """
    rules = []
    for i in range(1, len(itemset)):
        for antecedent in combinations(itemset, i):
            antecedent = frozenset(antecedent)
            consequent = frozenset(itemset) - antecedent
            # Only proceed if both antecedent and consequent are in frequent_itemsets
            if antecedent not in frequent_itemsets or consequent not in frequent_itemsets:
                continue
            # Calculate confidence
            ant_support = frequent_itemsets[antecedent]
            confidence = support / ant_support
            # Calculate lift
            cons_support = frequent_itemsets[consequent]
            lift = confidence / cons_support
            if confidence >= min_confidence and lift >= min_lift:
                rules.append({
                    'antecedent': antecedent,
                    'consequent': consequent,
                    'support': support,
                    'confidence': confidence,
                    'lift': lift
                })
    return rules

def fit_apriori(transactions, min_support=0.1, min_confidence=0.5, min_lift=1.0):
    """
    Find frequent itemsets and generate rules using Apriori algorithm.
    
    Args:
        transactions (list): List of transactions
        min_support (float): Minimum support threshold
        min_confidence (float): Minimum confidence threshold
        min_lift (float): Minimum lift threshold
        
    Returns:
        tuple: (frequent_itemsets, rules)
    """
    # Convert transactions to sets
    transactions = [set(transaction) for transaction in transactions]
    
    # Get unique items
    items = set()
    for transaction in transactions:
        items.update(transaction)
    
    # Find frequent 1-itemsets
    k = 1
    frequent_itemsets = get_frequent_itemsets(transactions, items, k, min_support)
    
    # Find frequent k-itemsets
    while True:
        k += 1
        candidates = generate_candidates(items, k)
        if not candidates:
            break
            
        frequent_k_itemsets = get_frequent_itemsets(transactions, items, k, min_support)
        if not frequent_k_itemsets:
            break
            
        frequent_itemsets.update(frequent_k_itemsets)
    
    # Generate rules
    rules = []
    for itemset, support in frequent_itemsets.items():
        if len(itemset) > 1:
            rules.extend(generate_rules(itemset, support, frequent_itemsets, 
                                      min_confidence, min_lift))
    
    return frequent_itemsets, rules

## 3. Visualization Functions

def plot_support_confidence(rules_df, title="Support vs Confidence"):
    """
    Plot support vs confidence scatter plot using seaborn and save to PNG file.
    
    Args:
        rules_df (DataFrame): DataFrame containing rules
        title (str): Plot title
    """
    # Create directory if it doesn't exist
    os.makedirs('algorithms/association_rules/apriori', exist_ok=True)
    
    plt.figure(figsize=(10, 8))
    sns.scatterplot(data=rules_df, x='support', y='confidence', 
                   size='lift', hue='lift', sizes=(50, 200))
    plt.title(title)
    plt.xlabel('Support')
    plt.ylabel('Confidence')
    
    # Save plot
    filename = f'algorithms/association_rules/apriori/apriori-{title.lower().replace(" ", "_")}.png'
    plt.savefig(filename)
    plt.close()

def plot_top_rules(rules_df, n=10, metric='lift'):
    """
    Plot top N rules by specified metric using seaborn and save to PNG file.
    
    Args:
        rules_df (DataFrame): DataFrame containing rules
        n (int): Number of top rules to plot
        metric (str): Metric to sort rules by
    """
    # Create directory if it doesn't exist
    os.makedirs('algorithms/association_rules/apriori', exist_ok=True)
    
    top_rules = rules_df.nlargest(n, metric)
    
    plt.figure(figsize=(12, 6))
    sns.barplot(data=top_rules, x=metric, y='antecedent')
    plt.title(f'Top {n} Rules by {metric.capitalize()}')
    plt.xlabel(metric.capitalize())
    plt.ylabel('Antecedent')
    
    # Save plot
    filename = f'algorithms/association_rules/apriori/apriori-top_{n}_rules_by_{metric}.png'
    plt.savefig(filename)
    plt.close()

## 4. Model Training and Evaluation

# Run code sequentially
# Sample transaction data
transactions = [
    ['milk', 'bread', 'butter'],
    ['bread', 'diapers'],
    ['milk', 'diapers', 'beer'],
    ['milk', 'bread', 'diapers'],
    ['bread', 'diapers', 'beer'],
    ['milk', 'bread', 'diapers', 'beer'],
    ['bread', 'milk']
]

# Hyperparameters explained:
# - min_support: Minimum support threshold (0.2 means itemset must appear in at least 20% of transactions)
# - min_confidence: Minimum confidence threshold (0.5 means rule must be correct at least 50% of the time)
# - min_lift: Minimum lift threshold (1.0 means rule must be at least as good as random)
print("\nTraining Apriori Algorithm...")
frequent_itemsets, rules = fit_apriori(
    transactions,
    min_support=0.2,
    min_confidence=0.5,
    min_lift=1.0
)

# Convert rules to DataFrame for visualization
rules_df = pd.DataFrame(rules)
if not rules_df.empty:
    rules_df['antecedent'] = rules_df['antecedent'].apply(lambda x: ', '.join(sorted(x)))
    rules_df['consequent'] = rules_df['consequent'].apply(lambda x: ', '.join(sorted(x)))
else:
    print("No association rules found.")

# Print results
print("\nFrequent Itemsets:")
for itemset, support in frequent_itemsets.items():
    print(f"{itemset}: {support:.3f}")

print("\nAssociation Rules:")
for rule in rules:
    print(f"{rule['antecedent']} -> {rule['consequent']}")
    print(f"Support: {rule['support']:.3f}, Confidence: {rule['confidence']:.3f}, Lift: {rule['lift']:.3f}")
    print()

# Plot results
if not rules_df.empty:
    plot_support_confidence(rules_df, "Support vs Confidence")
    plot_top_rules(rules_df, n=5, metric='lift')
    plot_top_rules(rules_df, n=5, metric='confidence')