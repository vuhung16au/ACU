# Security Best Practices

## The Golden Rule

**NEVER commit sensitive information to version control!**

❌ Connection strings with passwords  
❌ API keys  
❌ Tokens  
❌ Credentials  
❌ Secrets of any kind  

---

## Why Security Matters

### What Can Go Wrong?

```csharp
// ❌ NEVER DO THIS!
var connectionString = "Server=prod-db.company.com;Database=users;User=admin;Password=SuperSecret123";
```

**If committed to Git:**
1. ✅ Push to GitHub
2. 😱 Bots scan public repos within minutes
3. 💥 Database compromised
4. 📰 Data breach headlines
5. 💸 Lawsuits and fines

**Even private repos:**
- Teammates' laptopslaptops get stolen
- Ex-employees retain access
- Accidental public push
- Third-party tools with repo access

---

## Secure Approaches

### Development: User Secrets

**What:** Stored outside project directory, not in Git

**How It Works:**

```bash
# Initialize user secrets
cd YourProject
dotnet user-secrets init

# This adds to .csproj:
<UserSecretsId>a-unique-guid</UserSecretsId>

# Set a secret
dotnet user-secrets set "ConnectionStrings:DefaultConnection" "Host=localhost;Database=mydb;Username=user;Password=realpassword"

# List secrets
dotnet user-secrets list

# Remove a secret
dotnet user-secrets remove "ConnectionStrings:DefaultConnection"

# Clear all secrets
dotnet user-secrets clear
```

**Storage Location:**
- **macOS/Linux:** `~/.microsoft/usersecrets/<UserSecretsId>/secrets.json`
- **Windows:** `%APPDATA%\Microsoft\UserSecrets\<UserSecretsId>\secrets.json`

**Access in Code:**

```csharp
// Program.cs - automatically loaded in Development
var connectionString = builder.Configuration.GetConnectionString("DefaultConnection");

// Or explicit:
var apiKey = builder.Configuration["ApiSettings:ApiKey"];
```

**appsettings.json (committed to Git):**

```json
{
  "ConnectionStrings": {
    "DefaultConnection": "Host=localhost;Database=placeholder;Username=placeholder;Password=USE_USER_SECRETS"
  },
  "ApiSettings": {
    "ApiKey": "USE_USER_SECRETS"
  }
}
```

### Production: Environment Variables

**Set on Server:**

```bash
# Linux/macOS
export ConnectionStrings__DefaultConnection="Server=prod-db;..."

# Windows
setx ConnectionStrings__DefaultConnection "Server=prod-db;..."

# Docker
docker run -e ConnectionStrings__DefaultConnection="..." myapp
```

**Note:** Use double underscores `__` for nested configuration

**docker-compose.yml:**

```yaml
services:
  webapp:
    image: myapp
    environment:
      ConnectionStrings__DefaultConnection: "${DB_CONNECTION_STRING}"
      ApiSettings__ApiKey: "${API_KEY}"
    env_file:
      - .env  # Load from .env file
```

**.env file (NOT committed):**

```bash
DB_CONNECTION_STRING=Server=localhost;Database=mydb;Username=user;Password=secret
API_KEY=your-api-key-here
```

**.env.example (committed):**

```bash
DB_CONNECTION_STRING=Server=localhost;Database=mydb;Username=user;Password=CHANGE_ME
API_KEY=your-api-key-here
```

### Production: Azure Key Vault (Advanced)

**For enterprise applications:**

```csharp
// Program.cs
builder.Configuration.AddAzureKeyVault(
    new Uri("https://myvault.vault.azure.net/"),
    new DefaultAzureCredential());

// Secrets automatically loaded from Azure
var connectionString = builder.Configuration.GetConnectionString("DefaultConnection");
```

---

## Configuration Hierarchy

ASP.NET Core loads configuration in this order (later overrides earlier):

1. `appsettings.json`
2. `appsettings.{Environment}.json` (e.g., `appsettings.Development.json`)
3. **User Secrets** (Development only)
4. **Environment Variables**
5. Command-line arguments

**Example:**

```json
// appsettings.json (committed)
{
  "ConnectionStrings": {
    "DefaultConnection": "Host=localhost;..."
  }
}

// User Secrets (Development, not committed)
{
  "ConnectionStrings": {
    "DefaultConnection": "Host=localhost;Password=devpassword"
  }
}

// Environment Variable (Production)
ConnectionStrings__DefaultConnection="Host=prod-db;Password=prodpassword"
```

**Result:** Development uses User Secrets, Production uses Environment Variable

---

## Securing Docker Compose

### ❌ Bad: Hardcoded Passwords

```yaml
services:
  postgres:
    environment:
      POSTGRES_PASSWORD: supersecret123  # DON'T!
```

### ✅ Good: Environment Variables

```yaml
services:
  postgres:
    environment:
      POSTGRES_PASSWORD: ${POSTGRES_PASSWORD}
    env_file:
      - .env
```

**.env (not committed):**

```bash
POSTGRES_PASSWORD=supersecret123
```

