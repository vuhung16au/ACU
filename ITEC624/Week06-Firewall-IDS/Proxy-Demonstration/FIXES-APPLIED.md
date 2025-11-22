# Fixes Applied During Build

## Issue 1: Network Subnet Conflicts ✅ FIXED

**Problem:** Docker networks conflicted with existing networks on the system
```
failed to create network internal-network: Pool overlaps with other one on this address space
```

**Root Cause:** Subnets `172.20.0.0/24` and `172.21.0.0/24` were already in use

**Solution:** Changed to unique 10.x.x.x subnet ranges:
- Forward Proxy Lab:
  - `internal-network` → `proxy-lab-internal` (10.10.0.0/24)
  - `external-network` → `proxy-lab-external` (10.11.0.0/24)
- Reverse Proxy Lab:
  - `dmz-network` → `proxy-lab-dmz` (10.20.0.0/24)
  - `backend-network` → `proxy-lab-backend` (10.21.0.0/24)

## Issue 2: Squid Proxy Startup Failures ✅ FIXED

**Problem:** Squid container was crash-looping with error:
```
FATAL: Squid is already running: Found fresh instance PID file (/var/run/squid.pid)
```

**Root Cause:** The `squid -z` (cache initialization) command was creating a PID file that interfered with the main `squid -N` command

**Solution:** Fixed startup sequence:
1. Added tmpfs mount for `/var/run` to prevent PID file persistence
2. Modified startup command to:
   - Remove stale PID files before initialization
   - Only run cache init if not already initialized
   - Remove PID file again before starting Squid
   - Use `exec` to replace shell with Squid process

```yaml
tmpfs:
  - /var/run:size=10M
command: >
  sh -c "
  apk add --no-cache squid &&
  mkdir -p /var/log/squid /var/cache/squid /var/run &&
  chown -R squid:squid /var/log/squid /var/cache/squid &&
  echo '10.11.0.100  allowed-site.com www.allowed-site.com' >> /etc/hosts &&
  echo '10.11.0.101  malicious-site.com www.malicious-site.com' >> /etc/hosts &&
  rm -f /var/run/squid.pid /var/run/*.pid &&
  if [ ! -d /var/cache/squid/00 ]; then squid -z -N; fi &&
  rm -f /var/run/squid.pid &&
  exec squid -N -d 1
  "
```

## Issue 3: Squid DNS Resolution Failures ✅ FIXED

**Problem:** Squid returned `503 Service Unavailable` with `ERR_DNS_FAIL` for all requests

**Root Cause:** Domain simulation via `/etc/hosts` was only configured in the client container, not in the Squid proxy container. Squid couldn't resolve `allowed-site.com` or `malicious-site.com`

**Solution:** Added `/etc/hosts` entries to the Squid container startup command:
```sh
echo '10.11.0.100  allowed-site.com www.allowed-site.com' >> /etc/hosts
echo '10.11.0.101  malicious-site.com www.malicious-site.com' >> /etc/hosts
```

## Issue 4: Nginx HTTP Method Test Expectation ✅ FIXED

**Problem:** Test expected HTTP 405 for forbidden methods, but Nginx returned 403

**Root Cause:** Nginx's `limit_except` directive returns `403 Forbidden`, not `405 Method Not Allowed`

**Solution:** Updated test script to accept both 403 and 405 as valid responses:
```bash
if [ "$HTTP_CODE" = "403" ] || [ "$HTTP_CODE" = "405" ]; then
    print_success "HTTP method filtering working (DELETE blocked with HTTP $HTTP_CODE)"
```

## Issue 5: Load Balancing Detection ✅ VERIFIED WORKING

**Initial Concern:** Load balancing appeared to send all requests to backend-1

**Investigation:** Manual testing showed perfect round-robin distribution (alternating between backends)

**Conclusion:** Load balancing was working correctly. The initial test run may have been during container startup before backend-2 was fully ready.

---

## Final Test Results ✅

### Forward Proxy Tests (4/4 PASSED)
1. ✅ Allowed Traffic - HTTP 200 from allowed-site.com
2. ✅ Blocked Traffic - HTTP 403 from malicious-site.com  
3. ✅ Proxy Bypass Attempt - Connection blocked by network isolation
4. ✅ Log Inspection - Squid logs showing TCP_MISS and TCP_DENIED

### Reverse Proxy Tests (5/5 PASSED)
1. ✅ Load Balancing - Even distribution between backend-1 and backend-2
2. ✅ Backend Isolation - Direct access blocked by network isolation
3. ✅ Header Manipulation - Backend details hidden, custom headers present
4. ✅ Forbidden Method - DELETE blocked with HTTP 403
5. ✅ Access Logs - Nginx logs showing upstream backend info

---

## Network Configuration Summary

### Forward Proxy Lab
```
proxy-lab-internal (10.10.0.0/24)
  ├─ forward-client (10.10.0.20)
  └─ squid-forward-proxy (10.10.0.10)

proxy-lab-external (10.11.0.0/24)
  ├─ squid-forward-proxy (10.11.0.10)
  ├─ allowed-site-server (10.11.0.100)
  └─ blocked-site-server (10.11.0.101)
```

### Reverse Proxy Lab
```
proxy-lab-dmz (10.20.0.0/24)
  ├─ internet-client (10.20.0.20)
  └─ nginx-reverse-proxy (10.20.0.10)

proxy-lab-backend (10.21.0.0/24) [INTERNAL ONLY]
  ├─ nginx-reverse-proxy (10.21.0.10)
  ├─ backend-server-1 (10.21.0.100)
  └─ backend-server-2 (10.21.0.101)
```

---

## Verification Commands

```bash
# Check all containers are running
docker ps

# Test forward proxy - allowed site
docker exec forward-client curl -x squid-forward-proxy:3128 http://allowed-site.com

# Test forward proxy - blocked site (should get 403)
docker exec forward-client curl -x squid-forward-proxy:3128 http://malicious-site.com

# Test reverse proxy load balancing
for i in {1..10}; do 
  docker exec internet-client curl -s http://nginx-reverse-proxy | grep -o "BACKEND-[12]"
done

# View logs
docker logs squid-forward-proxy
docker logs nginx-reverse-proxy
```

---

## All Issues Resolved ✅

The lab is now **100% functional** with all 9 tests passing successfully!

