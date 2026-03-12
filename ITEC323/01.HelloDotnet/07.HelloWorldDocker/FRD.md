# FRD - 07.HelloWorldDocker

## 1. Purpose

This demo provides a beginner-friendly example of packaging and running an ASP.NET Core Razor Pages application using Docker.

## 2. Functional Requirements

1. The project shall target .NET 10.
2. The project shall use C# 14 language version.
3. The app shall expose a home page at the root path (/).
4. The home page shall display a clear message indicating the app runs with .NET 10 and C# 14.
5. The repository shall include a Dockerfile that supports building and running the app.
6. The Dockerfile shall use a multi-stage build process.
7. The runtime container shall listen on port 8080.
8. The project shall include beginner-friendly setup and run instructions.

## 3. Non-Functional Requirements

1. Documentation shall be simple and suitable for first-time learners.
2. Commands shall be copy-ready and tested where possible.
3. The demo shall minimize complexity and dependencies.

## 4. Constraints

1. Use .NET 10 and C# 14.
2. Use Microsoft .NET container images from MCR.
3. Keep project scope limited to a minimal Razor Pages app.

## 5. Success Criteria

1. A user can build the image with docker build.
2. A user can run the container and view the page at [http://localhost:8080](http://localhost:8080).
3. The displayed page confirms .NET 10 and C# 14 context.
4. All requested documentation files are present and clear.
