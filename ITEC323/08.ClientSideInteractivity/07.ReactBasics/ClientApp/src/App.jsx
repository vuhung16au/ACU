import { useEffect, useState } from "react";
import LessonCard from "./components/LessonCard";
import AddLessonForm from "./components/AddLessonForm";

export default function App() {
  const [tips, setTips] = useState([]);
  const [lessons, setLessons] = useState([]);
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    let active = true;

    async function loadData() {
      try {
        setLoading(true);
        setError("");

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

        if (!active) {
          return;
        }

        setTips(tipsData);
        setLessons(lessonsData);
      } catch (err) {
        if (active) {
          setError(err.message || "Unknown error.");
        }
      } finally {
        if (active) {
          setLoading(false);
        }
      }
    }

    loadData();
    return () => {
      active = false;
    };
  }, []);

  async function createLesson(formData) {
    try {
      setSubmitting(true);
      setError("");

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
      setLessons((current) => [...current, created]);
    } catch (err) {
      setError(err.message || "Unknown error.");
    } finally {
      setSubmitting(false);
    }
  }

  return (
    <main className="page">
      <header className="hero">
        <span className="eyebrow">07.ReactBasics</span>
        <h1>React fundamentals with a .NET API backend</h1>
        <p>
          This mini app demonstrates functional components, JSX, useState, useEffect,
          API calls, and CORS-enabled local development.
        </p>
      </header>

      {loading && <p className="status">Loading lessons...</p>}
      {error && <p className="status error">{error}</p>}

      <section className="grid">
        <section className="panel">
          <h2>React Tips from API</h2>
          <ul>
            {tips.map((tip) => (
              <li key={tip}>{tip}</li>
            ))}
          </ul>
        </section>

        <AddLessonForm onCreate={createLesson} isSubmitting={submitting} />
      </section>

      <section className="panel">
        <h2>Lesson Cards</h2>
        <div className="cards">
          {lessons.map((lesson) => (
            <LessonCard key={lesson.id} lesson={lesson} />
          ))}
        </div>
      </section>
    </main>
  );
}
