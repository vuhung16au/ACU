# Network Reconnaissance - Command Documentation

This file contains all commands used in the network reconnaissance tasks.

---


## Task 1: Network Discovery and Host Enumeration

**Timestamp:** 2025-11-22 16:56:49

**Command:**
```bash
nmap -sn 172.20.0.0/24
```

**Purpose:** Perform a ping sweep of the local network subnet to identify all active hosts without performing port scanning.

**Flags Explanation:**
- `-sn`: Ping scan - disable port scan, only do host discovery
- Network range: 172.20.0.0/24

**Target:** 172.20.0.0/24 (Metasploitable2 at 172.20.0.10)

---


## Task 2: Service Version Detection

**Timestamp:** 2025-11-22 16:56:54

**Command:**
```bash
nmap -sV -T4 172.20.0.10
```

**Purpose:** Perform service version detection on the most common 1000 ports to identify running services and their version numbers.

**Flags Explanation:**
- `-sV`: Version detection - probe open ports to determine service/version info
- `-T4`: Timing template (0-5, higher is faster) - aggressive timing for faster scans
- Target: 172.20.0.10

**Scan Duration:** Top 1000 most common ports

---


## Task 3: Operating System Fingerprinting

**Timestamp:** 2025-11-22 16:59:32

**Command:**
```bash
nmap -O 172.20.0.10
```

**Purpose:** Perform operating system detection using TCP/IP stack fingerprinting to identify the target's OS.

**Flags Explanation:**
- `-O`: Enable OS detection using TCP/IP fingerprinting
- Target: 172.20.0.10
- Note: Requires privileged access (uses privileged Docker container)

**Technique:** Nmap analyzes responses from the target system (TCP window sizes, IP TTL, TCP options, etc.) and compares them against a database of known OS fingerprints.

---


## Task 4: Advanced Port Scanning Techniques

**Timestamp:** 2025-11-22 16:59:37

### SYN Stealth Scan (Half-Open Scan)

**Command:**
```bash
nmap -sS -T4 172.20.0.10
```

**Flags Explanation:**
- `-sS`: SYN stealth scan (half-open scan) - sends SYN packets and analyzes responses without completing TCP handshake
- `-T4`: Aggressive timing template for faster scanning
- Target: 172.20.0.10

**How it works:** Sends SYN packet → receives SYN/ACK → sends RST (doesn't complete handshake)

### TCP Connect Scan (Full Connection)

**Command:**
```bash
nmap -sT -T4 172.20.0.10
```

**Flags Explanation:**
- `-sT`: TCP connect scan - completes full TCP three-way handshake
- `-T4`: Aggressive timing template
- Target: 172.20.0.10

**How it works:** Sends SYN → receives SYN/ACK → sends ACK → completes full connection → closes connection

---


## Task 5: Vulnerability Assessment with NSE Scripts

**Timestamp:** 2025-11-22 16:59:43

**Command:**
```bash
nmap -sC -sV -T4 172.20.0.10
```

**Purpose:** Use Nmap Scripting Engine (NSE) to run default vulnerability detection scripts against the target to identify security weaknesses.

**Flags Explanation:**
- `-sC`: Run default NSE scripts (equivalent to --script=default)
- `-sV`: Version detection (required for many vulnerability scripts)
- `-T4`: Aggressive timing for faster scanning
- Target: 172.20.0.10

**NSE Scripts Run:** The default script set includes:
- Authentication bypass checks
- Default credential tests
- Known vulnerability detection
- Information disclosure checks
- Security misconfiguration identification

---

