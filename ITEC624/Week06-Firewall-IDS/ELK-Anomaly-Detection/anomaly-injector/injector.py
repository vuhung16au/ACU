#!/usr/bin/env python3
"""
Anomaly Traffic Injector
Generates 4 types of anomalous traffic patterns for ML detection demonstration.
"""

import os
import sys
import time
import random
import socket
import json
import argparse
from datetime import datetime
from faker import Faker

fake = Faker()

# Configuration
LOGSTASH_HOST = os.getenv('LOGSTASH_HOST', 'logstash')
LOGSTASH_PORT = int(os.getenv('LOGSTASH_PORT', '5000'))
ANOMALY_INTENSITY = int(os.getenv('ANOMALY_INTENSITY', '8'))
LOG_FILE_PATH = '/var/log/anomaly/anomaly.log'

# SQL Injection patterns
SQL_INJECTION_PATTERNS = [
    "' OR '1'='1",
    "'; DROP TABLE users--",
    "' UNION SELECT * FROM passwords--",
    "admin'--",
    "1' OR '1' = '1",
]

# XSS patterns
XSS_PATTERNS = [
    "<script>alert('XSS')</script>",
    "<img src=x onerror=alert('XSS')>",
    "javascript:alert('XSS')",
]

# Scanning paths (sequential)
SCAN_PATHS = [
    '/admin', '/admin/login', '/administrator', '/phpmyadmin',
    '/wp-admin', '/wp-login.php', '/.env', '/config.php',
    '/.git/config', '/backup.sql', '/database.sql',
    '/api/v1/admin', '/api/v2/admin', '/api/admin',
    '/.aws/credentials', '/.ssh/id_rsa',
]


def send_to_logstash(data):
    """Send log entry to Logstash via TCP"""
    try:
        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        sock.settimeout(2)
        sock.connect((LOGSTASH_HOST, LOGSTASH_PORT))
        sock.sendall((json.dumps(data) + '\n').encode('utf-8'))
        sock.close()
        return True
    except Exception as e:
        return False


def write_to_file(log_line):
    """Write log entry to file"""
    try:
        os.makedirs(os.path.dirname(LOG_FILE_PATH), exist_ok=True)
        with open(LOG_FILE_PATH, 'a') as f:
            f.write(log_line + '\n')
    except Exception as e:
        print(f"Error writing to file: {e}", file=sys.stderr)


def inject_burst_anomaly(duration=30, intensity=8):
    """
    Type 1: BURST - High volume spike (1000+ requests in short time)
    Tests: Isolation Forest detection (volume-based)
    """
    print(f"[BURST ANOMALY] Starting {duration}s burst attack with intensity {intensity}", flush=True)
    
    requests_per_second = intensity * 30  # e.g., intensity 8 = 240 req/s
    total_requests = requests_per_second * duration
    
    ip = fake.ipv4()  # Same IP for burst
    start_time = time.time()
    
    for i in range(total_requests):
        timestamp = datetime.now().strftime('%d/%b/%Y:%H:%M:%S %z')
        path = random.choice(['/api/v1/data', '/search', '/products'])
        status = 200
        size = random.randint(500, 2000)
        response_time = random.randint(50, 150)
        
        log_line = (
            f'{ip} - - [{timestamp}] "GET {path} HTTP/1.1" '
            f'{status} {size} "-" "BurstBot/1.0" {response_time}'
        )
        
        log_data = {
            'ip': ip,
            'timestamp': datetime.now().isoformat(),
            'method': 'GET',
            'path': path,
            'status': status,
            'size': size,
            'response_time': response_time,
            'referer': '-',
            'user_agent': 'BurstBot/1.0',
            'type': 'burst_anomaly'
        }
        
        write_to_file(log_line)
        send_to_logstash(log_data)
        
        if i % 100 == 0:
            elapsed = time.time() - start_time
            print(f"  Sent {i}/{total_requests} burst requests ({elapsed:.1f}s)", flush=True)
        
        time.sleep(1.0 / requests_per_second)
    
    print(f"[BURST ANOMALY] Completed: {total_requests} requests in {duration}s", flush=True)


def inject_error_flood(duration=30, intensity=8):
    """
    Type 2: ERROR FLOOD - High rate of HTTP errors (404/500)
    Tests: Error rate detection
    """
    print(f"[ERROR FLOOD] Starting {duration}s error flood with intensity {intensity}", flush=True)
    
    requests_per_second = intensity * 10  # e.g., intensity 8 = 80 errors/s
    total_requests = requests_per_second * duration
    
    error_codes = [404] * 7 + [500] * 2 + [403] * 1
    ip = fake.ipv4()
    start_time = time.time()
    
    for i in range(total_requests):
        timestamp = datetime.now().strftime('%d/%b/%Y:%H:%M:%S %z')
        status = random.choice(error_codes)
        path = f'/api/v1/resource{random.randint(1000, 9999)}'
        size = random.randint(100, 500)
        response_time = random.randint(10, 50)
        
        log_line = (
            f'{ip} - - [{timestamp}] "GET {path} HTTP/1.1" '
            f'{status} {size} "-" "ErrorBot/1.0" {response_time}'
        )
        
        log_data = {
            'ip': ip,
            'timestamp': datetime.now().isoformat(),
            'method': 'GET',
            'path': path,
            'status': status,
            'size': size,
            'response_time': response_time,
            'referer': '-',
            'user_agent': 'ErrorBot/1.0',
            'type': 'error_flood'
        }
        
        write_to_file(log_line)
        send_to_logstash(log_data)
        
        if i % 100 == 0:
            print(f"  Sent {i}/{total_requests} error requests", flush=True)
        
        time.sleep(1.0 / requests_per_second)
    
    print(f"[ERROR FLOOD] Completed: {total_requests} errors", flush=True)


