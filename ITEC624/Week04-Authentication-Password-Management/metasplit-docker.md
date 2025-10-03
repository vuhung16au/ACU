Here's a quick guide to setting up Metasploit Framework in Docker for pentesting:

**Step 1: Install Docker**
- Ensure Docker is installed and running on your system.

**Step 2: Pull the Metasploit Framework Docker Image**
```bash
docker pull metasploitframework/metasploit-framework
```
*This downloads the official Metasploit Framework image.*

**Step 3: Run the Metasploit Container**
```bash
docker run --rm -it metasploitframework/metasploit-framework
```
*This starts the container and drops you into an interactive shell with Metasploit.*

**Step 4: Start Metasploit Console**
```bash
msfconsole
```
*You're now in the Metasploit Framework, ready to perform scanning or exploitation.*

**(Optional) Persist Data and Map Ports**
To save your configuration, payloads, or open ports for reverse shells, add:
```bash
docker run --rm -it \
  -v ~/.msf4:/root/.msf4 \
  -v /tmp/msf:/tmp/data \
  -p 4444:4444 \
  metasploitframework/metasploit-framework
```
- `-v` maps host directories for persistent configs and artifacts.
- `-p` opens ports for incoming connections (e.g., reverse shells).

**Educational Note:**  
- Docker containers keep your pen-testing tools isolated and disposable.
- You can run multiple containers for different testing scenarios.

With these steps, you'll be ready to use Metasploit for ethical hacking and vulnerability assessments in minutes!
