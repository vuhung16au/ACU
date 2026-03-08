# Single Page Application (SPA) Architecture

## What Is a SPA?

**Single Page Application:** Web app that loads once and updates content dynamically without full page reloads.

**Traditional Multi-Page App:**
```
User clicks link → Browser requests new page → 
Server renders HTML → Full page reload → Display
```

**SPA:**
```
User clicks link → JavaScript updates view → 
Background API call → Update specific parts → No reload
```

## Traditional vs SPA

| Aspect | Traditional (MPA) | SPA |
|--------|------------------|-----|
| **Page Load** | Full reload each time | Load once |
| **Navigation** | Server renders new page | JavaScript changes view |
| **Data** | Embedded in HTML | Fetched via API |
| **State** | Lost on navigation | Maintained in memory |
| **Performance** | Slower transitions | Instant transitions |
| **SEO** | Excellent (native) | Requires setup |
| **Complexity** | Lower | Higher |
| **Initial Load** | Fast | Slower (downloads JS) |

## SPA Architecture Components

### 1. Frontend Application

**Responsibilities:**
- Render UI components
- Handle user interactions
- Manage application state
- Make API requests
- Client-side routing

**Technologies:**
- React, Vue, Angular, Blazor WASM
- JavaScript/TypeScript
- Build tools (Vite, Webpack)

### 2. Backend API

**Responsibilities:**
- Provide data endpoints
- Handle business logic
- Authenticate users
- Validate data
- Database operations

**Technologies:**
- ASP.NET Core Web API
- Minimal APIs
- RESTful or GraphQL

### 3. Communication Layer

```
┌─────────────┐         ┌─────────────┐
│   Frontend  │  HTTP   │   Backend   │
│  (React/    │ <────>  │  (.NET API) │
│   Vue/      │  JSON   │             │
│   Blazor)   │         │             │
└─────────────┘         └─────────────┘
```

## Client-Side Routing

### The Problem

SPA is single HTML page, but we want multiple "views".

### The Solution

**JavaScript Router:**
- Listens to URL changes
- Shows/hides components based on URL
- Updates browser history
- No server requests for navigation

**Example (React Router):**
```javascript
import { BrowserRouter, Routes, Route } from 'react-router-dom';

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/users" element={<Users />} />
                <Route path="/users/:id" element={<UserDetail />} />
            </Routes>
        </BrowserRouter>
    );
}
```

**URL Changes:**
- `localhost:3000/` → Shows `<Home />`
- `localhost:3000/users` → Shows `<Users />`
- `localhost:3000/users/123` → Shows `<UserDetail id={123} />`

**No page reload!**

## State Management

### What Is State?

Data that changes over time and affects what's displayed.

**Examples:**
- Current user info
- Shopping cart items
- Form input values
- Loading status

### Local State

Managed within a single component.

```javascript
// React example
const [count, setCount] = useState(0);
```

**Use when:** Data only needed in one component

### Global State

Shared across multiple components.

**Solutions:**
- **React:** Context API, Redux, Zustand
- **Vue:** Pinia, Vuex
- **Blazor:** Built-in state management

**Use when:** Data needed in many components (user, theme, cart)

## API Communication Patterns

### REST API Pattern

```javascript
// GET all users
fetch('/api/users')
    .then(res => res.json())
    .then(users => setUsers(users));

// GET single user
fetch('/api/users/123')
    .then(res => res.json())
    .then(user => setUser(user));

// POST new user
fetch('/api/users', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(newUser)
});

// PUT update
fetch('/api/users/123', {
    method: 'PUT',
    body: JSON.stringify(updatedUser)
});

// DELETE user
fetch('/api/users/123', { method: 'DELETE' });
```

### .NET API Endpoints

```csharp
// Program.cs (Minimal API)
app.MapGet("/api/users", async (AppDbContext db) =>
    await db.Users.ToListAsync());

app.MapGet("/api/users/{id}", async (int id, AppDbContext db) =>
    await db.Users.FindAsync(id));

app.MapPost("/api/users", async (User user, AppDbContext db) =>
{
    db.Users.Add(user);
    await db.SaveChangesAsync();
    return Results.Created($"/api/users/{user.Id}", user);
});

app.MapPut("/api/users/{id}", async (int id, User user, AppDbContext db) =>
{
    user.Id = id;
    db.Users.Update(user);
    await db.SaveChangesAsync();
    return Results.NoContent();
});

app.MapDelete("/api/users/{id}", async (int id, AppDbContext db) =>
{
    var user = await db.Users.FindAsync(id);
    if (user is null) return Results.NotFound();
    db.Users.Remove(user);
    await db.SaveChangesAsync();
    return Results.NoContent();
});
```

