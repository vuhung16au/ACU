# FRD-Copilot: Responsive Design Showcase

## Purpose

This folder is a future teaching module for responsive design in web and mobile-friendly web apps.

The goal is to show students how to build layouts and UI components that adapt well across:
- mobile phones
- tablets
- laptops
- desktop screens

This document is written for AI coding agents so the folder can be implemented later in a way that matches the rest of the repository.

Do not implement code from this document yet. Use it as a planning and implementation guide for future work.

---

## Target Stack

- **Platform**: .NET 10
- **Language**: C# 14
- **Primary App Style**: ASP.NET Core Razor Pages
- **Frontend Focus**: HTML5, CSS3, responsive layout techniques, light JavaScript only when helpful
- **Styling Approach**: mobile-first CSS
- **Build Tool**: `dotnet`
- **Primary Goal**: responsive web app design, not native mobile app development

---

## Folder Intent

`30-Responsive-Design` should be a showcase folder similar in spirit to the existing teaching folders.

It should:
- feel educational and beginner-friendly
- use short, practical examples
- progress from simple to more complete examples
- focus on responsive design for web apps
- align with the repository structure and tone already used in weeks 1-12

It should not:
- depend on heavy frontend frameworks unless there is a clear teaching reason
- introduce unnecessary architectural complexity
- mix too many topics into a single example

---

## Suggested Folder Structure

```text
30-Responsive-Design/
├── README.md
├── QUICKSTART.md
├── FRD.md
├── FRD-copilot.md
├── docs/
│   ├── Key-Takeaways.md
│   ├── Breakpoints.md
│   ├── MobileFirstDesign.md
│   └── ResponsivePatterns.md
├── 01.ResponsiveBasics/
├── 02.FlexboxAndGrid/
├── 03.ResponsiveNavigation/
├── 04.ResponsiveForms/
└── 05.ResponsiveDashboard/
```

---

## Suggested Learning Sequence

### `01.ResponsiveBasics/`
Purpose:
- introduce viewport, fluid layouts, relative sizing, and media queries

Key concepts:
- `meta viewport`
- `%`, `rem`, `vw`, `vh`
- `max-width`
- mobile-first CSS
- simple breakpoint usage

Suggested outcome:
- one simple page that changes layout and spacing cleanly between phone and desktop widths

### `02.FlexboxAndGrid/`
Purpose:
- teach responsive layout systems

Key concepts:
- Flexbox row/column switching
- CSS Grid for card layouts
- wrapping content
- gap and alignment

Suggested outcome:
- a responsive card gallery or feature grid

### `03.ResponsiveNavigation/`
Purpose:
- show how navigation changes across screen sizes

Key concepts:
- desktop top navigation
- mobile stacked or collapsed menu
- touch-friendly spacing
- simple show/hide menu interaction

Suggested outcome:
- one navigation example that works well on both narrow and wide screens

### `04.ResponsiveForms/`
Purpose:
- demonstrate mobile-friendly form design

Key concepts:
- single-column layout on mobile
- multi-column layout on wider screens
- readable labels and validation
- accessible tap targets

Suggested outcome:
- a student profile or contact form that adapts across breakpoints

### `05.ResponsiveDashboard/`
Purpose:
- combine the earlier concepts into a more realistic app

Key concepts:
- responsive header
- sidebar or stacked navigation
- responsive cards and tables
- content priority on small screens

Suggested outcome:
- a small dashboard-style Razor Pages app showing good responsive design decisions

---

## Documentation Expectations

When this folder is implemented later, the documentation should stay short and sharp.

### `README.md`
Should include:
- what responsive design is
- why it matters
- what examples are inside this folder
- the main learning objectives

### `QUICKSTART.md`
Should include:
- prerequisites
- how to build with `dotnet build`
- how to run the sample projects
- what students should expect to see

### `FRD.md`
Should include:
- purpose
- numbered functional requirements
- non-functional requirements
- constraints
- success criteria

### `docs/Key-Takeaways.md`
Should include:
- short summary of the main responsive design lessons

---

## AI Implementation Guidance

When implementing this folder later, follow these rules:

1. Use beginner-friendly code and comments.
2. Prefer Razor Pages over more advanced frontend stacks.
3. Keep each subproject focused on one main responsive concept.
4. Use semantic HTML wherever possible.
5. Use mobile-first CSS by default.
6. Avoid hardcoded pixel-heavy layouts unless teaching a contrast example.
7. Make layouts visibly different across small and large viewports.
8. Prefer practical examples over abstract demos.
9. Keep file names and folder naming consistent with the existing repository style.
10. Ensure projects build with `.NET 10` and are written in a style suitable for `C# 14`.

---

## Non-Functional Expectations

All future examples in this folder should aim for:
- clear structure
- simple setup
- responsive behaviour that can be tested by resizing the browser
- accessible spacing and readable typography
- maintainable CSS
- minimal JavaScript unless required

---

## Possible Teaching Themes

Good themes for sample apps:
- student portal
- course dashboard
- profile page
- responsive landing page
- mobile-friendly form workflow

These themes fit the unit and are easy for beginners to understand.

---

## Suggested Success Criteria

This folder will be successful when:
- students can clearly see the difference between fixed and responsive layouts
- each project demonstrates one important responsive pattern well
- all examples are easy to build and run with `dotnet`
- the folder feels consistent with the rest of the ITEC323 repository
- the examples prepare students to build web apps that work well on both phone and desktop screens

---

## Out of Scope For First Implementation

Avoid these in the first version unless explicitly requested:
- React or Vue implementations
- CSS framework comparisons across multiple libraries
- advanced animation systems
- complex state-heavy SPAs
- native mobile apps
- Progressive Web App features

Start simple. Teach the core responsive ideas first.
