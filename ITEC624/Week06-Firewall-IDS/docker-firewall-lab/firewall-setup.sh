#!/bin/sh

# Firewall Setup Script for Docker Lab
# This script configures iptables rules to demonstrate various firewall concepts

echo "Starting Firewall Configuration..."

# Enable IP forwarding
echo 1 > /proc/sys/net/ipv4/ip_forward

# Flush existing rules
iptables -F
iptables -t nat -F
iptables -t mangle -F

# Set default policies
iptables -P INPUT DROP
iptables -P FORWARD DROP
iptables -P OUTPUT ACCEPT

# Allow loopback traffic
iptables -A INPUT -i lo -j ACCEPT
iptables -A OUTPUT -o lo -j ACCEPT

# Allow established and related connections
iptables -A INPUT -m state --state ESTABLISHED,RELATED -j ACCEPT
iptables -A FORWARD -m state --state ESTABLISHED,RELATED -j ACCEPT

# Get container IPs dynamically
SERVER_IP=$(getent hosts server | awk '{ print $1 }')
CLIENT_IP=$(getent hosts client | awk '{ print $1 }')
FIREWALL_IP=$(hostname -i)

echo "Server IP: $SERVER_IP"
echo "Client IP: $CLIENT_IP"
echo "Firewall IP: $FIREWALL_IP"

# === LAB SCENARIO 1: Block all traffic except allowed ports ===
echo "Configuring Scenario 1: Block all traffic except HTTP (port 80) and SSH (port 22)"

# Allow HTTP traffic to server
iptables -A FORWARD -p tcp --dport 80 -d $SERVER_IP -j ACCEPT
iptables -A FORWARD -p tcp --dport 8080 -d $SERVER_IP -j ACCEPT

# Allow SSH traffic to server
iptables -A FORWARD -p tcp --dport 22 -d $SERVER_IP -j ACCEPT

# Allow HTTP and SSH to firewall itself
iptables -A INPUT -p tcp --dport 80 -j ACCEPT
iptables -A INPUT -p tcp --dport 22 -j ACCEPT

# === LAB SCENARIO 2: Port redirection (DNAT) ===
echo "Configuring Scenario 2: Port redirection - redirect port 80 to server:8080"

# Redirect HTTP traffic from firewall:80 to server:8080
iptables -t nat -A PREROUTING -p tcp --dport 80 -j DNAT --to-destination $SERVER_IP:8080
iptables -A FORWARD -p tcp --dport 8080 -d $SERVER_IP -j ACCEPT

# === LAB SCENARIO 3: Block specific host (client) ===
echo "Configuring Scenario 3: Block specific host - blocking client IP for ICMP"

# Block ICMP (ping) from client
iptables -A FORWARD -s $CLIENT_IP -p icmp -j DROP

# === LAB SCENARIO 4: Allow traffic from specific host ===
echo "Configuring Scenario 4: Allow traffic from specific host - allow client to access SSH"

# Allow SSH from client to server
iptables -A FORWARD -s $CLIENT_IP -p tcp --dport 22 -d $SERVER_IP -j ACCEPT

# Allow HTTP from client to server
iptables -A FORWARD -s $CLIENT_IP -p tcp --dport 8080 -d $SERVER_IP -j ACCEPT

# === Additional security rules ===
# Log dropped packets for monitoring
iptables -A INPUT -j LOG --log-prefix "FIREWALL-DROPPED-INPUT: "
iptables -A FORWARD -j LOG --log-prefix "FIREWALL-DROPPED-FORWARD: "

# Allow DNS queries
iptables -A FORWARD -p udp --dport 53 -j ACCEPT
iptables -A FORWARD -p tcp --dport 53 -j ACCEPT

# === Display current rules ===
echo "Current iptables rules:"
echo "=== FILTER TABLE ==="
iptables -L -v -n
echo "=== NAT TABLE ==="
iptables -t nat -L -v -n

# Keep the container running
echo "Firewall configuration complete. Container will stay running..."
tail -f /dev/null
