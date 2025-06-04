## Eclat Algorithm Implementation
# This notebook demonstrates the implementation of the Eclat algorithm for association rule mining.

## 1. Import Required Libraries
import numpy as np
import pandas as pd
from collections import defaultdict
from itertools import combinations
import seaborn as sns
import matplotlib.pyplot as plt
import os

# Set random seed for reproducibility
np.random.seed(2220)

# Create output directory for visuals
output_dir = 'algorithms/association_rules/eclat'
os.makedirs(output_dir, exist_ok=True)

## 2. Helper Functions for Eclat Implementation

def convert_to_vertical(transactions):
    """
    Convert horizontal format to vertical format.
    
    Args:
        transactions (list): List of transactions in horizontal format
        
    Returns:
        dict: Dictionary mapping items to their transaction IDs
    """
    vertical_format = defaultdict(set)
    for i, transaction in enumerate(transactions):
        for item in transaction:
            vertical_format[item].add(i)
    return vertical_format

def get_frequent_itemsets(vertical_format, n_transactions, min_support):
    """
    Find frequent itemsets using vertical format.
    
    Args:
        vertical_format (dict): Dictionary mapping items to their transaction IDs
        n_transactions (int): Total number of transactions
        min_support (float): Minimum support threshold
        
    Returns:
        dict: Dictionary of frequent itemsets with their support and transaction IDs
    """
    frequent_itemsets = {}
    
    # Find frequent 1-itemsets
    for item, tids in vertical_format.items():
        support = len(tids) / n_transactions
        if support >= min_support:
            frequent_itemsets[frozenset([item])] = (support, tids)
    
    # Generate k-itemsets
    k = 1
    while True:
        k += 1
        candidates = generate_candidates(frequent_itemsets, k)
        if not candidates:
            break
            
        for itemset in candidates:
            # Intersect transaction IDs
            tids = set.intersection(*[vertical_format[item] 
                                    for item in itemset])
            support = len(tids) / n_transactions
            
            if support >= min_support:
                frequent_itemsets[itemset] = (support, tids)
    
    return frequent_itemsets

def generate_candidates(frequent_itemsets, k):
    """
    Generate candidate k-itemsets.
    
    Args:
        frequent_itemsets (dict): Dictionary of frequent itemsets
        k (int): Size of itemsets to generate
        
    Returns:
        set: Set of candidate k-itemsets
    """
    candidates = set()
    items = set()
    for itemset in frequent_itemsets:
        items.update(itemset)
    
    for itemset in combinations(items, k):
        itemset = frozenset(itemset)
        # Check if all (k-1)-subsets are frequent
        if all(frozenset(subset) in frequent_itemsets 
              for subset in combinations(itemset, k-1)):
            candidates.add(itemset)
    
    return candidates

def generate_rules(frequent_itemsets, min_confidence, min_lift):
    """
    Generate association rules from frequent itemsets.
    
    Args:
        frequent_itemsets (dict): Dictionary of frequent itemsets
        min_confidence (float): Minimum confidence threshold
        min_lift (float): Minimum lift threshold
        
    Returns:
        list: List of association rules
    """
    rules = []
    for itemset, (support, _) in frequent_itemsets.items():
        if len(itemset) < 2:
            continue
            
        for i in range(1, len(itemset)):
            for antecedent in combinations(itemset, i):
                antecedent = frozenset(antecedent)
                consequent = itemset - antecedent
                
                # Calculate confidence
                ant_support = frequent_itemsets[antecedent][0]
                confidence = support / ant_support
                
                # Calculate lift
                cons_support = frequent_itemsets[consequent][0]
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

def fit_eclat(transactions, min_support=0.1, min_confidence=0.5, min_lift=1.0):
    """
    Find frequent itemsets and generate rules using Eclat algorithm.
    
    Args:
        transactions (list): List of transactions
        min_support (float): Minimum support threshold
        min_confidence (float): Minimum confidence threshold
        min_lift (float): Minimum lift threshold
        
    Returns:
        tuple: (frequent_itemsets, rules)
    """
    # Convert to vertical format
    vertical_format = convert_to_vertical(transactions)
    
    # Find frequent itemsets
    frequent_itemsets = get_frequent_itemsets(
        vertical_format, len(transactions), min_support)
    
    # Generate rules
    rules = generate_rules(frequent_itemsets, min_confidence, min_lift)
    
    return frequent_itemsets, rules

## 3. Visualization Functions

def plot_support_confidence(rules_df, title="Support vs Confidence"):
    """
    Plot support vs confidence scatter plot using seaborn.
    
    Args:
        rules_df (DataFrame): DataFrame containing rules
        title (str): Plot title
    """
    plt.figure(figsize=(10, 8))
    sns.scatterplot(data=rules_df, x='support', y='confidence', 
                   size='lift', hue='lift', sizes=(50, 200))
    plt.title(title)
    plt.xlabel('Support')
    plt.ylabel('Confidence')
    plt.savefig(f'{output_dir}/eclat-support-confidence.png')
    plt.close()

def plot_top_rules(rules_df, n=10, metric='lift'):
    """
    Plot top N rules by specified metric using seaborn.
    
    Args:
        rules_df (DataFrame): DataFrame containing rules
        n (int): Number of top rules to plot
        metric (str): Metric to sort rules by
    """
    top_rules = rules_df.nlargest(n, metric)
    
    plt.figure(figsize=(12, 6))
    sns.barplot(data=top_rules, x=metric, y='antecedent')
    plt.title(f'Top {n} Rules by {metric.capitalize()}')
    plt.xlabel(metric.capitalize())
    plt.ylabel('Antecedent')
    plt.savefig(f'{output_dir}/eclat-top-rules.png')
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
print("\nTraining Eclat Algorithm...")
frequent_itemsets, rules = fit_eclat(
    transactions,
    min_support=0.2,
    min_confidence=0.5,
    min_lift=1.0
)

# Convert rules to DataFrame for visualization
rules_df = pd.DataFrame(rules)
rules_df['antecedent'] = rules_df['antecedent'].apply(lambda x: ', '.join(sorted(x)))
rules_df['consequent'] = rules_df['consequent'].apply(lambda x: ', '.join(sorted(x)))

# Print results
print("\nFrequent Itemsets:")
for itemset, (support, _) in frequent_itemsets.items():
    print(f"{itemset}: {support:.3f}")

print("\nAssociation Rules:")
for rule in rules:
    print(f"{rule['antecedent']} -> {rule['consequent']}")
    print(f"Support: {rule['support']:.3f}, Confidence: {rule['confidence']:.3f}, Lift: {rule['lift']:.3f}")
    print()

# Plot results
plot_support_confidence(rules_df, "Support vs Confidence")
plot_top_rules(rules_df, n=5, metric='lift')
plot_top_rules(rules_df, n=5, metric='confidence')