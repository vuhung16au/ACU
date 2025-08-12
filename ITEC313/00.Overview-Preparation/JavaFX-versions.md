# JavaFX LTS versions comparison (17, 21, 25)

A quick side‑by‑side of the requested LTS releases. Note: As of 12 Aug 2025, JavaFX 25 has not yet reached GA and is available as Early Access (EA).

## Summary table

| Release | GA Date | Latest version (as of Aug 2025) | Minimum JDK required | Long Term Support | Support Status (public updates) |
|---|---|---:|---:|---|---|
| JavaFX 17 | 7 Sep 2021 | 17.0.16 (Jul 2025) | 11 | LTS | Until Oct 2026 (then extended/custom via Gluon) |
| JavaFX 21 | 19 Sep 2023 | 21.0.8 (Jul 2025) | 17 | LTS | Active LTS; maintenance releases planned through at least Jan 2027 (then extended/custom) |
| JavaFX 25 | Sep 2025 (planned) | Early Access | 23 | LTS | Not GA yet; EA builds only (no support). LTS after GA via Gluon |

Sources: Gluon JavaFX Roadmap and official release notes (see references).

## Key features and notable changes

 
### JavaFX 17 (LTS)
 
- Platform/runtime
  - Baseline LTS after JavaFX 11; minimum JDK 11.
  - Multiple WebKit upgrades; media, graphics, accessibility improvements across update releases.
- Security/behavioral changes
  - FXML: JavaScript script engine disabled by default (17.0.6). Can be re‑enabled with `-Djavafx.allowjs=true`. (see: <https://gluonhq.com/products/javafx/openjfx-17-release-notes/>)
- Media/Web/Toolkit highlights
  - Media: support for libavcodec 59.
  - Web: WebKit updates (615.x series); numerous rendering and API fixes; security hardening.
  - Accessibility: multiple Narrator/Screen Reader fixes on Windows; control selection fixes.
- Notable new features
  - LTS baseline with ongoing 17.0.x updates; stability, security, and compatibility improvements aggregated over releases. (see: <https://gluonhq.com/products/javafx/openjfx-17-release-notes/>)

 
### JavaFX 21 (LTS)
 
- Platform/runtime requirements
  - Requires macOS 11+ on Mac; older macOS versions are not supported. (see: <https://gluonhq.com/products/javafx/openjfx-21-release-notes/>)
  - Requires GTK 3.8+ on Linux (GTK2 removed). (see: <https://gluonhq.com/products/javafx/openjfx-21-release-notes/>)
  - Minimum JDK 17.
- Libraries and components
  - Web: WebKit 616.1 at GA with continued updates up to 620.1; many web rendering and input fixes.
  - Media: updates including libavcodec 60 support; GStreamer and Glib upgrades.
- Other
  - Ongoing 21.0.x maintenance updates through 2025; planned through Jan 2027 per release notes schedule. (see: <https://gluonhq.com/products/javafx/openjfx-21-release-notes/>)
- Notable new features
  - Hard platform baselines (macOS 11+, GTK3) improving consistency; WebView engine refreshes; media stack modernizations (e.g., libavcodec 60).

 
### JavaFX 25 (LTS, planned)
 
- Status: Early Access (EA) as of Aug 2025; GA targeted for Sep 2025. (roadmap: <https://gluonhq.com/products/javafx/>)
- Minimum JDK: 23. (roadmap: <https://gluonhq.com/products/javafx/>)
- Latest: EA builds only; details subject to change. EA features may not appear in GA and are unsupported. (roadmap disclaimers)
- Notable new features
  - To be confirmed at GA; follow the roadmap and release notes once published.

 
## Major changes by release (selected)
 
- 17: FXML JavaScript engine disabled by default; WebKit and media updates; broad fixes in controls, accessibility, and graphics.
- 21: macOS 11+ required; GTK2 support removed (GTK3 required); continued WebKit and media stack updates, including libavcodec 60.
- 25: Planned LTS on JDK 23 with details to be finalized at GA.

 
## References

- Gluon JavaFX Roadmap (versions, GA dates, minimum JDK, LTS status): https://gluonhq.com/products/javafx/
- OpenJFX 17 Release Notes (all 17.0.x updates and highlights): https://gluonhq.com/products/javafx/openjfx-17-release-notes/
- OpenJFX 21 Release Notes (GA notes, platform requirements, and 21.0.x updates): https://gluonhq.com/products/javafx/openjfx-21-release-notes/
- JavaFX overview (Wikipedia): https://en.wikipedia.org/wiki/JavaFX
- Documentation hub: https://fxdocs.github.io/docs/html5/

Notes
- “Support Status” reflects Gluon’s public LTS information; extended/custom support is available upon request.
- EA builds are not supported and may lack security fixes; functionality is subject to change at GA.
