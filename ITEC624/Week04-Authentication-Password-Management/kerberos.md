# Kerberos on Debian-based Linux Distributions

## Introduction

**Note:** This document provides a brief introduction to Kerberos. Kerberos is an advanced topic in network authentication and security. This guide covers the basics of installing, configuring, and using Kerberos on Debian-based Linux distributions (such as Ubuntu, Debian, etc.). For production environments and enterprise deployments, consult comprehensive Kerberos documentation and consider engaging with security professionals.

## What is Kerberos?

Kerberos is a network authentication protocol developed by MIT in the 1980s. It is designed to provide strong authentication for client/server applications by using secret-key cryptography. Named after Cerberus, the three-headed dog from Greek mythology guarding the gates of Hades, Kerberos uses a trusted third-party approach to verify the identities of users and services.

**Key characteristics:**

- **Centralized authentication**: Single sign-on (SSO) capability across multiple services
- **Mutual authentication**: Both client and server verify each other's identity
- **Ticket-based**: Uses encrypted tickets instead of sending passwords over the network
- **Time-limited**: Tickets have expiration times to minimize security risks
- **Replay attack protection**: Uses timestamps to prevent replay attacks
- **No password transmission**: Passwords are never sent over the network in plaintext

**Common use cases:**

- Enterprise networks requiring secure authentication
- Single sign-on (SSO) solutions
- Integration with Active Directory in mixed environments
- Secure access to network services (SSH, NFS, HTTP, etc.)
- Hadoop and big data clusters
- Database authentication

## How it works?

Kerberos authentication involves three main components:

1. **Client**: The user or application requesting access
2. **Key Distribution Center (KDC)**: The authentication server
3. **Service**: The resource or application the client wants to access

The KDC consists of two parts:
- **Authentication Server (AS)**: Issues Ticket Granting Tickets (TGT)
- **Ticket Granting Server (TGS)**: Issues service tickets

### Kerberos Authentication Flow Diagram

```
┌─────────┐                                          ┌──────────┐
│ Client  │                                          │   KDC    │
│         │                                          │ (AS/TGS) │
└────┬────┘                                          └────┬─────┘
     │                                                   │
     │  1. Request TGT (AS_REQ)                          │
     │  ───────────────────────────────────────────────> │
     │     (Username, Service: krbtgt, Timestamp)       │
     │                                                   │
     │                                                   │ 2. Lookup user in database
     │                                                   │    Verify credentials
     │                                                   │
     │  3. TGT Response (AS_REP)                         │
     │  <─────────────────────────────────────────────── │
     │     (TGT encrypted with user's password hash,    │
     │      Session key encrypted with user's password) │
     │                                                   │
     │  4. Decrypt TGT using password hash              │
     │     Store TGT and session key                     │
     │                                                   │
     │  5. Request Service Ticket (TGS_REQ)              │
     │  ───────────────────────────────────────────────> │
     │     (TGT, Authenticator encrypted with           │
     │      TGS session key, Service name, Timestamp)    │
     │                                                   │
     │                                                   │ 6. Verify TGT, validate authenticator
     │                                                   │    Check timestamp, create service ticket
     │                                                   │
     │  7. Service Ticket (TGS_REP)                      │
     │  <─────────────────────────────────────────────── │
     │     (Service ticket encrypted with service key,  │
     │      Service session key encrypted with TGS       │
     │      session key)                                 │
     │                                                   │
     │                                                   │
     │                                                    ┌──────────┐
     │                                                    │ Service  │
     │                                                    │ Server   │
     │                                                    └────┬─────┘
     │                                                         │
     │  8. Request Service Access (AP_REQ)                    │
     │  ────────────────────────────────────────────────────> │
     │     (Service ticket, Authenticator encrypted           │
     │      with service session key, Timestamp)              │
     │                                                         │
     │                                                         │ 9. Verify service ticket
     │                                                         │    Validate authenticator
     │                                                         │    Check timestamp
     │                                                         │
     │  10. Service Response (AP_REP) [Optional]              │
     │  <──────────────────────────────────────────────────── │
     │     (Mutual authentication response)                   │
     │                                                         │
     │  11. Access Granted                                    │
     │                                                         │
```

