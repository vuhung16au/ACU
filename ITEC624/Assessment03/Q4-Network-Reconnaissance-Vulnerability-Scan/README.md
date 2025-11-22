# Network Security Lab - Nmap Reconnaissance Assessment

A comprehensive Docker-based penetration testing lab using Kali Linux and Metasploitable 2 for learning network reconnaissance and vulnerability assessment with Nmap.

## 📋 Overview

This lab demonstrates five essential network security assessment tasks:

1. **Network Discovery** - Host enumeration using ping sweeps
2. **Service Version Detection** - Identifying running services and versions
3. **OS Fingerprinting** - Operating system detection
4. **Port Scanning Techniques** - Comparing SYN stealth vs TCP connect scans
5. **Vulnerability Assessment** - Using NSE scripts to identify security weaknesses

## 🔧 Prerequisites

### Required Software

- **Docker** (version 20.10 or higher)
  - Install: https://docs.docker.com/get-docker/
- **Docker Compose** (version 1.29 or higher)
  - Usually included with Docker Desktop
  - Install: https://docs.docker.com/compose/install/

### System Requirements

- **OS:** Linux, macOS, or Windows with WSL2
- **RAM:** Minimum 4GB, Recommended 8GB
- **Disk:** At least 5GB free space for Docker images
- **Network:** Internet connection for initial image download

### Verify Installation

```bash
# Check Docker
docker --version
docker info

# Check Docker Compose
docker-compose --version
# OR
docker compose version
```

## 🚀 Quick Start

### 1. Setup Environment

Run the setup script to initialize the lab environment:

```bash
cd Q4
chmod +x setup.sh
./setup.sh
```

This will:
- ✓ Check for Docker and Docker Compose installation
- ✓ Download Kali Linux and Metasploitable 2 images (if not present)
- ✓ Start both containers with proper networking
- ✓ Install nmap in Kali Linux container
- ✓ Create the reports directory structure
- ✓ Verify connectivity between containers

**First run may take 5-10 minutes to download images (~1.5GB total)**

### 2. Run All Tasks

Execute all five reconnaissance tasks sequentially:

```bash
chmod +x tasks-run-all.sh
./tasks-run-all.sh
```

This will run all tasks automatically and generate comprehensive reports.

### 3. View Results

```bash
# View summarized findings
cat reports/results.md

# View all commands used
cat reports/commands.md

# View raw outputs
cat reports/output.md

# Complete your reflection
nano reports/reflection.md
```

## 📁 Project Structure

```
Q4/
├── docker-compose.yml           # Docker container configuration
├── setup.sh                     # Environment setup script
├── cleanup.sh                   # Cleanup and teardown script
├── tasks-run-all.sh            # Master script to run all tasks
├── task01-pingsweep.sh         # Network discovery
├── task02-service-version.sh   # Service version detection
├── task03-os-detection.sh      # OS fingerprinting
├── task04-port-scanning.sh     # Port scanning comparison
├── task05-vulnerability-scan.sh # NSE vulnerability assessment
├── reports/                     # Auto-generated reports
│   ├── commands.md             # Command documentation
│   ├── output.md               # Raw command outputs
│   ├── results.md              # Analysis and findings
│   ├── reflection.md           # Your reflection (manual)
│   └── execution-summary-*.log # Execution logs
├── README.md                    # This file
└── PLAN-AT3-v1.md              # Project planning document
```

## 🔨 Individual Task Execution

You can run tasks individually for testing or re-running specific scans:

```bash
# Make scripts executable (one-time)
chmod +x task*.sh

# Run individual tasks
./task01-pingsweep.sh          # ~10 seconds
./task02-service-version.sh    # ~2-3 minutes
./task03-os-detection.sh       # ~30-60 seconds
./task04-port-scanning.sh      # ~4-6 minutes (runs 2 scans)
./task05-vulnerability-scan.sh # ~3-5 minutes
```

Each task is **idempotent** - you can safely run them multiple times. Results are appended with timestamps.

## 🐳 Docker Container Management

### Manual Container Control

```bash
# Start containers
docker-compose up -d

# Check container status
docker ps

# View container logs
docker-compose logs

# Stop containers (preserves data)
docker-compose stop

# Stop and remove containers
docker-compose down

# Access Kali Linux shell
docker exec -it kali-linux bash

# Access Metasploitable 2 shell
docker exec -it metasploitable2 bash
```

### Network Configuration

- **Network Name:** pentest-net
- **Subnet:** 172.20.0.0/24
- **Gateway:** 172.20.0.1
- **Kali Linux:** 172.20.0.5
- **Metasploitable 2:** 172.20.0.10

### Verify Connectivity

```bash
# From host, check if containers are running
docker exec kali-linux ping -c 2 172.20.0.10

# Get target IP
docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' metasploitable2
```

## 📊 Understanding the Reports

