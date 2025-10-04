Here's a quick, educational tutorial to set up Metasploitable2 using Docker for pentesting:

**Step 1: Install Docker**
- Make sure Docker is installed and running on your system.

**Step 2: Pull the Metasploitable2 Image**
```bash
docker pull tleemcjr/metasploitable2
```
*This downloads the vulnerable Metasploitable2 image.*

**Step 3: Run Metasploitable2 Container**
```bash
docker run -it --name metasploitable2 tleemcjr/metasploitable2
```
*This starts the container interactively, allowing you to access its shell.*

**Step 4: Find the Container's IP Address**
```bash
ifconfig
```
*You'll need the IP for pentesting (e.g., from Kali or another attack VM). Look for lines like `inet addr: 172.x.x.x`.*

**Step 5: Start Your Pentest Attack**
- Launch your attack tools (e.g. Nmap, Metasploit) from your attacker VM/container, targeting the Metasploitable2 IP.

**Extra**  
- For a complete lab, also run a Kali Linux container and place both in a custom Docker network for easier interaction.

**Educational Notes:**  
- Metasploitable2 is a purposely vulnerable Linux VM for practicing exploitation and enumeration.
- Running it in Docker is safe: containers are isolated and can be reset quickly.
- Always use Metasploitable2 in a controlled, non-production environment.

*This setup will get you started within minutes, ideal for beginners and anyone looking to hone their ethical hacking skills.*