### Step-by-step explanation:

1. **AS_REQ (Authentication Server Request)**: Client requests a Ticket Granting Ticket (TGT) from the Authentication Server, providing username and requesting the `krbtgt` service.

2. **AS looks up user**: The AS looks up the user in its database and retrieves the user's password hash.

3. **AS_REP (Authentication Server Response)**: The AS creates:
   - A TGT encrypted with the KDC's secret key
   - A TGS session key encrypted with the user's password hash
   - Both are sent to the client

4. **Client decrypts**: The client uses the user's password (converted to hash) to decrypt the TGS session key. The TGT cannot be decrypted by the client (it's encrypted with the KDC's key).

5. **TGS_REQ (Ticket Granting Server Request)**: When the client needs access to a service, it creates an authenticator (timestamp, client identity) encrypted with the TGS session key, and sends both the TGT and authenticator to the TGS.

6. **TGS validates**: The TGS decrypts the TGT using the KDC's key, extracts the TGS session key, uses it to decrypt the authenticator, validates the timestamp, and creates a service ticket.

7. **TGS_REP (Ticket Granting Server Response)**: The TGS sends:
   - A service ticket encrypted with the service's secret key
   - A service session key encrypted with the TGS session key

8. **AP_REQ (Application Request)**: The client creates an authenticator encrypted with the service session key and sends both the service ticket and authenticator to the service server.

9. **Service validates**: The service decrypts the service ticket using its secret key, extracts the service session key, uses it to decrypt the authenticator, and validates the timestamp.

10. **AP_REP (Application Response)**: Optional mutual authentication where the service proves its identity to the client.

11. **Access granted**: The service provides access to the requested resource.

### Key components:

- **TGT (Ticket Granting Ticket)**: Long-lived ticket used to request service tickets (typically 10 hours)
- **Service Ticket**: Short-lived ticket for accessing specific services (typically 5 minutes)
- **Authenticator**: Proof of identity containing timestamp, encrypted with a session key
- **Session Keys**: Symmetric keys used for secure communication between parties
- **Realm**: A Kerberos administrative domain (e.g., `EXAMPLE.COM`)

## How to install Kerberos on Debian based Linux distro

### Prerequisites

- Root or sudo access
- Network connectivity
- A fully qualified domain name (FQDN) for the KDC server (recommended)

### Installation Steps

1. **Update package list:**
```bash
sudo apt update
```

2. **Install Kerberos KDC and administration server:**
```bash
sudo apt install krb5-kdc krb5-admin-server krb5-config
```

During installation, you'll be prompted for:
- **Default Kerberos version 5 realm**: Enter your realm name (e.g., `EXAMPLE.COM`)
- **Kerberos servers**: Enter the hostname of your KDC server
- **Administrative servers**: Enter the hostname of your admin server (usually same as KDC)

3. **Install additional packages (optional):**
```bash
# Client utilities for testing and administration
sudo apt install krb5-user

# Documentation
sudo apt install krb5-doc

# Development libraries (for applications integrating with Kerberos)
sudo apt install libkrb5-dev
```

4. **Verify installation:**
```bash
# Check if KDC is installed
dpkg -l | grep krb5

# Check Kerberos version
krb5-config --version
```

### Installation Components

- **krb5-kdc**: Key Distribution Center server
- **krb5-admin-server**: Administrative server for managing the Kerberos database
- **krb5-config**: Configuration package that sets up `/etc/krb5.conf`
- **krb5-user**: Client utilities (`kinit`, `klist`, `kdestroy`, `kpasswd`)
- **krb5-doc**: Documentation and man pages

## Briefly explain the packages related to Kerberos

