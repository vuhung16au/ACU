# Modern JavaScript Guide

## ES6+ Essentials (2015-2026)

### Arrow Functions

```javascript
// Old way
function add(a, b) {
    return a + b;
}

// Arrow function
const add = (a, b) => a + b;

// With block
const greet = (name) => {
    console.log(`Hello ${name}`);
    return `Welcome!`;
};

// Single parameter (no parentheses needed)
const square = x => x * x;
```

**Use in callbacks:**
```javascript
// Clean and concise
document.querySelector('#btn').addEventListener('click', () => {
    console.log('Clicked!');
});

// Array operations
const numbers = [1, 2, 3, 4, 5];
const doubled = numbers.map(n => n * 2);
```

### Template Literals

```javascript
// Old way
const name = 'Alice';
const greeting = 'Hello ' + name + '!';

// Template literal
const greeting = `Hello ${name}!`;

// Multi-line strings
const html = `
    <div class="card">
        <h2>${title}</h2>
        <p>${content}</p>
    </div>
`;

// Expressions inside ${}
const price = 19.99;
const message = `Total: $${(price * 1.1).toFixed(2)}`;
```

### Destructuring

```javascript
// Object destructuring
const user = { name: 'Bob', age: 25, email: 'bob@example.com' };
const { name, age } = user;
console.log(name); // 'Bob'

// Array destructuring
const [first, second] = [10, 20, 30];
console.log(first);  // 10
console.log(second); // 20

// In function parameters
function displayUser({ name, email }) {
    console.log(`${name}: ${email}`);
}
displayUser(user);
```

### Spread Operator

```javascript
// Copy array
const arr1 = [1, 2, 3];
const arr2 = [...arr1, 4, 5]; // [1, 2, 3, 4, 5]

// Merge objects
const defaults = { theme: 'dark', lang: 'en' };
const userPrefs = { lang: 'fr' };
const settings = { ...defaults, ...userPrefs };
// { theme: 'dark', lang: 'fr' }

// Function arguments
const numbers = [1, 5, 3, 9, 2];
console.log(Math.max(...numbers)); // 9
```

### let and const

```javascript
// Use const for values that won't be reassigned
const API_URL = 'https://api.example.com';
const user = { name: 'Alice' };
user.name = 'Bob'; // OK - object is mutable
// user = {};      // Error - can't reassign

// Use let for values that will change
let counter = 0;
counter++;

// Avoid var (old, confusing scope rules)
```

## Array Methods

### map() - Transform Each Item

```javascript
const numbers = [1, 2, 3, 4];
const doubled = numbers.map(n => n * 2);
// [2, 4, 6, 8]

const users = [
    { name: 'Alice', age: 25 },
    { name: 'Bob', age: 30 }
];
const names = users.map(user => user.name);
// ['Alice', 'Bob']
```

### filter() - Keep Matching Items

```javascript
const numbers = [1, 2, 3, 4, 5, 6];
const evens = numbers.filter(n => n % 2 === 0);
// [2, 4, 6]

const adults = users.filter(user => user.age >= 18);
```

### find() - Get First Match

```javascript
const users = [
    { id: 1, name: 'Alice' },
    { id: 2, name: 'Bob' }
];
const user = users.find(u => u.id === 2);
// { id: 2, name: 'Bob' }
```

### reduce() - Combine to Single Value

```javascript
const numbers = [1, 2, 3, 4];
const sum = numbers.reduce((total, n) => total + n, 0);
// 10

// Complex example
const cart = [
    { name: 'Book', price: 20 },
    { name: 'Pen', price: 5 }
];
const total = cart.reduce((sum, item) => sum + item.price, 0);
// 25
```

### forEach() - Run Function for Each

```javascript
const names = ['Alice', 'Bob', 'Charlie'];
names.forEach(name => {
    console.log(`Hello ${name}`);
});

// Note: Can't break or return early
// Use for...of loop if you need that
```

## Promises

### What Is a Promise?

Represents a value that will be available **later** (async operation).

**Three States:**
- **Pending:** Operation in progress
- **Fulfilled:** Operation succeeded
- **Rejected:** Operation failed

### Creating Promises

```javascript
const myPromise = new Promise((resolve, reject) => {
    setTimeout(() => {
        const success = true;
        if (success) {
            resolve('Operation succeeded!');
        } else {
            reject('Operation failed!');
        }
    }, 1000);
});
```

### Using Promises (.then/.catch)

```javascript
myPromise
    .then(result => {
        console.log(result); // 'Operation succeeded!'
    })
    .catch(error => {
        console.error(error);
    });
```

