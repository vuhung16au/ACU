# Network Reconnaissance - Results Summary

This file contains the summarized findings from all network reconnaissance tasks.

---


## Task 1: Network Discovery and Host Enumeration

**Timestamp:** 2025-11-22 16:56:49

**Summary:**
- Total active hosts discovered:        3
- Network scanned: 172.20.0.0/24
- Target identified: 172.20.0.10 (Metasploitable2)

**Active Hosts:**
```
Nmap scan report for 172.20.0.1
Host is up (0.000040s latency).
--
Nmap scan report for metasploitable2.q4_pentest-net (172.20.0.10)
Host is up (0.000034s latency).
--
Nmap scan report for kali (172.20.0.5)
Host is up.
```

**Key Findings:**
- Successfully identified Metasploitable2 container at 172.20.0.10
- Network discovery completed without port scanning (stealth host enumeration)
- Ping sweep reveals live systems on the network segment

**Response Times:**
Host is up (0.000040s latency).
Host is up (0.000034s latency).
Host is up.

---


## Task 2: Service Version Detection

**Timestamp:** 2025-11-22 16:56:54

**Summary:**
- Target IP: 172.20.0.10 (Metasploitable2)
- Total services detected: 21
- Scan type: Service version detection on top 1000 ports
- Timing: Aggressive (-T4)

**Services Detected:**
```
21/tcp   open  ftp         vsftpd 2.3.4
22/tcp   open  ssh         OpenSSH 4.7p1 Debian 8ubuntu1 (protocol 2.0)
23/tcp   open  telnet      Linux telnetd
25/tcp   open  smtp        Postfix smtpd
80/tcp   open  http        Apache httpd 2.2.8 ((Ubuntu) DAV/2)
111/tcp  open  rpcbind     2 (RPC #100000)
139/tcp  open  netbios-ssn Samba smbd 3.X - 4.X (workgroup: WORKGROUP)
445/tcp  open  netbios-ssn Samba smbd 3.X - 4.X (workgroup: WORKGROUP)
512/tcp  open  exec?
513/tcp  open  login
514/tcp  open  tcpwrapped
1099/tcp open  java-rmi    GNU Classpath grmiregistry
1524/tcp open  ingreslock?
2121/tcp open  ftp         ProFTPD 1.3.1
3306/tcp open  mysql       MySQL 5.0.51a-3ubuntu5
5432/tcp open  postgresql  PostgreSQL DB 8.3.0 - 8.3.7
5900/tcp open  vnc         VNC (protocol 3.3)
6000/tcp open  X11         (access denied)
6667/tcp open  irc         UnrealIRCd
8009/tcp open  ajp13       Apache Jserv (Protocol v1.3)
8180/tcp open  http        Apache Tomcat/Coyote JSP engine 1.1
```

**Key Findings:**

The scan revealed multiple services running on the target system. Notable findings include:

- **Port 21/tcp:** ftp vsftpd 2.3.4 
- **Port 22/tcp:** ssh OpenSSH 4.7p1 Debian
- **Port 23/tcp:** telnet Linux telnetd 

**Security Implications:**
- Multiple network services are exposed, increasing the attack surface
- Version information can be used to identify known vulnerabilities
- Older service versions may contain unpatched security flaws
- Service banners provide valuable information for attackers

**Recommendations:**
- Disable unnecessary services to reduce attack surface
- Update services to latest secure versions
- Implement network segmentation and access controls
- Monitor service logs for suspicious activity

---


## Task 3: Operating System Fingerprinting

**Timestamp:** 2025-11-22 16:59:32

**Summary:**
- Target IP: 172.20.0.10 (Metasploitable2)
- Scan type: Operating System Detection
- Method: TCP/IP stack fingerprinting

**Operating System Information:**
```
Running: Linux 5.X
OS CPE: cpe:/o:linux:linux_kernel:5
OS details: Linux 5.0 - 5.14
Network Distance: 1 hop

OS detection performed. Please report any incorrect results at https://nmap.org/submit/ .
Nmap done: 1 IP address (1 host up) scanned in 1.68 seconds
```

**Key Findings:**

- **Operating System:** Linux-based system detected
- **OS Family:** Unix/Linux

