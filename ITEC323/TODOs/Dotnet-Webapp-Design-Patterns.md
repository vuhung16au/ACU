While MVC is a foundational concept, the modern .NET ecosystem has evolved significantly. Depending on whether you are building the user interface, the backend API, or the overall system architecture, different patterns dominate the landscape.

Here are the most popular web app design patterns in .NET right now:

### 0. Foundation patterns 

- MVC (Model-View-Controller) 
- MVVM (Model-View-ViewModel)

### 1. User Interface (UI) Patterns

* **Razor Pages (Page-Based Pattern):** This is actually Microsoft's recommended approach over traditional MVC for server-rendered HTML apps. Instead of splitting a page's logic across separate Model, View, and Controller folders, Razor Pages pairs the UI (HTML) directly with a "PageModel" (C#) in the same folder. It is much easier to maintain for page-heavy websites.
* **Blazor (Component-Based Pattern):** This is the breakout star for modern .NET web development. It allows you to build rich, interactive Single Page Applications (SPAs) using C# instead of JavaScript. You build reusable UI components that can run directly in the browser using WebAssembly or stream from the server.
* **Backend-for-Frontend (BFF):** When .NET developers *do* use JavaScript frameworks (like React, Angular, or Vue) for the frontend, they often use the BFF pattern. Instead of the frontend calling microservices directly, it calls a dedicated .NET backend tailored specifically for that UI. The BFF handles heavy lifting, security, and token management.

### 2. API & Routing Patterns

* **Controller-Based APIs (REST):** The traditional way to build APIs in .NET, relying on the same underlying mechanics as MVC to route web requests to specific C# classes (Controllers).
* **Minimal APIs:** Introduced recently, this is a highly popular, lightweight alternative to heavy Controllers. It allows developers to define API endpoints in just a few lines of code (often right in the startup file), making it incredibly fast and perfect for microservices.

### 3. Enterprise Backend Patterns

* **Clean Architecture (The Onion Architecture):** This is the gold standard for structuring the folders and projects of a medium-to-large .NET web app. It puts your core business logic (the "Domain") in the very center, entirely isolated from the outside world. The database, UI, and external APIs are pushed to the outer rings, meaning you can swap out a database without touching your core logic.
* **CQRS (Command Query Responsibility Segregation):** In complex apps, the code to read data is often very different from the code to update it. CQRS splits these operations in half: "Commands" change data, and "Queries" read data. In the .NET world, this is almost always implemented using a massively popular library called **MediatR**.
* **Microservices (Cloud-Native):** For massive, scalable web apps, developers break the monolith into dozens of small, independently deployable .NET services that communicate over APIs or message buses. Microsoft has even released a stack called **.NET Aspire** to specifically make building cloud-native microservices easier.
