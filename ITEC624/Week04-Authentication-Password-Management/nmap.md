## Nmap Quick Guide: Port Scanning and OS Detection

Target examples below use `192.168.0.x` (single host) or `192.168.0.0/24` (subnet).

### Basics
- Default scan (common TCP ports):
  ```bash
  nmap 192.168.0.x
  ```
- Fast/top ports or specific ranges:
  ```bash
  nmap -F 192.168.0.x            # fast common ports
  nmap -p 1-1024 192.168.0.x     # range
  nmap -p 80,443,8080 192.168.0.x
  ```

### Scan common services
- Web server (HTTP 80):
  ```bash
  nmap -p 80 192.168.0.x
  nmap -p 80 -sV 192.168.0.x     # identify service/version
  ```
- SMTP (25):
  ```bash
  nmap -p 25 -sV 192.168.0.x
  ```
- FTP (21):
  ```bash
  nmap -p 21 -sV 192.168.0.x
  ```
- SFTP/SSH (22):
  ```bash
  nmap -p 22 -sV 192.168.0.x
  ```

### Open vs. closed ports
```bash
nmap 192.168.0.x                  # shows open/closed/filtered
nmap -p 1-1024 --reason 192.168.0.x
```

### OS detection and service versions
```bash
nmap -O 192.168.0.x               # OS detection
nmap -sV 192.168.0.x              # service/version
nmap -A 192.168.0.x               # aggressive: OS, versions, scripts, traceroute
```

### Scan IP ranges / networks
```bash
nmap 192.168.0.10-50              # range of IPs
nmap 192.168.0.0/24               # entire /24 subnet
nmap -sn 192.168.0.0/24           # host discovery only (ping sweep)
```

### Useful options
- `-Pn`: skip host discovery (treat hosts as online)
- `-T4`: faster timing (be mindful of false positives/network impact)
- `-oN results.txt`: save output; also `-oG` (grepable), `-oX` (XML)

### Safety and etiquette
- Only scan systems you own or are authorized to test.
- Coordinate with network owners; heavy scans can trigger alerts.


