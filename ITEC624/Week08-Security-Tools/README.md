# Overview

This document gives a quick, practical overview of several classic network and security tools and how to run them from the command line on Linux. For each tool you'll find:

- What it is and what it does
- A sample command you can run
- Cross-platform syntax differences where applicable

## NSLOOKUP

What it is and what it does:

- nslookup is a simple DNS query tool. It lets you resolve hostnames to IPs (and vice versa), query specific DNS record types (A, AAAA, MX, TXT, etc.), and test using different DNS servers.

Sample command line run (Linux):

- Basic A record lookup:
  - `nslookup -type=A example.com`
- Query MX records using a specific DNS server (Google DNS):
  - `nslookup -type=MX example.com 8.8.8.8`
- Interactive mode for multiple queries:
  - `nslookup` (then enter commands like `set type=MX` and `example.com`)

**Cross-platform syntax differences:**

- **Linux/Unix**: `nslookup -type=A example.com`
- **Windows**: `nslookup -type=A example.com` (same syntax)
- **macOS**: `nslookup -type=A example.com` (same syntax)
- **Note**: All platforms use the same nslookup syntax, but some Linux distributions may require installing the `dnsutils` package: `sudo apt install dnsutils` (Ubuntu/Debian) or `sudo yum install bind-utils` (RHEL/CentOS)

## TRACERT

What it is and what it does:

- Tracing the route packets take to a destination helps diagnose routing problems and latency. The command varies by platform: `tracert` on Windows, `traceroute` on Linux/macOS.

Sample command line run (Linux):

- ICMP-based traceroute to 8.8.8.8:
  - `sudo traceroute -I 8.8.8.8`
- UDP default traceroute to a domain:
  - `traceroute example.com`
- TCP-based traceroute (useful for firewalled networks):
  - `sudo traceroute -T -p 80 example.com`
- Specify number of hops and timeout:
  - `traceroute -m 15 -w 3 example.com`

**Cross-platform syntax differences:**

- **Linux**: `traceroute example.com` (may require `sudo` for ICMP)
- **Windows**: `tracert example.com` (different command name, no sudo needed)
- **macOS**: `traceroute example.com` (same as Linux)
- **Note**: Linux may require installing traceroute: `sudo apt install traceroute` (Ubuntu/Debian) or `sudo yum install traceroute` (RHEL/CentOS)

## PING

What it is and what it does:

- ping sends ICMP Echo Requests to a host to measure reachability and round-trip time. It's a first-step connectivity and latency check.

Sample command line run (Linux):

- Send 4 echo requests to 1.1.1.1:
  - `ping -c 4 1.1.1.1`
- Specify an interface (e.g., eth0) and count:
  - `ping -I eth0 -c 4 example.com`
- Continuous ping with interval:
  - `ping -i 2 example.com`
- Ping with specific packet size:
  - `ping -s 1000 example.com`
- Ping with timeout:
  - `ping -W 5 example.com`

**Cross-platform syntax differences:**

- **Linux**: `ping -c 4 example.com` (count with -c)
- **Windows**: `ping -n 4 example.com` (count with -n)
- **macOS**: `ping -c 4 example.com` (same as Linux)
- **Note**: Linux ping is usually pre-installed, but on some minimal distributions you may need: `sudo apt install iputils-ping` (Ubuntu/Debian) or `sudo yum install iputils` (RHEL/CentOS)

## Special IP Addresses

When testing network connectivity and DNS resolution, these special IP addresses are commonly used:

### 1.1.1.1 (Cloudflare DNS)

- **Provider**: Cloudflare
- **Purpose**: Fast, privacy-focused DNS resolver
- **Features**:
  - No logging of personal data
  - Fast response times
  - Supports DNS over HTTPS (DoH) and DNS over TLS (DoT)
- **Test commands**:
  - `ping 1.1.1.1`
  - `nslookup example.com 1.1.1.1`

### 8.8.8.8 (Google DNS)

- **Provider**: Google
- **Purpose**: Reliable, widely-used DNS resolver
- **Features**:
  - High availability and reliability
  - Global anycast network
  - Supports DNSSEC
- **Test commands**:
  - `ping 8.8.8.8`
  - `nslookup example.com 8.8.8.8`

**Why use these IPs for testing:**

- They are always available and respond to ping
- They provide reliable DNS resolution
- They help test if internet connectivity is working
- They can bypass local DNS issues
- They're useful for comparing DNS response times

## SATAN

What it is and what it does:

- SATAN (Security Administrator Tool for Analyzing Networks) is a historically important, early network vulnerability scanner from the mid-1990s. It’s not maintained and not included in Kali Linux today. Modern equivalents offer far better coverage and safety.

Sample command line run (modern equivalent on Kali):

- Use Nmap with version detection, OS detection, and common vulnerability scripts against a target:
  - `sudo nmap -sV -O -Pn --script vuln <target>`
- Probe a specific service/port with targeted NSE scripts:
  - `sudo nmap -sV --script "default,vuln,auth" -p 22,80,443 <target>`

Note: If you specifically need SATAN for research or history, you'd have to build it from archived sources in a controlled lab environment. Prefer current tools (Nmap/NSE, OpenVAS/Greenbone, Nessus).

## IP Scanner (nmap)

What it is and what it does:

- An IP scanner discovers live hosts on a network. On Kali Linux, the most common choice is Nmap for host discovery (ping sweep) and port scanning. Another option for Layer-2 discovery on local nets is `arp-scan`.

Sample command line run (Kali Linux):

- Nmap ping sweep of a /24 network:
  - `sudo nmap -sn 192.168.1.0/24`
- Fast top-ports scan against a single host:
  - `sudo nmap -F <target>`
