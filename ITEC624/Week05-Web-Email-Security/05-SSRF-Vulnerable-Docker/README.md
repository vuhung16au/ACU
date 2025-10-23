# 🔒 SSRF Vulnerability Lab

**Server-Side Request Forgery (SSRF) Educational Environment**

This Docker container demonstrates various SSRF vulnerabilities and their secure implementations for educational purposes.

## 🎯 What is SSRF?

**Server-Side Request Forgery (SSRF)** is a web security vulnerability that allows an attacker to induce the server-side application to make HTTP requests to an arbitrary domain of the attacker's choosing. In typical SSRF attacks, the attacker might cause the server to make a connection back to itself, to other web-based services within the organization's infrastructure, or to external third-party systems.

## 🚨 Vulnerability Impact

- **Internal Network Scanning**: Access to internal services and databases
- **Local File Access**: Reading sensitive files using `file://` protocol
- **Cloud Metadata Access**: Accessing cloud provider metadata services
- **Port Scanning**: Discovering open ports on internal systems
- **Protocol Smuggling**: Using protocols like `gopher://` to bypass filters

## 🏗️ Container Structure

```
05-SSRF-Vulnerable-Docker/
├── Dockerfile              # Container configuration
├── package.json            # Node.js dependencies
├── server.js               # Main application with SSRF vulnerabilities
├── views/                  # EJS templates
│   ├── index.ejs          # Home page with attack vectors
│   ├── vulnerable.ejs     # Basic SSRF endpoint
│   ├── vulnerable-request.ejs  # Request library SSRF
│   ├── vulnerable-file.ejs     # File protocol SSRF
│   ├── vulnerable-gopher.ejs   # Gopher protocol SSRF
│   └── safe.ejs           # Secure implementation
└── README.md              # This documentation
```

## 🚀 Quick Start

### Build and Run the Container

```bash
# Build the Docker image
docker build -t ssrf-lab .

# Run the container
docker run -p 3000:3000 ssrf-lab
```

### Access the Application

Open your browser and navigate to: `http://localhost:3000`

## 🎯 Attack Vectors Demonstrated

### 1. Basic SSRF (`/vulnerable`)
- **Vulnerability**: Direct URL usage without validation
- **Attack**: Access internal services via `http://localhost:PORT/endpoint`
- **Impact**: Internal service discovery and data exfiltration

### 2. Request Library SSRF (`/vulnerable-request`)
- **Vulnerability**: Using `request` library without proper validation
- **Attack**: Different protocol handling and request smuggling
- **Impact**: Bypass some security controls

### 3. File Protocol SSRF (`/vulnerable-file`)
- **Vulnerability**: Allowing `file://` protocol access
- **Attack**: Local file disclosure using `file:///path/to/file`
- **Impact**: Sensitive file access (passwords, configs, keys)

### 4. Gopher Protocol SSRF (`/vulnerable-gopher`)
- **Vulnerability**: Allowing `gopher://` protocol access
- **Attack**: Advanced protocol-based attacks
- **Impact**: Bypass filters and access internal services

### 5. Safe Implementation (`/safe`)
- **Security**: Proper URL validation and whitelisting
- **Protection**: Domain whitelist, protocol validation, internal network blocking

## 🎯 Exploitation Examples

### Internal Service Access
```bash
# Access internal admin panel
http://localhost:3000/vulnerable?url=http://localhost:8080/admin

# Access internal database
http://localhost:3000/vulnerable?url=http://localhost:3306/api/data

# Access internal Redis
http://localhost:3000/vulnerable?url=http://localhost:6379/cache
```

### File Access Attacks
```bash
# Read system files
http://localhost:3000/vulnerable-file?url=file:///etc/passwd

# Read application config
http://localhost:3000/vulnerable-file?url=file:///app/package.json

# Read SSH keys
http://localhost:3000/vulnerable-file?url=file:///root/.ssh/id_rsa
```

### Gopher Protocol Attacks
```bash
# Access internal services via gopher
http://localhost:3000/vulnerable-gopher?url=gopher://localhost:8080/admin

# Cloud metadata access
http://localhost:3000/vulnerable-gopher?url=gopher://169.254.169.254/latest/meta-data/
```

## 🛡️ How to Fix SSRF Vulnerabilities

