On Linux, several powerful tools are designed for Wi-Fi security testing, auditing, and analysis. These tools primarily serve penetration testers, network administrators, and security researchers seeking to identify vulnerabilities or strengthen wireless defenses.

### Aircrack-ng
Aircrack-ng is a complete Wi-Fi security suite used to capture, analyze, and crack WEP, WPA, and WPA2 keys using monitoring and dictionary attacks. It supports de-authentication and replay attacks and works with any network interface card that supports monitor mode.[1]

### Kismet
Kismet is a passive wireless sniffer, detector, and intrusion detection system (WIDS). It detects hidden SSIDs, identifies access points, and logs packets across Wi-Fi, Bluetooth, and Zigbee, supporting GPS mapping and RF monitoring.[4][1]

### Fern Wi-Fi Cracker
Fern Wi-Fi Cracker provides a Python-based graphical interface for Wi-Fi penetration testing. It supports WEP/WPA/WPS cracking through ARP request replay, Caffe-Latte, and brute-force attacks. It can also exploit network protocols for session hijacking.[6][1]

### Wifite
Wifite automates Wi-Fi network attacks by chaining tools like Aircrack-ng, Reaver, and PixieWPS. It conducts simultaneous WEP/WPA/WPS brute-force, handshake capture, and pixie-dust attacks, prioritizing nearest access points.[1][6]

### PixieWPS
PixieWPS targets WPS vulnerabilities by brute-forcing PINs offline. It exploits weak entropy in routers, enabling fast key recovery during “pixie-dust” attacks, often integrated into Wifite workflows.[1]

### Wifiphisher
Wifiphisher is a rogue access point (Evil Twin) framework that enables man-in-the-middle (MitM) and phishing attacks on wireless clients. It can capture WPA credentials or inject malware by simulating legitimate networks with custom phishing templates.[5]

### Bettercap
Bettercap is an advanced network reconnaissance and MitM attack tool for wired and wireless environments. It captures packets, performs spoofing (ARP/DNS/NDP), and injects traffic, supporting BLE and HID device testing.[6]

### Wireshark
Wireshark is a network protocol analyzer used to intercept and decode packet traffic in real-time. It supports detailed inspection of wireless frames, encrypted TLS analysis, and packet filtering, making it essential for Wi-Fi diagnostics and protocol-level auditing.[8][1]

### Supporting Security Tools
Complementary Linux tools such as **UFW** (firewall management), **Snort**, and **Suricata** (intrusion detection and prevention) are valuable for defending networks and monitoring malicious activity post-audit.[2]

Overall, these tools form a robust toolkit for offensive and defensive Wi-Fi security analysis, commonly bundled with **Kali Linux** or installable on any Debian-based distribution.[3][9]

[1](https://www.infosecinstitute.com/resources/penetration-testing/kali-linux-top-8-tools-for-wireless-attacks/)
[2](https://tuxcare.com/blog/linux-security-tools/)
[3](https://www.kali.org/tools/)
[4](https://www.kismetwireless.net)
[5](https://github.com/wifiphisher/wifiphisher)
[6](https://www.techtarget.com/searchsecurity/tip/Top-Kali-Linux-tools-and-how-to-use-them)
[7](https://nccs.gov.in/public/events/Wi-Fi_Network_Analysis_Tools_v1.0.pdf)
[8](https://www.wireshark.org)
[9](https://www.kali.org)