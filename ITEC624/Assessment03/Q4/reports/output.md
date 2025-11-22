# Network Reconnaissance - Command Outputs

This file contains the raw outputs from all network reconnaissance tasks.

---


## Task 1: Network Discovery and Host Enumeration

**Timestamp:** 2025-11-22 16:56:49

**Command:** `nmap -sn 172.20.0.0/24`

**Raw Output:**
```
Starting Nmap 7.95 ( https://nmap.org ) at 2025-11-22 05:56 UTC
Nmap scan report for 172.20.0.1
Host is up (0.000040s latency).
MAC Address: 3A:89:EB:94:70:2E (Unknown)
Nmap scan report for metasploitable2.q4_pentest-net (172.20.0.10)
Host is up (0.000034s latency).
MAC Address: 7E:D1:75:55:02:5D (Unknown)
Nmap scan report for kali (172.20.0.5)
Host is up.
Nmap done: 256 IP addresses (3 hosts up) scanned in 2.04 seconds
```

---


## Task 2: Service Version Detection

**Timestamp:** 2025-11-22 16:56:54

**Command:** `nmap -sV -T4 172.20.0.10`

**Raw Output:**
```
Starting Nmap 7.95 ( https://nmap.org ) at 2025-11-22 05:56 UTC
Nmap scan report for metasploitable2.q4_pentest-net (172.20.0.10)
Host is up (0.0000040s latency).
Not shown: 979 closed tcp ports (reset)
PORT     STATE SERVICE     VERSION
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
1 service unrecognized despite returning data. If you know the service/version, please submit the following fingerprint at https://nmap.org/cgi-bin/submit.cgi?new-service :
SF-Port1524-TCP:V=7.95%I=7%D=11/22%Time=692150AD%P=aarch64-unknown-linux-g
SF:nu%r(NULL,2E,"\x1b\]0;@metasploitable:\x20/\x07root@metasploitable:/#\x
SF:20")%r(GenericLines,EA,"\x1b\]0;@metasploitable:\x20/\x07root@metasploi
SF:table:/#\x20\n\x1b\]0;@metasploitable:\x20/\x07root@metasploitable:/#\x
SF:20\n\x1b\]0;@metasploitable:\x20/\x07root@metasploitable:/#\x20\n\x1b\]
SF:0;@metasploitable:\x20/\x07root@metasploitable:/#\x20\n\x1b\]0;@metaspl
SF:oitable:\x20/\x07root@metasploitable:/#\x20")%r(GetRequest,51B,"\x1b\]0
SF:;@metasploitable:\x20/\x07root@metasploitable:/#\x20GET\x20/\x20HTTP/1\
SF:.0\n<HTML>\n<HEAD>\n<TITLE>Directory\x20/</TITLE>\n<BASE\x20HREF=\"file
SF::/\">\n</HEAD>\n<BODY>\n<H1>Directory\x20listing\x20of\x20/</H1>\n<UL>\
SF:n<LI><A\x20HREF=\"\./\">\./</A>\n<LI><A\x20HREF=\"\.\./\">\.\./</A>\n<L
SF:I><A\x20HREF=\"\.dockerenv\">\.dockerenv</A>\n<LI><A\x20HREF=\"bin/\">b
SF:in/</A>\n<LI><A\x20HREF=\"boot/\">boot/</A>\n<LI><A\x20HREF=\"cdrom/\">
SF:cdrom/</A>\n<LI><A\x20HREF=\"core\">core</A>\n<LI><A\x20HREF=\"dev/\">d
SF:ev/</A>\n<LI><A\x20HREF=\"etc/\">etc/</A>\n<LI><A\x20HREF=\"home/\">hom
SF:e/</A>\n<LI><A\x20HREF=\"initrd/\">initrd/</A>\n<LI><A\x20HREF=\"initrd
SF:\.img\">initrd\.img</A>\n<LI><A\x20HREF=\"lib/\">lib/</A>\n<LI><A\x20HR
SF:EF=\"lost%2Bfound/\">lost\+found/</A>\n<LI><A\x20HREF=\"media/\">media/
SF:</A>\n<LI><A\x20HREF=\"mnt/\">mnt/</A>\n<LI><A\x20HREF=\"nohup\.out\">n
SF:ohup\.out</A>\n<LI><A\x20HREF=\"opt/\">opt/</A>\n<LI><A\x20HREF=\"proc/
SF:\">proc/</A>\n<LI><A\x20HREF=\"root/\">root/</A>\n<LI><A\x20HREF=\"sbin
SF:/\">sbin/</A>\n<LI><A\x20HREF=\"srv/\">srv/</A>\n<LI><A\x20HREF=\"sys/\
SF:">sys/</A>\n<LI><A\x20HREF=\"")%r(HTTPOptions,11D,"\x1b\]0;@metasploita
SF:ble:\x20/\x07root@metasploitable:/#\x20OPTIONS\x20/\x20HTTP/1\.0\nbash:
SF:\x20OPTIONS:\x20command\x20not\x20found\n\x1b\]0;@metasploitable:\x20/\
SF:x07root@metasploitable:/#\x20\n\x1b\]0;@metasploitable:\x20/\x07root@me
SF:tasploitable:/#\x20\n\x1b\]0;@metasploitable:\x20/\x07root@metasploitab
SF:le:/#\x20\n\x1b\]0;@metasploitable:\x20/\x07root@metasploitable:/#\x20"
SF:)%r(RTSPRequest,11D,"\x1b\]0;@metasploitable:\x20/\x07root@metasploitab
SF:le:/#\x20OPTIONS\x20/\x20RTSP/1\.0\nbash:\x20OPTIONS:\x20command\x20not
SF:\x20found\n\x1b\]0;@metasploitable:\x20/\x07root@metasploitable:/#\x20\
SF:n\x1b\]0;@metasploitable:\x20/\x07root@metasploitable:/#\x20\n\x1b\]0;@
SF:metasploitable:\x20/\x07root@metasploitable:/#\x20\n\x1b\]0;@metasploit
SF:able:\x20/\x07root@metasploitable:/#\x20");
MAC Address: 7E:D1:75:55:02:5D (Unknown)
Service Info: Hosts:  metasploitable.localdomain, irc.Metasploitable.LAN; OSs: Unix, Linux; CPE: cpe:/o:linux:linux_kernel

Service detection performed. Please report any incorrect results at https://nmap.org/submit/ .
Nmap done: 1 IP address (1 host up) scanned in 154.57 seconds
```

