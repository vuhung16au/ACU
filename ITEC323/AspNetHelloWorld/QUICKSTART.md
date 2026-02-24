# Quick start

Get AspNetHelloWorld running in under a minute.

## Prerequisites

- [.NET SDK](https://dotnet.microsoft.com/download) installed (e.g. .NET 10 or .NET 8)

## Option A: You already have the project

```bash
cd AspNetHelloWorld
dotnet run
```

Open **http://localhost:5150** in your browser. You should see:

```
Hello World!
```

## Option B: Create the project from scratch

```bash
dotnet new web -n AspNetHelloWorld
cd AspNetHelloWorld
dotnet run
```

Then open **http://localhost:5150**.

---

**Stop the app:** Press `Ctrl+C` in the terminal.

**Run with hot reload (optional):**

```bash
dotnet watch run
```

See [README.md](README.md) and [docs/HelloASP.NET.md](docs/HelloASP.NET.md) for more.
