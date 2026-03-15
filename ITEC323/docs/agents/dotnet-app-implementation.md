# .NET App Implementation Guidelines for AI Agents

This document provides short, practical guidance for AI agents building .NET apps in the ITEC323 repository.

## Overview

Use these guidelines when implementing beginner-friendly .NET applications, especially web, UI, and cross-platform projects.

## Core Skills

### 1. ASP.NET Core Razor Pages

- Prefer **Razor Pages** for beginner web apps unless the project clearly needs a different pattern
- Keep page models small and focused on one page or task
- Put validation close to the form model so students can see the full flow
- Use clear page names such as `Create`, `Edit`, `Details`, and `Delete`
- Explain how the `.cshtml` page and `.cshtml.cs` page model work together

### 2. Interaction Design, UI, and UX

- Design for clarity before decoration
- Keep navigation predictable and labels easy to understand
- Make forms short, readable, and grouped by task
- Always show useful feedback for loading, success, and errors
- Avoid crowded screens; leave space between sections and controls

### 3. Responsive Design

- Build layouts that work well on phone, tablet, and desktop screens
- For **Razor** and **Blazor**, start with simple stacks, grids, and flexible widths
- For **.NET MAUI**, use responsive layouts such as `Grid`, `VerticalStackLayout`, and `HorizontalStackLayout`
- Test common breakpoints and make sure text stays readable
- Do not hide critical actions on smaller screens

### 4. Security

- Validate all user input on both client and server when applicable
- Encode output in web apps to reduce **cross-site scripting (XSS)** risk
- Use parameterized queries or Entity Framework to reduce **SQL injection** risk
- Check authorization before showing data or allowing actions
- Do not trust API input just because it comes from your own frontend
- Keep secrets out of code and configuration files checked into source control

## Short Policies

- **Teach the pattern**: Choose approaches students can read and repeat
- **One screen, one purpose**: Keep pages and views focused
- **Mobile-ready by default**: Assume the UI will be used on smaller screens
- **Secure by default**: Treat validation, encoding, and authorization as standard work
- **Explain the why**: Add short comments or docs when a design or security choice teaches an important concept

## When in Doubt

1. Start with the simplest working UI
2. Prefer Razor Pages for beginner web apps
3. Check whether the layout still works on a small screen
4. Check whether user input is validated, encoded, and safely stored
5. Refer to the main [AGENTS.md](../../AGENTS.md) for repository-wide guidance
