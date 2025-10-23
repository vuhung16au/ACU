#!/bin/bash

# Firewall Lab Test Script
# This script automates the testing of the firewall lab

echo "=== Docker Firewall Lab Test Script ==="
echo ""

# Check if containers are running
echo "1. Checking container status..."
docker-compose ps

echo ""
echo "2. Testing basic connectivity..."

# Test HTTP through firewall (port redirection)
echo "Testing HTTP through firewall (port 80 -> server:8080):"
docker exec client curl -s -o /dev/null -w "HTTP Status: %{http_code}\n" http://firewall:80/ || echo "HTTP test failed"

# Test direct HTTP to server
echo "Testing direct HTTP to server:"
docker exec client curl -s -o /dev/null -w "HTTP Status: %{http_code}\n" http://server:8080/ || echo "Direct HTTP test failed"

# Test SSH connectivity
echo "Testing SSH connectivity:"
docker exec client timeout 5 telnet server 22 2>/dev/null && echo "SSH port is open" || echo "SSH test failed or timeout"

# Test blocked ICMP (should fail)
echo "Testing blocked ICMP (ping):"
docker exec client ping -c 2 server 2>/dev/null && echo "Ping succeeded (unexpected)" || echo "Ping blocked (expected)"

# Test blocked port
echo "Testing blocked port (MySQL 3306):"
docker exec client timeout 3 telnet server 3306 2>/dev/null && echo "Port 3306 is open (unexpected)" || echo "Port 3306 blocked (expected)"

echo ""
echo "3. Checking firewall rules..."
echo "Current iptables rules:"
docker exec firewall iptables -L -v -n

echo ""
echo "4. Network information:"
echo "Client IP: $(docker exec client hostname -i)"
echo "Server IP: $(docker exec client getent hosts server | awk '{print $1}')"
echo "Firewall IP: $(docker exec client getent hosts firewall | awk '{print $1}')"

echo ""
echo "=== Test Complete ==="
echo "For interactive testing, run: docker exec -it client sh"
echo "Then use the commands from the README.md file"
