#!/bin/sh

# Firewall Lab Testing Script
# This script provides various test commands for the firewall lab

echo "=== Firewall Lab Testing Script ==="
echo "Available test commands:"
echo ""

echo "1. Test HTTP connectivity to server (should work):"
echo "   curl -v http://server:8080/"
echo ""

echo "2. Test HTTP through firewall port redirection (80->8080):"
echo "   curl -v http://firewall:80/"
echo ""

echo "3. Test SSH connectivity to server (should work):"
echo "   telnet server 22"
echo ""

echo "4. Test SSH through firewall:"
echo "   telnet firewall 22"
echo ""

echo "5. Test blocked ICMP (ping) - should fail:"
echo "   ping -c 3 server"
echo ""

echo "6. Test API endpoint:"
echo "   curl -v http://server:8080/api"
echo ""

echo "7. Test health check:"
echo "   curl -v http://server:8080/health"
echo ""

echo "8. Test blocked port (e.g., 3306 MySQL):"
echo "   telnet server 3306"
echo ""

echo "9. Check firewall rules from client perspective:"
echo "   nmap -p 80,443,22,8080 firewall"
echo ""

echo "10. Test with different user agents:"
echo "    curl -H 'User-Agent: TestBot' http://server:8080/"
echo ""

echo "=== Network Information ==="
echo "Client IP: $(hostname -i)"
echo "Server IP: $(getent hosts server | awk '{print $1}')"
echo "Firewall IP: $(getent hosts firewall | awk '{print $1}')"
echo ""

echo "Run any of these commands to test the firewall rules!"
echo "Type 'exit' to leave the client container."