**OS Detection Details:**
Running: Linux 5.X
OS details: Linux 5.0 - 5.14

**How OS Detection Works:**
- Analyzes TCP/IP stack behavior and responses
- Examines TCP window sizes, options, and sequencing
- Compares TTL values and IP header flags
- Matches patterns against a database of known OS fingerprints

**Security Implications:**
- OS version information helps identify platform-specific vulnerabilities
- Attackers can target OS-specific exploits based on this information
- Outdated OS versions may indicate lack of security patching
- OS fingerprinting is a passive technique that's difficult to detect

**Defensive Measures:**
- Use OS fingerprint obfuscation tools if needed
- Keep systems updated with latest security patches
- Implement network segmentation to limit scanning
- Monitor for repeated fingerprinting attempts

---


## Task 4: Advanced Port Scanning Techniques

**Timestamp:** 2025-11-22 16:59:37

**Summary:**
- Target IP: 172.20.0.10 (Metasploitable2)
- Two scan types performed: SYN Stealth (-sS) and TCP Connect (-sT)
- Timing: Aggressive (-T4)

### Scan Results Comparison

| Scan Type | Open Ports Found | Connection Method |
|-----------|------------------|-------------------|
| SYN Stealth | 21 | Half-open (doesn't complete handshake) |
| TCP Connect | 21 | Full connection (completes handshake) |

### SYN Stealth Scan Ports
```
21/tcp   open  ftp
22/tcp   open  ssh
23/tcp   open  telnet
25/tcp   open  smtp
80/tcp   open  http
111/tcp  open  rpcbind
139/tcp  open  netbios-ssn
445/tcp  open  microsoft-ds
512/tcp  open  exec
513/tcp  open  login
514/tcp  open  shell
1099/tcp open  rmiregistry
1524/tcp open  ingreslock
2121/tcp open  ccproxy-ftp
3306/tcp open  mysql
5432/tcp open  postgresql
5900/tcp open  vnc
6000/tcp open  X11
6667/tcp open  irc
8009/tcp open  ajp13
```

### TCP Connect Scan Ports
```
21/tcp   open  ftp
22/tcp   open  ssh
23/tcp   open  telnet
25/tcp   open  smtp
80/tcp   open  http
111/tcp  open  rpcbind
139/tcp  open  netbios-ssn
445/tcp  open  microsoft-ds
512/tcp  open  exec
513/tcp  open  login
514/tcp  open  shell
1099/tcp open  rmiregistry
1524/tcp open  ingreslock
2121/tcp open  ccproxy-ftp
3306/tcp open  mysql
5432/tcp open  postgresql
5900/tcp open  vnc
6000/tcp open  X11
6667/tcp open  irc
8009/tcp open  ajp13
```

### Key Differences Between Scan Types

**SYN Stealth Scan (-sS):**
- ✓ **Stealthier:** Doesn't complete TCP handshake, less likely to be logged
- ✓ **Faster:** Requires fewer packets (SYN → SYN/ACK → RST)
- ✓ **Lower footprint:** May evade simple IDS/IPS rules
- ✗ **Requires privileges:** Needs root/admin to craft raw packets
- ✗ **Not invisible:** Modern IDS can detect SYN scans
- Uses raw sockets to send crafted SYN packets
- Doesn't appear in application logs (only connection attempts)

**TCP Connect Scan (-sT):**
- ✓ **No privileges needed:** Uses standard OS socket API
- ✓ **More reliable:** Full connection ensures accurate results
- ✓ **Works everywhere:** No raw socket requirement
- ✗ **More detectable:** Full connections are always logged
- ✗ **Slower:** More packets required (full 3-way handshake + close)
- ✗ **Higher footprint:** Easy to detect and trace
- Creates complete connection records in logs
- Applications may log these connection attempts

### Why SYN Scanning is "Stealthier"

1. **Incomplete Handshake:** Never completes TCP connection, so some applications don't log it
2. **Fewer Packets:** Less network traffic = smaller detection footprint
3. **Faster Execution:** Quick RST after SYN/ACK reduces exposure time
4. **Historical Evasion:** Older firewalls and IDS systems didn't detect half-open connections

**Important Note:** Modern security systems (IDS/IPS, firewalls, SIEM) detect BOTH types:
- SYN floods and scan patterns are well-known attack signatures
- Connection logs capture both complete and incomplete handshakes
- Neither technique is truly "invisible" to modern defenses

### Security Implications

**For Attackers:**
- SYN scans can reduce detection probability against legacy systems
- Both methods reveal open ports and services
- Scan timing and patterns are often more important than scan type

**For Defenders:**
- Monitor for both SYN scan patterns and unusual connection attempts
- Use IDS/IPS to detect port scanning behavior
- Implement rate limiting and connection thresholds
- Log and analyze both successful and failed connection attempts
- Use tools like fail2ban or iptables recent module to block scanners

---


## Task 5: Vulnerability Assessment with NSE Scripts

**Timestamp:** 2025-11-22 16:59:43

**Summary:**
- Target IP: 172.20.0.10 (Metasploitable2)
- Scan type: NSE vulnerability assessment with default scripts
- Total services analyzed: 21
- Potential vulnerabilities identified: 2+

### Vulnerability Findings

**Critical Security Concerns Detected:**

```
PORT     STATE SERVICE     VERSION
21/tcp   open  ftp         vsftpd 2.3.4
|_ftp-anon: Anonymous FTP login allowed (FTP code 230)
| ftp-syst: 
|   STAT: 
| FTP server status:
|      Connected to 172.20.0.5
|      Logged in as ftp
--
|   authentication_level: user
|   challenge_response: supported
|_  message_signing: disabled (dangerous, but default)

Service detection performed. Please report any incorrect results at https://nmap.org/submit/ .
Nmap done: 1 IP address (1 host up) scanned in 162.87 seconds
```

### Top Vulnerabilities Identified

Based on the NSE scan results, at least two significant security concerns were identified:

**1. Anonymous Access / Weak Authentication**
- Services allowing anonymous access were detected
- This allows unauthorized users to access resources
- Affected services: 
|_ftp-anon: Anonymous FTP login allowed (FTP code 230)

**Security Impact:**
- Unauthorized access to sensitive data
- Potential for data exfiltration
- Lateral movement opportunity for attackers

**2. Outdated/Vulnerable Service Versions**
- Multiple outdated services detected that may have known vulnerabilities

**Security Impact:**
- Known exploits can be used to compromise the system
- Privilege escalation opportunities
- Remote code execution potential
- Complete system compromise possible

### Additional Security Concerns

**Service Exposure:**
- Multiple network services are unnecessarily exposed
- Each service increases the attack surface
- Some services have default configurations

**Information Disclosure:**
- Service banners reveal detailed version information
- Helps attackers identify specific exploits
- Facilitates targeted attack planning

### Security Implications Summary

**Risk Level: HIGH**

The target system exhibits multiple critical vulnerabilities:

1. **Authentication Weaknesses:** Anonymous or weak authentication allows unauthorized access
2. **Vulnerable Software:** Outdated versions with known exploits
3. **Information Disclosure:** Detailed service information aids attackers
4. **Large Attack Surface:** Multiple exposed services increase risk
5. **Default Configurations:** Services running with insecure defaults

### Recommendations

**Immediate Actions:**
1. Disable anonymous access on all services
2. Update all services to latest secure versions
3. Remove or disable unnecessary network services
4. Implement strong authentication mechanisms
5. Configure service banners to minimize information disclosure

**Long-term Security Measures:**
1. Implement network segmentation and access controls
2. Deploy intrusion detection/prevention systems (IDS/IPS)
3. Regular vulnerability scanning and patch management
4. Security hardening following CIS benchmarks
5. Continuous monitoring and logging
6. Implement principle of least privilege
7. Regular security audits and penetration testing

**Defense in Depth:**
- Firewall rules to restrict service access
- VPN for remote access instead of direct exposure
- Multi-factor authentication where applicable
- Security Information and Event Management (SIEM)
- Regular security awareness training

### NSE Script Categories Used

The default scripts include checks for:
- **Auth:** Authentication and authorization issues
- **Broadcast:** Network broadcast information
- **Default:** Safe default scripts for common vulnerabilities
- **Discovery:** Service and system information gathering
- **Safe:** Scripts unlikely to crash or affect the target
- **Version:** Detailed version information gathering

---

