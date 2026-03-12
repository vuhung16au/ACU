const announcementsButton = document.getElementById("announcementsButton");
const spotlightButton = document.getElementById("spotlightButton");
const summaryButton = document.getElementById("summaryButton");
const activityButton = document.getElementById("activityButton");
const checkInButton = document.getElementById("checkInButton");
const checkInForm = document.getElementById("checkInForm");
const activityNameInput = document.getElementById("activityName");
const reflectionInput = document.getElementById("reflection");

const announcementsPanel = document.getElementById("announcementsPanel");
const spotlightPanel = document.getElementById("spotlightPanel");
const activityLogPanel = document.getElementById("activityLogPanel");

const announcementsStatus = document.getElementById("announcementsStatus");
const spotlightStatus = document.getElementById("spotlightStatus");
const summaryStatus = document.getElementById("summaryStatus");
const activityStatus = document.getElementById("activityStatus");

const completedActivities = document.getElementById("completedActivities");
const remainingActivities = document.getElementById("remainingActivities");
const nextFocus = document.getElementById("nextFocus");
const summaryRefreshedAt = document.getElementById("summaryRefreshedAt");

function showStatus(panel, message, type) {
    panel.textContent = message;
    panel.className = `panel-status status-${type}`;
}

function hideStatus(panel) {
    panel.textContent = "";
    panel.className = "panel-status hidden";
}

function setButtonState(button, isBusy) {
    if (button) {
        button.disabled = isBusy;
    }
}

async function parseResponse(response) {
    const text = await response.text();
    const data = text ? JSON.parse(text) : null;

    if (!response.ok) {
        throw new Error(getErrorMessage(data));
    }

    return data;
}

function getErrorMessage(data) {
    if (!data) {
        return "The request failed and no extra details were returned.";
    }

    if (data.message) {
        return data.message;
    }

    if (data.errors) {
        const messages = Object.values(data.errors).flat();
        if (messages.length > 0) {
            return messages.join(" ");
        }
    }

    return "The request failed. Check the browser console for more details.";
}

function formatDateTime(value) {
    return new Date(value).toLocaleString();
}

function escapeHtml(value) {
    return value
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll("\"", "&quot;")
        .replaceAll("'", "&#39;");
}

function renderAnnouncements(items) {
    if (items.length === 0) {
        announcementsPanel.innerHTML = '<p class="empty-state">No announcements are available right now.</p>';
        return;
    }

    announcementsPanel.innerHTML = items.map((item) => `
        <article class="announcement-card">
            <div class="announcement-meta">${escapeHtml(item.topic)} · ${formatDateTime(item.publishedAt)}</div>
            <h3>${escapeHtml(item.title)}</h3>
            <p class="mb-0">${escapeHtml(item.message)}</p>
        </article>`).join("");
}

function renderSpotlight(item) {
    spotlightPanel.innerHTML = `
        <div class="spotlight-type">${escapeHtml(item.resourceType)}</div>
        <h3>${escapeHtml(item.title)}</h3>
        <p>${escapeHtml(item.summary)}</p>
        <p class="mb-0"><strong>Next step:</strong> ${escapeHtml(item.nextStep)}</p>`;
}

function renderSummary(item) {
    completedActivities.textContent = item.completedActivities;
    remainingActivities.textContent = item.remainingActivities;
    nextFocus.textContent = item.nextFocus;
    summaryRefreshedAt.textContent = formatDateTime(item.refreshedAt);
}

function renderActivityLog(items) {
    if (items.length === 0) {
        activityLogPanel.innerHTML = '<p class="empty-state">No activity has been logged yet.</p>';
        return;
    }

    activityLogPanel.innerHTML = items.map((item) => createActivityMarkup(item)).join("");
}

function createActivityMarkup(item) {
    return `
        <article class="activity-entry" data-id="${item.id}">
            <div class="activity-meta">${formatDateTime(item.createdAt)}</div>
            <h3>${escapeHtml(item.activityName)}</h3>
            <p class="mb-0">${escapeHtml(item.reflection)}</p>
        </article>`;
}

async function refreshAnnouncements() {
    setButtonState(announcementsButton, true);
    showStatus(announcementsStatus, "Loading announcements...", "loading");

    try {
        const items = await fetch("/api/learningdashboard/announcements").then(parseResponse);
        renderAnnouncements(items);
        showStatus(announcementsStatus, "Announcements updated without changing the rest of the page.", "success");
    } catch (error) {
        showStatus(announcementsStatus, error.message, "error");
    } finally {
        setButtonState(announcementsButton, false);
    }
}

async function refreshSpotlight() {
    setButtonState(spotlightButton, true);
    showStatus(spotlightStatus, "Loading spotlight resource...", "loading");

    try {
        const item = await fetch("/api/learningdashboard/spotlight").then(parseResponse);
        renderSpotlight(item);
        showStatus(spotlightStatus, "Spotlight card refreshed independently.", "success");
    } catch (error) {
        showStatus(spotlightStatus, error.message, "error");
    } finally {
        setButtonState(spotlightButton, false);
    }
}

async function refreshSummary() {
    setButtonState(summaryButton, true);
    showStatus(summaryStatus, "Refreshing summary cards...", "loading");

    try {
        const item = await fetch("/api/learningdashboard/progress").then(parseResponse);
        renderSummary(item);
        showStatus(summaryStatus, "Summary cards refreshed without resetting the form.", "success");
    } catch (error) {
        showStatus(summaryStatus, error.message, "error");
    } finally {
        setButtonState(summaryButton, false);
    }
}

async function reloadActivityLog() {
    setButtonState(activityButton, true);
    showStatus(activityStatus, "Loading activity log...", "loading");

    try {
        const items = await fetch("/api/learningdashboard/activity-log").then(parseResponse);
        renderActivityLog(items);
        showStatus(activityStatus, "Activity log reloaded successfully.", "success");
    } catch (error) {
        showStatus(activityStatus, error.message, "error");
    } finally {
        setButtonState(activityButton, false);
    }
}

async function submitCheckIn(event) {
    event.preventDefault();

    setButtonState(checkInButton, true);
    showStatus(activityStatus, "Saving the new check-in...", "loading");

    try {
        const createdItem = await fetch("/api/learningdashboard/check-ins", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                activityName: activityNameInput.value.trim(),
                reflection: reflectionInput.value.trim()
            })
        }).then(parseResponse);

        if (activityLogPanel.querySelector(".empty-state")) {
            activityLogPanel.innerHTML = "";
        }

        activityLogPanel.insertAdjacentHTML("afterbegin", createActivityMarkup(createdItem));
        showStatus(activityStatus, "New activity appended to the log.", "success");
        checkInForm.reset();
        await refreshSummary();
        hideStatus(summaryStatus);
        await refreshSpotlight();
        hideStatus(spotlightStatus);
    } catch (error) {
        showStatus(activityStatus, error.message, "error");
    } finally {
        setButtonState(checkInButton, false);
    }
}

announcementsButton.addEventListener("click", refreshAnnouncements);
spotlightButton.addEventListener("click", refreshSpotlight);
summaryButton.addEventListener("click", refreshSummary);
activityButton.addEventListener("click", reloadActivityLog);
checkInForm.addEventListener("submit", submitCheckIn);

refreshAnnouncements();
refreshSpotlight();
refreshSummary();
reloadActivityLog();
