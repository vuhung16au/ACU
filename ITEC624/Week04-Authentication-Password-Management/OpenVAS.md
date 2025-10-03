# Quick Guide: Setting up OpenVAS Scanner

OpenVAS is a free, open-source vulnerability scanner used for assessing networks and systems. Here’s how you can get started with [openvas-scanner](https://github.com/greenbone/openvas-scanner).[1]

***

## 1. Installation (from Source)
**Requirements:** CMake, git, build tools, and dependencies (see full list in the repo’s `INSTALL.md`).

**Basic steps:**
```bash
git clone https://github.com/greenbone/openvas-scanner.git
cd openvas-scanner
cmake .
make install
```
*This builds and installs the scanner on your machine.*

**Alternate: Use Docker (Easier, Experimental)**
```bash
git clone https://github.com/greenbone/openvas-scanner.git
cd openvas-scanner
docker build -t openvas-local -f .docker/prod.Dockerfile .
```
Or pull an official Community Edition container (recommended for beginners):

Go to: https://www.greenbone.net/en/testnow for ready-made VMs/containers.

***

## 2. Basic Scanning with OpenVAS
OpenVAS is usually managed as part of the Greenbone Vulnerability Management (GVM) stack. 

**To scan a target:**
1. Start the scanner service if not already done:
   ```bash
   openvas
   ```
2. Use the `gvm-cli` or a GVM web GUI (e.g., Greenbone Security Assistant) for full scanning features:
   - Create a: Task → Configure Target Host/IP → Launch Scan
3. After scan completes, review reports in the GVM interface.

**Note:** The raw OpenVAS scanner isn’t usually operated by itself—it relies on the complete GVM system for managing tasks, scheduling, and reporting.

***

## Why Use OpenVAS?
- **Free + Frequently Updated:** Open source with a large vulnerability test feed
- **Versatile:** Scans networks, servers, applications

***

**Tip:** For the smoothest experience as a newcomer, try the Greenbone Community Edition virtual appliance or official container, which includes all the pieces preconfigured. 

For more options and advanced setup: check the [official OpenVAS documentation](https://greenbone.github.io/docs/).

[1](https://github.com/greenbone/openvas-scanner)