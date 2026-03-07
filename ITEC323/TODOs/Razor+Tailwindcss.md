Integrating Tailwind CSS into an ASP.NET Core Razor Pages project is a great way to build modern, responsive UIs quickly. Since Razor Pages uses server-side rendering, the most standard approach is to use the Tailwind CLI via Node.js to watch and compile your CSS.

Here is the step-by-step guide to setting it up from scratch.

### Prerequisites

* **.NET SDK** installed.
* **Node.js and npm** installed (required to run the Tailwind CLI).

---

### Step 1: Create the Razor Pages Project

Open your terminal or command prompt and create a new Razor Pages web application:

```bash
dotnet new webapp -n RazorTailwindApp
cd RazorTailwindApp

```

### Step 2: Initialize npm and Install Tailwind

Next, initialize a `package.json` file and install Tailwind CSS as a development dependency.

```bash
npm init -y
npm install -D tailwindcss

```

Generate the Tailwind configuration file (`tailwind.config.js`):

```bash
npx tailwindcss init

```

### Step 3: Configure Tailwind

Open the newly created `tailwind.config.js` file in the root of your project. You need to tell Tailwind where to look for your HTML/Razor files so it knows which CSS classes to compile.

Update the `content` array to include your Razor Pages:

```javascript
/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./Pages/**/*.cshtml",
    "./Views/**/*.cshtml",
    "./wwwroot/**/*.html"
  ],
  theme: {
    extend: {},
  },
  plugins: [],
}

```

### Step 4: Set Up Your CSS Files

Create a new folder in the root of your project named `Styles` and create a file inside it called `app.css` (so the path is `Styles/app.css`). Add the Tailwind directives to this file:

```css
@tailwind base;
@tailwind components;
@tailwind utilities;

```

*(Note: We use a separate `Styles` folder so we don't accidentally overwrite our raw Tailwind directives when the CLI outputs the compiled CSS into `wwwroot`).*

### Step 5: Add Build Scripts

Open your `package.json` file and update the `"scripts"` section to include commands for building and watching your CSS.

```json
  "scripts": {
    "css:build": "tailwindcss -i ./Styles/app.css -o ./wwwroot/css/site.css --minify",
    "css:watch": "tailwindcss -i ./Styles/app.css -o ./wwwroot/css/site.css --watch"
  }

```

* `css:watch`: Runs in the background during development, updating `site.css` instantly when you save a `.cshtml` file.
* `css:build`: Compiles and minifies the CSS for production.

### Step 6: Link the Compiled CSS

Open your layout file located at `Pages/Shared/_Layout.cshtml`. Ensure that the `<link>` tag points to the output file (`site.css`), which ASP.NET templates usually include by default.

```html
<head>
    <link rel="stylesheet" href="~/css/site.css" asp-append-version="true" />
</head>

```

*(You can safely delete the default `site.css` contents in `wwwroot/css/site.css` as Tailwind will overwrite it).*

### Step 7: Run the Application

You will need to run two terminal windows (or split your terminal) during development.

**Terminal 1: Start the Tailwind Watcher**

```bash
npm run css:watch

```

**Terminal 2: Run the ASP.NET App**

```bash
dotnet watch run

```

Now, when you open `Pages/Index.cshtml` and add Tailwind classes (like `<h1 class="text-4xl font-bold text-blue-500">Hello Tailwind!</h1>`), the changes will automatically compile and reflect in your browser.

---
