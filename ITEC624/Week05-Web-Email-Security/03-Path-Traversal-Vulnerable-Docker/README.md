# Path Traversal Lab: Intentionally Vulnerable Application

This lab demonstrates Path Traversal (Directory Traversal) vulnerabilities in a Node.js/Express application. It provides both vulnerable and secure implementations to help you understand the differences and learn proper defense techniques.

**Suggested image name:** `path-traversal-lab` (alternatives: `path-traversal-vulnerable`, `path-traversal-playground`).

## 🚀 Quick Start

### Run with Docker

```bash
# From this directory
docker build -t path-traversal-lab .

# Run on port 3000
docker run --rm -p 3000:3000 path-traversal-lab
```

Open `http://localhost:3000` in your browser.

### Run Locally (Development)

```bash
npm install
npm start
```

## 📋 Application Overview

### Routes Available:
- **`/`** - Home page with vulnerability explanation and testing interface
- **`/vulnerable?file=<filename>`** - Vulnerable file access endpoint
- **`/safe?file=<filename>`** - Safe file access endpoint with validation
- **`/files`** - List available files in the application

### Sample Files Created:
- `readme.txt` - Basic information file
- `config.txt` - Configuration file with database settings
- `secrets.txt` - File containing sensitive information
- `data.json` - JSON data file with user information
- `sensitive-data.txt` - Critical file outside the intended directory (for testing)

## 🎯 What is Path Traversal?

Path Traversal (also known as Directory Traversal) is a vulnerability that allows attackers to access files and directories outside the intended directory structure. This occurs when user input is used to construct file paths without proper validation or sanitization.

### Why This Application is Vulnerable

The vulnerable endpoint directly uses user input to construct file paths:

```javascript
// VULNERABLE: Direct path construction without validation
const filePath = path.join(__dirname, 'files', filename);
const content = fs.readFileSync(filePath, 'utf8');
```

This allows attackers to use path traversal sequences like `../` to access files outside the intended directory.

## 🔓 How to Exploit (For Learning)

### 1. Basic Path Traversal

**Vulnerable Endpoint Payloads:**
- Access files outside directory: `../sensitive-data.txt`
- Access system files: `../../../etc/passwd`
- Bypass basic filters: `....//....//....//etc//passwd`

### 2. URL Encoding Bypass

**Encoded Payloads:**
- `%2e%2e%2f%2e%2e%2f%2e%2e%2fetc%2fpasswd` (URL encoded `../../../etc/passwd`)
- `%252e%252e%252f` (Double URL encoded `../`)

### 3. Advanced Techniques

**Filter Bypass Methods:**
- Null byte injection: `../../../etc/passwd%00.txt`
- Unicode encoding: `%c0%ae%c0%ae%c0%af` (Unicode encoded `../`)
- Mixed separators: `..\..\..\windows\system32\drivers\etc\hosts`

### 💡 Exploitation Hints

1. **Start with simple payloads** like `../sensitive-data.txt`
2. **Use URL encoding** to bypass basic filters
3. **Try different path separators** (`/` vs `\`)
4. **Test null byte injection** with `%00`
5. **Compare vulnerable vs safe implementations**

## 🛡️ How to Fix (In This Application)

### 1. Input Validation and Sanitization

```javascript
// SAFE: Validate and sanitize the filename
if (filename.includes('..') || filename.includes('/') || filename.includes('\\')) {
    return res.status(400).send('Invalid filename: Path traversal detected');
}
```

### 2. Path Resolution and Directory Checking

```javascript
// SAFE: Use path.resolve and check if file is within allowed directory
const allowedDir = path.join(__dirname, 'files');
const requestedPath = path.resolve(allowedDir, filename);

// SAFE: Ensure the resolved path is within the allowed directory
if (!requestedPath.startsWith(allowedDir)) {
    return res.status(403).send('Access denied: File outside allowed directory');
}
```

### 3. Whitelist-Based Access Control

```javascript
// SAFE: Use a whitelist of allowed files
const allowedFiles = ['readme.txt', 'config.txt', 'secrets.txt', 'data.json'];
if (!allowedFiles.includes(filename)) {
    return res.status(403).send('File not allowed');
}
```

## 🔒 How to Prevent (General Guidance)

### 1. **Input Validation**
- Validate file names against a whitelist
- Reject any input containing path traversal sequences (`..`, `/`, `\`)
- Use regular expressions to validate file name format
- Limit file name length and character set

### 2. **Path Resolution**
- Always resolve paths to absolute paths
- Check that resolved paths are within allowed directories
- Use `path.resolve()` and `path.relative()` for safe path operations
- Never trust user input for path construction

### 3. **Access Control**
- Implement proper file access permissions
- Use chroot or containerization to limit file system access
- Run applications with minimal required privileges
- Separate application files from system files

### 4. **Additional Security Measures**
- Use Content Security Policy (CSP) headers
- Implement proper error handling (don't reveal file system structure)
- Log and monitor file access attempts
- Regular security testing and code reviews

## 🧪 Testing the Application

### 1. Test Vulnerable Endpoints
- Try accessing files with path traversal payloads
- Test different encoding methods
- Attempt to access system files
- Observe how the application handles invalid paths

### 2. Test Safe Endpoints
- Use the same payloads with the safe endpoint
- Notice how validation prevents unauthorized access
- Compare the security measures implemented

### 3. File System Exploration
- Visit `/files` to see available files
- Try accessing files outside the intended directory
- Test with various file extensions and names

## 📊 File Structure

The application creates the following structure:

```
/app/
├── files/                    # Intended directory for file access
│   ├── readme.txt           # Sample file
│   ├── config.txt           # Configuration file
│   ├── secrets.txt          # Sensitive information
│   └── data.json            # JSON data file
├── sensitive-data.txt       # Critical file (outside files/)
├── server.js               # Main application
└── views/                  # EJS templates
```

## 🎓 Learning Objectives

After completing this lab, you should understand:

1. **How Path Traversal vulnerabilities occur**
2. **Common attack techniques and payloads**
3. **How to identify vulnerable code patterns**
4. **Proper input validation and sanitization**
5. **Safe file handling practices**
6. **Defense mechanisms and best practices**

## 🧹 Cleanup

Stop the container with Ctrl+C (or remove with `docker ps` / `docker stop`).

## 📚 Additional Resources

- [OWASP Path Traversal Prevention Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/Path_Traversal_Cheat_Sheet.html)
- [CWE-22: Improper Limitation of a Pathname to a Restricted Directory](https://cwe.mitre.org/data/definitions/22.html)
- [Node.js Path Module Documentation](https://nodejs.org/api/path.html)

## ⚠️ Disclaimer

This application is intentionally vulnerable and should **NEVER** be used in production environments. It is designed solely for educational purposes to help understand Path Traversal vulnerabilities and proper defense techniques.

## 🏷️ Suggested Names

- `path-traversal-lab` (recommended)
- `path-traversal-vulnerable`
- `path-traversal-playground`
- `directory-traversal-lab`
