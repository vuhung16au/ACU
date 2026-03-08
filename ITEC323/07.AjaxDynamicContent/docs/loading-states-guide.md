# Loading States Guide

## Why Loading States Matter

Users need feedback during asynchronous operations. Without it, they think the app is broken.

**Good UX:** Clear feedback for every action

## Loading State Types

### 1. Spinner/Loading Indicator

**Use for:** Short operations (1-5 seconds)

```html
<button id="loadBtn" onclick="loadData()">
    Load Data
    <span id="spinner" class="spinner-border spinner-border-sm" style="display:none;"></span>
</button>
<div id="result"></div>

<script>
async function loadData() {
    const spinner = document.getElementById('spinner');
    const result = document.getElementById('result');
    const btn = document.getElementById('loadBtn');
    
    // Show loading
    spinner.style.display = 'inline-block';
    btn.disabled = true;
    
    try {
        const response = await fetch('/api/data');
        const data = await response.json();
        result.textContent = data.message;
    } catch (error) {
        result.textContent = 'Error: ' + error.message;
    } finally {
        // Hide loading
        spinner.style.display = 'none';
        btn.disabled = false;
    }
}
</script>
```

### 2. Skeleton Screen

**Use for:** Page load, content preview

```html
<style>
    .skeleton {
        background: linear-gradient(90deg, #f0f0f0 25%, #e0e0e0 50%, #f0f0f0 75%);
        background-size: 200% 100%;
        animation: loading 1.5s infinite;
        border-radius: 4px;
    }
    
    @keyframes loading {
        0% { background-position: 200% 0; }
        100% { background-position: -200% 0; }
    }
    
    .skeleton-text {
        height: 16px;
        margin-bottom: 8px;
    }
    
    .skeleton-title {
        height: 24px;
        width: 60%;
        margin-bottom: 16px;
    }
</style>

<div id="content">
    <!-- Show while loading -->
    <div class="skeleton skeleton-title"></div>
    <div class="skeleton skeleton-text"></div>
    <div class="skeleton skeleton-text"></div>
    <div class="skeleton skeleton-text" style="width: 80%;"></div>
</div>

<script>
async function loadContent() {
    const response = await fetch('/api/article');
    const article = await response.json();
    
    // Replace skeleton with real content
    document.getElementById('content').innerHTML = `
        <h2>${article.title}</h2>
        <p>${article.body}</p>
    `;
}

loadContent();
</script>
```

### 3. Progress Bar

**Use for:** Multi-step processes, file uploads

```html
<div class="progress" style="display:none;" id="progress">
    <div class="progress-bar" id="progressBar" style="width: 0%"></div>
</div>

<script>
async function uploadFile(file) {
    const progress = document.getElementById('progress');
    const progressBar = document.getElementById('progressBar');
    
    progress.style.display = 'block';
    
    const formData = new FormData();
    formData.append('file', file);
    
    const xhr = new XMLHttpRequest();
    
    xhr.upload.addEventListener('progress', (e) => {
        if (e.lengthComputable) {
            const percent = (e.loaded / e.total) * 100;
            progressBar.style.width = percent + '%';
        }
    });
    
    xhr.open('POST', '/api/upload');
    xhr.send(formData);
}
</script>
```

### 4. Overlay/Modal

**Use for:** Blocking operations, prevent double-clicks

```html
<style>
    #loadingOverlay {
        display: none;
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: rgba(0, 0, 0, 0.5);
        z-index: 9999;
        justify-content: center;
        align-items: center;
    }
    
    #loadingOverlay.active {
        display: flex;
    }
</style>

<div id="loadingOverlay">
    <div class="spinner-border text-light" style="width: 3rem; height: 3rem;"></div>
</div>

<script>
function showLoading() {
    document.getElementById('loadingOverlay').classList.add('active');
}

function hideLoading() {
    document.getElementById('loadingOverlay').classList.remove('active');
}

async function saveData() {
    showLoading();
    try {
        await fetch('/api/save', { method: 'POST', body: data });
    } finally {
        hideLoading();
    }
}
</script>
```

## Bootstrap Spinners

```html
<!-- Small inline spinner -->
<span class="spinner-border spinner-border-sm"></span>

<!-- Standard spinner -->
<div class="spinner-border" role="status">
    <span class="visually-hidden">Loading...</span>
</div>

<!-- Growing spinner -->
<div class="spinner-grow" role="status">
    <span class="visually-hidden">Loading...</span>
</div>

<!-- Colored -->
<div class="spinner-border text-primary"></div>
<div class="spinner-border text-success"></div>
```

