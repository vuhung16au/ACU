# Application Proxy Firewall Lab - Educational Demo (HTTP Layer)

## Project Structure

```
/Proxy-Demonstration
├── README.md (Main documentation with intro, concepts, setup, Mermaid diagrams)
├── Makefile (help, build, clean, test, test-forward, test-reverse, logs targets)
├── scripts/ (Run from host machine with error handling)
│   ├── run-all-tests.sh (Execute all tests, preserve logs to logs/)
│   ├── test-forward-proxy.sh (Forward proxy test suite)
│   └── test-reverse-proxy.sh (Reverse proxy test suite)
├── logs/ (Preserved test execution logs)
│   ├── forward-proxy-test-TIMESTAMP.log
│   └── reverse-proxy-test-TIMESTAMP.log
├── forward-proxy-lab/
│   ├── docker-compose.yml (with log volumes)
│   ├── squid/
│   │   └── squid.conf (ACL rules for allowed-site.com / malicious-site.com)
│   ├── allowed-site/
│   │   └── index.html (Simulates safe-site.com content)
│   ├── blocked-site/
│   │   └── index.html (Simulates malicious-site.com content)
│   └── README.md (Scenario-specific Mermaid diagram + test explanations)
└── reverse-proxy-lab/
    ├── docker-compose.yml (with log volumes)
    ├── nginx/
    │   └── nginx.conf (Upstream, load balancing, security headers)
    ├── backend-servers/
    │   ├── server1/
    │   │   └── index.html (Returns "Response from Backend Server 1")
    │   └── server2/
    │       └── index.html (Returns "Response from Backend Server 2")
    └── README.md (Scenario-specific Mermaid diagram + test explanations)
```

## Scenario 1: Forward Proxy (Squid) - HTTP Only, Fully Offline

**Architecture:** Client → Squid Proxy → Simulated Internet Servers

**Container Names:**
- `forward-client` (Alpine with curl + custom /etc/hosts)
- `squid-forward-proxy` (Squid with domain-based ACL rules)
- `allowed-site-server` (Nginx serving allowed-site.com)
- `blocked-site-server` (Nginx serving malicious-site.com)

**Network Names:**
- `proxy-lab-internal` (10.10.0.0/24 - client connected to proxy only)
- `proxy-lab-external` (10.11.0.0/24 - proxy connected to both web servers)

**IMPORTANT**: Use 10.x.x.x subnets to avoid conflicts with existing Docker networks. Do NOT use 172.x.x.x ranges.

**Domain Simulation:**
- BOTH client AND proxy containers need custom `/etc/hosts`:
  ```
  10.11.0.100  allowed-site.com www.allowed-site.com
  10.11.0.101  malicious-site.com www.malicious-site.com
  ```
- **CRITICAL**: Squid container MUST have these entries in its startup command, otherwise DNS resolution fails
- Squid ACLs use real domain patterns (e.g., `acl blocked_domains dstdomain malicious-site.com`)
- Documentation explains real-world equivalent (external DNS)

**Docker Volumes:**
- `squid-logs:/var/log/squid` (persistent logs)
- `squid-cache:/var/cache/squid` (cache directory)

**tmpfs Mount:**
- `/var/run:size=10M` (CRITICAL: prevents PID file persistence issues)

**Test Cases (4 tests):**

1. **Test 1: Allowed Traffic** 
   - Client requests `http://allowed-site.com` via proxy → Proxy allows → Success (200 OK)
   - Learning: ACL permit rules, request logging
   
2. **Test 2: Blocked Traffic** 
   - Client requests `http://malicious-site.com` via proxy → Proxy blocks → 403 Forbidden
   - Learning: ACL deny rules, content filtering at application layer
   
3. **Test 3: Proxy Bypass Attempt** 
   - Client tries direct connection to malicious-site.com (without proxy) → Connection fails
   - Learning: Network isolation enforcement, perimeter security
   
4. **Test 4: Log Inspection** 
   - Display Squid access logs showing all request attempts
   - **IMPORTANT**: Read from `/var/log/squid/access.log` file using `docker exec`, NOT from `docker logs` stdout
   - Learning: Audit trail, forensic analysis, compliance monitoring

## Scenario 2: Reverse Proxy (Nginx) - HTTP Only, Fully Offline

**Architecture:** Client → Nginx Reverse Proxy → Backend Servers (Load Balanced)

