# Application Proxy Firewall Lab - Educational Demo (HTTP Layer)

---

## ⚠️ IMPORTANT NOTE - ORIGINAL PLAN (v0)

**This is the ORIGINAL plan before fixes were applied.**

### Known Issues with This Version:

If you implement this plan as-is, you will encounter these problems:

1. **Network Subnet Conflicts** ❌
   - Uses `172.20.0.0/24` and `172.21.0.0/24` which commonly conflict with existing Docker networks
   - **Solution**: Use `10.x.x.x` ranges instead (see PLAN-Proxy-Firewall.md)

2. **Squid Container Crash-Loop** ❌
   - Squid will fail with "already running" error due to PID file persistence
   - **Solution**: Add tmpfs mount for `/var/run` and fix startup sequence

3. **DNS Resolution Failure in Squid** ❌
   - Squid returns `503 ERR_DNS_FAIL` because it doesn't have `/etc/hosts` entries
   - **Solution**: Add hosts entries to BOTH client AND Squid containers

4. **Test 4 Failure (Log Inspection)** ❌
   - Test script looks for logs in `docker logs` but Squid writes to file
   - **Solution**: Read from `/var/log/squid/access.log` file instead

5. **Test 4 Failure (HTTP Method Filtering)** ❌
   - Test expects HTTP 405 but Nginx returns HTTP 403
   - **Solution**: Accept both 403 and 405 as valid responses

6. **Vague Network Names** ⚠️
   - Generic names like `internal-network` may conflict with other projects
   - **Solution**: Use prefixed names like `proxy-lab-internal`

### Recommended Action:

**Use PLAN-Proxy-Firewall.md instead** - it includes all fixes and critical warnings for successful implementation.

This v0 file is preserved for reference and educational purposes to show what can go wrong without proper implementation details.

---

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
- `internal-network` (client connected to proxy only)
- `external-network` (proxy connected to both web servers)

**Domain Simulation:**
- Client container uses custom `/etc/hosts`:
  ```
  <allowed-site-server-ip>  allowed-site.com www.allowed-site.com
  <blocked-site-server-ip>  malicious-site.com www.malicious-site.com
  ```
- Squid ACLs use real domain patterns (e.g., `acl blocked_domains dstdomain malicious-site.com`)
- Documentation explains real-world equivalent (external DNS)

**Docker Volumes:**
- `squid-logs:/var/log/squid` (persistent logs)

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
   - Learning: Audit trail, forensic analysis, compliance monitoring

## Scenario 2: Reverse Proxy (Nginx) - HTTP Only, Fully Offline

**Architecture:** Client → Nginx Reverse Proxy → Backend Servers (Load Balanced)

**Container Names:**
- `internet-client` (Alpine with curl)
- `nginx-reverse-proxy` (Nginx with upstream config)
- `backend-server-1` (Nginx serving unique content)
- `backend-server-2` (Nginx serving unique content)

**Network Names:**
- `dmz-network` (client to reverse proxy)
- `backend-network` (reverse proxy to backends, isolated from dmz)

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
   - Send `DELETE /index.html` request → Proxy blocks with 405 Method Not Allowed
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
- `docker logs squid-forward-proxy` (capture logs)
- Parse and colorize output
- Save to timestamped log file in `logs/` directory
- Idempotent (can run multiple times)

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
- Access logs to stdout and volume

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

