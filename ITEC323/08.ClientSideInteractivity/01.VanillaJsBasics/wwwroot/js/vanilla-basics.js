const demoCards = document.querySelectorAll('[data-demo-card]');
const selectButtons = document.querySelectorAll('[data-select-card]');
const statusText = document.querySelector('#statusText');
const toggleEmphasisButton = document.querySelector('#toggleEmphasis');
const clearSelectionButton = document.querySelector('#clearSelection');
const outputInput = document.querySelector('#outputInput');
const safeOutput = document.querySelector('#safeOutput');
const htmlOutput = document.querySelector('#htmlOutput');
const studentForm = document.querySelector('#studentForm');
const formMessage = document.querySelector('#formMessage');
const studentCards = document.querySelector('#studentCards');
const resetStudentsButton = document.querySelector('#resetStudentsButton');

let selectedCardIndex = -1;

function setStatusMessage(message) {
    if (!statusText) {
        return;
    }

    statusText.textContent = message;
}

function clearCardSelection() {
    demoCards.forEach((card) => {
        card.classList.remove('is-selected');
    });

    selectedCardIndex = -1;
    setStatusMessage('No card selected yet.');
}

selectButtons.forEach((button) => {
    button.addEventListener('click', () => {
        const indexText = button.getAttribute('data-select-card');
        const index = Number(indexText);

        if (!Number.isInteger(index) || index < 0 || index >= demoCards.length) {
            return;
        }

        clearCardSelection();
        demoCards[index].classList.add('is-selected');
        selectedCardIndex = index;
        setStatusMessage(`Selected card ${index + 1}: ${demoCards[index].querySelector('h2')?.textContent ?? 'Unknown'}.`);
    });
});

if (toggleEmphasisButton) {
    toggleEmphasisButton.addEventListener('click', () => {
        document.body.classList.toggle('emphasis-mode');
    });
}

if (clearSelectionButton) {
    clearSelectionButton.addEventListener('click', () => {
        clearCardSelection();
    });
}

if (outputInput && safeOutput && htmlOutput) {
    outputInput.addEventListener('input', () => {
        const value = outputInput.value.trim();
        const safeValue = value.length > 0 ? value : 'Waiting for user input...';

        safeOutput.textContent = safeValue;

        // We use controlled text inserted into known markup for a simple classroom comparison.
        htmlOutput.innerHTML = `<strong>${safeValue}</strong> <em>(rendered with innerHTML)</em>`;
    });
}

function showFormMessage(message, messageClass) {
    if (!formMessage) {
        return;
    }

    formMessage.textContent = message;
    formMessage.classList.remove('hidden', 'message-success', 'message-warning');
    formMessage.classList.add(messageClass);
}

function createStudentCard(name, topic) {
    const card = document.createElement('article');
    card.className = 'info-card';

    const title = document.createElement('h3');
    title.textContent = name;

    const topicText = document.createElement('p');
    topicText.textContent = `Favourite topic: ${topic}`;

    card.appendChild(title);
    card.appendChild(topicText);
    return card;
}

if (studentForm && studentCards) {
    studentForm.addEventListener('submit', (event) => {
        event.preventDefault();

        const nameInput = /** @type {HTMLInputElement | null} */ (document.querySelector('#studentName'));
        const topicInput = /** @type {HTMLInputElement | null} */ (document.querySelector('#studentTopic'));

        const name = nameInput?.value.trim() ?? '';
        const topic = topicInput?.value.trim() ?? '';

        if (name.length < 2 || topic.length < 2) {
            showFormMessage('Please enter at least 2 characters for both fields.', 'message-warning');
            return;
        }

        studentCards.prepend(createStudentCard(name, topic));
        showFormMessage('Student card added successfully.', 'message-success');

        studentForm.reset();
        nameInput?.focus();
    });
}

if (resetStudentsButton && studentCards) {
    resetStudentsButton.addEventListener('click', () => {
        studentCards.innerHTML = '';
        showFormMessage('Student cards cleared.', 'message-warning');
    });
}
