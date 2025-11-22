# Quick Start Guide

## 🚀 Get Started in 3 Steps

### 1. Build the Lab
```bash
make build
```
This will:
- Start forward proxy lab containers (4 containers)
- Start reverse proxy lab containers (4 containers)
- Create Docker networks with proper isolation
- Takes ~2-3 minutes on first run

### 2. Run the Tests
```bash
make test
```
This will:
- Run 4 forward proxy tests (Squid)
- Run 5 reverse proxy tests (Nginx)
- Generate colored PASS/FAIL output
- Save logs to `logs/` directory

### 3. Clean Up
```bash
make clean
```
This will:
- Stop all containers
- Remove networks
- Preserve test logs in `logs/`

---

## 📋 What's Included

### Forward Proxy Lab (`forward-proxy-lab/`)
**Demonstrates:** Outbound traffic control (egress filtering)

**Containers:**
- `forward-client` - Internal user
- `squid-forward-proxy` - Squid proxy with ACLs
- `allowed-site-server` - Simulates safe website
- `blocked-site-server` - Simulates malicious website

**Tests:**
1. ✅ Allowed traffic passes through
2. ❌ Blocked traffic is denied (403)
3. 🔒 Proxy bypass attempt fails
4. 📋 Access logs show all activity

### Reverse Proxy Lab (`reverse-proxy-lab/`)
**Demonstrates:** Inbound traffic protection (ingress filtering)

**Containers:**
- `internet-client` - External user
- `nginx-reverse-proxy` - Nginx with load balancing
- `backend-server-1` - Internal app server
- `backend-server-2` - Internal app server

**Tests:**
1. ⚖️ Load balancing distributes traffic
2. 🔒 Backend isolation prevents direct access
3. 🛡️ Headers hide internal details
4. ⛔ Forbidden methods are blocked
5. 📋 Access logs track requests

---

## 🎓 Learning Objectives

After completing this lab, you'll understand:

- **Forward Proxy**: Controls what internal users can access externally
- **Reverse Proxy**: Protects internal servers from external access
- **Application Layer**: Deep inspection beyond IP/port filtering
- **Network Isolation**: Defense in depth architecture
- **Load Balancing**: High availability and performance
- **Audit Logging**: Security monitoring and compliance

---

## 🔧 Common Commands

```bash
# View all available commands
make help

# Check container status
make status

# View proxy logs
make logs-forward   # Squid logs
make logs-reverse   # Nginx logs

# Run specific test suite
make test-forward   # Only forward proxy tests
make test-reverse   # Only reverse proxy tests

# Restart everything
make restart

# Rebuild from scratch
make rebuild

# Open shell in client container
make shell-forward-client
make shell-internet-client
```

---

## 📖 Detailed Documentation

- **Main README**: `README.md` - Full lab documentation
- **Forward Proxy**: `forward-proxy-lab/README.md` - Squid details
- **Reverse Proxy**: `reverse-proxy-lab/README.md` - Nginx details
- **Implementation Plan**: `PLAN-Proxy-Firewall.md` - Technical design

---

## ⚠️ Troubleshooting

### Containers won't start
```bash
docker info  # Check if Docker is running
make clean   # Clean up old containers
make build   # Rebuild
```

### Tests are failing
```bash
docker ps                     # Check all containers are running
docker logs squid-forward-proxy  # Check proxy logs
docker logs nginx-reverse-proxy  # Check proxy logs
make rebuild                  # Rebuild everything
```

### Permission errors on scripts
```bash
chmod +x scripts/*.sh  # Make scripts executable
```

---

## 🌐 Browser Testing (Optional)

The reverse proxy is also exposed on your host at `http://localhost:8080`

Open your browser and visit:
- `http://localhost:8080` - Will load balance between backends
- Refresh multiple times to see different colored pages (purple/pink)

---

## 📊 Expected Test Results

### Forward Proxy (4 tests)
- ✓ Test 1: HTTP 200 from allowed-site.com
- ✓ Test 2: HTTP 403 from malicious-site.com
- ✓ Test 3: Direct connection times out
- ✓ Test 4: Logs show TCP_MISS and TCP_DENIED

### Reverse Proxy (5 tests)
- ✓ Test 1: ~50/50 split between backends
- ✓ Test 2: Direct backend access fails
- ✓ Test 3: Generic "Server: nginx" header
- ✓ Test 4: DELETE method returns 405
- ✓ Test 5: Logs show upstream backends

---

## 🎯 Next Steps

1. ✅ Complete the automated tests
2. 📝 Review the logs in `logs/` directory
3. 🔍 Inspect configuration files:
   - `forward-proxy-lab/squid/squid.conf`
   - `reverse-proxy-lab/nginx/nginx.conf`
4. 🧪 Modify ACL rules and test custom scenarios
5. 📚 Read the detailed READMEs for each lab

---

**Ready to begin?** Run `make build` now! 🚀

