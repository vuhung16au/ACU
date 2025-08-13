// Custom JavaScript for Spring Boot Web App

document.addEventListener('DOMContentLoaded', function() {
    console.log('Spring Boot Web App loaded successfully!');
    
    // Add fade-in animation to main content
    const mainContent = document.querySelector('main');
    if (mainContent) {
        mainContent.classList.add('fade-in');
    }
    
    // Initialize tooltips
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });
    
    // Form validation enhancement
    enhanceFormValidation();
    
    // Language selector enhancement
    enhanceLanguageSelector();
    
    // Add smooth scrolling to anchor links
    addSmoothScrolling();
    
    // Add current time display
    updateCurrentTime();
});

/**
 * Enhance form validation with custom behavior
 */
function enhanceFormValidation() {
    const forms = document.querySelectorAll('.needs-validation');
    
    forms.forEach(form => {
        // Real-time validation feedback
        const inputs = form.querySelectorAll('input, textarea, select');
        inputs.forEach(input => {
            input.addEventListener('blur', function() {
                validateField(this);
            });
            
            input.addEventListener('input', function() {
                if (this.classList.contains('is-invalid')) {
                    validateField(this);
                }
            });
        });
        
        // Form submission handling
        form.addEventListener('submit', function(event) {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
                
                // Focus on first invalid field
                const firstInvalid = form.querySelector('.is-invalid');
                if (firstInvalid) {
                    firstInvalid.focus();
                }
            }
            
            form.classList.add('was-validated');
        });
    });
}

/**
 * Validate individual form field
 */
function validateField(field) {
    const isValid = field.checkValidity();
    
    if (isValid) {
        field.classList.remove('is-invalid');
        field.classList.add('is-valid');
    } else {
        field.classList.remove('is-valid');
        field.classList.add('is-invalid');
    }
}

/**
 * Enhance language selector functionality
 */
function enhanceLanguageSelector() {
    const languageLinks = document.querySelectorAll('a[href*="lang="]');
    
    languageLinks.forEach(link => {
        link.addEventListener('click', function(e) {
            // Add loading indicator
            const originalText = this.textContent;
            this.textContent = 'Loading...';
            this.disabled = true;
            
            // Re-enable after a short delay (simulating page load)
            setTimeout(() => {
                this.textContent = originalText;
                this.disabled = false;
            }, 1000);
        });
    });
}

/**
 * Add smooth scrolling to anchor links
 */
function addSmoothScrolling() {
    const anchorLinks = document.querySelectorAll('a[href^="#"]');
    
    anchorLinks.forEach(link => {
        link.addEventListener('click', function(e) {
            e.preventDefault();
            
            const targetId = this.getAttribute('href');
            const targetElement = document.querySelector(targetId);
            
            if (targetElement) {
                targetElement.scrollIntoView({
                    behavior: 'smooth',
                    block: 'start'
                });
            }
        });
    });
}

/**
 * Update current time display
 */
function updateCurrentTime() {
    const timeElements = document.querySelectorAll('[data-time-update]');
    
    if (timeElements.length > 0) {
        setInterval(() => {
            const now = new Date();
            const timeString = now.toLocaleString();
            
            timeElements.forEach(element => {
                element.textContent = timeString;
            });
        }, 1000);
    }
}

/**
 * Show notification message
 */
function showNotification(message, type = 'info') {
    const notification = document.createElement('div');
    notification.className = `alert alert-${type} alert-dismissible fade show position-fixed`;
    notification.style.cssText = 'top: 20px; right: 20px; z-index: 1050; min-width: 300px;';
    
    notification.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    `;
    
    document.body.appendChild(notification);
    
    // Auto-remove after 5 seconds
    setTimeout(() => {
        if (notification.parentNode) {
            notification.remove();
        }
    }, 5000);
}

/**
 * Handle API responses
 */
function handleApiResponse(response, successCallback, errorCallback) {
    if (response.ok) {
        return response.text().then(data => {
            if (successCallback) successCallback(data);
            return data;
        });
    } else {
        if (errorCallback) errorCallback(response.statusText);
        throw new Error(`HTTP error! status: ${response.status}`);
    }
}

/**
 * Make API request to get current time
 */
function getCurrentTime(format = 'default') {
    fetch(`/api/time?format=${format}`)
        .then(response => handleApiResponse(
            response,
            (data) => {
                console.log('Current time:', data);
                // Update time display if needed
                const timeDisplay = document.querySelector('#current-time-display');
                if (timeDisplay) {
                    timeDisplay.textContent = data;
                }
            },
            (error) => {
                console.error('Error fetching time:', error);
                showNotification('Failed to fetch current time', 'danger');
            }
        ));
}

/**
 * Utility function to format date
 */
function formatDate(date, format = 'default') {
    const options = {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit'
    };
    
    if (format === 'short') {
        options.second = undefined;
    }
    
    return date.toLocaleDateString(undefined, options);
}

// Export functions for global access
window.WebApp = {
    showNotification,
    getCurrentTime,
    formatDate,
    handleApiResponse
};
