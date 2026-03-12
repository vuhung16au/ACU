const refreshButton = document.getElementById("refreshButton");
const messagePanel = document.getElementById("messagePanel");
const loadingPanel = document.getElementById("loadingPanel");
const statusMessage = document.getElementById("statusMessage");
const refreshedAt = document.getElementById("refreshedAt");
const cityCount = document.getElementById("cityCount");
const currentWeatherPanel = document.getElementById("currentWeatherPanel");
const forecastPanel = document.getElementById("forecastPanel");

let isBusy = false;
let pollingTimer = null;

function setBusyState(busy) {
    isBusy = busy;
    refreshButton.disabled = busy;
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

async function parseApiResponse(response) {
    const responseText = await response.text();
    const data = responseText ? JSON.parse(responseText) : null;

    if (!response.ok) {
        throw new Error("The local weather request failed.");
    }

    return data;
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

function renderCurrentWeather(items) {
    if (items.length === 0) {
        currentWeatherPanel.innerHTML = '<p class="empty-state">No city snapshots are available.</p>';
        return;
    }

    currentWeatherPanel.innerHTML = items.map((item) => `
        <article class="weather-card">
            <div class="weather-meta">${escapeHtml(item.city)} · ${formatDateTime(item.observedAt)}</div>
            <div class="temperature">${item.temperatureCelsius}&deg;C</div>
            <p><strong>${escapeHtml(item.condition)}</strong></p>
            <p class="mb-0">Humidity: ${item.humidityPercent}% · Wind: ${item.windSpeedKph} km/h</p>
        </article>`).join("");
}

function renderForecast(items) {
    if (items.length === 0) {
        forecastPanel.innerHTML = '<p class="empty-state">No forecast items are available.</p>';
        return;
    }

    forecastPanel.innerHTML = items.map((item) => `
        <article class="forecast-card">
            <div class="forecast-meta">${escapeHtml(item.day)}</div>
            <h3>${escapeHtml(item.condition)}</h3>
            <p class="mb-0">High: ${item.maxTemperatureCelsius}&deg;C · Low: ${item.minTemperatureCelsius}&deg;C</p>
        </article>`).join("");
}

function renderDashboard(data) {
    statusMessage.textContent = data.statusMessage;
    refreshedAt.textContent = formatDateTime(data.refreshedAt);
    cityCount.textContent = data.currentWeather.length;
    renderCurrentWeather(data.currentWeather);
    renderForecast(data.forecast);
}

async function loadDashboard(successMessage) {
    if (isBusy) {
        return;
    }

    clearMessage();
    setBusyState(true);

    try {
        const data = await fetch("/api/weatherdashboard").then(parseApiResponse);
        renderDashboard(data);

        if (successMessage) {
            showMessage(successMessage, "success");
        }
    } catch (error) {
        showMessage(error.message, "error");
    } finally {
        setBusyState(false);
    }
}

function startPolling() {
    stopPolling();
    pollingTimer = window.setInterval(() => {
        loadDashboard();
    }, 15000);
}

function stopPolling() {
    if (pollingTimer) {
        window.clearInterval(pollingTimer);
        pollingTimer = null;
    }
}

refreshButton.addEventListener("click", () => loadDashboard("Weather data refreshed successfully."));
window.addEventListener("beforeunload", stopPolling);

loadDashboard("Weather data loaded successfully.");
startPolling();