- Local network ARP scan (alternative):
  - `sudo arp-scan --localnet`

## Nessus

What it is and what it does:

- Nessus (by Tenable) is a widely used vulnerability scanner that identifies misconfigurations and known vulnerabilities across hosts and services. On Kali, Nessus is typically installed from Tenable’s package and managed via a web interface; command-line utilities exist mostly for administration.

Sample command line run (Kali Linux):

- Start the Nessus service (after installation):
  - `sudo systemctl start nessusd`
- Enable on boot and check status:
  - `sudo systemctl enable nessusd && systemctl status nessusd`
- Manage users with the CLI (path may vary by install):
  - `sudo /opt/nessus/sbin/nessuscli adduser`

Note: Actual scan creation and execution are usually done via the web UI at <https://localhost:8834> after the service starts. For pure CLI-driven scanning, consider the Tenable API or open-source alternatives like Greenbone/OpenVAS (`gvm-*` commands).

## Wireshark

What it is and what it does:

- Wireshark is a packet analyzer used to capture and inspect network traffic. The GUI is `wireshark`, while `tshark` is its command-line counterpart—handy on servers or headless sessions.

Sample command line run (Kali Linux, using tshark):

- Capture HTTP traffic on interface eth0 with a capture filter:
  - `sudo tshark -i eth0 -f "tcp port 80"`
- Read from a pcap file and display HTTP requests only:
  - `tshark -r capture.pcap -Y "http.request"`

## Conclusion

These tools cover quick DNS checks (nslookup), reachability and path diagnosis (ping, traceroute), host discovery and service probing (nmap as an IP scanner), deep packet inspection (Wireshark/tshark), and full vulnerability scanning at scale (Nessus). Use them responsibly, only against systems you own or are authorized to test.

## Additional Tools

Below are more commonly referenced tools. Where a tool is Windows-only, a Kali Linux alternative is provided with example commands.

### Nmap (additional examples)

What it is and what it does:

- Nmap is a powerful network scanner for host discovery, port scanning, service and OS detection.

Sample command line run (Kali Linux):

- Ping discovery (host discovery only) on a /24:
  - `sudo nmap -sn 10.0.0.0/24`
- Network scan (SYN scan, all ports) against a single host:
  - `sudo nmap -sS -p- -T4 10.0.0.5`

### John the Ripper (jumbo)

What it is and what it does:

- John the Ripper (jumbo) is a password cracker supporting many hash formats and archives. Use only with authorization.

Sample command line run (Kali Linux):

- Crack Unix-style combined passwd/shadow hashes with a wordlist:
  - `unshadow passwd.txt shadow.txt > hashes.txt && john --wordlist=/usr/share/wordlists/rockyou.txt hashes.txt`
- Show cracked credentials:
  - `john --show hashes.txt`

### Nessus (Tenable)

What it is and what it does:

- Commercial vulnerability scanner. See the Nessus section above for notes. Managed via web UI on <https://localhost:8834> after service start.

Sample command line run (Kali Linux):

- Start service:
  - `sudo systemctl start nessusd`

### Snort

What it is and what it does:

- Snort is a network intrusion detection/prevention system (NIDS/NIPS) that inspects traffic against rules.

Sample command line run (Kali Linux):

- Run with config on interface eth0 and log alerts to console:
  - `sudo snort -c /etc/snort/snort.conf -i eth0 -A console -q`

### Ettercap

What it is and what it does:

- Ettercap performs man-in-the-middle attacks and traffic analysis on LANs (education/lab use with permission only).

Sample command line run (Kali Linux):

- Text-mode ARP poisoning between target and gateway on eth0:
  - `sudo ettercap -T -M arp /192.168.1.10/ /192.168.1.1/ -i eth0`

### Nikto

What it is and what it does:

- Nikto is a web server scanner that looks for dangerous files, outdated servers, and known issues.

Sample command line run (Kali Linux):

- Scan a web target:
  - `nikto -h http://example.com`

### THC Hydra

What it is and what it does:

- Hydra is a login cracker for network services (SSH, FTP, HTTP, etc.). Use only against systems you’re authorized to test.

Sample command line run (Kali Linux):

- Brute-force SSH with user and password lists:
  - `hydra -L users.txt -P passwords.txt ssh://10.0.0.5`

### Dsniff suite

What it is and what it does:

- Dsniff includes tools for network traffic sniffing and credential harvesting in lab settings (e.g., arpspoof, urlsnarf). Authorized environments only.

Sample command line run (Kali Linux):

- ARP spoof a victim towards gateway (example):
  - `sudo arpspoof -i eth0 -t 192.168.1.10 192.168.1.1`
- Sniff HTTP requests on interface:
  - `sudo urlsnarf -i eth0`

### Aircrack-ng

What it is and what it does:

- Aircrack-ng is a suite for auditing Wi‑Fi security, including capture and key recovery (authorized testing only).

Sample command line run (Kali Linux):

- Capture handshake and attempt key recovery with a wordlist:
  - `sudo airmon-ng start wlan0 && sudo airodump-ng --write capture wlan0mon && aircrack-ng capture-01.cap -w /usr/share/wordlists/rockyou.txt`

### Netfilter (nftables)

What it is and what it does:

- Netfilter is the Linux kernel packet filtering framework. On modern Kali, `nftables` is the front end for firewall rules.

Sample command line run (Kali Linux):

- List current ruleset and allow inbound SSH as an example:
  - `sudo nft list ruleset`
  - `sudo nft add table inet filter && sudo nft add chain inet filter input '{ type filter hook input priority 0; }' && sudo nft add rule inet filter input tcp dport 22 accept`
