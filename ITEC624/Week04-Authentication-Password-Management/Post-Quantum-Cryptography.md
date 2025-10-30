# Post-Quantum Cryptography (PQC)

## Introduction

In the rapidly evolving landscape of cybersecurity, a new threat looms on the horizon: quantum computers. While these powerful machines promise revolutionary advances in fields like medicine, materials science, and artificial intelligence, they also pose a significant risk to the cryptographic systems that protect our digital world today. Post-Quantum Cryptography (PQC) represents our proactive response to this emerging challenge, developing cryptographic algorithms that can withstand attacks from both classical and quantum computers.

This document provides an overview of Post-Quantum Cryptography, explaining why it matters and what steps organizations and security professionals need to take to prepare for the quantum era.

## What is Post-Quantum Cryptography (PQC)?

**Post-Quantum Cryptography (PQC)** refers to cryptographic algorithms specifically designed to remain secure against attacks from quantum computers. These algorithms are also known as **quantum-resistant algorithms** because they can withstand the enhanced computational capabilities that quantum computers bring.

Unlike quantum cryptography (which uses quantum mechanical properties for secure communication), PQC algorithms run on classical computers but are mathematically structured to resist quantum attacks. This makes them practical for deployment in our existing infrastructure while providing protection against future quantum threats.

### Key Characteristics of PQC Algorithms

- **Quantum-resistant:** Designed to be secure against both classical and quantum computer attacks
- **Classical implementation:** Can be implemented and run on today's conventional computers
- **Mathematical foundation:** Based on hard mathematical problems that remain difficult even for quantum computers (e.g., lattice problems, hash functions, multivariate equations)
- **Backward compatible:** Can be integrated with existing systems through hybrid approaches

## Why Should We Care About PQC?

### The Quantum Threat is Real

Quantum computers leverage quantum bits (qubits) and quantum phenomena like superposition and entanglement to perform certain calculations exponentially faster than classical computers. Two quantum algorithms pose the greatest threat to modern cryptography:

1. **Shor's Algorithm:** Can factor large numbers and solve discrete logarithm problems efficiently, breaking:
   - RSA (Rivest-Shamir-Adleman)
   - DSA (Digital Signature Algorithm)
   - ECC (Elliptic Curve Cryptography)
   - Diffie-Hellman key exchange

2. **Grover's Algorithm:** Provides quadratic speedup for searching unstructured databases, weakening:
   - Symmetric key algorithms (AES, 3DES)
   - Hash functions (SHA-256, SHA-3)

### Vulnerable Systems and Use Cases

The impact of quantum computers extends across virtually all digital security infrastructure:

#### Public Key Infrastructure (PKI)
RSA and ECC keys form the backbone of digital trust worldwide. If these can be cracked, certificates, code signing, and identity verification systems become compromised.

#### SSL/TLS Encryption
Web servers and secure communications rely heavily on RSA and ECC. A quantum adversary could decrypt all HTTPS traffic, exposing sensitive data like passwords, financial information, and personal communications.

#### Messaging Applications
Popular encrypted messaging apps like WhatsApp, Telegram, and Signal use ECDH (Elliptic Curve Diffie-Hellman) for key agreement. Quantum attacks could break the confidentiality of these communications.

#### Cryptocurrencies
- **Bitcoin:** Uses ECDSA (Elliptic Curve Digital Signature Algorithm) for transaction signatures
- **Ethereum:** Also relies on ECDSA for signing transactions
Both could be vulnerable to quantum attacks, potentially allowing attackers to forge signatures or steal funds.

#### Email and File Encryption
PGP/GPG encryption systems, which use combinations of RSA, IDEA, AES, and other algorithms, may eventually be compromised, especially for data encrypted with RSA key exchange.

#### The "Harvest Now, Decrypt Later" Attack

Perhaps the most concerning threat is the **"Harvest Now, Decrypt Later"** strategy, where adversaries:
1. Intercept and store encrypted data today
2. Wait for quantum computers to become available
3. Decrypt the stored data retroactively

This means that sensitive data encrypted today could be at risk tomorrow, even if quantum computers are years away from being practical. Data with long-term confidentiality requirements (government secrets, medical records, financial data) is particularly vulnerable.

### Why Act Now?

While experts estimate that large-scale quantum computers capable of breaking current encryption may be 10-15 years away, we need to start transitioning now for several critical reasons:

1. **Long development and deployment cycles:** Updating cryptographic infrastructure across the internet takes years or even decades
2. **Legacy systems:** Many systems will continue using current cryptography long after quantum computers arrive
3. **Regulatory compliance:** Organizations need time to meet evolving security standards
4. **The retroactive threat:** As mentioned above, attackers are already harvesting encrypted data for future decryption
5. **Risk mitigation:** Like climate change, the expert consensus is clear—quantum threats are coming, and proactive preparation is essential

## Mitigating Quantum Threats

Security professionals and organizations can take several concrete steps to prepare for the quantum era:

### 1. Adopt Quantum-Resistant Algorithms

- **Identify critical infrastructure:** Audit your systems to identify where public key cryptography is used
- **Experiment and pilot:** Begin testing NIST-approved PQC algorithms in non-production environments
- **Plan migration paths:** Develop roadmaps for transitioning from vulnerable algorithms to quantum-resistant ones
- **Prioritize high-value targets:** Focus first on systems with long-term security requirements

### 2. Implement Hybrid Cryptography

Hybrid cryptography combines quantum-resistant algorithms with traditional cryptographic methods, providing:
- **Defense in depth:** Security even if one algorithm is compromised
- **Smooth transition:** Backward compatibility with existing systems
- **Risk mitigation:** Protection against implementation flaws in new PQC algorithms

Example: Using both ECC and a lattice-based algorithm together for key exchange.

### 3. Increase Awareness and Continuous Learning

