const taskForm = document.getElementById("taskForm");
const taskIdInput = document.getElementById("taskId");
const titleInput = document.getElementById("title");
const techniqueInput = document.getElementById("technique");
const descriptionInput = document.getElementById("description");
const isCompletedInput = document.getElementById("isCompleted");
const saveButton = document.getElementById("saveButton");
const resetButton = document.getElementById("resetButton");
const loadButton = document.getElementById("loadButton");
const refreshButton = document.getElementById("refreshButton");
const messagePanel = document.getElementById("messagePanel");
const loadingPanel = document.getElementById("loadingPanel");
const taskTableBody = document.getElementById("taskTableBody");
const totalTasks = document.getElementById("totalTasks");
const completedTasks = document.getElementById("completedTasks");
const pendingTasks = document.getElementById("pendingTasks");
const refreshedAt = document.getElementById("refreshedAt");
const statusText = document.getElementById("statusText");

let isBusy = false;
let pollingTimer = null;
let currentTasks = [];

function setBusyState(busy) {
    isBusy = busy;

    const buttons = [saveButton, resetButton, loadButton, refreshButton, ...document.querySelectorAll(".row-action")];
    buttons.forEach((button) => {
        if (button) {
            button.disabled = busy;
        }
    });

    loadingPanel.classList.toggle("hidden", !busy);
}

function showMessage(text, type) {
    messagePanel.textContent = text;
    messagePanel.className = `message-panel message-${type}`;
}

function clearMessage() {
    messagePanel.textContent = "";
    messagePanel.className = "message-panel hidden";
}

function resetForm() {
    taskIdInput.value = "";
    taskForm.reset();
    saveButton.textContent = "Save Task";
    clearMessage();
}

function getTaskPayload() {
    return {
        title: titleInput.value.trim(),
        technique: techniqueInput.value.trim(),
        description: descriptionInput.value.trim() || null,
        isCompleted: isCompletedInput.checked
    };
}

async function parseApiResponse(response) {
    if (response.status === 204) {
        return null;
    }

    const responseText = await response.text();
    const data = responseText ? JSON.parse(responseText) : null;

    if (!response.ok) {
        throw new Error(getErrorMessage(data));
    }

    return data;
}

