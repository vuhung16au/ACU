# The /etc/passwd File

## Introduction

The `/etc/passwd` file is a fundamental system file in Unix-like operating systems that contains essential user account information. It serves as a database of user accounts on the system, storing details such as usernames, user IDs, group IDs, home directories, and default shells. Understanding `/etc/passwd` is crucial for system administrators, security professionals, and anyone working with Unix-like systems.

## What is a Unix-like Operating System (OS)?

A Unix-like operating system is an OS that behaves in a manner similar to Unix, while not necessarily conforming to or being certified to any version of the Single UNIX Specification. Unix-like systems share common characteristics:

- **Multi-user, multi-tasking**: Multiple users can run multiple processes simultaneously
- **Hierarchical file system**: Organized in a tree structure with a single root directory (`/`)
- **Shell-based interface**: Command-line interface with powerful scripting capabilities
- **Portable**: Written in high-level languages, particularly C
- **File permissions**: Built-in access control mechanisms

Examples include:
- **Linux** (GNU/Linux): Various distributions like Ubuntu, Debian, CentOS, Red Hat
- **macOS**: Apple's Unix-based operating system (certified Unix)
- **FreeBSD, OpenBSD, NetBSD**: BSD-derived systems
- **Solaris**: Oracle's Unix system
- **AIX**: IBM's Unix variant

## The Structure of the /etc/passwd File

The `/etc/passwd` file is a plain text file with one line per user account. Each line contains seven colon-separated fields:

```
username:password:UID:GID:GECOS:home_directory:shell
```

Fields are separated by colons (`:`) and must appear in this specific order. Even if a field is empty, it must still be represented by its colon delimiter.

## The Fields of the /etc/passwd File

1. **Username** (Field 1): The login name of the user. Must be unique and typically limited to alphanumeric characters, hyphens, and underscores.

2. **Password** (Field 2): Historically stored encrypted passwords, but now typically contains `x` or `*` indicating that the actual password hash is stored in `/etc/shadow` for security reasons. An empty field means no password is required.

3. **User ID (UID)** (Field 3): A unique numeric identifier for the user. UID 0 is reserved for the root user. System accounts typically have UIDs below 1000, while regular users usually have UIDs starting from 1000 or 10000.

4. **Group ID (GID)** (Field 4): The primary group ID for the user. This determines the user's primary group membership, which affects file creation permissions.

5. **GECOS** (Field 5): General Electric Comprehensive Operating System field, also called the "comment field." Typically contains:
   - Full name of the user
   - Room number
   - Work phone
   - Home phone
   - Other contact information
   - Format: Usually comma-separated, though conventions vary

6. **Home Directory** (Field 6): The absolute path to the user's home directory, where personal files and configuration are stored. Example: `/home/username` on Linux, `/Users/username` on macOS.

7. **Shell** (Field 7): The absolute path to the user's default login shell. Common values:
   - `/bin/bash`: Bash shell (most common on Linux)
   - `/bin/sh`: POSIX-compliant shell
   - `/bin/zsh`: Z shell (default on macOS)
   - `/usr/bin/nologin` or `/bin/false`: Prevents interactive login (used for system accounts)
   - `/bin/csh`: C shell

## The Examples of the /etc/passwd File

Here are example entries from `/etc/passwd`:

