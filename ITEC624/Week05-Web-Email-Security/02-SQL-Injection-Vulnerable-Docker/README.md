# SQL Injection Lab: Intentionally Vulnerable Application

This lab demonstrates classic SQL injection vulnerabilities in a Node.js/Express application with SQLite database. It provides both vulnerable and secure implementations to help you understand the differences and learn proper defense techniques.

**Suggested image name:** `sql-injection-lab` (alternatives: `sql-injection-vulnerable`, `sql-injection-playground`).

## 🚀 Quick Start

### Run with Docker

```bash
# From this directory
docker build -t sql-injection-lab .

# Run on port 3000
docker run --rm -p 3000:3000 sql-injection-lab
```

Open `http://localhost:3000` in your browser.

### Run Locally (Development)

```bash
npm install
npm start
```

## 📋 Application Overview

### Routes Available:
- **`/`** - Login page with both vulnerable and safe login forms
- **`/search`** - Vulnerable user search with SQL injection
- **`/safe-search`** - Safe user search using parameterized queries
- **`/schema`** - Database schema information

### Sample Users:
- `admin` / `admin123` (admin role)
- `john_doe` / `password123` (user role)
- `jane_smith` / `secret456` (user role)
- `bob_wilson` / `mypassword` (user role)

## 🎯 What is SQL Injection?

SQL Injection is a code injection technique where malicious SQL statements are inserted into an application's database query. This happens when user input is directly concatenated into SQL queries without proper sanitization or parameterization.

### Why This Application is Vulnerable

The vulnerable endpoints use direct string concatenation to build SQL queries:

```javascript
// VULNERABLE: Direct string concatenation
const query = `SELECT * FROM users WHERE username = '${username}' AND password = '${password}'`;
```

This allows attackers to inject malicious SQL code that gets executed by the database.

## 🔓 How to Exploit (For Learning)

### 1. Authentication Bypass

**Vulnerable Login Form:**
- Username: `admin' --`
- Password: (leave empty)

This payload comments out the password check, allowing login as admin.

### 2. Union-Based Data Extraction

**Search Form Payloads:**
- Extract all users: `' OR '1'='1`
- Union attack: `' UNION SELECT id,username,email,role,password FROM users --`
- Extract admin users: `' OR role='admin' --`
- Database schema: `' UNION SELECT name,name,name,name,name FROM sqlite_master WHERE type='table' --`

### 3. Advanced Techniques

**Login Form Advanced Payloads:**
- Always true: `' OR '1'='1`
- Extract all data: `' UNION SELECT 1,username,email,role,password FROM users --`
- Conditional extraction: `' UNION SELECT 1,2,3,4,5 FROM users WHERE role='admin' --`

### 💡 Exploitation Hints

1. **Start with simple payloads** like `' OR '1'='1`
2. **Use UNION attacks** to extract data from other tables
3. **Comment out remaining query** with `--` or `#`
4. **Test different injection points** (username, password, search)
5. **Compare vulnerable vs safe implementations**

## 🛡️ How to Fix (In This Application)

### 1. Use Parameterized Queries (Prepared Statements)

```javascript
// SAFE: Parameterized query
const query = `SELECT * FROM users WHERE username = ? AND password = ?`;
db.all(query, [username, password], callback);
```

### 2. Input Validation and Sanitization

```javascript
// Validate input format
if (!/^[a-zA-Z0-9_]+$/.test(username)) {
    return res.status(400).send('Invalid username format');
}
```

### 3. Use ORM/Query Builder

```javascript
// Using an ORM like Sequelize
const user = await User.findOne({
    where: { username: username, password: password }
});
```

## 🔒 How to Prevent (General Guidance)

### 1. **Always Use Parameterized Queries**
- Never concatenate user input directly into SQL queries
- Use prepared statements or parameterized queries
- This is the most effective defense

### 2. **Input Validation**
- Validate input format and length
- Use allowlists for expected values
- Reject suspicious patterns

### 3. **Least Privilege Principle**
- Database users should have minimal required permissions
- Use separate database accounts for different operations
- Avoid using database admin accounts in applications

### 4. **Defense in Depth**
- Implement Web Application Firewalls (WAF)
- Use database monitoring and logging
- Regular security testing and code reviews

### 5. **Additional Security Measures**
- Enable SQL query logging
- Use database connection encryption
- Implement rate limiting
- Regular security updates

## 🧪 Testing the Application

### 1. Test Vulnerable Endpoints
- Try the vulnerable login form with injection payloads
- Test the vulnerable search functionality
- Observe how the SQL queries are constructed

### 2. Test Safe Endpoints
- Use the safe login form with the same payloads
- Notice how parameterized queries prevent injection
- Compare the executed SQL queries

### 3. Database Schema Exploration
- Visit `/schema` to understand the database structure
- Use this information to craft more targeted attacks
- Learn how to extract metadata from databases

## 📊 Database Schema

The application uses an in-memory SQLite database with the following structure:

```sql
CREATE TABLE users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL,
    email TEXT NOT NULL,
    password TEXT NOT NULL,
    role TEXT DEFAULT 'user'
);
```

## 🎓 Learning Objectives

After completing this lab, you should understand:

1. **How SQL injection vulnerabilities occur**
2. **Common SQL injection attack techniques**
3. **How to identify vulnerable code patterns**
4. **Proper defense mechanisms and best practices**
5. **The importance of input validation and parameterized queries**

## 🧹 Cleanup

Stop the container with Ctrl+C (or remove with `docker ps` / `docker stop`).

## 📚 Additional Resources

- [OWASP SQL Injection Prevention Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/SQL_Injection_Prevention_Cheat_Sheet.html)
- [SQLite Documentation](https://www.sqlite.org/docs.html)
- [Node.js SQLite3 Documentation](https://github.com/mapbox/node-sqlite3)

## ⚠️ Disclaimer

This application is intentionally vulnerable and should **NEVER** be used in production environments. It is designed solely for educational purposes to help understand SQL injection vulnerabilities and proper defense techniques.

## 🏷️ Suggested Names

- `sql-injection-lab` (recommended)
- `sql-injection-vulnerable`
- `sql-injection-playground`
- `sql-injection-training`
