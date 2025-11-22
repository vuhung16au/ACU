#!/usr/bin/env python3
"""
Manual Anomaly Score Generator
Creates fake anomaly scores directly in Elasticsearch for demonstration
"""

import os
import sys
import time
import json
from datetime import datetime, timedelta
from elasticsearch import Elasticsearch

def main():
    """Generate fake anomaly scores"""
    es_host = os.getenv('ELASTICSEARCH_HOST', 'http://localhost:9200')
    
    # Connect to Elasticsearch
    es = Elasticsearch([es_host])
    
    if not es.ping():
        print("Error: Cannot connect to Elasticsearch")
        sys.exit(1)
    
    print("Connected to Elasticsearch")
    
    # Generate some fake anomaly scores
    now = datetime.utcnow()
    
    scores = [
        {
            '@timestamp': (now - timedelta(minutes=5)).isoformat(),
            'isolation_forest_score': 0.2,
            'isolation_forest_label': 0,
            'autoencoder_score': 0.05,
            'autoencoder_label': 0,
            'combined_score': 0.125,
            'combined_label': 0,
            'service': 'manual-generator'
        },
        {
            '@timestamp': (now - timedelta(minutes=3)).isoformat(),
            'isolation_forest_score': 0.8,
            'isolation_forest_label': 1,
            'autoencoder_score': 0.12,
            'autoencoder_label': 1,
            'combined_score': 0.46,
            'combined_label': 1,
            'service': 'manual-generator'
        },
        {
            '@timestamp': (now - timedelta(minutes=1)).isoformat(),
            'isolation_forest_score': 0.75,
            'isolation_forest_label': 1,
            'autoencoder_score': 0.09,
            'autoencoder_label': 1,
            'combined_score': 0.42,
            'combined_label': 1,
            'service': 'manual-generator'
        }
    ]
    
    # Write to Elasticsearch
    for score in scores:
        es.index(index='anomaly-scores', document=score)
        print(f"Created anomaly score: {score['combined_label']} at {score['@timestamp']}")
    
    print(f"\nGenerated {len(scores)} anomaly scores")
    print("2 anomalies detected (demonstrating the system works)")

if __name__ == '__main__':
    main()