| Package | Description | Purpose |
|---------|-------------|---------|
| `krb5-kdc` | Key Distribution Center | The core Kerberos server that issues tickets. Contains both the Authentication Server (AS) and Ticket Granting Server (TGS). Required for running a Kerberos realm. |
| `krb5-admin-server` | Kerberos Administration Server | Manages the Kerberos database using `kadmin` or `kadmin.local`. Allows remote administration via `kadmind` daemon. Required for managing principals and policies. |
| `krb5-user` | Kerberos user programs | Client-side utilities including:<br>- `kinit`: Obtain and cache tickets<br>- `klist`: List cached tickets<br>- `kdestroy`: Destroy cached tickets<br>- `kpasswd`: Change passwords<br>- `kvno`: Display version numbers of keys |
| `krb5-config` | Configuration files | Sets up default `/etc/krb5.conf` configuration file during installation. Provides initial configuration based on installation prompts. |
| `krb5-doc` | Kerberos documentation | Comprehensive documentation including man pages, HOWTO guides, and reference materials. Helpful for understanding advanced configuration. |
| `libkrb5-dev` | Development libraries | Libraries and header files for developing applications that integrate with Kerberos. Required for compiling programs that use GSSAPI or Kerberos authentication. |
| `libkrb5-3` | Kerberos runtime libraries | Core libraries used by Kerberos applications. Usually installed as a dependency of other Kerberos packages. |
| `krb5-pkinit` | PKINIT plugin | Plugin for public key initialization. Allows using X.509 certificates for initial authentication instead of passwords. |

### Additional related packages:

- **`krb5-kdc-ldap`**: LDAP backend for Kerberos KDC (alternative to default database backend)
- **`krb5-multidev`**: Multiple development versions of Kerberos libraries
- **`krb5-locales`**: Locale data for Kerberos messages

## How to configure Kerberos on Debian based Linux distro

### 1. Create the Kerberos Database

After installation, create the Kerberos database:

```bash
sudo krb5_newrealm
```

This command will:
- Create the Kerberos database
- Create the KDC configuration file (`/etc/krb5kdc/kdc.conf`)
- Set up ACL (Access Control List) for administration
- Start the KDC and admin server

You'll be prompted to enter a master password for the database. **Store this password securely** as it's needed for administrative tasks.

### 2. Configure `/etc/krb5.conf`

The main configuration file is `/etc/krb5.conf`. Edit it according to your needs:

```bash
sudo nano /etc/krb5.conf
```

Example configuration:

```ini
[libdefaults]
    default_realm = EXAMPLE.COM
    dns_lookup_realm = false
    dns_lookup_kdc = false
    ticket_lifetime = 24h
    renew_lifetime = 7d
    forwardable = true
    rdns = false

[realms]
    EXAMPLE.COM = {
        kdc = kdc.example.com
        admin_server = kdc.example.com
        default_domain = example.com
    }

[domain_realm]
    .example.com = EXAMPLE.COM
    example.com = EXAMPLE.COM

[logging]
    kdc = FILE:/var/log/krb5kdc.log
    admin_server = FILE:/var/log/kadmind.log
    default = FILE:/var/log/krb5libs.log
```

**Key settings:**
- `default_realm`: Your Kerberos realm name
- `ticket_lifetime`: How long tickets are valid (default: 10 hours)
- `renew_lifetime`: Maximum renewal period (default: 7 days)
- `kdc`: Hostname of your KDC server
- `admin_server`: Hostname of your admin server

### 3. Configure `/etc/krb5kdc/kdc.conf`

Edit the KDC configuration:

```bash
sudo nano /etc/krb5kdc/kdc.conf
```

Example configuration:

```ini
[kdcdefaults]
    kdc_ports = 750,88
    kdc_tcp_ports = 750,88

[realms]
    EXAMPLE.COM = {
        database_module = db_module2
        acl_file = /etc/krb5kdc/kadm5.acl
        key_stash_file = /etc/krb5kdc/stash
        kdc_ports = 750,88
        kdc_tcp_ports = 750,88
        max_life = 10h 0m 0s
        max_renewable_life = 7d 0h 0m 0s
        master_key_type = aes256-cts
        supported_enctypes = aes256-cts:normal aes128-cts:normal
        default_principal_flags = +preauth
    }

[db_module2]
    db_library = kldap
```

### 4. Configure ACL (Access Control List)

