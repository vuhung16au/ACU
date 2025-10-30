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

## Conclusion

Post-Quantum Cryptography represents a critical evolution in cybersecurity. While quantum computers capable of breaking current encryption may still be years away, the time to prepare is now. By understanding PQC, implementing quantum-resistant algorithms, and maintaining crypto agility, organizations can protect their data and systems against both present and future threats.

The transition to Post-Quantum Cryptography is not just a technical upgrade—it's a fundamental shift in how we think about long-term security. As security professionals, staying informed and proactive about PQC is essential to safeguarding the digital infrastructure we all depend on.

## References and Further Reading

1. **Post Quantum Cryptography (PQC) | Part-1: Introduction**  
   Comprehensive introduction to PQC concepts, threats, and mitigation strategies  
   [Source document referenced in issue]

2. **NIST Post-Quantum Cryptography Standardization**  
   Official NIST PQC project page with standards, documents, and updates  
   https://csrc.nist.gov/projects/post-quantum-cryptography

3. **NIST FIPS 203: Module-Lattice-Based Key Encapsulation Mechanism**  
   Official standard for ML-KEM (CRYSTALS-Kyber)  
   https://csrc.nist.gov/pubs/fips/203/final

4. **NIST FIPS 204: Module-Lattice-Based Digital Signature Standard**  
   Official standard for ML-DSA (CRYSTALS-Dilithium)  
   https://csrc.nist.gov/pubs/fips/204/final

5. **NIST FIPS 205: Stateless Hash-Based Digital Signature Standard**  
   Official standard for SLH-DSA (SPHINCS+)  
   https://csrc.nist.gov/pubs/fips/205/final

6. **Cloud Security Alliance - Quantum-Safe Security Working Group**  
   Resources and guidance for implementing quantum-safe security  
   https://cloudsecurityalliance.org/research/working-groups/quantum-safe-security/

7. **Open Quantum Safe Project**  
   Open-source software for prototyping and experimenting with quantum-resistant cryptography  
   https://openquantumsafe.org/

8. **ETSI Quantum Safe Cryptography**  
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
