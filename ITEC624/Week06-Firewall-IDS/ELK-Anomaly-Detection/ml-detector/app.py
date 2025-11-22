#!/usr/bin/env python3
"""
ML Detection Service Orchestrator
Runs both Isolation Forest and Autoencoder algorithms in parallel.
Continuously monitors Elasticsearch for new logs and writes anomaly scores back.
"""

import os
import sys
import time
import json
from datetime import datetime, timedelta
import numpy as np
import pandas as pd
from elasticsearch import Elasticsearch
from elasticsearch.helpers import bulk
import yaml

# Import our detectors
from isolation_forest import IsolationForestDetector
from autoencoder import AutoencoderDetector


class MLDetectionService:
    """
    Main ML Detection Service that orchestrates both algorithms.
    """
    
    def __init__(self):
        """Initialize the service with configuration"""
        self.es_host = os.getenv('ELASTICSEARCH_HOST', 'http://elasticsearch:9200')
        self.poll_interval = int(os.getenv('ML_POLL_INTERVAL', '30'))
        
        # Initialize Elasticsearch client
        self.es = None
        self._connect_elasticsearch()
        
        # Load configuration
        self.config = self._load_config()
        self.features = self.config.get('features', [
            'requests_per_minute',
            'avg_response_time',
            'error_rate',
            'request_size_variance',
            'unique_ips',
            'status_code_diversity'
        ])
        
        # Initialize detectors
        self.if_detector = IsolationForestDetector()
        self.ae_detector = AutoencoderDetector()
        
        # Try to load pre-trained models
        self.models_loaded = False
        self._load_models()
        
        # Logging
        self.log_dir = 'logs'
        os.makedirs(self.log_dir, exist_ok=True)
        
        print("[ML Service] Initialized successfully!", flush=True)
        print(f"[ML Service] Polling interval: {self.poll_interval}s", flush=True)
        print(f"[ML Service] Features: {self.features}", flush=True)
    
    def _connect_elasticsearch(self):
        """Connect to Elasticsearch with retries"""
        max_retries = 30
        for attempt in range(max_retries):
            try:
                self.es = Elasticsearch([self.es_host], request_timeout=30)
                if self.es.ping():
                    print(f"[ML Service] Connected to Elasticsearch at {self.es_host}", flush=True)
                    return True
            except Exception as e:
                if attempt < max_retries - 1:
                    print(f"[ML Service] Waiting for Elasticsearch... ({attempt + 1}/{max_retries})", flush=True)
                    time.sleep(2)
                else:
                    print(f"[ML Service] Failed to connect to Elasticsearch: {e}", file=sys.stderr, flush=True)
                    sys.exit(1)
        return False
    
    def _load_config(self):
        """Load configuration from YAML file"""
        try:
            config_path = 'config/ml-config.yaml'
            if os.path.exists(config_path):
                with open(config_path, 'r') as f:
                    return yaml.safe_load(f)
        except Exception as e:
            print(f"[ML Service] Warning: Could not load config: {e}", flush=True)
        return {}
    
    def _load_models(self):
        """Load pre-trained models if they exist"""
        if_loaded = self.if_detector.load_model()
        ae_loaded = self.ae_detector.load_model()
        
        self.models_loaded = if_loaded and ae_loaded
        
        if self.models_loaded:
            print("[ML Service] Pre-trained models loaded successfully!", flush=True)
        else:
            print("[ML Service] Models not found. Will train on first data batch.", flush=True)
    
    def fetch_recent_logs(self, minutes=5):
        """
        Fetch recent logs from Elasticsearch.
        
        Args:
            minutes: How many minutes of logs to fetch
            
        Returns:
            DataFrame with log data
        """
        try:
            # Query for recent logs
            query = {
                "query": {
                    "range": {
                        "@timestamp": {
                            "gte": f"now-{minutes}m",
                            "lte": "now"
                        }
                    }
                },
                "size": 10000,
                "sort": [{"@timestamp": "desc"}]
            }
            
            response = self.es.search(index="logs-*", body=query)
            hits = response['hits']['hits']
            
            if not hits:
                return None
            
            # Convert to DataFrame
            logs = []
            for hit in hits:
                source = hit['_source']
                logs.append({
                    '_id': hit['_id'],
                    'timestamp': source.get('@timestamp'),
                    'ip': source.get('ip'),
                    'method': source.get('method'),
                    'path': source.get('path'),
                    'status': source.get('status'),
                    'size': source.get('size'),
                    'response_time': source.get('response_time'),
                    'is_anomaly': source.get('is_anomaly', False),
                    'anomaly_type': source.get('anomaly_type', 'normal')
                })
            
            df = pd.DataFrame(logs)
            return df
            
        except Exception as e:
            print(f"[ML Service] Error fetching logs: {e}", file=sys.stderr, flush=True)
            return None
    
    def extract_features(self, df):
        """
        Extract features from log DataFrame for ML detection.
        
        Args:
            df: DataFrame with log data
            
        Returns:
            Feature matrix (numpy array)
        """
        if df is None or len(df) == 0:
            return None
        
        # Group by time windows (1-minute buckets)
        df['timestamp_parsed'] = pd.to_datetime(df['timestamp'])
        df['time_bucket'] = df['timestamp_parsed'].dt.floor('1min')
        
        # Aggregate features per time bucket
        features = []
        
        for bucket, group in df.groupby('time_bucket'):
            # Feature 1: Requests per minute
            requests_per_minute = len(group)
            
            # Feature 2: Average response time
            avg_response_time = group['response_time'].mean()
            
            # Feature 3: Error rate
            error_count = (group['status'] >= 400).sum()
            error_rate = error_count / len(group) if len(group) > 0 else 0
            
            # Feature 4: Request size variance
            request_size_variance = group['size'].var()
            
            # Feature 5: Unique IPs
            unique_ips = group['ip'].nunique()
            
            # Feature 6: Status code diversity (entropy)
            status_counts = group['status'].value_counts()
            status_probs = status_counts / len(group)
            status_code_diversity = -np.sum(status_probs * np.log2(status_probs + 1e-10))
            
            features.append([
                requests_per_minute,
                avg_response_time,
                error_rate,
                request_size_variance,
                unique_ips,
                status_code_diversity
            ])
        
        return np.array(features)
    
    def train_models(self, X):
        """
        Train both models on baseline data.
        
        Args:
            X: Feature matrix
        """
        print(f"[ML Service] Training models on {len(X)} samples...", flush=True)
        
        # Train Isolation Forest
        self.if_detector.train(X, save=True)
        
        # Train Autoencoder
        self.ae_detector.train(X, save=True)
        
        self.models_loaded = True
        print("[ML Service] Models trained successfully!", flush=True)
    
    def detect_anomalies(self, X):
        """
        Run both detection algorithms on feature matrix.
        
        Args:
            X: Feature matrix
            
        Returns:
            Dictionary with results from both algorithms
        """
        if not self.models_loaded:
            print("[ML Service] Models not loaded. Skipping detection.", flush=True)
            return None
        
        # Run Isolation Forest
        if_scores, if_labels = self.if_detector.predict(X)
        
        # Run Autoencoder
        ae_scores, ae_labels = self.ae_detector.predict(X)
        
        return {
            'isolation_forest_scores': if_scores,
            'isolation_forest_labels': if_labels,
            'autoencoder_scores': ae_scores,
            'autoencoder_labels': ae_labels,
            'combined_scores': (if_scores + ae_scores) / 2,  # Average
            'combined_labels': ((if_labels + ae_labels) > 0).astype(int)  # OR logic
        }
    
    def write_results_to_es(self, results, timestamp_base, num_samples):
        """
        Write anomaly detection results back to Elasticsearch.
        
        Args:
            results: Detection results dictionary
            timestamp_base: Base timestamp for the results
            num_samples: Number of samples in the results
        """
        try:
            # Write results for each time bucket
            for i in range(num_samples):
                doc = {
                    '@timestamp': timestamp_base,
                    'isolation_forest_score': float(results['isolation_forest_scores'][i]),
                    'isolation_forest_label': int(results['isolation_forest_labels'][i]),
                    'autoencoder_score': float(results['autoencoder_scores'][i]),
                    'autoencoder_label': int(results['autoencoder_labels'][i]),
                    'combined_score': float(results['combined_scores'][i]),
                    'combined_label': int(results['combined_labels'][i]),
                    'service': 'ml-detector',
                    'sample_index': i
                }
                
                self.es.index(index='anomaly-scores', document=doc)
            
            print(f"[ML Service] Wrote {num_samples} anomaly score records to ES", flush=True)
            
        except Exception as e:
            print(f"[ML Service] Error writing results: {e}", file=sys.stderr, flush=True)
    
    def run(self):
        """Main service loop"""
        print("[ML Service] Starting detection loop...", flush=True)
        
        iteration = 0
        
        try:
            while True:
                iteration += 1
                print(f"\n[ML Service] === Iteration {iteration} ===", flush=True)
                
                # Fetch recent logs
                df = self.fetch_recent_logs(minutes=5)
                
                if df is None or len(df) == 0:
                    print("[ML Service] No logs found. Waiting...", flush=True)
                    time.sleep(self.poll_interval)
                    continue
                
                print(f"[ML Service] Fetched {len(df)} logs", flush=True)
                
                # Extract features
                X = self.extract_features(df)
                
                if X is None or len(X) == 0:
                    print("[ML Service] No features extracted. Waiting...", flush=True)
                    time.sleep(self.poll_interval)
                    continue
                
                print(f"[ML Service] Extracted {len(X)} feature vectors", flush=True)
                
                # Train models if not loaded (lowered threshold from 50 to 5)
                if not self.models_loaded and len(X) >= 5:
                    # Train on first batch (assuming it's mostly normal)
                    self.train_models(X)
                    time.sleep(self.poll_interval)
                    continue
                
                # Detect anomalies
                if self.models_loaded:
                    results = self.detect_anomalies(X)
                    
                    if results is not None:
                        # Count detections
                        if_anomalies = results['isolation_forest_labels'].sum()
                        ae_anomalies = results['autoencoder_labels'].sum()
                        combined_anomalies = results['combined_labels'].sum()
                        
                        print(f"[ML Service] Detection results:", flush=True)
                        print(f"  - Isolation Forest: {if_anomalies}/{len(X)} anomalies", flush=True)
                        print(f"  - Autoencoder: {ae_anomalies}/{len(X)} anomalies", flush=True)
                        print(f"  - Combined: {combined_anomalies}/{len(X)} anomalies", flush=True)
                        
                        # Write all results for each time bucket
                        timestamp = datetime.utcnow().isoformat()
                        self.write_results_to_es(results, timestamp, len(X))
                
                # Wait before next iteration
                print(f"[ML Service] Waiting {self.poll_interval}s...", flush=True)
                time.sleep(self.poll_interval)
                
        except KeyboardInterrupt:
            print("\n[ML Service] Stopping detection service...", flush=True)
        except Exception as e:
            print(f"[ML Service] Fatal error: {e}", file=sys.stderr, flush=True)
            sys.exit(1)


def main():
    """Entry point"""
    print("=" * 60, flush=True)
    print("ML Anomaly Detection Service", flush=True)
    print("=" * 60, flush=True)
    
    service = MLDetectionService()
    service.run()


if __name__ == '__main__':
    main()

