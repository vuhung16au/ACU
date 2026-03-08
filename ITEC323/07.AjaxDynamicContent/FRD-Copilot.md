



# Week 7 AJAX Module - Planning & Structure

## Suggested Folder Name

**`07.AjaxDynamicContent`** or **`07.AjaxRealTimeUpdates`**

I recommend: **`07.AjaxDynamicContent`**

## Module Overview

**Learning Goal:** Make web pages "alive" by fetching and sending data in the background without full-page reloads

**Key Technologies:**
- JavaScript Fetch API
- HTMX (partial updates without custom JS)
- Blazor (C#-based SPA)
- ASP.NET Core Web API
- SignalR (real-time communication)
- Swagger/OpenAPI (API documentation)

---

## Proposed Project Structure

```
07.AjaxDynamicContent/
├── README.md
├── FRD.md
├── docs/
│   ├── fetch-api-guide.md
│   ├── htmx-guide.md
│   ├── blazor-basics-guide.md
│   ├── web-api-guide.md
│   ├── loading-states-guide.md
│   ├── cors-guide.md
│   ├── swagger-guide.md
│   └── wcf-intro.md (historical context)
├── 01.BasicFetchAPI/
├── 02.PartialPageUpdates/
├── 03.HtmxPartialRendering/
├── 04.LocalTodoApi/
├── 05.WeatherDashboard/
├── 06.BlazorCounter/
└── 07.ComprehensiveApp/
```

---

## Project Breakdown

### **01.BasicFetchAPI** (Introduction)
**Concept:** Simple Fetch API calling local endpoint

**Features:**
- Single Razor Page with button
- Click button → Fetch joke from local API endpoint
- Display result in `<div>`
- Show loading state (spinner)
- Error handling

**Files:**
- `Pages/Index.cshtml` - Frontend with Fetch JS
- `Pages/api/GetJoke.cshtml.cs` - Returns JSON joke
- Basic loading spinner CSS

**Learning Objectives:**
- Understand Fetch API syntax
- Handle JSON responses
- Basic async/await patterns
- Loading indicators

---

### **02.PartialPageUpdates** (Fetch with View Components)
**Concept:** Update specific page sections without reload

**Features:**
- Product list page
- Click "Load More" → Fetch next batch of products
- Append to existing list
- Uses View Component for rendering
- Pagination state management

**Files:**
- `Pages/Products.cshtml` - Main page
- `ViewComponents/ProductCardViewComponent.cs`
- `Views/Shared/Components/ProductCard/Default.cshtml`
- `Pages/api/GetProducts.cshtml.cs` - Returns JSON product array

**Learning Objectives:**
- Partial page updates
- View Components with AJAX
- DOM manipulation

---

### **03.HtmxPartialRendering** (No Custom JavaScript)
**Concept:** HTMX for partial updates using HTML attributes

**Features:**
- Search input with live results
- `hx-get`, `hx-trigger`, `hx-target` attributes
- Server returns HTML fragment (not JSON)
- Loading indicators with `htmx-indicator`

**Files:**
- `Pages/Search.cshtml` - HTMX frontend
- `Pages/api/SearchResults.cshtml` - Returns HTML partial
- HTMX CDN integration

**Learning Objectives:**
- HTMX declarative approach
- Difference between JSON vs HTML responses
- No JavaScript required

---

### **04.LocalTodoApi** (Complete Frontend/Backend)
**Concept:** Minimal self-contained app - no internet required

**Features:**
- Todo CRUD operations (Create, Read, Update, Delete)
- In-memory storage (no database)
- RESTful API with proper HTTP verbs
- Fetch API for all operations
- Runs completely offline

**API Endpoints:**
```
GET    /api/todos          - List all
POST   /api/todos          - Create
PUT    /api/todos/{id}     - Update
DELETE /api/todos/{id}     - Delete
```

**Files:**
- `Pages/Index.cshtml` - Single-page frontend
- `Controllers/TodosController.cs` - Web API controller
- `Models/Todo.cs`
- `Services/TodoService.cs` - In-memory storage

**Learning Objectives:**
- Build RESTful APIs
- HTTP verb attributes ([HttpGet], [HttpPost], etc.)
- Complete CRUD with AJAX
- Local development without external APIs

---

### **05.WeatherDashboard** (Real-Time Updates)
**Concept:** Weather app with auto-refresh every 15 seconds

**Features:**
- Display current weather (temperature, conditions, location)
- Uses OpenWeatherMap API (free tier)
- Auto-refresh with `setInterval()`
- Manual refresh button
- Loading states during fetch
- Error handling (API down, rate limit)
- Fallback: Can use mock data if no API key

**Files:**
- `Pages/Weather.cshtml` - Dashboard UI
- `Pages/api/GetWeather.cshtml.cs` - Server-side API call
- `wwwroot/css/weather.css` - Weather icons/styling
- `appsettings.json` - API key configuration

**Learning Objectives:**
- Timed polling with `setInterval()`
- External API integration
- Environment variable configuration
- Error handling strategies

---

### **06.BlazorCounter** (C# SPA Introduction)
**Concept:** Simple Blazor Server app with real-time updates

**Features:**
- Counter component
- Increment/decrement buttons
- Real-time sync across multiple browser tabs (SignalR)
- No JavaScript - all C#

**Files:**
- `Components/Counter.razor`
- `Program.cs` - Blazor Server setup
- Introduction to Blazor component model

**Learning Objectives:**
- Blazor as SPA alternative
- SignalR for real-time communication
- Component-based architecture
- C# instead of JavaScript

---

### **07.ComprehensiveApp** (Integration)
**Concept:** Task management app combining all techniques

**Features:**
- Dashboard with multiple widgets
- Task list (HTMX for updates)
- Live notifications (Blazor component)
- Weather widget (Fetch API with auto-refresh)
- API with Swagger documentation
- CORS configuration
- Loading states throughout
- Error boundaries

**Technologies Used:**
- Razor Pages (main layout)
- Fetch API (weather)
- HTMX (task updates)
- Blazor component (notifications)
- Web API with Swagger
- SignalR (real-time notifications)

**Files:**
- Multi-page Razor application
- Controllers for API endpoints
- Swagger configuration
- CORS setup
- Multiple View Components

**Learning Objectives:**
- Combine multiple AJAX techniques
- API documentation with Swagger
- CORS configuration
- Production-ready patterns

---

## Documentation Files (docs/)

### **fetch-api-guide.md**
- Fetch syntax (`fetch()`, `.then()`, `async/await`)
- Request methods (GET, POST, PUT, DELETE)
- Headers and body formatting
- Error handling patterns
- Examples with Razor Pages endpoints

### **htmx-guide.md**
- Core attributes (`hx-get`, `hx-post`, `hx-target`, `hx-swap`)
- Triggers (`hx-trigger` - click, keyup, etc.)
- Loading indicators
- Server returns HTML vs JSON
- When to use HTMX vs Fetch

### **blazor-basics-guide.md**
- Blazor Server vs Blazor WebAssembly
- Component structure (`@code` blocks)
- Event handling with C#
- SignalR integration (automatic)
- When to choose Blazor

### **web-api-guide.md**
- Creating API controllers
- HTTP verb attributes
- Returning `JsonResult` vs `IActionResult`
- Model binding from body/query
- Status codes (200, 201, 400, 404, 500)

### **loading-states-guide.md**
- Loading spinners (CSS)
- Skeleton screens
- Disable buttons during requests
- HTMX indicators (`htmx-indicator` class)
- Progress bars

### **cors-guide.md**
- What is CORS?
- When is CORS needed?
- Configuring CORS in `Program.cs`
- AllowAnyOrigin vs specific origins
- Preflight requests

### **swagger-guide.md**
- Installing Swashbuckle.AspNetCore
- Enabling Swagger UI
- XML comments for documentation
- Testing APIs in browser
- Swagger JSON schema

### **wcf-intro.md** (Brief historical context)
- What was WCF? (Legacy .NET Framework)
- SOAP vs REST
- Why we use Web API now
- Migration path (WCF → ASP.NET Core Web API)

---

## Key Learning Outcomes

After completing Week 7, students will:

✅ Use JavaScript Fetch API to make asynchronous requests  
✅ Build RESTful Web APIs with ASP.NET Core  
✅ Update page sections without full reload (partial updates)  
✅ Implement HTMX for declarative AJAX  
✅ Create simple Blazor components  
✅ Handle loading states and errors gracefully  
✅ Configure CORS for cross-origin requests  
✅ Document APIs with Swagger  
✅ Implement timed polling (`setInterval()`)  
✅ Understand when to use different AJAX techniques  

---

## Progressive Complexity

| Project | Difficulty | Focus |
|---------|-----------|-------|
| 01.BasicFetchAPI | ⭐ Beginner | Fetch basics, loading states |
| 02.PartialPageUpdates | ⭐⭐ Easy | DOM updates, View Components |
| 03.HtmxPartialRendering | ⭐⭐ Easy | HTMX, no JavaScript |
| 04.LocalTodoApi | ⭐⭐⭐ Medium | RESTful API, CRUD |
| 05.WeatherDashboard | ⭐⭐⭐ Medium | External API, polling |
| 06.BlazorCounter | ⭐⭐ Easy | Blazor intro, SignalR |
| 07.ComprehensiveApp | ⭐⭐⭐⭐ Advanced | Integration |

---

## Additional Considerations

### **Technology Choices**

**Fetch API vs Axios:**
- Start with native Fetch API (no dependencies)
- Mention Axios as alternative (simpler syntax, auto JSON parsing)
- Include Axios example in comprehensive app

**HTMX:**
- Essential for students who prefer minimal JavaScript
- Show contrast: Fetch requires JS, HTMX is declarative

**Blazor:**
- Important as C# alternative to JavaScript SPAs
- Keep examples simple (counter, basic forms)
- Mention Blazor WebAssembly vs Server

### **API Design**

- Use PageHandlers (`OnGetAsync()`) for simple endpoints
- Use Controllers for more complex APIs
- Show both approaches

### **Testing Without Internet**

- Mock data services for weather/external APIs
- Include fallback data files (JSON)
- Environment variable toggles

---