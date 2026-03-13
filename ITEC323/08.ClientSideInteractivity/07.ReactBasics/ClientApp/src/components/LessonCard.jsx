export default function LessonCard({ lesson }) {
  return (
    <article className="card">
      <h3>{lesson.title}</h3>
      <p>{lesson.summary}</p>
      <span className="pill">{lesson.level}</span>
    </article>
  );
}
