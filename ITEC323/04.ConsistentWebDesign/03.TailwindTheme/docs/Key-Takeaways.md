# Key Takeaways - Tailwind Theme

## Core Concepts

### 1. Tailwind via CDN
- Add `<script src="https://cdn.tailwindcss.com"></script>` in `_Layout.cshtml`.
- Best for learning and prototypes.
- For production, prefer a build pipeline to remove unused classes.

### 2. Utility-First Workflow
- Build UI directly in markup with small, focused classes.
- Example: `rounded-xl border bg-white p-6 shadow-sm`.
- Benefits: fast iteration and consistent spacing/typography.

### 3. Responsive Prefixes
- `sm:` = 640px+, `md:` = 768px+, `lg:` = 1024px+, `xl:` = 1280px+.
- Example: `grid-cols-1 sm:grid-cols-2 md:grid-cols-3`.

### 4. Common Patterns
- Cards: border + radius + shadow utilities
- Alerts: semantic color utilities (`bg-emerald-50`, `text-emerald-800`)
- Forms: focus styles (`focus:ring-2`, `focus:border-sky-500`)

### 5. Tailwind vs Bootstrap (quick compare)
- Bootstrap: pre-designed components
- Tailwind: low-level utilities for custom design
- Use Bootstrap when speed with defaults matters; use Tailwind when design flexibility matters.

## Best Practices

- Keep class names readable; group by layout, spacing, color, typography.
- Extract repeated patterns into partial views in later modules.
- Test at multiple widths before finalizing layout.
