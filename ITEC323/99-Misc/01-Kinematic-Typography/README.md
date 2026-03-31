# Kinematic Typography

## Overview
This project is a lightweight interactive typography experiment. A dragon-like cursor moves through a responsive text grid and pushes words aside in real time, turning static reading into a kinetic visual experience.

## Tech Stack Used
- HTML5
- CSS3
- JavaScript
- HTML5 Canvas API

## What It Demonstrates
- Responsive `3 x 3` text composition for desktop, tablet, and mobile
- Inverse-kinematics style cursor motion
- Physics-inspired word displacement based on proximity
- Organic dragon rendering using overlapping circles on canvas

## How to Run
Open [kinematic-typography.html](/Users/vuhung/Desktop/kinematic-typography/kinematic-typography.html) in any modern web browser.

## Record an Animated WebP
1. Install dependencies with `npm install`
2. Install Playwright's Chromium browser with `npx playwright install chromium`
3. Run `npm run record`

The script opens `kinematic-typography.html` at `600x800`, moves the mouse randomly for 5 seconds, saves a `.webm` recording, and converts it to `artifacts/kinematic-typography.webp`.

## Conclusion
This project shows how simple web technologies can combine motion, typography, maths, and visual storytelling into a compact interactive art piece.
