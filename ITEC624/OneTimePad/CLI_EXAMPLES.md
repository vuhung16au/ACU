# One-Time Pad CLI Examples

This document provides examples of how to use the new command-line interface for the One-Time Pad cipher.

## Available Options

```bash
python3 src/oneTimepad.py --help
```

### 1. Encrypt with Generated Key (`-e` or `--encrypt`)

Encrypts a message using an automatically generated random key:

```bash
python3 src/oneTimepad.py -e
# Enter message when prompted
```

**Example:**
```
$ python3 src/oneTimepad.py -e
Enter message to encrypt: HELLO WORLD
Original message: 'HELLO WORLD'
Prepared message: 'HELLOWORLD'
Generated key: 'DKNQFOQPQP'
Ciphertext: 'KOYBTKEGBS'
```

### 2. Encrypt with Custom Key (`--encrypt-custom`)

Encrypts a message using a user-provided key:

```bash
python3 src/oneTimepad.py --encrypt-custom
# Enter message and key when prompted
```

**Example:**
```
$ python3 src/oneTimepad.py --encrypt-custom
Enter message to encrypt: HELLO
Enter encryption key: WORLD
Original message: 'HELLO'
Prepared message: 'HELLO'
Custom key: 'WORLD'
Ciphertext: 'DSGBT'
```

### 3. Decrypt Message (`-d` or `--decrypt`)

Decrypts a ciphertext using the provided key:

```bash
python3 src/oneTimepad.py -d
# Enter ciphertext and key when prompted
```

**Example:**
```
$ python3 src/oneTimepad.py -d
Enter ciphertext to decrypt: DSGBT
Enter decryption key: WORLD
Ciphertext: 'DSGBT'
Key: 'WORLD'
Decrypted plaintext: 'HELLO'
```

### 4. Demo Mode (`--demo`)

Runs the interactive demonstration and tutorial:

```bash
python3 src/oneTimepad.py --demo
```

## Shell Script Testing

The `oneTimepad.sh` script has been updated with new testing options:

```bash
# Test all CLI features
./oneTimepad.sh -c

# Run unit tests only
./oneTimepad.sh -t

# Interactive testing (includes both legacy and CLI options)
./oneTimepad.sh -i

# Run demo
./oneTimepad.sh -d

# Show help
./oneTimepad.sh -h
```

## Important Notes

1. **Key Requirements:**
   - Keys must contain only uppercase letters A-Z
   - Keys must be at least as long as the prepared message
   - Keys should be truly random and used only once

2. **Message Preparation:**
   - Input messages are automatically converted to uppercase
   - Only letters A-Z are retained (numbers, punctuation, and spaces are removed)
   - Example: "Hello, World!" becomes "HELLOWORLD"

3. **Security Considerations:**
   - Never reuse keys
   - Keep keys completely secret
   - Use truly random keys for maximum security
   - Key length should equal message length

## Complete Example Workflow

```bash
# 1. Encrypt a message
$ python3 src/oneTimepad.py -e
Enter message to encrypt: SECRET MESSAGE
Original message: 'SECRET MESSAGE'
Prepared message: 'SECRETMESSAGE'
Generated key: 'RANDOMKEYFORTE'
Ciphertext: 'JZGDRLZBJDMQZ'

# 2. Decrypt the message (using the same key)
$ python3 src/oneTimepad.py -d
Enter ciphertext to decrypt: JZGDRLZBJDMQZ
Enter decryption key: RANDOMKEYFORTE
Ciphertext: 'JZGDRLZBJDMQZ'
Key: 'RANDOMKEYFORTE'
Decrypted plaintext: 'SECRETMESSAGE'
```

## Error Handling

The CLI provides helpful error messages for common issues:

- Empty messages or keys
- Keys that are too short
- Invalid characters in keys or ciphertext
- General encryption/decryption errors

```bash
$ python3 src/oneTimepad.py --encrypt-custom
Enter message to encrypt: HELLO
Enter encryption key: ABC
Error: Key length (3) must be at least as long as prepared text (5)
```