### Chaining Promises

```javascript
fetch('/api/user/1')
    .then(response => response.json())     // Parse JSON
    .then(user => {
        console.log(user);
        return fetch(`/api/posts/${user.id}`); // Get user's posts
    })
    .then(response => response.json())
    .then(posts => {
        console.log(posts);
    })
    .catch(error => {
        console.error('Error:', error); // Catches any error in chain
    });
```

## Async/Await

### The Modern Way

**Benefits:**
- Looks like synchronous code
- Easier to read and debug
- Standard try/catch error handling

### Basic Syntax

```javascript
async function fetchUser() {
    const response = await fetch('/api/user/1');
    const user = await response.json();
    return user;
}

// Calling async function
fetchUser().then(user => console.log(user));
```

### Error Handling

```javascript
async function getUserData() {
    try {
        const response = await fetch('/api/user/1');
        
        if (!response.ok) {
            throw new Error(`HTTP ${response.status}`);
        }
        
        const user = await response.json();
        return user;
        
    } catch (error) {
        console.error('Failed to fetch user:', error);
        return null;
    }
}
```

### Sequential vs Parallel

```javascript
// Sequential (slow - waits for each)
async function sequential() {
    const user = await fetchUser();      // Wait
    const posts = await fetchPosts();    // Then wait
    return { user, posts };
}

// Parallel (fast - starts both at once)
async function parallel() {
    const [user, posts] = await Promise.all([
        fetchUser(),
        fetchPosts()
    ]);
    return { user, posts };
}
```

## Fetch API

### Basic GET Request

```javascript
async function getData() {
    const response = await fetch('/api/data');
    const data = await response.json();
    return data;
}
```

### POST with JSON

```javascript
async function createUser(userData) {
    const response = await fetch('/api/users', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(userData)
    });
    
    return await response.json();
}

// Usage
const newUser = { name: 'Alice', email: 'alice@example.com' };
createUser(newUser);
```

### Complete Example with Error Handling

```javascript
async function fetchWithErrorHandling(url) {
    try {
        const response = await fetch(url);
        
        // Check HTTP status
        if (!response.ok) {
            throw new Error(`HTTP ${response.status}: ${response.statusText}`);
        }
        
        const data = await response.json();
        return { success: true, data };
        
    } catch (error) {
        console.error('Fetch failed:', error);
        return { success: false, error: error.message };
    }
}
```

### Loading States

```javascript
async function loadUsers() {
    const statusEl = document.querySelector('#status');
    const listEl = document.querySelector('#userList');
    
    try {
        // Show loading
        statusEl.textContent = 'Loading...';
        
        const response = await fetch('/api/users');
        const users = await response.json();
        
        // Display results
        listEl.innerHTML = users.map(u => `
            <li>${u.name}</li>
        `).join('');
        
        statusEl.textContent = '';
        
    } catch (error) {
        statusEl.textContent = 'Failed to load users';
    }
}
```

## Practical Patterns

### Debouncing (Search Input)

```javascript
function debounce(func, delay) {
    let timeout;
    return function(...args) {
        clearTimeout(timeout);
        timeout = setTimeout(() => func.apply(this, args), delay);
    };
}

// Usage
const searchInput = document.querySelector('#search');
const debouncedSearch = debounce(async (query) => {
    const results = await fetch(`/api/search?q=${query}`);
    // Display results
}, 300);

searchInput.addEventListener('input', (e) => {
    debouncedSearch(e.target.value);
});
```

### Form Submission

```javascript
const form = document.querySelector('#myForm');

form.addEventListener('submit', async (e) => {
    e.preventDefault(); // Don't reload page
    
    const formData = new FormData(form);
    const data = Object.fromEntries(formData);
    
    try {
        const response = await fetch('/api/submit', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });
        
        if (response.ok) {
            alert('Success!');
            form.reset();
        }
    } catch (error) {
        alert('Submission failed');
    }
});
```

## Browser Compatibility (2026)

All modern JavaScript features covered here are supported in:

- ✅ Chrome 90+
- ✅ Edge 90+
- ✅ Firefox 88+
- ✅ Safari 14+

**No transpilation needed** for standard web development.

## Summary

✅ **Arrow functions** for concise code  
✅ **Template literals** for string interpolation  
✅ **Destructuring** for cleaner variable assignment  
✅ **Array methods** for data transformation  
✅ **Promises** for async operations  
✅ **Async/await** for readable async code  
✅ **Fetch API** for HTTP requests  

**Next:** Apply these in [01.VanillaJsBasics](../01.VanillaJsBasics/)
