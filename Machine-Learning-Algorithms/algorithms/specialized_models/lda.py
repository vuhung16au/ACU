#!/usr/bin/env python3
# -*- coding: utf-8 -*-

## Simplified Latent Dirichlet Allocation (LDA) Implementation
## This is a basic implementation of LDA for demonstration purposes.

import numpy as np
import matplotlib.pyplot as plt
from typing import List, Tuple, Dict
from collections import defaultdict

def initialize_counts(
    documents: List[List[str]],
    n_topics: int
) -> Tuple[Dict[str, int], Dict[int, str], np.ndarray, np.ndarray, np.ndarray, List[List[Tuple[int, int]]]]:
    """Initialize count matrices and vocabulary for LDA."""
    vocab = {word: idx for idx, word in enumerate(set(word for doc in documents for word in doc))}
    inverse_vocab = {idx: word for word, idx in vocab.items()}
    n_words = len(vocab)
    
    word_topic_counts = np.zeros((n_words, n_topics))
    doc_topic_counts = np.zeros((len(documents), n_topics))
    topic_counts = np.zeros(n_topics)
    
    word_topic_assignments = []
    for doc_idx, doc in enumerate(documents):
        doc_assignments = []
        for word in doc:
            word_idx = vocab[word]
            topic = np.random.randint(n_topics)
            doc_assignments.append((word_idx, topic))
            word_topic_counts[word_idx, topic] += 1
            doc_topic_counts[doc_idx, topic] += 1
            topic_counts[topic] += 1
        word_topic_assignments.append(doc_assignments)
    
    return vocab, inverse_vocab, word_topic_counts, doc_topic_counts, topic_counts, word_topic_assignments

def compute_topic_probability(
    word_idx: int,
    doc_idx: int,
    topic: int,
    n_words: int,
    word_topic_counts: np.ndarray,
    doc_topic_counts: np.ndarray,
    topic_counts: np.ndarray,
    n_topics: int,
    alpha: float = 0.1,
    beta: float = 0.1
) -> float:
    """Compute probability of a topic for a word in a document."""
    doc_topic_prob = (doc_topic_counts[doc_idx, topic] + alpha) / (
        doc_topic_counts[doc_idx].sum() + n_topics * alpha
    )
    
    word_topic_prob = (word_topic_counts[word_idx, topic] + beta) / (
        topic_counts[topic] + n_words * beta
    )
    
    return doc_topic_prob * word_topic_prob

def train_lda(
    documents: List[List[str]],
    n_topics: int,
    alpha: float = 0.1,
    beta: float = 0.1,
    max_iter: int = 20
) -> Tuple[Dict[str, int], Dict[int, str], np.ndarray, np.ndarray, np.ndarray]:
    """Train LDA model using collapsed Gibbs sampling."""
    vocab, inverse_vocab, word_topic_counts, doc_topic_counts, topic_counts, word_topic_assignments = initialize_counts(
        documents, n_topics
    )
    n_words = len(vocab)
    
    for iteration in range(max_iter):
        for doc_idx, doc_assignments in enumerate(word_topic_assignments):
            for word_pos, (word_idx, old_topic) in enumerate(doc_assignments):
                word_topic_counts[word_idx, old_topic] -= 1
                doc_topic_counts[doc_idx, old_topic] -= 1
                topic_counts[old_topic] -= 1
                
                topic_probs = np.array([
                    compute_topic_probability(
                        word_idx, doc_idx, topic, n_words,
                        word_topic_counts, doc_topic_counts, topic_counts,
                        n_topics, alpha, beta
                    )
                    for topic in range(n_topics)
                ])
                topic_probs /= topic_probs.sum()
                
                new_topic = np.random.choice(n_topics, p=topic_probs)
                word_topic_assignments[doc_idx][word_pos] = (word_idx, new_topic)
                word_topic_counts[word_idx, new_topic] += 1
                doc_topic_counts[doc_idx, new_topic] += 1
                topic_counts[new_topic] += 1
        
        if (iteration + 1) % 5 == 0:
            print(f"Iteration {iteration + 1}/{max_iter}")
    
    return vocab, inverse_vocab, word_topic_counts, doc_topic_counts, topic_counts

def get_top_words(
    word_topic_counts: np.ndarray,
    inverse_vocab: Dict[int, str],
    n_words: int = 5
) -> List[List[Tuple[str, float]]]:
    """Get top words for each topic."""
    topic_word_dist = word_topic_counts.T / word_topic_counts.sum(axis=0)[:, np.newaxis]
    top_words = []
    for topic in range(topic_word_dist.shape[0]):
        top_indices = np.argsort(topic_word_dist[topic])[-n_words:][::-1]
        topic_words = [
            (inverse_vocab[idx], topic_word_dist[topic, idx])
            for idx in top_indices
        ]
        top_words.append(topic_words)
    return top_words

def plot_top_words(top_words: List[List[Tuple[str, float]]]) -> None:
    """Plot top words for each topic."""
    n_topics = len(top_words)
    fig, axes = plt.subplots(1, n_topics, figsize=(4*n_topics, 4))
    
    for topic_idx, words in enumerate(top_words):
        words, probs = zip(*words)
        axes[topic_idx].barh(words, probs)
        axes[topic_idx].set_title(f'Topic {topic_idx + 1}')
        axes[topic_idx].set_xlabel('Probability')
    
    plt.tight_layout()
    plt.savefig('algorithms/specialized_models/lda-top-words.png')
    plt.close()

# Example usage with a small test dataset
if __name__ == "__main__":
    # Simple test dataset
    test_documents = [
        ['machine', 'learning', 'data', 'science'],
        ['python', 'code', 'software'],
        ['ai', 'neural', 'network']
    ]
    
    # Train LDA model
    n_topics = 2
    vocab, inverse_vocab, word_topic_counts, doc_topic_counts, topic_counts = train_lda(
        test_documents,
        n_topics=n_topics,
        max_iter=20
    )
    
    # Get and print top words
    top_words = get_top_words(word_topic_counts, inverse_vocab, n_words=3)
    print("\nTop words for each topic:")
    for topic_idx, words in enumerate(top_words):
        print(f"\nTopic {topic_idx + 1}:")
        for word, prob in words:
            print(f"  {word}: {prob:.3f}")
    
    # Plot results
    plot_top_words(top_words)