const taskForm = document.getElementById("taskForm");
const taskIdInput = document.getElementById("taskId");
const titleInput = document.getElementById("title");
const categoryInput = document.getElementById("category");
const dueDateInput = document.getElementById("dueDate");
const notesInput = document.getElementById("notes");
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
const generatedAt = document.getElementById("generatedAt");

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
        category: categoryInput.value.trim(),
        dueDate: dueDateInput.value || null,
        notes: notesInput.value.trim() || null,
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

    return "The request failed. Check the browser console for more details.";
}

function formatDate(dateText) {
    if (!dateText) {
        return "No due date";
    }

    const date = new Date(dateText);
    return date.toLocaleDateString();
}

function formatDateTime(dateText) {
    if (!dateText) {
        return "Not available";
    }

    const date = new Date(dateText);
    return date.toLocaleString();
}

function renderTasks(tasks) {
    currentTasks = tasks;

    if (tasks.length === 0) {
        taskTableBody.innerHTML = `
            <tr>
                <td colspan="6" class="empty-state">No tasks are stored in memory yet. Add one with the form.</td>
            </tr>`;
        return;
    }

    taskTableBody.innerHTML = tasks
        .map((task) => {
            const statusClass = task.isCompleted ? "status-complete" : "status-pending";
            const statusLabel = task.isCompleted ? "Completed" : "Pending";
            const toggleLabel = task.isCompleted ? "Mark Pending" : "Mark Complete";

            return `
                <tr>
                    <td>
                        <strong>${escapeHtml(task.title)}</strong>
                        <div class="text-muted">${escapeHtml(task.notes ?? "No notes added.")}</div>
                    </td>
                    <td>${escapeHtml(task.category)}</td>
                    <td><span class="status-pill ${statusClass}">${statusLabel}</span></td>
                    <td>${formatDate(task.dueDate)}</td>
                    <td>${formatDateTime(task.lastUpdated)}</td>
                    <td>
                        <div class="table-actions">
                            <button class="button button-secondary row-action" type="button" data-action="edit" data-id="${task.id}">Edit</button>
                            <button class="button button-secondary row-action" type="button" data-action="toggle" data-id="${task.id}">${toggleLabel}</button>
                            <button class="button button-danger row-action" type="button" data-action="delete" data-id="${task.id}">Delete</button>
                        </div>
                    </td>
                </tr>`;
        })
        .join("");
}

function renderSummary(summary) {
    totalTasks.textContent = summary.totalTasks;
    completedTasks.textContent = summary.completedTasks;
    pendingTasks.textContent = summary.pendingTasks;
    generatedAt.textContent = formatDateTime(summary.generatedAt);
}

function fillForm(task) {
    taskIdInput.value = task.id;
    titleInput.value = task.title;
    categoryInput.value = task.category;
    dueDateInput.value = task.dueDate ? task.dueDate.slice(0, 10) : "";
    notesInput.value = task.notes ?? "";
    isCompletedInput.checked = task.isCompleted;
    saveButton.textContent = "Update Task";
    showMessage(`Editing task #${task.id}. Change the fields and click Update Task.`, "warning");
    titleInput.focus();
}

function escapeHtml(value) {
    return value
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll("\"", "&quot;")
        .replaceAll("'", "&#39;");
}

async function loadData(successMessage, skipBusyGuard = false) {
    if (isBusy && !skipBusyGuard) {
        return;
    }

    clearMessage();
    setBusyState(true);

    try {
        const [tasks, summary] = await Promise.all([
            fetch("/api/studytasks").then(parseApiResponse),
            fetch("/api/studytasks/summary").then(parseApiResponse)
        ]);

        renderTasks(tasks);
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

    const taskPayload = getTaskPayload();

    if (!taskPayload.title || !taskPayload.category) {
        showMessage("Title and category are required before sending the request.", "warning");
        return;
    }

    const taskId = taskIdInput.value;
    const requestUrl = taskId ? `/api/studytasks/${taskId}` : "/api/studytasks";
    const requestMethod = taskId ? "PUT" : "POST";

    setBusyState(true);

    try {
        await fetch(requestUrl, {
            method: requestMethod,
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(taskPayload)
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
        await fetch(`/api/studytasks/${taskId}`, {
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
        await fetch(`/api/studytasks/${taskId}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                title: task.title,
                category: task.category,
                dueDate: task.dueDate,
                notes: task.notes,
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
        loadData();
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
loadButton.addEventListener("click", () => loadData("Tasks loaded successfully."));
refreshButton.addEventListener("click", () => loadData("Data refreshed successfully."));
taskTableBody.addEventListener("click", handleTableClick);
window.addEventListener("beforeunload", stopPolling);

loadData();
startPolling();
