const loadingPanel = document.querySelector('#loadingPanel');
const messagePanel = document.querySelector('#messagePanel');
const loadWithPromisesButton = document.querySelector('#loadWithPromises');
const loadWithAsyncAwaitButton = document.querySelector('#loadWithAsyncAwait');
const simulateErrorButton = document.querySelector('#simulateError');
const clearFormButton = document.querySelector('#clearForm');
const sessionForm = document.querySelector('#sessionForm');
const sessionTableBody = document.querySelector('#sessionTableBody');

function setLoading(isLoading) {
    if (!loadingPanel) {
        return;
    }

    loadingPanel.classList.toggle('hidden', !isLoading);
}

function showMessage(text, typeClass) {
    if (!messagePanel) {
        return;
    }

    messagePanel.textContent = text;
    messagePanel.classList.remove('hidden', 'message-info', 'message-success', 'message-warning');
    messagePanel.classList.add(typeClass);
}

function clearMessage() {
    if (!messagePanel) {
        return;
    }

    messagePanel.textContent = '';
    messagePanel.classList.add('hidden');
    messagePanel.classList.remove('message-info', 'message-success', 'message-warning');
}

function formatDate(utcIsoText) {
    const date = new Date(utcIsoText);
    return date.toLocaleTimeString();
}

function renderSessions(sessions) {
    if (!sessionTableBody) {
        return;
    }

    if (!Array.isArray(sessions) || sessions.length === 0) {
        sessionTableBody.innerHTML = '<tr><td colspan="5" class="empty-state">No sessions yet. Add one using the form.</td></tr>';
        return;
    }

    sessionTableBody.innerHTML = sessions
        .map((session) => {
            const statusClass = session.isCompleted ? 'badge-success' : 'badge-warning';
            const statusLabel = session.isCompleted ? 'Completed' : 'Pending';

            return `
                <tr>
                    <td>${session.studentName}</td>
                    <td>${session.topic}</td>
                    <td>${session.minutesStudied}</td>
                    <td><span class="status-badge ${statusClass}">${statusLabel}</span></td>
                    <td>${formatDate(session.lastUpdatedUtc)}</td>
                </tr>`;
        })
        .join('');
}

function renderSummary(summary) {
    const total = document.querySelector('#totalSessions');
    const completed = document.querySelector('#completedSessions');
    const pending = document.querySelector('#pendingSessions');
    const average = document.querySelector('#averageMinutes');

    if (!total || !completed || !pending || !average) {
        return;
    }

    total.textContent = String(summary.totalSessions ?? 0);
    completed.textContent = String(summary.completedSessions ?? 0);
    pending.textContent = String(summary.pendingSessions ?? 0);
    average.textContent = String(summary.averageMinutes ?? 0);
}

function fetchJsonWithPromises(url) {
    return fetch(url).then((response) => {
        if (!response.ok) {
            return response.json().catch(() => ({})).then((body) => {
                const message = body.message || body.title || `Request failed with status ${response.status}`;
                throw new Error(message);
            });
        }

        return response.json();
    });
}

function loadDataWithPromises() {
    clearMessage();
    setLoading(true);

    Promise.all([
        fetchJsonWithPromises('/api/learningsessions'),
        fetchJsonWithPromises('/api/learningsessions/summary')
    ])
        .then(([sessions, summary]) => {
            renderSessions(sessions);
            renderSummary(summary);
            showMessage('Loaded using Promise chain (.then/.catch).', 'message-success');
        })
        .catch((error) => {
            showMessage(`Promise chain error: ${error.message}`, 'message-warning');
        })
        .finally(() => {
            setLoading(false);
        });
}

async function fetchJsonWithAsyncAwait(url, options) {
    const response = await fetch(url, options);

    if (!response.ok) {
        let body = {};

        try {
            body = await response.json();
        } catch {
            body = {};
        }

        const message = body.message || body.title || `Request failed with status ${response.status}`;
        throw new Error(message);
    }

    return response.json();
}

async function loadDataWithAsyncAwait() {
    clearMessage();
    setLoading(true);

    try {
        const sessions = await fetchJsonWithAsyncAwait('/api/learningsessions');
        const summary = await fetchJsonWithAsyncAwait('/api/learningsessions/summary');

        renderSessions(sessions);
        renderSummary(summary);
        showMessage('Loaded using async/await with try/catch.', 'message-success');
    } catch (error) {
        showMessage(`Async/await error: ${error.message}`, 'message-warning');
    } finally {
        setLoading(false);
    }
}

async function createSession(event) {
    event.preventDefault();

    const studentNameInput = document.querySelector('#studentName');
    const topicInput = document.querySelector('#topic');
    const minutesInput = document.querySelector('#minutesStudied');
    const isCompletedInput = document.querySelector('#isCompleted');

    const studentName = studentNameInput?.value.trim() ?? '';
    const topic = topicInput?.value.trim() ?? '';
    const minutesStudied = Number(minutesInput?.value ?? '0');
    const isCompleted = Boolean(isCompletedInput?.checked);

    if (studentName.length < 2 || topic.length < 2 || Number.isNaN(minutesStudied) || minutesStudied < 5) {
        showMessage('Enter valid values before creating a session.', 'message-warning');
        return;
    }

    setLoading(true);

    try {
        await fetchJsonWithAsyncAwait('/api/learningsessions', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                studentName,
                topic,
                minutesStudied,
                isCompleted
            })
        });

        showMessage('Session created. Reloading data...', 'message-success');
        sessionForm?.reset();
        await loadDataWithAsyncAwait();
    } catch (error) {
        showMessage(`Create failed: ${error.message}`, 'message-warning');
    } finally {
        setLoading(false);
    }
}

async function runFailureDemo() {
    clearMessage();
    setLoading(true);

    try {
        await fetchJsonWithAsyncAwait('/api/learningsessions/failure-demo');
    } catch (error) {
        showMessage(`Expected failure captured: ${error.message}`, 'message-warning');
    } finally {
        setLoading(false);
    }
}

loadWithPromisesButton?.addEventListener('click', loadDataWithPromises);
loadWithAsyncAwaitButton?.addEventListener('click', loadDataWithAsyncAwait);
simulateErrorButton?.addEventListener('click', runFailureDemo);
sessionForm?.addEventListener('submit', createSession);
clearFormButton?.addEventListener('click', () => {
    sessionForm?.reset();
    clearMessage();
});
