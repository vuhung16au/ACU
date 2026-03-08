# Functional Requirements Document: Consistent Web Design Module

## Purpose

This module teaches students to create maintainable, professional websites using ASP.NET Core Razor Pages layout system, partial views, and CSS frameworks to implement the DRY (Don't Repeat Yourself) principle.

## Target Audience

First-year students learning ASP.NET Core Razor Pages with basic HTML/CSS knowledge.

## Learning Objectives

By completing this module, students will be able to:

1. Create and use layout pages to eliminate HTML duplication
2. Implement `_ViewStart.cshtml` to specify default layouts
3. Use `_ViewImports.cshtml` for shared namespaces and tag helpers
4. Build reusable UI components with partial views
5. Integrate CSS frameworks (Bootstrap, Tailwind CSS)
6. Create multiple layouts for different site sections
7. Use sections to inject page-specific content into layouts
8. Apply DRY principles to web development

## Functional Requirements

### FR1: Layout System Understanding
**Priority**: Critical  
**Description**: Students must understand and implement the Razor Pages layout system.

**Acceptance Criteria**:
- Can create `_Layout.cshtml` with `@RenderBody()`
- Can use `_ViewStart.cshtml` to set default layout
- Can use `_ViewImports.cshtml` for tag helpers
- Understand layout inheritance hierarchy

My environment:
- ASP.NET Core Razor Pages
- dotnet 10
- C# 14 
- MacOS 

Your responses must include 
Keep the docs short and sharp.
- README.md: Overview, learning objectives, key concepts
- QUICKSTART.md: Step-by-step setup and run instructions
- `docs/Key-Takeaways.md`: Summary of important concepts and best practices

Acceptance Criteria:
- The new project is created with the same structure as `01.BasicLayout`
- `dotnet build` and `dotnet run` work successfully in the new project

Purpose: Understanding _Layout.cshtml, _ViewStart.cshtml, _ViewImports.cshtml

Minimal styling (just basic HTML structure)
Shows how layout inheritance works
Demonstrates @RenderBody(), @RenderSection()
Pages: Home, About, Contact (all use same layout)
Key Learning: Layout system basics, no distractions from CSS

### FR2: CSS Framework Integration
Folder: 02.BootstrapTheme
**Priority**: High  
**Description**: Students can integrate and use industry-standard CSS frameworks.

**Acceptance Criteria**:
- Can integrate Bootstrap 5 via CDN or local files
- Can integrate Tailwind CSS
- Understand when to use each framework
- Can create responsive layouts using framework utilities
- Keep the code, docs, and instructions concise and focused on the core concepts without unnecessary details or distractions.

My environment:
- ASP.NET Core Razor Pages
- dotnet 10
- C# 14 
- MacOS 

Your responses must include:
- README.md: Overview, learning objectives, key concepts
- QUICKSTART.md: Step-by-step setup and run instructions
- `docs/Key-Takeaways.md`: Summary of important concepts and best practices
Keep the docs short and sharp.

Acceptance Criteria:
- The new project is created with the same structure as `01.BasicLayout`
- `dotnet build` and `dotnet run` work successfully in the new project

**Purpose**: Industry-standard CSS framework
- Same structure as BasicLayout but with Bootstrap 5
- Responsive navigation bar
- Grid system examples
- Common components (cards, alerts, buttons)
- **Key Learning**: Bootstrap integration, CDN vs local files, responsive design

### TODO: FR3: Partial Views Implementation
**Priority**: High  
**Description**: Students can create and use partial views for reusable components.

**Acceptance Criteria**:
- Can create partial views (e.g., `_Navigation.cshtml`, `_Footer.cshtml`)
- Can invoke partials using `<partial>` tag helper
- Can pass data to partial views
- Understand when to create a partial view

### FR4: Section Usage
**Priority**: Medium  
**Description**: Students can use sections to inject page-specific content into layouts.

**Acceptance Criteria**:
- Can define optional and required sections
- Can render sections with `@RenderSection()`
- Can add page-specific scripts and styles
- Understand `IsSectionDefined()` checks

### TODO: FR5: Multiple Layouts
**Priority**: Medium  
**Description**: Students can implement different layouts for different site sections.

**Acceptance Criteria**:
- Can create multiple layout files
- Can override layout at page level or folder level
- Can implement public and admin layouts
- Understand layout selection priority

### TODO: FR6: DRY Principle Application
**Priority**: Critical  
**Description**: Students eliminate code duplication across their web applications.

**Acceptance Criteria**:
- No duplicated `<head>` content across pages
- No duplicated navigation or footer code
- Reusable components extracted to partials
- Consistent styling across all pages

## Non-Functional Requirements

### NFR1: Code Quality
- Follow C# and HTML naming conventions
- Include XML documentation for complex logic
- Use semantic HTML elements
- Write clean, readable code

### NFR2: Educational Value
- Projects progress from simple to complex
- Each concept introduced in isolation before combining
- Clear comments explaining layout mechanics
- Examples demonstrate real-world scenarios

### NFR3: Performance
- Layouts render efficiently (no noticeable delay)
- CSS loaded optimally (CDN or bundled)
- No excessive partial view nesting

### NFR4: Accessibility
- Semantic HTML in layouts
- Proper heading hierarchy
- ARIA landmarks in navigation
- Responsive design for mobile devices

### NFR5: Browser Compatibility
- Works in Chrome, Firefox, Safari, Edge
- Responsive across desktop, tablet, mobile
- CSS framework defaults support modern browsers

## Project Requirements

### 01.BasicLayout
- Simple layout with header, main, footer
- No CSS framework (focus on mechanics)
- Multiple pages using same layout
- Demonstrates `@RenderBody()` and `@RenderSection()`

### 02.BootstrapTheme
- Bootstrap 5 integration
- Responsive navigation bar
- Grid system examples
- Common components (cards, buttons, forms)

### TODO: 03.TailwindTheme
- Tailwind CSS integration
- Utility-first approach
- Same functionality as BootstrapTheme
- Side-by-side comparison capability

### TODO: 04.PartialViews
- At least 5 partial views (nav, footer, sidebar, card, alert)
- Demonstrates data passing to partials
- Shows when/why to create partials
- DRY principle in action

### TODO: 05.MultipleLayouts
- Minimum 3 layouts (public, admin, print)
- Folder-level `_ViewStart.cshtml`
- Page-level layout override example
- Different styling per layout type

### TODO: 06.ComprehensiveExample
- Real-world application (e.g., blog, portfolio, e-commerce)
- Uses all previous concepts
- Multiple layouts and extensive partials
- Production-ready structure

## Documentation Requirements

Each project must include:
- **README.md**: Overview, learning objectives, key concepts
- **QUICKSTART.md**: Step-by-step setup and run instructions
- **FRD.md**: Project-specific functional requirements

Module must include:
- **docs/layouts-explained.md**: Technical deep dive
- **docs/partial-views-guide.md**: Usage patterns and best practices
- **docs/css-frameworks-comparison.md**: Framework selection guide
- **docs/dry-principles.md**: Duplication avoidance strategies

## Constraints

- Must use ASP.NET Core Razor Pages (not MVC)
- Must target .NET 8.0 LTS (compatible with .NET 9.0)
- Must work on Windows, macOS, and Linux
- No JavaScript frameworks (Vue, React) - keep it simple
- Focus on server-side rendering

## Success Criteria

Students successfully complete this module when they can:
1. Build a multi-page website with zero duplicated layout code
2. Create a professional-looking site using a CSS framework
3. Extract reusable components into partial views
4. Implement multiple layouts for different purposes
5. Explain the purpose of `_Layout`, `_ViewStart`, and `_ViewImports`
6. Apply DRY principles to reduce code duplication

## Testing Requirements

- Each project must build without errors
- All pages must render correctly
- Layouts must apply consistently across pages
- Responsive design must work on mobile/desktop
- Links between pages must function

## Future Enhancements

Potential additions for advanced students:
- View Components (more powerful than partials)
- Tag Helpers for custom components
- Asset bundling and minification
- Theme switching (light/dark mode)
- Internationalization (i18n) with layouts