### 1. Input Validation
```javascript
// ❌ VULNERABLE
app.get('/vulnerable', (req, res) => {
  const url = req.query.url;
  axios.get(url); // Direct usage without validation
});

// ✅ SECURE
app.get('/safe', (req, res) => {
  const url = req.query.url;
  const urlObj = new URL(url);
  
  // Validate protocol
  if (!['http:', 'https:'].includes(urlObj.protocol)) {
    return res.status(400).json({ error: 'Invalid protocol' });
  }
  
  // Validate domain
  const allowedDomains = ['httpbin.org', 'api.github.com'];
  if (!allowedDomains.includes(urlObj.hostname)) {
    return res.status(400).json({ error: 'Domain not allowed' });
  }
  
  // Block internal networks
  if (isInternalIP(urlObj.hostname)) {
    return res.status(400).json({ error: 'Internal networks not allowed' });
  }
});
```

### 2. URL Whitelisting
```javascript
const ALLOWED_DOMAINS = [
  'httpbin.org',
  'jsonplaceholder.typicode.com',
  'api.github.com'
];

function isAllowedDomain(hostname) {
  return ALLOWED_DOMAINS.includes(hostname);
}
```

### 3. Internal Network Blocking
```javascript
function isInternalIP(hostname) {
  const internalRanges = [
    /^127\./,           // localhost
    /^10\./,            // private class A
    /^172\.(1[6-9]|2[0-9]|3[0-1])\./, // private class B
    /^192\.168\./,      // private class C
    /^169\.254\./,      // link-local
    /^::1$/,            // IPv6 localhost
    /^fc00:/,           // IPv6 private
    /^fe80:/            // IPv6 link-local
  ];
  
  return internalRanges.some(range => range.test(hostname));
}
```

### 4. Protocol Validation
```javascript
function isValidProtocol(protocol) {
  const allowedProtocols = ['http:', 'https:'];
  return allowedProtocols.includes(protocol);
}
```

## 🔒 Prevention Strategies

### 1. **Network Segmentation**
- Isolate web applications from internal services
- Use firewalls to block unnecessary internal communication
- Implement network access controls

### 2. **Input Validation**
- Validate all user input, especially URLs
- Use allowlists instead of blocklists
- Implement proper URL parsing and validation

### 3. **Security Controls**
- Disable unnecessary protocols (file://, gopher://, etc.)
- Implement request timeouts
- Use outbound proxies with filtering

### 4. **Monitoring and Logging**
- Log all outbound requests
- Monitor for suspicious patterns
- Implement alerting for unusual requests

## 🎓 Learning Objectives

After working with this lab, you should understand:

1. **SSRF Attack Vectors**: How different protocols and techniques can be exploited
2. **Impact Assessment**: The potential damage SSRF can cause to internal infrastructure
3. **Detection Methods**: How to identify SSRF vulnerabilities in code
4. **Prevention Techniques**: Best practices for securing applications against SSRF
5. **Secure Coding**: How to implement proper input validation and URL filtering

## 🚨 Security Warning

⚠️ **This application contains intentional security vulnerabilities for educational purposes only!**

- Do not deploy this application in production environments
- Use only in isolated, controlled environments
- Ensure proper network isolation when running
- Never expose this application to the internet

## 🛠️ Troubleshooting

### Common Issues

1. **Port Conflicts**: Ensure port 3000 is available
2. **Docker Issues**: Make sure Docker is running and you have proper permissions
3. **Network Access**: Some attacks may not work depending on your network configuration

### Debug Mode

To run with debug logging:
```bash
DEBUG=* docker run -p 3000:3000 ssrf-lab
```

## 📚 Additional Resources

- [OWASP SSRF Prevention Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/Server_Side_Request_Forgery_Prevention_Cheat_Sheet.html)
- [PortSwigger SSRF Tutorial](https://portswigger.net/web-security/ssrf)
- [CWE-918: Server-Side Request Forgery](https://cwe.mitre.org/data/definitions/918.html)

## 🎨 Color Scheme

This application uses the following color palette:
- **Purple**: `#3C1053` - Primary brand color
- **Red**: `#F2120C` - Warning and error states
- **Deep Charcoal**: `#302C2A` - Text and dark elements
- **Soft Ivory**: `#F2EFEB` - Background and light elements
- **Warm Stone**: `#918B83` - Secondary elements
- **Faculty Purple**: `#B51825` - Accent color

---

**Happy Learning! 🎓🔒**