def inject_slow_requests(duration=30, intensity=5):
    """
    Type 3: SLOW REQUESTS - High latency anomaly
    Tests: Autoencoder detection (pattern deviation)
    """
    print(f"[SLOW REQUESTS] Starting {duration}s slow request attack", flush=True)
    
    requests_per_second = intensity * 2  # Fewer requests but very slow
    total_requests = requests_per_second * duration
    
    ip = fake.ipv4()
    start_time = time.time()
    
    for i in range(total_requests):
        timestamp = datetime.now().strftime('%d/%b/%Y:%H:%M:%S %z')
        path = random.choice(['/api/v1/heavy', '/search', '/report'])
        status = 200
        size = random.randint(10000, 100000)  # Large responses
        response_time = random.randint(5000, 10000)  # 5-10 seconds!
        
        log_line = (
            f'{ip} - - [{timestamp}] "POST {path} HTTP/1.1" '
            f'{status} {size} "-" "SlowBot/1.0" {response_time}'
        )
        
        log_data = {
            'ip': ip,
            'timestamp': datetime.now().isoformat(),
            'method': 'POST',
            'path': path,
            'status': status,
            'size': size,
            'response_time': response_time,
            'referer': '-',
            'user_agent': 'SlowBot/1.0',
            'type': 'slow_requests'
        }
        
        write_to_file(log_line)
        send_to_logstash(log_data)
        
        if i % 10 == 0:
            print(f"  Sent {i}/{total_requests} slow requests", flush=True)
        
        time.sleep(1.0 / requests_per_second)
    
    print(f"[SLOW REQUESTS] Completed: {total_requests} slow requests", flush=True)


def inject_scan_pattern(duration=30, intensity=5):
    """
    Type 4: SCAN PATTERN - Sequential port/path scanning
    Tests: Autoencoder detection (sequential pattern anomaly)
    """
    print(f"[SCAN PATTERN] Starting {duration}s scan pattern attack", flush=True)
    
    requests_per_second = intensity * 3
    total_requests = min(requests_per_second * duration, len(SCAN_PATHS) * 3)
    
    ip = fake.ipv4()
    scan_index = 0
    start_time = time.time()
    
    for i in range(total_requests):
        timestamp = datetime.now().strftime('%d/%b/%Y:%H:%M:%S %z')
        path = SCAN_PATHS[scan_index % len(SCAN_PATHS)]
        
        # Add SQL injection to some paths
        if random.random() > 0.7:
            path += '?' + random.choice(SQL_INJECTION_PATTERNS)
        
        status = random.choice([404] * 8 + [403] * 2)
        size = random.randint(100, 500)
        response_time = random.randint(20, 80)
        
        log_line = (
            f'{ip} - - [{timestamp}] "GET {path} HTTP/1.1" '
            f'{status} {size} "-" "Scanner/1.0" {response_time}'
        )
        
        log_data = {
            'ip': ip,
            'timestamp': datetime.now().isoformat(),
            'method': 'GET',
            'path': path,
            'status': status,
            'size': size,
            'response_time': response_time,
            'referer': '-',
            'user_agent': 'Scanner/1.0',
            'type': 'scan_pattern'
        }
        
        write_to_file(log_line)
        send_to_logstash(log_data)
        
        scan_index += 1
        
        if i % 10 == 0:
            print(f"  Sent {i}/{total_requests} scan requests", flush=True)
        
        time.sleep(1.0 / requests_per_second)
    
    print(f"[SCAN PATTERN] Completed: {total_requests} scan requests", flush=True)


def main():
    parser = argparse.ArgumentParser(description='Inject anomalous traffic patterns')
    parser.add_argument('--type', '-t', 
                       choices=['burst', 'errors', 'slow', 'scan', 'all'],
                       default='all',
                       help='Type of anomaly to inject')
    parser.add_argument('--duration', '-d', type=int, default=30,
                       help='Duration in seconds (default: 30)')
    parser.add_argument('--intensity', '-i', type=int, default=ANOMALY_INTENSITY,
                       help='Intensity level 1-10 (default: 8)')
    
    args = parser.parse_args()
    
    print(f"Anomaly Injector starting...", flush=True)
    print(f"Target: {LOGSTASH_HOST}:{LOGSTASH_PORT}", flush=True)
    print(f"Type: {args.type}, Duration: {args.duration}s, Intensity: {args.intensity}", flush=True)
    print("=" * 60, flush=True)
    
    try:
        if args.type == 'burst' or args.type == 'all':
            inject_burst_anomaly(args.duration, args.intensity)
            if args.type == 'all':
                time.sleep(5)
        
        if args.type == 'errors' or args.type == 'all':
            inject_error_flood(args.duration, args.intensity)
            if args.type == 'all':
                time.sleep(5)
        
        if args.type == 'slow' or args.type == 'all':
            inject_slow_requests(args.duration, args.intensity)
            if args.type == 'all':
                time.sleep(5)
        
        if args.type == 'scan' or args.type == 'all':
            inject_scan_pattern(args.duration, args.intensity)
        
        print("=" * 60, flush=True)
        print("Anomaly injection completed!", flush=True)
        
    except KeyboardInterrupt:
        print("\nStopping anomaly injector...", flush=True)
    except Exception as e:
        print(f"Error: {e}", file=sys.stderr, flush=True)
        sys.exit(1)


if __name__ == '__main__':
    main()