- **Share knowledge:** Educate teams, peers, and stakeholders about PQC
- **Monitor developments:** Stay updated with NIST's standardization process and research advances
- **Participate in communities:** Engage with security communities focusing on PQC implementation
- **Training programs:** Invest in training for development and security teams

### 4. Crypto Agility

Design systems with **crypto agility**—the ability to quickly swap out cryptographic algorithms without major architectural changes. This includes:
- Using abstraction layers for cryptographic operations
- Avoiding hard-coded algorithm choices
- Implementing flexible key management systems
- Planning for algorithm upgrades in system lifecycles

## NIST Post-Quantum Cryptography Standardization

The **National Institute of Standards and Technology (NIST)** has been leading global efforts to standardize quantum-resistant cryptographic algorithms since 2016. The process has included:

- Multiple evaluation rounds
- Public scrutiny and cryptanalysis by the global cryptography community
- Security, performance, and implementation analysis
- International conferences and workshops

### NIST-Approved PQC Standards (2024)

As of August 2024, NIST has finalized three Federal Information Processing Standards (FIPS):

#### FIPS 203: Module-Lattice-Based Key Encapsulation Mechanism (ML-KEM)
- **Based on:** CRYSTALS-Kyber algorithm
- **Purpose:** Secure key establishment
- **Security basis:** Module Learning With Errors (Module-LWE) problem
- **Use case:** Replacing RSA and ECDH for key exchange in TLS, VPNs, and other protocols

#### FIPS 204: Module-Lattice-Based Digital Signature Algorithm (ML-DSA)
- **Based on:** CRYSTALS-Dilithium algorithm
- **Purpose:** Digital signatures for authentication and integrity
- **Security basis:** Module Learning With Errors and Module Short Integer Solution problems
- **Use case:** Replacing RSA and ECDSA signatures in certificates, code signing, and document authentication

#### FIPS 205: Stateless Hash-Based Digital Signature Algorithm (SLH-DSA)
- **Based on:** SPHINCS+ algorithm
- **Purpose:** Digital signatures with conservative security assumptions
- **Security basis:** Hash functions (considered very secure against quantum attacks)
- **Use case:** High-security applications requiring conservative cryptographic assumptions, backup signature schemes

### Additional Candidates

NIST continues to evaluate additional algorithms for specific use cases and is standardizing more algorithms for:
- Different performance characteristics
- Diverse mathematical foundations (for security diversity)
- Specialized applications

## Best Practices for Organizations

1. **Conduct a cryptographic inventory:** Document all uses of cryptography in your systems
2. **Assess risk and prioritize:** Determine which systems need quantum-resistant protection first
3. **Test PQC implementations:** Run pilots with NIST-standardized algorithms
4. **Develop transition timelines:** Create realistic migration plans (5-10 year horizons)
5. **Implement hybrid solutions:** Where possible, use both classical and PQC algorithms
6. **Monitor performance impact:** PQC algorithms may have different performance profiles than current ones
7. **Stay informed:** Follow NIST updates and industry best practices

## Part 2 (Advanced): Types of PQC Algorithms

This section provides a deeper dive into the different categories of post-quantum cryptographic algorithms under evaluation and standardization by NIST. Understanding these algorithm types helps security professionals make informed decisions about which approaches best suit their specific security requirements and operational constraints.

### Overview of Algorithm Categories

NIST has categorized post-quantum cryptographic algorithms into several main types, each based on different mathematical foundations. The primary categories include:
- **Code-based cryptography**
- **Hash-based cryptography**
- **Lattice-based cryptography**
- Additional types under evaluation (isogeny-based, multivariate-based, MPC-in-the-Head)

### Code-Based Cryptography

#### Foundation and Principles

Code-based cryptography leverages the mathematical difficulty of decoding error-correcting codes without possessing a special key. The security relies on the computational hardness of the **syndrome decoding problem** for general linear codes.

#### Key Examples

**McEliece Cryptosystem:**
- One of the oldest post-quantum cryptosystems (proposed in 1978)
- Remains unbroken after decades of cryptanalysis
- Based on binary Goppa codes
- Highly trusted for long-term security

**Modern Code-Based Algorithms:**
- **BIKE (Bit Flipping Key Encapsulation):** Uses quasi-cyclic codes for more compact keys
- **HQC (Hamming Quasi-Cyclic):** Balances security with practical key sizes

#### Advantages

- **Fast encryption and signature verification:** Operations are computationally efficient
- **Long-standing security:** McEliece has withstood 40+ years of cryptanalysis
- **Well-understood mathematics:** Based on mature coding theory
- **Resistance to quantum attacks:** No known quantum algorithms significantly threaten properly parameterized code-based schemes

#### Disadvantages

- **Very large public key sizes:** McEliece keys can be several hundred kilobytes to megabytes
  - Traditional RSA-2048: ~256 bytes
  - McEliece equivalent security: ~100-500 KB or more
- **Limited adoption:** Large key sizes make them impractical for many applications
- **Storage and transmission overhead:** Not suitable for bandwidth-constrained environments

#### Use Cases

Best suited for:
- Scenarios where key exchange happens infrequently
- Systems with ample storage capacity
- High-security applications requiring conservative cryptographic choices
- Long-term archival encryption

### Hash-Based Cryptography

#### Foundation and Principles

Hash-based cryptography uses the security properties of cryptographic hash functions (like SHA-256 or SHA-3) as their foundation. These schemes are considered highly conservative because they rely on well-studied, widely trusted hash functions.

**Key Concept: Merkle Trees**
- A tree structure where each non-leaf node is a hash of its child nodes
- Enables efficient verification of many hash values
- Forms the backbone of most hash-based signature schemes

**One-Time Signatures:**
- **Lamport signatures:** Simple scheme where each signature can be used only once
- Each key pair can sign exactly one message
- Foundation for more advanced hash-based schemes

#### Stateful vs. Stateless Schemes

**Stateful Hash-Based Signatures:**

Schemes like **LMS (Leighton-Micali Signature)** and **XMSS (Extended Merkle Signature Scheme)** require maintaining state information about which keys have been used.

