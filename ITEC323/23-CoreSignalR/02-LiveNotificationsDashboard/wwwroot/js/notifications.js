"use strict";

const statusBadge = document.getElementById("connectionStatus");
const notificationsList = document.getElementById("notificationsList");
const totalCount = document.getElementById("totalCount");
const infoCount = document.getElementById("infoCount");
const successCount = document.getElementById("successCount");
const warningCount = document.getElementById("warningCount");
const eventButtons = document.querySelectorAll(".event-button");

/**
 * Updates the small connection badge shown in the control panel.
 * @param {string} message The status text to display.
 */
function setStatus(message) {
    statusBadge.textContent = message;
}

/**
 * Formats a UTC date string into a short local time.
 * @param {string} utcValue The incoming UTC date value.
 * @returns {string} A short local time string.
 */
function formatTime(utcValue) {
    const date = new Date(utcValue);

    return date.toLocaleTimeString([], {
        hour: "2-digit",
        minute: "2-digit"
    });
}

/**
 * Updates the top summary cards using the latest counts.
 * @param {{ totalNotifications: number, infoCount: number, successCount: number, warningCount: number }} summary The latest summary data.
 */
function renderSummary(summary) {
    totalCount.textContent = summary.totalNotifications;
    infoCount.textContent = summary.infoCount;
    successCount.textContent = summary.successCount;
    warningCount.textContent = summary.warningCount;
}

/**
 * Creates one notification list item.
 * textContent is used so dynamic values stay as plain text.
 * @param {{ title: string, message: string, severity: string, source: string, createdAtUtc: string }} notification The notification data.
 * @returns {HTMLLIElement} The generated list item.
 */
function buildNotificationItem(notification) {
    const listItem = document.createElement("li");
    listItem.className = `notification-item notification-item-${notification.severity}`;

    const header = document.createElement("div");
    header.className = "notification-header";

    const title = document.createElement("strong");
    title.textContent = notification.title;

    const meta = document.createElement("span");
    meta.className = "notification-meta";
    meta.textContent = `${notification.source} • ${formatTime(notification.createdAtUtc)}`;

    const message = document.createElement("p");
    message.className = "notification-message";
    message.textContent = notification.message;

    header.appendChild(title);
    header.appendChild(meta);
    listItem.appendChild(header);
    listItem.appendChild(message);

    return listItem;
}

/**
 * Adds a new notification to the top of the list.
 * @param {{ title: string, message: string, severity: string, source: string, createdAtUtc: string }} notification The notification data.
 */
function prependNotification(notification) {
    notificationsList.prepend(buildNotificationItem(notification));

    while (notificationsList.children.length > 12) {
        notificationsList.removeChild(notificationsList.lastElementChild);
    }
}

/**
 * Replaces the current list with the provided notifications.
 * @param {Array<{ title: string, message: string, severity: string, source: string, createdAtUtc: string }>} notifications The notifications to render.
 */
function renderNotifications(notifications) {
    notificationsList.innerHTML = "";

    notifications.forEach((notification) => {
        notificationsList.appendChild(buildNotificationItem(notification));
    });
}

async function startConnection() {
    if (!window.signalR) {
        setStatus("SignalR client failed to load.");
        return;
    }

    const connection = new window.signalR.HubConnectionBuilder()
        .withUrl("/notificationsHub")
        .withAutomaticReconnect()
        .build();

    connection.on("LoadDashboard", (notifications, summary) => {
        renderNotifications(notifications);
        renderSummary(summary);
    });

    connection.on("ReceiveNotification", (notification, summary) => {
        prependNotification(notification);
        renderSummary(summary);
    });

    connection.onreconnecting(() => {
        eventButtons.forEach((button) => {
            button.disabled = true;
        });
        setStatus("Reconnecting...");
    });

    connection.onreconnected(() => {
        eventButtons.forEach((button) => {
            button.disabled = false;
        });
        setStatus("Connected");
    });

    connection.onclose(() => {
        eventButtons.forEach((button) => {
            button.disabled = true;
        });
        setStatus("Disconnected");
    });

    eventButtons.forEach((button) => {
        button.addEventListener("click", async () => {
            try {
                await connection.invoke(
                    "SendManualNotification",
                    button.dataset.title,
                    button.dataset.message,
                    button.dataset.severity,
                    button.dataset.source);
            } catch (error) {
                console.error(error);
                setStatus("Send failed");
            }
        });
    });

    try {
        await connection.start();
        eventButtons.forEach((button) => {
            button.disabled = false;
        });
        setStatus("Connected");
    } catch (error) {
        console.error(error);
        setStatus("Retrying...");
        setTimeout(startConnection, 3000);
    }
}

startConnection();