---


## Task 3: Operating System Fingerprinting

**Timestamp:** 2025-11-22 16:59:32

**Command:** `nmap -O 172.20.0.10`

**Raw Output:**
```
Starting Nmap 7.95 ( https://nmap.org ) at 2025-11-22 05:59 UTC
Nmap scan report for metasploitable2.q4_pentest-net (172.20.0.10)
Host is up (0.0021s latency).
Not shown: 979 closed tcp ports (reset)
PORT     STATE SERVICE
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
8180/tcp open  unknown
MAC Address: 7E:D1:75:55:02:5D (Unknown)
Device type: general purpose
Running: Linux 5.X
OS CPE: cpe:/o:linux:linux_kernel:5
OS details: Linux 5.0 - 5.14
Network Distance: 1 hop

OS detection performed. Please report any incorrect results at https://nmap.org/submit/ .
Nmap done: 1 IP address (1 host up) scanned in 1.68 seconds
```

---


## Task 4: Advanced Port Scanning Techniques

**Timestamp:** 2025-11-22 16:59:37

### SYN Stealth Scan Output

**Command:** `nmap -sS -T4 172.20.0.10`

**Raw Output:**
```
Starting Nmap 7.95 ( https://nmap.org ) at 2025-11-22 05:59 UTC
Nmap scan report for metasploitable2.q4_pentest-net (172.20.0.10)
Host is up (0.0000040s latency).
Not shown: 979 closed tcp ports (reset)
PORT     STATE SERVICE
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
8180/tcp open  unknown
MAC Address: 7E:D1:75:55:02:5D (Unknown)

Nmap done: 1 IP address (1 host up) scanned in 0.17 seconds
```

