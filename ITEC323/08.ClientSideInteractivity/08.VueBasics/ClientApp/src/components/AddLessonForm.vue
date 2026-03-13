<template>
  <form class="form" @submit.prevent="handleSubmit">
    <h3>Add Lesson</h3>

    <label>
      Title
      <input v-model="form.title" type="text" required />
    </label>

    <label>
      Summary
      <textarea v-model="form.summary" rows="3" required></textarea>
    </label>

    <label>
      Level
      <select v-model="form.level">
        <option>Beginner</option>
        <option>Intermediate</option>
        <option>Advanced</option>
      </select>
    </label>

    <button type="submit" :disabled="isSubmitting">
      {{ isSubmitting ? "Saving..." : "Create Lesson" }}
    </button>
  </form>
</template>

<script setup>
import { reactive } from "vue";

const props = defineProps({
  isSubmitting: {
    type: Boolean,
    default: false
  }
});

const emit = defineEmits(["create"]);

// reactive object keeps all form fields together
const form = reactive({
  title: "",
  summary: "",
  level: "Beginner"
});

async function handleSubmit() {
  // emit a plain object copy so the parent receives a snapshot
  await emit("create", { ...form });
  // reset form after submission
  form.title = "";
  form.summary = "";
  form.level = "Beginner";
}
</script>