```
root:x:0:0:root:/root:/bin/bash
daemon:x:1:1:daemon:/usr/sbin:/usr/sbin/nologin
bin:x:2:2:bin:/bin:/usr/sbin/nologin
sys:x:3:3:sys:/dev:/usr/sbin/nologin
sync:x:4:65534:sync:/bin:/bin/sync
games:x:5:60:games:/usr/games:/usr/sbin/nologin
man:x:6:12:man:/var/cache/man:/usr/sbin/nologin
lp:x:7:7:lp:/var/spool/lpd:/usr/sbin/nologin
mail:x:8:8:mail:/var/mail:/usr/sbin/nologin
news:x:9:9:news:/var/spool/news:/usr/sbin/nologin
uucp:x:10:10:uucp:/var/spool/uucp:/usr/sbin/nologin
proxy:x:13:13:proxy:/bin:/usr/sbin/nologin
www-data:x:33:33:www-data:/var/www:/usr/sbin/nologin
backup:x:34:34:backup:/var/backups:/usr/sbin/nologin
list:x:38:38:Mailing List Manager:/var/list:/usr/sbin/nologin
irc:x:39:39:ircd:/var/run/ircd:/usr/sbin/nologin
gnats:x:41:41:Gnats Bug-Reporting System (admin):/var/lib/gnats:/usr/sbin/nologin
nobody:x:65534:65534:nobody:/nonexistent:/usr/sbin/nologin
systemd-network:x:100:102:systemd Network Management,,,:/run/systemd:/usr/sbin/nologin
systemd-resolve:x:101:103:systemd Resolver,,,:/run/systemd:/usr/sbin/nologin
messagebus:x:102:104::/nonexistent:/usr/sbin/nologin
sshd:x:103:65534::/run/sshd:/usr/sbin/nologin
johndoe:x:1000:1000:John Doe,Office 123,555-1234,555-5678:/home/johndoe:/bin/bash
janedoe:x:1001:1001:Jane Doe:/home/janedoe:/bin/bash
```

**Analysis of example entries:**

- `root:x:0:0:root:/root:/bin/bash`: Root user with UID 0, GID 0, home directory `/root`, uses bash shell
- `daemon:x:1:1:daemon:/usr/sbin:/usr/sbin/nologin`: System account for daemon processes, cannot login
- `johndoe:x:1000:1000:John Doe,Office 123,555-1234,555-5678:/home/johndoe:/bin/bash`: Regular user with full GECOS information
- `nobody:x:65534:65534:nobody:/nonexistent:/usr/sbin/nologin`: System account used for running unprivileged services

## How to use `grep` command to search for a user in the /etc/passwd file

The `grep` command is invaluable for searching `/etc/passwd`. Here are practical examples:

### Search for a specific username:
```bash
grep "username" /etc/passwd
```

### Search for root user:
```bash
grep "root" /etc/passwd
```

### Case-insensitive search:
```bash
grep -i "USERNAME" /etc/passwd
```

### Find users with a specific UID:
```bash
grep ":0:" /etc/passwd  # Find users with UID 0 (root equivalent)
```

### Find users with UID less than 1000 (system accounts):
```bash
grep -E '^[^:]*:[^:]*:[0-9]{1,3}:' /etc/passwd
```

### Find users with UID greater than or equal to 1000 (regular users):
```bash
grep -E '^[^:]*:[^:]*:([1-9][0-9]{3}|[1-9][0-9]{4,}):' /etc/passwd
```

### Find users with a specific shell:
```bash
grep "/bin/bash$" /etc/passwd  # Users using bash shell
grep "/usr/sbin/nologin$" /etc/passwd  # System accounts that cannot login
```

### Find users with a specific home directory pattern:
```bash
grep "/home/" /etc/passwd  # Users with home directories in /home
```

### Count total number of users:
```bash
grep -c "^[^:]" /etc/passwd  # Count non-comment lines
wc -l /etc/passwd  # Count all lines
```

### Show line numbers:
```bash
grep -n "username" /etc/passwd
```

### Invert match (show all except matching):
```bash
grep -v "nologin" /etc/passwd  # Show users who can login
```

## A script to parse the /etc/passwd file and extract the user information, save it to a CSV file

A Python script to parse `/etc/passwd` and export to CSV:

See `Week04-Authentication-Password-Management/parse_passwd.py` for the implementation.

**Usage:**
```bash
python parse_passwd.py /etc/passwd output.csv
```

