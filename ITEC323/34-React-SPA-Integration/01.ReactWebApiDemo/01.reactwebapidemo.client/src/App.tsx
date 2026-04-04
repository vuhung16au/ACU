import { FormEvent, useEffect, useState } from 'react';
import './App.css';

interface ApiRouteInfo {
  httpMethod: string;
  route: string;
  purpose: string;
}

interface SpaModuleOverview {
  title: string;
  description: string;
  localDevelopmentFlow: string;
  publishedDeploymentFlow: string;
  apiRoutes: ApiRouteInfo[];
  learningChecklist: string[];
}

interface PracticeMessageRequest {
  studentName: string;
  currentTopic: string;
}

interface PracticeMessageResponse {
  heading: string;
  message: string;
  nextStep: string;
  reminderItems: string[];
}

function App() {
  const [overview, setOverview] = useState<SpaModuleOverview | null>(null);
  const [practiceMessage, setPracticeMessage] = useState<PracticeMessageResponse | null>(null);
  const [loadError, setLoadError] = useState('');
  const [submitError, setSubmitError] = useState('');
  const [isLoadingOverview, setIsLoadingOverview] = useState(true);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [formModel, setFormModel] = useState<PracticeMessageRequest>({
    studentName: 'Ava',
    currentTopic: 'React state updates'
  });

  useEffect(() => {
    void loadOverview();
  }, []);

  async function loadOverview() {
    setIsLoadingOverview(true);
    setLoadError('');

    try {
      const response = await fetch('/api/spa-integration/overview');

      if (!response.ok) {
        throw new Error('Overview request failed.');
      }

      const data = (await response.json()) as SpaModuleOverview;
      setOverview(data);
    } catch (error) {
      console.error(error);
      setLoadError('The React app could not load the starter content from ASP.NET Core.');
    } finally {
      setIsLoadingOverview(false);
    }
  }

  async function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    setSubmitError('');
    setPracticeMessage(null);

    if (!formModel.studentName.trim() || !formModel.currentTopic.trim()) {
      setSubmitError('Enter both a student name and a topic before sending the request.');
      return;
    }

    setIsSubmitting(true);

    try {
      const response = await fetch('/api/spa-integration/practice-message', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(formModel)
      });

      if (!response.ok) {
        throw new Error('Practice message request failed.');
      }

      const data = (await response.json()) as PracticeMessageResponse;
      setPracticeMessage(data);
    } catch (error) {
      console.error(error);
      setSubmitError('The backend returned an error. Check the form values and try again.');
    } finally {
      setIsSubmitting(false);
    }
  }

  return (
    <main className="page-shell">
      <section className="hero">
        <p className="eyebrow">React + ASP.NET Core</p>
        <h1>SPA Integration Demo</h1>
        <p className="hero-copy">
          This sample keeps the backend small and the React UI simple so you can trace a request from form input to JSON response to screen update.
        </p>
      </section>

      {isLoadingOverview ? <p className="status-card">Loading starter content from ASP.NET Core...</p> : null}
      {loadError ? <p className="status-card error-card">{loadError}</p> : null}

      {overview ? (
        <section className="content-grid">
          <article className="panel panel-highlight">
            <h2>{overview.title}</h2>
            <p>{overview.description}</p>
          </article>

          <article className="panel">
            <h2>Local development flow</h2>
            <p>{overview.localDevelopmentFlow}</p>
          </article>

          <article className="panel">
            <h2>Published deployment flow</h2>
            <p>{overview.publishedDeploymentFlow}</p>
          </article>

          <article className="panel">
            <h2>API routes to inspect</h2>
            <ul className="route-list">
              {overview.apiRoutes.map((apiRoute) => (
                <li key={`${apiRoute.httpMethod}-${apiRoute.route}`}>
                  <strong>{apiRoute.httpMethod}</strong>
                  <code>{apiRoute.route}</code>
                  <span>{apiRoute.purpose}</span>
                </li>
              ))}
            </ul>
          </article>

          <article className="panel">
            <h2>Learning checklist</h2>
            <ul className="checklist">
              {overview.learningChecklist.map((item) => (
                <li key={item}>{item}</li>
              ))}
            </ul>
          </article>

          <article className="panel panel-form">
            <h2>Send a user-triggered request</h2>
            <p>Use this form to trigger a POST request and render the JSON response returned by ASP.NET Core.</p>

            <form onSubmit={handleSubmit}>
              <label htmlFor="studentName">Student name</label>
              <input
                id="studentName"
                name="studentName"
                value={formModel.studentName}
                onChange={(event) => setFormModel({ ...formModel, studentName: event.target.value })}
              />

              <label htmlFor="currentTopic">Current topic</label>
              <input
                id="currentTopic"
                name="currentTopic"
                value={formModel.currentTopic}
                onChange={(event) => setFormModel({ ...formModel, currentTopic: event.target.value })}
              />

              <button type="submit" disabled={isSubmitting}>
                {isSubmitting ? 'Sending request...' : 'Generate practice message'}
              </button>
            </form>

            {submitError ? <p className="inline-error">{submitError}</p> : null}

            {practiceMessage ? (
              <section className="response-card">
                <h3>{practiceMessage.heading}</h3>
                <p>{practiceMessage.message}</p>
                <p className="next-step"><strong>Next step:</strong> {practiceMessage.nextStep}</p>
                <ul className="checklist">
                  {practiceMessage.reminderItems.map((reminder) => (
                    <li key={reminder}>{reminder}</li>
                  ))}
                </ul>
              </section>
            ) : null}
          </article>
        </section>
      ) : null}
    </main>
  );
}

export default App;
