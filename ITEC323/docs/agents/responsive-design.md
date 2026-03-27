# Responsive Design Guidelines for AI Agents

This document provides focused responsive design guidance for AI agents contributing UI work in the ITEC323 repository.

## Overview

Use these guidelines when building layouts for .NET MAUI, Razor, and Blazor so interfaces remain usable across phone, tablet, and desktop screen sizes.

## Core Responsive Design Principles

### 1. Start Mobile-Ready

- Assume the interface may be used on smaller screens
- Keep important content and actions visible without relying on large displays
- Do not hide critical actions on smaller screens

### 2. Use Flexible Layouts

- For Razor and Blazor, prefer simple stacks, grids, and flexible widths
- For .NET MAUI, use layouts such as `Grid`, `VerticalStackLayout`, and `HorizontalStackLayout`
- Let content reflow naturally instead of forcing fixed widths when possible

### 3. Protect Readability

- Make sure text remains readable at common screen sizes
- Leave enough spacing between controls for touch and mouse users
- Avoid crowded layouts that become difficult to scan on mobile devices

### 4. Test Common Breakpoints

- Check phone, tablet, and desktop layouts
- Confirm that headings, forms, buttons, and navigation still work at each size
- Make sure content order still makes sense when layouts stack vertically

## Short Policies

- Mobile-ready by default
- Flexible layouts over fixed layouts
- Readability on every screen
- Critical actions stay visible

## When in Doubt

1. Start with a simple vertical layout
2. Test the smallest likely screen first
3. Keep actions visible and text readable
4. Refer to the main [AGENTS.md](../AGENTS.md) for repository-wide guidance
