<template>
  <article class="card">
    <h3>{{ lesson.title }}</h3>
    <p>{{ lesson.summary }}</p>
    <span class="pill">{{ lesson.level }}</span>
  </article>
</template>

<script setup>
defineProps({
  lesson: {
    type: Object,
    required: true
  }
});
</script>
