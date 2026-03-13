# Key Takeaways - Vue Basics

## Core concepts

- Vue 3 single-file components (`.vue`) combine `<template>`, `<script setup>`, and optional `<style>` in one file.
- `ref()` wraps a primitive value in a reactive container; read/write via `.value` in script, directly in template.
- `reactive()` wraps a plain object so all properties are reactive — ideal for grouped form state.
- `computed()` returns a readonly reactive value derived from other reactive state; recalculates automatically.
- `onMounted()` is the lifecycle hook for loading remote data after the component is attached to the DOM.
- `v-model` is syntactic sugar for `:value` + `@input` — it creates two-way binding on form inputs.
- `defineProps` and `defineEmits` (compiler macros) replace the `props` / `emits` options in `<script setup>`.

## Practical patterns used in this module

- Loading state and error state for async data fetching inside `onMounted`.
- `Promise.all` for parallel fetches of tips and lessons.
- Computed property `beginnerCount` automatically reflects list changes without manual tracking.
- `reactive` form object reset after submission to clear all fields at once.
- Child emits `create` event; parent handles the POST and appends the new lesson to `lessons.value`.

## Vue vs React comparison

| Concern | Vue 3 | React 18 |
|---|---|---|
| Reactivity | `ref` / `reactive` / `computed` | `useState` / `useMemo` |
| Side effects | `onMounted`, `watch` | `useEffect` |
| Two-way binding | `v-model` (built-in) | Controlled input pattern |
| Templating | HTML-first `.vue` SFC | JSX inside `.jsx` |
| Component comms | Props + `emit` | Props + callback props |
| Loops | `v-for` directive | `.map()` in JSX |

## Suggested practice

1. Add client-side validation for empty or too-short summaries before submitting.
2. Add a `v-model`-bound filter to show only `Beginner` lessons.
3. Add delete support: backend `DELETE /api/lessons/:id` and a delete button in `LessonCard.vue`.
4. Convert `AddLessonForm.vue` to use `ref` instead of `reactive` and compare the two approaches.
5. Compare this flow with `07.ReactBasics` to notice structural similarities and Vue-specific conveniences.
