# Functional Requirements Document

## Purpose

Create a documentation-first teaching module that analyzes Microsoft's official `dotnet/eShop` reference application and shows how to run it locally as a class demo using Docker-backed infrastructure.

## Functional Requirements

1. The module must explain that `35-eShop-Architecture` is an analysis and demonstration module, not a from-scratch implementation project.
2. The module must include a beginner-friendly architecture document for the official upstream `dotnet/eShop` sample.
3. The architecture document must explain the role of `.NET Aspire` and `eShop.AppHost`.
4. The architecture document must explain how Docker is used for local infrastructure, including RabbitMQ, Redis, and PostgreSQL.
5. The architecture document must explain the responsibilities of at least `Catalog`, `Basket`, `Ordering`, `Identity`, and `Webhooks`.
6. The architecture document must explain how RabbitMQ supports asynchronous service-to-service communication.
7. The architecture document must explain how data is separated across services instead of using one shared database.
8. The module must include a local demo workflow that runs the upstream eShop sample from the official repository.
9. The module must include a shell script that automates the class demo setup without storing upstream source code in this repository.
10. The module documentation must include equivalent manual commands so the demo can be run without the helper script.

## Non-Functional Requirements

1. The module must remain educational and readable for students who have already learned Razor Pages and simpler ASP.NET Core applications.
2. The analysis must describe the current Aspire-based architecture and must not present the app as a legacy Docker Compose-first sample.
3. The helper script must operate outside the repository workspace by using a temporary folder such as `/tmp/eshop-demo`.
4. The helper script must pin to a specific upstream commit for repeatable classroom demos.
5. The helper script must fail with clear, friendly messages when Docker, `.NET 10`, or a trusted local development certificate is unavailable.

## Success Criteria

- all required module documentation files are present
- students can explain why eShop uses multiple services instead of a single web app
- students can identify RabbitMQ, Redis, and PostgreSQL in the local demo
- the helper script can launch the upstream `eShop.AppHost` project when prerequisites are installed
- instructors can use the module as a guided demo without copying upstream code into `ITEC323`