### commands.md
- Documents all Nmap commands used
- Explains command flags and options
- Provides context for each scan type

### output.md
- Contains complete raw output from all scans
- Preserves original formatting
- Useful for detailed analysis

### results.md
- Summarized findings and analysis
- Security implications
- Recommendations for remediation
- Key discoveries highlighted

### reflection.md
- Template for your written reflection
- Should contain 1-2 paragraphs on:
  - Importance of reconnaissance in penetration testing
  - How attackers use this information
  - How defenders can use the same information
  - Ethical considerations

## 🧹 Cleanup

### Quick Cleanup (Preserve Reports)

```bash
chmod +x cleanup.sh
./cleanup.sh
# Choose 'y' when asked about preserving reports
```

### Complete Cleanup (Remove Everything)

```bash
./cleanup.sh
# Choose 'n' when asked about preserving reports

# Optional: Remove Docker images to free space
docker rmi kalilinux/kali-rolling
docker rmi tleemcjr/metasploitable2
```

## 🔧 Troubleshooting

### Docker Daemon Not Running

```bash
# macOS/Linux
sudo systemctl start docker

# Or start Docker Desktop application
```

### Containers Not Starting

```bash
# Check Docker logs
docker-compose logs

# Remove and recreate containers
docker-compose down
docker-compose up -d

# Check for port conflicts
docker ps -a
```

### Permission Denied Errors

```bash
# Make scripts executable
chmod +x *.sh

# On Linux, you might need sudo for Docker
sudo usermod -aG docker $USER
# Then log out and back in
```

### Nmap Scans Taking Too Long

The scans use `-T4` (aggressive timing) by default. If scans are too slow:

1. Check container resources: `docker stats`
2. Ensure containers have adequate CPU/RAM
3. Check network connectivity between containers

### "Script Not Found" Errors

```bash
# Ensure you're in the Q4 directory
cd Q4

# Verify files exist
ls -la task*.sh

# Re-run setup if needed
./setup.sh
```

### Scan Results Show No Vulnerabilities

Metasploitable 2 is intentionally vulnerable. If no vulnerabilities are found:

1. Verify target IP: `docker inspect metasploitable2`
2. Ensure services have started: `docker exec metasploitable2 netstat -tuln`
3. Wait a minute after startup for services to initialize
4. Re-run the vulnerability scan

## 🎓 Learning Objectives

After completing this lab, you should understand:

- ✓ How to perform network reconnaissance with Nmap
- ✓ Different port scanning techniques and their trade-offs
- ✓ How to identify services and operating systems remotely
- ✓ Basic vulnerability assessment methodology
- ✓ The importance of reconnaissance in security testing
- ✓ Both offensive and defensive perspectives of network scanning

## ⚖️ Ethical Considerations

**IMPORTANT:** 

- ✓ This lab uses isolated Docker containers - safe for learning
- ✓ Only scan systems you own or have explicit permission to test
- ✗ Never scan networks or systems without authorization
- ✗ Unauthorized scanning may be illegal in your jurisdiction
- ✓ Always follow responsible disclosure practices
- ✓ Use these skills ethically and professionally

## 🔄 Portability

This lab is designed to be **fully portable** across different machines:

- ✅ Self-contained Docker environment
- ✅ No hardcoded absolute paths
- ✅ Static IP assignments in isolated network
- ✅ Works on Linux, macOS, and Windows (WSL2)
- ✅ All dependencies containerized

Simply copy the entire Q4 directory to another machine with Docker installed and run `./setup.sh`.

## 📝 Assessment Deliverables

When submitting this lab, include:

1. **reports/commands.md** - Command documentation
2. **reports/output.md** - Raw scan outputs
3. **reports/results.md** - Analysis and findings
4. **reports/reflection.md** - Your completed reflection (1-2 paragraphs)
5. **reports/execution-summary-*.log** - Execution logs

## 🆘 Getting Help

### Common Issues

1. **Docker not installed**: Follow installation guide at https://docs.docker.com/get-docker/
2. **Permission errors**: Ensure scripts are executable (`chmod +x *.sh`)
3. **Containers not communicating**: Run `./cleanup.sh` then `./setup.sh` to reset
4. **Scans failing**: Verify both containers are running with `docker ps`

### Resources

- Nmap Documentation: https://nmap.org/book/man.html
- NSE Scripts: https://nmap.org/nsedoc/
- Docker Documentation: https://docs.docker.com/
- Metasploitable 2 Guide: https://docs.rapid7.com/metasploit/metasploitable-2/

## 📜 License

This is an educational project for ITEC624 Assessment 03.

## 🙏 Acknowledgments

- **Nmap**: Gordon Lyon (Fyodor) - https://nmap.org/
- **Kali Linux**: Offensive Security - https://www.kali.org/
- **Metasploitable 2**: Rapid7 - https://www.rapid7.com/

---

**Last Updated:** 2025-11-22  
**Version:** 1.0

