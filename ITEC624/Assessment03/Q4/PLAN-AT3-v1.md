# Network Security Lab - Nmap Reconnaissance Assessment

**Note:** All files created under `Q4/` directory. Scripts run from host machine and are fully portable across different systems.

## Docker Infrastructure Setup

**docker-compose.yml** with:
- Custom bridge network `pentest-net` with subnet 172.20.0.0/24
- Kali Linux container (172.20.0.5) with privileged mode, named `kali-linux`
- Metasploitable 2 container (172.20.0.10), named `metasploitable2`
- Volume mount: `./` → `/workspace` in Kali container
- Container names for DNS-based communication

**setup.sh** - Environment Setup Script:
- Check Docker and docker-compose installation
- Start containers with `docker-compose up -d`
- Verify both containers are running
- Test connectivity between containers
- Create reports/ directory structure

**cleanup.sh** - Teardown Script:
- Stop and remove containers
- Clean up Docker network
- Optionally preserve or remove reports/

## Task Scripts (Executable from Host)

All scripts follow this pattern:
1. Check if containers are running (error handling)
2. Get Metasploitable 2 IP using: `docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' metasploitable2`
3. Execute nmap via: `docker exec kali-linux nmap [options] $TARGET_IP`
4. Capture output and format into markdown
5. Append to reports/commands.md, reports/output.md, reports/results.md with timestamps

**task01-pingsweep.sh** - Network Discovery
- Ping sweep: `nmap -sn 172.20.0.0/24`
- Document active hosts with IPs and response times
- Auto-append formatted results to all three report files

**task02-service-version.sh** - Service Version Detection
- Service scan: `nmap -sV -T4 $TARGET_IP`
- Parse and highlight 3+ services with versions
- Auto-append formatted results to reports

**task03-os-detection.sh** - OS Fingerprinting
- OS detection: `nmap -O $TARGET_IP`
- Extract OS family and version details
- Auto-append formatted results to reports

**task04-port-scanning.sh** - SYN vs TCP Scanning
- SYN scan: `nmap -sS -T4 $TARGET_IP`
- TCP scan: `nmap -sT -T4 $TARGET_IP`
- Compare both outputs side-by-side
- Document stealth differences
- Auto-append formatted comparison to reports

**task05-vulnerability-scan.sh** - NSE Vulnerability Assessment
- NSE scan: `nmap -sC -sV -T4 $TARGET_IP`
- Parse and highlight 2+ vulnerabilities
- Document security implications
- Auto-append formatted findings to reports

**tasks-run-all.sh** - Master Execution Script
- Run from host machine
- Execute tasks 1-5 sequentially
- Display progress indicators
- Generate execution summary log
- Verify all reports are populated

## Reports Structure

**reports/** directory auto-created by scripts:

**commands.md** - Auto-generated command documentation:
- Each task appends its command with timestamp
- Include syntax explanations and flags used

**output.md** - Auto-generated raw outputs:
- Each task appends formatted terminal output
- Preserve nmap formatting with code blocks

**results.md** - Auto-generated findings summary:
- Each task appends key discoveries
- Highlight important findings (services, vulnerabilities, OS info)

**reflection.md** - Manual entry (template provided):
- Template with prompts for 1-2 paragraph reflection
- Guidance on attacker vs defender perspectives

## Supporting Files

**README.md** - Complete setup and usage guide:
- Prerequisites (Docker, docker-compose)
- Quick start: `./setup.sh && ./tasks-run-all.sh`
- Individual task execution instructions
- Troubleshooting section
- Portability notes

**.gitignore** - Exclude:
- Temporary nmap output files
- Large scan XML files
- Docker volumes data

## Portability Design

**Cross-machine compatibility:**
- Static IPs in isolated Docker network (works on any host)
- No hardcoded absolute paths
- Container names instead of IPs where possible
- Self-contained docker-compose setup
- Prerequisites check in setup.sh

**Idempotent Design:**
- Scripts check container status before running
- Reports append with timestamps (safe to re-run)
- Cleanup script for fresh starts
- No destructive operations without confirmation

## Implementation Checklist

- [ ] Create docker-compose.yml with static IPs, custom network, and container configuration
- [ ] Create setup.sh with prerequisites check, container startup, and reports directory creation
- [ ] Create cleanup.sh for container teardown and optional cleanup
- [ ] Implement task01-pingsweep.sh with error handling and markdown auto-generation
- [ ] Implement task02-service-version.sh with service parsing and reporting
- [ ] Implement task03-os-detection.sh with OS fingerprinting and reporting
- [ ] Implement task04-port-scanning.sh with SYN vs TCP comparison
- [ ] Implement task05-vulnerability-scan.sh with NSE scripts and vulnerability parsing
- [ ] Implement tasks-run-all.sh master script with progress tracking
- [ ] Create comprehensive README.md with setup, usage, and troubleshooting
- [ ] Create reports/reflection.md template with prompts