**Container Names:**
- `internet-client` (Alpine with curl)
- `nginx-reverse-proxy` (Nginx with upstream config)
- `backend-server-1` (Nginx serving unique content)
- `backend-server-2` (Nginx serving unique content)

**Network Names:**
- `proxy-lab-dmz` (10.20.0.0/24 - client to reverse proxy)
- `proxy-lab-backend` (10.21.0.0/24 - reverse proxy to backends, isolated from dmz, internal: true)

**IMPORTANT**: Use 10.x.x.x subnets to avoid conflicts. Backend network MUST have `internal: true` for isolation.

**Load Balancing Detection:**
- Each backend returns unique content: "Response from Backend Server 1/2"
- Test scripts parse response body to track distribution

**Docker Volumes:**
- `nginx-logs:/var/log/nginx` (persistent logs)

**Test Cases (5 tests):**

1. **Test 1: Load Balancing** 
   - Send 10 requests to `http://nginx-reverse-proxy` → Parse responses → Show distribution
   - Expected: ~5 requests to each backend (round-robin)
   - Learning: Traffic distribution, high availability, fault tolerance
   
2. **Test 2: Backend Isolation** 
   - Client tries direct access to backend-server-1/2 → Connection refused (no route)
   - Learning: Internal network hiding, defense in depth
   
3. **Test 3: Header Manipulation** 
   - Check response headers → "Server" header shows "nginx" not backend details
   - Learning: Information disclosure prevention, fingerprinting protection
   
4. **Test 4: Forbidden Method** 
   - Send `DELETE /index.html` request → Proxy blocks with 403 Forbidden (or 405)
   - **NOTE**: Nginx `limit_except` directive returns 403, not 405. Test should accept both.
   - Learning: HTTP method filtering, attack surface reduction
   
5. **Test 5: Access Logs** 
   - Display Nginx logs showing all proxied requests with client IPs and status codes
   - Learning: Request inspection, security monitoring, incident response

## Makefile Targets

```makefile
.PHONY: help build clean test test-forward test-reverse logs-forward logs-reverse

help:           Show this help message with all available targets
build:          Build and start all Docker containers for both labs
clean:          Stop and remove all containers, networks (logs preserved in logs/)
test:           Run all automated tests (forward + reverse), save logs
test-forward:   Run forward proxy tests only
test-reverse:   Run reverse proxy tests only
logs-forward:   Display Squid proxy logs
logs-reverse:   Display Nginx proxy logs
```

## Test Script Design (Host-based, Error Handling, Preserved Logs)

**Script Structure:**
```bash
#!/bin/bash
# 1. Check if Docker is running
# 2. Check if required containers are running (exit with error if not)
# 3. Run tests with colored output:
#    - GREEN (✓ PASS), RED (✗ FAIL), YELLOW (INFO), BLUE (LOG)
# 4. Save full output to logs/SCENARIO-TIMESTAMP.log
# 5. Summary: X/Y tests passed

# Format for each test:
# ========================================
# [TEST N] Test Name
# ========================================
# ACTION: What request is sent
# EXPECTED: What should happen
# RESULT: ✓ PASS / ✗ FAIL
# RESPONSE: <excerpt>
# LOGS: <relevant proxy log lines>
```

**Error Handling:**
- Check Docker daemon status
- Verify container status before testing (`docker ps | grep <container>`)
- Exit with clear error message if setup incomplete
- Provide remediation hint (e.g., "Run 'make build' first")

**Execution Method:**
- `docker exec forward-client curl --proxy squid-forward-proxy:3128 http://allowed-site.com`
- `docker exec squid-forward-proxy cat /var/log/squid/access.log` (capture access logs)
- Parse and colorize output
- Save to timestamped log file in `logs/` directory
- Idempotent (can run multiple times)

**CRITICAL Error Handling:**
- Check if Docker daemon is running: `docker info`
- Verify containers are running before testing: `docker ps | grep <container-name>`
- Exit with clear error message and remediation hint if prerequisites not met

## README.md Content Structure

**Main README.md:**

1. **Introduction**
   - Purpose: Educational lab for Application Proxy Firewalls (HTTP layer)
   - Audience: Cybersecurity students
   - Learning objectives: Understand Layer 7 inspection, traffic control, proxy types