## SEO Challenges

### The Problem

**Search engines see:**
```html
<!DOCTYPE html>
<html>
<head><title>My App</title></head>
<body>
    <div id="root"></div>  <!-- Empty! -->
    <script src="app.js"></script>
</body>
</html>
```

Content is rendered by JavaScript **after** page loads.

### Solutions

**1. Server-Side Rendering (SSR)**
- Render initial HTML on server
- Send pre-rendered HTML to browser
- JavaScript "hydrates" to make interactive
- Best for public-facing apps

**2. Static Site Generation (SSG)**
- Pre-render pages at build time
- Serve static HTML files
- Best for content that rarely changes

**3. Pre-rendering Service**
- Use service like Prerender.io
- Detects search bots, serves pre-rendered HTML
- Easiest but costs money

**4. Blazor Server**
- Uses SignalR, renders on server
- SEO-friendly by default
- Different hosting model

## Authentication in SPAs

### JWT Token Pattern

```
1. User logs in → POST /api/auth/login
2. Server validates → Returns JWT token
3. Store token → localStorage or cookie
4. Subsequent requests → Include token in header
```

**Frontend (React):**
```javascript
// Login
const response = await fetch('/api/auth/login', {
    method: 'POST',
    body: JSON.stringify({ username, password })
});
const { token } = await response.json();
localStorage.setItem('authToken', token);

// Authenticated request
fetch('/api/users', {
    headers: {
        'Authorization': `Bearer ${localStorage.getItem('authToken')}`
    }
});
```

**Backend (ASP.NET):**
```csharp
// Requires authentication
app.MapGet("/api/users", async (AppDbContext db) =>
    await db.Users.ToListAsync())
    .RequireAuthorization();
```

## SPA Deployment

### Development

```
Frontend (React/Vue): localhost:5173 (Vite)
Backend (.NET API):   localhost:5001
```

Need CORS configuration (see [CORS Guide](../../07.AjaxDynamicContent/docs/cors-guide.md))

### Production

**Option 1: Separate Deployment**
```
Frontend: Netlify, Vercel, Azure Static Web Apps
Backend:  Azure App Service, AWS, Heroku
```

**Option 2: .NET Hosts Both**
```
.NET publishes frontend files to wwwroot/
Serves SPA and API from same origin
No CORS needed
```

```csharp
// Serve SPA files
app.UseStaticFiles();
app.MapFallbackToFile("index.html"); // All routes → index.html
```

## When NOT to Use SPA

**Avoid SPAs for:**

❌ **Simple websites** - Blogs, marketing sites (use Razor Pages)  
❌ **SEO-critical content** - Unless you set up SSR  
❌ **Low-bandwidth users** - Large initial download  
❌ **JavaScript-disabled browsers** - Won't work at all  
❌ **Tight deadline + small team** - Higher complexity  

**Consider instead:**
- Razor Pages with HTMX
- Server-rendered with Alpine.js
- Blazor Server (not WASM)

## When TO Use SPA

**SPAs are great for:**

✅ **Web applications** (not websites) - Gmail, Slack, Trello  
✅ **Dashboards** - Data visualization, admin panels  
✅ **Interactive tools** - Editors, calculators, configurators  
✅ **Real-time apps** - Chat, collaboration tools  
✅ **Mobile-first** - Responsive web apps  

## Hybrid Approach

**Best of both worlds:**

```
Public pages: Server-rendered (SEO)
    ↓
Login
    ↓
Private app: SPA (rich interactions)
```

**Example:**
- Homepage, blog, about → Razor Pages
- User dashboard → React SPA
- Both share .NET backend

## Progressive Web Apps (PWA)

**SPA + Offline capabilities:**

- Install like native app
- Work offline with Service Workers
- Push notifications
- Home screen icon

**Use when:** Mobile-first, need offline functionality

## Summary

**SPA Benefits:**
- ✅ Fast, app-like experience
- ✅ No page reloads
- ✅ Rich interactivity
- ✅ Shared logic between web/mobile

**SPA Challenges:**
- ⚠️ SEO setup needed
- ⚠️ Larger initial load
- ⚠️ More complex architecture
- ⚠️ JavaScript dependency

**Use SPA when:**
Building an **application** (not website) that needs rich interactivity.

**Avoid SPA when:**
Building a **website** where content and SEO are primary concerns.

---

**See SPAs in action:** [07.ReactBasics](../07.ReactBasics/), [08.VueBasics](../08.VueBasics/)
