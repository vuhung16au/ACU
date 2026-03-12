# QUICKSTART - 07.HelloWorldDocker

## Prerequisites

- Docker Desktop installed and running.
- Optional for local run without Docker:
  - .NET SDK 10.0

## 1. Open Terminal in Project Folder

From the repository root:

```bash
cd 01.HelloDotnet/07.HelloWorldDocker
```

## 2. Build the Docker Image

```bash
docker build -t helloworlddocker:1.0 .
```

## 3. Run the Container

```bash
docker run --rm -p 8080:8080 --name helloworlddocker-app helloworlddocker:1.0
```

## 4. Open in Browser

Go to:

[http://localhost:8080](http://localhost:8080)

You should see a page with this message:

Hello .NET 10 and C# 14

## 5. Stop the Container

Press Ctrl+C in the terminal where the container is running.

## Optional: Run Without Docker

```bash
dotnet run
```

Then browse to the local URL shown in terminal output.

## Troubleshooting

1. Port already in use:
   - Change host port, for example:

     ```bash
     docker run --rm -p 8081:8080 helloworlddocker:1.0
     ```

2. Docker not running:
   - Start Docker Desktop and retry.

3. Build fails on image pull:
   - Confirm internet access and retry pulling base image:

     ```bash
     docker pull mcr.microsoft.com/dotnet/sdk:10.0
     ```