*Characteristics:*
- Must track key usage to prevent reuse
- Reusing a key can compromise security
- Smaller signatures compared to stateless schemes
- Operational complexity in key management

*Challenges:*
- State synchronization in distributed systems
- Backup and recovery complexity
- Risk of state loss leading to security vulnerabilities

**Stateless Hash-Based Signatures:**

**SPHINCS+** is the primary stateless hash-based signature scheme, providing significant operational advantages.

*Characteristics:*
- No state tracking required
- Can generate signatures without worrying about key reuse
- Larger signatures than stateful schemes
- Simpler deployment and management

#### Advantages

- **Conservative security:** Based on minimal cryptographic assumptions (hash function security)
- **Quantum resistance:** Hash functions remain secure against known quantum algorithms (Grover's algorithm only provides quadratic speedup)
- **Well-understood primitives:** Decades of analysis on hash functions
- **Predictable security:** Hash function security directly translates to signature security

#### Disadvantages

- **Large signatures (stateless):** SPHINCS+ signatures can be 8-40 KB
- **Operational complexity (stateful):** State management can be challenging
- **Performance trade-offs:** Verification can be slower than lattice-based alternatives
- **Limited to signatures:** Hash-based schemes don't provide key encapsulation

#### Algorithm Details

**LMS (Leighton-Micali Signature):**
- Stateful hash-based signature scheme
- Not from NIST PQC competition but approved for quantum-safe use
- Multiple parameter sets with varying Merkle tree heights
- Supports SHA-256 and SHAKE hash functions
- Standardized in RFC 8554

**XMSS (Extended Merkle Signature Scheme):**
- Stateful hash-based signature with extended features
- Recognized and approved by both NIST and CNSA (Commercial National Security Algorithm Suite)
- Flexible performance/security trade-offs through Merkle tree parameterization
- Standardized in RFC 8391 and NIST SP 800-208

**SPHINCS+:**
- Stateless hash-based signature algorithm (see Part 3 for detailed standardization info)
- Multiple parameter sets optimized for different priorities:
  - Smaller signatures (at cost of slower performance)
  - Faster performance (at cost of larger signatures)
- Supports multiple hash functions: SHA-2, SHAKE, Haraka

### Lattice-Based Cryptography

#### Foundation and Principles

Lattice-based cryptography relies on the computational hardness of certain mathematical problems in high-dimensional lattices. A lattice is a regular grid of points in multi-dimensional space.

**Key Hard Problems:**

1. **Shortest Vector Problem (SVP):** Finding the shortest non-zero vector in a lattice
2. **Learning With Errors (LWE):** Distinguishing noisy linear equations from random data
3. **Module Learning With Errors (Module-LWE):** A structured variant of LWE used in modern schemes

**Visualization:**
Imagine a 2D grid of points extending infinitely in all directions. In cryptography, we use hundreds or thousands of dimensions, making the problems exponentially harder.

#### Key Lattice-Based Algorithms

**ML-DSA (Module-Lattice-Based Digital Signature Algorithm):**
- Based on CRYSTALS-Dilithium
- Standardized as FIPS 204
- Used for digital signatures
- Three security levels available

**ML-KEM (Module-Lattice-Based Key Encapsulation Mechanism):**
- Based on CRYSTALS-Kyber
- Standardized as FIPS 203
- Used for secure key establishment
- Three parameter sets for different security levels

#### Advantages

- **Fast operations:** Efficient key generation, encryption, and signing
- **Reasonable key sizes:** Smaller than code-based, larger than traditional RSA/ECC but manageable
  - Typical ML-KEM public key: 800-1,600 bytes
  - Typical ML-DSA public key: 1,300-2,600 bytes
- **Versatile:** Supports both encryption and signatures
- **Strong security proofs:** Well-analyzed with security reductions to hard lattice problems
- **NIST's primary choice:** Both standardized algorithms are lattice-based

#### Disadvantages

- **Still larger than classical cryptography:** 
  - RSA-2048: ~256-byte keys
  - ECC-256: ~32-byte keys
  - Lattice-based: 800-2,600 byte keys
- **Relatively new:** Less cryptanalysis history compared to code-based or hash-based
- **Implementation sensitivity:** Side-channel attacks are a concern; constant-time implementations essential
- **Mathematical complexity:** Requires deep understanding for proper implementation

#### Performance Characteristics

Lattice-based algorithms generally offer:
- Fast encryption/signing operations
- Fast decryption/verification
- Acceptable key and ciphertext/signature sizes for most applications
- Suitable for real-time communications (TLS, VPNs)

### Additional Algorithm Types Under Evaluation

#### Isogeny-Based Cryptography

**Foundation:**
Uses the mathematical structure of isogenies between elliptic curves—special maps that preserve the group structure.

**Status:**
- Most candidates (including SIKE) have been withdrawn due to discovered vulnerabilities
- **SQIsign** still under review in NIST's additional signature schemes evaluation
- Research continues but adoption is limited

**Key Characteristics:**
- Very small key sizes (similar to ECC)
- Relatively slow computation
- Security concerns have emerged

#### Multivariate-Based Cryptography

**Foundation:**
Security based on the difficulty of solving systems of multivariate quadratic equations over finite fields.

**Status:**
- Most candidates like Rainbow have been withdrawn
- Vulnerabilities discovered in several proposals
- Large key sizes remain a challenge

**Challenges:**
- Repeated cryptanalytic breaks
- Difficulty achieving both security and efficiency
- Limited confidence in long-term security

#### MPC-in-the-Head (Multi-Party Computation in the Head)

**Foundation:**
Uses zero-knowledge proof principles where a prover demonstrates knowledge without revealing the secret itself.

**Key Characteristics:**
- Stateless signature schemes
- Compact public keys and signatures
- Novel approach combining MPC protocols with zero-knowledge proofs

**Status:**
- Part of NIST's "Additional Digital Signature Schemes" evaluation
- Promising for specialized applications
- Still under active research and standardization

### NIST's Diversification Strategy

NIST recognizes that **algorithm diversity** is crucial for long-term security. The standardization approach includes:

1. **Primary Standards:** Lattice-based (ML-KEM, ML-DSA) and hash-based (SLH-DSA) algorithms
2. **Additional Candidates:** Evaluating alternatives for:
   - Performance diversity
   - Mathematical diversity (protecting against future breaks)
   - Specialized use cases

3. **Continuous Evaluation:** Ongoing rounds for additional signature schemes and alternate approaches

**Rationale for Diversity:**
- If one mathematical approach is broken, alternatives remain secure
- Different applications have different performance/security requirements
- Future-proofing against unforeseen cryptanalytic advances

### Algorithm Selection Considerations

When choosing PQC algorithms for implementation, consider:

| Factor | Code-Based | Hash-Based | Lattice-Based |
|--------|------------|------------|---------------|
| **Key Size** | Very Large | Medium | Medium-Large |
| **Signature/Ciphertext Size** | Medium | Large (stateless) | Medium |
| **Performance** | Fast | Medium | Fast |
| **Security Confidence** | Very High | Very High | High |
| **Standardization** | Limited | SLH-DSA (FIPS 205) | ML-KEM (FIPS 203), ML-DSA (FIPS 204) |
| **Primary Use** | Key Encapsulation | Digital Signatures | Both |

### Summary

The diversity of PQC algorithm types reflects the complexity of achieving quantum resistance while maintaining practical performance. Each approach offers unique trade-offs:

- **Code-based:** Maximum security confidence but impractical key sizes
- **Hash-based:** Conservative choice for signatures with proven security
- **Lattice-based:** Best balance of performance, size, and security—NIST's primary choice
- **Emerging types:** Under evaluation for specialized needs and mathematical diversity

Understanding these algorithm types enables security professionals to make informed decisions about PQC adoption, balancing security requirements, performance constraints, and operational feasibility. As the quantum threat evolves and cryptanalysis advances, maintaining a portfolio of diverse cryptographic approaches ensures resilience against future challenges.

## Part 3 (Advanced): NIST-Approved Quantum-Resistant Algorithms

This section provides detailed specifications and characteristics of the nine key quantum-resistant algorithms that have been approved or are under final consideration by NIST. Understanding these algorithms in depth is essential for security architects and cryptography engineers planning PQC implementations.

### Overview of NIST-Approved Algorithms

As of 2024, NIST has identified **nine key algorithms** as quantum-resistant and suitable for standardization or deployment:

**Digital Signature Algorithms:**
1. Dilithium (basis for ML-DSA)
2. ML-DSA (FIPS 204)
3. SPHINCS+ (basis for SLH-DSA)
4. SLH-DSA (FIPS 205)
5. Falcon
6. LMS (Leighton-Micali Signature)
7. XMSS (Extended Merkle Signature Scheme)

**Key Encapsulation Mechanisms:**
8. Kyber (basis for ML-KEM)
9. ML-KEM (FIPS 203)

### Lattice-Based Algorithms

#### Dilithium

**Overview:**
Dilithium is a lattice-based digital signature scheme that provides the foundation for the standardized ML-DSA algorithm.

**Mathematical Foundation:**
- Based on **Module Learning With Errors (Module-LWE)** problem
- Uses **CRYSTALS (Cryptographic Suite for Algebraic Lattices)** framework
- Security reduction to hard lattice problems (Module-SIS and Module-LWE)

**Key Features:**
- **Compact keys and signatures:** Efficient for bandwidth-constrained environments
- **Fast signing and verification:** Suitable for real-time applications
- **No floating-point arithmetic:** Simplifies secure implementation
- **Strong security proofs:** Well-analyzed with formal security reductions

**Parameter Sets (Security Variants):**

| Variant | NIST Security Level | Public Key Size | Signature Size | Performance |
|---------|---------------------|-----------------|----------------|-------------|
| **Dilithium2** | Level 2 (≈AES-128) | ~1,312 bytes | ~2,420 bytes | Fastest |
| **Dilithium3** | Level 3 (≈AES-192) | ~1,952 bytes | ~3,293 bytes | Balanced |
| **Dilithium5** | Level 5 (≈AES-256) | ~2,592 bytes | ~4,595 bytes | Most Secure |

**Use Cases:**
- Code signing
- Software distribution
- Document signing
- Certificate authorities
- IoT device authentication (Dilithium2)
- High-security government applications (Dilithium5)

**Implementation Considerations:**
- Constant-time implementation essential to prevent timing attacks
- Moderate memory requirements
- Efficient on both desktop and embedded platforms

#### ML-DSA (FIPS 204)

**Overview:**
**ML-DSA (Module-Lattice-Based Digital Signature Algorithm)** is the standardized version of Dilithium, published as **FIPS 204** in August 2024.

**Relationship to Dilithium:**
- Derived directly from Dilithium with minor refinements
- Essentially the same algorithm with NIST-approved parameters
- Incorporates feedback from the standardization process

**Security Levels:**

| ML-DSA Variant | NIST Level | Target Security | Key Sizes |
|----------------|------------|-----------------|-----------|
| **ML-DSA-44** | 2 | 128-bit quantum security | PK: 1,312 bytes<br>Signature: 2,420 bytes |
| **ML-DSA-65** | 3 | 192-bit quantum security | PK: 1,952 bytes<br>Signature: 3,293 bytes |
| **ML-DSA-87** | 5 | 256-bit quantum security | PK: 2,592 bytes<br>Signature: 4,595 bytes |

**Standardization Status:**
- **Published:** August 13, 2024
- **FIPS Number:** 204
- **Status:** Approved for federal use
- **Adoption:** Being integrated into major cryptographic libraries

**Recommended Applications:**
- Replacement for RSA and ECDSA signatures
- TLS certificate signing
- Email encryption (S/MIME)
- VPN authentication
- Blockchain transaction signing

#### Kyber

**Overview:**
Kyber is a lattice-based **key encapsulation mechanism (KEM)** designed for secure key establishment.

**Mathematical Foundation:**
- Based on **Module Learning With Errors (Module-LWE)** problem
- Part of the CRYSTALS suite
- IND-CCA2 secure (indistinguishability under adaptive chosen-ciphertext attack)

**How KEM Works:**
1. **Key Generation:** Creates a public/private key pair
2. **Encapsulation:** Uses the public key to generate a shared secret and encapsulate it in a ciphertext
3. **Decapsulation:** Uses the private key to extract the shared secret from the ciphertext

**Parameter Sets:**

| Variant | NIST Security Level | Public Key | Ciphertext | Shared Secret |
|---------|---------------------|------------|------------|---------------|
| **Kyber-512** | Level 1 (≈AES-128) | 800 bytes | 768 bytes | 32 bytes |
| **Kyber-768** | Level 3 (≈AES-192) | 1,184 bytes | 1,088 bytes | 32 bytes |
| **Kyber-1024** | Level 5 (≈AES-256) | 1,568 bytes | 1,568 bytes | 32 bytes |

**Advantages:**
- **Excellent performance:** Fast key generation, encapsulation, and decapsulation
- **Balanced sizes:** Reasonable key and ciphertext sizes
- **Well-studied:** Extensive cryptanalysis over multiple NIST rounds
- **IND-CCA2 security:** Strongest standard security notion for KEMs

**Use Cases:**
- TLS/SSL key exchange (replacing ECDH)
- VPN tunnel establishment
- Secure messaging key agreement
- Hybrid key exchange with classical algorithms

#### ML-KEM (FIPS 203)

**Overview:**
**ML-KEM (Module-Lattice-Based Key Encapsulation Mechanism)** is the standardized version of Kyber, published as **FIPS 203** in August 2024.

**Relationship to Kyber:**
- Based on Kyber with standardization refinements
- Minor parameter adjustments and specification clarifications
- Maintains the security and performance characteristics of Kyber

**Security Modes:**

| ML-KEM Variant | NIST Level | Public Key Size | Ciphertext Size | Performance Profile |
|----------------|------------|-----------------|-----------------|---------------------|
| **ML-KEM-512** | 1 | 800 bytes | 768 bytes | Fastest, suitable for constrained environments |
| **ML-KEM-768** | 3 | 1,184 bytes | 1,088 bytes | Balanced security/performance |
| **ML-KEM-1024** | 5 | 1,568 bytes | 1,568 bytes | Highest security |

**Standardization Status:**
- **Published:** August 13, 2024
- **FIPS Number:** 203
- **Status:** Approved for federal use
- **Industry Adoption:** Rapid integration into protocols and libraries

**Deployment Recommendations:**
- **ML-KEM-768** recommended as default for most applications
- **ML-KEM-512** for resource-constrained IoT devices
- **ML-KEM-1024** for high-security government and financial applications

**Integration Examples:**
- **TLS 1.3:** Hybrid key exchange (ML-KEM + ECDH)
- **IPsec/IKEv2:** Post-quantum VPN establishment
- **Signal Protocol:** Post-quantum secure messaging
- **OpenSSH:** Quantum-resistant key exchange

#### Falcon

**Overview:**
Falcon is a **lattice-based signature scheme** using NTRU lattices, offering very compact signatures and fast signing operations.

**Mathematical Foundation:**
- Based on **NTRU lattice problems**
- Uses **Fast Fourier Transform (FFT)** for efficient operations
- Security relies on Short Integer Solution (SIS) problem over NTRU lattices

**Key Features:**
- **Very compact signatures:** Smallest among lattice-based schemes
- **Fast signing:** Efficient signature generation
- **Floating-point operations:** Uses floating-point arithmetic (implementation challenge)

**Security Levels:**

| Variant | NIST Security Level | Public Key | Signature Size | Notes |
|---------|---------------------|------------|----------------|-------|
| **Falcon-512** | Level 1 (≈AES-128) | 897 bytes | ~666 bytes | Compact, fast |
| **Falcon-1024** | Level 5 (≈AES-256) | 1,793 bytes | ~1,280 bytes | High security |

**Advantages:**
- **Smallest signatures:** Among lattice-based schemes
- **High performance:** Fast signing and verification
- **Compact keys:** Smaller than Dilithium/ML-DSA

**Challenges:**
- **Floating-point arithmetic:** Requires careful implementation to avoid side channels
- **Implementation complexity:** More difficult to implement securely than Dilithium
- **Limited deployment:** Slower adoption compared to ML-DSA

**Standardization Status:**
- **Status:** Still a finalist, not yet standardized in a FIPS document
- **Expected:** Likely to be standardized as an alternative to ML-DSA
- **Use case:** Specialized applications needing smallest possible signatures

**Recommended For:**
- Bandwidth-constrained networks
- IoT devices with limited storage
- Embedded systems requiring compact signatures
- Applications where floating-point hardware is available

### Hash-Based Signature Algorithms

#### SPHINCS+

**Overview:**
SPHINCS+ is a **stateless hash-based signature scheme** that provides conservative security assumptions based solely on the security of hash functions.

**Mathematical Foundation:**
- Built on **cryptographic hash functions** (no other mathematical assumptions)
- Uses few-time signature schemes (FORS) and hypertrees
- Security directly tied to hash function collision and preimage resistance

**Key Features:**
- **Stateless:** No need to track key usage
- **Conservative security:** Minimal cryptographic assumptions
- **Flexible parameterization:** Multiple configurations for different needs

**Supported Hash Functions:**
- **SHA-256** (SHA-2 family)
- **SHAKE256** (SHA-3 family)
- **Haraka** (specialized for short inputs)

**Parameter Sets:**

SPHINCS+ offers multiple parameter sets optimized for different priorities:

**Small Signature Variants:**
- Optimize for smallest signature size
- Slower signing and verification
- Example: SPHINCS+-SHA2-128s (~7.8 KB signatures)

**Fast Variants:**
- Optimize for performance
- Larger signatures
- Example: SPHINCS+-SHA2-128f (~17 KB signatures)

**Security Levels:**
- **128-bit security** (NIST Level 1)
- **192-bit security** (NIST Level 3)
- **256-bit security** (NIST Level 5)

**Trade-offs:**

| Priority | Signature Size | Signing Time | Verification Time |
|----------|----------------|--------------|-------------------|
| **Small (s)** | ~7-40 KB | Slower | Slower |
| **Fast (f)** | ~16-50 KB | Faster | Faster |

**Advantages:**
- **No state management:** Simplifies deployment
- **Conservative assumptions:** Only relies on hash functions
- **Long-term security confidence:** Hash functions well-studied
- **Quantum resistance:** Strong resistance to quantum attacks

**Disadvantages:**
- **Large signatures:** 8-50 KB depending on parameters
- **Performance:** Slower than lattice-based alternatives
- **Bandwidth intensive:** Not suitable for bandwidth-constrained applications

#### SLH-DSA (FIPS 205)

**Overview:**
**SLH-DSA (Stateless Hash-Based Digital Signature Algorithm)** is the standardized version of SPHINCS+, published as **FIPS 205** in August 2024.

**Relationship to SPHINCS+:**
- Derived from SPHINCS+ with standardization refinements
- Supports only **SHA-2** and **SHAKE** hash functions (Haraka removed)
- Highly specified parameter sets for consistent implementation

**Standardization Status:**
- **Published:** August 13, 2024
- **FIPS Number:** 205
- **Status:** Approved for federal use
- **Purpose:** Conservative alternative to lattice-based signatures

**Parameter Sets (FIPS 205):**

| Variant | Hash Function | Security Level | Public Key | Signature Size | Optimization |
|---------|---------------|----------------|------------|----------------|--------------|
| **SLH-DSA-SHA2-128s** | SHA-256 | 128-bit | 32 bytes | ~7.8 KB | Small |
| **SLH-DSA-SHA2-128f** | SHA-256 | 128-bit | 32 bytes | ~17 KB | Fast |
| **SLH-DSA-SHAKE-128s** | SHAKE256 | 128-bit | 32 bytes | ~7.8 KB | Small |
| **SLH-DSA-SHAKE-128f** | SHAKE256 | 128-bit | 32 bytes | ~17 KB | Fast |
| *Similar variants for 192-bit and 256-bit security* |

**Use Cases:**
- **High-assurance systems:** Where conservative cryptography is required
- **Long-term signatures:** Archival and legal documents
- **Backup signature scheme:** Redundancy in case lattice-based schemes are broken
- **Regulatory compliance:** Government and financial sectors requiring diverse algorithms

**Implementation Guidance:**
- Choose **"s" variants** for applications where bandwidth is limited
- Choose **"f" variants** for applications requiring faster signing/verification
- Use **SHA-2** for maximum compatibility
- Use **SHAKE** for more modern hash function base

**Recommended Deployment:**
- As a **secondary signature scheme** alongside ML-DSA
- In hybrid signature configurations
- For high-value, long-term data protection

#### LMS (Leighton-Micali Signature)

**Overview:**
LMS is a **stateful hash-based signature scheme** that predates the NIST PQC competition but has been approved for quantum-safe use.

**Mathematical Foundation:**
- Based on **Merkle tree** construction
- Uses one-time signatures (Lamport-Diffie or Winternitz)
- Security relies on hash function properties

**Key Characteristics:**
- **Stateful:** Must maintain and update state for each signature
- **Compact signatures:** Smaller than SPHINCS+ (typically 1-4 KB)
- **Efficient verification:** Fast verification process

**Parameter Options:**
- **Tree heights:** Determines number of signatures per key (2^h signatures)
  - Common values: h = 5, 10, 15, 20, 25
- **Hash functions:** SHA-256 or SHAKE256
- **Winternitz parameter:** Trades signature size vs. computation

**Advantages:**
- **Efficient:** Fast signing and verification
- **Compact:** Smaller signatures than stateless schemes
- **Conservative security:** Based on well-understood hash functions

**Challenges:**
- **State management:** Critical security requirement
  - Must track which signatures have been used
  - State loss can compromise security
  - Challenging in distributed systems
- **Limited signatures:** Fixed number of signatures per key (2^h)
- **Operational complexity:** Backup and recovery procedures needed

**Standardization:**
- **RFC 8554:** IETF standard
- **NIST approval:** Recognized for quantum-safe use
- **Not from NIST PQC competition** but approved independently

**Recommended Use Cases:**
- Code signing (limited number of releases)
- Firmware updates
- Certificate signing in controlled environments
- Applications where state management is feasible

**Not Recommended For:**
- High-volume signing operations
- Distributed systems without reliable state synchronization
- Scenarios where state loss is likely

#### XMSS (Extended Merkle Signature Scheme)

**Overview:**
XMSS is an **extended version of Merkle signature schemes** offering enhanced flexibility and security guarantees, also stateful like LMS.

**Mathematical Foundation:**
- Extended Merkle tree construction
- Multiple tree layers for increased signature capacity
- Security based on hash function properties

**Key Features:**
- **Flexible parameterization:** Various tree heights and hash functions
- **Forward security:** Past signatures remain secure even if current key is compromised
- **Standardized security proofs:** Formal security analysis

**Parameter Options:**
- **XMSS:** Single tree
- **XMSS^MT:** Multi-tree variant for more signatures
- **Tree heights:** Configurable for different signature capacities
- **Hash functions:** SHA-256, SHAKE256

**Signature Capacity:**
- XMSS: Up to 2^h signatures (e.g., h=20 = ~1 million signatures)
- XMSS^MT: Much larger capacity with multi-tree approach

**Advantages:**
- **Forward security:** Enhanced security property
- **Well-specified:** Clear parameter sets and security analysis
- **Dual recognition:** Approved by both **NIST** and **CNSA** (Commercial National Security Algorithm Suite)
- **Performance/security flexibility:** Tunable parameters

**Challenges:**
- **State management:** Same challenges as LMS
- **Complex implementation:** More sophisticated than LMS
- **Operational overhead:** State backup and synchronization

**Standardization:**
- **RFC 8391:** IETF standard for XMSS
- **NIST SP 800-208:** NIST guidance for stateful hash-based signatures
- **CNSA 2.0:** Approved for national security systems

**Use Cases:**
- Government and military applications
- High-security certificate authorities
- Long-term secure signing with state management capability
- Applications requiring forward security

**Comparison: LMS vs. XMSS**

| Feature | LMS | XMSS |
|---------|-----|------|
| **Complexity** | Simpler | More complex |
| **Flexibility** | Less flexible | More parameterization options |
| **Forward Security** | No | Yes |
| **Signature Capacity** | 2^h | 2^h (XMSS) or much higher (XMSS^MT) |
| **Standardization** | RFC 8554 | RFC 8391, NIST SP 800-208 |
| **Implementation** | Easier | More challenging |

### Algorithm Selection Matrix

The following matrix helps guide algorithm selection based on use case requirements:

#### For Digital Signatures

| Requirement | Primary Choice | Alternative | Backup/Conservative |
|-------------|----------------|-------------|---------------------|
| **General purpose** | ML-DSA | Falcon | SLH-DSA |
| **Smallest signatures** | Falcon | ML-DSA-44 | - |
| **Fastest performance** | ML-DSA | Falcon | - |
| **Conservative security** | SLH-DSA | LMS/XMSS | - |
| **IoT/embedded** | ML-DSA-44 | Falcon-512 | - |
| **High security** | ML-DSA-87 | SLH-DSA-256 | - |
| **Code signing (limited)** | LMS | XMSS | ML-DSA |
| **Long-term archival** | SLH-DSA | XMSS | - |

#### For Key Encapsulation

| Requirement | Choice | Alternative |
|-------------|--------|-------------|
| **General purpose** | ML-KEM-768 | - |
| **Constrained devices** | ML-KEM-512 | - |
| **High security** | ML-KEM-1024 | - |
| **TLS/VPN** | ML-KEM-768 | ML-KEM-1024 |

### Distinctive Features Summary

#### Parameterization
Every approved algorithm offers **multiple parameter sets** to accommodate different security requirements:
- **NIST Level 1:** ~128-bit quantum security (comparable to AES-128)
- **NIST Level 3:** ~192-bit quantum security (comparable to AES-192)
- **NIST Level 5:** ~256-bit quantum security (comparable to AES-256)

#### Stateful vs. Stateless

**Stateless (Easier Deployment):**
- SPHINCS+
- SLH-DSA
- All lattice-based schemes (ML-DSA, ML-KEM, Dilithium, Kyber, Falcon)

**Stateful (Requires Key Usage Tracking):**
- LMS
- XMSS

#### Standardization and Naming Conventions

**NIST Standardized Names:**
- **ML-KEM** (Module-Lattice-Based KEM) = Kyber
- **ML-DSA** (Module-Lattice-Based DSA) = Dilithium
- **SLH-DSA** (Stateless Hash-Based DSA) = SPHINCS+

The "ML-" prefix represents the **standardized NIST nomenclature** distinguishing the final FIPS standards from the original algorithm submissions.

### NIST Standardization Timeline

**2016:** NIST initiates PQC standardization project

**2017-2020:** Multiple evaluation rounds
- Round 1: 69 candidates
- Round 2: 26 candidates
- Round 3: 7 finalists + 8 alternates

**2022:** Initial algorithm selections announced
- Kyber (KEM)
- Dilithium (signature)
- SPHINCS+ (signature)
- Falcon (signature, continued evaluation)

**August 2024:** First three FIPS standards published
- **FIPS 203:** ML-KEM
- **FIPS 204:** ML-DSA
- **FIPS 205:** SLH-DSA

**Ongoing:** Additional signature scheme evaluation and alternate algorithm review

### Implementation Considerations

#### Performance Benchmarks (Approximate)

**Signing Performance (operations per second on modern CPU):**
- ML-DSA: ~5,000-10,000 signs/sec
- Falcon: ~7,000-15,000 signs/sec
- SLH-DSA: ~50-500 signs/sec (depending on parameters)

**Verification Performance:**
- ML-DSA: ~10,000-20,000 verifications/sec
- Falcon: ~10,000-15,000 verifications/sec
- SLH-DSA: ~2,000-5,000 verifications/sec

**Key Encapsulation Performance:**
- ML-KEM: ~10,000-20,000 encapsulations/sec
- ML-KEM: ~10,000-20,000 decapsulations/sec

#### Memory Requirements

**RAM Usage (typical):**
- ML-DSA: ~100-200 KB
- ML-KEM: ~50-100 KB
- Falcon: ~150-300 KB (due to FFT operations)
- SLH-DSA: ~50-150 KB

#### Side-Channel Protection

All algorithms require **constant-time implementations** to prevent timing attacks. Special considerations:
- **Lattice-based:** Relatively straightforward constant-time implementation
- **Falcon:** Complex due to floating-point operations
- **Hash-based:** Simpler, hash functions already constant-time

### Security Evaluation and Cryptanalysis

**Current Status (as of 2024):**
- **ML-KEM/ML-DSA:** Extensively analyzed, no significant weaknesses found
- **SLH-DSA:** Conservative, based on mature hash functions
- **Falcon:** Well-analyzed but more complex implementation
- **LMS/XMSS:** Long-standing, high confidence

**NIST's Approach:**
- Ongoing security evaluation
- Open cryptanalysis process
- Regular security reviews
- Collaboration with international cryptography community

### Migration and Deployment Guidance

**Hybrid Deployment (Recommended Initial Approach):**
1. **Phase 1:** Deploy hybrid schemes (PQC + classical)
   - Example: ML-KEM + ECDH for key exchange
   - Example: ML-DSA + RSA for signatures

2. **Phase 2:** Transition to PQC-only as confidence grows
   - Remove classical algorithms
   - Full quantum resistance

**Algorithm Diversity:**
Deploy multiple algorithms for defense in depth:
- Primary: ML-DSA for signatures
- Secondary: SLH-DSA as backup
- KEM: ML-KEM for key exchange

### Summary

The nine quantum-resistant algorithms represent the culmination of years of research, cryptanalysis, and standardization efforts:

**Coverage:**
- **Diverse mathematical foundations:** Lattice-based, hash-based, Merkle tree-based
- **Operational models:** Key encapsulation and digital signatures
- **Performance profiles:** From ultra-fast (lattice) to conservative (hash-based)
- **Security guarantees:** Multiple parameter sets for various threat models

**NIST Recommendations:**
- **Primary:** ML-KEM (key exchange), ML-DSA (signatures)
- **Conservative backup:** SLH-DSA (signatures)
- **Specialized:** Falcon (compact signatures), LMS/XMSS (stateful signing)

**Current State:**
- Active standardization and implementation underway
- Major vendors integrating into products
- Migration guidance being developed
- Continuous security evaluation ongoing

These algorithms constitute the **core toolset for protecting data and digital identities in the post-quantum world**, with NIST's formal recommendations enabling organizations to begin migration and implementation with confidence.

## Conclusion

Post-Quantum Cryptography represents a critical evolution in cybersecurity. While quantum computers capable of breaking current encryption may still be years away, the time to prepare is now. By understanding PQC, implementing quantum-resistant algorithms, and maintaining crypto agility, organizations can protect their data and systems against both present and future threats.

The transition to Post-Quantum Cryptography is not just a technical upgrade—it's a fundamental shift in how we think about long-term security. As security professionals, staying informed and proactive about PQC is essential to safeguarding the digital infrastructure we all depend on.

## References and Further Reading

1. **Post Quantum Cryptography (PQC) | Part-1: Introduction**  
   Comprehensive introduction to PQC concepts, threats, and mitigation strategies  
   [Source document referenced in issue]

2. **Post Quantum Cryptography (PQC) | Part-2: Types of Algorithms**  
   Detailed overview of PQC algorithm categories including code-based, hash-based, lattice-based, and emerging types  
   https://www.youtube.com/watch?v=mWmouyqipXw

3. **Post Quantum Cryptography (PQC) | Part-3: Approved Algorithms**  
   In-depth review of 9 NIST-approved quantum-resistant algorithms with specifications and use cases  
   https://www.youtube.com/watch?v=ni1x44mCgWE

4. **NIST Post-Quantum Cryptography Standardization**  
   Official NIST PQC project page with standards, documents, and updates  
   https://csrc.nist.gov/projects/post-quantum-cryptography

5. **NIST FIPS 203: Module-Lattice-Based Key Encapsulation Mechanism**  
   Official standard for ML-KEM (CRYSTALS-Kyber)  
   https://csrc.nist.gov/pubs/fips/203/final

6. **NIST FIPS 204: Module-Lattice-Based Digital Signature Standard**  
   Official standard for ML-DSA (CRYSTALS-Dilithium)  
   https://csrc.nist.gov/pubs/fips/204/final

7. **NIST FIPS 205: Stateless Hash-Based Digital Signature Standard**  
   Official standard for SLH-DSA (SPHINCS+)  
   https://csrc.nist.gov/pubs/fips/205/final

8. **RFC 8554: Leighton-Micali Hash-Based Signatures**  
   IETF standard for LMS stateful hash-based signatures  
   https://www.rfc-editor.org/rfc/rfc8554.html

9. **RFC 8391: XMSS: eXtended Merkle Signature Scheme**  
   IETF standard for XMSS stateful hash-based signatures  
   https://www.rfc-editor.org/rfc/rfc8391.html

10. **NIST SP 800-208: Recommendation for Stateful Hash-Based Signature Schemes**  
    NIST guidance for LMS and XMSS implementation  
    https://csrc.nist.gov/publications/detail/sp/800-208/final

11. **Cloud Security Alliance - Quantum-Safe Security Working Group**  
    Resources and guidance for implementing quantum-safe security  
    https://cloudsecurityalliance.org/research/working-groups/quantum-safe-security/

12. **Open Quantum Safe Project**  
    Open-source software for prototyping and experimenting with quantum-resistant cryptography  
    https://openquantumsafe.org/

13. **ETSI Quantum Safe Cryptography**  
    European standards and specifications for quantum-safe cryptography  
    https://www.etsi.org/technologies/quantum-safe-cryptography

## Key Terms Glossary

- **Quantum Computer:** A computer that uses quantum mechanics principles (superposition, entanglement) to perform calculations
- **Qubit:** Quantum bit; the basic unit of quantum information
- **Shor's Algorithm:** Quantum algorithm for factoring large integers efficiently
- **Grover's Algorithm:** Quantum algorithm for searching unsorted databases with quadratic speedup
- **Lattice-based Cryptography:** PQC approach based on the hardness of lattice problems
- **Hash-based Signatures:** Digital signature schemes based on hash functions
- **Hybrid Cryptography:** Using both classical and quantum-resistant algorithms together
- **Crypto Agility:** The ability to quickly change cryptographic algorithms in a system
- **ML-KEM:** Module-Lattice-Based Key Encapsulation Mechanism (CRYSTALS-Kyber)
- **ML-DSA:** Module-Lattice-Based Digital Signature Algorithm (CRYSTALS-Dilithium)
- **SLH-DSA:** Stateless Hash-Based Digital Signature Algorithm (SPHINCS+)