2. **Key Concepts**
   - **Forward Proxy**: Client-side proxy controlling outbound traffic (employees → internet)
     - Use case: Content filtering, bandwidth control, anonymity
   - **Reverse Proxy**: Server-side proxy protecting inbound traffic (internet → servers)
     - Use case: Load balancing, SSL termination, DDoS protection
   - **Application Layer Gateway**: Deep packet inspection, protocol validation
   - **Comparison Table**: Forward vs Reverse proxy (purpose, placement, use cases)
   - **Note**: Lab uses HTTP only for simplicity; real-world deployments use HTTPS

3. **Prerequisites**
   - Docker Engine 20.10+ and Docker Compose v2.0+
   - No internet connection required (fully offline lab)
   - ~200MB disk space for images

4. **Lab Setup**
   ```bash
   make help     # Show all available commands
   make build    # Build and start all containers (takes 2-3 minutes)
   make test     # Run all tests (results saved to logs/)
   make clean    # Clean up (preserves logs/)
   ```

5. **Running Tests**
   - Automated testing: `make test`, `make test-forward`, `make test-reverse`
   - Understanding colored output (PASS/FAIL indicators)
   - Log preservation: Check `logs/` directory for detailed results
   - Expected results table for each test

6. **Network Diagrams** (Mermaid format)
   - Forward proxy topology (3 networks, isolation boundaries)
   - Reverse proxy topology (DMZ architecture)

7. **Learning Outcomes**
   - Security concepts demonstrated by each test
   - When to use forward vs reverse proxy

8. **Troubleshooting**
   - Container not starting
   - DNS resolution issues
   - Permission errors

**Scenario-specific READMEs:**

- Detailed Mermaid diagram for that scenario
- Container configuration explanations
- ACL/proxy rules breakdown with examples
- Hosts file configuration (how domain simulation works)
- Manual testing commands (step-by-step alternative to scripts)
- Security implications of each test case
- Real-world equivalent (how this maps to production deployments)

## Container Design (Lightweight)

**Base Images:**
- Squid: `alpine:3.19` + squid package (~25MB total)
- Nginx: `nginx:1.25-alpine` (~40MB)
- Clients: `alpine:3.19` + curl (~10MB)
- Web servers: `nginx:1.25-alpine` with custom index.html

**Key Configuration:**

**Forward Proxy (Squid):**
- ACLs using domain patterns (dstdomain)
- allowed_domains: allowed-site.com
- blocked_domains: malicious-site.com
- HTTP port 3128
- Access logs to file volume (`/var/log/squid/access.log`)

**CRITICAL Squid Startup Sequence (to avoid PID file conflicts):**
```sh
apk add --no-cache squid &&
mkdir -p /var/log/squid /var/cache/squid /var/run &&
chown -R squid:squid /var/log/squid /var/cache/squid &&
echo '10.11.0.100  allowed-site.com www.allowed-site.com' >> /etc/hosts &&
echo '10.11.0.101  malicious-site.com www.malicious-site.com' >> /etc/hosts &&
rm -f /var/run/squid.pid /var/run/*.pid &&
if [ ! -d /var/cache/squid/00 ]; then squid -z -N; fi &&
rm -f /var/run/squid.pid &&
exec squid -N -d 1
```
**Key points:**
- Add hosts entries BEFORE starting Squid
- Clean PID files before initialization
- Only initialize cache if not already done
- Clean PID again before starting
- Use `exec` to replace shell process
- Must have tmpfs mount for `/var/run`

**Reverse Proxy (Nginx):**
- Upstream block with backend-server-1 and backend-server-2
- Round-robin load balancing
- proxy_hide_header Server (add custom header)
- limit_except GET HEAD (block DELETE, PUT, etc.)
- Access logs to volume

**Network Configuration:**
- Strict isolation using Docker networks
- No default bridge network
- Clients cannot reach backends directly
- DNS resolution via Docker's internal DNS + custom hosts files

## Security Scenarios Demonstrated

**Forward Proxy (Egress Control):**
- Content filtering based on domain ACLs
- Egress traffic control (prevent data exfiltration to malicious sites)
- Audit logging for compliance (who accessed what, when)
- Network isolation (proxy is single exit point)

**Reverse Proxy (Ingress Protection):**
- Internal topology hiding (backends invisible from internet)
- Load distribution for DoS resilience
- HTTP method filtering (prevent unauthorized operations)
- Header sanitization (prevent information leakage)
- Centralized access logging

**Real-world Applications:**
- Forward: Corporate networks, schools, public WiFi
- Reverse: Web application firewalls (WAF), API gateways, CDN origin shields