Edit `/etc/krb5kdc/kadm5.acl` to define who can administer Kerberos:

```bash
sudo nano /etc/krb5kdc/kadm5.acl
```

Example:

```
*/admin@EXAMPLE.COM    *
```

This allows any principal with `/admin` in the name to have full administrative privileges.

### 5. Start and Enable Services

```bash
# Start KDC service
sudo systemctl start krb5-kdc

# Start admin server
sudo systemctl start krb5-admin-server

# Enable services to start on boot
sudo systemctl enable krb5-kdc
sudo systemctl enable krb5-admin-server

# Check status
sudo systemctl status krb5-kdc
sudo systemctl status krb5-admin-server
```

### 6. Create Administrative Principal

Create an admin principal for managing Kerberos:

```bash
sudo kadmin.local
```

Inside `kadmin.local`:

```
kadmin.local: addprinc admin/admin@EXAMPLE.COM
Enter password for principal "admin/admin@EXAMPLE.COM":
Re-enter password for principal "admin/admin@EXAMPLE.COM":
Principal "admin/admin@EXAMPLE.COM" created.

kadmin.local: quit
```

### 7. Firewall Configuration

If using a firewall, allow Kerberos ports:

```bash
# UDP ports
sudo ufw allow 88/udp    # Kerberos authentication
sudo ufw allow 464/udp   # kpasswd server

# TCP ports (if needed)
sudo ufw allow 88/tcp
sudo ufw allow 464/tcp
sudo ufw allow 749/tcp   # Admin server
```

### 8. Test Configuration

```bash
# Check KDC is running
sudo systemctl status krb5-kdc

# Test kinit with admin principal
kinit admin/admin@EXAMPLE.COM

# List tickets
klist

# Test kadmin
kadmin -p admin/admin@EXAMPLE.COM
```

## How to use Kerberos on Debian based Linux distro

### Basic Client Operations

#### 1. Obtain a Ticket (kinit)

Authenticate and obtain a TGT:

```bash
kinit username@EXAMPLE.COM
```

You'll be prompted for the password. Alternatively, use a keytab file:

```bash
kinit -k -t /path/to/keytab username@EXAMPLE.COM
```

Options:
- `-k`: Use keytab file instead of password
- `-t`: Specify keytab file path
- `-f`: Forwardable ticket
- `-r`: Renewable ticket lifetime

#### 2. List Cached Tickets (klist)

View your current tickets:

```bash
klist
```

Output shows:
- Principal name
- Ticket expiration time
- Service tickets for specific services

View with more details:

```bash
klist -v
```

#### 3. Destroy Tickets (kdestroy)

Remove all cached tickets:

```bash
kdestroy
```

#### 4. Change Password (kpasswd)

Change your Kerberos password:

```bash
kpasswd username@EXAMPLE.COM
```

You'll be prompted for:
1. Current password
2. New password
3. New password (confirmation)

### Administrative Operations

#### 5. Administer Principals (kadmin)

Connect to kadmin as an admin user:

```bash
kadmin -p admin/admin@EXAMPLE.COM
```

Or use `kadmin.local` on the KDC server (no password required):

```bash
sudo kadmin.local
```

**Common kadmin commands:**

```bash
# List all principals
kadmin: list_principals

# Add a new principal
kadmin: addprinc username@EXAMPLE.COM

# Delete a principal
kadmin: delprinc username@EXAMPLE.COM

# Modify a principal
kadmin: modprinc +requires_preauth username@EXAMPLE.COM

# Set password expiration
kadmin: modprinc -expire "2025-12-31 23:59:59" username@EXAMPLE.COM

# Get principal details
kadmin: getprinc username@EXAMPLE.COM

# Create a keytab file
kadmin: ktadd -k /path/to/keytab username@EXAMPLE.COM

# List policies
kadmin: list_policies

# Create a policy
kadmin: add_policy -maxlife 1days -minlength 8 password_policy

# Apply policy to principal
kadmin: modprinc -policy password_policy username@EXAMPLE.COM
```

#### 6. Use Keytabs

