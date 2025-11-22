#!/usr/bin/env python3
"""
Normal Traffic Log Generator
Generates realistic Apache Combined Log Format entries to simulate normal web traffic.
"""

import os
import sys
import time
import random
import socket
import json
from datetime import datetime
from faker import Faker

fake = Faker()

# Configuration from environment
LOG_RATE = int(os.getenv('LOG_RATE', '150'))  # requests per minute
LOGSTASH_HOST = os.getenv('LOGSTASH_HOST', 'logstash')
LOGSTASH_PORT = int(os.getenv('LOGSTASH_PORT', '5000'))
LOG_FILE_PATH = '/var/log/normal/access.log'

# Realistic web paths
PATHS = [
    '/', '/index.html', '/about', '/contact', '/products', '/services',
    '/blog', '/api/v1/users', '/api/v1/products', '/api/v1/orders',
    '/static/css/style.css', '/static/js/app.js', '/static/img/logo.png',
    '/login', '/dashboard', '/profile', '/settings', '/logout',
    '/search', '/category/electronics', '/category/books', '/category/clothing',
]

# HTTP methods distribution (realistic)
METHODS = ['GET'] * 85 + ['POST'] * 10 + ['PUT'] * 3 + ['DELETE'] * 2

# Status codes distribution (mostly successful)
STATUS_CODES = [200] * 85 + [201] * 5 + [204] * 3 + [301] * 2 + [302] * 2 + [404] * 2 + [500] * 1

# Response sizes (bytes)
def get_response_size(status):
    if status in [200, 201]:
        return random.randint(1000, 50000)
    elif status in [301, 302]:
        return random.randint(200, 500)
    elif status == 404:
        return random.randint(300, 800)
    else:
        return random.randint(100, 1000)

# Response times (milliseconds)
def get_response_time():
    # Normal distribution around 100ms
    return max(10, int(random.gauss(100, 30)))


def generate_log_entry():
    """Generate a single Apache Combined Log Format entry"""
    ip = fake.ipv4()
    timestamp = datetime.now().strftime('%d/%b/%Y:%H:%M:%S %z')
    method = random.choice(METHODS)
    path = random.choice(PATHS)
    status = random.choice(STATUS_CODES)
    size = get_response_size(status)
    response_time = get_response_time()
    referer = fake.url() if random.random() > 0.3 else '-'
    user_agent = fake.user_agent()
    
    # Apache Combined Log Format
    log_line = (
        f'{ip} - - [{timestamp}] "{method} {path} HTTP/1.1" '
        f'{status} {size} "{referer}" "{user_agent}" {response_time}'
    )
    
    return log_line, {
        'ip': ip,
        'timestamp': datetime.now().isoformat(),
        'method': method,
        'path': path,
        'status': status,
        'size': size,
        'response_time': response_time,
        'referer': referer,
        'user_agent': user_agent,
        'type': 'normal'
    }


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
        # Silently fail if Logstash is not ready yet
        return False


def write_to_file(log_line):
    """Write log entry to file"""
    try:
        os.makedirs(os.path.dirname(LOG_FILE_PATH), exist_ok=True)
        with open(LOG_FILE_PATH, 'a') as f:
            f.write(log_line + '\n')
    except Exception as e:
        print(f"Error writing to file: {e}", file=sys.stderr)


def main():
    print(f"Starting log generator: {LOG_RATE} requests/minute", flush=True)
    print(f"Sending logs to: {LOGSTASH_HOST}:{LOGSTASH_PORT}", flush=True)
    print(f"Writing logs to: {LOG_FILE_PATH}", flush=True)
    
    # Calculate sleep time between requests
    sleep_time = 60.0 / LOG_RATE
    
    request_count = 0
    start_time = time.time()
    
    try:
        while True:
            log_line, log_data = generate_log_entry()
            
            # Write to file
            write_to_file(log_line)
            
            # Send to Logstash
            send_to_logstash(log_data)
            
            request_count += 1
            
            # Print status every 100 requests
            if request_count % 100 == 0:
                elapsed = time.time() - start_time
                actual_rate = request_count / (elapsed / 60)
                print(f"Generated {request_count} logs, actual rate: {actual_rate:.1f}/min", flush=True)
            
            # Sleep with small random variation
            time.sleep(sleep_time * random.uniform(0.8, 1.2))
            
    except KeyboardInterrupt:
        print(f"\nStopping log generator. Generated {request_count} total logs.", flush=True)
    except Exception as e:
        print(f"Error: {e}", file=sys.stderr, flush=True)
        sys.exit(1)


if __name__ == '__main__':
    main()