---

## CRITICAL IMPLEMENTATION GOTCHAS

### Issue 1: Network Subnet Conflicts ⚠️
**Problem**: Docker networks may conflict with existing networks on the host system
**Solution**: Use 10.x.x.x private ranges instead of common 172.x.x.x ranges
**Network Names**: Use descriptive prefixes like `proxy-lab-*` to avoid naming conflicts

### Issue 2: Squid PID File Persistence ⚠️
**Problem**: Squid crashes with "already running" error due to stale PID files in volumes
**Solution**: 
- Add `tmpfs` mount for `/var/run` directory
- Clean PID files in startup command before initializing and before starting
- Use `exec squid` (not just `squid`) to replace shell process

### Issue 3: DNS Resolution in Squid ⚠️
**Problem**: Squid returns `503 ERR_DNS_FAIL` even though domains work in client
**Solution**: Add `/etc/hosts` entries to BOTH client AND Squid containers
**Why**: Each container has its own `/etc/hosts` - mounting doesn't propagate between containers

### Issue 4: Squid Access Log Location ⚠️
**Problem**: Test scripts can't find logs using `docker logs`
**Solution**: Squid writes to `/var/log/squid/access.log` file, not stdout
**Fix**: Use `docker exec squid-forward-proxy cat /var/log/squid/access.log` in tests

### Issue 5: Nginx Method Filtering Response Code ⚠️
**Problem**: Test expects HTTP 405, but Nginx returns HTTP 403
**Solution**: Nginx `limit_except` directive returns 403 Forbidden (not 405 Method Not Allowed)
**Fix**: Update test to accept both 403 and 405 as valid responses

### Issue 6: Docker Compose Version Warning ⚠️
**Problem**: Warning about obsolete `version` attribute in docker-compose.yml
**Solution**: Can safely ignore or remove `version: '3.8'` line (Docker Compose v2 doesn't need it)

### Issue 7: Load Balancing Not Detected Initially ⚠️
**Problem**: First test run may show all requests going to one backend
**Solution**: Wait for all containers to fully start (5-10 seconds) before running tests
**Verification**: Manually test with loop to confirm round-robin works

### Issue 8: Container Names vs Network Names ⚠️
**Problem**: Confusion between Docker service names and network names
**Solution**: 
- Service names: Used in `docker exec`, `docker logs`, etc.
- Network names: Used for isolation and defined in `networks:` section
- They can be different!

---

## Quick Troubleshooting Commands

```bash
# Check existing Docker networks and their subnets
docker network ls
docker network inspect $(docker network ls -q) | grep -E '"Subnet"|"Name"'

# Verify container status
docker ps --format "table {{.Names}}\t{{.Status}}\t{{.Ports}}"

# Test Squid manually
docker exec forward-client curl -v -x squid-forward-proxy:3128 http://allowed-site.com

# Check Squid can resolve domains
docker exec squid-forward-proxy cat /etc/hosts | grep -E "allowed|malicious"

# View Squid access logs directly
docker exec squid-forward-proxy cat /var/log/squid/access.log | tail -20

# Test Nginx load balancing manually
for i in {1..10}; do 
  docker exec internet-client curl -s http://nginx-reverse-proxy | grep -o "BACKEND-[12]"
done

# Check if backend network is truly internal
docker network inspect proxy-lab-backend | grep '"Internal"'

# Restart specific service
docker compose -f forward-proxy-lab/docker-compose.yml restart squid-forward-proxy

# Full rebuild with volume cleanup
docker compose -f forward-proxy-lab/docker-compose.yml down -v
docker compose -f forward-proxy-lab/docker-compose.yml up -d
```

---

## Expected Test Results

All 9 tests should pass:
- **Forward Proxy**: 4/4 PASSED
  - Test 1: HTTP 200 for allowed-site.com ✓
  - Test 2: HTTP 403 for malicious-site.com ✓
  - Test 3: Direct connection blocked ✓
  - Test 4: Access logs show TCP_MISS and TCP_DENIED ✓

- **Reverse Proxy**: 5/5 PASSED
  - Test 1: Load balancing ~50/50 distribution ✓
  - Test 2: Backend isolation enforced ✓
  - Test 3: Headers sanitized (3/3 checks) ✓
  - Test 4: DELETE method blocked (HTTP 403) ✓
  - Test 5: Access logs with upstream info ✓

