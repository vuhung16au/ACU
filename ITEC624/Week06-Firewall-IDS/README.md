# [Uncomplicated Firewall](https://wiki.alpinelinux.org/wiki/Uncomplicated_Firewall)

- Basic setup for UFW on Alpine Linux (or other Linux distros).
- Installation and configuration instructions.

# Standard Services and Ports 

Common services and their default ports.

| Service        | Port | Protocol |
|----------------|------|----------|
| HTTP           | 80   | TCP      |
| HTTPS          | 443  | TCP      |
| SSH            | 22   | TCP      |
| FTP            | 21   | TCP      |
| SMTP           | 25   | TCP      |
| DNS            | 53   | UDP/TCP  |
| DHCP           | 67   | UDP      |
| MySQL          | 3306 | TCP      |
| PostgreSQL     | 5432 | TCP      |

# IMCP, TCP, and UDP Protocols

| Protocol | Description                          |
|----------|--------------------------------------|
| ICMP     | Internet Control Message Protocol    | |
| TCP      | Transmission Control Protocol        | |
| UDP      | User Datagram Protocol               |

# IPv4 and IPv6

- IPv4: Internet Protocol version 4
- IPv6: Internet Protocol version 6

# Single-Home vs Dual-Home Systems

- Single-Home: Connected to one network.
- Dual-Home: Connected to two networks.

# Intrusion & Anomaly Detection (AD)

- Techniques to identify unauthorized access or unusual patterns in network traffic.

Tools: Snort, Suricata, 

# Filter DDoS

- Methods to mitigate Distributed Denial of Service attacks.

# Intrusion Detection Systems (IDSs)

- Systems designed to detect unauthorized access or anomalies in network traffic.
- Rules-based and behavior-based detection methods (traditional). Examples: Snort, Suricata.
- Machine learning-based detection methods (modern). Examples: Darktrace, Vectra.

# Docker Firewall Lab

See: docker-firewall-lab