
### Step 1: Create the Project

Open your terminal or command prompt and run the following command. The `-o` flag now tells the CLI to create a new folder named `HelloWorldApi`.

```bash
dotnet new web -o HelloWorldApi
cd HelloWorldApi

```

### Step 2: Review the Code

Open the newly created `HelloWorldApi` folder in your code editor. Your `Program.cs` file will be exactly the same clean, minimal setup:

```csharp
var builder = WebApplication.CreateBuilder(args);
var app = builder.Build();

app.MapGet("/", () => "Hello World!");

app.Run();

```

### Step 3: Run the API

Make sure your terminal is currently inside the `HelloWorldApi` directory, then start your server:

```bash
dotnet run

```

Once it builds, you can open the provided `http://localhost:<port>` link in your browser to see your API in action!

