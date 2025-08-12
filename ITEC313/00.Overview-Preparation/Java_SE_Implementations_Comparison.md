# Java SE implementations — versions and distributions (Aug 2025)

All distributions are built from the OpenJDK codebase. Differences are mainly in licensing, support, delivery cadence, and packaging.

## Quick matrix

| Implementation | Vendor / Maintainer | License | Typical versions offered (as of Aug 2025) | Primary focus | Support/EOL (indicative) |
|:--|:--|:--|:--|:--|:--|
| OpenJDK (project) | OpenJDK Community | GPLv2 + Classpath Exception | Current feature: 24; Next EA: 25 EA | Upstream reference implementation; source-first | Non‑LTS feature releases are superseded every 6 months; LTS update lines (jdk8u/11u/17u/21u) are community‑maintained; timelines are community‑driven. |
| Oracle JDK | Oracle | NFTC (JDK 24); OTN for newer JDK 17 updates; commercial subscription options | GA: 24; LTS: 21 (public), 17 (with subscription) | Certified builds, optional enterprise support | LTS roadmap: 21 Premier Support to Sep 2028, Extended to Sep 2031; 17 Premier to Sep 2026, Extended to Sep 2029; 8 Extended to Dec 2030; 25 (planned LTS) Premier 2030, Extended 2033. |
| Eclipse Adoptium Temurin | Eclipse Foundation (Adoptium WG) | GPLv2+CE | LTS: 8, 11, 17, 21; Current: 24 | High-quality community binaries, broad packaging | LTS supported at least: 8 to Nov 2026; 11 to Oct 2027; 17 to Oct 2027; 21 to Dec 2029. |
| Amazon Corretto | Amazon | GPLv2+CE | 8, 11, 17, 21, 24 | Free, production-ready, long-term support | LTS lines (8/11/17/21) maintained with quarterly security updates; support calendar published by AWS (current LTS lines active). |
| Azul Zulu | Azul | GPLv2+CE (with optional commercial support) | 8, 11, 17, 21, 24; 25 EA | Wide platform coverage; optional support/Prime performance | LTS with enterprise SLAs via Azul Platform Core; extended timelines published by Azul; current LTS lines active (8/11/17/21); 25 planned LTS. |
| Microsoft Build of OpenJDK | Microsoft | GPLv2+CE | LTS: 11, 17, 21; feature releases as available | Optimized for MS platforms/services | LTS builds supported; updates aligned with upstream and MS release notes (see Microsoft docs for lifecycle specifics). |
| Red Hat build of OpenJDK | Red Hat | GPLv2+CE | 8, 17, 21 (Windows/RHEL; containers). Mac via Temurin | RHEL/enterprise integration and support | 8 to Nov 30, 2026; 11 to Oct 31, 2024 (ELS to Oct 31, 2027); 17 to Oct 31, 2027; 21 to Dec 31, 2029. |

Notes:
- Release train: a new feature release every 6 months; LTS every 2 years (17, 21, 25...).
- All listed distributions are Java SE compatible (pass the TCK), unless otherwise noted by the vendor.

## Details by distribution

### OpenJDK (reference project)
 
- What: Upstream Java SE reference implementation since Java 7.
- Versions: GA 24; 25 early-access available via project sites.
- Who should use: Builders of downstream distributions; developers who prefer building from source or nightly/EA exploration via vendor binaries derived from OpenJDK.
- Ref: OpenJDK is the Java SE reference implementation; see Java SE article and vendor pages below.

### Oracle JDK
 
- Versions: JDK 24.0.2 is latest GA; JDK 21 is current LTS; JDK 17 LTS updates available for subscribers.
- License: NFTC for JDK 24 (free to use and redistribute in production while current); JDK 17+ updates from 17.0.13 use OTN with different terms; commercial support via Java SE Subscription.
- Platforms: Linux, macOS, Windows; script-friendly URLs provided.
- Best for: Enterprises needing certified builds, longer support windows, and Oracle tooling options.
- Ref: oracle.com/java/technologies/downloads

### Eclipse Adoptium Temurin
 
- Versions: LTS 8/11/17/21; current 24.
- Packaging: Direct downloads, Docker images, Debian/RPM/Alpine/SLES repos, Homebrew, SDKMAN, WinGet, REST API.
- Best for: Most users wanting free, stable, and widely packaged OpenJDK binaries.
- Ref: adoptium.net Temurin releases

### Amazon Corretto
 
- Versions: 8, 11, 17, 21, and 24 (feature); long-term support policy with performance and security updates.
- Platforms: Linux, Windows, macOS, Docker.
- Best for: AWS users and general production use at no cost.
- Ref: aws.amazon.com/corretto

### Azul Zulu
 
- Versions: 8, 11, 17, 21, 24; 25 EA also available.
- Offerings: Free builds (Zulu) and commercial support (Azul Platform Core). Azul Platform Prime adds advanced performance (Linux only) as a separate product.
- Platforms: Very broad coverage (including Alpine, many CPU architectures).
- Best for: Organizations needing wide platform/arch support and optional enterprise SLAs.
- Ref: azul.com/downloads

### Microsoft Build of OpenJDK
 
- Versions: LTS 11/17/21; Microsoft periodically publishes feature releases.
- Focus: Upstream contributions (e.g., Windows on Arm, Apple Silicon), tuned for Microsoft platforms and Azure.
- Platforms: macOS, Linux, Windows.
- Best for: Microsoft ecosystem users, Azure workloads, and CI/CD on MS infra.
- Ref: microsoft.com/openjdk

### Red Hat build of OpenJDK
 
- Versions: 8, 17, 21 with regular update bundles (e.g., Apr 2025 releases). Not provided for macOS; Red Hat recommends Temurin for mac.
- Platforms: RHEL and Windows installers; certified container images; guidance for other Linux variants.
- Best for: RHEL/OpenShift users wanting integrated support and certified containers.
- Ref: developers.redhat.com/products/openjdk/download

## Choosing a distribution
 
- If you want free, stable, and broad packaging: Temurin, Corretto, Zulu are excellent default choices.
- If you need vendor support and long maintenance windows: Oracle JDK (subscription), Red Hat build of OpenJDK, Azul Platform Core/Prime.
- If you run primarily on Azure/Windows: Microsoft Build of OpenJDK is a good fit.
- If you need unusual platforms/architectures: Azul Zulu typically has the widest coverage.

## References
 
 - Java SE overview (OpenJDK as reference implementation): [Wikipedia — Java SE](https://en.wikipedia.org/wiki/Java_Platform,_Standard_Edition)
 - Oracle JDK downloads and terms: [Oracle Java Downloads](https://www.oracle.com/nz/java/technologies/downloads/)
 - Amazon Corretto: [AWS Corretto](https://aws.amazon.com/corretto/)
 - Eclipse Adoptium Temurin: [Adoptium Temurin Releases](https://adoptium.net/en-GB/temurin/releases)
 - Azul Zulu downloads: [Azul Zulu Downloads](https://www.azul.com/downloads/?package=jdk#zulu)
 - Microsoft Build of OpenJDK: [Microsoft OpenJDK](https://www.microsoft.com/openjdk)
 - Red Hat build of OpenJDK: [Red Hat OpenJDK](https://developers.redhat.com/products/openjdk/download)
