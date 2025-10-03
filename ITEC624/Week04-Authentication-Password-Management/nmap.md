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



---

### Comprehensive list of commands and usage

Here’s a comprehensive list of nmap-related commands with concise explanations of their structure, options, and purposes. This augments the quick guide above and adds additional examples and output options.

### Nmap commands and examples

1. man nmap
   - Description: Opens the manual for nmap, detailing all usage, options, and documentation.
   - Options Used: None (manual open).

2. nmap 192.168.56.101
   - Description: Basic scan of a target IP, discovering open ports and services.
   - Options Used: None (default scan).

3. nmap -sP 192.168.56.0/24
   - Description: Ping scan to identify which hosts are up in a subnet.
   - Options Used:
     - -sP (ping scan): Deprecated; use -sn in recent nmap versions.
     - 192.168.56.0/24 (subnet notation): Scans all IPs in that subnet.

4. nmap 192.168.56.101 -sV
   - Description: Service version detection on a specific IP.
   - Options Used:
     - -sV: Attempts to determine service/version info on open ports.

5. nmap 192.168.56.101 -oN scan1
   - Description: Performs a scan and outputs results to scan1 in normal text format.
   - Options Used:
     - -oN scan1: Normal output to file scan1.

6. cat scan1
   - Description: Displays the scan output file created above (shell command; not an nmap flag).

### Additional nmap commands table

| Command                                   | Description                                                         | Options Used                        |
|-------------------------------------------|---------------------------------------------------------------------|-------------------------------------|
| nmap 192.168.237.129 -F                   | Scan 100 most common ports (Fast)                                   | -F                                  |
| nmap 192.168.237.129 -p-                  | Scan all 65535 ports                                                | -p-                                 |
| nmap 192.168.237.129 -p 1-100             | Scan ports 1 to 100                                                 | -p 1-100                            |
| nmap 192.168.237.129 -p 1-100,101-500     | Scan two port ranges: 1–100 and 101–500                             | -p 1-100,101-500                    |
| nmap 192.168.237.0/24 -sP                 | Ping scan of the network (use -sn instead in newer versions)        | -sP                                 |
| nmap 192.168.237.0/24                     | Scan a subnet                                                       | (CIDR, no extra options)            |
| nmap 192.168.237.129 -sS                  | SYN scan                                                            | -sS                                 |
| nmap 192.168.237.129 -sT                  | TCP connect scan                                                    | -sT                                 |
| nmap 192.168.237.129 -sV                  | Service version detection                                           | -sV                                 |
| nmap 192.168.237.129 -O                   | OS detection                                                        | -O                                  |
| nmap 192.168.237.129 -A                   | Aggressive mode (OS, version, script scan, traceroute)              | -A                                  |

#### Output options
- -oN [file]: Normal output format
- -oG [file]: Greppable format
- -oX [file]: XML format
- -oA [basename]: Output all formats at once

---

## Other ways to use nmap

- Basic host discovery: Find live hosts (nmap -sn [subnet])
- Scan custom port ranges: Only scan ports you care about (-p 80,443,8080)
- Detect OS and hardware: (-O)
- Save scan results: Output to file as needed (-oN, -oX, -oG, -oA)
- Evade firewalls: Use timing options or spoofing (-f, -D, -S)
- Run safe default scripts: (-sC)
- Scan IPv6: (nmap -6 [ipv6_target])
- Scan with custom timing: (-T4, -T5; faster, but may miss details)

## Advanced nmap techniques

- Script scanning & automation: Use the nmap scripting engine for vulnerability scans (-sC for default scripts, --script <scriptname> for specific ones)
  - Example: nmap --script=vuln [target]
- Decoy scanning: Hide your scan source (-D [decoy1,decoy2])
- Fragmentation: Evade some firewalls (-f)
- Spoof source IP: (-S [ip])
- Idle scan: Fully stealthy scanning using a zombie host (-sI [zombie_ip])
- Aggressive detection: (-A for OS, version, scripts, traceroute)
- Custom scan timing: (-T0 to -T5)
- UDP scan: (-sU)
- Scan IPv6 targets: (-6)
- Output for integration: Use XML or grep format (-oX, -oG) for scripting and automation integration

