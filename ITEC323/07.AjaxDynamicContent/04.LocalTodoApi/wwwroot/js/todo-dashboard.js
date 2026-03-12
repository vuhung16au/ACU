const todoForm = document.getElementById("todoForm");
const todoIdInput = document.getElementById("todoId");
const titleInput = document.getElementById("title");
const categoryInput = document.getElementById("category");
const dueDateInput = document.getElementById("dueDate");
const descriptionInput = document.getElementById("description");
const isCompletedInput = document.getElementById("isCompleted");
const saveButton = document.getElementById("saveButton");
const resetButton = document.getElementById("resetButton");
const loadButton = document.getElementById("loadButton");
const refreshButton = document.getElementById("refreshButton");
const messagePanel = document.getElementById("messagePanel");
const loadingPanel = document.getElementById("loadingPanel");
const todoTableBody = document.getElementById("todoTableBody");
const totalItems = document.getElementById("totalItems");
const completedItems = document.getElementById("completedItems");
const pendingItems = document.getElementById("pendingItems");
const generatedAt = document.getElementById("generatedAt");

let isBusy = false;
let pollingTimer = null;
let currentItems = [];

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
    todoIdInput.value = "";
    todoForm.reset();
    saveButton.textContent = "Save Todo";
    clearMessage();
}

function getTodoPayload() {
    return {
        title: titleInput.value.trim(),
        category: categoryInput.value.trim(),
        dueDate: dueDateInput.value || null,
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

    return "The request failed. Check the browser console for more details.";
}

function formatDate(value) {
    if (!value) {
        return "No due date";
    }

    return new Date(value).toLocaleDateString();
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

function renderTodoItems(items) {
    currentItems = items;

    if (items.length === 0) {
        todoTableBody.innerHTML = `
            <tr>
                <td colspan="6" class="empty-state">No todo items are stored in memory yet. Add one with the form.</td>
            </tr>`;
        return;
    }

    todoTableBody.innerHTML = items.map((item) => {
        const statusClass = item.isCompleted ? "status-complete" : "status-pending";
        const statusLabel = item.isCompleted ? "Completed" : "Pending";
        const toggleLabel = item.isCompleted ? "Mark Pending" : "Mark Complete";

        return `
            <tr>
                <td>
                    <strong>${escapeHtml(item.title)}</strong>
                    <div class="text-muted">${escapeHtml(item.description ?? "No description added.")}</div>
                </td>
                <td>${escapeHtml(item.category)}</td>
                <td><span class="status-pill ${statusClass}">${statusLabel}</span></td>
                <td>${formatDate(item.dueDate)}</td>
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
    totalItems.textContent = summary.totalItems;
    completedItems.textContent = summary.completedItems;
    pendingItems.textContent = summary.pendingItems;
    generatedAt.textContent = formatDateTime(summary.generatedAt);
}

function fillForm(item) {
    todoIdInput.value = item.id;
    titleInput.value = item.title;
    categoryInput.value = item.category;
    dueDateInput.value = item.dueDate ? item.dueDate.slice(0, 10) : "";
    descriptionInput.value = item.description ?? "";
    isCompletedInput.checked = item.isCompleted;
    saveButton.textContent = "Update Todo";
    showMessage(`Editing todo item #${item.id}. Change the fields and click Update Todo.`, "warning");
    titleInput.focus();
}

async function loadData(successMessage, skipBusyGuard = false) {
    if (isBusy && !skipBusyGuard) {
        return;
    }

    clearMessage();
    setBusyState(true);

    try {
        const [items, summary] = await Promise.all([
            fetch("/api/todoitems").then(parseApiResponse),
            fetch("/api/todoitems/summary").then(parseApiResponse)
        ]);

        renderTodoItems(items);
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

async function saveTodoItem(event) {
    event.preventDefault();

    if (isBusy) {
        return;
    }

    clearMessage();

    const todoPayload = getTodoPayload();

    if (!todoPayload.title || !todoPayload.category) {
        showMessage("Title and category are required before sending the request.", "warning");
        return;
    }

    const todoId = todoIdInput.value;
    const requestUrl = todoId ? `/api/todoitems/${todoId}` : "/api/todoitems";
    const requestMethod = todoId ? "PUT" : "POST";

    setBusyState(true);

    try {
        await fetch(requestUrl, {
            method: requestMethod,
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(todoPayload)
        }).then(parseApiResponse);

        resetForm();
        await loadData(todoId ? "Todo item updated successfully." : "Todo item created successfully.", true);
    } catch (error) {
        showMessage(error.message, "error");
        setBusyState(false);
    }
}

async function deleteTodoItem(todoId) {
    if (isBusy) {
        return;
    }

    setBusyState(true);

    try {
        await fetch(`/api/todoitems/${todoId}`, {
            method: "DELETE"
        }).then(parseApiResponse);

        await loadData("Todo item deleted successfully.", true);
    } catch (error) {
        showMessage(error.message, "error");
        setBusyState(false);
    }
}

async function toggleTodoItem(todoId) {
    const todoItem = currentItems.find((item) => item.id === todoId);

    if (!todoItem || isBusy) {
        return;
    }

    setBusyState(true);

    try {
        await fetch(`/api/todoitems/${todoId}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                title: todoItem.title,
                category: todoItem.category,
                dueDate: todoItem.dueDate,
                description: todoItem.description,
                isCompleted: !todoItem.isCompleted
            })
        }).then(parseApiResponse);

        await loadData("Todo status updated successfully.", true);
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

    const todoId = Number(clickedButton.dataset.id);
    const action = clickedButton.dataset.action;

    if (action === "edit") {
        const todoItem = currentItems.find((item) => item.id === todoId);
        if (todoItem) {
            fillForm(todoItem);
        }
        return;
    }

    if (action === "toggle") {
        toggleTodoItem(todoId);
        return;
    }

    if (action === "delete") {
        deleteTodoItem(todoId);
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

todoForm.addEventListener("submit", saveTodoItem);
resetButton.addEventListener("click", resetForm);
loadButton.addEventListener("click", () => loadData("Todo items loaded successfully."));
refreshButton.addEventListener("click", () => loadData("Todo data refreshed successfully."));
todoTableBody.addEventListener("click", handleTableClick);
window.addEventListener("beforeunload", stopPolling);

loadData();
startPolling();
