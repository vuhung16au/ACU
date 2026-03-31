"use strict";

const sendButton = document.getElementById("sendButton");
const userInput = document.getElementById("userInput");
const messageInput = document.getElementById("messageInput");
const messagesList = document.getElementById("messagesList");
const statusMessage = document.getElementById("statusMessage");

/**
 * Updates the small connection status label shown beside the send button.
 * @param {string} message The status text to display.
 */
function setStatus(message) {
    statusMessage.textContent = message;
}

/**
 * Adds a new message item to the chat list.
 * textContent is used so user input is treated as plain text instead of HTML.
 * @param {string} user The sender name.
 * @param {string} message The message text.
 */
function addMessage(user, message) {
    const listItem = document.createElement("li");
    listItem.textContent = `${user}: ${message}`;
    messagesList.appendChild(listItem);
}

async function startConnection() {
    if (!window.signalR) {
        setStatus("SignalR client failed to load.");
        return;
    }

    const connection = new window.signalR.HubConnectionBuilder()
        .withUrl("/chatHub")
        .withAutomaticReconnect()
        .build();

    connection.on("ReceiveMessage", (user, message) => {
        addMessage(user, message);
    });

    connection.onreconnecting(() => {
        sendButton.disabled = true;
        setStatus("Reconnecting...");
    });

    connection.onreconnected(() => {
        sendButton.disabled = false;
        setStatus("Connected");
    });

    connection.onclose(() => {
        sendButton.disabled = true;
        setStatus("Disconnected. Refresh to reconnect.");
    });

    sendButton.addEventListener("click", async (event) => {
        event.preventDefault();

        const user = userInput.value.trim() || "Anonymous";
        const message = messageInput.value.trim();

        if (!message) {
            setStatus("Type a message before sending.");
            return;
        }

        try {
            await connection.invoke("SendMessage", user, message);
            messageInput.value = "";
            messageInput.focus();
            setStatus("Connected");
        } catch (error) {
            console.error(error);
            setStatus("Message failed to send.");
        }
    });

    try {
        await connection.start();
        sendButton.disabled = false;
        setStatus("Connected");
        messageInput.focus();
    } catch (error) {
        console.error(error);
        setStatus("Unable to connect. Retrying in 3 seconds...");
        setTimeout(startConnection, 3000);
    }
}

startConnection();