**.gitignore:**

```
.env
*.env
!.env.example
```

**.env.example (committed):**

```bash
POSTGRES_PASSWORD=change_me_in_local_env
```

---

## Git Best Practices

### .gitignore Essentials

```bash
# .gitignore (at repository root)

# User Secrets
**/appsettings.Development.json
**/secrets.json

# Environment files
.env
.env.local
.env.*.local
*.env

# But allow .env.example
!.env.example

# Database files
*.db
*.db-shm
*.db-wal

# VS Code settings (optional)
.vscode/
.vs/
```

### Check Before Commit

```bash
# View what will be committed
git status
git diff --staged

# Search for potential secrets
git grep -i password
git grep -i secret
git grep -i "api.key"

# Check .gitignore is working
git check-ignore .env
# Should output: .env
```

### If You Accidentally Commit Secrets

**⚠️ URGENT: Secrets are compromised!**

1. **Immediately rotate credentials:**
   - Change database passwords
   - Regenerate API keys
   - Revoke tokens

2. **Remove from Git history:**

```bash
# Install BFG Repo-Cleaner
brew install bfg  # macOS
# Or download from: https://rtyley.github.io/bfg-repo-cleaner/

# Remove sensitive file from history
bfg --delete-files appsettings.json

# Or replace passwords in all commits
bfg --replace-text passwords.txt

# Clean up
git reflog expire --expire=now --all
git gc --prune=now --aggressive

# Force push (coordinate with team first!)
git push --force
```

**Better:** Consider repository as compromised, create new one with clean history

---

## Secure Connection String Patterns

### PostgreSQL

```bash
# Development (User Secrets)
dotnet user-secrets set "ConnectionStrings:DefaultConnection" "Host=localhost;Port=5432;Database=myapp;Username=appuser;Password=devpassword123"

# Production (Environment Variable)
export ConnectionStrings__DefaultConnection="Host=prod-db.mycompany.com;Port=5432;Database=myapp;Username=produser;Password=prodpassword456;SslMode=Require"
```

### SQL Server

```bash
# Development
dotnet user-secrets set "ConnectionStrings:DefaultConnection" "Server=localhost;Database=myapp;User Id=sa;Password=DevPassword123!;TrustServerCertificate=True"

# Production with Integrated Security (recommended)
export ConnectionStrings__DefaultConnection="Server=prod-db;Database=myapp;Integrated Security=True;TrustServerCertificate=False"
```

### MongoDB

```bash
# Development
dotnet user-secrets set "MongoDB:ConnectionString" "mongodb://admin:devpassword@localhost:27017"

# Production
export MongoDB__ConnectionString="mongodb+srv://produser:prodpassword@cluster.mongodb.net/myapp?retryWrites=true&w=majority"
```

---

## Code-Level Security

### Input Validation

```csharp
// ✅ Always validate user input
public async Task<IActionResult> OnPostAsync()
{
    if (!ModelState.IsValid)
    {
        return Page();
    }
    
    // Proceed with validated data
}
```

### Parameterized Queries (EF Core handles this!)

```csharp
// ✅ EF Core automatically parameterizes
var user = await _context.Users
    .FirstOrDefaultAsync(u => u.Username == username);
// Generated SQL uses parameters (safe from injection)

// ❌ Raw SQL (if you must use it)
var users = _context.Users
    .FromSqlRaw("SELECT * FROM Users WHERE Username = '" + username + "'")
    .ToList();
// SQL injection vulnerable!

// ✅ Raw SQL with parameters
var users = _context.Users
    .FromSqlRaw("SELECT * FROM Users WHERE Username = {0}", username)
    .ToList();
// Safe
```

### HTTPS Only

```csharp
// Program.cs
if (!app.Environment.IsDevelopment())
{
    app.UseHsts();  // Enforce HTTPS
}
app.UseHttpsRedirection();  // Redirect HTTP to HTTPS
```

---

## Database Security

### Principle of Least Privilege

```sql
-- ❌ Bad: App uses database admin account
POSTGRES_USER=postgres
POSTGRES_PASSWORD=admin123

-- ✅ Good: Create limited user
CREATE USER appuser WITH PASSWORD 'securepassword';
GRANT CONNECT ON DATABASE myapp TO appuser;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO appuser;
```

### Use Different Credentials Per Environment

| Environment | Username | Password | Permissions |
|-------------|----------|----------|-------------|
| Development | `devuser` | User Secrets | Full access |
| Staging | `stageuser` | Key Vault | Limited |
| Production | `produser` | Key Vault | Minimal required |

### Firewall Rules

**PostgreSQL `pg_hba.conf`:**

```conf
# Allow from localhost only (development)
host    all             all             127.0.0.1/32            md5

# Production: specific IPs only
host    all             produser        10.0.1.0/24             md5
```

---

## Auditing & Monitoring

### Log Security Events (Not Credentials!)

```csharp
// ✅ Log authentication attempts
_logger.LogInformation("User {Username} logged in", username);

// ❌ Never log passwords!
_logger.LogInformation("Login: {Username}/{Password}", username, password);
```

