#!/usr/bin/env python3
"""
Generate sample CSV datasets for offline analysis.
Creates baseline and anomalous data for testing and demonstrations.
"""

import pandas as pd
import numpy as np
from datetime import datetime, timedelta
import os

np.random.seed(42)

def generate_baseline_data(n_samples=10000):
    """Generate normal baseline traffic data"""
    print(f"Generating {n_samples} baseline samples...")
    
    data = []
    start_time = datetime.now() - timedelta(hours=2)
    
    for i in range(n_samples):
        timestamp = start_time + timedelta(seconds=i*0.36)  # ~100 req/min
        
        data.append({
            'timestamp': timestamp.isoformat(),
            'requests_per_minute': np.random.normal(150, 20),
            'avg_response_time': np.random.normal(100, 30),
            'error_rate': np.random.uniform(0, 0.05),
            'request_size_variance': np.random.normal(1000, 300),
            'unique_ips': np.random.randint(40, 60),
            'status_code_diversity': np.random.uniform(0.5, 1.0),
            'is_anomaly': False,
            'anomaly_type': 'normal'
        })
    
    df = pd.DataFrame(data)
    
    # Ensure positive values
    df['requests_per_minute'] = df['requests_per_minute'].clip(lower=50)
    df['avg_response_time'] = df['avg_response_time'].clip(lower=10)
    df['error_rate'] = df['error_rate'].clip(lower=0, upper=1)
    df['request_size_variance'] = df['request_size_variance'].clip(lower=100)
    
    return df

def generate_anomaly_data(n_samples=500):
    """Generate anomalous traffic data"""
    print(f"Generating {n_samples} anomaly samples...")
    
    data = []
    start_time = datetime.now() - timedelta(hours=1)
    
    # Different types of anomalies
    anomaly_types = ['burst', 'error_flood', 'slow_requests', 'scan_pattern']
    samples_per_type = n_samples // len(anomaly_types)
    
    for anomaly_type in anomaly_types:
        for i in range(samples_per_type):
            timestamp = start_time + timedelta(seconds=i*0.72)
            
            if anomaly_type == 'burst':
                # High volume
                requests_per_minute = np.random.normal(500, 100)
                avg_response_time = np.random.normal(150, 50)
                error_rate = np.random.uniform(0, 0.1)
                request_size_variance = np.random.normal(2000, 500)
                unique_ips = np.random.randint(30, 50)
                status_code_diversity = np.random.uniform(0.6, 1.0)
                
            elif anomaly_type == 'error_flood':
                # High error rate
                requests_per_minute = np.random.normal(200, 50)
                avg_response_time = np.random.normal(80, 20)
                error_rate = np.random.uniform(0.5, 0.9)
                request_size_variance = np.random.normal(500, 200)
                unique_ips = np.random.randint(10, 30)
                status_code_diversity = np.random.uniform(0.3, 0.7)
                
            elif anomaly_type == 'slow_requests':
                # High latency
                requests_per_minute = np.random.normal(100, 30)
                avg_response_time = np.random.normal(5000, 1000)
                error_rate = np.random.uniform(0, 0.1)
                request_size_variance = np.random.normal(10000, 3000)
                unique_ips = np.random.randint(40, 60)
                status_code_diversity = np.random.uniform(0.6, 1.0)
                
            else:  # scan_pattern
                # Sequential scanning
                requests_per_minute = np.random.normal(150, 40)
                avg_response_time = np.random.normal(50, 15)
                error_rate = np.random.uniform(0.6, 0.95)
                request_size_variance = np.random.normal(200, 50)
                unique_ips = 1  # Same IP
                status_code_diversity = np.random.uniform(0.1, 0.4)
            
            data.append({
                'timestamp': timestamp.isoformat(),
                'requests_per_minute': max(0, requests_per_minute),
                'avg_response_time': max(0, avg_response_time),
                'error_rate': np.clip(error_rate, 0, 1),
                'request_size_variance': max(0, request_size_variance),
                'unique_ips': int(unique_ips),
                'status_code_diversity': np.clip(status_code_diversity, 0, 1),
                'is_anomaly': True,
                'anomaly_type': anomaly_type
            })
    
    return pd.DataFrame(data)

def main():
    # Create data directory
    os.makedirs('../data', exist_ok=True)
    
    # Generate baseline data
    baseline_df = generate_baseline_data(10000)
    baseline_file = '../data/baseline_logs.csv'
    baseline_df.to_csv(baseline_file, index=False)
    print(f"✓ Saved baseline data: {baseline_file}")
    print(f"  Rows: {len(baseline_df)}")
    print(f"  Columns: {list(baseline_df.columns)}")
    print()
    
    # Generate anomaly data
    anomaly_df = generate_anomaly_data(500)
    anomaly_file = '../data/anomaly_logs.csv'
    anomaly_df.to_csv(anomaly_file, index=False)
    print(f"✓ Saved anomaly data: {anomaly_file}")
    print(f"  Rows: {len(anomaly_df)}")
    print(f"  Anomaly types:")
    for anomaly_type, count in anomaly_df['anomaly_type'].value_counts().items():
        print(f"    - {anomaly_type}: {count}")
    print()
    
    # Generate combined dataset
    combined_df = pd.concat([baseline_df, anomaly_df], ignore_index=True)
    combined_df = combined_df.sample(frac=1).reset_index(drop=True)  # Shuffle
    combined_file = '../data/combined_logs.csv'
    combined_df.to_csv(combined_file, index=False)
    print(f"✓ Saved combined data: {combined_file}")
    print(f"  Total rows: {len(combined_df)}")
    print(f"  Anomaly ratio: {combined_df['is_anomaly'].mean():.1%}")
    print()
    
    print("Dataset generation complete!")
    print()
    print("Use these datasets for:")
    print("  - Offline ML model training")
    print("  - Algorithm testing and validation")
    print("  - Student exercises and analysis")
    print("  - Performance benchmarking")

if __name__ == '__main__':
    main()