function getErrorMessage(data) {
    if (!data) {
        return "The request failed, but the server did not return extra details.";
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

    return "The request failed. Check the browser console or Swagger UI for more details.";
}

function formatDateTime(value) {
    if (!value) {
        return "Not available";
    }

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

function renderTasks(items) {
    currentTasks = items;

    if (items.length === 0) {
        taskTableBody.innerHTML = `
            <tr>
                <td colspan="5" class="empty-state">No capstone tasks are stored yet. Add one with the form.</td>
            </tr>`;
        return;
    }

    taskTableBody.innerHTML = items.map((item) => {
        const statusClass = item.isCompleted ? "status-complete" : "status-pending";
        const statusLabel = item.isCompleted ? "Completed" : "Pending";
        const toggleLabel = item.isCompleted ? "Mark Pending" : "Mark Complete";

        return `
            <tr>
                <td>
                    <strong>${escapeHtml(item.title)}</strong>
                    <div class="text-muted">${escapeHtml(item.description ?? "No description added.")}</div>
                </td>
                <td>${escapeHtml(item.technique)}</td>
                <td><span class="status-pill ${statusClass}">${statusLabel}</span></td>
                <td>${formatDateTime(item.lastUpdated)}</td>
                <td>
                    <div class="table-actions">
                        <button class="button button-secondary row-action" type="button" data-action="edit" data-id="${item.id}">Edit</button>
                        <button class="button button-secondary row-action" type="button" data-action="toggle" data-id="${item.id}">${toggleLabel}</button>
                        <button class="button button-danger row-action" type="button" data-action="delete" data-id="${item.id}">Delete</button>
                    </div>
                </td>
            </tr>`;
    }).join("");
}

function renderSummary(summary) {
    totalTasks.textContent = summary.totalTasks;
    completedTasks.textContent = summary.completedTasks;
    pendingTasks.textContent = summary.pendingTasks;
    statusText.textContent = summary.statusText;
    refreshedAt.textContent = formatDateTime(summary.refreshedAt);
}

function fillForm(item) {
    taskIdInput.value = item.id;
    titleInput.value = item.title;
    techniqueInput.value = item.technique;
    descriptionInput.value = item.description ?? "";
    isCompletedInput.checked = item.isCompleted;
    saveButton.textContent = "Update Task";
    showMessage(`Editing task #${item.id}. Change the fields and click Update Task.`, "warning");
    titleInput.focus();
}

async function refreshSummaryOnly() {
    try {
        const summary = await fetch("/api/capstonetasks/summary").then(parseApiResponse);
        renderSummary(summary);
    } catch (error) {
        showMessage(error.message, "error");
    }
}

async function loadData(successMessage, skipBusyGuard = false) {
    if (isBusy && !skipBusyGuard) {
        return;
    }

    clearMessage();
    setBusyState(true);

    try {
        const [items, summary] = await Promise.all([
            fetch("/api/capstonetasks").then(parseApiResponse),
            fetch("/api/capstonetasks/summary").then(parseApiResponse)
        ]);

        renderTasks(items);
        renderSummary(summary);

        if (successMessage) {
            showMessage(successMessage, "success");
        }
    } catch (error) {
        showMessage(error.message, "error");
    } finally {
        setBusyState(false);
    }
}

async function saveTask(event) {
    event.preventDefault();

    if (isBusy) {
        return;
    }

    clearMessage();

    const payload = getTaskPayload();

    if (!payload.title || !payload.technique) {
        showMessage("Title and technique are required before sending the request.", "warning");
        return;
    }

    const taskId = taskIdInput.value;
    const requestUrl = taskId ? `/api/capstonetasks/${taskId}` : "/api/capstonetasks";
    const requestMethod = taskId ? "PUT" : "POST";

    setBusyState(true);

    try {
        await fetch(requestUrl, {
            method: requestMethod,
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)
        }).then(parseApiResponse);

        resetForm();
        await loadData(taskId ? "Task updated successfully." : "Task created successfully.", true);
    } catch (error) {
        showMessage(error.message, "error");
        setBusyState(false);
    }
}

async function deleteTask(taskId) {
    if (isBusy) {
        return;
    }

    setBusyState(true);

    try {
        await fetch(`/api/capstonetasks/${taskId}`, {
            method: "DELETE"
        }).then(parseApiResponse);

        await loadData("Task deleted successfully.", true);
    } catch (error) {
        showMessage(error.message, "error");
        setBusyState(false);
    }
}

async function toggleTask(taskId) {
    const task = currentTasks.find((item) => item.id === taskId);

    if (!task || isBusy) {
        return;
    }

    setBusyState(true);

    try {
        await fetch(`/api/capstonetasks/${taskId}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                title: task.title,
                technique: task.technique,
                description: task.description,
                isCompleted: !task.isCompleted
            })
        }).then(parseApiResponse);

        await loadData("Task status updated successfully.", true);
    } catch (error) {
        showMessage(error.message, "error");
        setBusyState(false);
    }
}

function handleTableClick(event) {
    const clickedButton = event.target.closest(".row-action");

    if (!clickedButton) {
        return;
    }

    const taskId = Number(clickedButton.dataset.id);
    const action = clickedButton.dataset.action;

    if (action === "edit") {
        const task = currentTasks.find((item) => item.id === taskId);
        if (task) {
            fillForm(task);
        }
        return;
    }

    if (action === "toggle") {
        toggleTask(taskId);
        return;
    }

    if (action === "delete") {
        deleteTask(taskId);
    }
}

function startPolling() {
    stopPolling();
    pollingTimer = window.setInterval(() => {
        if (!isBusy) {
            refreshSummaryOnly();
        }
    }, 15000);
}

function stopPolling() {
    if (pollingTimer) {
        window.clearInterval(pollingTimer);
        pollingTimer = null;
    }
}

taskForm.addEventListener("submit", saveTask);
resetButton.addEventListener("click", resetForm);
loadButton.addEventListener("click", () => loadData("Capstone tasks loaded successfully."));
refreshButton.addEventListener("click", () => loadData("Capstone data refreshed successfully."));
taskTableBody.addEventListener("click", handleTableClick);
window.addEventListener("beforeunload", stopPolling);

loadData("Capstone data loaded successfully.");
startPolling();
