SpamAssassin is a widely used, open-source email filter that employs a sophisticated, multi-layered approach to identify and score spam. It doesn't rely on a single algorithm but rather a combination of techniques, each contributing to an overall "spam score" for an incoming email.

Here are the main algorithms and techniques used by SpamAssassin:

1.  **Heuristic Rules (Rule-based Scoring):**
    * This is a cornerstone of SpamAssassin. It uses a vast, constantly updated set of **rules** (often written as regular expressions) to analyze various parts of an email.
    * **Mail Headers:** Checks for suspicious or malformed headers, unusual routing paths, forged sender addresses (e.g., "From" address doesn't match the sending domain), and compliance with email standards (SPF, DKIM).
    * **Message Body Content:** Scans for common spam phrases ("click here," "free money," "Viagra"), excessive punctuation (e.g., "!!!!"), all-caps text, unusual character sets, and common spam clichés.
    * **HTML Formatting:** Looks for sloppy or suspicious HTML code, unbalanced tags, overly large font sizes, flashy colors, unbalanced image-to-text ratios, and broken or excessive links.
    * **Each rule has a score (positive or negative) associated with it.** If an email triggers a rule, that score is added to its total. Positive scores indicate spam characteristics, while negative scores indicate legitimate characteristics (e.g., being on a whitelist).

2.  **Bayesian Filtering:**
    * SpamAssassin incorporates a **Bayesian filter**, which is a statistical method that learns from a user's (or administrator's) specific email patterns.
    * **How it works:** Users "train" the filter by feeding it examples of known spam ("ham") and known legitimate emails ("ham"). The filter then calculates the probability that a given word or phrase will appear in spam versus legitimate email.
    * **Learning:** Over time, as more emails are classified (either manually by the user or automatically by SpamAssassin based on a high score), the Bayesian filter refines its probabilities, becoming more accurate and personalized to the user's email habits.

3.  **DNS Blocklists (RBLs - Realtime Blackhole Lists / DNSBLs):**
    * SpamAssassin queries various online databases (RBLs/DNSBLs) that list IP addresses or domains known to send spam.
    * If the sending IP address or domain of an incoming email is found on one of these blacklists, it adds points to the spam score.
    * Conversely, it can also use whitelists (DNSWLs) to reduce the score for known good senders.

4.  **Collaborative Filtering Databases:**
    * SpamAssassin integrates with distributed filtering networks like **Vipul's Razor** or **Distributed Checksum Clearinghouse (DCC)**.
    * **How it works:** When a user reports a spam email, a cryptographic hash (checksum) of that email's content is sent to a central database. Other SpamAssassin installations can then query this database. If an incoming email matches a hash of a known spam message, it's highly likely to be spam, and points are added. This allows for rapid identification of widespread spam campaigns.

5.  **Sender Policy Framework (SPF) and DomainKeys Identified Mail (DKIM) Checks:**
    * SpamAssassin verifies email authentication protocols like SPF and DKIM.
    * **SPF:** Checks if the sending server's IP address is authorized to send email on behalf of the domain in the "From" address.
    * **DKIM:** Verifies that the email has not been tampered with in transit and that it genuinely originated from the claimed domain using cryptographic signatures.
    * Failure of these checks significantly increases the spam score, as it indicates potential spoofing or phishing attempts.

**The Scoring System:**

Each of these tests contributes a score (positive or negative) to the email. SpamAssassin sums up all these individual scores to get a **total spam score**. The mail server administrator sets a **threshold score** (e.g., 5.0 by default). If an email's total score meets or exceeds this threshold, it is classified as spam and can be marked, moved to a spam folder, or even rejected/deleted.

This modular and layered approach makes SpamAssassin highly adaptable and effective against evolving spam techniques.