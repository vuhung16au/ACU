import { useState } from "react";

const initialForm = {
  title: "",
  summary: "",
  level: "Beginner"
};

export default function AddLessonForm({ onCreate, isSubmitting }) {
  const [form, setForm] = useState(initialForm);

  function handleChange(event) {
    const { name, value } = event.target;
    setForm((current) => ({ ...current, [name]: value }));
  }

  async function handleSubmit(event) {
    event.preventDefault();
    await onCreate(form);
    setForm(initialForm);
  }

  return (
    <form className="form" onSubmit={handleSubmit}>
      <h3>Add Lesson</h3>
      <label>
        Title
        <input name="title" value={form.title} onChange={handleChange} required />
      </label>

      <label>
        Summary
        <textarea
          name="summary"
          value={form.summary}
          onChange={handleChange}
          rows="3"
          required
        />
      </label>

      <label>
        Level
        <select name="level" value={form.level} onChange={handleChange}>
          <option>Beginner</option>
          <option>Intermediate</option>
          <option>Advanced</option>
        </select>
      </label>

      <button type="submit" disabled={isSubmitting}>
        {isSubmitting ? "Saving..." : "Create Lesson"}
      </button>
    </form>
  );
}