Keytabs allow passwordless authentication:

**Create a keytab:**

```bash
kadmin -p admin/admin@EXAMPLE.COM
kadmin: ktadd -k /etc/krb5.keytab host/hostname.example.com@EXAMPLE.COM
kadmin: quit
```

**Use a keytab:**

```bash
kinit -k -t /etc/krb5.keytab host/hostname.example.com@EXAMPLE.COM
```

### Service Configuration

#### 7. Configure SSH with Kerberos

Edit `/etc/ssh/sshd_config`:

```bash
sudo nano /etc/ssh/sshd_config
```

Add/modify:

```
GSSAPIAuthentication yes
GSSAPICleanupCredentials yes
```

Restart SSH:

```bash
sudo systemctl restart sshd
```

#### 8. Test Kerberos Authentication

```bash
# Get ticket
kinit username@EXAMPLE.COM

# Verify ticket
klist

# Test SSH with Kerberos
ssh -v username@hostname.example.com

# If configured correctly, no password prompt should appear
```

### Troubleshooting

#### Check Logs

```bash
# KDC logs
sudo tail -f /var/log/krb5kdc.log

# Admin server logs
sudo tail -f /var/log/kadmind.log

# Client library logs
tail -f /var/log/krb5libs.log
```

#### Common Issues

1. **Clock skew**: Ensure all systems have synchronized clocks (use NTP)
   ```bash
   sudo apt install ntp
   sudo systemctl start ntp
   ```

2. **Cannot contact KDC**: Check network connectivity and DNS resolution
   ```bash
   ping kdc.example.com
   nslookup kdc.example.com
   ```

3. **Invalid credentials**: Verify username and realm spelling
   ```bash
   kinit username@EXAMPLE.COM  # Note: uppercase realm
   ```

4. **Ticket expired**: Renew or obtain a new ticket
   ```bash
   kinit -R  # Renew existing ticket
   # or
   kinit username@EXAMPLE.COM  # Get new ticket
   ```

### Example Workflow

```bash
# 1. Authenticate
kinit alice@EXAMPLE.COM
Password for alice@EXAMPLE.COM: ********

# 2. Verify ticket
klist
Ticket cache: FILE:/tmp/krb5cc_1000
Default principal: alice@EXAMPLE.COM

Valid starting     Expires            Service principal
12/01/24 10:00:00  12/01/24 20:00:00  krbtgt/EXAMPLE.COM@EXAMPLE.COM

# 3. Access service (SSH example)
ssh server.example.com
# No password prompt if Kerberos is configured

# 4. When done, destroy tickets
kdestroy

# 5. Verify tickets are destroyed
klist
klist: No credentials cache found (filename: /tmp/krb5cc_1000)
```

## Conclusion

Kerberos provides a robust, secure authentication mechanism for network services. This guide covered the basics of installing, configuring, and using Kerberos on Debian-based Linux distributions.

**Key takeaways:**

- Kerberos uses a trusted third-party (KDC) for authentication
- Tickets replace password transmission over the network
- Centralized authentication enables single sign-on (SSO)
- Proper configuration and key management are critical for security
- Regular monitoring and log review are essential for troubleshooting

**Next steps for deeper understanding:**

- Study Kerberos protocol specifications (RFC 4120)
- Learn about cross-realm trust relationships
- Explore integration with Active Directory
- Understand GSSAPI and how applications use Kerberos
- Study advanced topics like PKINIT, constrained delegation, and encryption types
- Practice setting up multi-server Kerberos deployments
- Learn about Kerberos security best practices

**Security considerations:**

- Protect the KDC database and stash file
- Use strong passwords for administrative principals
- Implement proper ACLs in `kadm5.acl`
- Keep Kerberos software updated
- Monitor logs for suspicious activity
- Ensure clock synchronization across all systems
- Use appropriate encryption types (prefer AES-256)
- Regular backup of the Kerberos database

Kerberos is a complex but powerful authentication system. Mastering it requires practice and understanding of both the protocol and its implementation details. This guide provides a foundation, but production deployments should involve thorough planning and security review.

