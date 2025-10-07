## Hashing: A Practical Guide for Students

### What is Hashing (Brief)
- A hash function maps input data of any size to a fixed-length output (the hash).
- Key properties: determinism, fixed-length output, avalanche effect (small input change -> big output change), preimage resistance, second-preimage resistance, collision resistance.

### Diagram: The Hashing Process
```
Input (message/file/password)
        |
        v
   +---------------------+
   |  Hash Function H()  |
   |  (e.g., SHA-256)    |
   +---------------------+
        |
        v
 Fixed-length Hash (digest)
```

### Cryptographic vs Non-Cryptographic Hashing
- Cryptographic hashing (security): designed to resist attacks (preimage/second-preimage/collisions).
  - Examples: SHA-256/512 (SHA-2), SHA-3, BLAKE2/3.
  - Uses: digital signatures, HMACs, blockchain, secure identifiers.
- Non-cryptographic hashing (performance/fingerprinting): fast, not secure against attacks.
  - Examples: CRC32, MurmurHash, CityHash, perceptual hashes (aHash/dHash/pHash), ssdeep (fuzzy hash).
  - Uses: checksums for corruption detection, hash tables, deduplication, media fingerprinting (Content ID), similarity/malware triage.

### Hashing Algorithms/Functions (At a Glance)
- Integrity/fingerprinting: MD5 (legacy), SHA-1 (legacy), SHA-256/512, SHA-3, BLAKE2/3.
- Password hashing (KDFs – slow/memory-hard on purpose): bcrypt, scrypt, Argon2id (recommended).
- Perceptual/similarity: aHash/dHash/pHash (images/video), ssdeep/sdhash (fuzzy file similarity).

### Hash Collisions
- Collision: two different inputs produce the same hash.
- Practical security note: MD5 and SHA-1 have known collision attacks; avoid for new security designs.
- Chosen-prefix collisions enable crafting two different valid documents with the same MD5/SHA-1 hash.

### Hash Security: Cracking Fast Hashes
- Fast cryptographic hashes (MD5/SHA-1/SHA-256) are vulnerable for password storage when unsalted: GPUs/ASICs try billions of guesses/second.
- Salts defeat rainbow tables; KDFs (bcrypt/scrypt/Argon2id) slow down attackers.
- Minimal rules for passwords:
  - Always use a unique random salt.
  - Use Argon2id (or bcrypt with cost ≥ 12) tuned to your hardware latency budget.
  - Never store plain or plain-hashed (SHA-256) passwords.

### How to Hash a Password (/etc/shadow)
- `/etc/shadow` format: `username:$id$salt$hash:...`
  - `$1$` MD5, `$5$` SHA-256, `$6$` SHA-512, `$y$`/`$argon2id$` on some systems (modern).
- Generate bcrypt (example, Python):
```python
import bcrypt
password = b"student-password"
salt = bcrypt.gensalt(rounds=12)
pwd_hash = bcrypt.hashpw(password, salt)
print(pwd_hash.decode())
```
- Verify:
```python
bcrypt.checkpw(password, pwd_hash)
```
- Linux example (OpenSSL, legacy, not for passwords):
```bash
# DO NOT use for password storage; demo only
echo -n 'secret' | openssl dgst -sha512
```

### How to Hash a File (MD5 for Integrity; Prefer SHA-256)
- MD5 can detect accidental corruption but is broken for security.
```bash
md5sum file.bin
sha256sum file.bin   # best practice for integrity checks
```

### How to Hash a String (SHA-1 demo; Prefer SHA-256)
- SHA-1 is deprecated for security; use SHA-256. Shown for learning only.
```bash
echo -n "hello" | sha1sum
echo -n "hello" | sha256sum  # recommended
```

### Fingerprints: YouTube Content ID (Non-crypto/perceptual)
- Content ID does not use simple cryptographic hashes.
- Uses robust audio/video fingerprints that survive re-encoding, trimming, noise.
- Concepts: perceptual hashing, locality-sensitive hashing, feature extraction.

### Hash and Bitcoin/Blockchain (Cryptographic)
- Bitcoin uses double SHA-256 on block headers; miners vary a nonce to satisfy difficulty.
- Merkle trees (SHA-256) aggregate transaction hashes; any change bubbles up to the root.
- Avalanche effect: flipping 1 bit in input radically changes the hash.

### Hash & Malware Detection
- Exact matching: vendors publish SHA-256 hashes for samples/releases; compare to verify.
- Fuzzy/similarity hashing (ssdeep/sdhash/pehash): find related variants even if not byte-identical.
- Caution: fuzzy hashes are not cryptographically secure; use for triage, not trust anchors.

### Hash & Digital Signatures (Cryptographic)
- We sign the hash of a message, not the whole message (efficiency + integrity binding).
- Choose collision-resistant hash (SHA-256/SHA-3). Avoid MD5/SHA-1.
- Authentication vs integrity:
  - HMAC (with a secret key) for authenticity + integrity in symmetric settings.
  - Digital signatures (RSA-PSS, ECDSA, Ed25519) for authenticity + non-repudiation.

### Best-Practice Checklist
- Passwords: Argon2id/bcrypt with unique salts; tune cost/memory.
- Integrity: prefer SHA-256; for authenticity, use signatures or HMAC, not bare hashes.
- Avoid MD5/SHA-1 in new security designs.
- Distinguish cryptographic vs perceptual/fuzzy hashing by purpose.


