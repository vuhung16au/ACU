Creating a Blazor application using the .NET CLI is the fastest way to get started. In modern .NET (like .NET 8, 9, or 10), Microsoft consolidated the templates into a single, unified `blazor` template that handles both server-side and client-side rendering.

Here is how you can create, run, and customize your first Blazor "Hello World" application from the command line.

### Step 1: Create the Project

Open your terminal or command prompt and run the following command. The `-o` flag tells the CLI to create a new output folder named `HelloWorldBlazor`.

```bash
dotnet new blazor -o HelloWorldBlazor

```

### Step 2: Navigate and Run

Move into your newly created project directory and start the application using the `dotnet run` command.

```bash
cd HelloWorldBlazor
dotnet run

```

Once the app compiles, your terminal will display a local URL (usually something like `http://localhost:5000` or `https://localhost:7000`). Open that link in your web browser to see the default template running.

### Step 3: Make it "Hello World!"

To actually make it a "Hello World" app, you need to edit the main component.

1. Open the project in your code editor (like Visual Studio or VS Code).
2. Navigate to the **Components/Pages** folder.
3. Open the `Home.razor` file.
4. Replace the existing boilerplate code with standard HTML and Blazor syntax:

```html
@page "/"

<PageTitle>Hello World</PageTitle>

<h1>Hello, World!</h1>
<p>Welcome to my very first Blazor application.</p>

```

Save the file. If you run the app using `dotnet watch` instead of `dotnet run`, the browser will automatically refresh the moment you hit save!
