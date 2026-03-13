<template>
  <main class="page">
    <header class="hero">
      <span class="eyebrow">08.VueBasics</span>
      <h1>Vue 3 fundamentals with a .NET API backend</h1>
      <p>
        This mini app demonstrates single-file components, Composition API,
        <code>ref</code>, <code>computed</code>, <code>v-model</code>, and API
        calls with <code>onMounted</code>.
      </p>
    </header>

    <p v-if="loading" class="status">Loading lessons...</p>
    <p v-if="error" class="status error">{{ error }}</p>

    <section class="grid">
      <!-- Tips panel -->
      <section class="panel">
        <h2>Vue Tips from API</h2>
        <ul>
          <li v-for="(tip, index) in tips" :key="index">{{ tip }}</li>
        </ul>
      </section>

      <!-- Add Lesson Form -->
      <AddLessonForm :is-submitting="submitting" @create="createLesson" />
    </section>

    <!-- Lesson cards -->
    <section class="panel">
      <h2>Lessons ({{ lessons.length }} total, {{ beginnerCount }} beginner)</h2>
      <div class="cards">
        <LessonCard v-for="lesson in lessons" :key="lesson.id" :lesson="lesson" />
      </div>
    </section>
  </main>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import LessonCard from "./components/LessonCard.vue";
import AddLessonForm from "./components/AddLessonForm.vue";

// --- reactive state ---
const tips = ref([]);
const lessons = ref([]);
const loading = ref(true);
const submitting = ref(false);
const error = ref("");

// computed: count beginner lessons automatically
const beginnerCount = computed(
  () => lessons.value.filter((l) => l.level === "Beginner").length
);

// --- fetch on mount ---
onMounted(async () => {
  try {
    loading.value = true;
    error.value = "";

    const [tipsRes, lessonsRes] = await Promise.all([
      fetch("/api/tips"),
      fetch("/api/lessons")
    ]);

    if (!tipsRes.ok || !lessonsRes.ok) {
      throw new Error("Failed to load API data.");
    }

    const [tipsData, lessonsData] = await Promise.all([
      tipsRes.json(),
      lessonsRes.json()
    ]);

    tips.value = tipsData;
    lessons.value = lessonsData;
  } catch (err) {
    error.value = err.message || "Unknown error.";
  } finally {
    loading.value = false;
  }
});

// --- create lesson ---
async function createLesson(formData) {
  try {
    submitting.value = true;
    error.value = "";

    const response = await fetch("/api/lessons", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(formData)
    });

    if (!response.ok) {
      const payload = await response.json();
      throw new Error(payload.error || "Unable to create lesson.");
    }

    const created = await response.json();
    lessons.value = [...lessons.value, created];
  } catch (err) {
    error.value = err.message || "Unknown error.";
  } finally {
    submitting.value = false;
  }
}
</script>