## HTMX Loading Indicators

```html
<style>
    .htmx-indicator {
        display: none;
    }
    
    .htmx-request .htmx-indicator {
        display: inline;
    }
    
    .htmx-request.htmx-disabled {
        opacity: 0.6;
        pointer-events: none;
    }
</style>

<button hx-get="/api/data" hx-target="#result">
    Load Data
    <span class="htmx-indicator spinner-border spinner-border-sm"></span>
</button>
<div id="result"></div>
```

## Disabling UI During Load

### Disable Button

```javascript
async function submitForm() {
    const btn = document.getElementById('submitBtn');
    
    btn.disabled = true;
    btn.textContent = 'Saving...';
    
    try {
        await fetch('/api/save', { method: 'POST' });
    } finally {
        btn.disabled = false;
        btn.textContent = 'Save';
    }
}
```

### Disable Form

```javascript
async function saveForm() {
    const form = document.getElementById('myForm');
    const inputs = form.querySelectorAll('input, button');
    
    // Disable all inputs
    inputs.forEach(input => input.disabled = true);
    
    try {
        await fetch('/api/save', { method: 'POST' });
    } finally {
        // Re-enable
        inputs.forEach(input => input.disabled = false);
    }
}
```

## Error States

```html
<button id="loadBtn" onclick="loadData()">Load</button>
<div id="result"></div>
<div id="error" class="alert alert-danger" style="display:none;"></div>

<script>
async function loadData() {
    const result = document.getElementById('result');
    const error = document.getElementById('error');
    
    error.style.display = 'none';
    result.textContent = 'Loading...';
    
    try {
        const response = await fetch('/api/data');
        
        if (!response.ok) {
            throw new Error('Failed to load');
        }
        
        const data = await response.json();
        result.textContent = data.message;
        
    } catch (err) {
        result.textContent = '';
        error.textContent = err.message;
        error.style.display = 'block';
    }
}
</script>
```

## Success States

```html
<button id="saveBtn" onclick="save()">Save</button>
<div id="success" class="alert alert-success" style="display:none;">
    ✅ Saved successfully!
</div>

<script>
async function save() {
    const success = document.getElementById('success');
    
    try {
        await fetch('/api/save', { method: 'POST' });
        
        // Show success
        success.style.display = 'block';
        
        // Auto-hide after 3 seconds
        setTimeout(() => {
            success.style.display = 'none';
        }, 3000);
        
    } catch (error) {
        alert('Error: ' + error.message);
    }
}
</script>
```

## Loading State State Machine

```javascript
class LoadingState {
    constructor() {
        this.state = 'idle';  // idle, loading, success, error
    }
    
    setLoading() {
        this.state = 'loading';
        this.updateUI();
    }
    
    setSuccess() {
        this.state = 'success';
        this.updateUI();
    }
    
    setError(message) {
        this.state = 'error';
        this.errorMessage = message;
        this.updateUI();
    }
    
    updateUI() {
        const spinner = document.getElementById('spinner');
        const error = document.getElementById('error');
        const success = document.getElementById('success');
        
        spinner.style.display = this.state === 'loading' ? 'block' : 'none';
        error.style.display = this.state === 'error' ? 'block' : 'none';
        success.style.display = this.state === 'success' ? 'block' : 'none';
        
        if (this.state === 'error') {
            error.textContent = this.errorMessage;
        }
    }
}
```

## Best Practices

✅ **Do:**
- Show loading indicator immediately
- Disable interactive elements during load
- Use appropriate loader for context
- Provide error feedback
- Auto-hide success messages (3-5 seconds)
- Use try-finally to ensure cleanup

❌ **Don't:**
- Leave users guessing if something is happening
- Use spinners for instant operations
- Forget to re-enable UI after error
- Use blocking overlays for simple updates
- Show technical error messages to users

## Quick Reference

```javascript
// Standard pattern
async function fetchData() {
    const btn = document.getElementById('btn');
    const spinner = document.getElementById('spinner');
    const result = document.getElementById('result');
    
    // Show loading
    btn.disabled = true;
    spinner.style.display = 'inline';
    
    try {
        const response = await fetch('/api/data');
        if (!response.ok) throw new Error('Failed');
        const data = await response.json();
        result.textContent = data.message;
    } catch (error) {
        alert('Error: ' + error.message);
    } finally {
        // Always cleanup
        btn.disabled = false;
        spinner.style.display = 'none';
    }
}
```