The script:
- Reads `/etc/passwd` (or a specified file)
- Parses all 7 fields from each user entry
- Extracts GECOS field components (full name, room, phone numbers, etc.)
- Exports data to CSV format with columns: username, uid, gid, full_name, room_number, work_phone, home_phone, other, home_directory, shell, password_field
- Provides summary statistics (total users, system users, regular users, users with login shells)

## Briefly explain commands related to the /etc/passwd file

| Command | Description | Usage Example |
|---------|-------------|---------------|
| `passwd` | Change user password. Updates `/etc/shadow`, not `/etc/passwd` directly. | `passwd` (change own password)<br>`passwd username` (admin changes user's password) |
| `su` | Switch user or become superuser. Uses user info from `/etc/passwd` to switch context. | `su - username` (switch to user)<br>`su -` or `su root` (become root) |
| `adduser` | High-level user addition script (Debian/Ubuntu). More interactive, user-friendly wrapper around `useradd`. | `adduser username` (interactive user creation) |
| `useradd` | Low-level command to add new user account. Directly modifies `/etc/passwd`, `/etc/shadow`, `/etc/group`. | `useradd -m -s /bin/bash username` (create user with home dir and shell) |
| `userdel` | Delete a user account. Removes entry from `/etc/passwd` and related files. | `userdel username` (delete user)<br>`userdel -r username` (delete user and home directory) |
| `usermod` | Modify existing user account properties. Updates `/etc/passwd` entries. | `usermod -s /bin/zsh username` (change shell)<br>`usermod -d /new/home username` (change home directory)<br>`usermod -g newgroup username` (change primary group) |
| `vipw` | Safely edit `/etc/passwd` file using the default editor. Provides file locking to prevent corruption. | `vipw` (edit passwd)<br>`vipw -s` (edit shadow file) |
| `chpasswd` | Batch password change utility. Reads username:password pairs and updates `/etc/shadow`. | `echo "user:newpass" \| chpasswd` (change password via stdin) |
| `id` | Display user and group IDs. Reads from `/etc/passwd` and `/etc/group`. | `id` (current user)<br>`id username` (specific user) |
| `whoami` | Display current username. Reads from `/etc/passwd`. | `whoami` |
| `getent` | Get entries from Name Service Switch databases including `/etc/passwd`. | `getent passwd` (all entries)<br>`getent passwd username` (specific user) |
| `pwck` | Verify integrity of `/etc/passwd` and `/etc/shadow` files. Checks for format errors. | `pwck` (check passwd)<br>`pwck -r` (read-only mode) |
| `chage` | Change user password aging information. Modifies `/etc/shadow` expiration fields. | `chage -l username` (list aging info)<br>`chage -M 90 username` (set max days) |

## Briefly explain /etc/shadow file

The `/etc/shadow` file stores encrypted password hashes and password aging information. It was introduced to address security concerns with `/etc/passwd`, which must be world-readable.

**Key features:**
- **Restricted access**: Readable only by root (permissions: `640` or `400`)
- **Password hashes**: Stores encrypted passwords using algorithms like SHA-512, bcrypt, or Argon2
- **Password aging**: Contains fields for password expiration, minimum days, maximum days, warning period, and account expiration
- **Locked accounts**: Accounts can be locked by placing `!` or `*` before the password hash

**Structure:** Each line contains colon-separated fields:
```
username:encrypted_password:last_password_change:min_days:max_days:warn_days:inactive_days:expiration_date:reserved
```

**Security benefit:** By separating password hashes from user information, `/etc/passwd` can remain world-readable (needed by many utilities), while sensitive password data is protected in `/etc/shadow`.

## Briefly explain /etc/group file

The `/etc/group` file defines groups on the system. Groups allow multiple users to share file permissions and other resources.

**Structure:** Each line contains colon-separated fields:
```
group_name:password:GID:user_list
```

**Fields:**
1. **Group name**: The name of the group
2. **Password**: Usually `x` (stored in `/etc/gshadow`), or empty if no password
3. **GID**: Group ID number
4. **User list**: Comma-separated list of users who are members of the group (not including the primary group members)

**Example entries:**
```
root:x:0:
sudo:x:27:admin,johndoe
users:x:100:
adm:x:4:syslog
www-data:x:33:
docker:x:999:johndoe,janedoe
```

**Relationship to /etc/passwd:** The GID field in `/etc/passwd` specifies the user's primary group, which is automatically a member of the group in `/etc/group`. Additional group memberships are listed in the user_list field of `/etc/group`.

## Briefly explain the difference between /etc/passwd and /etc/shadow under different OSes

| OS | /etc/passwd | /etc/shadow | Notes |
|----|-------------|-------------|-------|
| **GNU/Linux** | Contains: username, `x`, UID, GID, GECOS, home, shell<br>Password field shows `x` indicating shadow file usage<br>World-readable (644) | Contains: encrypted passwords, aging info<br>Root-only readable (400/640)<br>Standard shadow password system | Most Linux distributions use shadow passwords by default since the 1990s |
| **macOS** | Contains: username, UID, GID, GECOS, home, shell<br>No password field (password always in shadow/hash elsewhere)<br>World-readable | Uses `/var/db/dslocal/nodes/Default/users/` (plist files) instead of traditional `/etc/shadow`<br>OpenDirectory/NetInfo replaces traditional Unix files | Modern macOS doesn't use `/etc/shadow`. Uses Directory Services (LDAP-like) for authentication |
| **IBM AIX** | Traditional format: username, password hash or `!`, UID, GID, GECOS, home, shell<br>World-readable | `/etc/security/passwd` (not `/etc/shadow`)<br>More complex structure with additional security attributes | AIX uses `/etc/security/passwd` with extended attributes. May still have password hash in `/etc/passwd` if shadow not configured |
| **Oracle Solaris** | Contains: username, `x`, UID, GID, GECOS, home, shell<br>Password field shows `x`<br>World-readable (444) | Standard `/etc/shadow` format<br>Root-only readable (400)<br>Standard shadow password system | Traditional Unix-style, similar to Linux |
| **FreeBSD** | Contains: username, `*`, UID, GID, GECOS, home, shell<br>Password field shows `*` (always uses master.passwd)<br>World-readable (644) | Uses `/etc/master.passwd` (not `/etc/shadow`)<br>Contains passwords and user database<br>Protected file (600), generates `/etc/passwd` from it via `pwd_mkdb` | FreeBSD uses `master.passwd` instead of `shadow`. `/etc/passwd` is generated from `master.passwd` and should not be edited directly |

**Key differences:**
- **Linux/Solaris**: Standard `/etc/shadow` implementation
- **macOS**: No `/etc/shadow`; uses Directory Services
- **AIX**: Uses `/etc/security/passwd` with extended attributes
- **FreeBSD**: Uses `/etc/master.passwd`; `/etc/passwd` is a generated file

## Conclusion

The `/etc/passwd` file is a cornerstone of Unix-like system administration. It serves as the central database for user account information, enabling the operating system to map usernames to UIDs, locate home directories, and determine login shells. Understanding its structure, fields, and relationship with related files like `/etc/shadow` and `/etc/group` is essential for:

- **System administration**: Managing user accounts, permissions, and access
- **Security auditing**: Identifying accounts, checking for unauthorized access, understanding user privileges
- **Troubleshooting**: Diagnosing login issues, permission problems, and account-related errors
- **Automation**: Parsing and processing user data for scripts and system management tools

Modern Unix-like systems have evolved beyond storing passwords directly in `/etc/passwd`, using shadow password systems or directory services for enhanced security. However, `/etc/passwd` remains essential for storing non-sensitive user account metadata that must be accessible to various system utilities.

Mastery of `/etc/passwd` and related commands enables effective user account management, security hardening, and system administration in Unix-like environments.