### Monitor for Suspicious Activity

- Failed login attempts
- Unusual query patterns
- Large data exports
- Off-hours access

---

## Secure Development Checklist

### Before Every Commit

- [ ] No hardcoded passwords in code
- [ ] No connection strings with credentials
- [ ] No API keys or tokens
- [ ] `.gitignore` includes `.env`, `secrets.json`
- [ ] Run `git grep -i password` to double-check

### Project Setup

- [ ] Initialize User Secrets: `dotnet user-secrets init`
- [ ] Create `.env.example` with placeholder values
- [ ] Add security notes to README
- [ ] Configure HTTPS redirection
- [ ] Use strong, unique passwords per environment

### Production Deployment

- [ ] Use environment variables or Key Vault
- [ ] Enable HTTPS/TLS
- [ ] Configure firewall rules
- [ ] Use database user with minimal permissions
- [ ] Enable database audit logging
- [ ] Set up monitoring and alerts

---

## Team Collaboration

### Onboarding New Developers

**README.md should include:**

```markdown
## Setup

1. Clone repository
2. Copy `.env.example` to `.env`
3. Update `.env` with your local database credentials
4. Initialize User Secrets:
   ```bash
   dotnet user-secrets init
   dotnet user-secrets set "ConnectionStrings:DefaultConnection" "your-connection-string"
   ```
5. Run migrations: `dotnet ef database update`
6. Start app: `dotnet run`
```

### Sharing Credentials Securely

**❌ Don't:**
- Email passwords
- Slack/Teams DM credentials
- Commit to shared documents

**✅ Do:**
- Use password manager (1Password, LastPass)
- Encrypted communication (Signal, secure chat)
- Corporate secrets management system

---

## Testing Security

### Check .gitignore Works

```bash
# Create a test secret file
echo "password=secret123" > .env

# Check if Git ignores it
git status
# .env should NOT appear in untracked files

# Verify
git check-ignore .env
# Should output: .env
```

### Scan for Secrets

**Tools:**
- **git-secrets** - https://github.com/awslabs/git-secrets
- **TruffleHog** - https://github.com/trufflesecurity/trufflehog
- **GitHub Secret Scanning** (automatic on GitHub)

```bash
# Install git-secrets
brew install git-secrets

# Set up hooks
cd your-repo
git secrets --install
git secrets --register-aws

# Scan repository
git secrets --scan
```

---

## Common Mistakes

### ❌ Mistake 1: Testing with Production Credentials

```csharp
// Don't use production DB for development!
var connString = "Server=PROD-DB;..."  // NO!
```

**Solution:** Separate environments, separate credentials

### ❌ Mistake 2: Committing appsettings.Development.json

**.gitignore should include:**

```bash
**/appsettings.Development.json
```

### ❌ Mistake 3: Forgetting Docker Environment Variables

```yaml
# ❌ Hardcoded
environment:
  POSTGRES_PASSWORD: secret123

# ✅ From .env file
environment:
  POSTGRES_PASSWORD: ${POSTGRES_PASSWORD}
```

### ❌ Mistake 4: Connection String in Exception Messages

```csharp
// ❌ Bad
catch (Exception ex)
{
    _logger.LogError("Failed to connect: {ConnectionString}", connectionString);
}

// ✅ Good
catch (Exception ex)
{
    _logger.LogError(ex, "Database connection failed");
}
```

---

## Quick Reference

### User Secrets Commands

```bash
dotnet user-secrets init
dotnet user-secrets set "Key:Subkey" "value"
dotnet user-secrets list
dotnet user-secrets remove "Key:Subkey"
dotnet user-secrets clear
```

### Environment Variable Syntax

```bash
# Nested configuration
Key:Subkey:Value → Key__Subkey__Value

# Example
ConnectionStrings:DefaultConnection → ConnectionStrings__DefaultConnection
```

### Essential .gitignore Entries

```bash
.env
*.env
!.env.example
**/appsettings.Development.json
**/secrets.json
*.db
```

---

## Next Steps

- Practice with [06.SecureConnections](../06.SecureConnections/)
- Review [Docker Setup Guide](docker-setup.md) for secure Docker configs
- Implement in [07.ComprehensiveApp](../07.ComprehensiveApp/)

---

## Key Takeaways

✅ Never commit credentials to Git  
✅ Use User Secrets for development  
✅ Use Environment Variables or Key Vault for production  
✅ Always check `.gitignore` before committing  
✅ Rotate credentials if accidentally exposed  
✅ Use different credentials per environment  
✅ Grant minimal database permissions  

**Remember:** Security is not optional - it's your responsibility as a developer!

---

## Resources

- [ASP.NET Core User Secrets](https://learn.microsoft.com/en-us/aspnet/core/security/app-secrets)
- [Azure Key Vault](https://azure.microsoft.com/en-us/services/key-vault/)
- [OWASP Top 10](https://owasp.org/www-project-top-ten/)
- [Git Secrets Tool](https://github.com/awslabs/git-secrets)