### TCP Connect Scan Output

**Command:** `nmap -sT -T4 172.20.0.10`

**Raw Output:**
```
Starting Nmap 7.95 ( https://nmap.org ) at 2025-11-22 05:59 UTC
Nmap scan report for metasploitable2.q4_pentest-net (172.20.0.10)
Host is up (0.000075s latency).
Not shown: 979 closed tcp ports (conn-refused)
PORT     STATE SERVICE
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
8180/tcp open  unknown
MAC Address: 7E:D1:75:55:02:5D (Unknown)

Nmap done: 1 IP address (1 host up) scanned in 0.10 seconds
```

---


## Task 5: Vulnerability Assessment with NSE Scripts

**Timestamp:** 2025-11-22 16:59:43

**Command:** `nmap -sC -sV -T4 172.20.0.10`

**Raw Output:**
```
Starting Nmap 7.95 ( https://nmap.org ) at 2025-11-22 05:59 UTC
Nmap scan report for metasploitable2.q4_pentest-net (172.20.0.10)
Host is up (0.000015s latency).
Not shown: 979 closed tcp ports (reset)
PORT     STATE SERVICE     VERSION
21/tcp   open  ftp         vsftpd 2.3.4
|_ftp-anon: Anonymous FTP login allowed (FTP code 230)
| ftp-syst: 
|   STAT: 
| FTP server status:
|      Connected to 172.20.0.5
|      Logged in as ftp
|      TYPE: ASCII
|      No session bandwidth limit
|      Session timeout in seconds is 300
|      Control connection is plain text
|      Data connections will be plain text
|      vsFTPd 2.3.4 - secure, fast, stable
|_End of status
22/tcp   open  ssh         OpenSSH 4.7p1 Debian 8ubuntu1 (protocol 2.0)
| ssh-hostkey: 
|   1024 60:0f:cf:e1:c0:5f:6a:74:d6:90:24:fa:c4:d5:6c:cd (DSA)
|_  2048 56:56:24:0f:21:1d:de:a7:2b:ae:61:b1:24:3d:e8:f3 (RSA)
23/tcp   open  telnet      Linux telnetd
25/tcp   open  smtp        Postfix smtpd
| sslv2: 
|   SSLv2 supported
|   ciphers: 
|     SSL2_DES_192_EDE3_CBC_WITH_MD5
|     SSL2_RC4_128_WITH_MD5
|     SSL2_RC2_128_CBC_WITH_MD5
|     SSL2_RC4_128_EXPORT40_WITH_MD5
|     SSL2_RC2_128_CBC_EXPORT40_WITH_MD5
|_    SSL2_DES_64_CBC_WITH_MD5
|_ssl-date: 2025-11-22T06:02:25+00:00; 0s from scanner time.
|_smtp-commands: metasploitable.localdomain, PIPELINING, SIZE 10240000, VRFY, ETRN, STARTTLS, ENHANCEDSTATUSCODES, 8BITMIME, DSN
80/tcp   open  http        Apache httpd 2.2.8 ((Ubuntu) DAV/2)
|_http-server-header: Apache/2.2.8 (Ubuntu) DAV/2
|_http-title: Metasploitable2 - Linux
111/tcp  open  rpcbind     2 (RPC #100000)
| rpcinfo: 
|   program version    port/proto  service
|   100000  2            111/tcp   rpcbind
|   100003  2,3,4       2049/tcp   nfs
|   100003  2,3,4       2049/udp   nfs
|   100005  1,2,3      50419/tcp   mountd
|   100005  1,2,3      58275/udp   mountd
|   100021  1,3,4      33414/tcp   nlockmgr
|   100021  1,3,4      58022/udp   nlockmgr
|   100024  1          42939/tcp   status
|_  100024  1          49024/udp   status
139/tcp  open  netbios-ssn Samba smbd 3.X - 4.X (workgroup: WORKGROUP)
445/tcp  open  netbios-ssn Samba smbd 3.0.20-Debian (workgroup: WORKGROUP)
512/tcp  open  exec?
513/tcp  open  login
514/tcp  open  tcpwrapped
1099/tcp open  java-rmi    GNU Classpath grmiregistry
1524/tcp open  ingreslock?
| fingerprint-strings: 
|   GenericLines: 
|     ]0;@metasploitable: /
|     root@metasploitable:/# 
|     ]0;@metasploitable: /
|     root@metasploitable:/# 
|     ]0;@metasploitable: /
|     root@metasploitable:/# 
|     ]0;@metasploitable: /
|     root@metasploitable:/# 
|     ]0;@metasploitable: /
|     root@metasploitable:/#
|   GetRequest: 
|     ]0;@metasploitable: /
|     root@metasploitable:/# GET / HTTP/1.0
|     <HTML>
|     <HEAD>
|     <TITLE>Directory /</TITLE>
|     <BASE HREF="file:/">
|     </HEAD>
|     <BODY>
|     <H1>Directory listing of /</H1>
|     <UL>
|     <LI><A HREF="./">./</A>
|     <LI><A HREF="../">../</A>
|     <LI><A HREF=".dockerenv">.dockerenv</A>
|     <LI><A HREF="bin/">bin/</A>
|     <LI><A HREF="boot/">boot/</A>
|     <LI><A HREF="cdrom/">cdrom/</A>
|     <LI><A HREF="core">core</A>
|     <LI><A HREF="dev/">dev/</A>
|     <LI><A HREF="etc/">etc/</A>
|     <LI><A HREF="home/">home/</A>
|     <LI><A HREF="initrd/">initrd/</A>
|     <LI><A HREF="initrd.img">initrd.img</A>
|     <LI><A HREF="lib/">lib/</A>
|     <LI><A HREF="lost%2Bfound/">lost+found/</A>
|     <LI><A HREF="media/">media/</A>
|     <LI><A HREF="mnt/">mnt/</A>
|     <LI><A HREF="nohup.out">nohup.out</A>
|     <LI><A HREF="opt/">opt/</A>
|     <LI><A HREF="proc/">proc/</A>
|     <LI><A HREF="root/">root/</A>
|     <LI><A HREF="sbin/">sbin/</A>
|     <LI><A HREF="srv/">srv/</A>
|     <LI><A HREF="sys/">sys/</A>
|     <LI><A HREF="
|   HTTPOptions: 
|     ]0;@metasploitable: /
|     root@metasploitable:/# OPTIONS / HTTP/1.0
|     bash: OPTIONS: command not found
|     ]0;@metasploitable: /
|     root@metasploitable:/# 
|     ]0;@metasploitable: /
|     root@metasploitable:/# 
|     ]0;@metasploitable: /
|     root@metasploitable:/# 
|     ]0;@metasploitable: /
|     root@metasploitable:/#
|   NULL: 
|     ]0;@metasploitable: /
|     root@metasploitable:/#
|   RTSPRequest: 
|     ]0;@metasploitable: /
|     root@metasploitable:/# OPTIONS / RTSP/1.0
|     bash: OPTIONS: command not found
|     ]0;@metasploitable: /
|     root@metasploitable:/# 
|     ]0;@metasploitable: /
|     root@metasploitable:/# 
|     ]0;@metasploitable: /
|     root@metasploitable:/# 
|     ]0;@metasploitable: /
|_    root@metasploitable:/#
2121/tcp open  ftp         ProFTPD 1.3.1
3306/tcp open  mysql       MySQL 5.0.51a-3ubuntu5
| mysql-info: 
|   Protocol: 10
|   Version: 5.0.51a-3ubuntu5
|   Thread ID: 10
|   Capabilities flags: 43564
|   Some Capabilities: ConnectWithDatabase, Support41Auth, SupportsCompression, SwitchToSSLAfterHandshake, SupportsTransactions, Speaks41ProtocolNew, LongColumnFlag
|   Status: Autocommit
|_  Salt: B_739dmu%RR%^-c/ZZ7@
5432/tcp open  postgresql  PostgreSQL DB 8.3.0 - 8.3.7
|_ssl-date: 2025-11-22T06:02:24+00:00; 0s from scanner time.
5900/tcp open  vnc         VNC (protocol 3.3)
| vnc-info: 
|   Protocol version: 3.3
|   Security types: 
|_    VNC Authentication (2)
6000/tcp open  X11         (access denied)
6667/tcp open  irc         UnrealIRCd
| irc-info: 
|   users: 1
|   servers: 1
|   lusers: 1
|   lservers: 0
|   server: irc.Metasploitable.LAN
|   version: Unreal3.2.8.1. irc.Metasploitable.LAN 
|   uptime: 0 days, 0:07:26
|   source ident: nmap
|   source host: 602E07B6.802FA63B.4F589F96.IP
|_  error: Closing Link: jzzjqxwax[172.20.0.5] (Quit: jzzjqxwax)
8009/tcp open  ajp13       Apache Jserv (Protocol v1.3)
|_ajp-methods: Failed to get a valid response for the OPTION request
8180/tcp open  http        Apache Tomcat/Coyote JSP engine 1.1
|_http-favicon: Apache Tomcat
|_http-server-header: Apache-Coyote/1.1
|_http-title: Apache Tomcat/5.5
1 service unrecognized despite returning data. If you know the service/version, please submit the following fingerprint at https://nmap.org/cgi-bin/submit.cgi?new-service :
SF-Port1524-TCP:V=7.95%I=7%D=11/22%Time=69215156%P=aarch64-unknown-linux-g
SF:nu%r(NULL,2E,"\x1b\]0;@metasploitable:\x20/\x07root@metasploitable:/#\x
SF:20")%r(GenericLines,EA,"\x1b\]0;@metasploitable:\x20/\x07root@metasploi
SF:table:/#\x20\n\x1b\]0;@metasploitable:\x20/\x07root@metasploitable:/#\x
SF:20\n\x1b\]0;@metasploitable:\x20/\x07root@metasploitable:/#\x20\n\x1b\]
SF:0;@metasploitable:\x20/\x07root@metasploitable:/#\x20\n\x1b\]0;@metaspl
SF:oitable:\x20/\x07root@metasploitable:/#\x20")%r(GetRequest,51B,"\x1b\]0
SF:;@metasploitable:\x20/\x07root@metasploitable:/#\x20GET\x20/\x20HTTP/1\
SF:.0\n<HTML>\n<HEAD>\n<TITLE>Directory\x20/</TITLE>\n<BASE\x20HREF=\"file
SF::/\">\n</HEAD>\n<BODY>\n<H1>Directory\x20listing\x20of\x20/</H1>\n<UL>\
SF:n<LI><A\x20HREF=\"\./\">\./</A>\n<LI><A\x20HREF=\"\.\./\">\.\./</A>\n<L
SF:I><A\x20HREF=\"\.dockerenv\">\.dockerenv</A>\n<LI><A\x20HREF=\"bin/\">b
SF:in/</A>\n<LI><A\x20HREF=\"boot/\">boot/</A>\n<LI><A\x20HREF=\"cdrom/\">
SF:cdrom/</A>\n<LI><A\x20HREF=\"core\">core</A>\n<LI><A\x20HREF=\"dev/\">d
SF:ev/</A>\n<LI><A\x20HREF=\"etc/\">etc/</A>\n<LI><A\x20HREF=\"home/\">hom
SF:e/</A>\n<LI><A\x20HREF=\"initrd/\">initrd/</A>\n<LI><A\x20HREF=\"initrd
SF:\.img\">initrd\.img</A>\n<LI><A\x20HREF=\"lib/\">lib/</A>\n<LI><A\x20HR
SF:EF=\"lost%2Bfound/\">lost\+found/</A>\n<LI><A\x20HREF=\"media/\">media/
SF:</A>\n<LI><A\x20HREF=\"mnt/\">mnt/</A>\n<LI><A\x20HREF=\"nohup\.out\">n
SF:ohup\.out</A>\n<LI><A\x20HREF=\"opt/\">opt/</A>\n<LI><A\x20HREF=\"proc/
SF:\">proc/</A>\n<LI><A\x20HREF=\"root/\">root/</A>\n<LI><A\x20HREF=\"sbin
SF:/\">sbin/</A>\n<LI><A\x20HREF=\"srv/\">srv/</A>\n<LI><A\x20HREF=\"sys/\
SF:">sys/</A>\n<LI><A\x20HREF=\"")%r(HTTPOptions,11D,"\x1b\]0;@metasploita
SF:ble:\x20/\x07root@metasploitable:/#\x20OPTIONS\x20/\x20HTTP/1\.0\nbash:
SF:\x20OPTIONS:\x20command\x20not\x20found\n\x1b\]0;@metasploitable:\x20/\
SF:x07root@metasploitable:/#\x20\n\x1b\]0;@metasploitable:\x20/\x07root@me
SF:tasploitable:/#\x20\n\x1b\]0;@metasploitable:\x20/\x07root@metasploitab
SF:le:/#\x20\n\x1b\]0;@metasploitable:\x20/\x07root@metasploitable:/#\x20"
SF:)%r(RTSPRequest,11D,"\x1b\]0;@metasploitable:\x20/\x07root@metasploitab
SF:le:/#\x20OPTIONS\x20/\x20RTSP/1\.0\nbash:\x20OPTIONS:\x20command\x20not
SF:\x20found\n\x1b\]0;@metasploitable:\x20/\x07root@metasploitable:/#\x20\
SF:n\x1b\]0;@metasploitable:\x20/\x07root@metasploitable:/#\x20\n\x1b\]0;@
SF:metasploitable:\x20/\x07root@metasploitable:/#\x20\n\x1b\]0;@metasploit
SF:able:\x20/\x07root@metasploitable:/#\x20");
MAC Address: 7E:D1:75:55:02:5D (Unknown)
Service Info: Hosts:  metasploitable.localdomain, irc.Metasploitable.LAN; OSs: Unix, Linux; CPE: cpe:/o:linux:linux_kernel

Host script results:
|_clock-skew: mean: 1h15m00s, deviation: 2h30m01s, median: 0s
| smb-os-discovery: 
|   OS: Unix (Samba 3.0.20-Debian)
|   Computer name: metasploitable
|   NetBIOS computer name: 
|   Domain name: 
|   FQDN: metasploitable
|_  System time: 2025-11-22T01:02:18-05:00
|_nbstat: NetBIOS name: METASPLOITABLE, NetBIOS user: <unknown>, NetBIOS MAC: <unknown> (unknown)
|_smb2-time: Protocol negotiation failed (SMB2)
| smb-security-mode: 
|   account_used: <blank>
|   authentication_level: user
|   challenge_response: supported
|_  message_signing: disabled (dangerous, but default)

Service detection performed. Please report any incorrect results at https://nmap.org/submit/ .
Nmap done: 1 IP address (1 host up) scanned in 162.87 seconds
```

---

